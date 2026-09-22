package br.edu.matriculas.modelo;

public class FuncionarioSecretaria extends Usuario {

    private String registro;

    public FuncionarioSecretaria(String nome, String login, String senha, String registro) {
        super(nome, login, senha);
        this.registro = registro;
    }

    public String getRegistro() {
        return registro;
    }
}
