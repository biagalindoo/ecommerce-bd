# E-commerce Dashboard

Sistema de dashboard para e-commerce com operações CRUD usando SQL puro (sem ORMs).

## 🚀 Configuração

### 🐳 **Execução com Docker (Recomendado)**

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/biagalindoo/ecommerce-bd.git
   cd ecommerce-bd
   ```

2. **Configure o arquivo `.env`:**
   ```bash
   cp env.example .env
   # Edite o arquivo .env com suas configurações de banco
   ```

3. **Execute com Docker:**
   ```bash
   # Linux/Mac
   ./start.sh
   
   # Windows
   start.bat
   
   # Ou manualmente
   docker-compose up --build
   ```

4. **Acesse a aplicação:**
   - Dashboard: http://localhost:8080/ecommerce-dashboard/dashboard
   - Usuários: http://localhost:8080/ecommerce-dashboard/usuario
   - Produtos: http://localhost:8080/ecommerce-dashboard/produto

### 🔧 **Execução Manual (Sem Docker)**

#### 1. Configuração do Banco de Dados

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

#### 2. Configuração do MySQL

1. **Execute os scripts SQL na ordem:**
   ```bash
   mysql -u root -p < 01-create-tables.sql
   mysql -u root -p < 02-populate-tables.sql
   ```

2. **Teste as consultas:**
   ```bash
   mysql -u root -p < 03-consultas.sql
   ```

#### 3. Execução da Aplicação

1. **Compile o projeto:**
   ```bash
   mvn clean compile
   ```

2. **Execute a aplicação Spring Boot:**
   ```bash
   mvn spring-boot:run
   ```

3. **Acesse a aplicação:**
   - Dashboard: http://localhost:8080/ecommerce-dashboard/dashboard
   - Usuários: http://localhost:8080/ecommerce-dashboard/usuario
   - Produtos: http://localhost:8080/ecommerce-dashboard/produto

## 📁 Estrutura do Projeto

```
├── src/main/java/com/ecommerce/
│   ├── entity/          # Entidades JPA (Usuario, Produto, etc.)
│   ├── repository/      # Repositories Spring Data JPA
│   ├── service/         # Services com lógica de negócio
│   ├── controller/      # Controllers REST/Web
│   └── EcommerceDashboardApplication.java # Classe principal Spring Boot
├── src/main/resources/
│   ├── templates/       # Templates Thymeleaf
│   └── application.properties # Configurações Spring Boot
├── 01-create-tables.sql # Script de criação das tabelas
├── 02-populate-tables.sql # Script de população das tabelas
├── 03-consultas.sql     # Consultas SQL de exemplo
├── .env                 # Configurações do banco (não versionado)
├── env.example          # Template de configuração
├── Dockerfile           # Configuração Docker
├── docker-compose.yml   # Orquestração Docker
└── pom.xml              # Configuração Maven
```

## 🔧 Funcionalidades

- ✅ **Dashboard** com estatísticas em tempo real
- ✅ **CRUD de Usuários** (criar, listar, editar, deletar)
- ✅ **CRUD de Produtos** (criar, listar, editar, deletar)
- ✅ **Busca avançada** por nome, cidade, estado
- ✅ **Filtro de estoque baixo** para produtos
- ✅ **Validação de formulários** com Bean Validation
- ✅ **Interface responsiva** com Bootstrap 5
- ✅ **Templates Thymeleaf** para renderização
- ✅ **Spring Data JPA** para acesso a dados
- ✅ **Configuração via arquivo .env**

## 🛠️ Tecnologias

- **Java 11+**
- **Spring Boot 2.7.18**
- **Spring Data JPA**
- **Spring MVC**
- **Thymeleaf**
- **Maven**
- **MySQL**
- **Docker & Docker Compose**

## 🐳 **Docker**

### Comandos Docker

```bash
# Construir e iniciar
docker-compose up --build

# Executar em background
docker-compose up -d

# Parar containers
docker-compose down

# Ver logs
docker-compose logs -f

# Reconstruir apenas a aplicação
docker-compose build ecommerce-app

# Executar comandos no container
docker-compose exec ecommerce-app bash
```

### Arquivos Docker

- `Dockerfile` - Configuração da imagem da aplicação
- `docker-compose.yml` - Configuração dos serviços
- `docker-compose.override.yml` - Configurações de desenvolvimento
- `.dockerignore` - Arquivos ignorados no build
- `start.sh` / `start.bat` - Scripts de inicialização

## 📝 Notas

- O arquivo `.env` contém informações sensíveis e não deve ser versionado
- Use o arquivo `env.example` como template para suas configurações
- Todas as operações de banco usam SQL puro, sem ORMs
- A aplicação usa o padrão Singleton para conexão com banco