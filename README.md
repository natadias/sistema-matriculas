# Sistema de Matrículas

Sistema de informatização do processo de matrículas de uma universidade. A secretaria mantém o
currículo de cada semestre e as informações sobre cursos, disciplinas, professores e alunos; os
alunos se matriculam e cancelam matrículas em disciplinas durante os períodos de matrícula; e os
professores consultam quem está matriculado em suas disciplinas.

Projeto desenvolvido como trabalho da disciplina Laboratório de Desenvolvimento de Software.

## Regras de negócio principais

- Um aluno se matricula em até **4 disciplinas obrigatórias** (1ª opção) e até **2 disciplinas
  optativas** por semestre.
- Durante o período de matrículas, o aluno pode se matricular e também cancelar matrículas já feitas.
- Uma disciplina só fica **ativa** no semestre seguinte se, ao final do período de matrículas, tiver
  **pelo menos 3 alunos** matriculados; caso contrário, é **cancelada**.
- Uma disciplina tem no máximo **60 alunos**; ao atingir esse número, as matrículas para ela são
  encerradas automaticamente.
- Ao se matricular, o aluno gera uma notificação para o **Sistema de Cobranças**, que passa a cobrá-lo
  pelas disciplinas do semestre.
- Todo usuário (aluno, professor, secretaria) acessa o sistema mediante **login com senha**.

## Diagrama de Caso de Uso

![Diagrama de Caso de Uso do Sistema de Matrículas](docs/caso-de-uso.png)

Fonte editável (PlantUML): [`docs/caso-de-uso.puml`](docs/caso-de-uso.puml).

**Atores:** Aluno, Professor, Secretaria e Sistema de Cobranças (sistema externo, notificado após
uma matrícula).

> **Correção (revisão código↔diagramas):** adicionado o caso de uso "Cadastrar Funcionário da
> Secretaria" — o método `cadastrarFuncionario(...)` já existia na fachada `SistemaMatriculas` (e
> no diagrama de classes) para permitir a recarga de dados persistidos, mas não tinha um caso de
> uso correspondente nem opção no menu; agora a Secretaria também pode cadastrar novos funcionários
> pela CLI (US15).

## Diagrama de Classes

![Diagrama de Classes do Sistema de Matrículas](docs/diagrama-classes.png)

Fonte editável (PlantUML): [`docs/diagrama-classes.puml`](docs/diagrama-classes.puml).

O diagrama modela o pacote `modelo` (entidades de domínio — `Usuario` e suas especializações
`Aluno`, `Professor` e `FuncionarioSecretaria`, além de `Curso`, `Disciplina`, `Curriculo`,
`PeriodoMatricula` e `Matricula`), o pacote `servico`, com a fachada `SistemaMatriculas`
concentrando as operações dos casos de uso e a interface `SistemaCobrancas` representando o
sistema externo de cobranças, e o pacote `persistencia`, com `RepositorioDados` responsável por
salvar/carregar o estado do sistema em arquivo.

> **Correção:** `Disciplina` ganhou a referência que faltava para `Curso` (a relação já
> existia no diagrama — `Curso "1" *-- "*" Disciplina` — mas não estava implementada na classe);
> `SistemaMatriculas` ganhou `cadastrarFuncionario(...)`, o histórico de `periodos` e o
> `periodoAtual` usado para validar se as matrículas podem ocorrer; e foi adicionado o pacote
> `persistencia` (classe `RepositorioDados`), que não existia nos diagramas anteriores.
>
> **Correção (revisão código↔diagramas):** a multiplicidade entre `Curriculo` e `Curso` estava
> errada (`"1" -- "1"`, sugerindo um único currículo por curso) e foi corrigida para `"*" -- "1"`,
> já que a secretaria gera um currículo por semestre para o mesmo curso; removida uma relação
> duplicada entre `Disciplina` e `Matricula`; `Disciplina.setStatus(...)` e
> `PeriodoMatricula.setAberto(...)` (setters genéricos sem caso de uso correspondente) foram
> substituídos por `ativar()`/`cancelar()` e `abrir()`/`encerrar()`, alinhados aos nomes usados nos
> próprios casos de uso (UC13, UC16, UC17); e setters mortos sem nenhuma chamada no código
> (`Usuario.setNome/setLogin/setSenha`, `Aluno.setCurso`, `Disciplina.setProfessor`,
> `Curso.setNome/setNumeroCreditos`) foram removidos por não corresponderem a nenhuma
> funcionalidade modelada.

