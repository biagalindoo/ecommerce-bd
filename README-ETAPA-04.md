# Etapa 04 - Consultas Avançadas, Visões e Índices

## 📋 Resumo da Implementação

Esta etapa implementa consultas avançadas, visões (views) e índices para otimização de performance no sistema de e-commerce.

## 🗂️ Arquivos Criados

### 1. Consultas Avançadas (`04-consultas-avancadas.sql`)
- **4 Consultas Avançadas** implementadas:
  - 1 Anti JOIN (LEFT JOIN com filtro NULL)
  - 1 Full Outer JOIN
  - 2 Subconsultas (WHERE e HAVING)

### 2. Índices (`04-consultas-avancadas.sql`)
- **4 Índices** criados para otimização:
  - `idx_usuario_data_nascimento` - Para consultas por idade
  - `idx_produto_preco` - Para análises de preço
  - `idx_pedido_status` - Para filtros por status
  - `idx_pedido_data` - Para análises temporais

### 3. Visões (Views) (`04-consultas-avancadas.sql`)
- **2 Visões** elaboradas com 3+ JOINs:
  - `vw_dashboard_vendas_usuario` - Dashboard consolidado de vendas
  - `vw_analise_produtos_fornecedores` - Análise completa de produtos

### 4. Interface Web
- **Controlador**: `ConsultasAvancadasController.java`
- **Templates HTML**:
  - `consultas-avancadas/index.html` - Página principal
  - `consultas-avancadas/resultado.html` - Resultados de usuários
  - `consultas-avancadas/resultado-produtos.html` - Resultados de produtos
  - `consultas-avancadas/dashboard-vendas.html` - Dashboard de vendas
  - `consultas-avancadas/produtos-fornecedores.html` - Análise de produtos

## 🔍 Consultas Implementadas

### 1. Anti JOIN - Usuários que Nunca Fizeram Pedidos
```sql
SELECT u.id, CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo,
       u.email, u.data_nascimento, TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) AS idade
FROM Usuario u
LEFT JOIN Pedido p ON u.id = p.usuario_id
WHERE p.id IS NULL
ORDER BY u.data_nascimento DESC;
```

### 2. Full Outer JOIN - Produtos e Fornecedores
```sql
SELECT COALESCE(p.id, fp.produto_id) AS produto_id,
       COALESCE(p.nome, 'Produto não encontrado') AS nome_produto,
       COALESCE(f.nome_fantasia, 'Fornecedor não encontrado') AS nome_fornecedor
FROM Produto p
FULL OUTER JOIN FornecedorProduto fp ON p.id = fp.produto_id
FULL OUTER JOIN Fornecedor f ON fp.fornecedor_id = f.id;
```

### 3. Subconsulta - Produtos Acima da Média do Armazém
```sql
SELECT p.id, p.nome, p.preco, a.nome AS nome_armazem,
       (SELECT ROUND(AVG(p2.preco), 2) FROM Produto p2 WHERE p2.armazem_id = p.armazem_id) AS preco_medio_armazem
FROM Produto p
LEFT JOIN Armazem a ON p.armazem_id = a.id
WHERE p.preco > (SELECT AVG(p4.preco) FROM Produto p4 WHERE p4.armazem_id = p.armazem_id);
```

### 4. Subconsulta - Usuários com Gastos Acima da Média
```sql
SELECT u.id, CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo,
       COUNT(p.id) AS total_pedidos, SUM(p.valor_total) AS valor_total_gasto
FROM Usuario u
INNER JOIN Pedido p ON u.id = p.usuario_id
WHERE p.status_pedido != 'cancelado'
GROUP BY u.id, u.primeiro_nome, u.sobrenome, u.email
HAVING SUM(p.valor_total) > (SELECT AVG(p3.valor_total) FROM Pedido p3 WHERE p3.status_pedido != 'cancelado');
```

## 👁️ Visões Implementadas

### 1. Dashboard de Vendas por Usuário (`vw_dashboard_vendas_usuario`)
**Justificativa**: Permite análise rápida do desempenho de vendas por usuário, incluindo dados pessoais, endereço, telefone e estatísticas de pedidos.

**JOINs utilizados**:
- Usuario ↔ Pedido (LEFT JOIN)
- Usuario ↔ Endereco (LEFT JOIN)  
- Usuario ↔ Telefone (LEFT JOIN)

**Campos agregados**:
- Total de pedidos por status
- Valor total gasto
- Valor médio por pedido
- Último pedido

### 2. Análise de Produtos e Fornecedores (`vw_analise_produtos_fornecedores`)
**Justificativa**: Permite análise completa do catálogo de produtos, incluindo informações de fornecedores, estoque, vendas e margem de lucro.

**JOINs utilizados**:
- Produto ↔ Armazem (LEFT JOIN)
- Produto ↔ FornecedorProduto (LEFT JOIN)
- FornecedorProduto ↔ Fornecedor (LEFT JOIN)
- Produto ↔ ItemPedido (LEFT JOIN)
- ItemPedido ↔ Pedido (LEFT JOIN)

**Campos calculados**:
- Margem de lucro
- Percentual de margem
- Status de estoque
- Receita total
- Total vendido

## 🚀 Como Executar

### 1. Executar Scripts SQL
```bash
# Executar no MySQL
mysql -u root -p < 04-consultas-avancadas.sql
mysql -u root -p < 05-executar-indices-visoes.sql
```

### 2. Executar Aplicação
```bash
# Compilar e executar
mvn clean compile
mvn spring-boot:run
```

### 3. Acessar Interface
- **URL Principal**: http://localhost:8080/consultas-avancadas
- **Menu**: Consultas Avançadas (na sidebar)

## 📊 Funcionalidades da Interface

### Página Principal
- Cards interativos para cada tipo de consulta
- Descrição detalhada de cada funcionalidade
- Estatísticas dos índices criados

### Páginas de Resultado
- Tabelas responsivas com dados formatados
- Estatísticas em tempo real
- Filtros interativos (onde aplicável)
- Análises de performance

### Dashboard de Vendas
- Visão consolidada de todos os usuários
- Top 5 usuários por valor gasto
- Distribuição por faixa etária
- Métricas de engajamento

### Análise de Produtos
- Catálogo completo com fornecedores
- Cálculos de margem de lucro
- Status de estoque colorido
- Filtros por status de estoque

## 🔧 Tecnologias Utilizadas

- **Backend**: Spring Boot, Java
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome
- **Banco de Dados**: MySQL
- **Otimização**: Índices, Visões, Consultas otimizadas

## 📈 Benefícios da Implementação

### Performance
- Índices otimizam consultas frequentes
- Visões pre-calculam dados complexos
- Redução significativa no tempo de resposta

### Análise de Dados
- Consultas avançadas revelam insights importantes
- Visões facilitam relatórios complexos
- Interface intuitiva para análise

### Manutenibilidade
- Código SQL organizado e documentado
- Separação clara entre lógica de negócio e apresentação
- Reutilização de consultas através de visões

## 🎯 Próximos Passos

1. **Monitoramento**: Implementar logs de performance das consultas
2. **Cache**: Adicionar cache para consultas frequentes
3. **Exportação**: Permitir exportação de relatórios em PDF/Excel
4. **Alertas**: Sistema de alertas para estoque baixo e usuários inativos
5. **Dashboards**: Gráficos interativos para as visões criadas
