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

/**
 * Conta o numero de caracteres no @param string. até encontrar o caractere null ('\0').
 *
 * @param string - string a contar os caracteres.
 * @return O número de caracteres presentes na string.
 */
int countChars(char* string){
    int count = 0;
    // Percorre a string até encontrar o caractere nulo 
    while(string[count] != '\0'){
        count++;
    }
    return count; // Retorna o número de caracteres contados.
}

/**
 * Cria um n número de processos filhos.
 * O nº de processos filhos criados é determinado pelo parâmetro 'n'.
 *
 * @param n O número de processos filhos a serem criados.
 * @return Uma struct `returnValues` contendo informações sobre o processo filho criado.
 * 	Valores possiveis para o campo child:
 * 	-1	Erro na criação do processo
 * 	0	processo pai
 * 	>0	pid do processo
 */
returnValues cria_filhos(int n) {
    returnValues values; //garvar aqui os valores a retornar
    pid_t pid = 0; 
    int i = 0;//counter
    for (i = 0; i < n; ++i) {
        pid = fork(); // Cria um novo processo.
        // Define o campo `child` como -1 para indicar erro.
        if (pid < 0){
            values.child = -1; // Define o campo `child` como -1 para indicar erro.
            return values; // Retorna a estrutura de valores.
        }
        // Se o ID do processo for 0, indica que este é o processo filho.
        else if (pid == 0){
					
            values.child = i + 1;  // Define `child` como o índice do filho mais um.
            values.pid = getpid();// Obtém o ID do processo filho.
            return values; // Retorna a estrutura de valores.
        }
    }
    // Se o processo atual é o processo pai.
    values.child = 0; // Define `child` como 0.
    return values;// Retorna a estrutura de valores.
}

/**
 * Handler do sinal SIGUSR1. é chamada quando o processo recebe o sinal SIGUSR1.
 *
 * @param signal O número do sinal recebido.
 */
void sigUsr1Handler(int signal){ 
	puts("Handled SIGUSR1\n"); 
}

/**
 * Procura por um novo prefixo nos nomes dos ficheiros passados .
 * O prefixo é definido como a parte de cada nome de ficheiro antes do primeiro hífen ('-').
 * Se um novo prefixo é encontrado, ele é armazenado em 'currentPrefix'.
 *
 * @param fileNames Um array de strings contendo os nomes dos ficheiros.
 * @param fileCount O número de ficheiros no array 'fileNames'.
 * @param currentPrefix O prefixo atual que está sendo considerado.
 * @param oldPrefixes Uma string contendo todos os prefixos já processados.
 * @return 0 se um novo prefixo é encontrado e atualizado em 'currentPrefix',
 *         1 se nenhum novo prefixo é encontrado.
 */
int findNewPrefix(char** fileNames, int fileCount, char* currentPrefix, char* oldPrefixes) {
    for (int i = 0; i < fileCount; i++) {
		// Encontra a posição do primeiro hífen no nome do ficheiro.
        char* hyphen_pos = strchr(fileNames[i], '-');
        // Se um hífen é encontrado.
        if (hyphen_pos != NULL) {
			// Calcula o comprimento do prefixo.
            int len = hyphen_pos - fileNames[i];
            // Cria uma string temporária para armazenar o prefixo.
            char tempPrefix[len + 1];
            // Copiar o prefixo para a string temporária.
            strncpy(tempPrefix, fileNames[i], len);
            tempPrefix[len] = '\0';
			// Verifica se o prefixo encontrado é diferente do prefixo atual ou se o prefixo atual é uma string vazia.
            if(strcmp(tempPrefix, currentPrefix) != 0 || strcmp(currentPrefix, "") == 0){
                // Verificar se o prefixo já foi processado
                if(!(strstr(oldPrefixes, tempPrefix) != NULL)){
                    // Atualiza o prefixo atual com o novo prefixo encontrado.
                    strcpy(currentPrefix, tempPrefix);
                    return 0; // Retorna 0 para indicar que um novo prefixo foi encontrado e atualizado.
                }
            }
        }
    }
    return 1; // Retorna 1 para indicar que nenhum novo prefixo foi encontrado.
}


