package com.ecommerce.controller;

import com.ecommerce.entity.Produto;
import com.ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Controller para operações de Produto
 */
@Controller
@RequestMapping("/produto")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;
    
    /**
     * Lista todos os produtos
     */
    @GetMapping
    public String listar(@RequestParam(value = "nome", required = false) String nome,
                        @RequestParam(value = "categoria", required = false) String categoria,
                        @RequestParam(value = "estoqueBaixo", required = false) Boolean estoqueBaixo,
                        Model model) {
        
        List<Produto> produtos;
        
        if (nome != null && !nome.trim().isEmpty()) {
            produtos = produtoService.buscarPorNome(nome);
        } else if (categoria != null && !categoria.trim().isEmpty()) {
            produtos = produtoService.buscarPorCategoria(categoria);
        } else if (estoqueBaixo != null && estoqueBaixo) {
            produtos = produtoService.buscarProdutosComEstoqueBaixo(10);
        } else {
            produtos = produtoService.listarTodos();
        }
        
        model.addAttribute("produtos", produtos);
        model.addAttribute("totalProdutos", produtos.size());
        return "produto/list";
    }
    
    /**
     * Exibe formulário para novo produto
     */
    @GetMapping("/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/form";
    }
    
    /**
     * Salva novo produto
     */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("produto") Produto produto,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "produto/form";
        }
        
        try {
            produtoService.salvar(produto);
            redirectAttributes.addFlashAttribute("success", "Produto salvo com sucesso!");
            return "redirect:/produto";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao salvar produto: " + e.getMessage());
            return "produto/form";
        }
    }
    
    /**
     * Exibe formulário para editar produto
     */
    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Produto> produtoOpt = produtoService.buscarPorId(id);
        
        if (produtoOpt.isPresent()) {
            model.addAttribute("produto", produtoOpt.get());
            return "produto/edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Produto não encontrado");
            return "redirect:/produto";
        }
    }
    
    /**
     * Atualiza produto
     */
    @PostMapping("/atualizar")
    public String atualizar(@Valid @ModelAttribute("produto") Produto produto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "produto/edit";
        }
        
        try {
            produtoService.atualizar(produto);
            redirectAttributes.addFlashAttribute("success", "Produto atualizado com sucesso!");
            return "redirect:/produto";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar produto: " + e.getMessage());
            return "produto/edit";
        }
    }
    
    /**
     * Exclui produto
     */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            produtoService.excluir(id);
            redirectAttributes.addFlashAttribute("success", "Produto excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir produto: " + e.getMessage());
        }
        return "redirect:/produto";
    }
    
    /**
     * Exibe detalhes do produto
     */
    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Produto> produtoOpt = produtoService.buscarPorId(id);
        
        if (produtoOpt.isPresent()) {
            model.addAttribute("produto", produtoOpt.get());
            return "produto/detalhes";
        } else {
            redirectAttributes.addFlashAttribute("error", "Produto não encontrado");
            return "redirect:/produto";
        }
    }
    
    /**
     * Busca produtos com estoque baixo
     */
    @GetMapping("/estoque-baixo")
    public String estoqueBaixo(Model model) {
        List<Produto> produtos = produtoService.buscarProdutosComEstoqueBaixo(10);
        model.addAttribute("produtos", produtos);
        model.addAttribute("totalProdutos", produtos.size());
        return "produto/estoque-baixo";
    }
    
    /**
     * Busca produtos mais vendidos
     */
    @GetMapping("/mais-vendidos")
    public String maisVendidos(Model model) {
        List<Produto> produtos = produtoService.buscarProdutosMaisVendidos();
        model.addAttribute("produtos", produtos);
        model.addAttribute("totalProdutos", produtos.size());
        return "produto/mais-vendidos";
    }
}
