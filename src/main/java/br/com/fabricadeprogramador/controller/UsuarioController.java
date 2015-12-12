package br.com.fabricadeprogramador.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fabricadeprogramador.entidades.Usuario;
import br.com.fabricadeprogramador.persistencia.dao.UsuarioDAO;

@WebServlet(urlPatterns={"/usucontroller"})
public class UsuarioController extends HttpServlet{

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//1) Pegando da Tela
		
		String id = req.getParameter("id");
		String nome = req.getParameter("nome");
		String login = req.getParameter("login");
		String senha = req.getParameter("senha");
		
		//2) Criando Objeto Usuario e populando com os dados da tela
		Usuario usuario = new Usuario();
		if(!id.isEmpty()){
			usuario.setId(Integer.parseInt(id));
		}
		usuario.setNome(nome);
		usuario.setLogin(login);
		usuario.setSenha(senha);
		
		//3) Criando um UsuarioDAO para salvar o usuario
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		//4) Invocar o Método salvar
		usuarioDAO.salvar(usuario);
		
		//5) Respondendo mensagem na tela
		//resp.getWriter().print("Salvo com Sucesso!");
		
		
		
		String sucesso = "<script>"; 
		sucesso=sucesso+ "	window.alert('Salvo com Sucesso');";
		sucesso=sucesso+"	location.href='usucontroller' ;";
		sucesso=sucesso+"</script>";
		
		resp.getWriter().print(sucesso);
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String acao =  req.getParameter("acao");
		if (acao!=null && acao.equals("exc")){
			String id = req.getParameter("id");
			if (!id.isEmpty()){
				
				Usuario usuarioAExcluir = new Usuario();
				usuarioAExcluir.setId(Integer.parseInt(id));
				
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				usuarioDAO.excluir(usuarioAExcluir);
			
				
				String sucesso = "<script>"; 
				sucesso=sucesso+ "	window.alert('Excluido com Sucesso');";
				sucesso=sucesso+"	location.href='usucontroller' ;";
				sucesso=sucesso+"</script>";
				
				resp.getWriter().print(sucesso);
			}
			
		}else if (acao!=null && acao.equals("edit")){	
			//1) Pega o id da tela
			String id =  req.getParameter("id");
			//2) Passar pro DAO carregar o usuario pelo id
			
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario usuario = usuarioDAO.buscarPorId(Integer.parseInt(id));
			if(usuario!=null){
			//3) Levar o objeto usuario para o form.jsp
				//3.1) Armazenar o usuario no request
					req.setAttribute("usuario",usuario);
			
				//3.2) Encaminhar para JSP por meio o forward
					
					RequestDispatcher dispatcher = req.getRequestDispatcher("formusuario.jsp");
					dispatcher.forward(req, resp);
			}else{
				resp.getWriter().print("Usuário não econtrado!");
			}
			
		}else if (acao!=null && acao.equals("novo")){		
			
			Usuario usuario = new Usuario();
			usuario.setId(0);
			usuario.setNome("");
			usuario.setLogin("");
			usuario.setSenha("");
			
			req.setAttribute("usuario",usuario);
	
			RequestDispatcher dispatcher = req.getRequestDispatcher("formusuario.jsp");
			dispatcher.forward(req, resp);
			
		}else  {
			//1) Consulta no banco
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			List<Usuario > lista = usuarioDAO.buscarTodos();
		
			//2) Colocar no Request HTTP a lista
			req.setAttribute("lista", lista);
			
			//3 Encaminhar o Request e Response para o Tela
			RequestDispatcher dispatcher = req.getRequestDispatcher("lista.jsp");
			dispatcher.forward(req, resp);
			//ou
			//req.getRequestDispatcher("lista.jsp").forward(req, resp);
			
		}
		
	
	}
}
