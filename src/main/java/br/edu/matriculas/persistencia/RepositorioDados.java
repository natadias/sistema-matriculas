package br.edu.matriculas.persistencia;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import br.edu.matriculas.modelo.Aluno;
import br.edu.matriculas.modelo.Curriculo;
import br.edu.matriculas.modelo.Curso;
import br.edu.matriculas.modelo.Disciplina;
import br.edu.matriculas.modelo.FuncionarioSecretaria;
import br.edu.matriculas.modelo.Matricula;
import br.edu.matriculas.modelo.PeriodoMatricula;
import br.edu.matriculas.modelo.Professor;
import br.edu.matriculas.modelo.StatusDisciplina;
import br.edu.matriculas.modelo.StatusMatricula;
import br.edu.matriculas.modelo.TipoMatricula;
import br.edu.matriculas.servico.SistemaMatriculas;

public class RepositorioDados {

    private final Path diretorio;

    public RepositorioDados(String diretorio) {
        this.diretorio = Path.of(diretorio);
    }

    public void salvar(SistemaMatriculas sistema) {
        salvarCursos(sistema.getCursos());
        salvarProfessores(sistema.getProfessores());
        salvarAlunos(sistema.getAlunos());
        salvarDisciplinas(sistema.getDisciplinas());
        salvarFuncionarios(sistema.getFuncionarios());
        salvarCurriculos(sistema.getCurriculos());
        salvarPeriodos(sistema.getPeriodos());
        salvarMatriculas(sistema.getDisciplinas());
    }

    public void carregar(SistemaMatriculas sistema) {
        for (Curso curso : carregarCursos()) {
            sistema.cadastrarCurso(curso);
        }
        for (Professor professor : carregarProfessores()) {
            sistema.cadastrarProfessor(professor);
        }
        for (Aluno aluno : carregarAlunos(sistema.getCursos())) {
            sistema.cadastrarAluno(aluno);
        }
        for (Disciplina disciplina : carregarDisciplinas(sistema.getCursos(), sistema.getProfessores())) {
            sistema.cadastrarDisciplina(disciplina);
        }
        for (FuncionarioSecretaria funcionario : carregarFuncionarios()) {
            sistema.cadastrarFuncionario(funcionario);
        }
        for (Curriculo curriculo : carregarCurriculos(sistema.getCursos(), sistema.getDisciplinas())) {
            sistema.definirCurriculo(curriculo);
        }
        for (PeriodoMatricula periodo : carregarPeriodos()) {
            if (periodo.isAberto()) {
                sistema.abrirPeriodoMatricula(periodo);
            } else {
                sistema.getPeriodos().add(periodo);
            }
        }
        carregarMatriculas(sistema.getAlunos(), sistema.getDisciplinas());
    }

    // ---------- Curso ----------

    private void salvarCursos(List<Curso> cursos) {
        var linhas = cursos.stream()
                .<String[]>map(c -> new String[] { c.getNome(), String.valueOf(c.getNumeroCreditos()) })
                .toList();
        ArquivoUtil.escreverLinhas(diretorio.resolve("cursos.txt"), linhas);
    }

    private List<Curso> carregarCursos() {
        return ArquivoUtil.lerLinhas(diretorio.resolve("cursos.txt")).stream()
                .map(c -> new Curso(c[0], Integer.parseInt(c[1])))
                .toList();
    }

    // ---------- Professor ----------

    private void salvarProfessores(List<Professor> professores) {
        var linhas = professores.stream()
                .<String[]>map(p -> new String[] { p.getNome(), p.getLogin(), p.getSenha(), p.getRegistroFuncional() })
                .toList();
        ArquivoUtil.escreverLinhas(diretorio.resolve("professores.txt"), linhas);
    }

    private List<Professor> carregarProfessores() {
        return ArquivoUtil.lerLinhas(diretorio.resolve("professores.txt")).stream()
                .map(p -> new Professor(p[0], p[1], p[2], p[3]))
                .toList();
    }

    // ---------- Aluno ----------

    private void salvarAlunos(List<Aluno> alunos) {
        var linhas = alunos.stream()
                .<String[]>map(a -> new String[] { a.getNome(), a.getLogin(), a.getSenha(), a.getMatricula(),
                        a.getCurso().getNome() })
                .toList();
        ArquivoUtil.escreverLinhas(diretorio.resolve("alunos.txt"), linhas);
    }

    private List<Aluno> carregarAlunos(List<Curso> cursos) {
        return ArquivoUtil.lerLinhas(diretorio.resolve("alunos.txt")).stream()
                .map(a -> new Aluno(a[0], a[1], a[2], a[3], buscarPorNome(cursos, Curso::getNome, a[4])))
                .toList();
    }

    // ---------- Disciplina ----------

    private void salvarDisciplinas(List<Disciplina> disciplinas) {
        var linhas = disciplinas.stream()
                .<String[]>map(d -> new String[] { d.getCodigo(), d.getNome(), String.valueOf(d.getCreditos()),
                        d.getCurso().getNome(), d.getProfessor().getLogin(), d.getStatus().name() })
                .toList();
        ArquivoUtil.escreverLinhas(diretorio.resolve("disciplinas.txt"), linhas);
    }

