package com.ecommerce.controller;

import com.ecommerce.entity.Fornecedor;
import com.ecommerce.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/fornecedores")
public class FornecedorController {
    
    @Autowired
    private FornecedorService fornecedorService;
    
    @GetMapping
    public String listar(@RequestParam(value = "q", required = false) String termo, Model model) {
        List<Fornecedor> fornecedores = (termo != null && !termo.trim().isEmpty())
                ? fornecedorService.buscarPorNome(termo)
                : fornecedorService.listarTodos();
        model.addAttribute("fornecedores", fornecedores);
        model.addAttribute("totalFornecedores", fornecedores.size());
        return "fornecedores/list";
    }
    
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedores/form";
    }
    
    @PostMapping("/novo")
    public String salvar(@ModelAttribute Fornecedor fornecedor) {
        try {
            fornecedorService.salvar(fornecedor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/fornecedores";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Fornecedor f = fornecedorService.buscarPorId(id).orElse(null);
        if (f == null) { return "redirect:/fornecedores"; }
        model.addAttribute("fornecedor", f);
        return "fornecedores/form";
    }
    
    @PostMapping("/editar")
    public String atualizar(@ModelAttribute Fornecedor fornecedor) {
        try {
            fornecedorService.atualizar(fornecedor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/fornecedores";
    }
    
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        try {
            fornecedorService.excluir(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/fornecedores";
    }
}


