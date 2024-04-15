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

int findFirstPrefix(char *dirPath, char *prefix) {
    DIR *dir;
    struct dirent* entry;

    dir = opendir(dirPath);

    if (dir == NULL) {
        perror("Error opening directory\n");
        return 1;
    }
    
    while ((entry = readdir(dir)) != NULL) {                   // itera sobre os ficheiros do diretório
        // Verifica se é um ficheiro regular e se contém um hífen
        if (strchr(entry->d_name, '-') != NULL) { 
            char *hyphen_pos = strchr(entry->d_name, '-');     // encontra '-'
            int len = hyphen_pos - entry->d_name;              // calcula o tamanho do prefixo
            strncpy(prefix, entry->d_name, len);               // Copy the prefix
            prefix[len] = '\0';                                // termina a "string"
            break;
        }
    }

    closedir(dir);

    //Confirma se foi encontrado algum prefixo
    if (strcmp(prefix, "") == 0) {
        printf("No prefix found.\n");
        return 1;
    } else {
        return 0;
    }
}

int findNewPrefix(const char *dirPath, char *currentPrefix) {
    DIR *dir;
    struct dirent *entry;
    char prefix[20] = "";

    // Open the directory
    dir = opendir(dirPath);

    if (dir == NULL) {
        perror("Error opening directory\n");
        return 1;
    }
    while ((entry = readdir(dir)) != NULL) {
        char *hyphen_pos = strchr(entry->d_name, '-');
        if (hyphen_pos != NULL) {
            int len = hyphen_pos - entry->d_name;
            char tempPrefix[len + 1];
            strncpy(tempPrefix, entry->d_name, len);
            tempPrefix[len] = '\0';
            
            // Verifica se o prefixo é diferente do prefixo atual
            if (strcmp(tempPrefix, currentPrefix) != 0) {
                strcpy(currentPrefix, tempPrefix);
                break;
            }
        }
    }

    closedir(dir);

    if (prefix == NULL) {
        printf("No new prefix found.\n");
        return 1;
    } else {
        return 0;
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
    
}

int moveFilesToDirectory(char* inputPath, char* basePathJobApplication, char* prefix) {

    // find [inputPath] -name '[prefix]-%' -exec mv -t [basePathJobApplication] {} +
}
