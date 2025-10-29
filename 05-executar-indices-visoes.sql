-- =====================================================
-- SCRIPT PARA EXECUTAR ÍNDICES E VISÕES
-- =====================================================

-- Executar este script após criar as tabelas e popular com dados
-- para criar os índices e visões necessários para as consultas avançadas

-- =====================================================
-- CRIAR ÍNDICES
-- =====================================================

-- Índice 1: Para otimizar consultas por data de nascimento (usado em análises de idade)
CREATE INDEX IF NOT EXISTS idx_usuario_data_nascimento ON Usuario(data_nascimento);

-- Índice 2: Para otimizar consultas por preço de produto (usado em análises de preço)
CREATE INDEX IF NOT EXISTS idx_produto_preco ON Produto(preco);

-- Índice 3: Para otimizar consultas por status de pedido (usado em análises de vendas)
CREATE INDEX IF NOT EXISTS idx_pedido_status ON Pedido(status_pedido);

-- Índice 4: Para otimizar consultas por data de pedido (usado em análises temporais)
CREATE INDEX IF NOT EXISTS idx_pedido_data ON Pedido(data_pedido);

-- =====================================================
-- CRIAR VISÕES
-- =====================================================

-- VISÃO 1: Dashboard de Vendas por Usuário
-- Descrição: Visão consolidada com informações completas de vendas por usuário
-- Justificativa: Permite análise rápida do desempenho de vendas por usuário,
-- incluindo dados pessoais, endereço, telefone e estatísticas de pedidos
CREATE OR REPLACE VIEW vw_dashboard_vendas_usuario AS
SELECT 
    u.id AS usuario_id,
    CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo,
    u.email,
    u.cpf,
    TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) AS idade,
    CONCAT(e.rua, ', ', e.numero, ' - ', e.bairro, ', ', e.cidade, '/', e.estado) AS endereco_completo,
    t.numero AS telefone,
    COUNT(DISTINCT p.id) AS total_pedidos,
    COUNT(DISTINCT CASE WHEN p.status_pedido = 'pago' THEN p.id END) AS pedidos_pagos,
    COUNT(DISTINCT CASE WHEN p.status_pedido = 'enviado' THEN p.id END) AS pedidos_enviados,
    COUNT(DISTINCT CASE WHEN p.status_pedido = 'cancelado' THEN p.id END) AS pedidos_cancelados,
    COALESCE(SUM(CASE WHEN p.status_pedido != 'cancelado' THEN p.valor_total ELSE 0 END), 0) AS valor_total_gasto,
    ROUND(COALESCE(AVG(CASE WHEN p.status_pedido != 'cancelado' THEN p.valor_total END), 0), 2) AS valor_medio_pedido,
    MAX(p.data_pedido) AS ultimo_pedido
FROM Usuario u
LEFT JOIN Pedido p ON u.id = p.usuario_id
LEFT JOIN Endereco e ON u.id = e.usuario_id
LEFT JOIN Telefone t ON u.id = t.usuario_id
GROUP BY u.id, u.primeiro_nome, u.sobrenome, u.email, u.cpf, u.data_nascimento, 
         e.rua, e.numero, e.bairro, e.cidade, e.estado, t.numero;

-- VISÃO 2: Análise Completa de Produtos e Fornecedores
-- Descrição: Visão detalhada com informações de produtos, fornecedores e performance de vendas
-- Justificativa: Permite análise completa do catálogo de produtos, incluindo
-- informações de fornecedores, estoque, vendas e margem de lucro
CREATE OR REPLACE VIEW vw_analise_produtos_fornecedores AS
SELECT 
    p.id AS produto_id,
    p.nome AS nome_produto,
    p.descricao,
    p.preco AS preco_venda,
    p.quantidade_estoque,
    a.nome AS nome_armazem,
    f.nome_fantasia AS fornecedor_principal,
    f.razao_social AS razao_social_fornecedor,
    fp.custo_unitario_compra,
    ROUND(p.preco - COALESCE(fp.custo_unitario_compra, 0), 2) AS margem_lucro,
    ROUND(((p.preco - COALESCE(fp.custo_unitario_compra, 0)) / p.preco) * 100, 2) AS percentual_margem,
    COUNT(DISTINCT ip.pedido_id) AS total_pedidos,
    COALESCE(SUM(ip.quantidade), 0) AS total_vendido,
    COALESCE(SUM(ip.subtotal), 0) AS receita_total,
    ROUND(COALESCE(AVG(ip.preco_unitario), 0), 2) AS preco_medio_vendido,
    CASE 
        WHEN p.quantidade_estoque = 0 THEN 'Sem estoque'
        WHEN p.quantidade_estoque < 10 THEN 'Estoque baixo'
        WHEN p.quantidade_estoque < 50 THEN 'Estoque médio'
        ELSE 'Estoque alto'
    END AS status_estoque
FROM Produto p
LEFT JOIN Armazem a ON p.armazem_id = a.id
LEFT JOIN FornecedorProduto fp ON p.id = fp.produto_id
LEFT JOIN Fornecedor f ON fp.fornecedor_id = f.id
LEFT JOIN ItemPedido ip ON p.id = ip.produto_id
LEFT JOIN Pedido ped ON ip.pedido_id = ped.id AND ped.status_pedido != 'cancelado'
GROUP BY p.id, p.nome, p.descricao, p.preco, p.quantidade_estoque, a.nome,
         f.nome_fantasia, f.razao_social, fp.custo_unitario_compra;

-- =====================================================
-- VERIFICAR ÍNDICES CRIADOS
-- =====================================================

-- Consulta para verificar os índices criados
SELECT 
    TABLE_NAME,
    INDEX_NAME,
    COLUMN_NAME,
    NON_UNIQUE
FROM INFORMATION_SCHEMA.STATISTICS 
WHERE TABLE_SCHEMA = DATABASE()
AND INDEX_NAME LIKE 'idx_%'
ORDER BY TABLE_NAME, INDEX_NAME;

-- =====================================================
-- VERIFICAR VISÕES CRIADAS
-- =====================================================

-- Consulta para verificar as visões criadas
SELECT 
    TABLE_NAME,
    TABLE_TYPE,
    TABLE_COMMENT
FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_SCHEMA = DATABASE()
AND TABLE_TYPE = 'VIEW'
AND TABLE_NAME LIKE 'vw_%'
ORDER BY TABLE_NAME;

-- =====================================================
-- TESTAR VISÕES
-- =====================================================

-- Teste da Visão 1: Dashboard de Vendas por Usuário
-- SELECT * FROM vw_dashboard_vendas_usuario ORDER BY valor_total_gasto DESC LIMIT 10;

-- Teste da Visão 2: Análise Completa de Produtos e Fornecedores
-- SELECT * FROM vw_analise_produtos_fornecedores WHERE status_estoque = 'Estoque baixo' ORDER BY total_vendido DESC;
