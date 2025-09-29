#!/bin/bash

# Script para iniciar a aplicação E-commerce Dashboard
echo "🚀 Iniciando E-commerce Dashboard..."

# Verificar se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker não está rodando. Por favor, inicie o Docker Desktop."
    exit 1
fi

# Verificar se o arquivo .env existe
if [ ! -f .env ]; then
    echo "⚠️  Arquivo .env não encontrado. Criando a partir do template..."
    cp env.example .env
    echo "✅ Arquivo .env criado. Por favor, configure suas credenciais de banco de dados."
    echo "📝 Edite o arquivo .env com suas configurações de banco de dados."
    exit 1
fi

# Construir e iniciar os containers
echo "🔨 Construindo e iniciando containers..."
docker-compose up --build -d

# Aguardar a aplicação ficar pronta
echo "⏳ Aguardando aplicação ficar pronta..."
sleep 10

# Verificar se a aplicação está rodando
if curl -f http://localhost:8080/ecommerce-dashboard/dashboard > /dev/null 2>&1; then
    echo "✅ Aplicação iniciada com sucesso!"
    echo "🌐 Acesse: http://localhost:8080/ecommerce-dashboard/dashboard"
    echo "📊 Dashboard: http://localhost:8080/ecommerce-dashboard/dashboard"
    echo "👥 Usuários: http://localhost:8080/ecommerce-dashboard/usuario"
    echo "📦 Produtos: http://localhost:8080/ecommerce-dashboard/produto"
else
    echo "❌ Erro ao iniciar a aplicação. Verifique os logs:"
    echo "docker-compose logs"
fi
