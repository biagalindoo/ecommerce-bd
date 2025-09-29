package com.ecommerce.controller;

import com.ecommerce.entity.Usuario;
import com.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controller para operações de Usuario
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    /**
     * Lista todos os usuários
     */
    @GetMapping
    public String listar(@RequestParam(value = "nome", required = false) String nome,
                        @RequestParam(value = "cidade", required = false) String cidade,
                        @RequestParam(value = "estado", required = false) String estado,
                        Model model) {
        
        List<Usuario> usuarios;
        
        if (nome != null && !nome.trim().isEmpty()) {
            usuarios = usuarioService.buscarPorNome(nome);
        } else if (cidade != null && !cidade.trim().isEmpty()) {
            usuarios = usuarioService.buscarPorCidade(cidade);
        } else if (estado != null && !estado.trim().isEmpty()) {
            usuarios = usuarioService.buscarPorEstado(estado);
        } else {
            usuarios = usuarioService.listarTodos();
        }
        
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalUsuarios", usuarios.size());
        return "usuario/list";
    }
    
    /**
     * Exibe formulário para novo usuário
     */
    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/form";
    }
    
    /**
     * Salva novo usuário
     */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("usuario") Usuario usuario,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "usuario/form";
        }
        
        // Verifica se email já existe
        if (usuarioService.emailExiste(usuario.getEmail())) {
            result.rejectValue("email", "error.usuario", "Email já cadastrado");
            return "usuario/form";
        }
        
        try {
            usuarioService.salvar(usuario);
            redirectAttributes.addFlashAttribute("success", "Usuário salvo com sucesso!");
            return "redirect:/usuario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao salvar usuário: " + e.getMessage());
            return "usuario/form";
        }
    }
    
    /**
     * Exibe formulário para editar usuário
     */
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            return "usuario/edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuário não encontrado");
            return "redirect:/usuario";
        }
    }
    
    /**
     * Atualiza usuário
     */
    @PostMapping("/atualizar")
    public String atualizar(@Valid @ModelAttribute("usuario") Usuario usuario,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "usuario/edit";
        }
        
        try {
            usuarioService.atualizar(usuario);
            redirectAttributes.addFlashAttribute("success", "Usuário atualizado com sucesso!");
            return "redirect:/usuario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar usuário: " + e.getMessage());
            return "usuario/edit";
        }
    }
    
    /**
     * Exclui usuário
     */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.excluir(id);
            redirectAttributes.addFlashAttribute("success", "Usuário excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir usuário: " + e.getMessage());
        }
        return "redirect:/usuario";
    }
    
    /**
     * Exibe detalhes do usuário
     */
    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
        
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            return "usuario/detalhes";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuário não encontrado");
            return "redirect:/usuario";
        }
    }
}
