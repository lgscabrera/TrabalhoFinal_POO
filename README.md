# Academic System

![Java](https://img.shields.io/badge/Java-25-blue) ![Maven](https://img.shields.io/badge/Maven-3.9-C71A36) ![JavaFX](https://img.shields.io/badge/JavaFX-21-orange) ![Docker](https://img.shields.io/badge/Docker-ready-2496ED) ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI/CD-2088FF) ![Status](https://img.shields.io/badge/Status-Em%20desenvolvimento-yellow)

Academic System é uma aplicação Java desenvolvida como trabalho semestral da disciplina de Orientação a Objetos, ministrada pelo prof. Rodrigo Martins Pagliares no Departamento de Ciência da Computação da Universidade Federal de Alfenas (UNIFAL-MG).

O sistema suporta gerenciamento de turmas e avaliações acadêmicas, exercitando incrementalmente conceitos de OO, engenharia de software, testes, logging, segurança, persistência, containerização, interface gráfica e CI/CD.

---

## Sumário

- [Visão Geral](#visão-geral)
- [Divisão de Responsabilidades do Grupo](#divisão-de-responsabilidades-do-grupo)
- [Objetivos do Projeto](#objetivos-do-projeto)
- [Conceitos Praticados](#conceitos-praticados)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Modelo de Segurança](#modelo-de-segurança)
- [Estrutura do Repositório](#estrutura-do-repositório)
- [Como Executar](#como-executar)
- [Modelo de Domínio](#modelo-de-domínio)
- [Evolução da Persistência](#evolução-da-persistência)
- [Funcionalidades de Relatório](#funcionalidades-de-relatório)
- [Proteção de Branch](#proteção-de-branch)
- [Histórias de Usuário](#histórias-de-usuário)
- [Melhorias Futuras](#melhorias-futuras)
- [Finalidade Educacional](#finalidade-educacional)
- [Licença](#licença)

---

## Visão Geral

O Academic System permite que professores e administradores gerenciem turmas e avaliações por meio de uma interface de linha de comando e de uma interface gráfica JavaFX.

O projeto evolui incrementalmente por meio de histórias de usuário e histórias técnicas no estilo ágil. Cada história introduz ou refina uma funcionalidade, decisão arquitetural, conceito de OO, mecanismo de persistência, preocupação de segurança, prática de testes ou capacidade de implantação.

---

## Divisão de Responsabilidades do Grupo

O projeto foi dividido em cinco responsabilidades principais, separadas por camadas arquiteturais:

### 🧑‍💻 Pessoa 1 — Core Academic & Core Architecture
**Foco:** Regras de negócio principais, modelos de domínio e estruturação dos serviços base.

- Funcionalidades acadêmicas: cadastro de turmas, gerenciamento via CLI, relatórios (US-2361, US-2363, US-2364, US-2375, US-2376)
- Arquitetura e refatoração: Singleton, Lombok, ClassService, AssessmentService, ReportService, simplificação do controlador principal (US-0000, TUS-2365, TUS-2370, TUS-2396, TUS-2397, TUS-2399, TUS-2400)
- Testes: TUS-2401, TUS-2402, TUS-2404

### 🧑‍💻 Pessoa 2 — Persistência de Dados & Logs
**Foco:** Manipulação de arquivos, padrão Strategy nos repositórios e auditoria do sistema.

- Persistência em TXT, XML e JSON; configuração dinâmica pelo Administrador (TUS-2362, US-2372, US-2373, US-2374, US-2377)
- Criação e isolamento do PersistenceService (TUS-2398)
- Configuração do SLF4J/Logback; logs de persistência e relatórios (TUS-2390, TUS-2393, TUS-2394)
- Testes: US-2389, TUS-2395, TUS-2403

### 🧑‍💻 Pessoa 3 — Segurança, Validação & Exceções
**Foco:** Proteção do sistema (RBAC), controle de sessões e consistência dos dados.

- Segurança RBAC: autenticação, autorização, logout, menus sequenciais por papel (US-2366, US-2378, US-2379, US-2380)
- Hierarquia de exceções customizadas e validação com Jakarta Bean Validation (US-2367, US-2368, US-2369, TUS-2371)
- Logs de segurança: logins, logouts e falhas de autorização (TUS-2391, TUS-2392)
- Testes: TUS-2382, TUS-2384, TUS-2385, US-2386, US-2387

### 🧑‍💻 Pessoa 4 — Interface Gráfica (JavaFX)
**Foco:** Camada de apresentação visual e integração com os controladores da aplicação.

- Infraestrutura e telas base: configuração do JavaFX no Maven, tela de Login, tela principal baseada no papel do usuário (TUS-2406, TUS-2407, TUS-2408)
- Telas de cadastro e visualização: turmas, avaliações e visualização gráfica de dados (TUS-2409, TUS-2410, TUS-2413)
- Telas administrativas: geração de relatórios e configuração de persistência (TUS-2411, TUS-2412)
- Arquitetura da GUI: AuthenticationController para desacoplar a interface das regras de serviço (TUS-2414)

### 🧑‍💻 Pessoa 5 — DevOps, CI/CD & Infraestrutura de Testes
**Foco:** Automação do ciclo de vida da aplicação, containerização e qualidade de código.

- Docker e empacotamento: Dockerfile com build Maven interno (TUS-2381)
- Infraestrutura de testes: JUnit Jupiter, Mockito (TUS-2383, TUS-2405)
- CI/CD: GitHub Actions para Java 25, builds automáticos, validação de PRs (TUS-2415, TUS-2418)
- Automação avançada: cobertura de código, publicação de imagem Docker, releases automáticos (TUS-2416, TUS-2417, TUS-2419)
- Governança: branch protection para a branch main (TUS-2420)

---

## Objetivos do Projeto

1. Complementar os conhecimentos de orientação a objetos com funcionalidades técnicas e requisitos de qualidade (GUI, segurança, persistência, logging, CI/CD).
2. Incentivar a adoção dos princípios de Engenharia de Software Aprimorada por IA (AI-Augmented Software Engineering), avaliando ganhos de produtividade com LLMs mantendo o humano no ciclo de decisão.

---

## Conceitos Praticados

- Abstração, Herança, Polimorfismo, Encapsulamento
- Associação entre objetos, identidade e igualdade com `equals`/`hashCode`
- Padrões: Singleton, Repository, Strategy
- RBAC (Role-Based Access Control), gerenciamento de sessão
- Hierarquias de exceções customizadas
- Jakarta Bean Validation
- Arquitetura em camadas (MVC-inspirada)
- Persistência: TXT, XML, JSON
- Geração de relatórios
- Menus dinâmicos por papel
- Desenvolvimento de GUI com JavaFX
- Lombok
- Testes automatizados: JUnit Jupiter, Mockito
- Logging de aplicação e auditoria
- Docker
- GitHub Actions / CI/CD

---

## Tecnologias

| Tecnologia | Finalidade |
|---|---|
| Java SE 25 | Linguagem principal |
| Maven | Gerenciamento de dependências e build |
| Lombok | Redução de código boilerplate |
| Jakarta Bean Validation | Validação declarativa do domínio |
| JUnit Jupiter | Testes unitários automatizados |
| Mockito | Framework de mocking para testes |
| SLF4J | API de logging |
| Logback | Implementação de logging |
| XML | Persistência estruturada |
| JSON | Persistência leve |
| TXT | Persistência simples em arquivo |
| JavaFX | Interface gráfica |
| Docker | Entrega containerizada |
| GitHub Actions | Automação CI/CD |

---

## Arquitetura

O sistema segue uma arquitetura em camadas que promove separação de responsabilidades, manutenibilidade e extensibilidade.

Componentes principais:

- **Domain Model Layer** — entidades e regras de negócio
- **Persistence Layer** — repositórios e estratégias de persistência
- **Security Layer** — autenticação e autorização
- **Validation Layer** — validação declarativa com Bean Validation
- **Service Layer** — ClassService, AssessmentService, PersistenceService, ReportService
- **Controller Layer** — AcademicSystemController, AuthenticationController
- **User Interface Layer** — CLI e JavaFX
- **Logging Layer** — SLF4J/Logback
- **Testing Infrastructure** — JUnit Jupiter, Mockito
- **CI/CD Automation Layer** — GitHub Actions

---

## Modelo de Segurança

O sistema implementa RBAC, autenticação baseada em sessão, verificações de autorização e auditoria via logging.

### Ciclo de vida da autenticação

```
Login → Validação de credenciais → Identificação do papel →
Criação de sessão → Menu/tela específicos do papel →
Verificações de autorização → Operações autorizadas → Logout → Encerramento da sessão
```

### Papéis suportados

| Papel | Permissões principais |
|---|---|
| ADMIN | Cadastrar turmas, salvar dados, configurar persistência, gerar relatórios administrativos |
| PROFESSOR | Cadastrar avaliações, gerar relatórios acadêmicos, visualizar dados |

### Auditoria de segurança

O sistema registra via logging: tentativas de autenticação (sucesso e falha), operações de logout, falhas de autorização e tentativas de acesso a operações protegidas.

---

## Estrutura do Repositório

```
.
├── .github
│   └── workflows
├── src
│   ├── main
│   │   ├── java
│   │   │   └── org.example.academic.system
│   │   │       ├── (raiz)              — AcademicSystem (Singleton)
│   │   │       ├── controller          — AcademicSystemController, AuthenticationController
│   │   │       ├── exception           — hierarquias de exceções customizadas
│   │   │       ├── model               — entidades de domínio
│   │   │       ├── report              — geradores de relatórios
│   │   │       ├── repository          — repositórios TXT, XML, JSON
│   │   │       ├── security            — AuthenticationService, AuthorizationService
│   │   │       ├── service             — ClassService, AssessmentService, PersistenceService, ReportService
│   │   │       ├── validation          — DomainValidator (Bean Validation)
│   │   │       └── view
│   │   │           ├── (cli)           — menus e interação via linha de comando
│   │   │           └── javafx          — JavaFXMain, controllers e telas FXML
│   │   └── resources
│   │       ├── logback.xml
│   │       └── org/example/.../view/javafx
│   │           ├── LoginScreen.fxml
│   │           ├── MainScreen.fxml
│   │           ├── ClassRegistrationScreen.fxml
│   │           ├── AssessmentRegistrationScreen.fxml
│   │           ├── ReportScreen.fxml
│   │           ├── PersistenceConfigScreen.fxml
│   │           ├── VisualizationScreen.fxml
│   │           └── styles.css
│   └── test
│       └── java
│           └── org.example.academic.system
├── logs
├── Dockerfile
├── pom.xml
└── README.md
```

---

## Como Executar

### Requisitos

- Java 25 ou superior
- Maven 3.9 ou superior
- Docker (opcional)

### Clonar o repositório

```bash
git clone https://github.com/Amoreirinha/Trabalho-Final-POO.git
cd Trabalho-Final-POO/academic-system
```

### Build do projeto

```bash
mvn clean install
```

### Executar via Maven (CLI)

```bash
mvn exec:java
```

### Executar a interface gráfica (JavaFX)

```bash
mvn javafx:run
```

### Empacotar e executar JAR

```bash
mvn clean package
java -jar target/AcademicSystem-1.0-SNAPSHOT.jar
```

### Executar com Docker

```bash
docker build -t academic-system .
docker run -it academic-system
```

---

## Modelo de Domínio

O sistema modela as principais entidades do gerenciamento acadêmico:

- **Turmas** (AcademicClass)
- **Avaliações** (Assessment): Exame, Trabalho Prático, Seminário, Atividade
- **Usuários** (User) e **Papéis** (Role)

A hierarquia de avaliações usa herança e polimorfismo. Usuários são associados a papéis que determinam as operações autorizadas. O modelo de domínio define identidade e igualdade de objetos por meio de `equals`/`hashCode` nos campos identificadores.

---

## Evolução da Persistência

| Versão | Tipo de Persistência |
|---|---|
| v1 | Somente console |
| v2 | TXT |
| v3 | XML |
| v4 | JSON |

A camada de persistência usa abstrações de repositório que desacoplam a lógica de negócio do armazenamento. As implementações TXT, XML e JSON podem ser trocadas por uma estratégia de persistência configurável.

---

## Funcionalidades de Relatório

- Relatório de resumo de turmas e avaliações
- Relatório de validação de pesos das avaliações
- Relatório de configuração de persistência

---

## Proteção de Branch

A branch `main` está protegida. Alterações devem ser submetidas via pull requests. Pushes diretos não são permitidos. Antes de mesclar, o workflow de validação do GitHub Actions deve ser concluído com sucesso. PRs com erros de compilação ou testes falhando não podem ser mesclados.

---

## Histórias de Usuário

### Funcionalidades Acadêmicas

| ID | História de Usuário | Status |
|---|---|---|
| US-2361 | Cadastrar avaliações em turmas | ✅ |
| US-2363 | Cadastrar turmas via teclado | ✅ |
| US-2364 | Gerenciar sistema via menu CLI | ✅ |
| US-2375 | Gerar relatório de resumo de turmas | ✅ |
| US-2376 | Gerar relatório de pesos das avaliações | ✅ |

### Funcionalidades de Persistência

| ID | História de Usuário | Status |
|---|---|---|
| TUS-2362 | Persistir avaliações em arquivo TXT | ✅ |
| US-2372 | Configurar tipo de persistência como administrador | ✅ |
| US-2373 | Salvar dados acadêmicos em arquivo XML | ✅ |
| US-2374 | Salvar dados acadêmicos em arquivo JSON | ✅ |
| US-2377 | Gerar relatório de configuração de persistência | ✅ |

### Funcionalidades de Segurança

| ID | História de Usuário | Status |
|---|---|---|
| US-2366 | Autenticar usuários e autorizar ações por papel | ✅ |
| US-2369 | Tratar erros de autenticação/autorização com exceções customizadas | ✅ |
| US-2378 | Renderização dinâmica de menus por papel | ✅ |
| US-2379 | Logout | ✅ |
| US-2380 | Exibir menus sequenciais por papel | ✅ |

### Validação e Tratamento de Exceções

| ID | História de Usuário | Status |
|---|---|---|
| US-2367 | Tratar erros de domínio com exceções customizadas | ✅ |
| US-2368 | Tratar erros de teclado com exceções customizadas | ✅ |
| TUS-2371 | Validar objetos de domínio com Jakarta Bean Validation | ✅ |

### Arquitetura e Refatoração

| ID | História de Usuário | Status |
|---|---|---|
| US-0000 | Iniciar sistema acadêmico | ✅ |
| TUS-2365 | Refatorar modelo de domínio com Lombok | ✅ |
| TUS-2370 | Refatorar operações de menu para AcademicSystemController | ✅ |
| TUS-2382 | Definir igualdade para objetos de domínio identificáveis | ✅ |
| TUS-2396 | Introduzir ClassService | ✅ |
| TUS-2397 | Introduzir AssessmentService | ✅ |
| TUS-2398 | Introduzir PersistenceService | ✅ |
| TUS-2399 | Introduzir ReportService | ✅ |
| TUS-2400 | Simplificar AcademicSystemController | ✅ |
| TUS-2414 | Introduzir AuthenticationController para login JavaFX | ✅ |

### Docker e Implantação

| ID | História de Usuário | Status |
|---|---|---|
| TUS-2381 | Entregar sistema acadêmico com Docker | ✅ |

### Infraestrutura de Testes e Testes Automatizados

| ID | História de Usuário | Status |
|---|---|---|
| TUS-2383 | Configurar infraestrutura de testes automatizados | ✅ |
| TUS-2384 | Testar igualdade de objetos de domínio identificáveis | ✅ |
| TUS-2385 | Testar validação de domínio acadêmico | ✅ |
| US-2386 | Testar comportamento de autenticação | ✅ |
| US-2387 | Testar comportamento de autorização | ✅ |
| US-2388 | Testar geração de relatórios | ✅ |
| US-2389 | Testar repositórios de persistência | ✅ |
| TUS-2395 | Verificar comportamento da infraestrutura de logging | ✅ |
| TUS-2401 | Testar comportamento do ClassService | ✅ |
| TUS-2402 | Testar comportamento do AssessmentService | ✅ |
| TUS-2403 | Testar comportamento do PersistenceService | ✅ |
| TUS-2404 | Testar comportamento do ReportService | ✅ |
| TUS-2405 | Testar comportamento de delegação do AcademicSystemController | ✅ |

### Logging e Auditoria

| ID | História de Usuário | Status |
|---|---|---|
| TUS-2390 | Configurar infraestrutura de logging | ✅ |
| TUS-2391 | Registrar eventos de autenticação e logout | ✅ |
| TUS-2392 | Registrar falhas de autorização | ✅ |
| TUS-2393 | Registrar operações de persistência | ✅ |
| TUS-2394 | Registrar geração de relatórios | ✅ |

### Interface Gráfica (JavaFX)

| ID | História de Usuário | Status |
|---|---|---|
| TUS-2406 | Configurar infraestrutura da aplicação JavaFX | ✅ |
| TUS-2407 | Criar tela de login JavaFX | ✅ |
| TUS-2408 | Criar tela principal baseada no papel do usuário | ✅ |
| TUS-2409 | Criar tela de cadastro de turmas JavaFX | ✅ |
| TUS-2410 | Criar tela de cadastro de avaliações JavaFX | ✅ |
| TUS-2411 | Criar tela de relatórios JavaFX | ✅ |
| TUS-2412 | Criar tela de configuração de persistência JavaFX | ✅ |
| TUS-2413 | Criar tela de visualização de turmas e avaliações JavaFX | ✅ |

### CI/CD e Automação

| ID | História de Usuário | Status |
|---|---|---|
| TUS-2415 | Configurar pipeline CI com GitHub Actions | ✅ |
| TUS-2416 | Gerar relatórios de cobertura de testes | ✅ |
| TUS-2417 | Publicar imagem Docker automaticamente | ✅ |
| TUS-2418 | Configurar workflow de validação de pull requests | ✅ |
| TUS-2419 | Configurar workflow de release | ✅ |
| TUS-2420 | Configurar proteção de branch para pull requests | ✅ |

---

## Melhorias Futuras

- API REST
- Persistência em banco de dados
- Testes de integração
- Migração para Spring Boot
- Hibernate/JPA
- Frontend web
- Gerenciamento avançado de papéis
- Gerenciamento de estudantes
- Cálculo de notas e relatórios finais
- Interface de gerenciamento de usuários
- Criptografia de senhas
- Exportação de relatórios para PDF e Excel
- Suporte a múltiplos usuários concorrentes
- Implantação em nuvem

---

## Finalidade Educacional

Este projeto foi desenvolvido para fins educacionais como suporte ao aprendizado de:

Orientação a Objetos · Engenharia de Software · Desenvolvimento Ágil · Princípios SOLID · Refatoração · Arquitetura de Software · Padrão Repository · Estratégias de Persistência · Validação · Autenticação e Autorização · Segurança · Logging e Auditoria · Testes Automatizados · Containerização · Implantação · Tratamento de Exceções · Desenvolvimento de GUI · Automação CI/CD

---

## Licença

Este repositório destina-se a uso acadêmico e educacional.
