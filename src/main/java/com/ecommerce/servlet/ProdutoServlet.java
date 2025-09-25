package com.ecommerce.servlet;

import com.ecommerce.dao.ProdutoDAO;
import com.ecommerce.model.Produto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet para gerenciar operações CRUD de produtos
 */
@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet {
    
    private ProdutoDAO produtoDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        produtoDAO = new ProdutoDAO();
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
                listarProdutos(request, response);
                break;
            case "form":
                mostrarFormulario(request, response);
                break;
            case "edit":
                mostrarFormularioEdicao(request, response);
                break;
            case "delete":
                deletarProduto(request, response);
                break;
            case "search":
                buscarProdutos(request, response);
                break;
            case "estoque-baixo":
                listarEstoqueBaixo(request, response);
                break;
            default:
                listarProdutos(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            criarProduto(request, response);
        } else if ("update".equals(action)) {
            atualizarProduto(request, response);
        }
    }
    
    private void listarProdutos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Produto> produtos = produtoDAO.listarTodos();
        request.setAttribute("produtos", produtos);
        request.getRequestDispatcher("/WEB-INF/views/produto/list.jsp").forward(request, response);
    }
    
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<String> armazens = produtoDAO.listarArmazens();
        request.setAttribute("armazens", armazens);
        request.getRequestDispatcher("/WEB-INF/views/produto/form.jsp").forward(request, response);
    }
    
    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Produto produto = produtoDAO.buscarPorId(id);
        
        if (produto != null) {
            List<String> armazens = produtoDAO.listarArmazens();
            request.setAttribute("produto", produto);
            request.setAttribute("armazens", armazens);
            request.getRequestDispatcher("/WEB-INF/views/produto/edit.jsp").forward(request, response);
        } else {
            response.sendRedirect("produto?action=list&error=produto_nao_encontrado");
        }
    }
    
    private void criarProduto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Produto produto = new Produto();
            produto.setNome(request.getParameter("nome"));
            produto.setDescricao(request.getParameter("descricao"));
            produto.setPreco(Double.parseDouble(request.getParameter("preco")));
            produto.setQuantidadeEstoque(Integer.parseInt(request.getParameter("quantidadeEstoque")));
            produto.setArmazemId(Integer.parseInt(request.getParameter("armazemId")));
            
            boolean sucesso = produtoDAO.inserir(produto);
            
            if (sucesso) {
                response.sendRedirect("produto?action=list&success=produto_criado");
            } else {
                response.sendRedirect("produto?action=form&error=erro_criar_produto");
            }
            
        } catch (Exception e) {
            response.sendRedirect("produto?action=form&error=erro_criar_produto");
        }
    }
    
    private void atualizarProduto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Produto produto = new Produto();
            produto.setId(Integer.parseInt(request.getParameter("id")));
            produto.setNome(request.getParameter("nome"));
            produto.setDescricao(request.getParameter("descricao"));
            produto.setPreco(Double.parseDouble(request.getParameter("preco")));
            produto.setQuantidadeEstoque(Integer.parseInt(request.getParameter("quantidadeEstoque")));
            produto.setArmazemId(Integer.parseInt(request.getParameter("armazemId")));
            
            boolean sucesso = produtoDAO.atualizar(produto);
            
            if (sucesso) {
                response.sendRedirect("produto?action=list&success=produto_atualizado");
            } else {
                response.sendRedirect("produto?action=edit&id=" + produto.getId() + "&error=erro_atualizar_produto");
            }
            
        } catch (Exception e) {
            response.sendRedirect("produto?action=list&error=erro_atualizar_produto");
        }
    }
    
    private void deletarProduto(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean sucesso = produtoDAO.deletar(id);
            
            if (sucesso) {
                response.sendRedirect("produto?action=list&success=produto_deletado");
            } else {
                response.sendRedirect("produto?action=list&error=erro_deletar_produto");
            }
            
        } catch (Exception e) {
            response.sendRedirect("produto?action=list&error=erro_deletar_produto");
        }
    }
    
    private void buscarProdutos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nome = request.getParameter("nome");
        List<Produto> produtos = produtoDAO.buscarPorNome(nome);
        
        request.setAttribute("produtos", produtos);
        request.setAttribute("searchTerm", nome);
        request.getRequestDispatcher("/WEB-INF/views/produto/list.jsp").forward(request, response);
    }
    
    private void listarEstoqueBaixo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Produto> produtos = produtoDAO.buscarEstoqueBaixo();
        request.setAttribute("produtos", produtos);
        request.setAttribute("estoqueBaixo", true);
        request.getRequestDispatcher("/WEB-INF/views/produto/list.jsp").forward(request, response);
    }
}
