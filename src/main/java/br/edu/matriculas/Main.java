package br.edu.matriculas;

import br.edu.matriculas.servico.SistemaCobrancasImpl;
import br.edu.matriculas.servico.SistemaMatriculas;

public class Main {

    public static void main(String[] args) {
        SistemaMatriculas sistemaMatriculas = new SistemaMatriculas(new SistemaCobrancasImpl());
        // TODO: implementar o menu de linha de comando
    }
}
