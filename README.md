# 🏪 E-commerce Dashboard

Dashboard simples em Java para gerenciar banco de dados e-commerce usando **SQL puro** (sem ORMs).

## 📋 Características

- ✅ **SQL Puro**: Sem ORMs ou frameworks de abstração
- ✅ **Interface Web**: Dashboard responsivo e intuitivo
- ✅ **CRUD Completo**: Inserção, visualização, alteração e deleção
- ✅ **2 Tabelas**: Usuario e Produto
- ✅ **Dashboard**: Estatísticas e visão geral do sistema

## 🛠️ Tecnologias Utilizadas

- **Java 11**
- **Servlet API 4.0**
- **JSP + JSTL**
- **MySQL 8.0**
- **Maven**
- **CSS3** (responsivo)

## 📦 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/ecommerce/
│   │       ├── database/
│   │       │   └── DatabaseConnection.java
│   │       ├── model/
│   │       │   ├── Usuario.java
│   │       │   └── Produto.java
│   │       ├── dao/
│   │       │   ├── UsuarioDAO.java
│   │       │   └── ProdutoDAO.java
│   │       └── servlet/
│   │           ├── DashboardServlet.java
│   │           ├── UsuarioServlet.java
│   │           └── ProdutoServlet.java
│   └── webapp/
│       └── WEB-INF/
│           ├── views/
│           │   ├── dashboard/
│           │   ├── usuario/
│           │   └── produto/
│           └── web.xml
├── pom.xml
└── README.md
```

## 🚀 Como Executar

### 1. Pré-requisitos

- **Java 11+**
- **Maven 3.6+**
- **MySQL 8.0+**
- **Tomcat 9.0+** (ou servidor similar)

### 2. Configuração do Banco

1. Execute os scripts SQL na ordem:
   ```bash
   mysql -u root -p < 01-create-tables.sql
   mysql -u root -p < 02-populate-tables.sql
   ```

2. Verifique se o banco `ecommerce_bd` foi criado com sucesso.

### 3. Configuração da Aplicação

1. **Clone ou baixe o projeto**

2. **Configure a conexão** no arquivo `DatabaseConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_bd";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "sua_senha_aqui";
   ```

3. **Compile o projeto**:
   ```bash
   mvn clean compile
   ```

4. **Gere o WAR**:
   ```bash
   mvn clean package
   ```

5. **Deploy no Tomcat**:
   - Copie o arquivo `target/ecommerce-dashboard.war` para a pasta `webapps` do Tomcat
   - Inicie o Tomcat

### 4. Acessar a Aplicação

Abra o navegador e acesse:
```
http://localhost:8080/ecommerce-dashboard/dashboard
```

## 📱 Funcionalidades

### Dashboard Principal
- 📊 Estatísticas gerais (usuários, produtos, pedidos)
- 💰 Valor total em estoque
- ⚠️ Produtos com estoque baixo
- 👥 Usuários recentes
- 💎 Produtos mais caros

### Gerenciamento de Usuários
- ➕ **Inserir**: Cadastrar novos usuários
- 👁️ **Visualizar**: Listar todos os usuários
- ✏️ **Alterar**: Editar dados dos usuários
- 🗑️ **Deletar**: Remover usuários
- 🔍 **Buscar**: Pesquisar por nome

### Gerenciamento de Produtos
- ➕ **Inserir**: Cadastrar novos produtos
- 👁️ **Visualizar**: Listar todos os produtos
- ✏️ **Alterar**: Editar dados dos produtos
- 🗑️ **Deletar**: Remover produtos
- 🔍 **Buscar**: Pesquisar por nome
- ⚠️ **Estoque Baixo**: Filtrar produtos com estoque < 10

## 🗄️ Operações SQL Implementadas

### Usuario (Tabela 1)
```sql
-- Inserir
INSERT INTO Usuario (email, senha_hash, cpf, primeiro_nome, sobrenome, data_nascimento) 
VALUES (?, ?, ?, ?, ?, ?)

-- Visualizar
SELECT * FROM Usuario ORDER BY primeiro_nome, sobrenome

-- Alterar
UPDATE Usuario SET email = ?, senha_hash = ?, cpf = ?, primeiro_nome = ?, 
                   sobrenome = ?, data_nascimento = ? WHERE id = ?

-- Deletar
DELETE FROM Usuario WHERE id = ?

-- Buscar
SELECT * FROM Usuario WHERE primeiro_nome LIKE ? OR sobrenome LIKE ?
```

### Produto (Tabela 2)
```sql
-- Inserir
INSERT INTO Produto (nome, descricao, preco, quantidade_estoque, armazem_id) 
VALUES (?, ?, ?, ?, ?)

-- Visualizar
SELECT p.*, a.nome as nome_armazem FROM Produto p 
LEFT JOIN Armazem a ON p.armazem_id = a.id ORDER BY p.nome

-- Alterar
UPDATE Produto SET nome = ?, descricao = ?, preco = ?, quantidade_estoque = ?, 
                   armazem_id = ? WHERE id = ?

-- Deletar
DELETE FROM Produto WHERE id = ?

-- Buscar
SELECT p.*, a.nome as nome_armazem FROM Produto p 
LEFT JOIN Armazem a ON p.armazem_id = a.id WHERE p.nome LIKE ?

-- Estoque Baixo
SELECT p.*, a.nome as nome_armazem FROM Produto p 
LEFT JOIN Armazem a ON p.armazem_id = a.id WHERE p.quantidade_estoque < 10
```

## 🎨 Interface

- **Design Responsivo**: Funciona em desktop, tablet e mobile
- **Cores Modernas**: Gradientes e paleta profissional
- **Navegação Intuitiva**: Menu claro e fácil de usar
- **Feedback Visual**: Mensagens de sucesso/erro
- **Validação**: Validação básica no frontend e backend

## 🔧 Configurações Adicionais

### Porta do Servidor
Para alterar a porta padrão (8080), edite o arquivo `server.xml` do Tomcat.

### Banco de Dados
Para usar outro banco, altere as configurações em `DatabaseConnection.java`.

### Logs
Os logs são exibidos no console do servidor de aplicação.

## 📝 Notas Importantes

- ✅ **SQL Puro**: Todas as operações usam SQL nativo
- ✅ **Sem ORMs**: Nenhum framework de mapeamento objeto-relacional
- ✅ **Interface Simples**: Dashboard básico e funcional
- ✅ **CRUD Completo**: Todas as operações implementadas
- ✅ **2 Tabelas**: Usuario e Produto com operações completas

## 🐛 Solução de Problemas

### Erro de Conexão com Banco
- Verifique se o MySQL está rodando
- Confirme usuário e senha em `DatabaseConnection.java`
- Verifique se o banco `ecommerce_bd` existe

### Erro 404
- Verifique se o WAR foi deployado corretamente
- Confirme a URL de acesso

### Erro de Compilação
- Verifique se o Java 11+ está instalado
- Confirme se o Maven está configurado

## 📞 Suporte

Para dúvidas ou problemas, verifique:
1. Logs do servidor de aplicação
2. Logs do MySQL
3. Configurações de conexão
4. Versões das dependências

---

**Desenvolvido com ❤️ usando Java e SQL puro**
