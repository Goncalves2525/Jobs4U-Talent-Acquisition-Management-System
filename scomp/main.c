#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <sys/wait.h>
#include "functions.h"

#define CONFIGFILE "configs.txt"

volatile pid_t pid;
volatile sig_atomic_t nChildren;


void sigIntHandler(int signal){
	//kill(pid, SIGTERM);
	waitpid(pid, NULL, 0);
	//printf("Killed monitor child\n");

	int i;
	for(i=0; i<nChildren; i++){
		wait(NULL);
		//printf("Killed worker child %d\n", i+1);
	}

	exit(EXIT_SUCCESS);
}


int main(int argc, char *argv[]) {

    // Usar argumentos do ficheiro de configuração ou do command prompt: arguments arglocal:
	arglocal arg;
    if(argc>1){
        strcpy(arg.inputPath, argv[1]);
        strcpy(arg.outputPath, argv[2]);
        strcpy(arg.reportPath, argv[3]);
        arg.nWorkers = atoi(argv[4]);
        arg.timeInterval = atoi(argv[5]);
    } else {
        if(extractArguments(CONFIGFILE, &arg) == -1){
            printf("Erro ao ler ficheiro configs.txt.\n");
            return -1; // terminar com erro
        };
    }

	// Validar se tem todos os argumentos:
    if(validateAllArgumentsAvailable(&arg) == -1){
        return -1; // terminar com erro
    };

	// Variável a ser usada no SIGINT handler:
	nChildren = arg.nWorkers;
    
    // Cria o diretorio output, caso não exista:
	createDirectory(arg.outputPath);
	
	// Cria o diretorio report, caso não exista:
	char reportInOutput[100];
	strcpy(reportInOutput, arg.outputPath);
	strcat(reportInOutput, "/");
	strcat(reportInOutput, arg.reportPath);
	createDirectory(reportInOutput);
	
	// Cria o ficheiro report, caso não exista:
	char sessionFile[200];
	strcpy(sessionFile, reportInOutput);
	strcat(sessionFile, "/reportSession_");
	time_t currentTime = time(NULL);
	char timeString[20];
    snprintf(timeString, sizeof(timeString), "%ld", currentTime);
	strcat(sessionFile, timeString);
	strcat(sessionFile, ".txt");
	createSessionFile(sessionFile);

    // Prepara SIGUSR1:
	struct sigaction act;                      
	memset(&act, 0, sizeof(struct sigaction)); 
	act.sa_handler = sigUsr1Handler;            
	sigaction(SIGUSR1, &act, NULL);             

    // Cria filho que monitoriza:
    pid = fork();
    
    // Tratamento erros fork:
    if(pid == -1){
        perror("Fork ERROR.\n");
        exit(EXIT_FAILURE);
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Código filho que monitoriza:
    if(pid == 0){
        monitor_files(arg.inputPath, arg.timeInterval);
        exit(EXIT_SUCCESS);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Continua código do processo principal (pai)
    
    // Preparar os PIPES para comunicação entre pai e filhos trabalhadores:
    enum extremidade {READ=0, WRITE=1};  

	// Pipes para que o pai possa enviar dados para os filhos trabalhadores:
    int pipeDown[arg.nWorkers][2];                    
    memset(pipeDown, 0, sizeof(pipeDown));                  
    for(int i = 0; i < arg.nWorkers; i++){      
        if(pipe(pipeDown[i]) == -1){                  
            perror("Pipe Down failed!\n");           
            exit(EXIT_FAILURE);                 
        }                                       
    }    

	// Pipes para que os filhos trabalhadores possam enviar dados para o pai:
	int pipeUp[arg.nWorkers][2];                    
    memset(pipeUp, 0, sizeof(pipeUp));                  
    for(int i = 0; i < arg.nWorkers; i++){      
        if(pipe(pipeUp[i]) == -1){                  
            perror("Pipe Up failed!\n");           
            exit(EXIT_FAILURE);                 
        }                                       
    } 

    // Cria filhos trabalhadores:
	returnValues result = cria_filhos(arg.nWorkers);
	
	// Tratamento erros fork:
	if(result.child == -1){
		perror("ERROR when creating children processes.\n");
		exit(EXIT_FAILURE);
	}
	
	// Variável usada pelos processos pai e filhos trabalhadores para guardar o nome do prefixo:
	char currentPrefix[20] = "";

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Código do filho trabalhador
	if(result.child > 0) {
		
		// Instancia variáveis a usar pelo filho trabalhador:
		childReport report;
		report.available = 0;  // APAGAR ESTA PARTE DA ESTRUTURA?!
		report.pid = getpid();
		report.qtyFilesMoved = 0;
		report.createdPath[0] = '\0';
		report.filesMoved[0] = '\0';
		
		close(pipeDown[result.child - 1][WRITE]); // fechar canal de escrita
		close(pipeUp[result.child - 1][READ]); // fechar canal de leitura

		while(1){
			
			// Ler prefixo do pipe e colocar a variável available da estrutura como "ocupado/válido" (1):
			read(pipeDown[result.child - 1][READ], currentPrefix, strlen(currentPrefix) + 1);
			report.available = 1;
			
			// Obter detalhe da "job reference" e do "e-mail do candidato":
			char jobReference[250];
			char jobApplicant[250];
			if(getApplicationDetails(currentPrefix, jobReference, jobApplicant) == -1){
				//printf("Failed to get application details from prefix: %s.\n", currentPrefix);
				report.available = 0;
			}
			
			// Criar diretório da job reference, se não falhar nenhuma tarefa anterior:
			char basePath[200];
			strcpy(basePath, arg.outputPath);
			strcat(basePath, "/");
			char jobReferencePath[200];
			if(report.available == 1) {
				strcpy(jobReferencePath, basePath);
				strcat(jobReferencePath, jobReference);
				if(createDirectory(jobReferencePath) == -1){
					printf("Failed to create job reference directory: %s.\n", jobReferencePath);
					report.available = 0;
				}
			}

			// Criar diretório da application, se não falhar nenhuma tarefa anterior:
			char jobApplicantPath[200];
			if(report.available == 1) {
				strcpy(jobApplicantPath, jobReferencePath);
				strcat(jobApplicantPath, "/");
				strcat(jobApplicantPath, jobApplicant);
				if(createDirectory(jobApplicantPath) == -1){
					printf("Failed to create job applicant directory: %s.\n", jobApplicantPath);
					report.available = 0;
				} else {
					strcpy(report.createdPath, jobApplicantPath);
				}
			}
			
			// Contar ficheiros disponíveis a mover, se não falhar nenhuma tarefa anterior:
			if(report.available == 1) {
				report.qtyFilesMoved = getFilesOnDirectory(arg.inputPath, currentPrefix, report.filesMoved);
				if(report.qtyFilesMoved <= 0){
					printf("No files to be moved.\n");
					report.available = 0;
				}
			}

			// Mover ficheiros para o diretório criado, se não falhar nenhuma tarefa anterior:
			if(report.available == 1) {
				if(moveFilesToDirectory(arg.inputPath, jobApplicantPath, currentPrefix) == -1){
					printf("Failed to move files to directory: %s.\n", jobApplicantPath);
					report.available = 0;
				};
			}

			// Escrever relatório para o pipe, enviando para o pai:
			write(pipeUp[result.child - 1][WRITE], &report, sizeof(report));

			// Limpa variáveis da estrutura:
			report.available = 0;
			report.qtyFilesMoved = 0;
			report.createdPath[0] = '\0';
			report.filesMoved[0] = '\0';

		}
		close(pipeDown[result.child - 1][READ]); // fechar canal de leitura
		close(pipeUp[result.child - 1][WRITE]); // fechar canal de escrita
		exit(EXIT_SUCCESS);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Continua código do processo principal (pai)

	// Preparar SIGINT
	struct sigaction act2;
	memset(&act2, 0, sizeof(struct sigaction));
	act2.sa_handler = sigIntHandler;
	sigaction(SIGINT, &act2, NULL);
	
	// Instancia variáveis a usar apenas pelo pai:
	int end = 0;
	int child = 0;
	int lastChild = arg.nWorkers;
	int qtyChildAvailable = lastChild;
	char* oldPrefixes;

	// Array de reports:
	childReport childReports[arg.nWorkers];
	for (int i = 0; i < lastChild; i++) {
    	childReports[i].available = 0;
    	childReports[i].qtyFilesMoved = 0; 
    	childReports[i].createdPath[0] = '\0';
    	childReports[i].filesMoved[0] = '\0';
	}

	// Matriz de gestão de tarefas atribuidas:
	int taskTable[arg.nWorkers][2];
	for (int i = 0; i < arg.nWorkers; i++) {
		taskTable[i][0] = (i+1);
		taskTable[i][1] = 0;
	}
	
	// Fecha partes dos pipes que não serão usadas:
	close(pipeDown[child][READ]); // fecha pipe DOWN de leitura
	close(pipeUp[child][WRITE]); // fecha pipe UP de escrita 

	while(1){

		// Inicia variáveis que serão usadas em cada ciclo:
		char* fileNames[50];
		int fileCount = 0;

		// Aguardar sinal do filho monitorizador
		pause();
		//("PAI: Recebi sinal do filho monitorizador.\n");
		
		// Repor o valor de end, contar os ficheiros que constam da pasta input, e guardar os nomes dos ficheiros que constam neste momento: 
		end = 0;
		fileCount = getDirFileNames(arg.inputPath, fileNames);
		//printf("filecount: %d\n", fileCount);
		if(fileCount == -1){
			perror("Error opening directory\n");
			end = 1;
		}
		oldPrefixes = malloc(sizeof(char) * fileCount);
		memset(oldPrefixes, 0, sizeof(char) * fileCount);

		
		// Ciclo para atribuir trabalho aos filhos:
		while(end == 0){

			// Encontrar um novo prefixo:
			end = findNewPrefix(fileNames, fileCount, currentPrefix, oldPrefixes);
			//printf("o valor de end é: %d\n", end);
			
			// Se for encontrado um novo prefixo, entregar trabalho ao filho trabalhador na fila:
			if(end == 0) {
				//printf("PAI: Hey filho %d, vou dar-te o prefixo %s.\n", child+1, currentPrefix);
				write(pipeDown[child][WRITE], currentPrefix, strlen(currentPrefix));
				taskTable[child][1]++;
				strcat(oldPrefixes, currentPrefix);

			// Se não for encontrado prefixo, fazer reset ao currentPrefix:
			} else {
				memset(currentPrefix, 0, sizeof(currentPrefix));
			}
			
			// Passar ao próximo filho. Se atingir o limite, voltar ao primeiro filho:
			child++;
			if(child == lastChild){
				child = 0;
			}
		}
		

		// Ciclo para ler dados para relatório, após trabalho concluído:
		//childReport report = childReports[child] // APAGAR?!
		int reportProcessed = 0;
		while(reportProcessed < fileCount) {
			//printf("\nPAI: Tenho %d relatórios para recolher\n\n", fileCount);
			if(taskTable[child][1] > 0) {
				//printf("PAI: O filho %d deverá enviar-me um relatório. Vou verificar.\n", child+1);
				read(pipeUp[child][READ], &childReports[child], sizeof(childReport));
				if(childReports[child].qtyFilesMoved > 0){
					//printf("PAI: O filho %d tem dados para o relatório. A tratar.\n", child+1);
					updateSessionFile(sessionFile, &childReports[child]);
				} else {
					//printf("PAI: O filho %d NÃO tem dados para o relatório.\n", child+1);
				}
				taskTable[child][1]--;
				reportProcessed++;
			}
			
			// Passar ao próximo filho. Se atingir o limite, voltar ao primeiro filho:
			child++;
			if(child == lastChild){
				child = 0;
			}
		}
	}

	// Libertar memória alocada e fechar os pipes:
	free(oldPrefixes);
	close(pipeDown[child][WRITE]);
	close(pipeUp[child][READ]);
	exit(EXIT_SUCCESS);
}
