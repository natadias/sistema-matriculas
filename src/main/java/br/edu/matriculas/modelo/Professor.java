package br.edu.matriculas.modelo;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario {

    private String registroFuncional;
    private List<Disciplina> disciplinasLecionadas = new ArrayList<>();

    public Professor(String nome, String login, String senha, String registroFuncional) {
        super(nome, login, senha);
        this.registroFuncional = registroFuncional;
    }

    public String getRegistroFuncional() {
        return registroFuncional;
    }

    public List<Disciplina> getDisciplinasLecionadas() {
        return disciplinasLecionadas;
    }
}
