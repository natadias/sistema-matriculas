package br.edu.matriculas.modelo;

import java.util.ArrayList;
import java.util.List;

public class Curriculo {

    private String semestre;
    private Curso curso;
    private List<Disciplina> disciplinas = new ArrayList<>();

    public Curriculo(String semestre, Curso curso) {
        this.semestre = semestre;
        this.curso = curso;
    }

    public String getSemestre() {
        return semestre;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
}
