package controller;

import javax.swing.JPanel;

import view.UsuarioView;
import view.ListaVendasView;
import view.LivrosView;
import view.LoginView;
import view.PaginaInicialView;
import view.PerfilView;
import view.RelatoriosView;
import view.VendasView;

public class PaginaInicialController {
	private PaginaInicialView view;

	public PaginaInicialController(PaginaInicialView view) {
		this.view = view;
	}

	public void tratarCliqueBotaoSidebar(String textoBotao) {
		switch (textoBotao) {
		case "Home":
			view.trocarConteudo(view.criarPaginaInicial());
			break;
		case "Livros":
			view.trocarConteudo(new LivrosView());
			break;
		case "Usuarios":
			view.trocarConteudo(new UsuarioView());
			break;

		case "Vendas":
			view.trocarConteudo(new VendasView(view.getNomeUsuario()));
			break;
		case "Lista Vendas":
			view.trocarConteudo(new ListaVendasView());
			break;
		case "Relatorios":
			view.trocarConteudo(new RelatoriosView());
			break;
		case "Meus Dados":
			view.trocarConteudo(new PerfilView());
			break;
		case "Sair":
			LoginController loginController = new LoginController(new LoginView());
			view.dispose();
			break;
		default:
			view.trocarConteudo(new JPanel());
		}
	}
}