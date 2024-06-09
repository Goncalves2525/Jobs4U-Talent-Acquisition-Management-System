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
#define WORKER_READ_MUTEX "/read_mutex"
#define WORKER_WRITE_MUTEX "/write_mutex"
#define MONITOR_MUTEX "/monitor_mutex"

#define SHARED_REPORT_DATA_SIZE sizeof(shared_report_data)
#define WAITING_TIME 1

volatile pid_t pid;
volatile sig_atomic_t nChildren;
char* oldPrefixes;
int fd;

sem_t *monitor_read_mutex;
//sem_t *monitor_write_mutex;
shared_data_type *shared_data;
sem_t *worker_write_mutex;
sem_t *worker_read_mutex;



shared_report_data  *shared_report;
sem_t *report_read_mutex;
sem_t *report_write_mutex;


/**
 *  
 * 
 */
void terminate_app(){
	
	//MONITOR DATA
	sem_close(monitor_read_mutex);
	shm_unlink(MONITOR_READ_MUTEX);
	//sem_close(monitor_write_mutex);
	//shm_unlink(MONITOR_WRITE_MUTEX);

	//PREFIXES DATA
	free(oldPrefixes);
	munmap(shared_data, DATA_SIZE);
	close(fd);
	shm_unlink(SHM_NAME);
	sem_close(worker_write_mutex);
	sem_close(worker_read_mutex);
	sem_unlink(WORKER_WRITE_MUTEX);
	sem_unlink(WORKER_READ_MUTEX);

	//REPORT DATA
	munmap(shared_report, SHARED_REPORT_DATA_SIZE);
	shm_unlink(SHARED_REPORT_NAME);
	sem_close(report_read_mutex);
	sem_close(report_write_mutex);
	sem_unlink(REPORT_READ_MUTEX);
	sem_unlink(REPORT_WRITE_MUTEX);
	exit(EXIT_SUCCESS);
}

