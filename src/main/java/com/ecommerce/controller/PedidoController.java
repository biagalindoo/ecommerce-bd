package com.ecommerce.controller;

import com.ecommerce.entity.Pedido;
import com.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;
    
    @GetMapping
    public String listar(@RequestParam(value = "status", required = false) String status, Model model) {
        List<Pedido> pedidos = (status != null && !status.trim().isEmpty())
                ? pedidoService.buscarPorStatus(status)
                : pedidoService.listarTodos();
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("totalPedidos", pedidos.size());
        return "pedidos/list";
    }
    
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "pedidos/form";
    }
    
    @PostMapping("/novo")
    public String salvar(@ModelAttribute Pedido pedido) {
        try {
            if (pedido.getStatusPedido() == null || pedido.getStatusPedido().isBlank()) {
                pedido.setStatusPedido("aberto");
            }
            pedidoService.salvar(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/pedidos";
    }

    @PostMapping("/status/{id}")
    public String atualizarStatus(@PathVariable Integer id, @RequestParam("status") String novoStatus) {
        try {
            pedidoService.atualizarStatusViaProcedure(id, novoStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/pedidos";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Pedido p = pedidoService.buscarPorId(id).orElse(null);
        if (p == null) { return "redirect:/pedidos"; }
        model.addAttribute("pedido", p);
        return "pedidos/form";
    }
    
    @PostMapping("/editar")
    public String atualizar(@ModelAttribute Pedido pedido) {
        try {
            pedidoService.atualizar(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/pedidos";
    }
    
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        try {
            pedidoService.excluir(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/pedidos";
    }
}


