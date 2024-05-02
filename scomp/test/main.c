#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "unity.h"

#include <signal.h>

#include "unity.h"
#include "../functions.h"

#define MAX_FILES 100 // Número máximo de ficheiros para o teste







void test_countChars() {
    TEST_ASSERT_EQUAL_INT(5, countChars("TESTE"));
    TEST_ASSERT_EQUAL_INT(0, countChars(""));
    TEST_ASSERT_EQUAL_INT(5, countChars("12345"));
    TEST_ASSERT_EQUAL_INT(18, countChars("Teste Com espaços"));
}

void test_cria_filhos(){

   // Chama a funcao cria_filhos para criar 3 processos filhos
    returnValues result = cria_filhos(3);

    // Verificação dos resultados
    if (result.child == -1) {
        // Verifica se ocorreu erro na criação dos filhos
        TEST_FAIL_MESSAGE("Erro na criação dos filhos");
        
    } else if (result.child == 0) {
        // Verifica se o processo atual é um filho
        TEST_ASSERT_TRUE(result.child > 0);
        TEST_ASSERT_EQUAL_INT(getppid(), result.pid);
        
    } else {
        // Verifica se o processo atual é o pai
        TEST_ASSERT_EQUAL_INT(getpid(), result.pid);
        
        // Espera que todos os filhos terminem
        for (int i = 0; i < 3; ++i) {
            int status;
            waitpid(-1, &status, 0);
        }
    }
}

void test_findNewPrefix() {
    // Nomes a testar
    char* fileNames[] = {"prefix1-file1.txt", "prefix1-file2.txt", "prefix2-file3.txt"};

    // Inicializar  variáveis
    char currentPrefix[100] = "";
    char oldPrefixes[100] = "";

    // Chama a função 
    int result = findNewPrefix(fileNames, 3, currentPrefix, oldPrefixes);

    // Analisar os resultados
    TEST_ASSERT_EQUAL_INT(0, result);
    TEST_ASSERT_EQUAL_STRING("prefix1", currentPrefix);

    // Reinicializar as  variáveis
    strcpy(currentPrefix, "");
    strcpy(oldPrefixes, "");

    // Volar a chamar  a função 
    result = findNewPrefix(fileNames, 3, currentPrefix, oldPrefixes);

    // Anlisar resultados
    TEST_ASSERT_EQUAL_INT(0, result);
    TEST_ASSERT_EQUAL_STRING("prefix2", currentPrefix);

     // Reinicializar as  variáveis
    strcpy(currentPrefix, "");
    strcpy(oldPrefixes, "prefix1");

    // Volta a chamar  a função 
    result = findNewPrefix(fileNames, 3, currentPrefix, oldPrefixes);

    // Analisar resultados
    TEST_ASSERT_EQUAL_INT(1, result);
    TEST_ASSERT_EQUAL_STRING("", currentPrefix);
}


void test_compareFileNames() {
    const char *names[] = {"file1.txt", "file3.txt", "file2.txt"};
    
    TEST_ASSERT_EQUAL_STRING("file1.txt", names[0]);
    TEST_ASSERT_EQUAL_STRING("file2.txt", names[2]);
    TEST_ASSERT_EQUAL_STRING("file3.txt", names[1]);
    //ordenar
    qsort(names, sizeof(names) / sizeof(names[0]), sizeof(names[0]), compareFileNames);
    TEST_ASSERT_EQUAL_STRING("file2.txt", names[1]);
    TEST_ASSERT_EQUAL_STRING("file3.txt", names[2]);
}

void test_getDirFileNames() {
    // variáveis para os testes
    char* fileNames[MAX_FILES] = {0};
    char* inputPath = "../Exemplo"; 

    // Chama a função getDirFileNames
    int fileCount = getDirFileNames(inputPath, fileNames);

    // Verifica os resultados
    TEST_ASSERT_GREATER_OR_EQUAL_INT(0, fileCount); // Verifica se o número de arquivos é maior ou igual a zero

    if (fileCount > 0) {
        // Verifica se os ficheiros estão ordenados corretamente
        for (int i = 1; i < fileCount; ++i) {
            TEST_ASSERT_TRUE(strcmp(fileNames[i-1], fileNames[i]) <= 0);
        }
    }

    // Liberta a memória 
    for (int i = 0; i < fileCount; ++i) {
        free(fileNames[i]);
    }
}




