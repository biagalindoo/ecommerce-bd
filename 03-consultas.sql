
-- CONSULTAS SQL - DIFERENTES NÍVEIS DE DIFICULDADE


-- CONSULTA 1: SIMPLES - Listar todos os usuários com informações básicas
-- Nível: Básico
-- Descrição: Lista todos os usuários com nome completo e email
-- Dificuldade: Baixa

SELECT 
    id,
    CONCAT(primeiro_nome, ' ', sobrenome) AS nome_completo,
    email,
    cpf,
    data_nascimento,
    TIMESTAMPDIFF(YEAR, data_nascimento, CURDATE()) AS idade
FROM Usuario
ORDER BY primeiro_nome, sobrenome;


-- CONSULTA 2: SIMPLES - Análise de produtos por faixa de preço
-- Nível: Básico
-- Descrição: Conta produtos por faixas de preço
-- Dificuldade: Baixa

SELECT 
    CASE 
        WHEN preco < 50 THEN 'Até R$ 50'
        WHEN preco BETWEEN 50 AND 100 THEN 'R$ 50 - R$ 100'
        WHEN preco BETWEEN 100 AND 200 THEN 'R$ 100 - R$ 200'
        WHEN preco BETWEEN 200 AND 500 THEN 'R$ 200 - R$ 500'
        ELSE 'Acima de R$ 500'
    END AS faixa_preco,
    COUNT(*) AS total_produtos,
    ROUND(AVG(preco), 2) AS preco_medio,
    SUM(quantidade_estoque) AS total_estoque
FROM Produto
GROUP BY faixa_preco
ORDER BY MIN(preco);


-- CONSULTA 3: COM JOIN - Produtos e usuários por armazém (simulado)
-- Nível: Intermediário
-- Descrição: Relaciona produtos com "usuários responsáveis" baseado no armazem_id
-- Dificuldade: Média

SELECT 
    p.nome AS produto,
    p.preco,
    p.quantidade_estoque,
    p.armazem_id,
    CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS responsavel_armazem,
    u.email AS email_responsavel
FROM Produto p
LEFT JOIN Usuario u ON p.armazem_id = u.id
WHERE p.quantidade_estoque > 0
ORDER BY p.armazem_id, p.nome;


-- CONSULTA 4: AVANÇADA - Análise de usuários por faixa etária com produtos
-- Nível: Intermediário
-- Descrição: Analisa usuários agrupados por faixa etária e relaciona com produtos que gerenciam
-- Dificuldade: Média

SELECT 
    u.id,
    CONCAT(u.primeiro_nome, ' ', u.sobrenome) AS nome_completo,
    u.email,
    CASE 
        WHEN TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) < 18 THEN 'Menores de 18'
        WHEN TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) BETWEEN 18 AND 25 THEN '18-25 anos'
        WHEN TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) BETWEEN 26 AND 35 THEN '26-35 anos'
        WHEN TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) BETWEEN 36 AND 50 THEN '36-50 anos'
        ELSE 'Acima de 50 anos'
    END AS faixa_etaria,
    TIMESTAMPDIFF(YEAR, u.data_nascimento, CURDATE()) AS idade,
    COUNT(p.id) AS produtos_gerenciados,
    COALESCE(SUM(p.preco * p.quantidade_estoque), 0) AS valor_total_produtos
FROM Usuario u
LEFT JOIN Produto p ON u.id = p.armazem_id
WHERE u.data_nascimento IS NOT NULL
GROUP BY u.id, u.primeiro_nome, u.sobrenome, u.email, u.data_nascimento
ORDER BY idade, nome_completo;

