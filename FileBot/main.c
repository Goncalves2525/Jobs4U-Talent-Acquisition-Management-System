#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include "functions.h"

int main(int argc, char *argv[]){
	//char* config = argv[1];
    //int nWorkers = atoi(argv[2]);
    int nWorkers = 5;
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
            while(1){
                pause(); //aguardar sinal do filho
                int i = 0;
                for(i = 0; i < nWorkers; i++){
                    close(fd[i][READ]);
                    
                    //CÓDIGO PARA ENVIAR CAMINHO PARA OS FILHOS TRABALHADORES

                    close(fd[i][WRITE]);
                }
            }
        }else{
            //CÓDIGO DO RAFAEL
        }
        
    }


}
