#include <unistd.h>
#include <dirent.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <signal.h>
#include <time.h>
#include "functions.h"

// Variável global para guardar o estado anterior da pasta
int previous_num_files = 0;

//count how many characaters are in a string
int countChars(char* string){
    int count = 0;
    while(string[count] != '\0'){
        count++;
    }
    return count;
}

returnValues cria_filhos(int n) {
    returnValues values;
    pid_t pid = 0;
    int i = 0;
    for (i = 0; i < n; ++i) {
        pid = fork();
        if (pid < 0){
            values.child = -1;
            return values;
        }else if (pid == 0){
            values.child = i + 1;
            values.pid = getpid();
            return values;
        }
    }
    values.child = 0;
    return values;
}

void sigUsr1Handler(int signal){ //não podemos usar printf
	printf("Handled SIGUSR1\n");
}

int findNewPrefix(char** fileNames, int fileCount, char* currentPrefix, char* oldPrefixes) {
    for (int i = 0; i < fileCount; i++) {
        char* hyphen_pos = strchr(fileNames[i], '-');
        if (hyphen_pos != NULL) {
            int len = hyphen_pos - fileNames[i];
            char tempPrefix[len + 1];
            strncpy(tempPrefix, fileNames[i], len);
            tempPrefix[len] = '\0';
           
            if(strcmp(tempPrefix, currentPrefix) != 0 || strcmp(currentPrefix, "") == 0){
                // Verificar se o prefixo já foi processado
                if(!(strstr(oldPrefixes, tempPrefix) != NULL)){
                    strcpy(currentPrefix, tempPrefix);
                    return 0;
                }
            }
        }
    }
    return 1; //não encontrou novo prefixo
}

int compareFileNames(const void *a, const void *b) {
    return strcmp(*(const char **)a, *(const char **)b);
}

int getDirFileNames(char* inputPath, char** fileNames) {
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
        if (strstr(entry->d_name, "candidate_data.txt")) {
           fileNames[i] = malloc(strlen(entry->d_name) + 1); //aloca memória para cada nome de ficheiro
            strcpy(fileNames[i], entry->d_name); //copia o nome do ficheiro
            fileCount++;
            i++;
        }
    }

    closedir(dir);
    if(i > 1){
        qsort(fileNames, fileCount, sizeof(char *), compareFileNames);
    }

    return fileCount;
}

int extractArguments(const char* configFile, arglocal* arg) {

    FILE *file = fopen(configFile, "r"); // abrir ficheiro
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
        char* token;
        while (fgets(lineRead, sizeof(lineRead), file) != NULL) { // percorre todas as linhas até o final do ficheiro
            token = strtok(lineRead, " ");
            // mediante a primeira parte da linha, aloca o valor ao respetivo elemento do struct
            if(strcmp(token, "#input-directory:") == 0){
				token = strtok(NULL, " ");
                if(token[strlen(token) - 2] == '\r'){ 
					token[strlen(token) - 2] = '\0'; 
				} else { 
					token[strlen(token) - 1] = '\0'; 
				}
                strcpy(arg->inputPath, token);
			}
			if(strcmp(token, "#output-directory:") == 0){
				token = strtok(NULL, " ");
                if(token[strlen(token) - 2] == '\r'){ 
					token[strlen(token) - 2] = '\0'; 
				} else { 
					token[strlen(token) - 1] = '\0'; 
				}
                strcpy(arg->outputPath, token);
			}
			if(strcmp(token, "#report-directory:") == 0){
				token = strtok(NULL, " ");
                if(token[strlen(token) - 2] == '\r'){ 
					token[strlen(token) - 2] = '\0'; 
				} else { 
					token[strlen(token) - 1] = '\0'; 
				}
                strcpy(arg->reportPath, token);
			}
			if(strcmp(token, "#worker-child:") == 0){
				token = strtok(NULL, " ");
                token[strlen(token) - 1] = '\0';
                arg->nWorkers = atoi(token);
			}
			if(strcmp(token, "#time-interval:") == 0){
				token = strtok(NULL, " ");
                //token[strlen(token) - 1] = '\0'; 
                // not applicable due to being configs.txt last line
                arg->timeInterval = atoi(token);
			}
        }
    }
    return 0;
}

