package controller.vendedor;

import javax.swing.JPanel;

import controller.LoginController;
import view.ListaVendasView;
import view.LivrosView;
import view.LoginView;
import view.PaginaInicialView;
import view.PerfilView;
import view.VendasView;
import view.vendedor.ListaDeVendasVendedorView;
import view.vendedor.LivrosVendedorView;
import view.vendedor.PaginaInicialVendedorView;

public class PaginaInicialVendedorController {
	private PaginaInicialVendedorView view;

	public PaginaInicialVendedorController(PaginaInicialVendedorView view) {
		this.view = view;
	}

	public void tratarCliqueBotaoSidebar(String textoBotao) {
		switch (textoBotao) {
		case "Home":
			view.trocarConteudo(view.criarPaginaInicial());
			break;
		case "Livros":
			view.trocarConteudo(new LivrosVendedorView());
			break;
		case "Vendas":
			view.trocarConteudo(new VendasView(view.getNomeUsuario()));
			break;
		case "Lista Vendas":
			view.trocarConteudo(new ListaDeVendasVendedorView(view.getNomeUsuario()));
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