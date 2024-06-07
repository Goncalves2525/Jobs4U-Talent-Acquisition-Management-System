#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <semaphore.h>
#include <sys/mman.h>

#include "functions.h"

#define CONFIGFILE "configs.txt"
#define DATA_SIZE sizeof(shared_data_type)
#define SHARED_REPORT_NAME "/shared_report"
#define REPORT_READ_MUTEX "/report_read_mutex"
#define REPORT_WRITE_MUTEX "/report_write_mutex"
#define SHARED_REPORT_DATA_SIZE sizeof(shared_report_data)
#define WAITING_TIME 1

volatile pid_t pid;
volatile sig_atomic_t nChildren;
char* oldPrefixes;
int fd;

sem_t *monitor_sem;

shared_data_type *shared_data;
sem_t *write_mutex;
sem_t *read_mutex;



shared_report_data  *shared_report;
sem_t *report_read_mutex;
sem_t *report_write_mutex;


/**
 *  
 * 
 */
void terminate_app(){
	//MONITOR DATA
	sem_close(monitor_sem);
	shm_unlink(monitor_sem);

	//PREFIXES DATA
	free(oldPrefixes);
	munmap(shared_data, DATA_SIZE);
	close(fd);
	shm_unlink(SHM_NAME);
	sem_close(write_mutex);
	sem_close(read_mutex);
	sem_unlink("/write_mutex");
	sem_unlink("/read_mutex");

	//REPORT DATA
	munmap(shared_report, SHARED_REPORT_DATA_SIZE);
	shm_unlink(SHARED_REPORT_NAME);
	sem_close(report_read_mutex);
	sem_close(report_write_mutex);
	sem_unlink(REPORT_READ_MUTEX);
	sem_unlink(REPORT_WRITE_MUTEX);
	exit(EXIT_SUCCESS);
}