/**
 * Comparação dois nomes de ficheiros e retorna um valor que indica a ordem entre eles.
 *
 * @param a - ponteiro para o primeiro nome de ficheiro a ser comparado.
 * @param b - ponteiro para o segundo nome de ficheiro a ser comparado.
 * @return Valores que retorna: 
 * 	<0	se o primeiro nome de ficheiro precede o segundo
 *	0	se os dois nomes de ficheiro são iguais
 *	>0	se o segundo nome de ficheiro precede o primeiro
 */
int compareFileNames(const void *a, const void *b) {
    return strcmp(*(const char **)a, *(const char **)b);
}

/**
 * Lista os nomes de ficheiros no diretório especificado que correspondem 
 * a um critérios em um array e ordenada em ordem alfabética.
 *
 * @param inputPath - path diretório a ser examinado.
 * @param fileNames Um array de ponteiros para strings, onde os nomes dos ficheiros serão armazenados.
 * @return Nº de ficheiros encontrados que correspondem ao critério .
 *         0 se nenhum ficheiro é encontrado ou ocorrer um erro ao abrir o diretório.
 */
int getDirFileNames(char* inputPath, char** fileNames) {
    DIR *dir; // Ponteiro para o diretório.
    struct dirent *entry; // Estrutura para armazenar informações sobre os ficheiros no diretório.
    int fileCount = 0;  // Contador de ficheiros encontrados.
    int i = 0; 
	// Abre o diretório especificado.
    dir = opendir(inputPath);
	//Testa se consehui abrir
    if (dir == NULL) {
		//Erro ao abrir e envia mensagem de eero
        perror("Error opening directory\n");
        return -1;
    }
	//Ir a cada um do diretorios
    while ((entry = readdir(dir)) != NULL) {
        //não conta com os ficheiros . e ..
        if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0 || strcmp(entry->d_name, ".DS_Store") == 0) {
            continue;
        }
        // Procura se contém a substring "candidate-data.txt".
        if (strstr(entry->d_name, "candidate-data.txt")) {
			
           fileNames[i] = malloc(strlen(entry->d_name) + 1); //aloca memória para cada nome de ficheiro
            strcpy(fileNames[i], entry->d_name); //copia o nome do ficheiro
            fileCount++;
            i++;
        }
    }

    closedir(dir);// Fecha o diretório após a leitura.
    // Se mais de um ficheiro foi encontrado, ordena os nomes dos ficheiros em ordem alfabética.
    if(i > 1){
        qsort(fileNames, fileCount, sizeof(char *), compareFileNames);
    }

    return fileCount; // Retorna o número de ficheiros encontrados.
}

/**
 * Lê o ficheiro de configuração e extrai os valores dos argumentos
 * , armazena-os numa struct 'arglocal'.
 *
 * @param configFile - path do ficheiro de configuração a ser lido.
 * @param arg Um ponteiro para a estrutura 'arglocal' onde os argumentos serão armazenados.
 * @return 	0 sucesso
 *			-1  erro
 */
int extractArguments(const char* configFile, arglocal* arg) {

    FILE *file = fopen(configFile, "r"); // abrir ficheiro
    if (file == NULL) { // testar erro ao abrir ficheiro
        perror("Error opening file");
        //fclose(file);
        return -1; // Retornar -1 para indicar erro
    }

    if (fgetc(file) == EOF) { // testar se o ficheiro está vazio
        return -1; // Retornar -1 para indicar erro
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
        // Fechar o ficheiro
		fclose(file);
    }
    return 0;
}


/**
 * Verifica se todos os argumentos necessários estão preenchidos e se tem valores válidos.
 *
 * @param arg Um ponteiro para a estrutura 'arglocal' contendo os argumentos a serem validados.
 * @return 	0 se todos os argumentos são válidos,
 * 			-1 argumento ausente ou inválido.
 */
