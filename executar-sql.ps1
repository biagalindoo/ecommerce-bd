# Script PowerShell para executar scripts SQL no MySQL
# Uso: .\executar-sql.ps1 -Arquivo "nome-do-arquivo.sql" -Usuario "root" -Senha "sua-senha"

param(
    [Parameter(Mandatory=$true)]
    [string]$Arquivo,
    
    [Parameter(Mandatory=$false)]
    [string]$Usuario = "root",
    
    [Parameter(Mandatory=$false)]
    [string]$Senha = "",
    
    [Parameter(Mandatory=$false)]
    [string]$Database = "ecommerce"
)

# Caminho do MySQL
$mysqlPath = "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"

# Verificar se o arquivo existe
if (-not (Test-Path $Arquivo)) {
    Write-Error "Arquivo $Arquivo não encontrado!"
    exit 1
}

# Verificar se o MySQL existe
if (-not (Test-Path $mysqlPath)) {
    Write-Error "MySQL não encontrado em $mysqlPath"
    Write-Host "Verifique se o MySQL está instalado corretamente."
    exit 1
}

Write-Host "Executando script SQL: $Arquivo" -ForegroundColor Green
Write-Host "Usuário: $Usuario" -ForegroundColor Yellow
Write-Host "Database: $Database" -ForegroundColor Yellow

# Construir comando
$comando = "& `"$mysqlPath`" -u $Usuario"

if ($Senha -ne "") {
    $comando += " -p$Senha"
}

$comando += " $Database < `"$Arquivo`""

Write-Host "Comando: $comando" -ForegroundColor Cyan

# Executar comando
try {
    Invoke-Expression $comando
    Write-Host "Script executado com sucesso!" -ForegroundColor Green
} catch {
    Write-Error "Erro ao executar script: $($_.Exception.Message)"
    Write-Host "Tente executar manualmente:" -ForegroundColor Yellow
    Write-Host "`"$mysqlPath`" -u $Usuario -p$Senha $Database < `"$Arquivo`"" -ForegroundColor Cyan
}

