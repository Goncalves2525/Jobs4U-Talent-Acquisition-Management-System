#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include "functions.h"

int main(int argc, char *argv[]){
	//char* config = argv[1];
    //int nWorkers = atoi(argv[2]);
    //char* inputPath = argv[3];
    //char* outputPath = argv[4];
    int nWorkers = 5;
    char* inputPath = "Input";
    enum extremidade {READ=0, WRITE=1};

                                                //  |
	struct sigaction act;                       //  |
	memset(&act, 0, sizeof(struct sigaction));  //  |
	act.sa_handler = sigUsr1Handler;            //  |- PREPARA O SINAL
	sigaction(SIGUSR1, &act, NULL);             //  |
                                                //  |       

    int fd[nWorkers][2];                        //  |
    memset(fd, 0, sizeof(fd));                  //  |
    for(int i = 0; i < nWorkers; i++){          //  |
        if(pipe(fd[i]) == -1){                  //  |
            perror("Pipe failed!\n");           //  |- CRIA OS PIPES
            exit(EXIT_FAILURE);                 //  |
        }                                       //  |
    }                                           //  |
                                                //  |

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
                for(i = 0; i < nWorkers; i++){
                    close(fd[i][READ]);  
                    write(fd[i][WRITE], filePath, strlen(filePath) + 1);
                    close(fd[i][WRITE]);
                }
            }
        }else{
            //CÓDIGO DO RAFAEL
        }
        
    }


}
