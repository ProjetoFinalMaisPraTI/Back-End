# Histórico de evolução do projeto

Este arquivo registra a evolução do sistema de ajuda financeira e serve como evidência de progresso para a avaliação do professor.

## docs: registra visão geral do sistema
A aplicação foi pensada para apoiar controle financeiro pessoal com foco em receitas, despesas e relatórios mensais.

## docs: detalha requisitos do projeto
Os requisitos definem gestão de usuários, lançamentos financeiros, filtros por mês e relatórios de saldo.

## docs: documenta arquitetura backend
A solução usa Spring Boot, JPA, PostgreSQL e padrões REST para separar camadas e facilitar manutenção.

## docs: descreve stack tecnológica
Java 21, Maven, Spring Boot 4.1.0, PostgreSQL, Lombok e JUnit 5 são os pilares da entrega atual.

## config: ajusta projeto Maven
O pom.xml foi organizado para incluir dependências de Web, JPA, Security, validação e testes unitários.

## config: define propriedades da aplicação
As configurações do ambiente usam variáveis para facilitar execução local e integração com PostgreSQL.

## feat: inicia estrutura de usuários
A entidade principal de usuários foi planejada com autenticação, e-mail e dados cadastrais essenciais.

## feat: cria entidade Usuario
A classe UsuarioEntity foi modelada com campos de identificação, dados pessoais, auditoria e relacionamento com movimentos financeiros.

## feat: cria entidade Despesa
A entidade Despesa foi estruturada com valor, data, categoria e controle de recorrência e status de pagamento.

## feat: cria entidade Ganho
A entidade Ganho complementa o fluxo financeiro com entradas, origem e datas de controle.

## feat: cria repositories JPA
Os repositórios foram criados para buscar dados por usuário, período e filtros mensais.

## feat: cria DTOs de entrada e saída
Os DTOs isolam a API de persistência e validam a comunicação entre as camadas do sistema.

## feat: implementa service de usuários
A lógica de criação, leitura, atualização e exclusão de usuários foi centralizada no serviço.

## feat: implementa service de despesas
O serviço de despesas valida usuário, persiste transações e filtra por mês e período.

## feat: implementa service de ganhos
O serviço de ganhos oferece o mesmo padrão de organização para receitas e lançamentos de entrada.

## feat: implementa controllers REST
As APIs de usuários, despesas e ganhos foram expostas com endpoints REST seguindo convenções HTTP.

## config: configura segurança
A segurança básica foi preparada com BCrypt e infraestrutura para futuras autenticações e controle de acesso.

## test: adiciona testes de usuário
Os testes unitários cobrem criação, busca, atualização, exclusão e validação de e-mail duplicado.