int validateAllArgumentsAvailable(arglocal* arg) {
    if(!strcmp(arg->inputPath, "\0")){
        printf("Path INVÁLIDO para o diretório input.\n");
        return -1;
    }
    if(!strcmp(arg->outputPath, "\0")){
        printf("Path INVÁLIDO para o diretório output.\n");
        return -1;
    }
    if(!strcmp(arg->reportPath, "\0")){
        printf("Path INVÁLIDO para o diretório report.\n");
        return -1;
    }
    if(arg->nWorkers <= 0){
        printf("Nr de 'worker childs' inválido.\n");
        return -1;
    }
    if(arg->timeInterval < 0){
        printf("Tempo de 'time interval' inválido.\n");
        return -1;
    }
    return 0;
}

int getApplicationDetails(char* currentPrefix, char* jobReference, char* jobApplicant) {
	
	char candidateDataFile[100];
	strcpy(candidateDataFile, "input/");
	strcat(candidateDataFile, currentPrefix);
	strcat(candidateDataFile, "-candidate-data.txt");
    
	
	FILE *file = fopen(candidateDataFile, "r"); // abrir ficheiro
	if (file == NULL) { // testar erro ao abrir ficheiro
		char errorMessage[300];
		strcpy(errorMessage, "Error opening file for candidate data ");
		strcat(errorMessage, candidateDataFile);
		strcat(errorMessage,"\n");
		perror(errorMessage);
		fclose(file);
		return -1;
	}
	
	char lineRead[250];
	int lineCounter = 0;
	while(fgets(lineRead, sizeof(lineRead), file) != NULL){
        //printf("Line counter %d\n", lineCounter);
		lineCounter++;
		switch (lineCounter){
			case 1:
				strcpy(jobReference, lineRead);
				if(jobReference[strlen(jobReference) - 2] == '\r'){ 
					jobReference[strlen(jobReference) - 2] = '\0'; 
				} else { 
					jobReference[strlen(jobReference) - 1] = '\0'; 
				}
				break;
			case 2:
				strcpy(jobApplicant, lineRead);
				if(jobApplicant[strlen(jobApplicant) - 2] == '\r'){ 
					jobApplicant[strlen(jobApplicant) - 2] = '\0'; 
				} else { 
					jobApplicant[strlen(jobApplicant) - 1] = '\0'; 
				}
				break;
		}
	}
	fclose(file);

	// Validar se ficheiro contem os 4 dados essenciais:
	if(lineCounter < 4){ 
		char errorMessage[300];
		strcpy(errorMessage, "Not enough candidate details at ");
		strcat(errorMessage, candidateDataFile);
		strcat(errorMessage,"\n");
		perror(errorMessage);
		return -1;
	}
	
	return 0;
}

int createDirectory(char* newDirectoryPath) {

    returnValues result = cria_filhos(1);

    // Lança erro de criação de filho
    if(result.child == -1){
        return -1;
    }

    // Sendo processo filho, cria um novo diretorio de acordo com o parametro
    if(result.child > 0){
		if(opendir(newDirectoryPath) == NULL){
			int valid = 0;
			char parameter[100];
			strcpy(parameter, "mkdir ");
			strcat(parameter, newDirectoryPath);
			valid = execlp("mkdir", "mkdir", newDirectoryPath, NULL);
			perror("Error creating directory\n");
			exit(valid);
		}
    }

    // Aguarda o término do filho para encerrar o processo
    wait(NULL);
    return 0;
}

int countFilesOnDirectory(char* inputPath, char* currentPrefix) {
	DIR *dir;
    struct dirent* entry;
    int fileCount = 0;

    dir = opendir(inputPath);

    if (dir == NULL) {
        perror("Error opening directory\n");
        closedir(dir);
        return -1;
    }

	size_t prefixLength = strlen(currentPrefix);
    while ((entry = readdir(dir)) != NULL) {
        if (strncmp(entry->d_name, currentPrefix, prefixLength) == 0) {
            fileCount++;
        }
    }

    closedir(dir);
    
    return fileCount;
}

