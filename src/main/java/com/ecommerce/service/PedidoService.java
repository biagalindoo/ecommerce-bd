package com.ecommerce.service;

import com.ecommerce.dao.PedidoDAO;
import com.ecommerce.entity.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoDAO pedidoDAO;
    
    public Optional<Pedido> buscarPorId(Integer id) { return pedidoDAO.buscarPorId(id); }
    public List<Pedido> listarTodos() { return pedidoDAO.listarTodos(); }
    public Pedido salvar(Pedido p) { return pedidoDAO.salvar(p); }
    public Pedido atualizar(Pedido p) { return pedidoDAO.atualizar(p); }
    public void excluir(Integer id) { pedidoDAO.excluir(id); }
    public List<Pedido> buscarPorStatus(String status) { return pedidoDAO.buscarPorStatus(status); }

    public void atualizarStatusViaProcedure(Integer pedidoId, String novoStatus) {
        pedidoDAO.atualizarStatusViaProcedure(pedidoId, novoStatus);
    }
}


