package br.edu.matriculas.modelo;

import java.time.LocalDate;

public class Matricula {

    private Aluno aluno;
    private Disciplina disciplina;
    private TipoMatricula tipo;
    private LocalDate dataMatricula;
    private StatusMatricula status = StatusMatricula.ATIVA;

    public Matricula(Aluno aluno, Disciplina disciplina, TipoMatricula tipo, LocalDate dataMatricula) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.tipo = tipo;
        this.dataMatricula = dataMatricula;
    }

    public void cancelar() {
        this.status = StatusMatricula.CANCELADA;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public TipoMatricula getTipo() {
        return tipo;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public StatusMatricula getStatus() {
        return status;
    }
}
