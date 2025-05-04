package controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Livro;
import model.Venda;
import service.LivroService;
import service.VendaService;
import view.BotaoVerListaDeLivrosVendidos;
import view.ListaVendasView;

public class ListaVendasController {
    
    private ListaVendasView view;
    private LivroService livroService;
    private VendaService vendaService;
    
    public ListaVendasController(ListaVendasView view, VendaService vendaService) {
        this.view = view;
        this.vendaService = vendaService;
        
       
        view.setController(this);
        
       
        configurarTabelaLivros();
        
       
        atualizarDadosTabela();
    }
    
    private void configurarTabelaLivros() {
        
        DefaultTableModel modeloTabela = view.getModeloTabela();
        JTable tabela = view.getTabela();
        
        tabela.getColumnModel().getColumn(8).setCellRenderer(new BotaoVerListaDeLivrosVendidos(tabela, vendaService, modeloTabela));
        tabela.getColumnModel().getColumn(8).setCellEditor(new BotaoVerListaDeLivrosVendidos(tabela, vendaService, modeloTabela));
    }
    
    public void atualizarDadosTabela() {
        List<Venda> vendas = vendaService.listarVendas();
        
        DefaultTableModel modeloTabela = view.getModeloTabela();
        modeloTabela.setRowCount(0);
        
        for (Venda venda : vendas) {
            
                Object[] dados = new Object[]{
                	String.valueOf(venda.getId()),
                	venda.getCliente(),
                	venda.getFuncionario(),
                	String.format("%.2f",venda.getPagamento().getValorTotalDaVenda()), 
                	String.format("%.2f",venda.getPagamento().getValorPago()), 
                	String.format("%.2f",venda.getPagamento().getTroco()), 
                	venda.getPagamento().getTipoPagamento(),
                	venda.getData().toString(),
                    
                   
                    //livro.getQuantidadeEmEstoque(), 
                    "Acoes"
                };
                modeloTabela.addRow(dados);
            
        }
    }

    
   
    public void filtrarLivros(String textoPesquisa) {
        List<Venda> vendas = vendaService.listarVendas();
        Object[][] dadosFiltrados = vendas.stream()
            .filter(venda -> 
                venda.getCliente().toLowerCase().contains(textoPesquisa) ||
                venda.getFuncionario().toLowerCase().contains(textoPesquisa) ||
                venda.getData().toString().contains(textoPesquisa)
            )
            .map(venda -> new Object[]{
            		String.valueOf(venda.getId()),
                	venda.getCliente(),
                	venda.getFuncionario(),
                	String.format("%.2f",venda.getPagamento().getValorTotalDaVenda()), 
                	String.format("%.2f",venda.getPagamento().getValorPago()), 
                	String.format("%.2f",venda.getPagamento().getTroco()), 
                	venda.getPagamento().getTipoPagamento(),
                	venda.getData().toString(), 
                "Acoes"
            })
            .toArray(Object[][]::new);
            
        view.atualizarTabela(dadosFiltrados);
    }
    
   
    
   
}