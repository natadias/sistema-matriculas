package br.edu.matriculas.ui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.edu.matriculas.modelo.Aluno;
import br.edu.matriculas.modelo.FuncionarioSecretaria;
import br.edu.matriculas.modelo.Professor;
import br.edu.matriculas.modelo.Usuario;
import br.edu.matriculas.persistencia.RepositorioDados;
import br.edu.matriculas.servico.SistemaMatriculas;

public class JanelaPrincipal extends JFrame {

    private static final String TELA_LOGIN = "login";
    private static final String TELA_ALUNO = "aluno";
    private static final String TELA_PROFESSOR = "professor";
    private static final String TELA_SECRETARIA = "secretaria";

    private final SistemaMatriculas sistema;
    private final RepositorioDados repositorio;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel telas = new JPanel(cardLayout);

    private final TelaLogin telaLogin;
    private final TelaAluno telaAluno;
    private final TelaProfessor telaProfessor;
    private final TelaSecretaria telaSecretaria;

    public JanelaPrincipal(SistemaMatriculas sistema, RepositorioDados repositorio) {
        super("Sistema de Matrículas");
        this.sistema = sistema;
        this.repositorio = repositorio;

        telaLogin = new TelaLogin(this, sistema);
        telaAluno = new TelaAluno(this, sistema, repositorio);
        telaProfessor = new TelaProfessor(this, sistema);
        telaSecretaria = new TelaSecretaria(this, sistema, repositorio);

        telas.add(telaLogin, TELA_LOGIN);
        telas.add(telaAluno, TELA_ALUNO);
        telas.add(telaProfessor, TELA_PROFESSOR);
        telas.add(telaSecretaria, TELA_SECRETARIA);

        telas.setBackground(Estilos.FUNDO);
        setContentPane(telas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(760, 520));
        setSize(860, 600);
        setLocationRelativeTo(null);
    }

    public void mostrarLogin() {
        telaLogin.limpar();
        cardLayout.show(telas, TELA_LOGIN);
    }

    public void entrarComo(Usuario usuario) {
        if (usuario instanceof Aluno aluno) {
            telaAluno.exibirPara(aluno);
            cardLayout.show(telas, TELA_ALUNO);
        } else if (usuario instanceof Professor professor) {
            telaProfessor.exibirPara(professor);
            cardLayout.show(telas, TELA_PROFESSOR);
        } else if (usuario instanceof FuncionarioSecretaria funcionario) {
            telaSecretaria.exibirPara(funcionario);
            cardLayout.show(telas, TELA_SECRETARIA);
        }
    }
}
