#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include "functions.h"

#define CONFIGFILE "configs.txt"

int main(int argc, char *argv[]){

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
    
    // Validar se tem todos os argumentos:
    if(validateAllArgumentsAvailable(&arg) == -1){
        return -1; // terminar com erro
    };

    // Preparar o SINAL
	struct sigaction act;                       //  |- PREPARA O SINAL
	memset(&act, 0, sizeof(struct sigaction));  //  |
	act.sa_handler = sigUsr1Handler;            //  |
	sigaction(SIGUSR1, &act, NULL);             //  |
    
    // Cria filho que monitoriza
    pid_t pid;
    pid = fork();
    
    // Tratamento erros fork
    if(pid == -1){
        perror("Fork ERROR.\n");
        exit(EXIT_FAILURE);
    }
    
    // Código filho que monitoriza
    if(pid == 0){
        monitor_files(arg.inputPath);
        exit(EXIT_SUCCESS);
    }
    
	// Continua código do processo principal (pai)
    
    // Preparar os PIPES
    enum extremidade {READ=0, WRITE=1};         //  |- CRIA OS PIPES
    int fd[arg.nWorkers][2];                    //  |
    memset(fd, 0, sizeof(fd));                  //  |
    for(int i = 0; i < arg.nWorkers; i++){      //  |
        if(pipe(fd[i]) == -1){                  //  |
            perror("Pipe failed!\n");           //  |
            exit(EXIT_FAILURE);                 //  |
        }                                       //  |
    }                                           //  |
    
    // Cria filhos que trabalham
	int result = cria_filhos(arg.nWorkers);
	
	// Tratamento erros fork
	if(result == -1){
		perror("ERROR when creating children processes.\n");
		exit(EXIT_FAILURE);
	}
	
	char currentPrefix[20] = "";
	
	// Código do filho trabalhador
	if(result > 0) {
		close(fd[result][WRITE]); // fechar canal de escrita
		sleep(10); // [TEMPORÁRIO] apagar mais tarde!
		while(1){
			int available = 0; // sinal para identificar o estado do filho (0=disponivel; -1=indisponível)
			// [TODO:] (enviar sinal a informar o pai de que está disponível para trabalhar)
			read(fd[result][READ], currentPrefix, strlen(currentPrefix) + 1); // ler o prefixo do pipe

			// Obter detalhe da "job reference" e do "e-mail do candidato":
			char jobReference[250];
			char jobApplicant[250];
			if(getApplicationDetails(currentPrefix, jobReference, jobApplicant) == -1){
				printf("Failed to get application details from prefix: %s.\n", currentPrefix);
				available = -1;
			}
			
			char basePath[] = "./input/";
			char jobReferencePath[200];
			if(available == 0) {
				// Criar diretório da job reference:
				strcpy(jobReferencePath, basePath);
				strcat(jobReferencePath, jobReference);
				if(createDirectory(jobReferencePath) == -1){
					printf("Failed to create job reference directory: %s.\n", jobReferencePath);
					available = -1;
				}
			}

			char jobApplicantPath[200];
			if(available == 0) {
				// Criar diretório da application:
				strcpy(jobApplicantPath, jobReferencePath);
				strcat(jobApplicantPath, "/");
				strcat(jobApplicantPath, jobApplicant);
				if(createDirectory(jobApplicantPath) == -1){
					printf("Failed to create job applicant directory: %s.\n", jobApplicantPath);
					available = -1;
				}
			}

			if(available == 0) {
				// Mover ficheiros para o diretório:
				if(moveFilesToDirectory(arg.inputPath, jobApplicantPath, currentPrefix) == -1){
					printf("Failed to move files to directory: %s.\n", jobApplicantPath);
					available = -1;
				};
			}
		}
		close(fd[result][READ]); // fechar canal de leitura
		exit(EXIT_SUCCESS);
	}
	
	// Continua código do processo principal (pai)
	char* fileName = "candidate-data.txt";
	int end = 0;
	int child = 0;
	int lastChild = arg.nWorkers;

	while(1){
		char* fileNames[50];
		int fileCount = 0;
		pause(); //aguardar sinal do filho
		fileCount = getDirFileNames(arg.inputPath, fileNames);
		if(fileCount == -1){
			perror("Error opening directory\n");
			exit(EXIT_FAILURE);
		}

		while(end == 0){
			end = findNewPrefix(fileNames, fileCount, currentPrefix);
			if(end == 0){
				close(fd[child][READ]);
				write(fd[child][WRITE], currentPrefix, strlen(currentPrefix));

				child++;
				if(child == lastChild){
					child = 0;
				}
			}
		}
	}
	close(fd[child][WRITE]);
	exit(EXIT_SUCCESS);
	
	//RICARDO: FALTA-ME MATAR OS FILHOS
	//CAROLO: FALTA CRIAR/ATUALIZAR RELATÓRIO?!
}
