#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "unity.h"

#include "../functions.h"

#define MAX_FILES 100 // Número máximo de ficheiros para o teste





void test_countChars() {
    TEST_ASSERT_EQUAL_INT(5, countChars("TESTE"));
    TEST_ASSERT_EQUAL_INT(0, countChars(""));
    TEST_ASSERT_EQUAL_INT(5, countChars("12345"));
    //TEST_ASSERT_EQUAL_INT(17, countChars("Teste Com espaços"));
}

void test_cria_filhos(){
	
}

void test_findNewPrefix(){
	
}

void test_compareFileNames() {
    const char *names[] = {"file1.txt", "file3.txt", "file2.txt"};
    
    TEST_ASSERT_EQUAL_STRING("file1.txt", names[0]);
    TEST_ASSERT_EQUAL_STRING("file2.txt", names[2]);
    TEST_ASSERT_EQUAL_STRING("file3.txt", names[1]);
    qsort(names, sizeof(names) / sizeof(names[0]), sizeof(names[0]), compareFileNames);
    TEST_ASSERT_EQUAL_STRING("file2.txt", names[1]);
    TEST_ASSERT_EQUAL_STRING("file3.txt", names[2]);
}

void test_getDirFileNames() {
    // variáveis para os testes
    char* fileNames[MAX_FILES] = {0};
    char* inputPath = "../Exemplo"; // Diretório atual

    // Chama a função a ser testada
    int fileCount = getDirFileNames(inputPath, fileNames);

    // Verifica os resultados
    TEST_ASSERT_GREATER_OR_EQUAL_INT(0, fileCount); // Verifica se o número de arquivos é maior ou igual a zero

    if (fileCount > 0) {
        // Verifica se os ficheiros estão ordenados corretamente
        for (int i = 1; i < fileCount; ++i) {
            TEST_ASSERT_TRUE(strcmp(fileNames[i-1], fileNames[i]) <= 0);
        }
    }

    // Liberta a memória alocada
    for (int i = 0; i < fileCount; ++i) {
        free(fileNames[i]);
    }
}

void test_extractArguments() {
    
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
    system("mkdir test_input_directory");
    system("touch test_input_directory/prefix1_file1.txt");
    system("touch test_input_directory/prefix1_file2.txt");
    system("touch test_input_directory/prefix2_file3.txt");

    // Criar diretório de destino de teste
    system("mkdir test_job_applicant_directory");

    // Chamaa função 
    int result = moveFilesToDirectory("test_input_directory", "test_job_applicant_directory", "prefix1");

    // Testar
    TEST_ASSERT_EQUAL_INT(0, result);

    // Verificar se os ficheiros foram movidos corretamente
    FILE *file1 = fopen("test_job_applicant_directory/prefix1_file1.txt", "r");
    FILE *file2 = fopen("test_job_applicant_directory/prefix1_file2.txt", "r");
    TEST_ASSERT_NOT_NULL(file1);
    TEST_ASSERT_NOT_NULL(file2);
    fclose(file1);
    fclose(file2);

    // Elimimar diretórios e ficheiros
    system("rm -rf test_input_directory test_job_applicant_directory");
}

void test_monitor_files(){
}

void test_createSessionFile(){
	
}

void test_updateSessionFile(){
	
}

void test_getFilesOnDirectory() {
    // Criar diretório de teste e ficheiros
    system("mkdir test_input_directory");
    system("touch test_input_directory/prefix1_file1.txt");
    system("touch test_input_directory/prefix1_file2.txt");
    system("touch test_input_directory/prefix2_file3.txt");

    // Cria array para o relatório de ficheiros movidos
    char reportFilesMoved[500] = "";

    // Chama a função a ser testada
    int result = getFilesOnDirectory("test_input_directory", "prefix1", reportFilesMoved);

    // Testar
    TEST_ASSERT_EQUAL_INT(2, result);
    TEST_ASSERT_EQUAL_STRING("prefix1_file1.txt\nprefix1_file2.txt\n", reportFilesMoved);

    // Elimimar diretórios e ficheiros
    system("rm -rf test_input_directory");
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
    RUN_TEST(test_compareFileNames);
    RUN_TEST(test_getDirFileNames);
    //RUN_TEST(test_extractArguments);
    RUN_TEST(test_countFilesOnDirectory);
    //RUN_TEST(test_moveFilesToDirectory);
    RUN_TEST(test_getFilesOnDirectory);
    return UNITY_END();
}
