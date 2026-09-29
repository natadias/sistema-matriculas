package br.edu.matriculas.modelo;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private String nome;
    private int numeroCreditos;
    private List<Disciplina> disciplinas = new ArrayList<>();

    public Curso(String nome, int numeroCreditos) {
        this.nome = nome;
        this.numeroCreditos = numeroCreditos;
    }

    public String getNome() {
        return nome;
    }

    public int getNumeroCreditos() {
        return numeroCreditos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    @Override
    public String toString() {
        return nome + " (" + numeroCreditos + " créditos)";
    }
}
