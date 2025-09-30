package com.ecommerce.controller;

import com.ecommerce.entity.*;
import com.ecommerce.service.ProdutoService;
import com.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/analise")
public class AnaliseController {

    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/categoria")
    public String analisePorCategoria(Model model) {
        List<AnaliseCategoria> analises = produtoService.analisarPorCategoria();
        model.addAttribute("analises", analises);
        return "analise/categoria";
    }
    
    @GetMapping("/usuarios")
    public String listarUsuariosCompletos(Model model) {
        List<UsuarioCompleto> usuarios = usuarioService.listarUsuariosCompletos();
        model.addAttribute("usuarios", usuarios);
        return "analise/usuarios";
    }
    
    @GetMapping("/preco")
    public String analisePorPreco(Model model) {
        List<AnalisePreco> analises = produtoService.analisarPorPreco();
        model.addAttribute("analises", analises);
        return "analise/preco";
    }
    
    @GetMapping("/produtos-responsaveis")
    public String produtosComResponsaveis(Model model) {
        List<ProdutoResponsavel> produtos = produtoService.listarProdutosComResponsaveis();
        model.addAttribute("produtos", produtos);
        return "analise/produtos-responsaveis";
    }
    
    @GetMapping("/idade")
    public String analisePorIdade(Model model) {
        List<UsuarioProduto> usuarios = usuarioService.listarUsuariosComProdutos();
        model.addAttribute("usuarios", usuarios);
        return "analise/idade";
    }
}