## Histórias de Usuário

### Autenticação

**US01 — Login**
> Como usuário do sistema (aluno, professor ou secretaria), eu quero fazer login com minha senha,
> para que eu possa acessar as funcionalidades específicas do meu perfil.

- [ ] O sistema valida usuário e senha antes de liberar qualquer outra funcionalidade.
- [ ] Login inválido exibe mensagem de erro e não concede acesso.

### Aluno

**US02 — Consultar disciplinas ofertadas**
> Como aluno, eu quero consultar as disciplinas ofertadas no currículo do semestre, para que eu
> possa decidir em quais me matricular.

- [ ] A lista mostra apenas disciplinas do currículo do semestre corrente.
- [ ] Cada disciplina mostra se ainda há vagas (limite de 60 matriculados).

**US03 — Matricular-se em disciplina obrigatória**
> Como aluno, eu quero me matricular em disciplinas obrigatórias como 1ª opção, para que eu cumpra
> os créditos exigidos pelo meu curso no semestre.

- [ ] O aluno pode se matricular em até 4 disciplinas obrigatórias por semestre.
- [ ] A matrícula só é aceita se o período de matrículas estiver aberto.
- [ ] A matrícula só é aceita se a disciplina ainda não atingiu 60 alunos matriculados; ao atingir
      60, a disciplina para de aceitar novas matrículas.
- [ ] Ao confirmar a matrícula, o Sistema de Cobranças é notificado para cobrar o aluno pela
      disciplina naquele semestre.

**US04 — Matricular-se em disciplina optativa**
> Como aluno, eu quero me matricular em disciplinas optativas alternativas, para que eu tenha
> flexibilidade de escolha além das obrigatórias.

- [ ] O aluno pode se matricular em até 2 disciplinas optativas por semestre.
- [ ] Aplicam-se as mesmas regras de período aberto, limite de vagas e notificação de cobrança da
      matrícula obrigatória (US03).

**US05 — Cancelar matrícula em disciplina**
> Como aluno, eu quero cancelar uma matrícula feita anteriormente, para que eu possa corrigir minha
> escolha enquanto o período de matrículas estiver aberto.

- [ ] O cancelamento só é permitido durante o período de matrículas.
- [ ] Após o cancelamento, a vaga na disciplina volta a ficar disponível para outros alunos.

**US06 — Consultar matrículas realizadas**
> Como aluno, eu quero consultar quais disciplinas estou matriculado no semestre, para que eu possa
> acompanhar minha situação antes do fechamento do período.

- [ ] A consulta mostra todas as disciplinas (obrigatórias e optativas) em que o aluno está
      matriculado no semestre corrente.

### Professor

**US07 — Consultar alunos matriculados em disciplina**
> Como professor, eu quero consultar quais alunos estão matriculados em cada uma das minhas
> disciplinas, para que eu possa me preparar para o semestre letivo.

- [ ] A lista só mostra alunos de disciplinas lecionadas pelo professor autenticado.
- [ ] A lista reflete o estado mais recente de matrículas/cancelamentos.

### Secretaria

**US08 — Gerenciar currículo do semestre**
> Como funcionário da secretaria, eu quero montar o currículo de cada semestre, para que os cursos
> tenham uma grade de disciplinas ofertadas.

- [ ] É possível associar disciplinas existentes ao currículo de um semestre específico.

**US09 — Gerenciar cursos**
> Como funcionário da secretaria, eu quero cadastrar e manter os cursos (nome e número de
> créditos), para que os alunos possam ser vinculados a eles.

- [ ] Um curso tem nome, número de créditos e é composto por várias disciplinas.

**US10 — Gerenciar disciplinas**
> Como funcionário da secretaria, eu quero cadastrar e manter as disciplinas, para que elas possam
> compor o currículo e receber matrículas.

- [ ] É possível cadastrar, editar e remover disciplinas.

