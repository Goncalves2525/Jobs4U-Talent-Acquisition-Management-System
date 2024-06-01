package applicationManagement.application;

import applicationManagement.domain.dto.WordCount;
import applicationManagement.domain.dto.WordCountMap;

public class OrderingService {
    public WordCount[] makeTopList(int topNumber, WordCountMap map) {
        WordCount[] list = new WordCount[topNumber];

        // TODO: JORGE
        // Atribuir X nr de palavras do map a uma thread (sugestão, o mesmo nr de topNumber
            // - OU - Atribuir uma palavra de cada vez, a uma thread, até não haver mais palavras.
        // Ordenar sub-contagens, com recurso a um qualquer método de ordenação.
        // Executar ordenação final para ser retornada

        return list;
    }
}
