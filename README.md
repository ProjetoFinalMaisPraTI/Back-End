# Sistema de Ajuda Financeira 💰

Um sistema completo de gerenciamento de finanças pessoais, familiar e empresarial, desenvolvido com as melhores práticas de engenharia de software.

## 📋 Visão Geral

O **Sistema de Ajuda Financeira** é uma plataforma que permite:
- **PF (Pessoa Física)**: Gerenciar gastos e ganhos pessoais
- **Familiar**: Expandir para gestão em grupo (2+ pessoas)
- **PJ (Empresa)**: Gerenciamento empresarial (feature futura)

### Requisitos Necessários (RN)
- ✅ Login de Usuário
- ✅ Cadastro de Usuário
- ✅ Incluir Despesas
- ✅ Incluir Ganhos
- ✅ Calcular Gasto Mensal Total
- ✅ Overview anual/mensal

### Requisitos Principais (RP)
- 🔄 Gerenciamento Familiar ou em Grupo
- 🔄 Gastos Recorrentes
- 🤖 Agente/Assistente de IA

### Requisitos Secundários (RS)
- 📱 Gerenciamento Empresarial (PJ)
- 📱 Aplicativo Mobile

---

## 🛠️ Stack Tecnológico

### Backend (Back-end)
- **Linguagem**: Java 21
- **Framework**: Spring Boot 4.1.0
- **Banco de Dados**: PostgreSQL
- **Build Tool**: Maven
- **Testes**: JUnit 5, Mockito

### Frontend (Front-end)
- **Linguagem**: JavaScript/TypeScript
- **Framework**: React
- **Build Tool**: Vite/Webpack

### Inteligência Artificial
- **Linguagem**: Python
- **Objetivo**: Agentes de IA e lógicas simples

### Banco de Dados
- **SGBD**: PostgreSQL
- **ORM**: JPA/Hibernate

---

## 👥 Divisão do Time

| Membro | Foco Principal | Responsabilidades |
|--------|----------------|--------------------|
| **Rafael Novo da Rosa** | Back-end | APIs, Testes Unitários, Testes Postman |
| **Agostinho** | Back-end | Desenvolvimento de APIs complementares |
| **Igor** | BD + IA | Estrutura de dados, Agentes Python |
| **Marcel** | Front-end | Interface e Layouts |
| **Diogo** | Front-end + Back-end | Integração Full-stack |
| **Pedro** | Front-end | Componentes e UI |
| **Glória** | Front-end | Componentes e UI |

**Responsável por Pull Requests**: Eduardo Goes

---

## 🚀 Como Iniciar

### Pré-requisitos
- Java 21
- Maven 3.8+
- PostgreSQL 14+
- Git

### Instalação

1. **Clone o repositório**
```bash
git clone git@github.com:Raphanrn/sistema-de-ajuda-financeira.git
cd sistema-de-ajuda-financeira
```

2. **Configure o banco de dados**
```bash
# Crie o banco de dados
createdb sistema_financeiro

# Configure as variáveis de ambiente
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=sistema_financeiro
export DB_USER=seu_usuario
export DB_PASSWORD=sua_senha
```

3. **Configure o application.properties**
```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

4. **Compile e execute**
```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 📁 Estrutura do Projeto

```
sistema-de-ajuda-financeira/
├── src/
│   ├── main/
│   │   ├── java/com/example/sistemafinanceiro/
│   │   │   ├── controller/        # Controllers REST
│   │   │   ├── service/           # Serviços de negócio
│   │   │   ├── repository/        # Repositórios JPA
│   │   │   ├── entity/            # Entidades JPA
│   │   │   ├── dto/               # Data Transfer Objects
│   │   │   ├── exception/         # Exceções customizadas
│   │   │   └── config/            # Configurações
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-dev.properties
│   └── test/
│       └── java/                  # Testes unitários e integração
├── pom.xml                         # Dependências Maven
└── README.md                       # Este arquivo
```

---

## 🔄 Fluxo de Git e GitHub

### Branches Principais
- **`main`**: Produção (estável). Apenas Pull Requests.
- **`develop`**: Desenvolvimento/Integração. Apenas Pull Requests.

### Branches Auxiliares
- **`feature/<nome-funcionalidade>`**: Novas funcionalidades
  - Exemplo: `feature/cria-home-page`
- **`refactor/<o-que-faz>`**: Correção de bugs e ajustes
  - Exemplo: `refactor/ajusta-texto-login`
- **`test/<nome-teste>`**: Testes
  - Exemplo: `test/teste-autenticacao`

### Padrão de Commits
```
<tipo>: <breve descrição>
```

