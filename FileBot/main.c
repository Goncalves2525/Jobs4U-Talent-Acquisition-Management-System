#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include "functions.h"

#define CONFIGFILE "configs.txt"

int main(int argc, char *argv[]){

    // Usar argumentos do ficheiro de configuração ou do command prompt:
    arguments arglocal;
    if(argc>1){
        strcpy(arglocal.inputPath, argv[1]);
        strcpy(arglocal.outputPath, argv[2]);
        strcpy(arglocal.reportPath, argv[3]);
        arglocal.nWorkers = atoi(argv[4]);
        arglocal.timeInterval = atoi(argv[5]);
    } else {
        if(extractArguments(CONFIGFILE, arglocal) == -1){
            printf("Erro ao ler ficheiro configs.txt.\n")
            return -1; // terminar com erro
        };
    }
    
    // Validar se tem todos os argumentos:
    if(validateAllArgumentsAvailable(arglocal) == -1){
        return -1; // terminar com erro
    };

    // Preparar o SINAL
	struct sigaction act;                       //  |- PREPARA O SINAL
	memset(&act, 0, sizeof(struct sigaction));  //  |
	act.sa_handler = sigUsr1Handler;            //  |
	sigaction(SIGUSR1, &act, NULL);             //  |
       

    // Preparar os PIPES
    enum extremidade {READ=0, WRITE=1};         //  |- CRIA OS PIPES
    int fd[nWorkers][2];                        //  |
    memset(fd, 0, sizeof(fd));                  //  |
    for(int i = 0; i < nWorkers; i++){          //  |
        if(pipe(fd[i]) == -1){                  //  |
            perror("Pipe failed!\n");           //  |
            exit(EXIT_FAILURE);                 //  |
        }                                       //  |
    }                                           //  |
    
    // Execução principal
    pid_t pid;
    pid = fork();
    if(pid == -1){
        perror("Fork ERROR.\n");
        exit(EXIT_FAILURE);
    }else if(pid == 0){
        //CÓDIGO DO JORGE
    }else{
        int result = cria_filhos(nWorkers);
        if(result == -1){
            perror("ERROR when creating children processes.\n");
            exit(EXIT_FAILURE);
        }else if(result == 0){
            char* fileName = "candidate-data.txt";
            char currentPrefix[20] = "";
            char filePath[50] = "";
            struct dirent *entry;
            while(1){
                pause(); //aguardar sinal do filho
                if(strcmp(currentPrefix, "") == 0){ //primeira vez que se recebe sinal
                    if(findFirstPrefix(inputPath, currentPrefix) == 1){
                        printf("No prefix found.\n");
                        exit(EXIT_FAILURE);
                    }
                }else{ //restantes vezes que se recebe sinal
                    if (findNewPrefix(inputPath, currentPrefix) == 1) {
                        printf("No prefix found.\n");
                        exit(EXIT_FAILURE);
                    }
                }
                //Criação do caminho para enviar aos filhos trabalhadores
                snprintf(filePath, sizeof(filePath), "%s/%s-%s", inputPath, currentPrefix, fileName);
                int i = 0;
    
    ////////////// CAROLO: REVER ESTE CICLO FOR COM RICARDO !!!

                for(i = 0; i < nWorkers; i++){
                    close(fd[i][READ]);  
                    write(fd[i][WRITE], filePath, strlen(filePath) + 1);
                    close(fd[i][WRITE]);
                }
            }
        }else{ // código dos "worker child"
            close(fd[result][WRITE]); // fechar canal de escrita
            read(fd[result][READ], filePath, strlen(filePath) + 1); // ler o path do pipe
            close(fd[result][READ]); // fechar canal de leitura

            /////// REVER O QUE É COLOCADO NO PIPE: preciso que seja inserido apenas o prefixo

            // Obter detalhe da "job reference" e do "e-mail do candidato":
            char candidateDataFile[100];
            strcpy(candidateDataFile, prefix);
            strcat(candidateDataFile, "-candidate-data.txt");
            FILE *file = fopen(candidateDataFile, "r"); // abrir ficheiro
            if (file == NULL) { // testar erro ao abrir ficheiro
                perror("Error opening file.\n");
                fclose(file);
                exit(EXIT_FAILURE);;
            }
            char lineRead[250];
            char jobRef[250];
            char jobAppli[250];
            int lineCounter = 0;
            while(fgets(lineRead, sizeof(lineRead), file) != EOF){
                lineCounter++;
                switch (lineCounter){
                    case 1:
                        strcpy(jobRef, lineRead);
                        break;
                    case 2:
                        strcpy(jobAppli, lineRead);
                        break;
                }
            }
            fclose(file);

            // Validar se ficheiro contem os 4 dados essenciais:
            if(lineCounter < 4){ 
                printf("Not enough candidate details.\n");
                exit(EXIT_FAILURE);
            }
            
            // Criar diretório da job reference:
            char basePath = "./input";
            char basePathJob[200];
            strcpy(basePathJob, basePath);
            strcat(basePathJob, "/");
            strcat(basePathJob, jobRef);
            if(createDirectory(basePathJob) == -1){
                printf("Failed to create job reference directory: %s.\n", basePathJob);
                exit(EXIT_FAILURE);
            }

            // Criar diretório da application:
            char basePathJobApplication[200];
            strcpy(basePathJobApplication, basePathJob);
            strcat(basePathJobApplication, "/");
            strcat(basePathJobApplication, jobRef);
            if(createDirectory(basePathJobApplication) == -1){
                printf("Failed to create job reference directory: %s.\n", basePathJobApplication);
                exit(EXIT_FAILURE);
            }

            // Mover ficheiros para o diretório:
            if(moveFilesToDirectory(basePathJobApplication, prefix) == -1){
                printf("Failed to move files to directory: %s.\n", basePathJobApplication);
                exit(EXIT_FAILURE);
            };

            exit(EXIT_SUCCESS);
        }
        
    }


}
