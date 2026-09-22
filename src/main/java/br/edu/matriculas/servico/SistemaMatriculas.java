package br.edu.matriculas.servico;

import java.util.ArrayList;
import java.util.List;

import br.edu.matriculas.modelo.Aluno;
import br.edu.matriculas.modelo.Curriculo;
import br.edu.matriculas.modelo.Curso;
import br.edu.matriculas.modelo.Disciplina;
import br.edu.matriculas.modelo.FuncionarioSecretaria;
import br.edu.matriculas.modelo.Matricula;
import br.edu.matriculas.modelo.PeriodoMatricula;
import br.edu.matriculas.modelo.Professor;
import br.edu.matriculas.modelo.TipoMatricula;
import br.edu.matriculas.modelo.Usuario;

public class SistemaMatriculas {

    private List<Aluno> alunos = new ArrayList<>();
    private List<Professor> professores = new ArrayList<>();
    private List<FuncionarioSecretaria> funcionarios = new ArrayList<>();
    private List<Curso> cursos = new ArrayList<>();
    private List<Disciplina> disciplinas = new ArrayList<>();
    private List<Curriculo> curriculos = new ArrayList<>();
    private SistemaCobrancas sistemaCobrancas;

    public SistemaMatriculas(SistemaCobrancas sistemaCobrancas) {
        this.sistemaCobrancas = sistemaCobrancas;
    }

    /** US01 - Login. */
    public Usuario autenticar(String login, String senha) {
        // TODO: localizar usuário por login e validar senha
        throw new UnsupportedOperationException("TODO");
    }

    /** US02 - Consultar disciplinas ofertadas no currículo de um semestre. */
    public List<Disciplina> consultarDisciplinasOfertadas(String semestre) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** US03/US04 - Matricular aluno em disciplina obrigatória ou optativa. */
    public Matricula matricular(Aluno aluno, Disciplina disciplina, TipoMatricula tipo) {
        // TODO: validar período aberto, limites (4 obrigatórias / 2 optativas), vagas (60) e
        // notificar sistemaCobrancas.notificarMatricula(...)
        throw new UnsupportedOperationException("TODO");
    }

    /** US05 - Cancelar matrícula em disciplina. */
    public void cancelarMatricula(Aluno aluno, Disciplina disciplina) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** US06 - Consultar matrículas realizadas por um aluno. */
    public List<Matricula> consultarMatriculas(Aluno aluno) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** US07 - Consultar alunos matriculados em uma disciplina. */
    public List<Aluno> consultarAlunosMatriculados(Disciplina disciplina) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** US09 - Cadastrar curso. */
    public void cadastrarCurso(Curso curso) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** US10 - Cadastrar disciplina. */
    public void cadastrarDisciplina(Disciplina disciplina) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** US11 - Cadastrar professor. */
    public void cadastrarProfessor(Professor professor) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** US12 - Cadastrar aluno. */
    public void cadastrarAluno(Aluno aluno) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** US08 - Definir currículo de um semestre. */
    public void definirCurriculo(Curriculo curriculo) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** US13 - Abrir período de matrículas. */
    public void abrirPeriodoMatricula(PeriodoMatricula periodo) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** US13 - Encerrar período de matrículas (dispara o processamento de encerramento, US14). */
    public void encerrarPeriodoMatricula(PeriodoMatricula periodo) {
        // TODO: encerrar e chamar processarEncerramentoPeriodo(periodo)
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * US14 - Ativa disciplinas com pelo menos MINIMO_PARA_ATIVAR matriculados e cancela as demais.
     */
    private void processarEncerramentoPeriodo(PeriodoMatricula periodo) {
        // TODO: percorrer disciplinas do período e aplicar a regra de ativação/cancelamento
        // (Disciplina.MINIMO_PARA_ATIVAR)
        throw new UnsupportedOperationException("TODO");
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public List<FuncionarioSecretaria> getFuncionarios() {
        return funcionarios;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public List<Curriculo> getCurriculos() {
        return curriculos;
    }
}
