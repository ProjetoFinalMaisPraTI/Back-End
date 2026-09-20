# Hist�rico de evolu��o do projeto

Este arquivo registra a evolu��o do sistema de ajuda financeira e serve como evid�ncia de progresso para a avalia��o do professor.

## docs: registra vis�o geral do sistema
A aplica��o foi pensada para apoiar controle financeiro pessoal com foco em receitas, despesas e relat�rios mensais.

## docs: detalha requisitos do projeto
Os requisitos definem gest�o de usu�rios, lan�amentos financeiros, filtros por m�s e relat�rios de saldo.

## docs: documenta arquitetura backend
A solu��o usa Spring Boot, JPA, PostgreSQL e padr�es REST para separar camadas e facilitar manuten��o.

## docs: descreve stack tecnol�gica
Java 21, Maven, Spring Boot 4.1.0, PostgreSQL, Lombok e JUnit 5 s�o os pilares da entrega atual.

## config: ajusta projeto Maven
O pom.xml foi organizado para incluir depend�ncias de Web, JPA, Security, valida��o e testes unit�rios.

## config: define propriedades da aplica��o
As configura��es do ambiente usam vari�veis para facilitar execu��o local e integra��o com PostgreSQL.

## feat: inicia estrutura de usu�rios
A entidade principal de usu�rios foi planejada com autentica��o, e-mail e dados cadastrais essenciais.

## feat: cria entidade Usuario
A classe UsuarioEntity foi modelada com campos de identifica��o, dados pessoais, auditoria e relacionamento com movimentos financeiros.

## feat: cria entidade Despesa
A entidade Despesa foi estruturada com valor, data, categoria e controle de recorr�ncia e status de pagamento.

## feat: cria entidade Ganho
A entidade Ganho complementa o fluxo financeiro com entradas, origem e datas de controle.

## feat: cria repositories JPA
Os reposit�rios foram criados para buscar dados por usu�rio, per�odo e filtros mensais.

## feat: cria DTOs de entrada e sa�da
Os DTOs isolam a API de persist�ncia e validam a comunica��o entre as camadas do sistema.

## feat: implementa service de usu�rios
A l�gica de cria��o, leitura, atualiza��o e exclus�o de usu�rios foi centralizada no servi�o.

## feat: implementa service de despesas
O servi�o de despesas valida usu�rio, persiste transa��es e filtra por m�s e per�odo.

## feat: implementa service de ganhos
O servi�o de ganhos oferece o mesmo padr�o de organiza��o para receitas e lan�amentos de entrada.

## feat: implementa controllers REST
As APIs de usu�rios, despesas e ganhos foram expostas com endpoints REST seguindo conven��es HTTP.

## config: configura seguran�a
A seguran�a b�sica foi preparada com BCrypt e infraestrutura para futuras autentica��es e controle de acesso.
