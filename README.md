# E-commerce Dashboard
Sistema de dashboard para e-commerce com operações CRUD e consultas analíticas 

## Como Obter o Projeto

### Opção 1: Clonar do GitHub

```bash
git clone https://github.com/biagalindoo/ecommerce-bd.git
cd ecommerce-bd
git checkout main
```

### Opção 2: Extrair Arquivo ZIP

1. Extraia o arquivo `.zip` em uma pasta de sua escolha
2. Navegue até a pasta do projeto:
   ```bash
   cd caminho/para/sua/pasta/ecommerce-dashboard
   ```

## Pré-requisitos

- Java 11+
- Maven 3.6+
- MySQL 8.0+

## Configuração

### 1. Banco de Dados

```bash
mysql -u root -p -e "CREATE DATABASE ecommerce_db;"

# Executar scripts SQL (na ordem)
mysql -u root -p ecommerce_db < 01-create-tables.sql
mysql -u root -p ecommerce_db < 02-populate-tables.sql
mysql -u root -p ecommerce_db < 03-consultas.sql
mysql -u root -p ecommerce_db < 04-consultas-avancadas.sql
mysql -u root -p ecommerce_db < 06-funcoes.sql
mysql -u root -p ecommerce_db < 07-procedures.sql
mysql -u root -p ecommerce_db < 08-triggers.sql
```

### 2. Aplicação

Edite `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_DO_MYSQL
server.port=8080
server.servlet.context-path=/ecommerce-dashboard
```

### 3. Executar

```bash
mvn clean compile
mvn spring-boot:run
```

Acesse: http://localhost:8080/ecommerce-dashboard/

## Funcionalidades

### CRUD
- **Usuários**: `/usuarios`
- **Produtos**: `/produtos`
- **Pedidos**: `/pedidos`
- **Fornecedores**: `/fornecedores`

### Consultas Analíticas
- **Básicas**: `/analise/usuarios`, `/analise/preco`, `/analise/produtos-responsaveis`, `/analise/idade`
- **Avançadas**: `/consultas-avancadas` (Anti-JOIN, Full Outer JOIN, Subconsultas, Views)

### Dashboard e Gráficos
- **Dashboard**: `/dashboard`
- **Gráficos**: `/graficos`
- **Consultas**: `/consultas`

## Estrutura do Projeto

```
├── src/main/java/com/ecommerce/
│   ├── entity/          # Entidades
│   ├── dao/             # DAOs com SQL
│   ├── service/         # Lógica de negócio
│   ├── controller/      # Controllers Web
│   └── database/        # Conexão com banco
├── src/main/resources/
│   ├── templates/       # Templates Thymeleaf
│   └── application.properties
├── 01-create-tables.sql
├── 02-populate-tables.sql
├── 03-consultas.sql
├── 04-consultas-avancadas.sql
├── 06-funcoes.sql
├── 07-procedures.sql
└── 08-triggers.sql
```

## Tecnologias

- **Java 11+**
- **Spring Boot 2.7.18**
- **Spring MVC** (sem JPA/ORM)
- **Thymeleaf**
- **MySQL** 
- **Bootstrap 5**
- **Maven**

## Características Técnicas

- Interface responsiva com Bootstrap 5

## Solução de Problemas

**Erro de conexão MySQL**: Verifique senha no `application.properties`

**Banco não encontrado**: Execute os scripts SQL na ordem correta

**Porta 8080 em uso**: Altere `server.port` no `application.properties`