int moveFilesToDirectory(char* inputPath, char* jobApplicantPath, char* currentPrefix) {
    // find [inputPath] -type f -name '[currentPrefix]-*' -exec mv {} [jobApplicantPath] \;
    // mv [inputPath]/[currentPrefix]-* [jobApplicantPath]/

    returnValues result = cria_filhos(1);

    // Lança erro de criação de filho
    if(result.child == -1){
        return -1;
    }

    // Sendo processo filho, move todos os ficheiros com o prefixo passado em parametro
    if(result.child > 0){
        
        char fromPath[200];
        strcpy(fromPath, inputPath);
        strcat(fromPath, "/");
        strcat(fromPath, currentPrefix);
        strcat(fromPath, "-*");
        
        char toPath[200];
        strcpy(toPath, jobApplicantPath);
        strcat(toPath, "/");
        
        char command[300];
        strcpy(command, "mv ");
        strcat(command, fromPath);
        strcat(command, " ");
        strcat(command, toPath);

		execlp("sh", "sh", "-c", command, NULL);
        //execlp("mv", "mv", fromPath, toPath, NULL); // cannot use wildcards
        perror("execlp");
        exit(EXIT_FAILURE);
    }

    // Aguarda o término do filho para encerrar o processo
    wait(NULL);
    return 0;
}

void monitor_files(char* inputPath, int timeInterval) {
    DIR* dir;
    struct dirent* entry;
    while (1) {
        dir = opendir(inputPath);
        if (dir == NULL) {
            char errorMessage[300];
			strcpy(errorMessage, "Error opening directory: ");
			strcat(errorMessage, inputPath);
			strcat(errorMessage,"\n");
			perror(errorMessage);
            return;
        }
        int num_files = 0;
        while ((entry = readdir(dir)) != NULL) {
            if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0) {
                continue; // Ignorar diretório atual e pai
            }
            num_files++;
        }
        closedir(dir);
        // Verificar se o número de ficheiros alterou
        if (num_files > previous_num_files) {
            // Se encontrar novos ficheiros enviar um sinal para o processo pai
            printf("FILHO: enviei sinal para o pai\n");
            kill(getppid(), SIGUSR1);
        }
        // Atualiza o estado anterior
        //previous_num_files = num_files;
        // Se não houver ficheiros na pasta, coloca o contador a 0
        if (num_files == 0) {
            previous_num_files = 0;
        }
        sleep(timeInterval);
    }
}

int createSessionFile(char* sessionFile) {
	FILE *file = fopen(sessionFile, "w"); // criar ficheiro, apagando algum anterior, caso já exista
    if (file == NULL) { // testar erro ao criar ficheiro
        perror("Error creating file");
        fclose(file);
        return -1;
    }
    fclose(file);
    return 0;
}

int updateSessionFile(char* sessionFile, childReport* report) {
    
    // #subdir: ABC \n - FILENAME1 \n - FILENAMEn \n \n 
    
    char qtyFilesMovedString[12];
    sprintf(qtyFilesMovedString, "%d", report->qtyFilesMoved);
    char newReportLine[700];
    strcpy(newReportLine, "#subdir: ");
    strcat(newReportLine, report->createdPath);
    strcat(newReportLine, " (qty files moved: ");
    strcat(newReportLine, qtyFilesMovedString);
    strcat(newReportLine, ")\n");
    strcat(newReportLine, report->filesMoved);
    strcat(newReportLine, "\n");
    
    FILE *file = fopen(sessionFile, "a"); // abre ficheiro, para adicionar novos dados
    if (file == NULL) { // testar erro ao abrir ficheiro
        perror("Error using file");
        fclose(file);
        return -1;
    }
    
    printf("vou escrever no %s a frase:\n%s\n", sessionFile, newReportLine); // TODO: apagar depois dos testes
    fprintf(file, "%s", newReportLine);

    fclose(file);
    
    return 0;
}

int getFilesOnDirectory(char* inputPath, char* currentPrefix, char* reportFilesMoved) {
	DIR *dir;
    struct dirent* entry;
    int fileCount = 0;

    dir = opendir(inputPath);

    if (dir == NULL) {
        perror("Error opening directory\n");
        closedir(dir);
        return -1;
    }

	size_t prefixLength = strlen(currentPrefix);
    while ((entry = readdir(dir)) != NULL) {
        if (strncmp(entry->d_name, currentPrefix, prefixLength) == 0) {
            fileCount++;
            strcat(reportFilesMoved, entry->d_name);
            strcat(reportFilesMoved, "\n");
        }
    }

    closedir(dir);
    
    return fileCount;
}
