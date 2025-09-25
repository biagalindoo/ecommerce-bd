package com.ecommerce.servlet;

import com.ecommerce.dao.UsuarioDAO;
import com.ecommerce.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet para gerenciar operações CRUD de usuários
 */
@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        usuarioDAO = new UsuarioDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listarUsuarios(request, response);
                break;
            case "form":
                mostrarFormulario(request, response);
                break;
            case "edit":
                mostrarFormularioEdicao(request, response);
                break;
            case "delete":
                deletarUsuario(request, response);
                break;
            case "search":
                buscarUsuarios(request, response);
                break;
            default:
                listarUsuarios(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            criarUsuario(request, response);
        } else if ("update".equals(action)) {
            atualizarUsuario(request, response);
        }
    }
    
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/views/usuario/list.jsp").forward(request, response);
    }
    
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/views/usuario/form.jsp").forward(request, response);
    }
    
    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = usuarioDAO.buscarPorId(id);
        
        if (usuario != null) {
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("/WEB-INF/views/usuario/edit.jsp").forward(request, response);
        } else {
            response.sendRedirect("usuario?action=list&error=usuario_nao_encontrado");
        }
    }
    
    private void criarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Usuario usuario = new Usuario();
            usuario.setEmail(request.getParameter("email"));
            usuario.setSenhaHash(request.getParameter("senhaHash"));
            usuario.setCpf(request.getParameter("cpf"));
            usuario.setPrimeiroNome(request.getParameter("primeiroNome"));
            usuario.setSobrenome(request.getParameter("sobrenome"));
            usuario.setDataNascimento(request.getParameter("dataNascimento"));
            
            boolean sucesso = usuarioDAO.inserir(usuario);
            
            if (sucesso) {
                response.sendRedirect("usuario?action=list&success=usuario_criado");
            } else {
                response.sendRedirect("usuario?action=form&error=erro_criar_usuario");
            }
            
        } catch (Exception e) {
            response.sendRedirect("usuario?action=form&error=erro_criar_usuario");
        }
    }
    
    private void atualizarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(request.getParameter("id")));
            usuario.setEmail(request.getParameter("email"));
            usuario.setSenhaHash(request.getParameter("senhaHash"));
            usuario.setCpf(request.getParameter("cpf"));
            usuario.setPrimeiroNome(request.getParameter("primeiroNome"));
            usuario.setSobrenome(request.getParameter("sobrenome"));
            usuario.setDataNascimento(request.getParameter("dataNascimento"));
            
            boolean sucesso = usuarioDAO.atualizar(usuario);
            
            if (sucesso) {
                response.sendRedirect("usuario?action=list&success=usuario_atualizado");
            } else {
                response.sendRedirect("usuario?action=edit&id=" + usuario.getId() + "&error=erro_atualizar_usuario");
            }
            
        } catch (Exception e) {
            response.sendRedirect("usuario?action=list&error=erro_atualizar_usuario");
        }
    }
    
    private void deletarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean sucesso = usuarioDAO.deletar(id);
            
            if (sucesso) {
                response.sendRedirect("usuario?action=list&success=usuario_deletado");
            } else {
                response.sendRedirect("usuario?action=list&error=erro_deletar_usuario");
            }
            
        } catch (Exception e) {
            response.sendRedirect("usuario?action=list&error=erro_deletar_usuario");
        }
    }
    
    private void buscarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nome = request.getParameter("nome");
        List<Usuario> usuarios = usuarioDAO.buscarPorNome(nome);
        
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("searchTerm", nome);
        request.getRequestDispatcher("/WEB-INF/views/usuario/list.jsp").forward(request, response);
    }
}
