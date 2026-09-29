package br.edu.matriculas.modelo;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {

    public static final int CAPACIDADE_MAXIMA = 60;
    public static final int MINIMO_PARA_ATIVAR = 3;

    private String codigo;
    private String nome;
    private int creditos;
    private Curso curso;
    private Professor professor;
    private StatusDisciplina status = StatusDisciplina.PENDENTE;
    private List<Matricula> matriculas = new ArrayList<>();

    public Disciplina(String codigo, String nome, int creditos, Curso curso, Professor professor) {
        this.codigo = codigo;
        this.nome = nome;
        this.creditos = creditos;
        this.curso = curso;
        this.professor = professor;
    }

    public boolean temVagasDisponiveis() {
        return getNumeroDeMatriculados() < CAPACIDADE_MAXIMA;
    }

    public int getNumeroDeMatriculados() {
        int total = 0;
        for (Matricula matricula : matriculas) {
            if (matricula.getStatus() == StatusMatricula.ATIVA) {
                total++;
            }
        }
        return total;
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

    public Curso getCurso() {
        return curso;
    }

    public Professor getProfessor() {
        return professor;
    }

    public StatusDisciplina getStatus() {
        return status;
    }

    public void ativar() {
        this.status = StatusDisciplina.ATIVA;
    }

    public void cancelar() {
        this.status = StatusDisciplina.CANCELADA;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " (" + creditos + " créditos, " + status + ", "
                + getNumeroDeMatriculados() + "/" + CAPACIDADE_MAXIMA + " matriculados)";
    }
}
