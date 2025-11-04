package com.ecommerce.service;

import com.ecommerce.dao.FornecedorProdutoDAO;
import com.ecommerce.entity.FornecedorProdutoVinculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FornecedorProdutoService {
    @Autowired
    private FornecedorProdutoDAO fornecedorProdutoDAO;
    
    public List<FornecedorProdutoVinculo> listarPorProduto(Integer produtoId) {
        return fornecedorProdutoDAO.listarPorProduto(produtoId);
    }
    
    public void vincular(Integer fornecedorId, Integer produtoId, Integer quantidade, BigDecimal custo) {
        fornecedorProdutoDAO.vincular(fornecedorId, produtoId, quantidade, custo);
    }
    
    public void desvincular(Integer fornecedorId, Integer produtoId) {
        fornecedorProdutoDAO.desvincular(fornecedorId, produtoId);
    }
}


