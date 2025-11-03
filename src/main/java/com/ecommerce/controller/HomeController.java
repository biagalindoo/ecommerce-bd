package com.ecommerce.controller;

import com.ecommerce.entity.Produto;
import com.ecommerce.entity.Usuario;
import com.ecommerce.service.ProdutoService;
import com.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProdutoService produtoService;
    
    /**
     * Página principal do dashboard
     */
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        try {
            // Estatísticas gerais
            long totalUsuarios = usuarioService.contarTotal();
            long totalProdutos = produtoService.contarTotal();
            
            
            List<Produto> produtosEstoqueBaixo = produtoService.buscarProdutosComEstoqueBaixo(10);
            long totalEstoqueBaixo = produtosEstoqueBaixo.size();
           
            List<Produto> produtosEsgotados = produtoService.buscarProdutosEsgotados();
            long totalEsgotados = produtosEsgotados.size();
           
            List<Usuario> usuariosComPedidos = usuarioService.buscarUsuariosComPedidos();
            long totalUsuariosComPedidos = usuariosComPedidos.size();
            
            List<Usuario> usuariosSemPedidos = usuarioService.buscarUsuariosSemPedidos();
            long totalUsuariosSemPedidos = usuariosSemPedidos.size();
            
            
            List<Produto> todosProdutos = produtoService.listarTodos();
            
            BigDecimal valorTotalEstoque = produtoService.calcularValorTotalEstoque();
            
            model.addAttribute("totalUsuarios", totalUsuarios);
            model.addAttribute("totalProdutos", totalProdutos);
            model.addAttribute("totalEstoqueBaixo", totalEstoqueBaixo);
            model.addAttribute("totalEsgotados", totalEsgotados);
            model.addAttribute("totalUsuariosComPedidos", totalUsuariosComPedidos);
            model.addAttribute("totalUsuariosSemPedidos", totalUsuariosSemPedidos);
            model.addAttribute("produtosEstoqueBaixo", produtosEstoqueBaixo);
            model.addAttribute("produtosEsgotados", produtosEsgotados);
            model.addAttribute("todosProdutos", todosProdutos);
            model.addAttribute("valorTotalEstoque", valorTotalEstoque);
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados do dashboard: " + e.getMessage());
            e.printStackTrace();
            
           
            model.addAttribute("totalUsuarios", 0);
            model.addAttribute("totalProdutos", 0);
            model.addAttribute("totalEstoqueBaixo", 0);
            model.addAttribute("totalEsgotados", 0);
            model.addAttribute("totalUsuariosComPedidos", 0);
            model.addAttribute("totalUsuariosSemPedidos", 0);
            model.addAttribute("produtosEstoqueBaixo", List.of());
            model.addAttribute("produtosEsgotados", List.of());
            model.addAttribute("todosProdutos", List.of());
            model.addAttribute("valorTotalEstoque", BigDecimal.ZERO);
        }
        
        return "dashboard/index";
    }
}