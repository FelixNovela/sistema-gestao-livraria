package controller;

import java.awt.Frame;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.Livro;
import service.LivroService;
import view.AdicionarLivroView;
import view.BotoesEditarLivro;
import view.LivrosView;

public class LivrosController {
    
    private LivrosView view;
    private LivroService livroService;
    
    public LivrosController(LivrosView view, LivroService livroService) {
        this.view = view;
        this.livroService = livroService;
        
       
        view.setController(this);
        
       
        configurarTabelaLivros();
        
       
        atualizarDadosTabela();
    }
    
    private void configurarTabelaLivros() {
        
        DefaultTableModel modeloTabela = view.getModeloTabela();
        JTable tabela = view.getTabela();
        
        tabela.getColumnModel().getColumn(6).setCellRenderer(new BotoesEditarLivro(tabela, livroService, modeloTabela));
        tabela.getColumnModel().getColumn(6).setCellEditor(new BotoesEditarLivro(tabela, livroService, modeloTabela));
    }
    
    public void atualizarDadosTabela() {
        List<Livro> livros = livroService.listarTodos();
        
        DefaultTableModel modeloTabela = view.getModeloTabela();
        modeloTabela.setRowCount(0);
        
        for (Livro livro : livros) {
            if (livro.isStatus()) {
                Object[] dados = new Object[]{
                    livro.getIsbn(), 
                    livro.getTitulo(), 
                    livro.getAutor(), 
                    livro.getCategoria(),
                    String.format("%.2f", livro.getPreco()), 
                    livro.getQuantidadeEmEstoque(), 
                    "Acoes"
                };
                modeloTabela.addRow(dados);
            }
        }
    }

    
   
    public void filtrarLivros(String textoPesquisa) {
        List<Livro> livros = livroService.listarTodos();
        Object[][] dadosFiltrados = livros.stream()
            .filter(livro -> (
                livro.getTitulo().toLowerCase().contains(textoPesquisa) ||
                livro.getAutor().toLowerCase().contains(textoPesquisa) ||
                livro.getIsbn().toLowerCase().contains(textoPesquisa) ||
                livro.getCategoria().toLowerCase().contains(textoPesquisa)) && (livro.isStatus())
            )
            .map(livro -> new Object[]{
                livro.getIsbn(), 
                livro.getTitulo(), 
                livro.getAutor(), 
                livro.getCategoria(),
                String.format("%.2f", livro.getPreco()), 
                livro.getQuantidadeEmEstoque(), 
                "Acoes"
            })
            .toArray(Object[][]::new);
            
        view.atualizarTabela(dadosFiltrados);
    }
    
   
    
    public void abrirDialogAdicionarLivro() {
        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(view);
        AdicionarLivroView dialog = new AdicionarLivroView(framePai); 
        AdicionarLivroController adicionarLivroController = new AdicionarLivroController(dialog, livroService, view.getModeloTabela());
        dialog.setController(adicionarLivroController);
        dialog.setVisible(true);
        
      
        atualizarDadosTabela();
    }
}