int validateAllArgumentsAvailable(arglocal* arg) {
    // Verificar se o inputPath é valido
    if(!strcmp(arg->inputPath, "\0")){
        printf("Path INVÁLIDO para o diretório input.\n");
        return -1;
    }
    // Verificar se o outputPath  é valido
    if(!strcmp(arg->outputPath, "\0")){
        printf("Path INVÁLIDO para o diretório output.\n");
        return -1;
    }
    // Verificar se o reportPath é valido
    if(!strcmp(arg->reportPath, "\0")){
        printf("Path INVÁLIDO para o diretório report.\n");
        return -1;
    }
    // Verificar se o nWorkers é valido
    if(arg->nWorkers <= 0){
        printf("Nr de 'worker childs' inválido.\n");
        return -1;
    }
    // Verificar se o timeInterval é valido
    if(arg->timeInterval < 0){
        printf("Tempo de 'time interval' inválido.\n");
        return -1;
    }
    return 0;
}

/**
 * Lê um ficheiro de candidatedata e extrai as informações relevantes
 * sobre a referência do trabalho e o candidato, armazenando-os em strings fornecidas.
 *
 * @param currentPrefix - prefixo atual para o nome do ficheiro.
 * @param jobReference - ponteiro para uma string onde a referência do trabalho será armazenada.
 * @param jobApplicant - ponteiro para uma string onde o nome do candidato será armazenado.
 * @return	0 sucesso,
 *			-1 erro ao abrir/ler ficheiro, ou se não contiver informações suficientes.
 */
int getApplicationDetails(char* currentPrefix, char* jobReference, char* jobApplicant) {
	
	// Construir a path
	char candidateDataFile[100];
	strcpy(candidateDataFile, "input/");
	strcat(candidateDataFile, currentPrefix);
	strcat(candidateDataFile, "-candidate-data.txt");
    
	
	FILE *file = fopen(candidateDataFile, "r"); // abrir ficheiro
	if (file == NULL) { // testar erro ao abrir ficheiro
		//Detalhar a mensagem de erro
		char errorMessage[300];
		strcpy(errorMessage, "Error opening file for candidate data ");
		strcat(errorMessage, candidateDataFile);
		strcat(errorMessage,"\n");
		perror(errorMessage);
		fclose(file);
		return -1; //retornar erro
	}
	
	char lineRead[250];
	int lineCounter = 0;
	//precorrer todas a linhas
	while(fgets(lineRead, sizeof(lineRead), file) != NULL){
        //printf("Line counter %d\n", lineCounter);
		lineCounter++;
		switch (lineCounter){
			case 1:
				//jobReference
				strcpy(jobReference, lineRead);
				if(jobReference[strlen(jobReference) - 2] == '\r'){ 
					jobReference[strlen(jobReference) - 2] = '\0'; 
				} else { 
					jobReference[strlen(jobReference) - 1] = '\0'; 
				}
				break;
			case 2:
				//jobApplicant
				strcpy(jobApplicant, lineRead);
				if(jobApplicant[strlen(jobApplicant) - 2] == '\r'){ 
					jobApplicant[strlen(jobApplicant) - 2] = '\0'; 
				} else { 
					jobApplicant[strlen(jobApplicant) - 1] = '\0'; 
				}
				break;
		}
	}
	fclose(file); //fechar ficheiro

	// Validar se ficheiro contem os 4 dados essenciais:
	if(lineCounter < 4){ 
		char errorMessage[300];
		strcpy(errorMessage, "Not enough candidate details at ");
		strcat(errorMessage, candidateDataFile);
		strcat(errorMessage,"\n");
		perror(errorMessage);
		return -1;//retorn erro
	}
	
	return 0; //retorna sucesso
}

/**
 * Cria um novo diretório.
 *
 * @param newDirectoryPath path para o novo diretório .
 * @return	0  sucesso,
 *			-1 erro.
 */
