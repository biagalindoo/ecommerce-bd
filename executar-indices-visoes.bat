@echo off
echo Executando scripts SQL para indices e visoes...
echo.

REM
set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"

REM
if not exist %MYSQL_PATH% (
    echo ERRO: MySQL nao encontrado em %MYSQL_PATH%
    echo Verifique se o MySQL esta instalado corretamente.
    pause
    exit /b 1
)

echo Executando 04-consultas-avancadas.sql...
%MYSQL_PATH% -u root -p ecommerce < 04-consultas-avancadas.sql
if %errorlevel% neq 0 (
    echo ERRO ao executar 04-consultas-avancadas.sql
    pause
    exit /b 1
)

echo.
echo Executando 05-executar-indices-visoes.sql...
%MYSQL_PATH% -u root -p ecommerce < 05-executar-indices-visoes.sql
if %errorlevel% neq 0 (
    echo ERRO ao executar 05-executar-indices-visoes.sql
    pause
    exit /b 1
)

echo.
echo Scripts executados com sucesso!
echo.
echo Para verificar se os indices foram criados, execute:
echo %MYSQL_PATH% -u root -p ecommerce -e "SHOW INDEX FROM Usuario;"
echo %MYSQL_PATH% -u root -p ecommerce -e "SHOW INDEX FROM Produto;"
echo %MYSQL_PATH% -u root -p ecommerce -e "SHOW INDEX FROM Pedido;"
echo.
echo Para verificar as visoes, execute:
echo %MYSQL_PATH% -u root -p ecommerce -e "SHOW TABLES LIKE 'vw_%';"
echo.
pause

