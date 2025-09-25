package com.ecommerce.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe para carregar configurações do arquivo .env
 */
public class EnvironmentConfig {
    
    private static Properties properties;
    private static final String ENV_FILE = ".env";
    
    static {
        loadProperties();
    }
    
    /**
     * Carrega as propriedades do arquivo .env
     */
    private static void loadProperties() {
        properties = new Properties();
        
        try (InputStream input = new FileInputStream(ENV_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo .env: " + e.getMessage());
            // Usar valores padrão se o arquivo não for encontrado
            setDefaultProperties();
        }
    }
    
    /**
     * Define propriedades padrão caso o arquivo .env não seja encontrado
     */
    private static void setDefaultProperties() {
        properties.setProperty("DB_HOST", "localhost");
        properties.setProperty("DB_PORT", "3306");
        properties.setProperty("DB_NAME", "ecommerce_bd");
        properties.setProperty("DB_USER", "root");
        properties.setProperty("DB_PASSWORD", "biafera123");
        properties.setProperty("APP_PORT", "8080");
        properties.setProperty("APP_CONTEXT_PATH", "/ecommerce-dashboard");
        properties.setProperty("DB_CONNECTION_TIMEOUT", "30000");
        properties.setProperty("DB_MAX_CONNECTIONS", "10");
    }
    
    /**
     * Obtém uma propriedade do arquivo .env
     * @param key chave da propriedade
     * @return valor da propriedade
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Obtém uma propriedade do arquivo .env com valor padrão
     * @param key chave da propriedade
     * @param defaultValue valor padrão caso a propriedade não seja encontrada
     * @return valor da propriedade ou valor padrão
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Obtém uma propriedade inteira do arquivo .env
     * @param key chave da propriedade
     * @param defaultValue valor padrão caso a propriedade não seja encontrada
     * @return valor inteiro da propriedade ou valor padrão
     */
    public static int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    // Métodos específicos para configurações do banco
    public static String getDbHost() {
        return getProperty("DB_HOST", "localhost");
    }
    
    public static int getDbPort() {
        return getIntProperty("DB_PORT", 3306);
    }
    
    public static String getDbName() {
        return getProperty("DB_NAME", "ecommerce_bd");
    }
    
    public static String getDbUser() {
        return getProperty("DB_USER", "root");
    }
    
    public static String getDbPassword() {
        return getProperty("DB_PASSWORD", "biafera123");
    }
    
    public static int getAppPort() {
        return getIntProperty("APP_PORT", 8080);
    }
    
    public static String getAppContextPath() {
        return getProperty("APP_CONTEXT_PATH", "/ecommerce-dashboard");
    }
    
    public static int getDbConnectionTimeout() {
        return getIntProperty("DB_CONNECTION_TIMEOUT", 30000);
    }
    
    public static int getDbMaxConnections() {
        return getIntProperty("DB_MAX_CONNECTIONS", 10);
    }
}