**US11 — Gerenciar professores**
> Como funcionário da secretaria, eu quero cadastrar e manter os professores e as disciplinas que
> lecionam, para que o vínculo professor-disciplina esteja correto no sistema.

- [ ] É possível associar um professor a uma ou mais disciplinas.

**US12 — Gerenciar alunos**
> Como funcionário da secretaria, eu quero cadastrar e manter os alunos, para que eles possam
> acessar o sistema e se matricular em disciplinas.

- [ ] Cada aluno cadastrado recebe login e senha para acesso ao sistema.

**US13 — Definir período de matrículas**
> Como funcionário da secretaria, eu quero abrir e encerrar o período de matrículas de um
> semestre, para que os alunos só possam se matricular/cancelar matrículas dentro da janela
> definida.

- [ ] Fora do período aberto, tentativas de matrícula ou cancelamento são recusadas.

**US14 — Processar encerramento do período de matrículas**
> Como sistema, eu quero verificar automaticamente, ao final do período de matrículas, quantos
> alunos estão matriculados em cada disciplina, para que apenas disciplinas viáveis ocorram no
> semestre seguinte.

- [ ] Disciplinas com 3 ou mais alunos matriculados são marcadas como ativas.
- [ ] Disciplinas com menos de 3 alunos matriculados são canceladas.

**US15 — Cadastrar funcionário da secretaria**
> Como funcionário da secretaria, eu quero cadastrar novos funcionários da secretaria, para que
> outros colegas também possam acessar o sistema com seu próprio login.

- [ ] Cada funcionário cadastrado recebe login e senha para acesso ao sistema.

## Projeto Java

Protótipo funcional: interface gráfica + persistência em arquivos de texto.
As regras de negócio das histórias de usuário acima estão implementadas (login, limites de
matrícula, controle de vagas, ativação/cancelamento de disciplinas ao encerrar o período etc.).

```
src/main/java/br/edu/matriculas/
├── Main.java                     # ponto de entrada: monta o sistema e abre a janela principal
├── ui/                            # telas Swing: Login, Aluno, Professor e Secretaria
├── modelo/                       # entidades de domínio (Usuario, Aluno, Professor, Curso, ...)
├── servico/                      # SistemaMatriculas (fachada dos casos de uso) e SistemaCobrancas
└── persistencia/                 # RepositorioDados: leitura/escrita do estado em data/*.txt
```

A interface é uma janela única (`JanelaPrincipal`) que alterna entre telas com `CardLayout`: tela de
login e, após autenticar, a tela do perfil correspondente (Aluno, Professor ou Secretaria), cada uma
com os botões das operações daquele perfil — equivalente aos menus da versão em linha de comando,
porém gráfico.

### Como executar

```
mvn compile
mvn exec:java
```

Requisitos: Java 17+ e Maven.

Usuários de teste criados:

| Perfil     | Login        | Senha | Observação |
|------------|--------------|-------|------------|
| Secretaria | `secretaria` | `123` | |
| Professor  | `ada`        | `123` | leciona Algoritmos, Banco de Dados e POO |
| Professor  | `alan`       | `123` | leciona Redes, IA e Fundamentos de SI |
| Professor  | `grace`      | `123` | leciona Eng. de Requisitos e Arquitetura de Software |
| Aluno      | `joao`       | `123` | Ciência da Computação — já matriculado em ALG101, BD201 |
| Aluno      | `beatriz`    | `123` | Ciência da Computação — já matriculado em ALG101, IA301 |
| Aluno      | `maria`      | `123` | Engenharia de Software — já matriculada em ES101 |
| Aluno      | `pedro`      | `123` | Engenharia de Software — já matriculado em ES101, POO102 |
| Aluno      | `carlos`     | `123` | Sistemas de Informação — já matriculado em SI101 |

### Persistência

Cada tipo de entidade é gravado em um arquivo de texto delimitado por `\|` dentro de `data/`
(`cursos.txt`, `professores.txt`, `alunos.txt`, `disciplinas.txt`, `funcionarios.txt`,
`curriculos.txt`, `periodos.txt` e `matriculas.txt`). O estado é salvo automaticamente após cada
operação de cadastro, matrícula, cancelamento ou abertura/encerramento de período, e recarregado a
cada execução — a pasta `data/` não é versionada (está no `.gitignore`).

