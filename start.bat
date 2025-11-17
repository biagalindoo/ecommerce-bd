@echo off
echo  Iniciando E-commerce Dashboard...

REM 
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo  Docker não está rodando. Por favor, inicie o Docker Desktop.
    pause
    exit /b 1
)

REM
if not exist .env (
    echo   Arquivo .env não encontrado. Criando a partir do template...
    copy env.example .env
    echo  Arquivo .env criado. Por favor, configure suas credenciais de banco de dados.
    echo  Edite o arquivo .env com suas configurações de banco de dados.
    pause
    exit /b 1
)

REM 
echo  Construindo e iniciando containers...
docker-compose up --build -d

REM 
echo  Aguardando aplicação ficar pronta...
timeout /t 10 /nobreak >nul

REM 
curl -f http://localhost:8080/ecommerce-dashboard/dashboard >nul 2>&1
if %errorlevel% equ 0 (
    echo  Aplicação iniciada com sucesso!
    echo  Acesse: http://localhost:8080/ecommerce-dashboard/dashboard
    echo  Dashboard: http://localhost:8080/ecommerce-dashboard/dashboard
    echo  Usuários: http://localhost:8080/ecommerce-dashboard/usuario
    echo  Produtos: http://localhost:8080/ecommerce-dashboard/produto
) else (
    echo  Erro ao iniciar a aplicação. Verifique os logs:
    echo docker-compose logs
)

pause
