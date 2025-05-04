package controller;

import javax.swing.JPanel;

import view.PaginaInicialView;
import view.FuncionarioView;
import view.ListaVendasView;
import view.LivrosView;
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
            case "Vendas":
            	view.trocarConteudo(new VendasView());
                break;
               
            case "Lista Vendas":
                view.trocarConteudo(new ListaVendasView());
                break;
            case "Funcionarios":
                view.trocarConteudo(new FuncionarioView());
                break;
            default:
                view.trocarConteudo(new JPanel());
        }
    }
}