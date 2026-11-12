# Sistema de Ajuda Financeira — API

API REST para gerenciamento de usuários, despesas, ganhos e transações.

## Tecnologias

- **Java 21**
- **Spring Boot 4.1** e Spring Web MVC
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Spring Security** com autenticação stateless por **JWT** (JJWT)
- **Jakarta Bean Validation** para validação de payloads
- **Springdoc OpenAPI / Swagger UI**
- **Maven Wrapper**
- **JUnit 5, Mockito e Spring Security Test**

## Pré-requisitos

- JDK 21
- PostgreSQL em execução
- Banco `sistema_financeiro` criado

## Configuração e execução local

O backend usa por padrão `localhost:5432`, banco `sistema_financeiro`, usuário
`postgres` e senha `postgres`. Sobrescreva os valores com variáveis de ambiente;
não coloque credenciais reais no código ou em arquivos versionados.

| Variável | Padrão | Descrição |
|---|---|---|
| `DB_HOST` | `localhost` | Host do PostgreSQL |
| `DB_PORT` | `5432` | Porta do PostgreSQL |
| `DB_NAME` | `sistema_financeiro` | Nome do banco |
| `DB_USER` | `postgres` | Usuário do banco |
| `DB_PASSWORD` | `postgres` | Senha do banco |

No Windows, pelo Prompt de Comando:

```cmd
cd /d "caminho\para\Back-End"
set "DB_PASSWORD=sua_senha"
mvnw.cmd spring-boot:run
```

No PowerShell:

```powershell
cd "caminho\para\Back-End"
$env:DB_PASSWORD = "sua_senha"
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
cd caminho/para/Back-End
DB_PASSWORD=sua_senha ./mvnw spring-boot:run
```

O Hibernate está configurado com `ddl-auto=update` e cria/atualiza as tabelas
quando a aplicação conecta ao banco. Para executar os testes:

```cmd
mvnw.cmd test
```

## Documentação da API

Com a aplicação em execução:

- **Swagger UI:** http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/api/v3/api-docs
- **URL base:** `http://localhost:8080/api`

O Swagger permite explorar os contratos e enviar requisições. A API local usa
**HTTP**; HTTPS só estará disponível quando for configurado TLS em um servidor
ou proxy de implantação.

## Autenticação

`POST /auth/registrar` e `POST /auth/login` são públicos. O cadastro e o login
retornam um JWT no campo `token`. As demais rotas, incluindo as de usuários,
despesas, ganhos e transações, exigem o cabeçalho:

```http
Authorization: Bearer <token>
```

### Registrar usuário

`POST /auth/registrar`

```json
{
  "nome": "João da Silva",
  "email": "joao@example.com",
  "senha": "SenhaForte123"
}
```

A senha deve ter ao menos 8 caracteres. Resposta: token, nome e e-mail.

### Login

`POST /auth/login`

```json
{
  "email": "joao@example.com",
  "senha": "SenhaForte123"
}
```

Credenciais inválidas retornam `401 Unauthorized`.

## Endpoints REST

Todos os caminhos abaixo são relativos à URL base `http://localhost:8080/api`.
Exceto as duas rotas `/auth`, os endpoints exigem JWT.

### Autenticação — público

| Método | Caminho | Descrição |
|---|---|---|
| `POST` | `/auth/registrar` | Cadastra usuário e retorna JWT |
| `POST` | `/auth/login` | Autentica e retorna JWT |

### Usuários — autenticado

| Método | Caminho | Descrição |
|---|---|---|
| `POST` | `/usuarios` | Cria usuário pelo DTO de usuário |
| `GET` | `/usuarios` | Lista usuários |
| `GET` | `/usuarios/{id}` | Busca usuário por ID |
| `GET` | `/usuarios/email/{email}` | Busca usuário por e-mail |
| `GET` | `/usuarios/verificar-email/{email}` | Verifica se o e-mail existe |
| `PUT` | `/usuarios/{id}` | Atualiza usuário |
| `DELETE` | `/usuarios/{id}` | Remove usuário |

Para criar uma conta de acesso, prefira `/auth/registrar`, que aceita os campos
`nome`, `email` e `senha` e retorna o token.

### Despesas — autenticado