void sigIntHandler(){
	waitpid(pid, NULL, 0);
	puts("\nReceice Kill");
	int i;
	for(i=0; i<nChildren; i++){
		wait(NULL);
		
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

    // // Prepara SIGUSR1:
	// struct sigaction act;                      
	// memset(&act, 0, sizeof(struct sigaction)); 
	// act.sa_handler = sigUsr1Handler;            
	// sigaction(SIGUSR1, &act, NULL);             

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
	monitor_read_mutex = sem_open(MONITOR_READ_MUTEX, O_CREAT, 0644,0 );
	//monitor_write_mutex = sem_open(MONITOR_WRITE_MUTEX, O_CREAT, 0644, 0);
    //-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    // Código filho que monitoriza:
    if(pid == 0){
        monitor_files(arg.inputPath, arg.timeInterval);
        exit(EXIT_SUCCESS);
    }

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
    shared_data->prefixo[0] ='\0';
	
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

	//--->SEMAPHORES WORKER E PAI
	worker_write_mutex = sem_open(WORKER_WRITE_MUTEX, O_CREAT, 0644, 1);
	worker_read_mutex = sem_open(WORKER_READ_MUTEX, O_CREAT, 0644, 0);
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

	

	//WORKERS
	if(result.child > 0) {
			char currentPrefix[20] = "";
			int available;
			int qtyFilesMoved ;
			char filesMoved[500];
			char createdPath[100];

		while(1){
			//Inincia as variaveis
			available = 0;
			qtyFilesMoved = 0;
			filesMoved[0] = '\0';
			createdPath[0] = '\0';
			currentPrefix[0] = '\0';
			sem_wait(worker_read_mutex);
			strcpy(currentPrefix, shared_data->prefixo);

			//printf("WORKER %d: Recebi o Buffer |%s| Prefixo |%s|\n", result.child,  shared_data->buffer, shared_data->prefixo);
			if(strcmp(currentPrefix, "") != 0 && currentPrefix[0] != '\0' && isNumeric(currentPrefix) == 1){
				printf("WORKER %d: A processar Prefixo: |%s|\n", result.child,  currentPrefix);
				// for (int i = 0; i < 20; i++) {
				// 	// Verifica se o caractere é nulo para evitar imprimir valores não inicializados
				// 	if (currentPrefix[i] == '\0') {
				// 		break;
				// 	}
				// 	printf("WORKER %d: - currentPrefix[%d]: %c -> %d \n", result.child, i, currentPrefix[i], currentPrefix[i]);
				// }
				// shared_data->buffer[0] = '\0'; 
				// strncpy(shared_data->buffer, '\0', BUFFER_SIZE );
				available = 1;
			}
			
			sem_post(worker_write_mutex);
			
			// Obter detalhe da "job reference" e do "e-mail do candidato":
			char jobReference[250];
			char jobApplicant[250];
			
			if(available == 1 && getApplicationDetails(currentPrefix, jobReference, jobApplicant) == -1){
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
			sem_wait(report_write_mutex);
			//----> ESCREVER DADOS DO RELATÓRIO NA MEMÓRIA PARTILHADA-----------------------
			if(available == 1) {
				//printf("WORKER: VOU ESCREVER RELATORIO do PREFIXO %s\n", currentPrefix); //Remover no Final
				//printf("WORKER:  ESCRITO RELATORIO do PREFIXO %s\n", currentPrefix);
				shared_report->qtyFilesMoved = qtyFilesMoved;
				shared_report->pid = getpid();
				strcpy(shared_report->createdPath, jobApplicantPath);
				strcpy(shared_report->filesMoved, filesMoved);
				
			}
			else{
				shared_report->qtyFilesMoved = 0;
			}
			sem_post(report_read_mutex);
			

		}
		exit(EXIT_SUCCESS);
	}


	// Continua código do processo principal (pai)


	// Preparar SIGINT
	struct sigaction act2;
	memset(&act2, 0, sizeof(struct sigaction));
	act2.sa_handler = sigIntHandler;
	sigaction(SIGINT, &act2, NULL);


	char currentPrefix[20] = "";
	//sem_post(monitor_write_mutex);
	while(1){
		

		//printf("DISTRIBUIDOR: A espera de Novos Ficheiros\n");
		
		//printf("DISTRIBUIDOR: A espera de Post para o Monitor\n");
		sem_wait(monitor_read_mutex);
		char* fileNames[50];
		int fileCount = 0;
		int fileToProcess = 0;
		int runningWorkers = 0;
		int end = 0;
		fileCount = getDirFileNames(arg.inputPath, fileNames);
		//printf("DISTRIBUIDOR: Recebi sinal do filho monitorizador. %d\n", fileCount);
		if(fileCount == 0){
			//perror("Error opening directory\n");
			end = 1;
		}
		//char* oldPrefixes;
		oldPrefixes = malloc(sizeof(char) * fileCount);
		//
		if(oldPrefixes == NULL){
			perror("Error allocating oldPrefixes memory\n");
			fileCount = -1;
		}else{
			memset(oldPrefixes, '\0', sizeof(char) * fileCount);
		}
		fileToProcess = fileCount;
		while (fileToProcess > 0)
		{
			int to = fileCount < arg.nWorkers ? fileCount : arg.nWorkers;
			//printf("DISTRIBUIDOR: fileToProcess %d,to %d,  nWorkers %d, runningWorkers %d \n", fileToProcess, to, arg.nWorkers, runningWorkers);
			
			for (int i = 0; i < to ; i++)
			{
				if (runningWorkers >=  arg.nWorkers)
				{
					printf("DISTRIBUIDOR: Nenhum Worker disponivel\n");
					break;
				}
				
				//Atribuir tarefas aos WORKERS
				end = findNewPrefix(fileNames, fileCount, currentPrefix, oldPrefixes);
				if(end !=0){
					// Se não for encontrado prefixo, fazer reset ao currentPrefix:
					memset(currentPrefix, '\0', sizeof(currentPrefix));
				}else{
					
					//printf("DISTRIBUIDOR: A Atribuir o Prefixo ao primeiro a responder %s\n", currentPrefix);
					//Distribuir pelo workers
					sem_wait(worker_write_mutex);
					strncpy(shared_data->buffer, currentPrefix, BUFFER_SIZE );
					strcpy(shared_data->prefixo, currentPrefix );
					//printf("DISTRIBUIDOR: A Processar o Prefixo %s - Buffer %s\n", currentPrefix, shared_data->buffer);
					sem_post(worker_read_mutex);
					runningWorkers++;
					strcat(oldPrefixes, currentPrefix);
					
				}
			}
			childReport report;
			int updateSession = 0; //indicador que a operação correu com sucesso como tal deve atualizar o report
			sem_wait(report_read_mutex);
			//Se worker indicar que moveu ficheiros a operação correu corretamente
			if(shared_report->qtyFilesMoved > 0){
				updateSession = 1;
				report.pid = shared_report->pid;
				report.qtyFilesMoved = shared_report->qtyFilesMoved;
				strcpy(report.createdPath, shared_report->createdPath );
				strcpy(report.filesMoved, shared_report->filesMoved);
				//Limpar dados da memoria partilhada
				shared_report->qtyFilesMoved = 0;
				shared_report->pid =0;
				shared_report->createdPath[0] = '\0';
			}
			sem_post(report_write_mutex);
			if(updateSession == 1){
				updateSessionFile(sessionFile, &report);
			}
			fileToProcess--;
			runningWorkers--;
		}
		
		//free(oldPrefixes);
		//sem_post(monitor_write_mutex);
	}
	terminate_app();
}
