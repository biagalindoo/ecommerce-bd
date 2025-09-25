package com.ecommerce.database;

import com.ecommerce.config.EnvironmentConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe para gerenciar conexão com o banco de dados MySQL
 * Usa SQL puro sem ORMs ou frameworks de abstração
 * Configurações carregadas do arquivo .env
 */
public class DatabaseConnection {
    
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Configurações carregadas do arquivo .env
    private static final String URL = String.format("jdbc:mysql://%s:%d/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
            EnvironmentConfig.getDbHost(),
            EnvironmentConfig.getDbPort(),
            EnvironmentConfig.getDbName());
    private static final String USERNAME = EnvironmentConfig.getDbUser();
    private static final String PASSWORD = EnvironmentConfig.getDbPassword();
    
    private static DatabaseConnection instance;
    private Connection connection;
    
    private DatabaseConnection() {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter conexão: " + e.getMessage());
        }
        return connection;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