| Método | Caminho | Descrição |
|---|---|---|
| `POST` | `/despesas` | Cria despesa |
| `GET` | `/despesas` | Lista geral (atualmente retorna lista vazia) |
| `GET` | `/despesas/{id}` | Busca despesa por ID |
| `GET` | `/despesas/usuario/{usuarioId}` | Lista despesas do usuário |
| `GET` | `/despesas/usuario/{usuarioId}/mes?ano={ano}&mes={mes}` | Filtra por mês |
| `GET` | `/despesas/usuario/{usuarioId}/periodo?dataInicio={yyyy-MM-dd}&dataFim={yyyy-MM-dd}` | Filtra por período |
| `PUT` | `/despesas/{id}` | Atualiza despesa |
| `DELETE` | `/despesas/{id}` | Remove despesa |

Campos principais para criação: `descricao`, `valor` positivo, `dataDespesa`
(`yyyy-MM-dd`) e `usuarioId`. Também aceita `categoria`,
`descricaoDetalhada`, `recorrente`, `tipoRecorrencia` e `paga`.

### Ganhos — autenticado

| Método | Caminho | Descrição |
|---|---|---|
| `POST` | `/ganhos` | Cria ganho |
| `GET` | `/ganhos` | Lista geral (atualmente retorna lista vazia) |
| `GET` | `/ganhos/{id}` | Busca ganho por ID |
| `GET` | `/ganhos/usuario/{usuarioId}` | Lista ganhos do usuário |
| `GET` | `/ganhos/usuario/{usuarioId}/mes?ano={ano}&mes={mes}` | Filtra por mês |
| `GET` | `/ganhos/usuario/{usuarioId}/periodo?dataInicio={yyyy-MM-dd}&dataFim={yyyy-MM-dd}` | Filtra por período |
| `PUT` | `/ganhos/{id}` | Atualiza ganho |
| `DELETE` | `/ganhos/{id}` | Remove ganho |

Campos principais para criação: `descricao`, `valor` positivo, `dataGanho`
(`yyyy-MM-dd`) e `usuarioId`. Também aceita `tipo`, `descricaoDetalhada`,
`recorrente` e `tipoRecorrencia`.

### Transações — autenticado

| Método | Caminho | Descrição |
|---|---|---|
| `GET` | `/transacoes` | Lista transações do usuário autenticado |
| `POST` | `/transacoes` | Cria transação vinculada ao usuário autenticado |
| `PUT` | `/transacoes/{id}` | Atualiza transação |
| `DELETE` | `/transacoes/{id}` | Remove transação |

O corpo aceita `titulo`, `valor`, `data` (`yyyy-MM-dd`), `parcelado`,
`qtdParcelas`, `descricao`, `recorrente`, `repetirDias` e `tipo` (`despesa` ou
`ganho`). O usuário é obtido a partir do JWT no endpoint de criação.

## Testes com Postman

1. Inicie o PostgreSQL e a API.
2. No Postman, crie um Environment `local` com:
   - `baseUrl`: `http://localhost:8080/api`
   - `token`: inicialmente vazio
3. Crie uma requisição `POST {{baseUrl}}/auth/registrar`, selecione **Body →
   raw → JSON** e envie o exemplo de cadastro acima.
4. Em **Scripts → Post-response**, salve o token retornado no Environment:

   ```javascript
   const body = pm.response.json();
   pm.environment.set("token", body.token);
   ```

5. Para testar rotas protegidas, abra a aba **Authorization** da requisição,
   escolha **Bearer Token** e informe `{{token}}`. Você também pode configurar
   isso na coleção e usar **Inherit auth from parent** nas requisições.
6. Teste, por exemplo, `GET {{baseUrl}}/transacoes` e depois envie uma transação
   com `POST {{baseUrl}}/transacoes`:

   ```json
   {
     "titulo": "Supermercado",
     "valor": 125.50,
     "data": "2026-11-12",
     "parcelado": false,
     "descricao": "Compras do mês",
     "recorrente": "nao",
     "tipo": "despesa"
   }
   ```

Para importar a coleção baseada no contrato OpenAPI, use **Import → Link** e
informe `http://localhost:8080/api/v3/api-docs`. O arquivo
[`README-postman.md`](./README-postman.md) contém um roteiro complementar.

### Respostas HTTP comuns

| Código | Significado |
|---|---|
| `200 OK` | Consulta, autenticação ou atualização concluída |
| `201 Created` | Recurso criado |
| `204 No Content` | Recurso removido |
| `400 Bad Request` | Payload inválido ou dados rejeitados |
| `401 Unauthorized` | JWT ausente/inválido ou credenciais inválidas |
| `404 Not Found` | Recurso não encontrado |
