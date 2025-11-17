package com.ecommerce.controller;

import com.ecommerce.entity.Usuario;
import com.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public String listar(@RequestParam(value = "nome", required = false) String nome,
                        Model model) {
        
        List<Usuario> usuarios;
        
        if (nome != null && !nome.trim().isEmpty()) {
            usuarios = usuarioService.buscarPorNome(nome);
        } else {
            usuarios = usuarioService.listarTodos();
        }
        
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalUsuarios", usuarios.size());
        return "usuarios/list";
    }
    
    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }
    
    /**
     * Salvar novo usuário
     */
    @PostMapping("/novo")
    public String salvarUsuario(@RequestParam String primeiroNome,
                                @RequestParam String sobrenome,
                                @RequestParam String email,
                                @RequestParam String cpf,
                                @RequestParam String dataNascimento,
                                @RequestParam String senhaHash) {
        try {
            Usuario usuario = new Usuario();
            usuario.setPrimeiroNome(primeiroNome);
            usuario.setSobrenome(sobrenome);
            usuario.setEmail(email);
            usuario.setCpf(cpf);
            usuario.setDataNascimento(java.time.LocalDate.parse(dataNascimento));
            usuario.setSenhaHash(senhaHash);
            
            usuarioService.salvar(usuario);
            return "redirect:/usuarios";
        } catch (Exception e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/usuarios";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id).orElse(null);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/form";
    }
    
    /**
     * Atualizar usuário
     */
    @PostMapping("/editar")
    public String atualizarUsuario(@RequestParam Integer id,
                                   @RequestParam String primeiroNome,
                                   @RequestParam String sobrenome,
                                   @RequestParam String email,
                                   @RequestParam String cpf,
                                   @RequestParam String dataNascimento,
                                   @RequestParam String senhaHash) {
        try {
            Usuario usuario = new Usuario();
            usuario.setId(id);
            usuario.setPrimeiroNome(primeiroNome);
            usuario.setSobrenome(sobrenome);
            usuario.setEmail(email);
            usuario.setCpf(cpf);
            usuario.setDataNascimento(java.time.LocalDate.parse(dataNascimento));
            usuario.setSenhaHash(senhaHash);
            
            usuarioService.atualizar(usuario);
            return "redirect:/usuarios";
        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/usuarios";
        }
    }
    
    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Integer id) {
        try {
            usuarioService.excluir(id);
        } catch (Exception e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/usuarios";
    }
}