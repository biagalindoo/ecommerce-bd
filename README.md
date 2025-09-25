# E-commerce Dashboard

Sistema de dashboard para e-commerce com operações CRUD usando SQL puro (sem ORMs).

## 🚀 Configuração

### 1. Configuração do Banco de Dados

1. **Copie o arquivo de exemplo:**
   ```bash
   cp env.example .env
   ```

2. **Edite o arquivo `.env` com suas configurações:**
   ```env
   # Configurações do Banco de Dados MySQL
   DB_HOST=localhost
   DB_PORT=3306
   DB_NAME=ecommerce_bd
   DB_USER=root
   DB_PASSWORD=sua_senha_aqui
   
   # Configurações da Aplicação
   APP_PORT=8080
   APP_CONTEXT_PATH=/ecommerce-dashboard
   
   # Configurações de Conexão
   DB_CONNECTION_TIMEOUT=30000
   DB_MAX_CONNECTIONS=10
   ```

### 2. Configuração do MySQL

1. **Execute os scripts SQL na ordem:**
   ```bash
   mysql -u root -p < 01-create-tables.sql
   mysql -u root -p < 02-populate-tables.sql
   ```

2. **Teste as consultas:**
   ```bash
   mysql -u root -p < 03-consultas.sql
   ```

### 3. Execução da Aplicação

1. **Compile o projeto:**
   ```bash
   mvn clean compile
   ```

2. **Execute o servidor:**
   ```bash
   mvn jetty:run
   ```

3. **Acesse a aplicação:**
   - Dashboard: http://localhost:8080/ecommerce-dashboard/dashboard
   - Usuários: http://localhost:8080/ecommerce-dashboard/usuario
   - Produtos: http://localhost:8080/ecommerce-dashboard/produto

## 📁 Estrutura do Projeto

```
├── src/main/java/com/ecommerce/
│   ├── config/           # Configurações (EnvironmentConfig)
│   ├── database/         # Conexão com banco (DatabaseConnection)
│   ├── model/           # Modelos de dados (Usuario, Produto)
│   ├── dao/             # Data Access Objects (UsuarioDAO, ProdutoDAO)
│   ├── servlet/         # Servlets (UsuarioServlet, ProdutoServlet, DashboardServlet)
│   └── filter/          # Filtros (CharacterEncodingFilter)
├── src/main/webapp/
│   ├── WEB-INF/
│   │   ├── views/       # Páginas JSP
│   │   └── web.xml      # Configuração da aplicação
│   └── css/             # Estilos CSS
├── 01-create-tables.sql # Script de criação das tabelas
├── 02-populate-tables.sql # Script de população das tabelas
├── 03-consultas.sql     # Consultas SQL de exemplo
├── .env                 # Configurações do banco (não versionado)
├── env.example          # Template de configuração
└── pom.xml              # Configuração Maven
```

## 🔧 Funcionalidades

- ✅ **Dashboard** com estatísticas
- ✅ **CRUD de Usuários** (criar, listar, editar, deletar)
- ✅ **CRUD de Produtos** (criar, listar, editar, deletar)
- ✅ **Busca** por nome em usuários e produtos
- ✅ **Filtro de estoque baixo** para produtos
- ✅ **Validação de formulários**
- ✅ **SQL puro** sem ORMs
- ✅ **Configuração via arquivo .env**

## 🛠️ Tecnologias

- **Java 11+**
- **Maven**
- **MySQL**
- **Jetty (servidor embarcado)**
- **JSP + JSTL**
- **JDBC (SQL puro)**
- **CSS + JavaScript**

## 📝 Notas

- O arquivo `.env` contém informações sensíveis e não deve ser versionado
- Use o arquivo `env.example` como template para suas configurações
- Todas as operações de banco usam SQL puro, sem ORMs
- A aplicação usa o padrão Singleton para conexão com banco