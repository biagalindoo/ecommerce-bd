# E-commerce Dashboard

Sistema de dashboard para e-commerce com operações CRUD usando **SQL puro** (sem ORMs) e **4 consultas analíticas** implementadas.

##  Configuração Rápida

### **1. Extrair a Pasta Zipada**

1. **Extraia o arquivo .zip** em uma pasta de sua escolha:
   ```
   Exemplo: C:\projetos\ecommerce-dashboard\
   ```

2. **Navegue até a pasta do projeto:**
   ```bash
   cd caminho/para/sua/pasta/ecommerce-dashboard
   ```

##  **Pré-requisitos**

Antes de começar, certifique-se de ter instalado:

- ✅ **Java 11 ou superior**
- ✅ **Maven 3.6+**
- ✅ **MySQL 8.0+**
- ✅ **IDE** (IntelliJ IDEA, Eclipse, VS Code)

##  **Configuração do Banco de Dados**

### **Passo 1: Criar o banco de dados**
```bash
# Conecte ao MySQL
mysql -u root -p

# Crie o banco de dados
CREATE DATABASE ecommerce_db;

# Saia do MySQL
exit;
```

### **Passo 2: Executar os scripts SQL**
```bash
# Execute os scripts na ordem (substitua pela sua senha do MySQL)
mysql -u root -p ecommerce_db < 01-create-tables.sql
mysql -u root -p ecommerce_db < 02-populate-tables.sql
```

##  **Configuração da Aplicação**

### **Passo 3: Configurar o arquivo `application.properties`**

Abra o arquivo `src/main/resources/application.properties` e configure:

```properties
# Configurações do Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_DO_MYSQL_AQUI

# Configurações da Aplicação
server.port=8080
server.servlet.context-path=/ecommerce-dashboard

# Configurações de Log
logging.level.com.ecommerce=DEBUG
```

** IMPORTANTE:** Substitua `SUA_SENHA_DO_MYSQL_AQUI` pela sua senha real do MySQL!

##  **Execução da Aplicação**

### **Passo 4: Executar o projeto**

```bash
# Compile o projeto
mvn clean compile

# Execute a aplicação
mvn spring-boot:run
```

### **Passo 5: Acessar a aplicação**

Após a execução, acesse:

- ** Dashboard:** http://localhost:8080/ecommerce-dashboard/
- ** Usuários:** http://localhost:8080/ecommerce-dashboard/usuarios
- ** Produtos:** http://localhost:8080/ecommerce-dashboard/produtos
- ** Consultas SQL:** http://localhost:8080/ecommerce-dashboard/consultas
- ** Gráficos:** http://localhost:8080/ecommerce-dashboard/graficos

##  **Consultas SQL Implementadas**

O sistema inclui **4 consultas analíticas** implementadas com SQL puro:

### **1. Usuários Completos** (Básico)
- Lista todos os usuários com nome completo e idade calculada
- **URL:** `/analise/usuarios`

### **2. Análise por Faixa de Preço** (Básico)
- Agrupa produtos por faixas de preço com estatísticas
- **URL:** `/analise/preco`

### **3. Produtos + Responsáveis** (Intermediário - JOIN)
- Relaciona produtos com usuários responsáveis usando LEFT JOIN
- **URL:** `/analise/produtos-responsaveis`

### **4. Usuários com Produtos** (Intermediário - JOIN)
- Analisa usuários com produtos que gerenciam e faixa etária
- **URL:** `/analise/idade`

##  **Tecnologias Utilizadas**

- **Java 11+**
- **Spring Boot 2.7.18**
- **Spring MVC** (sem JPA/ORM)
- **Thymeleaf** para templates
- **MySQL** com SQL puro
- **Bootstrap 5** para interface
- **Maven** para dependências

##  **Estrutura do Projeto**

