package br.edu.matriculas.servico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import br.edu.matriculas.modelo.Aluno;
import br.edu.matriculas.modelo.Curriculo;
import br.edu.matriculas.modelo.Curso;
import br.edu.matriculas.modelo.Disciplina;
import br.edu.matriculas.modelo.FuncionarioSecretaria;
import br.edu.matriculas.modelo.Matricula;
import br.edu.matriculas.modelo.PeriodoMatricula;
import br.edu.matriculas.modelo.Professor;
import br.edu.matriculas.modelo.StatusMatricula;
import br.edu.matriculas.modelo.TipoMatricula;
import br.edu.matriculas.modelo.Usuario;

public class SistemaMatriculas {

    public static final int MAXIMO_OBRIGATORIAS = 4;
    public static final int MAXIMO_OPTATIVAS = 2;

    private List<Aluno> alunos = new ArrayList<>();
    private List<Professor> professores = new ArrayList<>();
    private List<FuncionarioSecretaria> funcionarios = new ArrayList<>();
    private List<Curso> cursos = new ArrayList<>();
    private List<Disciplina> disciplinas = new ArrayList<>();
    private List<Curriculo> curriculos = new ArrayList<>();
    private List<PeriodoMatricula> periodos = new ArrayList<>();
    private PeriodoMatricula periodoAtual;
    private SistemaCobrancas sistemaCobrancas;

    public SistemaMatriculas(SistemaCobrancas sistemaCobrancas) {
        this.sistemaCobrancas = sistemaCobrancas;
    }

    public Usuario autenticar(String login, String senha) {
        for (Usuario usuario : todosOsUsuarios()) {
            if (usuario.getLogin().equals(login)) {
                return usuario.autenticar(senha) ? usuario : null;
            }
        }
        return null;
    }

    private List<Usuario> todosOsUsuarios() {
        List<Usuario> todos = new ArrayList<>();
        todos.addAll(alunos);
        todos.addAll(professores);
        todos.addAll(funcionarios);
        return todos;
    }

    public List<Disciplina> consultarDisciplinasOfertadas(String semestre) {
        Set<Disciplina> ofertadas = new LinkedHashSet<>();
        for (Curriculo curriculo : curriculos) {
            if (curriculo.getSemestre().equals(semestre)) {
                ofertadas.addAll(curriculo.getDisciplinas());
            }
        }
        return new ArrayList<>(ofertadas);
    }

    public Matricula matricular(Aluno aluno, Disciplina disciplina, TipoMatricula tipo) {
        if (periodoAtual == null || !periodoAtual.isAberto()) {
            throw new IllegalStateException("Não há período de matrículas aberto.");
        }
        for (Matricula matricula : aluno.getMatriculasAtivas()) {
            if (matricula.getDisciplina() == disciplina) {
                throw new IllegalStateException("Aluno já está matriculado nesta disciplina.");
            }
        }
        int matriculadasDoTipo = 0;
        for (Matricula matricula : aluno.getMatriculasAtivas()) {
            if (matricula.getTipo() == tipo) {
                matriculadasDoTipo++;
            }
        }
        int limite = tipo == TipoMatricula.OBRIGATORIA ? MAXIMO_OBRIGATORIAS : MAXIMO_OPTATIVAS;
        if (matriculadasDoTipo >= limite) {
            throw new IllegalStateException(
                    "Limite de " + limite + " disciplinas " + tipo.name().toLowerCase() + "(s) já atingido.");
        }
        if (!disciplina.temVagasDisponiveis()) {
            throw new IllegalStateException("Disciplina sem vagas disponíveis.");
        }

        Matricula matricula = new Matricula(aluno, disciplina, tipo, LocalDate.now());
        aluno.getMatriculas().add(matricula);
        disciplina.getMatriculas().add(matricula);
        sistemaCobrancas.notificarMatricula(aluno, disciplina);
        return matricula;
    }

    public void cancelarMatricula(Aluno aluno, Disciplina disciplina) {
        if (periodoAtual == null || !periodoAtual.isAberto()) {
            throw new IllegalStateException("Não há período de matrículas aberto.");
        }
        for (Matricula matricula : aluno.getMatriculasAtivas()) {
            if (matricula.getDisciplina() == disciplina) {
                matricula.cancelar();
                return;
            }
        }
        throw new IllegalStateException("Aluno não está matriculado nesta disciplina.");
    }

    public List<Matricula> consultarMatriculas(Aluno aluno) {
        return aluno.getMatriculasAtivas();
    }

    public List<Aluno> consultarAlunosMatriculados(Disciplina disciplina) {
        List<Aluno> matriculados = new ArrayList<>();
        for (Matricula matricula : disciplina.getMatriculas()) {
            if (matricula.getStatus() == StatusMatricula.ATIVA) {
                matriculados.add(matricula.getAluno());
            }
        }
        return matriculados;
    }

    public void cadastrarCurso(Curso curso) {
        cursos.add(curso);
    }

    public void cadastrarDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
        disciplina.getCurso().getDisciplinas().add(disciplina);
        disciplina.getProfessor().getDisciplinasLecionadas().add(disciplina);
    }

    public void cadastrarProfessor(Professor professor) {
        professores.add(professor);
    }

    public void cadastrarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public void cadastrarFuncionario(FuncionarioSecretaria funcionario) {
        funcionarios.add(funcionario);
    }

    public void definirCurriculo(Curriculo curriculo) {
        curriculos.add(curriculo);
    }

    public void abrirPeriodoMatricula(PeriodoMatricula periodo) {
        periodo.abrir();
        this.periodoAtual = periodo;
        if (!periodos.contains(periodo)) {
            periodos.add(periodo);
        }
    }

    public void encerrarPeriodoMatricula(PeriodoMatricula periodo) {
        periodo.encerrar();
        processarEncerramentoPeriodo(periodo);
    }

    private void processarEncerramentoPeriodo(PeriodoMatricula periodo) {
        for (Disciplina disciplina : consultarDisciplinasOfertadas(periodo.getSemestre())) {
            if (disciplina.getNumeroDeMatriculados() >= Disciplina.MINIMO_PARA_ATIVAR) {
                disciplina.ativar();
            } else {
                disciplina.cancelar();
            }
        }
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

    public PeriodoMatricula getPeriodoAtual() {
        return periodoAtual;
    }

    public List<PeriodoMatricula> getPeriodos() {
        return periodos;
    }
}
