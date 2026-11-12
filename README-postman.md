# Testando a API no Postman

## Pré-requisitos

- PostgreSQL ativo e banco `sistema_financeiro` criado
- Backend ativo em `http://localhost:8080`
- Postman instalado

A URL base local é `http://localhost:8080/api`. Esta configuração local usa
**HTTP**, não HTTPS. Para HTTPS, a implantação precisa disponibilizar TLS.

## Configurar o Environment

No Postman, crie e selecione um Environment chamado `local` com estas variáveis:

| Variável | Valor inicial |
|---|---|
| `baseUrl` | `http://localhost:8080/api` |
| `email` | `pessoa@example.com` |
| `token` | *(vazio)* |
| `usuarioId` | *(vazio)* |
| `despesaId` | *(vazio)* |
| `ganhoId` | *(vazio)* |
| `transacaoId` | *(vazio)* |

## Registrar e autenticar

1. Crie `POST {{baseUrl}}/auth/registrar`.
2. Em **Body → raw → JSON**, envie:

   ```json
   {
     "nome": "Pessoa de Teste",
     "email": "{{email}}",
     "senha": "SenhaForte123"
   }
   ```

3. Na aba **Scripts → Post-response**, armazene o JWT:

   ```javascript
   const body = pm.response.json();
   pm.environment.set("token", body.token);
   ```

O registro retorna o token, o nome e o e-mail. Se o e-mail já existir, use
`POST {{baseUrl}}/auth/login` com o mesmo e-mail e senha; o script acima também
serve para guardar o token de login.

Para todas as requisições protegidas, selecione **Authorization → Bearer Token**
e informe `{{token}}`. Alternativamente, defina isso na coleção e configure as
requisições filhas como **Inherit auth from parent**. As rotas `/auth/**` e a
documentação Swagger/OpenAPI são públicas; as demais rotas exigem JWT.

## Obter o ID do usuário

Envie `GET {{baseUrl}}/usuarios/email/{{email}}` com Bearer token. Em
**Scripts → Post-response**, salve o ID retornado:

```javascript
const body = pm.response.json();
pm.environment.set("usuarioId", body.id);
```

## Testar despesas

Envie `POST {{baseUrl}}/despesas` com Bearer token e JSON:

```json
{
  "descricao": "Conta de luz",
  "valor": 150.00,
  "dataDespesa": "2026-11-12",
  "categoria": "Moradia",
  "descricaoDetalhada": "Fatura do mês",
  "recorrente": true,
  "tipoRecorrencia": "MENSAL",
  "paga": false,
  "usuarioId": {{usuarioId}}
}
```

A resposta esperada é `201 Created`. Para guardar o ID da resposta, use:

```javascript
const body = pm.response.json();
pm.environment.set("despesaId", body.id);
```

Outras chamadas para testar:

```text
GET    {{baseUrl}}/despesas/usuario/{{usuarioId}}
GET    {{baseUrl}}/despesas/usuario/{{usuarioId}}/mes?ano=2026&mes=11
GET    {{baseUrl}}/despesas/usuario/{{usuarioId}}/periodo?dataInicio=2026-11-01&dataFim=2026-11-30
GET    {{baseUrl}}/despesas/{{despesaId}}
PUT    {{baseUrl}}/despesas/{{despesaId}}
DELETE {{baseUrl}}/despesas/{{despesaId}}
```

Para `PUT`, envie novamente os campos obrigatórios e o `usuarioId` no JSON. O
endpoint `GET /despesas` existe, mas atualmente retorna uma lista vazia; use as
rotas filtradas por usuário para consultar os registros.

## Testar ganhos

Envie `POST {{baseUrl}}/ganhos` com Bearer token e JSON:

```json
{
  "descricao": "Salário",
  "valor": 3500.00,
  "dataGanho": "2026-11-05",
  "tipo": "SALARIO",
  "descricaoDetalhada": "Pagamento mensal",
  "recorrente": true,
  "tipoRecorrencia": "MENSAL",
  "usuarioId": {{usuarioId}}
}
```

A resposta esperada é `201 Created`. Salve o ID da mesma forma, usando a
variável `ganhoId`. Endpoints para consultar, atualizar e remover:

```text
GET    {{baseUrl}}/ganhos/usuario/{{usuarioId}}
GET    {{baseUrl}}/ganhos/usuario/{{usuarioId}}/mes?ano=2026&mes=11
GET    {{baseUrl}}/ganhos/usuario/{{usuarioId}}/periodo?dataInicio=2026-11-01&dataFim=2026-11-30
GET    {{baseUrl}}/ganhos/{{ganhoId}}
PUT    {{baseUrl}}/ganhos/{{ganhoId}}
DELETE {{baseUrl}}/ganhos/{{ganhoId}}
```

O endpoint `GET /ganhos` existe, mas atualmente retorna uma lista vazia; use as
rotas filtradas por usuário para consultar os registros.

## Testar transações

Envie `POST {{baseUrl}}/transacoes` com Bearer token:

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

O usuário associado é obtido a partir do token. A resposta esperada é `200 OK`.
Guarde o `id` retornado na variável `transacaoId`. Os endpoints disponíveis são:

```text
GET    {{baseUrl}}/transacoes
POST   {{baseUrl}}/transacoes
PUT    {{baseUrl}}/transacoes/{{transacaoId}}
DELETE {{baseUrl}}/transacoes/{{transacaoId}}
```

## Importar a documentação OpenAPI

Com o backend ativo, escolha **Import → Link** no Postman e informe:

```text
http://localhost:8080/api/v3/api-docs
```

O Swagger UI também está disponível em
`http://localhost:8080/api/swagger-ui.html`.

## Respostas comuns

| Status | Significado |
|---|---|
| `200 OK` | Consulta, login ou atualização concluída |
| `201 Created` | Recurso criado |
| `204 No Content` | Recurso removido |
| `400 Bad Request` | Payload inválido ou rejeitado |
| `401 Unauthorized` | JWT ausente/inválido ou credenciais inválidas |
| `404 Not Found` | Recurso não encontrado |
