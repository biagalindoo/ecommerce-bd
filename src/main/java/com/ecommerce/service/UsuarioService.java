package com.ecommerce.service;

import com.ecommerce.entity.Usuario;
import com.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para lógica de negócio de Usuario
 */
@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /**
     * Salva um usuário
     */
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Busca usuário por ID
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    /**
     * Lista todos os usuários
     */
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    /**
     * Busca usuários por nome
     */
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContaining(nome);
    }
    
    /**
     * Busca usuário por email
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    /**
     * Busca usuários por cidade
     */
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorCidade(String cidade) {
        return usuarioRepository.findByCidade(cidade);
    }
    
    /**
     * Busca usuários por estado
     */
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorEstado(String estado) {
        return usuarioRepository.findByEstado(estado);
    }
    
    /**
     * Exclui um usuário
     */
    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    /**
     * Verifica se email já existe
     */
    @Transactional(readOnly = true)
    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    /**
     * Conta total de usuários
     */
    @Transactional(readOnly = true)
    public long contarTotal() {
        return usuarioRepository.count();
    }
    
    /**
     * Busca usuários com pedidos
     */
    @Transactional(readOnly = true)
    public List<Usuario> buscarUsuariosComPedidos() {
        return usuarioRepository.findUsuariosComPedidos();
    }
    
    /**
     * Busca usuários sem pedidos
     */
    @Transactional(readOnly = true)
    public List<Usuario> buscarUsuariosSemPedidos() {
        return usuarioRepository.findUsuariosSemPedidos();
    }
    
    /**
     * Atualiza um usuário
     */
    public Usuario atualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
