#ifndef FUNCTIONS_H
#define FUNCTIONS_H

#include <semaphore.h>

#define SHM_NAME "/shm"
#define BUFFER_SIZE 256
#define MONITOR_MUTEX "/monitor_mutex"
#define MONITOR_READ_MUTEX "/monitor_read_mutex"
#define MONITOR_WRITE_MUTEX "/monitor_write_mutex"


extern sem_t* monitor_read_mutex;
extern sem_t* monitor_write_mutex;
extern sem_t* monitor_sem;

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
    pid_t pid;
    char createdPath[100];
    char filesMoved[500];
} childReport;

typedef struct {
    char buffer[BUFFER_SIZE];
    int readCount;
    int writeCount;
} shared_data_type;

typedef struct 
{
    /* data */
    
    int qtyFilesMoved;
    pid_t pid;
    char createdPath[200];
    char filesMoved[500];

} shared_report_data;

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
