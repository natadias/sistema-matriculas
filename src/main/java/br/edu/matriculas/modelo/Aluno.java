package br.edu.matriculas.modelo;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {

    private String matricula;
    private Curso curso;
    private List<Matricula> matriculas = new ArrayList<>();

    public Aluno(String nome, String login, String senha, String matricula, Curso curso) {
        super(nome, login, senha);
        this.matricula = matricula;
        this.curso = curso;
    }

    public List<Matricula> getMatriculasAtivas() {
        // TODO: filtrar matriculas por StatusMatricula.ATIVA
        throw new UnsupportedOperationException("TODO");
    }

    public String getMatricula() {
        return matricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }
}
