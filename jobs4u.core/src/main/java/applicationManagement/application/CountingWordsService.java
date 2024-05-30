package applicationManagement.application;

import applicationManagement.domain.dto.WordCount;

import java.util.HashMap;

public class CountingWordsService {
    public static HashMap<String, WordCount> countWordsOnFiles(String path) {

        HashMap<String, WordCount> map = new HashMap<>();

        // TODO: JORGE
        // Com recurso ao path, identifica a lista de ficheiros disponível.
        // Atribui um ficheiro a uma thread.
        // Abre ficheiro e fecha ficheiro
        // Aguarda que terminem todas as threads geradas

        // TODO: RAFAEL
        // Com recurso ao ficheiro aberto, atribui uma thread para um x nr de linhas.
        // Processo linhas para adicionar novas palavras ou atualizar a contagem de palavras já existentes no map.
        // Garantir exclusão mutua no acesso ao map.

        return map;
    }

    // IMPLEMENTAR INFRA OS MÉTODOS QUE DEVERÃO SER synchronized
}
