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
import br.edu.matriculas.modelo.Matricula;
import br.edu.matriculas.modelo.PeriodoMatricula;
import br.edu.matriculas.modelo.TipoMatricula;
import br.edu.matriculas.persistencia.RepositorioDados;
import br.edu.matriculas.servico.SistemaMatriculas;

class TelaAluno extends JPanel {

    private final SistemaMatriculas sistema;
    private final RepositorioDados repositorio;

    private final JPanel cabecalho = Estilos.cabecalho("Aluno");
    private final DefaultListModel<Disciplina> modeloLista = new DefaultListModel<>();
    private final JList<Disciplina> listaDisciplinas = new JList<>(modeloLista);

    private Aluno aluno;

    TelaAluno(JanelaPrincipal janela, SistemaMatriculas sistema, RepositorioDados repositorio) {
        this.sistema = sistema;
        this.repositorio = repositorio;

        setLayout(new BorderLayout());
        setBackground(Estilos.FUNDO);
        add(cabecalho, BorderLayout.NORTH);

        Estilos.estilizarLista(listaDisciplinas);

        JPanel conteudo = Estilos.painelConteudo();
        conteudo.setLayout(new BorderLayout(0, 12));
        conteudo.add(Estilos.rotulo("Disciplinas ofertadas no semestre"), BorderLayout.NORTH);
        conteudo.add(Estilos.comBorda(listaDisciplinas), BorderLayout.CENTER);
        add(conteudo, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botoes.setBackground(Estilos.FUNDO);
        botoes.setBorder(new EmptyBorder(0, 0, 16, 0));

        JButton atualizar = Estilos.botaoSecundario("Atualizar");
        JButton matricularObrigatoria = Estilos.botaoPrimario("Matricular (Obrigatória)");
        JButton matricularOptativa = Estilos.botaoPrimario("Matricular (Optativa)");
        JButton cancelar = Estilos.botaoSecundario("Cancelar Matrícula");
        JButton minhasMatriculas = Estilos.botaoSecundario("Minhas Matrículas");
        JButton sair = Estilos.botaoSecundario("Sair");

        atualizar.addActionListener(e -> atualizarLista());
        matricularObrigatoria.addActionListener(e -> matricular(TipoMatricula.OBRIGATORIA));
        matricularOptativa.addActionListener(e -> matricular(TipoMatricula.OPTATIVA));
        cancelar.addActionListener(e -> cancelarMatricula());
        minhasMatriculas.addActionListener(e -> minhasMatriculas());
        sair.addActionListener(e -> janela.mostrarLogin());

        botoes.add(atualizar);
        botoes.add(matricularObrigatoria);
        botoes.add(matricularOptativa);
        botoes.add(cancelar);
        botoes.add(minhasMatriculas);
        botoes.add(sair);
        add(botoes, BorderLayout.SOUTH);
    }

    void exibirPara(Aluno aluno) {
        this.aluno = aluno;
        Estilos.atualizarCabecalho(cabecalho, "Aluno — " + aluno.getNome() + " (" + aluno.getMatricula() + ")");
        atualizarLista();
    }

    private void atualizarLista() {
        modeloLista.clear();
        PeriodoMatricula periodo = sistema.getPeriodoAtual();
        if (periodo == null) {
            JOptionPane.showMessageDialog(this, "Não há período de matrículas aberto no momento.");
            return;
        }
        List<Disciplina> disciplinas = sistema.consultarDisciplinasOfertadas(periodo.getSemestre());
        disciplinas.forEach(modeloLista::addElement);
    }

    private Disciplina disciplinaSelecionada() {
        Disciplina disciplina = listaDisciplinas.getSelectedValue();
        if (disciplina == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina na lista.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
        return disciplina;
    }

    private void matricular(TipoMatricula tipo) {
        Disciplina disciplina = disciplinaSelecionada();
        if (disciplina == null) {
            return;
        }
        try {
            sistema.matricular(aluno, disciplina, tipo);
            repositorio.salvar(sistema);
            JOptionPane.showMessageDialog(this, "Matrícula realizada em " + disciplina.getNome() + ".");
            atualizarLista();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível matricular",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarMatricula() {
        Disciplina disciplina = disciplinaSelecionada();
        if (disciplina == null) {
            return;
        }
        try {
            sistema.cancelarMatricula(aluno, disciplina);
            repositorio.salvar(sistema);
            JOptionPane.showMessageDialog(this, "Matrícula cancelada.");
            atualizarLista();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível cancelar",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void minhasMatriculas() {
        List<Matricula> matriculas = sistema.consultarMatriculas(aluno);
        if (matriculas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Você não possui matrículas ativas.");
            return;
        }
        StringBuilder texto = new StringBuilder();
        for (Matricula matricula : matriculas) {
            texto.append(matricula.getDisciplina().getCodigo()).append(" - ")
                    .append(matricula.getDisciplina().getNome()).append(" (").append(matricula.getTipo())
                    .append(")\n");
        }
        JLabel conteudo = new JLabel("<html><pre style='font-family:Segoe UI'>" + texto + "</pre></html>");
        JOptionPane.showMessageDialog(this, conteudo, "Minhas Matrículas", JOptionPane.INFORMATION_MESSAGE);
    }
}
