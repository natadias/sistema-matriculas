package br.edu.matriculas.modelo;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {

    public static final int CAPACIDADE_MAXIMA = 60;
    public static final int MINIMO_PARA_ATIVAR = 3;

    private String codigo;
    private String nome;
    private int creditos;
    private Professor professor;
    private StatusDisciplina status = StatusDisciplina.PENDENTE;
    private List<Matricula> matriculas = new ArrayList<>();

    public Disciplina(String codigo, String nome, int creditos, Professor professor) {
        this.codigo = codigo;
        this.nome = nome;
        this.creditos = creditos;
        this.professor = professor;
    }

    public boolean temVagasDisponiveis() {
        // TODO: comparar getNumeroDeMatriculados() com CAPACIDADE_MAXIMA
        throw new UnsupportedOperationException("TODO");
    }

    public int getNumeroDeMatriculados() {
        // TODO: contar matriculas com StatusMatricula.ATIVA
        throw new UnsupportedOperationException("TODO");
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getCreditos() {
        return creditos;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public StatusDisciplina getStatus() {
        return status;
    }

    public void setStatus(StatusDisciplina status) {
        this.status = status;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }
}
