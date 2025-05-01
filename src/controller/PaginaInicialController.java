package controller;

import javax.swing.JPanel;

import view.PaginaInicial;
import view.TelaLivrosView;
import view.TelaVendas;

public class PaginaInicialController {
    private PaginaInicial view;

    public PaginaInicialController(PaginaInicial view) {
        this.view = view;
    }

    public void tratarCliqueBotaoSidebar(String textoBotao) {
        switch (textoBotao) {
            case "Home":
                view.trocarConteudo(view.criarPaginaInicial());
                break;
            case "Livros":
                view.trocarConteudo(new TelaLivrosView());
                break;
            case "Vendas":
            	view.trocarConteudo(new TelaVendas());
                break;
               
            case "Clientes":
                view.trocarConteudo(new JPanel());
                break;
            default:
                view.trocarConteudo(new JPanel());
        }
    }
}