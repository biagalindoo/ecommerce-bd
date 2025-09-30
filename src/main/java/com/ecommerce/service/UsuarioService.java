package com.ecommerce.service;

import com.ecommerce.dao.UsuarioDAO;
import com.ecommerce.entity.Usuario;
import com.ecommerce.entity.UsuarioCompleto;
import com.ecommerce.entity.AnaliseIdade;
import com.ecommerce.entity.UsuarioProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service para operações de Usuario usando SQL puro
 */
@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioDAO usuarioDAO;
    
    /**
     * Busca usuário por ID
     */
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioDAO.buscarPorId(id);
    }
    
    /**
     * Lista todos os usuários
     */
    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }
    
    /**
     * Salva um usuário
     */
    public Usuario salvar(Usuario usuario) {
        return usuarioDAO.salvar(usuario);
    }
    
    /**
     * Atualiza um usuário
     */
    public Usuario atualizar(Usuario usuario) {
        return usuarioDAO.atualizar(usuario);
    }
    
    /**
     * Exclui um usuário
     */
    public void excluir(Integer id) {
        usuarioDAO.excluir(id);
    }
    
    /**
     * Busca usuários por nome
     */
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioDAO.buscarPorNome(nome);
    }
    
    /**
     * Conta total de usuários
     */
    public long contarTotal() {
        return usuarioDAO.contarTotal();
    }
    
    /**
     * Verifica se email já existe
     */
    public boolean emailExiste(String email) {
        return usuarioDAO.emailExiste(email);
    }
    
    /**
     * Busca usuários com pedidos (simplificado)
     */
    public List<Usuario> buscarUsuariosComPedidos() {
        // Retorna todos os usuários por enquanto
        return usuarioDAO.listarTodos();
    }
    
    /**
     * Busca usuários sem pedidos (simplificado)
     */
    public List<Usuario> buscarUsuariosSemPedidos() {
        // Retorna lista vazia por enquanto
        return List.of();
    }
    
    /**
     * Lista todos os usuários com informações completas
     */
    public List<UsuarioCompleto> listarUsuariosCompletos() {
        return usuarioDAO.listarUsuariosCompletos();
    }
    
    /**
     * Análise de usuários por faixa etária
     */
    public List<AnaliseIdade> analisarPorIdade() {
        return usuarioDAO.analisarPorIdade();
    }
    
    /**
     * Lista usuários com análise de produtos gerenciados (JOIN)
     */
    public List<UsuarioProduto> listarUsuariosComProdutos() {
        return usuarioDAO.listarUsuariosComProdutos();
    }
}