# ODSOFT-P1 — DevOps e Qualidade no Library Management System (Projeto 1)

## Contexto

Este repositório corresponde ao Projeto 1 da unidade curricular de **Software Development Organization** (2024/2025). O sistema de gestão de biblioteca (LMS), desenvolvido anteriormente, já expunha endpoints REST para gestão de livros, géneros, autores, leitores e empréstimos. Contudo, apresentava limitações ao nível de variabilidade, configurabilidade, fiabilidade, CI/CD e testabilidade.

## Objetivos

- **Implementar e automatizar um pipeline CI/CD com Jenkins**, incluindo deploy local e remoto (ex: vs-ctl.dei.isep.ipp.pt)
- **Automatizar tarefas de SCM, build, análise estática, testes, packaging e deploy**
- **Melhorar e evidenciar a qualidade do software** através de:
  - Testes unitários (opaque-box e transparent-box)
  - Testes de mutação (mutation tests)
  - Testes de integração (controller, serviço, domínio, repositórios, gateways)
  - Testes de aceitação
  - Cobertura de código e reporting
- **Documentar o sistema, pipeline e decisões críticas**
- **Analisar criticamente a evolução da pipeline e evidenciar melhorias ao longo do tempo**

## Abordagem e Soluções Técnicas

- **Jenkins** para orquestração do pipeline CI/CD:
  - Pipelines separadas para build local (`.jenkinsFileLocal`) e remota (`.jenkinsFile`)
  - Integração com GitHub para SCM e disparo de builds
  - Build automatizado com Maven (`pom.xml`)
  - Análise de código estático (ex: SonarQube)
  - Execução de testes unitários, de mutação, integração e aceitação
  - Geração de relatórios de cobertura e qualidade
  - Deploy automatizado local/remoto (SSH ou plugins Jenkins)
- **Testes Automatizados**:
  - Unitários (JUnit, Mockito)
  - Mutacionais (PIT)
  - Integração (Spring Boot Test)
  - Aceitação (Postman/Newman ou REST-assured)
- **Documentação**:
  - Relatório técnico e diagramas em `OdSoft Docs/` e `Docs/`
  - HELP.md para instruções rápidas de utilização
  - Reverse engineering e análise ASRs
- **Monitorização e Reporting**:
  - Coleção de métricas de pipeline e desempenho
  - Análise evolutiva do CI/CD e evidência de melhorias

## Tecnologias Utilizadas

- **Java 17** (backend)
- **Spring Boot** (REST API)
- **Maven** (gestão de builds)
- **Jenkins** (pipeline CI/CD)
- **SonarQube** (análise estática)
- **JUnit, Mockito, PIT** (testes automáticos)
- **Docker** (opcional, ambientes reprodutíveis)
- **GitHub** (SCM)

## Organização do Repositório

- `src/` — Código-fonte principal
- `.jenkinsFile`, `.jenkinsFileLocal` — Pipelines Jenkins remota/local
- `pom.xml` — Configuração Maven
- `Docs/` e `OdSoft Docs/` — Relatórios técnicos, documentação e evidências
- `HELP.md` — Instruções e FAQ
- `uploads-psoft-g1/` — Dados para testes ou submissão

## Racional

A aposta numa abordagem DevOps, com automação completa do ciclo de vida de desenvolvimento e integração contínua, teve como objetivo garantir qualidade, rastreabilidade, rapidez de entrega e fiabilidade do sistema, alinhando com as melhores práticas modernas de engenharia de software.


> **Software Development Organization, 2024/2025**  