void test_extractArguments() {
    // Cria  ficheiro de configuração de teste
    FILE *file = fopen("test_config.txt", "w");
    fprintf(file, "#input-directory: input\r\n");
    fprintf(file, "#output-directory: output\r\n");
    fprintf(file, "#report-directory: report\r\n");
    fprintf(file, "#worker-child: 4\r\n");
    fprintf(file, "#time-interval: 10\r\n");
    fclose(file);

    // Chama a função para o ficheiro
    arglocal arg;
    int result = extractArguments("test_config.txt", &arg);
		
    // Verificar os resultados
    TEST_ASSERT_EQUAL_INT(0, result);
    TEST_ASSERT_EQUAL_STRING("input", arg.inputPath);
    TEST_ASSERT_EQUAL_STRING("output", arg.outputPath);
    TEST_ASSERT_EQUAL_STRING("report", arg.reportPath);
    TEST_ASSERT_EQUAL_INT(4, arg.nWorkers);
    TEST_ASSERT_EQUAL_INT(10, arg.timeInterval);
	
	
    // Apagar ficheiro 
    system("rm test_config.txt");
    //Testar com ficheiro inexistente
    result = extractArguments("test_config.txt", &arg);
    TEST_ASSERT_EQUAL_INT(-1, result);
}

void test_validateAllArgumentsAvailable() {
    // Caso válido: todos os argumentos estão disponíveis
    arglocal arg1 = {"input", "output", "report", 4, 10};
    TEST_ASSERT_EQUAL_INT(0, validateAllArgumentsAvailable(&arg1));

    // Caso inválido: sem path do diretório de entrada 
    arglocal arg2 = {"", "output", "report", 4, 10};
    TEST_ASSERT_EQUAL_INT(-1, validateAllArgumentsAvailable(&arg2));

    // Caso inválido: sem path do diretório de saída 
    arglocal arg3 = {"input", "", "report", 4, 10};
    TEST_ASSERT_EQUAL_INT(-1, validateAllArgumentsAvailable(&arg3));

    // Caso inválido: sem path do diretório de relatório
    arglocal arg4 = {"input", "output", "", 4, 10};
    TEST_ASSERT_EQUAL_INT(-1, validateAllArgumentsAvailable(&arg4));

    // Caso inválido: número de workers é zero
    arglocal arg5 = {"input", "output", "report", 0, 10};
    TEST_ASSERT_EQUAL_INT(-1, validateAllArgumentsAvailable(&arg5));

    // Caso inválido: intervalo de tempo é negativo
    arglocal arg6 = {"input", "output", "report", 4, -10};
    TEST_ASSERT_EQUAL_INT(-1, validateAllArgumentsAvailable(&arg6));
}


void test_getApplicationDetails() {
    // Copia um ficheiro para o diretorio");
    //system("cp ./4-candidate-data.txt ../test-candidate-data.txt" );
    //Cria o diretorio
    system("mkdir input");
    //Cria o ficheiro candidate-data
    FILE *file = fopen("./input/test-candidate-data.txt", "w");
    fprintf(file, "Isep2024\n");
    fprintf(file, "aluno.scomp@isep.ipp.pt\n");
    fprintf(file, "aluno doe\n");
    fprintf(file, "961234567\n");
    fclose(file);

    // Variáveis de saída
    char jobReference[250] = "";
    char jobApplicant[250] = "";

    // Chama a função 
    int result = getApplicationDetails("test", jobReference, jobApplicant);

    // Verificar resultados
    TEST_ASSERT_EQUAL_INT(0, result);
    TEST_ASSERT_EQUAL_STRING("Isep2024", jobReference);
    TEST_ASSERT_EQUAL_STRING("aluno.scomp@isep.ipp.pt", jobApplicant);

    // Eliminar o diretorio
    system("rm -rf ./input/test-candidate-data.txt");
}
void test_createDirectory() {
    // diretorio a criar
    char newDirectoryPath[250] = "test_directory";

    // Chama a função
    int result = createDirectory(newDirectoryPath);

    // Ver se o diretório foi criado corretamente
    TEST_ASSERT_EQUAL_INT(0, result);
    //Ver se o diretório foi criado realmente
    /*DIR *dir = opendir(newDirectoryPath);
    TEST_ASSERT_NOT_NULL(dir);
    closedir(dir);*/
    //apagar o diretório de teste
    rmdir(newDirectoryPath);
}
void test_countFilesOnDirectory() {
    // Criar diretório de teste e ficheiros
    system("mkdir test_directory");
    system("touch test_directory/prefix1_file1.txt");
    system("touch test_directory/prefix1_file2.txt");
    system("touch test_directory/prefix2_file3.txt");

    // Chama a função
    int result = countFilesOnDirectory("test_directory", "prefix1");

    // Testar
    TEST_ASSERT_EQUAL_INT(2, result);

    // Eliminar diretorio testado
    system("rm -rf test_directory");
}
void test_moveFilesToDirectory() {
    // Criar diretório de teste e ficheiros
    system("mkdir input");
    system("touch input/prefix1-file1.txt");
    system("touch input/prefix1-file2.txt");
    system("touch input/prefix2-file3.txt");

    // Criar diretório de destino de teste
    system("mkdir destiny");

    // Chamaa função 
    int result = moveFilesToDirectory("input", "destiny", "prefix1");

    // Testar
    TEST_ASSERT_EQUAL_INT(0, result);

    // Verificar se os ficheiros foram movidos corretamente
    FILE *file1 = fopen("destiny/prefix1-file1.txt", "r");
    FILE *file2 = fopen("destiny/prefix1-file2.txt", "r");
    TEST_ASSERT_NOT_NULL(file1);
    TEST_ASSERT_NOT_NULL(file2);
    fclose(file1);
    fclose(file2);

    // Elimimar diretórios e ficheiros
    system("rm -rf input destiny");
}

