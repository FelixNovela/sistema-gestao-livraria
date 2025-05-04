package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.ItemVenda;
import model.Livro;
import model.Pagamento;
import service.LivroService;
import service.VendaService;
import view.VendasView;

public class VendasController {
    
    private VendasView view;
    private LivroService livroService;
    private VendaService vendaService;
    private Pagamento pagamento;
    public VendasController(VendasView view) {
        this.view = view;
        this.livroService = new LivroService();
        this.vendaService = new VendaService();
    }
    
    public void carregarDadosIniciais() {
        DefaultTableModel modeloTabelaLivros = view.getModeloTabelaLivros();
        modeloTabelaLivros.setRowCount(0);
        
        List<Livro> livros = livroService.listarTodos();
        for (Livro livro : livros) {
            modeloTabelaLivros.addRow(new Object[]{
                livro.getIsbn(), 
                livro.getTitulo(), 
                livro.getAutor(), 
                livro.getCategoria(), 
                livro.getQuantidadeEmEstoque(),
                String.format("%.2f", livro.getPreco()),
            });
        }
    }
    
    public void adicionarAoCarrinho() {
        int linhaSelecionada = view.getTabelaLivros().getSelectedRow();
        if (linhaSelecionada == -1) {
            view.mostrarMensagem(
                "Selecione um livro para adicionar ao carrinho", 
                "Nenhum livro selecionado", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Verificar estoque
        int estoque = Integer.parseInt(view.getTabelaLivros().getValueAt(linhaSelecionada, 4).toString());
        if (estoque <= 0) {
            view.mostrarMensagem(
                "Este livro nao possui estoque disponivel", 
                "Estoque Insuficiente", 
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        String titulo = view.getTabelaLivros().getValueAt(linhaSelecionada, 1).toString();
        String precoStr = view.getTabelaLivros().getValueAt(linhaSelecionada, 5).toString();
        double preco = Double.parseDouble(precoStr.replace("MT ", ""));
        
        DefaultTableModel modeloTabelaCarrinho = view.getModeloTabelaCarrinho();
        
        // Verificar se o livro já esta no carrinho
        for (int i = 0; i < modeloTabelaCarrinho.getRowCount(); i++) {
            if (modeloTabelaCarrinho.getValueAt(i, 0).equals(titulo)) {
                int qtdAtual = Integer.parseInt(modeloTabelaCarrinho.getValueAt(i, 1).toString());
                if (qtdAtual + 1 > estoque) {
                    view.mostrarMensagem(
                        "Estoque insuficiente para adicionar mais unidades!", 
                        "Estoque Insuficiente", 
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                
                modeloTabelaCarrinho.setValueAt(qtdAtual + 1, i, 1); 
                double subtotal = (qtdAtual + 1) * preco;
                modeloTabelaCarrinho.setValueAt(view.getFormatoMoeda().format(subtotal), i, 3);
                atualizarTotalVenda();
                return;
            }
        }
        
        // Adicionar novo item ao carrinho
        Object[] novoItem = {
            titulo,
            1,
            view.getFormatoMoeda().format(preco),
            view.getFormatoMoeda().format(preco)
        };
        modeloTabelaCarrinho.addRow(novoItem);
        atualizarTotalVenda();
    }
    
    public void atualizarQuantidadeItem(int linha) {
        DefaultTableModel modeloTabelaCarrinho = view.getModeloTabelaCarrinho();
        
        try {
            int quantidade = Integer.parseInt(modeloTabelaCarrinho.getValueAt(linha, 1).toString());
            if (quantidade <= 0) {
                modeloTabelaCarrinho.setValueAt(1, linha, 1);
                quantidade = 1; 
            }
            
            
            String tituloCarrinho = modeloTabelaCarrinho.getValueAt(linha, 0).toString();
            int estoqueDisponivel = 0;
            
            DefaultTableModel modeloTabelaLivros = view.getModeloTabelaLivros();
            for (int i = 0; i < modeloTabelaLivros.getRowCount(); i++) {
                if (modeloTabelaLivros.getValueAt(i, 1).equals(tituloCarrinho)) {
                    estoqueDisponivel = Integer.parseInt(modeloTabelaLivros.getValueAt(i, 4).toString());
                    break;
                }
            }
            
            if (quantidade > estoqueDisponivel) {
                view.mostrarMensagem(
                    "Estoque insuficiente! Disponível: " + estoqueDisponivel, 
                    "Estoque Insuficiente", 
                    JOptionPane.ERROR_MESSAGE
                );
                modeloTabelaCarrinho.setValueAt(estoqueDisponivel, linha, 1);
                quantidade = estoqueDisponivel;
            }
            
            double preco = Double.parseDouble(modeloTabelaCarrinho.getValueAt(linha, 2).toString()
                    .replace("MT ", "").trim());
            double subtotal = quantidade * preco;
            modeloTabelaCarrinho.setValueAt(view.getFormatoMoeda().format(subtotal), linha, 3);
            atualizarTotalVenda();
        } catch (NumberFormatException e) {
            modeloTabelaCarrinho.setValueAt(1, linha, 1);
            atualizarQuantidadeItem(linha);
        }
    }
    
    public void removerDoCarrinho() {
        int linhaSelecionada = view.getTabelaCarrinho().getSelectedRow();
        if (linhaSelecionada == -1) {
            view.mostrarMensagem(
                "Selecione um livro para remover do carrinho", 
                "Nenhum item selecionado", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        view.getModeloTabelaCarrinho().removeRow(linhaSelecionada);
        atualizarTotalVenda();
    }
    
    public void limparCarrinho() {
        DefaultTableModel modeloTabelaCarrinho = view.getModeloTabelaCarrinho();
        while (modeloTabelaCarrinho.getRowCount() > 0) {
            modeloTabelaCarrinho.removeRow(0);
        }
        atualizarTotalVenda();
    }
    
    public void atualizarTotalVenda() {
        double total = 0;
        DefaultTableModel modeloTabelaCarrinho = view.getModeloTabelaCarrinho();
        
        for (int i = 0; i < modeloTabelaCarrinho.getRowCount(); i++) {
            String subtotalStr = modeloTabelaCarrinho.getValueAt(i, 3).toString();
            double subtotal = Double.parseDouble(subtotalStr.replace("MT ", ""));
            total += subtotal;
        }
        
        view.getLblTotalVenda().setText(view.getFormatoMoeda().format(total));
        
    }
    
    public void finalizarVenda() {
        DefaultTableModel modeloTabelaCarrinho = view.getModeloTabelaCarrinho();
        
        String totalVendaString = view.getLblTotalVenda().getText().toString();
        totalVendaString = totalVendaString.replace("MT", "").trim();
        double totalVenda = Double.parseDouble(totalVendaString);
        
        double valorPago = Double.parseDouble(view.getValorPago().getText().toString());
        String formaPagamento = view.getCmbFormaPagamento().getSelectedItem().toString();
        
        if (modeloTabelaCarrinho.getRowCount() == 0) {
            view.mostrarMensagem(
                "O carrinho esta vazio. Adicione livros antes de finalizar a venda.", 
                "Carrinho Vazio", 
                JOptionPane.WARNING_MESSAGE 
            );
            return;
        }
        
        if (view.getCampoCliente().getText().isEmpty()) {
            view.mostrarMensagem(
                "Insira o nome do cliente para finalizar a venda.", 
                "Campo Funcionario null", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        pagamento = vendaService.verificarPagamento(formaPagamento, totalVenda, valorPago);
        if(pagamento == null) {
        	view.mostrarMensagem(
                    "Fallha ao efetuar Pagamento, valor pago menor que valor total da venda", 
                    "Valor pago menor que valor total da venda", 
                    JOptionPane.WARNING_MESSAGE
                );
        	return; 
        }
        
        
        
       if(pagamento != null) {
    	   
           
           
           DefaultTableModel modeloTabelaLivros = view.getModeloTabelaLivros();
           for (int i = 0; i < modeloTabelaCarrinho.getRowCount(); i++) {
               String tituloCarrinho = modeloTabelaCarrinho.getValueAt(i, 0).toString();
              
               int quantidadeVendida = Integer.parseInt(modeloTabelaCarrinho.getValueAt(i, 1).toString());
               
               for (int j = 0; j < modeloTabelaLivros.getRowCount(); j++) {
                   if (modeloTabelaLivros.getValueAt(j, 1).equals(tituloCarrinho)) {
                   	
                   	String isbn = modeloTabelaLivros.getValueAt(j, 0).toString();
                   	
                   	vendaService.adicionarLivroNaListaItemVenda(isbn, quantidadeVendida);
                   	vendaService.pegarNomeDoFuncionario(view.getNomeUsuario());
                       int estoqueAtual = Integer.parseInt(modeloTabelaLivros.getValueAt(j, 4).toString());
                       modeloTabelaLivros.setValueAt(estoqueAtual - quantidadeVendida, j, 4);
                       livroService.atualizarEstoque(isbn, estoqueAtual - quantidadeVendida);
                       break;
                   }
               }
           }
           
           String cliente = view.getCampoCliente().getText();
           String total = view.getLblTotalVenda().getText();
           
           
           vendaService.efetuarPagamento(pagamento, cliente);
       }
        
      
        
       
      
        
        
        view.mostrarMensagem(
            "Venda realizada com sucesso\n\n" +
            //"Funcionario: " + cliente + "\n" +
            "Forma de Pagamento: " + formaPagamento + "\n" +
            //"Total Da venda: " + total+ "\n"+
            "Valor Pago: " + valorPago+"\n"+
            "Trocos : " + (valorPago - totalVenda),
            "Venda Finalizada", 
            JOptionPane.INFORMATION_MESSAGE
        );
        
       
        limparTela();
    }
    
    public void limparTela() {
        limparCarrinho();
        view.getCampoCliente().setText("");
        view.getCmbFormaPagamento().setSelectedIndex(0);
        view.getValorPago().setText("0");
        atualizarTotalVenda();
    }
}