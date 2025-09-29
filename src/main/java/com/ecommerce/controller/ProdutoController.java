package com.ecommerce.controller;

import com.ecommerce.entity.Produto;
import com.ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para operações de Produto
 */
@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;
    
    /**
     * Lista todos os produtos
     */
    @GetMapping
    public String listar(@RequestParam(value = "nome", required = false) String nome,
                        Model model) {
        
        List<Produto> produtos;
        
        if (nome != null && !nome.trim().isEmpty()) {
            produtos = produtoService.buscarPorNome(nome);
        } else {
            produtos = produtoService.listarTodos();
        }
        
        model.addAttribute("produtos", produtos);
        model.addAttribute("totalProdutos", produtos.size());
        return "produtos/list";
    }
    
    /**
     * Formulário para novo produto
     */
    @GetMapping("/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/form";
    }
    
    /**
     * Salvar novo produto
     */
    @PostMapping("/novo")
    public String salvarProduto(@ModelAttribute Produto produto) {
        try {
            produtoService.salvar(produto);
            return "redirect:/produtos";
        } catch (Exception e) {
            System.err.println("Erro ao salvar produto: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/produtos";
        }
    }
    
    /**
     * Formulário para editar produto
     */
    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Integer id, Model model) {
        Produto produto = produtoService.buscarPorId(id).orElse(null);
        if (produto == null) {
            return "redirect:/produtos";
        }
        model.addAttribute("produto", produto);
        return "produtos/form";
    }
    
    /**
     * Atualizar produto
     */
    @PostMapping("/editar")
    public String atualizarProduto(@ModelAttribute Produto produto) {
        try {
            produtoService.atualizar(produto);
            return "redirect:/produtos";
        } catch (Exception e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/produtos";
        }
    }
    
    /**
     * Excluir produto
     */
    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Integer id) {
        try {
            produtoService.excluir(id);
        } catch (Exception e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/ecommerce-dashboard/produtos";
    }
}