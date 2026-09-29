package br.edu.matriculas.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import br.edu.matriculas.modelo.Aluno;
import br.edu.matriculas.modelo.Curriculo;
import br.edu.matriculas.modelo.Curso;
import br.edu.matriculas.modelo.Disciplina;
import br.edu.matriculas.modelo.FuncionarioSecretaria;
import br.edu.matriculas.modelo.PeriodoMatricula;
import br.edu.matriculas.modelo.Professor;
import br.edu.matriculas.persistencia.RepositorioDados;
import br.edu.matriculas.servico.SistemaMatriculas;

class TelaSecretaria extends JPanel {

    private final SistemaMatriculas sistema;
    private final RepositorioDados repositorio;
    private final JPanel cabecalho = Estilos.cabecalho("Secretaria");

    TelaSecretaria(JanelaPrincipal janela, SistemaMatriculas sistema, RepositorioDados repositorio) {
        this.sistema = sistema;
        this.repositorio = repositorio;

        setLayout(new BorderLayout());
        setBackground(Estilos.FUNDO);
        add(cabecalho, BorderLayout.NORTH);

        JPanel conteudo = Estilos.painelConteudo();
        conteudo.setLayout(new GridLayout(0, 2, 14, 14));
        adicionar(conteudo, "Cadastrar Curso", e -> cadastrarCurso());
        adicionar(conteudo, "Cadastrar Professor", e -> cadastrarProfessor());
        adicionar(conteudo, "Cadastrar Aluno", e -> cadastrarAluno());
        adicionar(conteudo, "Cadastrar Disciplina", e -> cadastrarDisciplina());
        adicionar(conteudo, "Cadastrar Funcionário", e -> cadastrarFuncionario());
        adicionar(conteudo, "Definir Currículo do Semestre", e -> definirCurriculo());
        adicionar(conteudo, "Abrir Período de Matrículas", e -> abrirPeriodoMatricula());
        adicionar(conteudo, "Encerrar Período de Matrículas", e -> encerrarPeriodoMatricula());
        adicionar(conteudo, "Listar Cadastros", e -> listarCadastros());
        add(conteudo, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        rodape.setBackground(Estilos.FUNDO);
        rodape.setBorder(new EmptyBorder(0, 0, 16, 0));
        JButton sair = Estilos.botaoSecundario("Sair");
        sair.addActionListener(e -> janela.mostrarLogin());
        rodape.add(sair);
        add(rodape, BorderLayout.SOUTH);
    }

    private void adicionar(JPanel painel, String texto, java.awt.event.ActionListener acao) {
        JButton botao = Estilos.botaoPrimario(texto);
        botao.addActionListener(acao);
        painel.add(botao);
    }

    void exibirPara(FuncionarioSecretaria funcionario) {
        Estilos.atualizarCabecalho(cabecalho, "Secretaria — " + funcionario.getNome());
    }

    private void cadastrarCurso() {
        String nome = pedirTexto("Nome do curso:");
        if (nome == null) {
            return;
        }
        Integer creditos = pedirInteiro("Número de créditos:");
        if (creditos == null) {
            return;
        }
        sistema.cadastrarCurso(new Curso(nome, creditos));
        repositorio.salvar(sistema);
        JOptionPane.showMessageDialog(this, "Curso cadastrado.");
    }

    private void cadastrarProfessor() {
        String nome = pedirTexto("Nome do professor:");
        if (nome == null) {
            return;
        }
        String login = pedirTexto("Login:");
        if (login == null) {
            return;
        }
        String senha = pedirTexto("Senha:");
        if (senha == null) {
            return;
        }
        String registro = pedirTexto("Registro funcional:");
        if (registro == null) {
            return;
        }
        sistema.cadastrarProfessor(new Professor(nome, login, senha, registro));
        repositorio.salvar(sistema);
        JOptionPane.showMessageDialog(this, "Professor cadastrado.");
    }

    private void cadastrarAluno() {
        Curso curso = pedirSelecao("Curso do aluno:", sistema.getCursos());
        if (curso == null) {
            return;
        }
        String nome = pedirTexto("Nome do aluno:");
        if (nome == null) {
            return;
        }
        String login = pedirTexto("Login:");
        if (login == null) {
            return;
        }
        String senha = pedirTexto("Senha:");
        if (senha == null) {
            return;
        }
        String matricula = pedirTexto("Matrícula:");
        if (matricula == null) {
            return;
        }
        sistema.cadastrarAluno(new Aluno(nome, login, senha, matricula, curso));
        repositorio.salvar(sistema);
        JOptionPane.showMessageDialog(this, "Aluno cadastrado.");
    }

    private void cadastrarDisciplina() {
        Curso curso = pedirSelecao("Curso da disciplina:", sistema.getCursos());
        if (curso == null) {
            return;
        }
        Professor professor = pedirSelecao("Professor responsável:", sistema.getProfessores());
        if (professor == null) {
            return;
        }
        String codigo = pedirTexto("Código da disciplina:");
        if (codigo == null) {
            return;
        }
        String nome = pedirTexto("Nome da disciplina:");
        if (nome == null) {
            return;
        }
        Integer creditos = pedirInteiro("Número de créditos:");
        if (creditos == null) {
            return;
        }
        sistema.cadastrarDisciplina(new Disciplina(codigo, nome, creditos, curso, professor));
        repositorio.salvar(sistema);
        JOptionPane.showMessageDialog(this, "Disciplina cadastrada.");
    }

    private void cadastrarFuncionario() {
        String nome = pedirTexto("Nome do funcionário:");
        if (nome == null) {
            return;
        }
        String login = pedirTexto("Login:");
        if (login == null) {
            return;
        }
        String senha = pedirTexto("Senha:");
        if (senha == null) {
            return;
        }
        String registro = pedirTexto("Registro:");
        if (registro == null) {
            return;
        }
        sistema.cadastrarFuncionario(new FuncionarioSecretaria(nome, login, senha, registro));
        repositorio.salvar(sistema);
        JOptionPane.showMessageDialog(this, "Funcionário cadastrado.");
    }

    private void definirCurriculo() {
        Curso curso = pedirSelecao("Curso do currículo:", sistema.getCursos());
        if (curso == null) {
            return;
        }
        String semestre = pedirTexto("Semestre (ex.: 2026.2):");
        if (semestre == null) {
            return;
        }
        if (sistema.getDisciplinas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre ao menos uma disciplina primeiro.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        JList<Disciplina> lista = new JList<>(sistema.getDisciplinas().toArray(new Disciplina[0]));
        Estilos.estilizarLista(lista);
        lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scroll = Estilos.comBorda(lista);
        scroll.setPreferredSize(new java.awt.Dimension(360, 220));
        int resultado = JOptionPane.showConfirmDialog(this, scroll,
                "Selecione as disciplinas ofertadas em " + semestre, JOptionPane.OK_CANCEL_OPTION);
        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }
        Curriculo curriculo = new Curriculo(semestre, curso);
        curriculo.getDisciplinas().addAll(lista.getSelectedValuesList());
        sistema.definirCurriculo(curriculo);
        repositorio.salvar(sistema);
        JOptionPane.showMessageDialog(this,
                "Currículo de " + semestre + " definido com " + curriculo.getDisciplinas().size() + " disciplina(s).");
    }

    private void abrirPeriodoMatricula() {
        String semestre = pedirTexto("Semestre (ex.: 2026.2):");
        if (semestre == null) {
            return;
        }
        Integer dias = pedirInteiro("Duração do período em dias:");
        if (dias == null) {
            return;
        }
        PeriodoMatricula periodo = new PeriodoMatricula(semestre, LocalDate.now(), LocalDate.now().plusDays(dias));
        sistema.abrirPeriodoMatricula(periodo);
        repositorio.salvar(sistema);
        JOptionPane.showMessageDialog(this, "Período de " + semestre + " aberto até " + periodo.getDataFim() + ".");
    }

    private void encerrarPeriodoMatricula() {
        PeriodoMatricula periodo = sistema.getPeriodoAtual();
        if (periodo == null || !periodo.isAberto()) {
            JOptionPane.showMessageDialog(this, "Não há período de matrículas aberto.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        sistema.encerrarPeriodoMatricula(periodo);
        repositorio.salvar(sistema);
        StringBuilder texto = new StringBuilder("Período de " + periodo.getSemestre() + " encerrado.\n\n");
        sistema.consultarDisciplinasOfertadas(periodo.getSemestre()).forEach(d -> texto.append(d).append("\n"));
        mostrarTexto("Encerramento processado", texto.toString());
    }

    private void listarCadastros() {
        StringBuilder texto = new StringBuilder();
        texto.append("Cursos:\n");
        sistema.getCursos().forEach(c -> texto.append("  ").append(c).append("\n"));
        texto.append("\nDisciplinas:\n");
        sistema.getDisciplinas().forEach(d -> texto.append("  ").append(d).append("\n"));
        texto.append("\nProfessores:\n");
        sistema.getProfessores().forEach(p -> texto.append("  ").append(p).append("\n"));
        texto.append("\nAlunos:\n");
        sistema.getAlunos().forEach(a -> texto.append("  ").append(a).append("\n"));
        mostrarTexto("Cadastros", texto.toString());
    }

    private void mostrarTexto(String titulo, String texto) {
        JTextArea area = new JTextArea(texto, 18, 46);
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setBackground(Estilos.FUNDO_CARTAO);
        area.setBorder(new EmptyBorder(8, 10, 8, 10));
        JOptionPane.showMessageDialog(this, Estilos.comBorda(area), titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private String pedirTexto(String mensagem) {
        String valor = JOptionPane.showInputDialog(this, mensagem);
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }

    private Integer pedirInteiro(String mensagem) {
        while (true) {
            String valor = JOptionPane.showInputDialog(this, mensagem);
            if (valor == null) {
                return null;
            }
            try {
                return Integer.parseInt(valor.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private <T> T pedirSelecao(String mensagem, List<T> opcoes) {
        if (opcoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cadastro disponível. Cadastre um primeiro.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
        @SuppressWarnings("unchecked")
        T selecionado = (T) JOptionPane.showInputDialog(this, mensagem, "Seleção", JOptionPane.QUESTION_MESSAGE, null,
                opcoes.toArray(), opcoes.get(0));
        return selecionado;
    }
}
