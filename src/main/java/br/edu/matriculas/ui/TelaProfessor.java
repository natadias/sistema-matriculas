package br.edu.matriculas.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.matriculas.modelo.Aluno;
import br.edu.matriculas.modelo.Disciplina;
import br.edu.matriculas.modelo.Professor;
import br.edu.matriculas.servico.SistemaMatriculas;

class TelaProfessor extends JPanel {

    private final SistemaMatriculas sistema;

    private final JPanel cabecalho = Estilos.cabecalho("Professor");
    private final DefaultListModel<Disciplina> modeloLista = new DefaultListModel<>();
    private final JList<Disciplina> listaDisciplinas = new JList<>(modeloLista);

    TelaProfessor(JanelaPrincipal janela, SistemaMatriculas sistema) {
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Estilos.FUNDO);
        add(cabecalho, BorderLayout.NORTH);

        Estilos.estilizarLista(listaDisciplinas);

        JPanel conteudo = Estilos.painelConteudo();
        conteudo.setLayout(new BorderLayout(0, 12));
        conteudo.add(Estilos.rotulo("Disciplinas que você leciona"), BorderLayout.NORTH);
        conteudo.add(Estilos.comBorda(listaDisciplinas), BorderLayout.CENTER);
        add(conteudo, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botoes.setBackground(Estilos.FUNDO);
        botoes.setBorder(new EmptyBorder(0, 0, 16, 0));

        JButton verMatriculados = Estilos.botaoPrimario("Ver Alunos Matriculados");
        JButton sair = Estilos.botaoSecundario("Sair");

        verMatriculados.addActionListener(e -> verAlunosMatriculados());
        sair.addActionListener(e -> janela.mostrarLogin());

        botoes.add(verMatriculados);
        botoes.add(sair);
        add(botoes, BorderLayout.SOUTH);
    }

    void exibirPara(Professor professor) {
        Estilos.atualizarCabecalho(cabecalho,
                "Professor — " + professor.getNome() + " (" + professor.getRegistroFuncional() + ")");
        modeloLista.clear();
        professor.getDisciplinasLecionadas().forEach(modeloLista::addElement);
    }

    private void verAlunosMatriculados() {
        Disciplina disciplina = listaDisciplinas.getSelectedValue();
        if (disciplina == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina na lista.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Aluno> alunos = sistema.consultarAlunosMatriculados(disciplina);
        if (alunos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno matriculado.");
            return;
        }
        StringBuilder texto = new StringBuilder();
        alunos.forEach(a -> texto.append(a).append("\n"));
        JLabel conteudo = new JLabel("<html><pre style='font-family:Segoe UI'>" + texto + "</pre></html>");
        JOptionPane.showMessageDialog(this, conteudo, "Alunos matriculados em " + disciplina.getCodigo(),
                JOptionPane.INFORMATION_MESSAGE);
    }
}
