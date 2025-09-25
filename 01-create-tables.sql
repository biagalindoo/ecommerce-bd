CREATE TABLE IF NOT EXISTS Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    cpf CHAR(11) UNIQUE NOT NULL,
    primeiro_nome VARCHAR(50) NOT NULL,
    sobrenome VARCHAR(50) NOT NULL,
    data_nascimento DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Telefone (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(20) NOT NULL,
    usuario_id INT NOT NULL,
    CONSTRAINT fk_usuario
      FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
      ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS Endereco (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cep CHAR(8) NOT NULL,
    rua VARCHAR(100) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(50),
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    estado CHAR(2) NOT NULL,
    pais VARCHAR(50) DEFAULT 'Brasil',
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Carrinho (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultima_modificacao DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_id INT UNIQUE,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Armazem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) CHECK (preco >= 0),
    quantidade_estoque INT CHECK (quantidade_estoque >= 0),
    armazem_id INT,
    FOREIGN KEY (armazem_id) REFERENCES Armazem(id)
        ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS ItemCarrinho (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quantidade INT CHECK (quantidade > 0),
    preco_unitario_momento DECIMAL(10,2) NOT NULL,
    carrinho_id INT NOT NULL,
    produto_id INT NOT NULL,
    FOREIGN KEY (carrinho_id) REFERENCES Carrinho(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES Produto(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    hora_pedido TIME,
    status_pedido ENUM('aberto','pago','enviado','cancelado') DEFAULT 'aberto',
    observacao TEXT,
    valor_total DECIMAL(10,2) CHECK (valor_total >= 0),
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS ItemPedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quantidade INT CHECK (quantidade > 0),
    preco_unitario DECIMAL(10,2) NOT NULL,
    desconto DECIMAL(10,2) DEFAULT 0,
    subtotal DECIMAL(10,2) GENERATED ALWAYS AS ((quantidade * preco_unitario) - desconto) STORED,
    pedido_id INT NOT NULL,
    produto_id INT NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES Pedido(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES Produto(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Pagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(10,2) NOT NULL,
    forma_pagamento ENUM('cartao','pix','boleto') NOT NULL,
    status_pagamento ENUM('pendente','confirmado','falhou') DEFAULT 'pendente',
    data_pagamento DATETIME,
    pedido_id INT UNIQUE,
    FOREIGN KEY (pedido_id) REFERENCES Pedido(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Fornecedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    nome_fantasia VARCHAR(100),
    cnpj CHAR(14) UNIQUE,
    razao_social VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS FornecedorProduto (
    fornecedor_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade_fornecida INT CHECK (quantidade_fornecida > 0),
    custo_unitario_compra DECIMAL(10,2) CHECK (custo_unitario_compra >= 0),
    PRIMARY KEY (fornecedor_id, produto_id),
    FOREIGN KEY (fornecedor_id) REFERENCES Fornecedor(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES Produto(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