**Exemplos**:
- `feature: implementa login de usuário`
- `refactor: corrige conexão com a api de cadastro`
- `test: implementa testes automatizados de incluir gastos`

### Fluxo de Pull Request (PR)

1. **Criar branch a partir de `develop`**:
```bash
git checkout develop
git pull origin develop
git checkout -b feature/sua-funcionalidade
```

2. **Fazer commits seguindo o padrão**:
```bash
git add .
git commit -m "feature: implementa login de usuário"
```

3. **Push para o repositório**:
```bash
git push origin feature/sua-funcionalidade
```

4. **Criar PR no GitHub**:
   - Base: `develop`
   - Descrever: O que foi feito, Como testar, Screenshots (se visual)
   - Aguardar aprovação do **Eduardo Goes**

5. **Após aprovação**:
   - Merge via GitHub
   - Delete a branch local e remota

---

## 📡 Endpoints da API (Base)

### Autenticação
```
POST   /api/auth/login              # Login de usuário
POST   /api/auth/register           # Registro de usuário
POST   /api/auth/logout             # Logout
```

### Usuários
```
GET    /api/usuarios                # Listar usuários
GET    /api/usuarios/{id}           # Obter usuário por ID
PUT    /api/usuarios/{id}           # Atualizar usuário
DELETE /api/usuarios/{id}           # Deletar usuário
```

### Despesas
```
GET    /api/despesas                # Listar despesas
POST   /api/despesas                # Criar despesa
GET    /api/despesas/{id}           # Obter despesa por ID
PUT    /api/despesas/{id}           # Atualizar despesa
DELETE /api/despesas/{id}           # Deletar despesa
GET    /api/despesas/mes/{mes}      # Despesas do mês
```

### Ganhos
```
GET    /api/ganhos                  # Listar ganhos
POST   /api/ganhos                  # Criar ganho
GET    /api/ganhos/{id}             # Obter ganho por ID
PUT    /api/ganhos/{id}             # Atualizar ganho
DELETE /api/ganhos/{id}             # Deletar ganho
GET    /api/ganhos/mes/{mes}        # Ganhos do mês
```

### Relatórios
```
GET    /api/relatorios/mensal       # Relatório mensal
GET    /api/relatorios/anual        # Relatório anual
GET    /api/relatorios/saldo        # Saldo total
```

---

## 🧪 Testes

### Executar Testes
```bash
# Todos os testes
mvn test

# Testes de uma classe específica
mvn test -Dtest=NomeDaClasseTest

# Com cobertura
mvn test jacoco:report
```

### Testes com Postman
Importe o arquivo `postman_collection.json` no Postman e execute as requisições.

---

## 📝 Convenções de Código

### Nomenclatura
- **Classes**: `PascalCase` (ex: `UsuarioService`)
- **Métodos**: `camelCase` (ex: `obterUsuarioPorId`)
- **Constantes**: `UPPER_SNAKE_CASE` (ex: `MAX_TENTATIVAS`)
- **Pacotes**: `lowercase` com ponto (ex: `com.example.service`)

### Padrões
- **Entidades JPA**: Sufixo `Entity` ou sem sufixo
- **DTOs**: Sufixo `DTO`
- **Repositories**: Estender `JpaRepository<T, ID>`
- **Services**: Interface + Implementação
- **Controllers**: Sufixo `Controller`

---

## 🤝 Contribuindo

1. Verifique as [convenções de git](#-fluxo-de-git-e-github)
2. Sempre pull antes de criar nova branch
3. Escreva testes para suas funcionalidades
4. Mantenha o código limpo e bem documentado
5. Aguarde aprovação do **Eduardo Goes** antes do merge

---

## 📞 Suporte

Para dúvidas ou problemas, entre em contato com:
- **Back-end**: Rafael Novo da Rosa
- **Front-end**: Marcel, Diogo, Pedro, Glória
- **Banco de Dados**: Igor
- **Pull Requests**: Eduardo Goes

---

## 📄 Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.

---

## 🎯 Roadmap

- [ ] **Fase 1**: APIs básicas (Usuário, Despesa, Ganho)
- [ ] **Fase 2**: Autenticação e Segurança
- [ ] **Fase 3**: Relatórios e Overview
- [ ] **Fase 4**: Gerenciamento Familiar
- [ ] **Fase 5**: Gastos Recorrentes
- [ ] **Fase 6**: Agente de IA
- [ ] **Fase 7**: Gerenciamento Empresarial
- [ ] **Fase 8**: Aplicativo Mobile

---

**Última atualização**: 15/08/2026
