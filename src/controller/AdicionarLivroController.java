package controller;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Livro;
import service.LivroService;
import view.AdicionarLivroView;

public class AdicionarLivroController {
    private AdicionarLivroView view;
    private LivroService livroService;
    private DefaultTableModel tabelaLivros;
    
    public AdicionarLivroController(AdicionarLivroView view, LivroService livroService, DefaultTableModel tabelaLivros) {
        this.view = view;
        this.livroService = livroService;
        this.tabelaLivros = tabelaLivros;
        
        this.view.setController(this);
    }
    
    public void salvarLivro() {
        try {
            String isbn = view.getIsbn();
            String titulo = view.getTitulo();
            String autor = view.getAutor();
            String editora = view.getEditora();
            String precoTexto = view.getPreco();
            String quantidadeTexto = view.getQuantidade();
            
           
            if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty() || editora.isEmpty() || 
                precoTexto.isEmpty() || quantidadeTexto.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Todos os campos sao obrigatorios.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!livroService.validarIsbn(isbn)) {
                JOptionPane.showMessageDialog(view, 
                    "ISBN invalido",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (livroService.buscarPorIsbn(isbn) != null) {
                JOptionPane.showMessageDialog(view, 
                    "Já existe um livro cadastrado com este ISBN.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double preco;
            int quantidade;
            
            try {
                preco = Double.parseDouble(precoTexto);
                if (preco < 0) {
                    JOptionPane.showMessageDialog(view, 
                        "O preco nao pode ser negativo.",
                        "Erro de Validacao", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, 
                    "O preco deve ser um número valido.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                quantidade = Integer.parseInt(quantidadeTexto);
                if (quantidade <= 0) {
                    JOptionPane.showMessageDialog(view, 
                        "A quantidade nao pode ser negativa.",
                        "Erro de Validacao", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, 
                    "A quantidade deve ser um numero inteiro valido.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            Livro novoLivro = new Livro(isbn, titulo, autor, editora, preco, quantidade);
            livroService.cadastrarLivro(novoLivro);

           
            tabelaLivros.addRow(new Object[]{
                isbn, titulo, autor, editora, String.format("%.2f", preco), quantidade
            });

            JOptionPane.showMessageDialog(view, 
                "Livro adicionado com sucesso",
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
                
            view.fechar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                "Erro ao salvar: " + ex.getMessage(),
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}