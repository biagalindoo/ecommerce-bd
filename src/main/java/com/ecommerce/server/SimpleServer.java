package com.ecommerce.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleServer {
    
    private static final int PORT = 8080;
    private static final String WEB_ROOT = "src/main/webapp";
    
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        // Servir arquivos estáticos
        server.createContext("/", new StaticFileHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("🚀 Servidor iniciado na porta " + PORT);
        System.out.println("📱 Acesse: http://localhost:" + PORT + "/dashboard");
        System.out.println("⏹️  Para parar, pressione Ctrl+C");
    }
    
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            
            if (path.equals("/") || path.equals("/dashboard")) {
                path = "/WEB-INF/views/dashboard/index.jsp";
            }
            
            File file = new File(WEB_ROOT + path);
            
            if (!file.exists()) {
                String response = "404 - Página não encontrada";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }
            
            String contentType = getContentType(path);
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, file.length());
            
            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream os = exchange.getResponseBody()) {
                
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        }
        
        private String getContentType(String path) {
            if (path.endsWith(".html") || path.endsWith(".jsp")) {
                return "text/html; charset=UTF-8";
            } else if (path.endsWith(".css")) {
                return "text/css";
            } else if (path.endsWith(".js")) {
                return "application/javascript";
            } else if (path.endsWith(".png")) {
                return "image/png";
            } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                return "image/jpeg";
            }
            return "text/plain";
        }
    }
}
