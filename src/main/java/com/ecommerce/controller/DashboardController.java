package com.ecommerce.controller;

import com.ecommerce.service.UsuarioService;
import com.ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller para o Dashboard principal
 */
@Controller
public class DashboardController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProdutoService produtoService;
    
    /**
     * Dashboard principal
     */
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        // Estatísticas gerais
        long totalUsuarios = usuarioService.contarTotal();
        long totalProdutos = produtoService.listarTodos().size();
        
        // Produtos com estoque baixo
        var produtosEstoqueBaixo = produtoService.buscarProdutosComEstoqueBaixo(10);
        int totalEstoqueBaixo = produtosEstoqueBaixo.size();
        
        // Produtos esgotados
        var produtosEsgotados = produtoService.buscarProdutosEsgotados();
        int totalEsgotados = produtosEsgotados.size();
        
        // Usuários com pedidos
        var usuariosComPedidos = usuarioService.buscarUsuariosComPedidos();
        int totalUsuariosComPedidos = usuariosComPedidos.size();
        
        // Usuários sem pedidos
        var usuariosSemPedidos = usuarioService.buscarUsuariosSemPedidos();
        int totalUsuariosSemPedidos = usuariosSemPedidos.size();
        
        // Produtos mais vendidos
        var produtosMaisVendidos = produtoService.buscarProdutosMaisVendidos();
        
        // Valor total do estoque
        var valorTotalEstoque = produtoService.calcularValorTotalEstoque().orElse(java.math.BigDecimal.ZERO);
        
        // Adiciona dados ao modelo
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalProdutos", totalProdutos);
        model.addAttribute("totalEstoqueBaixo", totalEstoqueBaixo);
        model.addAttribute("totalEsgotados", totalEsgotados);
        model.addAttribute("totalUsuariosComPedidos", totalUsuariosComPedidos);
        model.addAttribute("totalUsuariosSemPedidos", totalUsuariosSemPedidos);
        model.addAttribute("produtosEstoqueBaixo", produtosEstoqueBaixo);
        model.addAttribute("produtosEsgotados", produtosEsgotados);
        model.addAttribute("produtosMaisVendidos", produtosMaisVendidos);
        model.addAttribute("valorTotalEstoque", valorTotalEstoque);
        
        return "dashboard/index";
    }
}
