# Use uma imagem base do OpenJDK 11
FROM openjdk:11-jdk-slim

# Instalar Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de configuração do Maven
COPY pom.xml .

# Baixar dependências (cache do Maven)
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Compilar a aplicação Spring Boot
RUN mvn clean package -DskipTests

# Expor porta da aplicação
EXPOSE 8080

# Comando para executar a aplicação Spring Boot
CMD ["java", "-jar", "target/ecommerce-dashboard-1.0.0.jar"]
