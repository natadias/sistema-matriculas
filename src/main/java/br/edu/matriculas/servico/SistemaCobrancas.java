package br.edu.matriculas.servico;

import br.edu.matriculas.modelo.Aluno;
import br.edu.matriculas.modelo.Disciplina;

public interface SistemaCobrancas {

    void notificarMatricula(Aluno aluno, Disciplina disciplina);
}
