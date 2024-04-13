#include <unistd.h>



int cria_filhos(int n) {
    pid_t pid = 0;
    int i = 0;
    for (i = 0; i < n; ++i) {
        pid = fork();
        if (pid < 0)
            return -1;
        else if (pid == 0)
            return i + 1;
    }
    return 0;
}

void sigUsr1Handler(int signal){ //não podemos usar printf
	printf("Handled SIGUSR1\n");
}