#include <unistd.h>
#include <dirent.h>
#include <stdio.h>
#include <string.h>
#include "functions.h"

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

int findNewPrefix(char** fileNames, int fileCount, char* currentPrefix) {
    for (int i = 0; i < fileCount; i++) {
        char* hyphen_pos = strchr(fileNames[i], '-');
        if (hyphen_pos != NULL) {
            int len = hyphen_pos - fileNames[i];
            char tempPrefix[len + 1];
            strncpy(tempPrefix, fileNames[i], len);
            tempPrefix[len] = '\0';

            if (strcmp(tempPrefix, currentPrefix) != 0) {
                strcpy(currentPrefix, tempPrefix);
                return 0;
            }
        }
    }
    return 1; //não encontrou novo prefixo
}

int compareFileNames(const void *a, const void *b) {
    return strcmp(*(const char **)a, *(const char **)b);
}

int getDirFileNames(char* inputPath, char*** fileNames) {
    DIR *dir;
    struct dirent *entry;
    int fileCount = 0;
    int i = 0;

    dir = opendir(inputPath);

    if (dir == NULL) {
        perror("Error opening directory\n");
        return 1;
    }

    while ((entry = readdir(dir)) != NULL) {
        //não conta com os ficheiros . e ..
        if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0 || strcmp(entry->d_name, ".DS_Store") == 0) {
            continue;
        }
        fileNames[i] = malloc(strlen(entry->d_name) + 1); //aloca memória para cada nome de ficheiro
        strcpy(fileNames[i], entry->d_name); //copia o nome do ficheiro
        fileCount++;
        i++;
    }

    closedir(dir);
    qsort(fileNames, fileCount, sizeof(char *), compareFileNames);

    if (i == 0) {
        printf("No files found in directory.\n");
        return -1;
    } else {
        return fileCount;
    }
}

int extractArguments(const char* configFile, arguments* arg) {

    FILE *file = fopen(CONFIGFILE, "r"); // abrir ficheiro
    if (file == NULL) { // testar erro ao abrir ficheiro
        perror("Error opening file");
        fclose(file);
        return -1;
    }

    if (fgetc(file) == EOF) { // testar se o ficheiro está vazio
        return -1;
    } else { // executar código para recolher valores do ficheiro
        rewind(file); // reposicionar no inicio do ficheiro
        char lineRead[250]; // variável para guardar o conteúdo de cada linha temporariamente
        char token[250];
        while (fgets(lineRead, sizeof(lineRead), file) != EOF) { // percorre todas as linhas até o final do ficheiro
            token = strtok(configLine, " ");
            switch (token) { // mediante a primeira parte da linha, aloca o valor ao respetivo elemento do struct
                case "#input-directory:":
                    token = strtok(NULL, " ");
                    strcpy(arg.inputPath, token);
                    break;
                case "#output-directory:":
                    token = strtok(NULL, " ");
                    strcpy(arg.outputPath, token);
                    break;
                case "#report-directory:":
                    token = strtok(NULL, " ");
                    strcpy(arg.reportPath, token);
                    break;
                case "#worker-child:":
                    token = strtok(NULL, " ");
                    arg.nWorkers = atoi(token);
                    break;
                case "#time-interval:":
                    token = strtok(NULL, " ");
                    arg.timeInterval = atoi(token);
                    break;
            }
        }
    }
    return 0;
}

int validateAllArgumentsAvailable(arguments* arglocal) {
    if(!strcmp(arg.inputPath, "\0")){
        printf("Path INVÁLIDO para o diretório input.\n");
        return -1;
    }
    if(!strcmp(arg.outputPath, "\0")){
        printf("Path INVÁLIDO para o diretório output.\n");
        return -1;
    }
    if(!strcmp(arg.reportPath, "\0")){
        printf("Path INVÁLIDO para o diretório report.\n");
        return -1;
    }
    if(arg.nWorkers <= 0){
        printf("Nr de 'worker childs' inválido.\n");
        return -1;
    }
    if(arg.timeInterval < 0){
        printf("Tempo de 'time interval' inválido.\n");
        return -1;
    }
    return 0;
}

int createDirectory(char* newDirectoryPath) {

    int filho = cria_filhos(1);

    // Lança erro de criação de filho
    if(filho == -1){
        return -1;
    }

    // Sendo processo filho, cria um novo diretorio de acordo com o parametro
    if(filho > 0){
        int valid = 0;
        valid = execlp("mkdir", newDirectoryPath, NULL);
        exit(valid);
    }

    // Aguarda o término do filho para encerrar o processo
    wait(NULL);
    return 0;
}

int moveFilesToDirectory(char* inputPath, char* basePathJobApplication, char* prefix) {
    // find [inputPath] -name '[prefix]-%' -exec mv -t [basePathJobApplication] {} +

    int filho = cria_filhos(1);

    // Lança erro de criação de filho
    if(filho == -1){
        return -1;
    }

    // Sendo processo filho, move todos os ficheiros com o prefixo passado em parametro
    if(filho > 0){
        int valid = 0;

        char fullExecution[500];
        strcpy(fullExecution, inputPath);
        strcat(fullExecution, " -name ");
        strcat(fullExecution, " '");
        strcat(fullExecution, prefix);
        strcat(fullExecution, "-%' ");
        strcat(fullExecution, " -exec mv -t ");
        strcat(fullExecution, basePathJobApplication);
        strcat(fullExecution, " {} +");

        valid = execlp("find", fullExecution, NULL);
        exit(valid);
    }

    // Aguarda o término do filho para encerrar o processo
    wait(NULL);
    return 0;
}
