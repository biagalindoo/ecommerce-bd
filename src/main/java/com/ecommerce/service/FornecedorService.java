package com.ecommerce.service;

import com.ecommerce.dao.FornecedorDAO;
import com.ecommerce.entity.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    
    @Autowired
    private FornecedorDAO fornecedorDAO;
    
    public Optional<Fornecedor> buscarPorId(Integer id) { return fornecedorDAO.buscarPorId(id); }
    public List<Fornecedor> listarTodos() { return fornecedorDAO.listarTodos(); }
    public Fornecedor salvar(Fornecedor f) { return fornecedorDAO.salvar(f); }
    public Fornecedor atualizar(Fornecedor f) { return fornecedorDAO.atualizar(f); }
    public void excluir(Integer id) { fornecedorDAO.excluir(id); }
    public List<Fornecedor> buscarPorNome(String termo) { return fornecedorDAO.buscarPorNome(termo); }
}