void sigIntHandler(int signal){
	//kill(pid, SIGTERM);
	waitpid(pid, NULL, 0);
	//printf("Killed monitor child\n");

	int i;
	for(i=0; i<nChildren; i++){
		wait(NULL);
		//printf("Killed worker child %d\n", i+1);
	}
	terminate_app();
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

	//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	//US201b:
	//--->SEMÁFORO PARA MONITORIZAÇÃO
	monitor_sem = sem_open("/monitor_sem", O_CREAT, 0644, 0);
    //-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    // Código filho que monitoriza:
    if(pid == 0){
        monitor_files(arg.inputPath, arg.timeInterval);
        exit(EXIT_SUCCESS);
    }

	// Continua código do processo principal (pai)

	//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	//US2001:
	/*
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
	*/
	//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	//US2001b:
	//--->SHARED MEMORY
	fd = shm_open(SHM_NAME, O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
    if (fd == -1) {
        perror("shm_open failed\n");
        exit(EXIT_FAILURE);
    }
    ftruncate(fd, DATA_SIZE);
    if (fd == -1) {
        perror("ftruncate failed\n");
        exit(EXIT_FAILURE);
    }
    shared_data = (shared_data_type *)mmap(NULL, DATA_SIZE, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (shared_data == MAP_FAILED) {
        perror("mmap failed\n");
        exit(EXIT_FAILURE);
    }
	memset(shared_data, 0, sizeof(shared_data_type));
    
	//--->REPORT SHARED MEMORY
	int status = shm_open(SHARED_REPORT_NAME, O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
	if (status == -1) {
        perror("shm_open failed\n");
        exit(EXIT_FAILURE);
    }
    ftruncate(status, SHARED_REPORT_DATA_SIZE);
    if (status == -1) {
        perror("ftruncate failed\n");
        exit(EXIT_FAILURE);
    }
    shared_report = (shared_report_data*)mmap(NULL, SHARED_REPORT_DATA_SIZE, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (shared_report == MAP_FAILED) {
        perror("mmap failed\n");
        exit(EXIT_FAILURE);
    }

	memset(shared_report, 0, sizeof(shared_report_data));
	//default data
	shared_report->qtyFilesMoved = 0;
	shared_report->pid =0;
	shared_report->createdPath[0] = '\0';

	//--->SEMAPHORES
	write_mutex = sem_open("/write_mutex", O_CREAT, 0644, 1);
	read_mutex = sem_open("/read_mutex", O_CREAT, 0644, 0);
	report_read_mutex = sem_open(REPORT_READ_MUTEX, O_CREAT, 0644, 1);
	report_write_mutex = sem_open(REPORT_WRITE_MUTEX, O_CREAT, 0644, 0);
	//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


	
    
    

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
		//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
		//US2001:
		/*
		// Instancia variáveis a usar pelo filho trabalhador:
		childReport report;
		report.available = 0;  // APAGAR ESTA PARTE DA ESTRUTURA?!
		report.pid = getpid();
		report.qtyFilesMoved = 0;
		report.createdPath[0] = '\0';
		report.filesMoved[0] = '\0';
		
		close(pipeDown[result.child - 1][WRITE]); // fechar canal de escrita
		close(pipeUp[result.child - 1][READ]); // fechar canal de leitura
		*/
		//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

		while(1){
			//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
			//US2001:
			// Ler prefixo do pipe e colocar a variável available da estrutura como "ocupado/válido" (1):
			/*
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
			*/
			//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
			
			//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
			//US2001b:
			// Instancia variáveis a usar pelo filho trabalhador:
			int available;
			int qtyFilesMoved ;
			char filesMoved[500];

			char createdPath[100];
			//Inincia as variaveis
			available = 0;
			qtyFilesMoved = 0;
			filesMoved[0] = '\0';
			createdPath[0] = '\0';
			//printf("LER PREFIXO"); //Remover no Final
			//----> Ler prefixo da memória
			sem_wait(read_mutex);
			strncpy(currentPrefix, shared_data->buffer, BUFFER_SIZE - 1);
			
			if(!(strcmp(currentPrefix, "") == 0 || currentPrefix[0] == '\0')){
				printf("WORKER %d: Recebi prefixo %s\n", result.child, currentPrefix);
				shared_data->buffer[0] = '\0'; //evitar que outro filho leia este prefixo
				currentPrefix[BUFFER_SIZE - 1] = '\0';
			}

			sem_post(write_mutex);
			//printf("LIBERTAR MUTEX"); //Remover no Final
			if(strcmp(currentPrefix, "") == 0 || currentPrefix[0] == '\0'){
				continue;
			}
			available = 1;

			// Obter detalhe da "job reference" e do "e-mail do candidato":
			char jobReference[250];
			char jobApplicant[250];
			if(getApplicationDetails(currentPrefix, jobReference, jobApplicant) == -1){
				printf("Failed to get application details from prefix: %s.\n", currentPrefix); // TODO: apagar depois de testes
				available = 0;
			}
			
			// Criar diretório da job reference, se não falhar nenhuma tarefa anterior:
			char basePath[200];
			strcpy(basePath, arg.outputPath);
			strcat(basePath, "/");
			char jobReferencePath[200];
			if(available == 1) {
				strcpy(jobReferencePath, basePath);
				strcat(jobReferencePath, jobReference);
				if(createDirectory(jobReferencePath) == -1){
					printf("Failed to create job reference directory: %s.\n", jobReferencePath);
					available = 0;
				}
			}

			// Criar diretório da application, se não falhar nenhuma tarefa anterior:
			char jobApplicantPath[200];
			if(available == 1) {
				strcpy(jobApplicantPath, jobReferencePath);
				strcat(jobApplicantPath, "/");
				strcat(jobApplicantPath, jobApplicant);
				if(createDirectory(jobApplicantPath) == -1){
					printf("Failed to create job applicant directory: %s.\n", jobApplicantPath);
					available = 0;
				} else {
					strcpy(createdPath, jobApplicantPath);
				}
			}
			
			// Contar ficheiros disponíveis a mover, se não falhar nenhuma tarefa anterior:
			if(available == 1) {
				qtyFilesMoved = getFilesOnDirectory(arg.inputPath, currentPrefix, filesMoved);
				if(qtyFilesMoved <= 0){
					printf("No files to be moved.\n");
					available = 0;
				}
			}

			// Mover ficheiros para o diretório criado, se não falhar nenhuma tarefa anterior:
			if(available == 1) {
				if(moveFilesToDirectory(arg.inputPath, jobApplicantPath, currentPrefix) == -1){
					printf("Failed to move files to directory: %s.\n", jobApplicantPath);
					available = 0;
				};
			}

			//----> ESCREVER DADOS DO RELATÓRIO NA MEMÓRIA PARTILHADA-----------------------
			//if(available == 1) {
				//printf("WORKER: VOU ESCREVER RELATORIO do PREFIXO %s\n", currentPrefix); //Remover no Final
				
				sem_wait(report_write_mutex);
				//printf("WORKER:  ESCRITO RELATORIO do PREFIXO %s\n", currentPrefix);
				shared_report->qtyFilesMoved = qtyFilesMoved;
				shared_report->pid = getpid();
				strcpy(shared_report->createdPath, jobApplicantPath);
				strcpy(shared_report->filesMoved, filesMoved);
				sem_post(report_read_mutex);
			// }
			// else{
			// 	//printf("WORKER: PREFIXO %s SEM RELATORIO\n", currentPrefix); //Remover no Final

			// }

			//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
			
		}
		//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
		//US2001:
		/*
		close(pipeDown[result.child - 1][READ]); // fechar canal de leitura
		close(pipeUp[result.child - 1][WRITE]); // fechar canal de escrita
		*/
		//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

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
	struct timespec ts;// Hora
	
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
	//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	//US2001:
	/*
	// Fecha partes dos pipes que não serão usadas:
	close(pipeDown[child][READ]); // fecha pipe DOWN de leitura
	close(pipeUp[child][WRITE]); // fecha pipe UP de escrita 
	*/
	//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	while(1){

		// Inicia variáveis que serão usadas em cada ciclo:
		char* fileNames[50];
		int fileCount = 0;
		//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
		//US2001:
		// Aguardar sinal do filho monitorizador
		//pause();
		//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
		//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
		//US2001b:
		//US2001b Aguardar POST do filho monitorizador

		// Obter o tempo atual
		clock_gettime(CLOCK_REALTIME, &ts);
		// Adicionar segundos ao tempo atual
		ts.tv_sec += WAITING_TIME;

		//sem_wait(monitor_sem);
		if(sem_timedwait(monitor_sem, &ts) != -1){
			//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

			printf("PAI: Recebi sinal do filho monitorizador.\n");
			
			// Repor o valor de end, contar os ficheiros que constam da pasta input, e guardar os nomes dos ficheiros que constam neste momento: 
			end = 0;
			fileCount = getDirFileNames(arg.inputPath, fileNames);
			//printf("filecount: %d\n", fileCount);
			if(fileCount == -1){
				perror("Error opening directory\n");
				end = 1;
			}
			oldPrefixes = malloc(sizeof(char) * fileCount);
			//memset(oldPrefixes, 0, sizeof(char) * fileCount);
			if(oldPrefixes == NULL){
				perror("Error allocating oldPrefixes memory\n");
				end = 1;
			}else{
				memset(oldPrefixes, 0, sizeof(char) * fileCount);
			}
		}
			// Ciclo para atribuir trabalho aos filhos:
			while(end == 0){

				// Encontrar um novo prefixo:
				end = findNewPrefix(fileNames, fileCount, currentPrefix, oldPrefixes);
				//printf("o valor de end é: %d\n", end);
				
				// Se for encontrado um novo prefixo, entregar trabalho ao filho trabalhador na fila:
				if(end == 0) {
					//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
					//US2001:
					//printf("PAI: Hey filho %d, vou dar-te o prefixo %s.\n", child+1, currentPrefix);
					/*
					write(pipeDown[child][WRITE], currentPrefix, strlen(currentPrefix));
					taskTable[child][1]++;
					strcat(oldPrefixes, currentPrefix);
					*/
					//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
					
					
					//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
					//US2001b:
					//----->Escrever prefixo na memória
					sem_wait(write_mutex);

					strncpy(shared_data->buffer, currentPrefix, BUFFER_SIZE - 1);
					shared_data->buffer[BUFFER_SIZE - 1] = '\0';
					printf("ASSIGN WORKER: Enviei prefixo %s.\n", currentPrefix); 
					strcat(oldPrefixes, currentPrefix);

					sem_post(read_mutex);
					
					//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


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
		//while(reportProcessed < fileCount) {
		//	printf("\nPAI: LOOP WHILE\n");
			//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
			//US2001:
			//printf("\nPAI: Tenho %d relatórios para recolher\n\n", fileCount);
			/*
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
			*/
			//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
			//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
			//US2001b:
		
			childReport report;
				report.filesMoved[0] = '\0';
				//printf("PAI: À espera da memória partilhada\n", fileCount); // TODO: apagar depois de testes
				// Obter o tempo atual
				clock_gettime(CLOCK_REALTIME, &ts);
				// Adicionar segundos ao tempo atual
				ts.tv_sec += WAITING_TIME;
				if(sem_timedwait(report_read_mutex, &ts) != -1){
					//sem_wait(report_read_mutex);
					//printf("PAI: Ler memória partilhada\n", fileCount); // TODO: apagar depois de testes
					report.pid = shared_report->pid;
					report.qtyFilesMoved = shared_report->qtyFilesMoved;
					strcpy(report.createdPath, shared_report->createdPath );
					strcpy(report.filesMoved, shared_report->filesMoved);
					shared_report->qtyFilesMoved = 0;
					shared_report->pid =0;
					shared_report->createdPath[0] = '\0';
					sem_post(report_write_mutex);
					//if(childReports[child].qtyFilesMoved > 0){
						//printf("PAI: O filho %d tem dados para o relatório. A tratar.\n", report.pid);// TODO: apagar depois de testes
						updateSessionFile(sessionFile, &childReports[child]);
					//} else {
					//	printf("PAI: O filho %d NÃO tem dados para o relatório.\n", child+1);
					//}
					//taskTable[child][1]--;
					//printf("PAI: Relatório Processado\n", fileCount);// TODO: apagar depois de testes

					reportProcessed++;
				}
				else{
					//printf("PAI: TIMEOUT À espera da memória partilhada\n", fileCount);
				}
			//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

			
			// Passar ao próximo filho. Se atingir o limite, voltar ao primeiro filho:
			child++;
			if(child == lastChild){
				child = 0;
			}
		//}
	}

	// Libertar memória alocada e fechar os pipes:
	//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	//US2001b:
	/*
	free(oldPrefixes);
	close(pipeDown[child][WRITE]);
	close(pipeUp[child][READ]);
	exit(EXIT_SUCCESS);
	*/
	//-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	terminate_app();
}
