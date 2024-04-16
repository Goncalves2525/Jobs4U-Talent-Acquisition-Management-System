#ifndef FUNCTIONS_H
#define FUNCTIONS_H

int cria_filhos(int n);
void sigUsr1Handler(int signal);
int findNewPrefix(char** fileNames, int fileCount, char* currentPrefix);
int compareFileNames(const void *a, const void *b);
int getDirFileNames(char* inputPath, char*** fileNames);
int extractArguments(const char* configFile, arguments* arg);
int validateAllArgumentsAvailable(argumtents* arglocal);

struct arguments {
    char inputPath[100] = {};
    char outputPath[100] = {};
    char reportPath[100] = {};
    int nWorkers = 0;
    int timeInterval = -1;
};

#endif
