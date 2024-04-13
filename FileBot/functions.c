#include <unistd.h>
#include <dirent.h>
#include <stdio.h>
#include <string.h>


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
