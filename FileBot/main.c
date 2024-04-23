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
	printf("Killed monitor child\n"); //APAGAR

	int i;
	for(i=0; i<nChildren; i++){
		wait(NULL);
		printf("Killed worker child %d\n", i+1); //APAGAR
	}

	exit(EXIT_SUCCESS);
}


int main(int argc, char *argv[]) {

	arglocal arg;

    // Usar argumentos do ficheiro de configuração ou do command prompt: arguments arglocal;
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

	//variable to use in SIGINT handler
	nChildren = arg.nWorkers;
    
    // Validar se tem todos os argumentos:
    if(validateAllArgumentsAvailable(&arg) == -1){
        return -1; // terminar com erro
    };
    
    // cria o diretorio output, caso não exista
	createDirectory(arg.outputPath);
	
	// cria o diretorio report, caso não exista
	char reportInOutput[100];
	strcpy(reportInOutput, arg.outputPath);
	strcat(reportInOutput, "/");
	strcat(reportInOutput, arg.reportPath);
	createDirectory(reportInOutput);
	
	// cria o ficheiro report, caso não exista
	char sessionFile[200];
	strcpy(sessionFile, reportInOutput);
	strcat(sessionFile, "/reportSession_");
	time_t currentTime = time(NULL);
	char timeString[20];
    snprintf(timeString, sizeof(timeString), "%ld", currentTime);
	strcat(sessionFile, timeString);
	strcat(sessionFile, ".txt");
	createSessionFile(sessionFile);

    // Preparar SIGUS1
	struct sigaction act;                      
	memset(&act, 0, sizeof(struct sigaction)); 
	act.sa_handler = sigUsr1Handler;            
	sigaction(SIGUSR1, &act, NULL);             

    // Cria filho que monitoriza
    pid = fork();
    
    // Tratamento erros fork
    if(pid == -1){
        perror("Fork ERROR.\n");
        exit(EXIT_FAILURE);
    }
    
    // Código filho que monitoriza
    if(pid == 0){
        monitor_files(arg.inputPath, arg.timeInterval);
        exit(EXIT_SUCCESS);
    }

	// Continua código do processo principal (pai)
    
    // Preparar os PIPES
    enum extremidade {READ=0, WRITE=1};         
    int pipeDown[arg.nWorkers][2];                    
    memset(pipeDown, 0, sizeof(pipeDown));                  
    for(int i = 0; i < arg.nWorkers; i++){      
        if(pipe(pipeDown[i]) == -1){                  
            perror("Pipe Down failed!\n");           
            exit(EXIT_FAILURE);                 
        }                                       
    }    

	int pipeUp[arg.nWorkers][2];                    
    memset(pipeUp, 0, sizeof(pipeUp));                  
    for(int i = 0; i < arg.nWorkers; i++){      
        if(pipe(pipeUp[i]) == -1){                  
            perror("Pipe Up failed!\n");           
            exit(EXIT_FAILURE);                 
        }                                       
    } 

    // Cria filhos que trabalham
	returnValues result = cria_filhos(arg.nWorkers);
	
	// Tratamento erros fork
	if(result.child == -1){
		perror("ERROR when creating children processes.\n");
		exit(EXIT_FAILURE);
	}
	
	char currentPrefix[20] = "";
	
	// Código do filho trabalhador
	if(result.child > 0) {
		
		// instancia variáveis a usar pelo filho trabalhador
		childReport report;
		report.available = 0;
		report.qtyFilesMoved = 0;
		report.createdPath[0] = '\0';
		report.filesMoved[0] = '\0';
		
		close(pipeDown[result.child - 1][WRITE]); // fechar canal de escrita
		close(pipeUp[result.child - 1][READ]); // fechar canal de leitura

		while(1){
			//avisar o pai que está disponivel
			report.available = 0;
			write(pipeUp[result.child - 1][WRITE], &report, sizeof(report));
			printf("FILHO %d - Informei o pai que estou disponível.\n", result.child); // TODO: apagar depois de testes
			
			// ler o prefixo do pipe
			read(pipeDown[result.child - 1][READ], currentPrefix, strlen(currentPrefix) + 1);
			
			//avisar o pai que está ocupado
			report.available = 1;
			report.qtyFilesMoved = 0;
			report.createdPath[0] = '\0';
			report.filesMoved[0] = '\0';
			write(pipeUp[result.child - 1][WRITE], &report, sizeof(report));
			printf("FILHO %d - Informei o pai que estou ocupado com o prefixo %s.\n", result.child, currentPrefix); // TODO: apagar depois de testes
			
			// Obter detalhe da "job reference" e do "e-mail do candidato":
			char jobReference[250];
			char jobApplicant[250];
			if(getApplicationDetails(currentPrefix, jobReference, jobApplicant) == -1){
				printf("Failed to get application details from prefix: %s.\n", currentPrefix); // TODO: apagar depois de testes
				report.available = 0;
			}
			
			char basePath[200];
			strcpy(basePath, arg.outputPath);
			strcat(basePath, "/");
			char jobReferencePath[200];
			if(report.available == 1) {
				// Criar diretório da job reference:
				strcpy(jobReferencePath, basePath);
				strcat(jobReferencePath, jobReference);
				if(createDirectory(jobReferencePath) == -1){
					printf("Failed to create job reference directory: %s.\n", jobReferencePath);
					report.available = 0;
				}
			}

			char jobApplicantPath[200];
			if(report.available == 1) {
				// Criar diretório da application:
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
			
			if(report.available == 1) {
				// Contar ficheiros disponíveis a mover
				report.qtyFilesMoved = getFilesOnDirectory(arg.inputPath, currentPrefix, report.filesMoved);
				if(report.qtyFilesMoved <= 0){
					printf("No files to be moved.\n");
					report.available = 0;
				}
			}

			if(report.available == 1) {
				// Mover ficheiros para o diretório:
				if(moveFilesToDirectory(arg.inputPath, jobApplicantPath, currentPrefix) == -1){
					printf("Failed to move files to directory: %s.\n", jobApplicantPath);
					report.available = 0;
				};
			}
		}
		close(pipeDown[result.child - 1][READ]); // fechar canal de leitura
		close(pipeUp[result.child - 1][WRITE]); // fechar canal de escrita
		exit(EXIT_SUCCESS);
	}
	
	// Continua código do processo principal (pai)

	// Preparar SIGINT
	struct sigaction act2;
	memset(&act2, 0, sizeof(struct sigaction));
	act2.sa_handler = sigIntHandler;
	sigaction(SIGINT, &act2, NULL);
	
	// instancia variáveis a usar apenas pelo pai
	int end = 0;
	int child = 0;
	int lastChild = arg.nWorkers;
	int qtyChildAvailable = 0;
	char* oldPrefixes;
	childReport childReports[lastChild];
	for (int i = 0; i < lastChild; i++) {
    	childReports[i].available = 0;
    	childReports[i].qtyFilesMoved = 0; 
    	childReports[i].createdPath[0] = '\0';
    	childReports[i].filesMoved[0] = '\0';
	}
	
	// fecha partes dos pipes que não serão usados
	close(pipeDown[child][READ]);
	close(pipeUp[child][WRITE]);

	while(1){
		char* fileNames[50];
		int fileCount = 0;
		pause(); //aguardar sinal do filho monitorizador
		printf("PAI: Recebi sinal do filho monitorizador.\n"); // TODO: apagar depois de testes
		end = 0; // repor o valor de end após sinal do filho monitorizador 
		fileCount = getDirFileNames(arg.inputPath, fileNames);
		printf("filecount: %d\n", fileCount); // TODO: apagar depois de testes
		if(fileCount == -1){
			perror("Error opening directory\n");
			exit(EXIT_FAILURE);
		}

		oldPrefixes = malloc(sizeof(char) * fileCount);
		memset(oldPrefixes, 0, sizeof(oldPrefixes));
		while(end == 0){
			childReport report = childReports[child];
			end = findNewPrefix(fileNames, fileCount, currentPrefix, oldPrefixes);
			printf("o valor de end é: %d\n", end); // TODO: apagar depois de testes
			if(end == 0){
				//verificar se o filho está disponivel
				printf("PAI: Estou a verificar se o filho %d está disponível.\n", child+1); // TODO: apagar depois de testes
				read(pipeUp[child][READ], &report, sizeof(childReport));
				childReports[child] = report;
				if(report.available == 0){
					printf("PAI: O filho %d disse que está disponível.\n", child+1); // TODO: apagar depois de testes
					qtyChildAvailable++;
					if(report.qtyFilesMoved > 0){
						printf("PAI: O filho %d tem dados para o relatório. A tratar.\n", child+1); // TODO: apagar depois de testes
						updateSessionFile(sessionFile, &report);
					}
					printf("PAI: Hey filho %d, vou dar-te o prefixo %s.\n", child+1, currentPrefix); // TODO: apagar depois de testes
					write(pipeDown[child][WRITE], currentPrefix, strlen(currentPrefix));
					qtyChildAvailable--;
					strcat(oldPrefixes, currentPrefix);
				}else{
					//empty currentPrefix
					memset(currentPrefix, 0, sizeof(currentPrefix));
				}
			}
			
			child++;
			if(child == lastChild){
				child = 0;
			}
		}
		
		//verfifcar se todos os relatórios foram tratados
		/** int i;
		for(i = 0; i < lastChild; i++){
			if(childReports[i].qtyFilesMoved == 0){
				printf("Vou verificar se o filho %d tem dados para o relatório.\n", i+1); // TODO: apagar depois de testes
				read(pipeUp[i][READ], &childReports[i], sizeof(childReport));
				if(report.qtyFilesMoved > 0){
					printf("PAI: O filho %d tem dados para o relatório. A tratar.\n", child+1); // TODO: apagar depois de testes
					updateSessionFile(sessionFile, &report);
				}
			}
		} */
		
		printf("\nTenho %d filhos disponiveis\n\n", qtyChildAvailable); // TODO: apagar depois de testes
			
		while(qtyChildAvailable < arg.nWorkers) {
			printf("Vou verificar se o filho %d tem dados para o relatório.\n", child+1); // TODO: apagar depois de testes
			read(pipeUp[child][READ], &childReports[child], sizeof(childReport));
			if(childReports[child].available == 0 && childReports[child].qtyFilesMoved > 0){
				printf("PAI: O filho %d tem dados para o relatório. A tratar.\n", child+1); // TODO: apagar depois de testes
				updateSessionFile(sessionFile, &childReports[child]);
				qtyChildAvailable++;
			}
			child++;
			if(child == lastChild){
				child = 0;
			}
		}
	}
	free(oldPrefixes);
	close(pipeDown[child][WRITE]);
	close(pipeUp[child][READ]);
	exit(EXIT_SUCCESS);
	
	//PROCESSO MONITORIZADOR: REVER FORMA PARA VERIFICAR DIFERENÇAS NA PASTA INPUT, PARA SÓ ENVIAR SINAL QUANDO EXISTE EFETIVAMENTE ALGO NOVO
	//PROCESSO PAI: REVER FORMA DE COMO TIRA A FOTOGRAFIA À PASTA INPUT, DEPOIS DO SINAL DO FILHO MONITORIZADOR. USAR MESMA TÉCNICA QUE SERÁ USADA PELO FILHO, PARA VALIDAR ELEMENTOS NOVOS?!
}
