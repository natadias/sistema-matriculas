package br.edu.matriculas.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.matriculas.modelo.Usuario;
import br.edu.matriculas.servico.SistemaMatriculas;

class TelaLogin extends JPanel {

    private final JTextField campoLogin = new JTextField(16);
    private final JPasswordField campoSenha = new JPasswordField(16);
    private final JLabel mensagem = new JLabel(" ");

    TelaLogin(JanelaPrincipal janela, SistemaMatriculas sistema) {
        setLayout(new GridBagLayout());
        setBackground(Estilos.FUNDO);

        JPanel cartao = new JPanel(new GridBagLayout());
        cartao.setBackground(Estilos.FUNDO_CARTAO);
        cartao.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Estilos.BORDA, 1, true),
                new EmptyBorder(36, 44, 36, 44)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.gridwidth = 2;
        c.gridx = 0;

        JLabel icone = new JLabel("🎓", SwingConstants.CENTER);
        icone.setFont(icone.getFont().deriveFont(40f));
        c.gridy = 0;
        cartao.add(icone, c);

        JLabel titulo = Estilos.titulo("Sistema de Matrículas");
        c.gridy = 1;
        cartao.add(titulo, c);

        JLabel subtitulo = new JLabel("Entre com seu login e senha", SwingConstants.CENTER);
        subtitulo.setForeground(Estilos.TEXTO_SECUNDARIO);
        subtitulo.setFont(Estilos.FONTE_NORMAL);
        c.gridy = 2;
        c.insets = new Insets(0, 8, 24, 8);
        cartao.add(subtitulo, c);

        c.insets = new Insets(8, 8, 8, 8);
        c.gridwidth = 1;

        campoLogin.setFont(Estilos.FONTE_NORMAL);
        campoSenha.setFont(Estilos.FONTE_NORMAL);

        c.gridy = 3;
        c.gridx = 0;
        cartao.add(Estilos.rotulo("Login"), c);
        c.gridx = 1;
        cartao.add(campoLogin, c);

        c.gridy = 4;
        c.gridx = 0;
        cartao.add(Estilos.rotulo("Senha"), c);
        c.gridx = 1;
        cartao.add(campoSenha, c);

        JButton entrar = Estilos.botaoPrimario("Entrar");
        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.insets = new Insets(20, 8, 8, 8);
        cartao.add(entrar, c);

        mensagem.setForeground(new Color(0xDC, 0x26, 0x26));
        mensagem.setFont(Estilos.FONTE_NORMAL);
        mensagem.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridy = 6;
        c.insets = new Insets(4, 8, 0, 8);
        cartao.add(mensagem, c);

        add(cartao);

        Runnable tentarLogin = () -> {
            String login = campoLogin.getText().trim();
            String senha = new String(campoSenha.getPassword());
            Usuario usuario = sistema.autenticar(login, senha);
            if (usuario == null) {
                mensagem.setText("Login ou senha inválidos.");
                return;
            }
            janela.entrarComo(usuario);
        };
        entrar.addActionListener(e -> tentarLogin.run());
        campoSenha.addActionListener(e -> tentarLogin.run());
    }

    void limpar() {
        campoLogin.setText("");
        campoSenha.setText("");
        mensagem.setText(" ");
    }
}