    private List<Disciplina> carregarDisciplinas(List<Curso> cursos, List<Professor> professores) {
        return ArquivoUtil.lerLinhas(diretorio.resolve("disciplinas.txt")).stream()
                .map(d -> {
                    Curso curso = buscarPorNome(cursos, Curso::getNome, d[3]);
                    Professor professor = buscarPorNome(professores, Professor::getLogin, d[4]);
                    Disciplina disciplina = new Disciplina(d[0], d[1], Integer.parseInt(d[2]), curso, professor);
                    StatusDisciplina status = StatusDisciplina.valueOf(d[5]);
                    if (status == StatusDisciplina.ATIVA) {
                        disciplina.ativar();
                    } else if (status == StatusDisciplina.CANCELADA) {
                        disciplina.cancelar();
                    }
                    return disciplina;
                })
                .toList();
    }

    // ---------- FuncionarioSecretaria ----------

    private void salvarFuncionarios(List<FuncionarioSecretaria> funcionarios) {
        var linhas = funcionarios.stream()
                .<String[]>map(f -> new String[] { f.getNome(), f.getLogin(), f.getSenha(), f.getRegistro() })
                .toList();
        ArquivoUtil.escreverLinhas(diretorio.resolve("funcionarios.txt"), linhas);
    }

    private List<FuncionarioSecretaria> carregarFuncionarios() {
        return ArquivoUtil.lerLinhas(diretorio.resolve("funcionarios.txt")).stream()
                .map(f -> new FuncionarioSecretaria(f[0], f[1], f[2], f[3]))
                .toList();
    }

    // ---------- Curriculo ----------

    private void salvarCurriculos(List<Curriculo> curriculos) {
        var linhas = curriculos.stream()
                .<String[]>map(c -> new String[] { c.getSemestre(), c.getCurso().getNome(),
                        c.getDisciplinas().stream().map(Disciplina::getCodigo).reduce((a, b) -> a + "," + b)
                                .orElse("") })
                .toList();
        ArquivoUtil.escreverLinhas(diretorio.resolve("curriculos.txt"), linhas);
    }

    private List<Curriculo> carregarCurriculos(List<Curso> cursos, List<Disciplina> disciplinas) {
        return ArquivoUtil.lerLinhas(diretorio.resolve("curriculos.txt")).stream()
                .map(c -> {
                    Curriculo curriculo = new Curriculo(c[0], buscarPorNome(cursos, Curso::getNome, c[1]));
                    if (!c[2].isBlank()) {
                        for (String codigo : c[2].split(",")) {
                            curriculo.getDisciplinas().add(buscarPorNome(disciplinas, Disciplina::getCodigo, codigo));
                        }
                    }
                    return curriculo;
                })
                .toList();
    }

    // ---------- PeriodoMatricula ----------

    private void salvarPeriodos(List<PeriodoMatricula> periodos) {
        var linhas = periodos.stream()
                .<String[]>map(p -> new String[] { p.getSemestre(), p.getDataInicio().toString(),
                        p.getDataFim().toString(), String.valueOf(p.isAberto()) })
                .toList();
        ArquivoUtil.escreverLinhas(diretorio.resolve("periodos.txt"), linhas);
    }

    private List<PeriodoMatricula> carregarPeriodos() {
        return ArquivoUtil.lerLinhas(diretorio.resolve("periodos.txt")).stream()
                .map(p -> {
                    PeriodoMatricula periodo = new PeriodoMatricula(p[0], LocalDate.parse(p[1]), LocalDate.parse(p[2]));
                    if (Boolean.parseBoolean(p[3])) {
                        periodo.abrir();
                    }
                    return periodo;
                })
                .toList();
    }

    // ---------- Matricula ----------

    private void salvarMatriculas(List<Disciplina> disciplinas) {
        var linhas = new java.util.ArrayList<String[]>();
        for (Disciplina disciplina : disciplinas) {
            for (Matricula matricula : disciplina.getMatriculas()) {
                linhas.add(new String[] { matricula.getAluno().getLogin(), matricula.getDisciplina().getCodigo(),
                        matricula.getTipo().name(), matricula.getDataMatricula().toString(),
                        matricula.getStatus().name() });
            }
        }
        ArquivoUtil.escreverLinhas(diretorio.resolve("matriculas.txt"), linhas);
    }

    private void carregarMatriculas(List<Aluno> alunos, List<Disciplina> disciplinas) {
        for (String[] m : ArquivoUtil.lerLinhas(diretorio.resolve("matriculas.txt"))) {
            Aluno aluno = buscarPorNome(alunos, Aluno::getLogin, m[0]);
            Disciplina disciplina = buscarPorNome(disciplinas, Disciplina::getCodigo, m[1]);
            Matricula matricula = new Matricula(aluno, disciplina, TipoMatricula.valueOf(m[2]), LocalDate.parse(m[3]));
            if (StatusMatricula.valueOf(m[4]) == StatusMatricula.CANCELADA) {
                matricula.cancelar();
            }
            aluno.getMatriculas().add(matricula);
            disciplina.getMatriculas().add(matricula);
        }
    }

    // ---------- utilidades ----------

    private static <T> T buscarPorNome(List<T> itens, java.util.function.Function<T, String> chave, String valor) {
        return itens.stream()
                .filter(item -> chave.apply(item).equals(valor))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Referência não encontrada: " + valor));
    }
}
