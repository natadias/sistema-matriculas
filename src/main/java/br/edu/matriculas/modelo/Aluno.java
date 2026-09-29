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
        List<Matricula> ativas = new ArrayList<>();
        for (Matricula matricula : matriculas) {
            if (matricula.getStatus() == StatusMatricula.ATIVA) {
                ativas.add(matricula);
            }
        }
        return ativas;
    }

    public String getMatricula() {
        return matricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    @Override
    public String toString() {
        return matricula + " - " + nome + " (" + curso.getNome() + ")";
    }
}
