package com.ecommerce.service;

import com.ecommerce.dao.UsuarioDAO;
import com.ecommerce.entity.Usuario;
import com.ecommerce.entity.UsuarioCompleto;
import com.ecommerce.entity.UsuarioProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioDAO usuarioDAO;
   
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioDAO.buscarPorId(id);
    }
   
    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }
    
    public Usuario salvar(Usuario usuario) {
        return usuarioDAO.salvar(usuario);
    }
    
    public Usuario atualizar(Usuario usuario) {
        return usuarioDAO.atualizar(usuario);
    }
    
    public void excluir(Integer id) {
        usuarioDAO.excluir(id);
    }
    
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioDAO.buscarPorNome(nome);
    }
    
    public long contarTotal() {
        return usuarioDAO.contarTotal();
    }
    
    public boolean emailExiste(String email) {
        return usuarioDAO.emailExiste(email);
    }
    
    public List<Usuario> buscarUsuariosComPedidos() {
        // Retorna todos os usuários por enquanto
        return usuarioDAO.listarTodos();
    }
    
    public List<Usuario> buscarUsuariosSemPedidos() {
        // Retorna lista vazia por enquanto
        return List.of();
    }
    
    public List<UsuarioCompleto> listarUsuariosCompletos() {
        return usuarioDAO.listarUsuariosCompletos();
    }
    
    
    public List<UsuarioProduto> listarUsuariosComProdutos() {
        return usuarioDAO.listarUsuariosComProdutos();
    }
}