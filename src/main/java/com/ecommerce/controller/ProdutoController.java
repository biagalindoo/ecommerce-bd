package com.ecommerce.controller;

import com.ecommerce.entity.Produto;
import com.ecommerce.service.ProdutoService;
import com.ecommerce.service.FornecedorProdutoService;
import com.ecommerce.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private FornecedorProdutoService fornecedorProdutoService;
    
    @Autowired
    private FornecedorService fornecedorService;
    
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
    
    @GetMapping("/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/form";
    }

    /**
     * Gerenciar fornecedores vinculados a um produto
     */
    @GetMapping("/{id}/fornecedores")
    public String listarFornecedoresDoProduto(@PathVariable Integer id, Model model) {
        Produto produto = produtoService.buscarPorId(id).orElse(null);
        if (produto == null) { return "redirect:/produtos"; }
        model.addAttribute("produto", produto);
        model.addAttribute("vinculos", fornecedorProdutoService.listarPorProduto(id));
        model.addAttribute("fornecedores", fornecedorService.listarTodos());
        return "produtos/fornecedores";
    }

    @PostMapping("/{id}/fornecedores")
    public String vincularFornecedor(@PathVariable Integer id,
                                     @RequestParam Integer fornecedorId,
                                     @RequestParam(required = false) Integer quantidadeFornecida,
                                     @RequestParam(required = false) java.math.BigDecimal custoUnitarioCompra) {
        try {
            fornecedorProdutoService.vincular(fornecedorId, id, quantidadeFornecida, custoUnitarioCompra);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/produtos/" + id + "/fornecedores";
    }

    @GetMapping("/{id}/fornecedores/remover")
    public String desvincularFornecedor(@PathVariable Integer id,
                                        @RequestParam Integer fornecedorId) {
        try {
            fornecedorProdutoService.desvincular(fornecedorId, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/produtos/" + id + "/fornecedores";
    }
    
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
    
    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Integer id, Model model) {
        Produto produto = produtoService.buscarPorId(id).orElse(null);
        if (produto == null) {
            return "redirect:/produtos";
        }
        model.addAttribute("produto", produto);
        return "produtos/form";
    }
    
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
    
    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Integer id) {
        try {
            produtoService.excluir(id);
        } catch (Exception e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/produtos";
    }
}