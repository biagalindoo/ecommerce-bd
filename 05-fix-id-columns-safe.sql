-- Script seguro para corrigir tipos de dados das colunas ID
-- Desabilita verificações de FK temporariamente

USE ecommerce_bd;

-- Desabilitar verificações de chave estrangeira
SET FOREIGN_KEY_CHECKS = 0;

-- Alterar tabela Armazem
ALTER TABLE Armazem MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela Usuario  
ALTER TABLE Usuario MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela Telefone
ALTER TABLE Telefone MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela Endereco
ALTER TABLE Endereco MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela Produto
ALTER TABLE Produto MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela Carrinho
ALTER TABLE Carrinho MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela ItemCarrinho
ALTER TABLE ItemCarrinho MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela Pedido
ALTER TABLE Pedido MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela ItemPedido
ALTER TABLE ItemPedido MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela Pagamento
ALTER TABLE Pagamento MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela Fornecedor
ALTER TABLE Fornecedor MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Alterar tabela FornecedorProduto
ALTER TABLE FornecedorProduto MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Reabilitar verificações de chave estrangeira
SET FOREIGN_KEY_CHECKS = 1;

-- Verificar se as alterações foram aplicadas
SELECT 
    TABLE_NAME,
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = 'ecommerce_bd' 
AND COLUMN_NAME = 'id'
ORDER BY TABLE_NAME;
