package com.ecommerce.repository;

import com.ecommerce.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para entidade Usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca usuário por email
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Busca usuários por nome (primeiro nome ou sobrenome)
     */
    @Query("SELECT u FROM Usuario u WHERE " +
           "LOWER(u.primeiroNome) LIKE LOWER(CONCAT('%', :nome, '%')) OR " +
           "LOWER(u.sobrenome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNomeContaining(@Param("nome") String nome);
    
    /**
     * Busca usuários por cidade
     */
    @Query("SELECT DISTINCT u FROM Usuario u " +
           "JOIN u.enderecos e " +
           "WHERE LOWER(e.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))")
    List<Usuario> findByCidade(@Param("cidade") String cidade);
    
    /**
     * Busca usuários por estado
     */
    @Query("SELECT DISTINCT u FROM Usuario u " +
           "JOIN u.enderecos e " +
           "WHERE LOWER(e.estado) LIKE LOWER(CONCAT('%', :estado, '%'))")
    List<Usuario> findByEstado(@Param("estado") String estado);
    
    /**
     * Verifica se email já existe (para validação)
     */
    boolean existsByEmail(String email);
    
    /**
     * Conta total de usuários
     */
    long count();
    
    /**
     * Busca usuários com pedidos
     */
    @Query("SELECT DISTINCT u FROM Usuario u " +
           "JOIN u.pedidos p " +
           "WHERE p IS NOT NULL")
    List<Usuario> findUsuariosComPedidos();
    
    /**
     * Busca usuários sem pedidos
     */
    @Query("SELECT u FROM Usuario u " +
           "LEFT JOIN u.pedidos p " +
           "WHERE p IS NULL")
    List<Usuario> findUsuariosSemPedidos();
}