```
├── src/main/java/com/ecommerce/
│   ├── entity/          # Entidades (Usuario, Produto, etc.)
│   ├── dao/             # DAOs com SQL puro
│   ├── service/         # Services com lógica de negócio
│   ├── controller/      # Controllers Web
│   ├── database/        # Conexão com banco
│   └── EcommerceDashboardApplication.java
├── src/main/resources/
│   ├── templates/       # Templates Thymeleaf
│   │   ├── dashboard/   # Páginas do dashboard
│   │   ├── usuarios/    # Páginas de usuários
│   │   ├── produtos/    # Páginas de produtos
│   │   ├── analise/     # Páginas de consultas
│   │   └── consultas/   # Página principal de consultas
│   ├── static/          # Arquivos estáticos (CSS, JS, imagens)
│   └── application.properties
├── 01-create-tables.sql # Script de criação das tabelas
├── 02-populate-tables.sql # Script de população das tabelas
├── 03-consultas.sql     # 4 consultas SQL implementadas
└── pom.xml              # Configuração Maven
```

##  **Funcionalidades**

- ✅ **Dashboard** com estatísticas em tempo real
- ✅ **CRUD de Usuários** (criar, listar, editar, deletar)
- ✅ **CRUD de Produtos** (criar, listar, editar, deletar)
- ✅ **4 Consultas SQL** implementadas com diferentes níveis
- ✅ **2 JOINs** implementados (consultas 3 e 4)
- ✅ **Interface moderna** com Bootstrap 5
- ✅ **SQL puro** - sem ORMs ou frameworks de mapeamento
- ✅ **Templates Thymeleaf** responsivos
- ✅ **Validação de formulários** com Spring MVC
- ✅ **Página de consultas** organizada e bonita

##  **Características Técnicas**

- **SQL Puro:** Todas as consultas usam SQL direto via JDBC
- **Sem ORM:** Nenhum framework de mapeamento objeto-relacional
- **JOINs:** 2 consultas implementam relacionamentos entre tabelas
- **Agregações:** COUNT, SUM, AVG, MIN, MAX implementados
- **Funções:** CASE WHEN, CONCAT, TIMESTAMPDIFF, COALESCE
- **Interface:** Bootstrap 5 com design responsivo

##  **Notas Importantes**

- ✅ **Todas as operações** usam SQL puro via JDBC
- ✅ **Sem dependências** de JPA, Hibernate ou outros ORMs
- ✅ **4 consultas funcionais** que fazem sentido para e-commerce
- ✅ **Interface completa** para todas as funcionalidades
- ✅ **Código limpo** sem arquivos inúteis

##  **Solução de Problemas**

### **Problema: Erro de conexão com MySQL**
```
Caused by: java.sql.SQLException: Access denied for user 'root'@'localhost'
```
**Solução:** Verifique se a senha no `application.properties` está correta.

### **Problema: Banco de dados não encontrado**
```
Caused by: java.sql.SQLException: Unknown database 'ecommerce_db'
```
**Solução:** Execute os scripts SQL na ordem correta:
```bash
mysql -u root -p ecommerce_db < 01-create-tables.sql
mysql -u root -p ecommerce_db < 02-populate-tables.sql
```

### **Problema: Porta 8080 já está em uso**
```
Port 8080 was already in use
```
**Solução:** 
- Pare outros serviços na porta 8080
- Ou altere a porta no `application.properties`:
```properties
server.port=8081
```

### **Problema: Maven não encontrado**
```
'mvn' is not recognized as an internal or external command
```
**Solução:** 
- Instale o Maven
- Ou use o wrapper: `./mvnw spring-boot:run` (Linux/Mac) ou `mvnw.cmd spring-boot:run` (Windows)


1. **Verifique os logs** da aplicação no console
2. **Confirme se o MySQL está rodando** na porta 3306
3. **Teste a conexão** com o banco manualmente
4. **Verifique se todas as dependências** estão instaladas

## **Resumo Rápido**

1. ✅ **Extraia a pasta zipada** em uma pasta de sua escolha
2. ✅ **Configure o MySQL** e execute os scripts SQL
3. ✅ **Edite o `application.properties`** com sua senha do MySQL
4. ✅ **Execute `mvn spring-boot:run`** na pasta do projeto
5. ✅ **Acesse** http://localhost:8080/ecommerce-dashboard/