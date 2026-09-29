package br.edu.matriculas.servico;

import br.edu.matriculas.modelo.Aluno;
import br.edu.matriculas.modelo.Disciplina;

public class SistemaCobrancasImpl implements SistemaCobrancas {

    @Override
    public void notificarMatricula(Aluno aluno, Disciplina disciplina) {
        System.out.println("[Sistema de Cobranças] " + aluno.getNome() + " (" + aluno.getMatricula()
                + ") será cobrado pela disciplina " + disciplina.getCodigo() + " - " + disciplina.getNome());
    }
}
