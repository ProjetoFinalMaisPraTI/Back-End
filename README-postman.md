# Guia de Testes com Postman — Sistema de Ajuda Financeira

## Pré-requisitos

- [Postman](https://www.postman.com/downloads/) instalado
- Aplicação rodando localmente: `./mvnw spring-boot:run`
- PostgreSQL acessível com o banco `sistema_financeiro` criado
- Base URL configurada: `http://localhost:8080/api`

---

## Swagger UI

Com a aplicação no ar, acesse a documentação interativa:

```
http://localhost:8080/api/swagger-ui.html
```

O spec OpenAPI em JSON está disponível em:

```
http://localhost:8080/api/v3/api-docs
```

---

## Importando a coleção no Postman

1. Abra o Postman → clique em **Import**
2. Selecione a aba **Link** e cole:
   ```
   http://localhost:8080/api/v3/api-docs
   ```
3. Clique em **Continue** → **Import**
4. A coleção **Sistema de Ajuda Financeira — API** será criada automaticamente com todos os endpoints

---

## Variável de ambiente

Crie um **Environment** no Postman chamado `local` com as variáveis:

| Variável     | Valor inicial            |
|--------------|--------------------------|
| `baseUrl`    | `http://localhost:8080/api` |
| `usuarioId`  | *(preencha após criar um usuário)* |
| `despesaId`  | *(preencha após criar uma despesa)* |
| `ganhoId`    | *(preencha após criar um ganho)*    |

---

## Roteiro de testes — ordem recomendada

### 1. Usuários

#### Criar usuário
```
POST {{baseUrl}}/usuarios
Content-Type: application/json

{
  "nome": "João da Silva",
  "email": "joao@email.com",
  "telefone": "(51) 99999-0000",
  "dataNascimento": "1990-05-15",
  "cpf": "123.456.789-00",
  "ativo": true
}
```
**Esperado:** `201 Created` — copie o `id` retornado para `{{usuarioId}}`

---

#### Listar todos os usuários
```
GET {{baseUrl}}/usuarios
```
**Esperado:** `200 OK` com array de usuários

---

#### Buscar usuário por ID
```
GET {{baseUrl}}/usuarios/{{usuarioId}}
```
**Esperado:** `200 OK` com os dados do usuário

---

#### Buscar usuário por e-mail
```
GET {{baseUrl}}/usuarios/email/joao@email.com
```
**Esperado:** `200 OK`

---

#### Verificar disponibilidade de e-mail
```
GET {{baseUrl}}/usuarios/verificar-email/joao@email.com
```
**Esperado:** `200 OK` com `true`

---

#### Atualizar usuário
```
PUT {{baseUrl}}/usuarios/{{usuarioId}}
Content-Type: application/json

{
  "nome": "João da Silva Atualizado",
  "email": "joao@email.com",
  "telefone": "(51) 98888-1111",
  "ativo": true
}
```
**Esperado:** `200 OK` com dados atualizados

---

### 2. Despesas

#### Criar despesa
```
POST {{baseUrl}}/despesas
Content-Type: application/json

{
  "descricao": "Conta de luz",
  "valor": 150.00,
  "dataDespesa": "2026-08-10",
  "categoria": "Moradia",
  "descricaoDetalhada": "Fatura do mês de agosto",
  "recorrente": true,
  "tipoRecorrencia": "MENSAL",
  "paga": false,
  "usuarioId": {{usuarioId}}
}
```
**Esperado:** `201 Created` — copie o `id` retornado para `{{despesaId}}`

---

#### Buscar despesa por ID
```
GET {{baseUrl}}/despesas/{{despesaId}}
```
**Esperado:** `200 OK`

---

#### Listar despesas do usuário
```
GET {{baseUrl}}/despesas/usuario/{{usuarioId}}
```
**Esperado:** `200 OK` com array de despesas

---

#### Listar despesas por mês
```
GET {{baseUrl}}/despesas/usuario/{{usuarioId}}/mes?ano=2026&mes=8
```
**Esperado:** `200 OK` filtrando pelo mês

---

#### Listar despesas por período
```
GET {{baseUrl}}/despesas/usuario/{{usuarioId}}/periodo?dataInicio=2026-08-01&dataFim=2026-08-31
```
**Esperado:** `200 OK` filtrando pelo intervalo

---

#### Atualizar despesa
```
PUT {{baseUrl}}/despesas/{{despesaId}}
Content-Type: application/json

{
  "descricao": "Conta de luz — atualizada",
  "valor": 162.50,
  "dataDespesa": "2026-08-10",
  "categoria": "Moradia",
  "paga": true,
  "usuarioId": {{usuarioId}}
}
```
**Esperado:** `200 OK`

---

### 3. Ganhos

#### Criar ganho
```
POST {{baseUrl}}/ganhos
Content-Type: application/json

{
  "descricao": "Salário mensal",
  "valor": 3500.00,
  "dataGanho": "2026-08-05",
  "tipo": "SALARIO",
  "descricaoDetalhada": "Pagamento referente ao mês de agosto",
  "recorrente": true,
  "tipoRecorrencia": "MENSAL",
  "usuarioId": {{usuarioId}}
}
```
**Esperado:** `201 Created` — copie o `id` retornado para `{{ganhoId}}`

---

#### Buscar ganho por ID
```
GET {{baseUrl}}/ganhos/{{ganhoId}}
```
**Esperado:** `200 OK`

---

#### Listar ganhos do usuário
```
GET {{baseUrl}}/ganhos/usuario/{{usuarioId}}
```
**Esperado:** `200 OK` com array de ganhos

---

#### Listar ganhos por mês
```
GET {{baseUrl}}/ganhos/usuario/{{usuarioId}}/mes?ano=2026&mes=8
```
**Esperado:** `200 OK`

---

#### Listar ganhos por período
```
GET {{baseUrl}}/ganhos/usuario/{{usuarioId}}/periodo?dataInicio=2026-08-01&dataFim=2026-08-31
```
**Esperado:** `200 OK`

---

#### Atualizar ganho
```
PUT {{baseUrl}}/ganhos/{{ganhoId}}
Content-Type: application/json

{
  "descricao": "Salário mensal — reajuste",
  "valor": 3800.00,
  "dataGanho": "2026-08-05",
  "tipo": "SALARIO",
  "recorrente": true,
  "tipoRecorrencia": "MENSAL",
  "usuarioId": {{usuarioId}}
}
```
**Esperado:** `200 OK`

---

### 4. Limpeza (opcional)

#### Deletar ganho
```
DELETE {{baseUrl}}/ganhos/{{ganhoId}}
```
**Esperado:** `204 No Content`

#### Deletar despesa
```
DELETE {{baseUrl}}/despesas/{{despesaId}}
```
**Esperado:** `204 No Content`

#### Deletar usuário
```
DELETE {{baseUrl}}/usuarios/{{usuarioId}}
```
**Esperado:** `204 No Content`

---

## Casos de erro para validar

| Cenário | Requisição | Esperado |
|---|---|---|
| Criar usuário sem nome | `POST /usuarios` sem o campo `nome` | `400 Bad Request` |
| Criar usuário com e-mail inválido | `email: "nao-e-email"` | `400 Bad Request` |
| Criar despesa com valor negativo | `valor: -50` | `400 Bad Request` |
| Buscar ID inexistente | `GET /usuarios/9999` | `404 Not Found` |
| Criar despesa sem `usuarioId` | omitir o campo | `400 Bad Request` |
| Criar ganho sem `dataGanho` | omitir o campo | `400 Bad Request` |

---

## Dicas

- Use **Test Scripts** no Postman para salvar o `id` automaticamente:
  ```javascript
  // Cole isso na aba "Tests" do request POST /usuarios
  const json = pm.response.json();
  pm.environment.set("usuarioId", json.id);
  ```
- Repita o mesmo padrão para `despesaId` e `ganhoId`
- Ative **Auto-save responses** para comparar retornos entre execuções
