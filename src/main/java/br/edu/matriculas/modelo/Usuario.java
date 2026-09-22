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
        // TODO: implementar validação de senha
        throw new UnsupportedOperationException("TODO");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
