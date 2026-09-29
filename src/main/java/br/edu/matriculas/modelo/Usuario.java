package br.edu.matriculas.modelo;

public abstract class Usuario {

    protected String nome;
    protected String login;
    protected String senha;

    protected Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public boolean autenticar(String senha) {
        return this.senha != null && this.senha.equals(senha);
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
