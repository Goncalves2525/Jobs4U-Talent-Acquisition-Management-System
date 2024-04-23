#ifndef FUNCTIONS_H
#define FUNCTIONS_H

typedef struct Arguments {
    char inputPath[100];
    char outputPath[100];
    char reportPath[100];
    int nWorkers;
    int timeInterval;
} arglocal;

typedef struct ReturnValues {
    int child;
    pid_t pid;
} returnValues;

typedef struct ChildReport{
    int available;
    int qtyFilesMoved;
    char createdPath[100];
    char filesMoved[500];
}childReport;

returnValues cria_filhos(int n);
void sigUsr1Handler(int signal);
void monitor_files(char* inputPath, int timeInterval);
int findNewPrefix(char** fileNames, int fileCount, char* currentPrefix, char* oldPrefixes);
int compareFileNames(const void *a, const void *b);
int getDirFileNames(char* inputPath, char** fileNames);
int extractArguments(const char* configFile, arglocal* arg);
int validateAllArgumentsAvailable(arglocal* arg);
int getApplicationDetails(char* currentPrefix, char* jobReference, char* jobApplicant);
int createDirectory(char* newDirectoryPath);
int countFilesOnDirectory(char* inputPath, char* currentPrefix);
int moveFilesToDirectory(char* inputPath, char* jobApplicantPath, char* currentPrefix);
int createSessionFile(char* sessionFile);
int updateSessionFile(char* sessionFile, childReport* report);
int getFilesOnDirectory(char* inputPath, char* currentPrefix, char* reportFilesMoved);

#endif
