package br.edu.matriculas;

import java.time.LocalDate;

import javax.swing.SwingUtilities;

import br.edu.matriculas.modelo.Aluno;
import br.edu.matriculas.modelo.Curriculo;
import br.edu.matriculas.modelo.Curso;
import br.edu.matriculas.modelo.Disciplina;
import br.edu.matriculas.modelo.FuncionarioSecretaria;
import br.edu.matriculas.modelo.PeriodoMatricula;
import br.edu.matriculas.modelo.Professor;
import br.edu.matriculas.modelo.TipoMatricula;
import br.edu.matriculas.persistencia.RepositorioDados;
import br.edu.matriculas.servico.SistemaCobrancasImpl;
import br.edu.matriculas.servico.SistemaMatriculas;
import br.edu.matriculas.ui.Estilos;
import br.edu.matriculas.ui.JanelaPrincipal;

public class Main {

    public static void main(String[] args) {
        Estilos.aplicarLookAndFeel();

        RepositorioDados repositorio = new RepositorioDados("data");
        SistemaMatriculas sistema = new SistemaMatriculas(new SistemaCobrancasImpl());

        repositorio.carregar(sistema);
        semearDadosDemonstracao(sistema, repositorio);

        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal janela = new JanelaPrincipal(sistema, repositorio);
            janela.mostrarLogin();
            janela.setVisible(true);
        });
    }

    private static void semearDadosDemonstracao(SistemaMatriculas sistema, RepositorioDados repositorio) {
        if (!sistema.getFuncionarios().isEmpty() || !sistema.getCursos().isEmpty()) {
            return;
        }

        sistema.cadastrarFuncionario(new FuncionarioSecretaria("Secretaria Acadêmica", "secretaria", "123", "F001"));

        Curso computacao = new Curso("Ciência da Computação", 240);
        Curso engenhariaSoftware = new Curso("Engenharia de Software", 220);
        Curso sistemasInformacao = new Curso("Sistemas de Informação", 200);
        sistema.cadastrarCurso(computacao);
        sistema.cadastrarCurso(engenhariaSoftware);
        sistema.cadastrarCurso(sistemasInformacao);

        Professor ada = new Professor("Ada Lovelace", "ada", "123", "P001");
        Professor turing = new Professor("Alan Turing", "alan", "123", "P002");
        Professor grace = new Professor("Grace Hopper", "grace", "123", "P003");
        sistema.cadastrarProfessor(ada);
        sistema.cadastrarProfessor(turing);
        sistema.cadastrarProfessor(grace);

        Disciplina algoritmos = new Disciplina("ALG101", "Algoritmos", 4, computacao, ada);
        Disciplina bancoDeDados = new Disciplina("BD201", "Banco de Dados", 4, computacao, ada);
        Disciplina redes = new Disciplina("RED301", "Redes de Computadores", 4, computacao, turing);
        Disciplina ia = new Disciplina("IA301", "Inteligência Artificial", 4, computacao, turing);
        Disciplina engenhariaRequisitos = new Disciplina("ES101", "Engenharia de Requisitos", 4, engenhariaSoftware,
                grace);
        Disciplina arquiteturaSoftware = new Disciplina("ES201", "Arquitetura de Software", 4, engenhariaSoftware,
                grace);
        Disciplina poo = new Disciplina("POO102", "Programação Orientada a Objetos", 4, engenhariaSoftware, ada);
        Disciplina fundamentosSi = new Disciplina("SI101", "Fundamentos de Sistemas de Informação", 3,
                sistemasInformacao, turing);
        for (Disciplina disciplina : new Disciplina[] { algoritmos, bancoDeDados, redes, ia, engenhariaRequisitos,
                arquiteturaSoftware, poo, fundamentosSi }) {
            sistema.cadastrarDisciplina(disciplina);
        }

        Aluno joao = new Aluno("João Silva", "joao", "123", "2026001", computacao);
        Aluno maria = new Aluno("Maria Souza", "maria", "123", "2026002", engenhariaSoftware);
        Aluno carlos = new Aluno("Carlos Pereira", "carlos", "123", "2026003", sistemasInformacao);
        Aluno beatriz = new Aluno("Beatriz Lima", "beatriz", "123", "2026004", computacao);
        Aluno pedro = new Aluno("Pedro Santos", "pedro", "123", "2026005", engenhariaSoftware);
        for (Aluno aluno : new Aluno[] { joao, maria, carlos, beatriz, pedro }) {
            sistema.cadastrarAluno(aluno);
        }

        String semestre = "2026.2";

        Curriculo curriculoComputacao = new Curriculo(semestre, computacao);
        curriculoComputacao.getDisciplinas().add(algoritmos);
        curriculoComputacao.getDisciplinas().add(bancoDeDados);
        curriculoComputacao.getDisciplinas().add(redes);
        curriculoComputacao.getDisciplinas().add(ia);
        sistema.definirCurriculo(curriculoComputacao);

        Curriculo curriculoEngenharia = new Curriculo(semestre, engenhariaSoftware);
        curriculoEngenharia.getDisciplinas().add(engenhariaRequisitos);
        curriculoEngenharia.getDisciplinas().add(arquiteturaSoftware);
        curriculoEngenharia.getDisciplinas().add(poo);
        sistema.definirCurriculo(curriculoEngenharia);

        Curriculo curriculoSi = new Curriculo(semestre, sistemasInformacao);
        curriculoSi.getDisciplinas().add(fundamentosSi);
        sistema.definirCurriculo(curriculoSi);

        sistema.abrirPeriodoMatricula(new PeriodoMatricula(semestre, LocalDate.now(), LocalDate.now().plusDays(21)));

        sistema.matricular(joao, algoritmos, TipoMatricula.OBRIGATORIA);
        sistema.matricular(joao, bancoDeDados, TipoMatricula.OBRIGATORIA);
        sistema.matricular(beatriz, algoritmos, TipoMatricula.OBRIGATORIA);
        sistema.matricular(beatriz, ia, TipoMatricula.OPTATIVA);
        sistema.matricular(maria, engenhariaRequisitos, TipoMatricula.OBRIGATORIA);
        sistema.matricular(pedro, engenhariaRequisitos, TipoMatricula.OBRIGATORIA);
        sistema.matricular(pedro, poo, TipoMatricula.OBRIGATORIA);
        sistema.matricular(carlos, fundamentosSi, TipoMatricula.OBRIGATORIA);

        for (int i = 1; i <= 57; i++) {
            Aluno alunoTeste = new Aluno("Aluno Teste " + i, String.format("teste%02d", i), "123",
                    String.format("9000%03d", i), computacao);
            sistema.cadastrarAluno(alunoTeste);
            sistema.matricular(alunoTeste, algoritmos, TipoMatricula.OBRIGATORIA);
        }

        repositorio.salvar(sistema);
    }
}
