CREATE INDEX IF NOT EXISTS idx_usuario_data_nascimento ON Usuario(data_nascimento);
CREATE INDEX IF NOT EXISTS idx_produto_preco ON Produto(preco);
CREATE INDEX IF NOT EXISTS idx_pedido_status ON Pedido(status_pedido);
CREATE INDEX IF NOT EXISTS idx_pedido_data ON Pedido(data_pedido);


-- VISÃO 1
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

-- VISÃO 2
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



SELECT 
    TABLE_NAME,
    INDEX_NAME,
    COLUMN_NAME,
    NON_UNIQUE
FROM INFORMATION_SCHEMA.STATISTICS 
WHERE TABLE_SCHEMA = DATABASE()
AND INDEX_NAME LIKE 'idx_%'
ORDER BY TABLE_NAME, INDEX_NAME;


SELECT 
    TABLE_NAME,
    TABLE_TYPE,
    TABLE_COMMENT
FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_SCHEMA = DATABASE()
AND TABLE_TYPE = 'VIEW'
AND TABLE_NAME LIKE 'vw_%'
ORDER BY TABLE_NAME;

-- Teste visoes
