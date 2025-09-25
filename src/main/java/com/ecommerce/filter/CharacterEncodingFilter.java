package com.ecommerce.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro para configurar encoding UTF-8
 */
public class CharacterEncodingFilter implements Filter {
    
    private String encoding = "UTF-8";
    private boolean forceEncoding = false;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null && !encodingParam.trim().isEmpty()) {
            this.encoding = encodingParam;
        }
        
        String forceEncodingParam = filterConfig.getInitParameter("forceEncoding");
        if (forceEncodingParam != null) {
            this.forceEncoding = Boolean.parseBoolean(forceEncodingParam);
        }
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        if (this.forceEncoding || httpRequest.getCharacterEncoding() == null) {
            httpRequest.setCharacterEncoding(this.encoding);
        }
        
        if (this.forceEncoding || httpResponse.getCharacterEncoding() == null) {
            httpResponse.setCharacterEncoding(this.encoding);
        }
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Limpeza se necessário
    }
}