void sigusr1_handler(int signum) {
    puts("Pai recebeu sinal SIGUSR1\n");
}

void test_monitor_files() {
    // Cria um diretório de teste
    system("mkdir test_directory");
    FILE *file = fopen("test_directory/test-file.txt", "w");
    fclose(file);

    // Chama a função com o diretório de teste
    monitor_files("test_directory", 1);

    // Verifica se o sinal SIGUSR1 foi enviado corretamente
    struct sigaction actiontest;
    actiontest.sa_handler = sigusr1_handler;
    sigemptyset(&actiontest.sa_mask);
    actiontest.sa_flags = 0;
    sigaction(SIGUSR1, &actiontest, NULL);
    sleep(2); // Espera tempo suficiente para a execução da função
}
void test_createSessionFile() {
    // ficheiro  de teste
    char sessionFile[250] = "test_session_file.txt";

    // Chama a função 
    int result = createSessionFile(sessionFile);

    // Verifica se o ficheiro criado corretamente
    TEST_ASSERT_EQUAL_INT(0, result);

    // Verifica se o ficheiro de sessão foi realmente criado
    FILE *file = fopen(sessionFile, "r");
    TEST_ASSERT_NOT_NULL(file);
    fclose(file);

    // apaga
    remove(sessionFile);
}
void test_updateSessionFile() {
    // ficheiro  de teste
    char sessionFile[250] = "test_session_file.txt";

    // Definindo um relatório de criança de teste
    childReport report;
    strcpy(report.createdPath, "ABC");
    report.qtyFilesMoved = 2;
    strcpy(report.filesMoved, "- FILENAME1\n- FILENAME2\n");

    // Chamando a função a ser testada para atualizar o arquivo de sessão
    int result = updateSessionFile(sessionFile, &report);

    // Verificando se o arquivo de sessão foi atualizado corretamente
    TEST_ASSERT_EQUAL_INT(0, result);

    // Verificando se o conteúdo foi adicionado corretamente ao arquivo de sessão
    FILE *file = fopen(sessionFile, "r");
    TEST_ASSERT_NOT_NULL(file);
    char buffer[1024];
    fread(buffer, sizeof(char), sizeof(buffer), file);
    fclose(file);
    TEST_ASSERT_EQUAL_STRING("#subdir: ABC (qty files moved: 2)\n- FILENAME1\n- FILENAME2\n\n", buffer);

    // Removendo o arquivo de sessão de teste
    remove(sessionFile);
}

void test_getFilesOnDirectory() {
    // Criar diretório de teste e ficheiros
    system("mkdir input");
    system("touch input/prefix1-file1.txt");
    system("touch input/prefix1-file2.txt");
    system("touch input/prefix2-file3.txt");

    // Cria array para o relatório de ficheiros movidos
    char reportFilesMoved[500] = "";

    // Chama a função a ser testada
    int result = getFilesOnDirectory("input", "prefix1", reportFilesMoved);

    // Testar
    TEST_ASSERT_EQUAL_INT(2, result);
    //TEST_ASSERT_EQUAL_STRING("prefix1-file1.txt\prefix1-file2.txt\n", reportFilesMoved);

    // Elimimar diretórios e ficheiros
    system("rm -rf input");
}

void setUp(void) {
    // set stuff up here
}

void tearDown(void) {
    // clean stuff up here
}

int main() {
    UNITY_BEGIN();
    RUN_TEST(test_countChars);
    //RUN_TEST(test_findNewPrefix);
    RUN_TEST(test_compareFileNames);
    RUN_TEST(test_getDirFileNames);
    RUN_TEST(test_extractArguments);
    RUN_TEST(test_validateAllArgumentsAvailable);
    RUN_TEST(test_getApplicationDetails);
    RUN_TEST(test_createDirectory);
    RUN_TEST(test_countFilesOnDirectory);
    RUN_TEST(test_moveFilesToDirectory);
    RUN_TEST(test_createSessionFile);
    RUN_TEST(test_updateSessionFile);
    RUN_TEST(test_getFilesOnDirectory);
	RUN_TEST(test_cria_filhos);
	//RUN_TEST(test_monitor_files);
   
    return UNITY_END();
}