int createDirectory(char* newDirectoryPath) {
	// Cria um processo filho para executar a criação do diretório
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


/**
 * Conta o número de ficheiros em um diretório com um determinado prefixo.

 *
 * @param inputPath  - path do diretório a ser examinado.
 * @param currentPrefix - prefixo a ser comparado com os ficheiros.
 * @return	número de ficheiros encontrados com o prefixo ,
 *			1 erro.
 */
int countFilesOnDirectory(char* inputPath, char* currentPrefix) {
	DIR *dir;
    struct dirent* entry;
    int fileCount = 0;

    dir = opendir(inputPath);

    if (dir == NULL) {
		//Erro ao abrir diretorio
        perror("Error opening directory\n");
        closedir(dir);
        return -1; //retorna erro
    }
	// Calcula o tamanho do prefixo.
	size_t prefixLength = strlen(currentPrefix);
	// Percorre os ficheiros no diretório.
    while ((entry = readdir(dir)) != NULL) {
        if (strncmp(entry->d_name, currentPrefix, prefixLength) == 0) {
            fileCount++;
        }
    }

    closedir(dir);//fechar 
    
    return fileCount; //retorna nº encontrado
}

/**
 * Move todos os ficheiros no diretório de entrada que têm um prefixo específico
 * para o diretório de destino fornecido.
 *
 * @param inputPath - path do diretório de entrada onde os ficheiros serão procurados.
 * @param jobApplicantPath - path do diretório de destino onde os ficheiros serão movidos.
 * @param currentPrefix - prefixo usado para identificar os ficheiros a serem movidos.
 * @return	0 ficheiros movidos com sucesso,
 *			-1  erro.
 */
int moveFilesToDirectory(char* inputPath, char* jobApplicantPath, char* currentPrefix) {
    // find [inputPath] -type f -name '[currentPrefix]-*' -exec mv {} [jobApplicantPath] \;
    // mv [inputPath]/[currentPrefix]-* [jobApplicantPath]/

    returnValues result = cria_filhos(1);

    // Lança erro de criação de filho
    if(result.child == -1){
		perror("Erro ao criar o processo");
        return -1; //erro
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
        //execlp("mv", "mv", fromPath, toPath, NULL); // Não pode usar wildcards
        perror("execlp");
        exit(EXIT_FAILURE);
    }

    // Aguarda o término do filho para encerrar o processo
    wait(NULL);
    return 0; //retorna sucesso
}

/**
 * Monitoriza o número de ficheiros num diretório especificado
 * e envia um sinal para o processo pai se detectar alterações no número de ficheiros.
 *
 * @param inputPath - path  do diretório a ser monitorizado.
 * @param timeInterval - intervalo de tempo (em segundos), entre as verificações.
 */
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
        closedir(dir); // Fechar o diretório
        // Verificar se o número de ficheiros alterou
        if (num_files > previous_num_files) {
            // Se encontrar novos ficheiros envia um sinal para o processo pai
            // printf("FILHO: enviei sinal para o pai\n");
            kill(getppid(), SIGUSR1);
        }
        // Atualiza o estado anterior
        previous_num_files = num_files;
        
        // Se não houver ficheiros na pasta, coloca o contador a 0
        if (num_files == 0) {
            previous_num_files = 0;
        }
        sleep(timeInterval);
    }
}

/**
 * Cria um novo ficheiro de sessão .
 *
 * @param sessionFile - path do ficheiro de sessão a ser criado.
 * @return	0  sucesso,
 *			-1 erro.
 */
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


/**
 * Atualiza um ficheiro de sessão .
 *
 * @param sessionFile - path do ficheiro de sessão a ser atualizado.
 * @param childReport - ponteiro para strut.
 * @return	0 sucesso ,
 * 			-1 erro.
 */
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
    
    //printf("vou escrever no %s a frase:\n%s\n", sessionFile, newReportLine);
    fprintf(file, "%s", newReportLine);

    fclose(file);
    
    return 0;
}

/**
 * Verifica todos os ficheiros em um diretório que começam com um prefixo específico
 * e adiciona ao reportFilesMoved.
 *
 * @param inputPath - path do diretório a ser examinado.
 * @param currentPrefix - prefixo a ser comparado com os nomes dos ficheiros.
 * @param reportFilesMoved - ficheiros movidos.
 * @return número de ficheiros no diretório com o prefixo,
 *         -1 erro.
 */
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
