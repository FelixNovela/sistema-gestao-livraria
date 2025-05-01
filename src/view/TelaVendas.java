package view;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import model.Livro;
import service.LivroService;

public class TelaVendas extends JPanel {

    
    
    private static final Color COR_PRIMARIA = new Color(0x5D4037);
    private static final Color COR_SECUNDARIA = new Color(0x8D6E63);
    private static final Color COR_FUNDO = new Color(0xEFEBE9);
    private static final Color COR_PAINEL = new Color(0xD7CCC8);
    private static final Color COR_DESTAQUE = new Color(0xBCAAA4);
    private static final Color COR_TEXTO = new Color(0x3E2723);
    private static final Color COR_TEXTO_CLARO = new Color(0xFAFAFA);
    private static final Color COR_BORDA = new Color(0xBCAAA4);
    private static final Color COR_BOTAO = new Color(0x8D6E63);
    private static final Color COR_BOTAO_HOVER = new Color(0x6D4C41);
    private static final Color COR_ALERTA = new Color(0xEF5350); 
    private static final Color COR_SUCESSO = new Color(0x66BB6A);

    private final Font FONTE_PADRAO = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 26);
    private final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 13);

    
    private JTextField campoPesquisa;
   
    private JTable tabelaLivros;
    private DefaultTableModel modeloTabelaLivros;
    private JTable tabelaCarrinho;
    private DefaultTableModel modeloTabelaCarrinho;
    private JLabel lblTotalVenda;
    private JTextField cmbCliente;
    private JComboBox<String> cmbFormaPagamento;
    private JTextField valorPago;
    private JButton btnFinalizarVenda;
    private DecimalFormat formatoMoeda = new DecimalFormat("MT #,##0.00");

    public TelaVendas() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(COR_FUNDO);

        
        add(criarPainelSuperior(), BorderLayout.NORTH);
        add(criarPainelLivros(), BorderLayout.CENTER);
        add(criarPainelCarrinho(), BorderLayout.EAST);
        add(criarPainelInferior(), BorderLayout.SOUTH);
        
      
        carregarDadosIniciais();
    }

    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout(10, 0));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel titulo = new JLabel("Realizar Venda");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(COR_PRIMARIA);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        JLabel data = new JLabel("Data: " + sdf.format(new Date()));
        data.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        data.setForeground(COR_TEXTO);

        painel.add(titulo, BorderLayout.WEST);
        painel.add(data, BorderLayout.EAST);

        return painel;
    }

    private JPanel criarPainelLivros() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(COR_FUNDO);
        
     
        
        
        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelPesquisa.setBackground(COR_FUNDO);
        
        campoPesquisa = new JTextField("Pesquisar...");
        campoPesquisa.setPreferredSize(new Dimension(530, 30));
        campoPesquisa.setMaximumSize(new Dimension(180, 30));
        campoPesquisa.setFont(FONTE_PADRAO);
        campoPesquisa.setBackground(COR_PAINEL);
        campoPesquisa.setForeground(Color.GRAY);
        campoPesquisa.setCaretColor(COR_TEXTO);
        campoPesquisa.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_BORDA, 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        campoPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (campoPesquisa.getText().equals("Pesquisar...")) {
                    campoPesquisa.setText("");
                    campoPesquisa.setForeground(COR_TEXTO);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (campoPesquisa.getText().isEmpty()) {
                    campoPesquisa.setText("Pesquisar...");
                    campoPesquisa.setForeground(Color.GRAY);
                }
            }
        });
        
        
 
        painelPesquisa.add(campoPesquisa);
        
       
        String[] colunas = {"ISBN", "Titulo", "Autor", "Categoria", "Estoque", "Preco"};
        modeloTabelaLivros = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaLivros = new JTable(modeloTabelaLivros) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground((row % 2 == 0) ? COR_FUNDO : COR_DESTAQUE);
                    
                    if (col == 4) {
                        try {
                            int estoque = Integer.parseInt(getValueAt(row, col).toString());
                            if (estoque <= 3) c.setBackground(COR_ALERTA);
                        } catch (NumberFormatException ignored) {}
                    }
                } else {
                    c.setBackground(COR_DESTAQUE);
                }
                c.setForeground(COR_TEXTO);
                return c;
            }
        };
        
        tabelaLivros.setRowHeight(30);
        tabelaLivros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaLivros.setGridColor(COR_DESTAQUE);
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

       
        tabelaLivros.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabelaLivros.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaLivros.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabelaLivros.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelaLivros.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabelaLivros.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        JScrollPane scrollLivros = new JScrollPane(tabelaLivros);
        scrollLivros.getViewport().setBackground(COR_PAINEL);
        scrollLivros.setBorder(BorderFactory.createEmptyBorder());
        
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.setBackground(COR_FUNDO);
        
        JButton btnAdicionar = criarBotao("Adicionar ao Carrinho", COR_SUCESSO);
        btnAdicionar.addActionListener(e -> adicionarAoCarrinho());
        
        painelBotoes.add(btnAdicionar);
        
       
        painel.add(painelPesquisa, BorderLayout.NORTH);
        
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBackground(COR_FUNDO);
        painelCentral.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COR_DESTAQUE),
            "Livros Disponíveis", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16), COR_PRIMARIA
        ));
        painelCentral.add(scrollLivros, BorderLayout.CENTER);
        
        painel.add(painelCentral, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.SOUTH);
        
        return painel;
    }

    private JPanel criarPainelCarrinho() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(COR_FUNDO);
        painel.setPreferredSize(new Dimension(400, 0));
        
      
        String[] colunas = {"Título", "Quantidade", "Preco", "Subtotal"};
        modeloTabelaCarrinho = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        
        tabelaCarrinho = new JTable(modeloTabelaCarrinho);
        tabelaCarrinho.setRowHeight(30);
        tabelaCarrinho.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaCarrinho.setGridColor(COR_DESTAQUE);
        
        
        JTextField editorQtd = new JTextField();
        editorQtd.setHorizontalAlignment(JTextField.CENTER);
        tabelaCarrinho.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(editorQtd));
        
        
        modeloTabelaCarrinho.addTableModelListener(e -> {
            if (e.getColumn() == 1) { 
                int row = e.getFirstRow();
                try {
                    int quantidade = Integer.parseInt(modeloTabelaCarrinho.getValueAt(row, 1).toString());
                    double preco = Double.parseDouble(modeloTabelaCarrinho.getValueAt(row, 2).toString()
                            .replace("MT ", "").replace(",", ""));
                    double subtotal = quantidade * preco;
                    modeloTabelaCarrinho.setValueAt(formatoMoeda.format(subtotal), row, 3);
                    atualizarTotalVenda();
                } catch (NumberFormatException ignored) {
                    
                    modeloTabelaCarrinho.setValueAt(1, row, 1);
                }
            }
        });
        
        JScrollPane scrollCarrinho = new JScrollPane(tabelaCarrinho);
        scrollCarrinho.getViewport().setBackground(COR_PAINEL);
        scrollCarrinho.setBorder(BorderFactory.createEmptyBorder());
        
      
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBackground(COR_FUNDO);
        
        JButton btnRemover = criarBotao("❌ Remover Item", COR_ALERTA);
        btnRemover.addActionListener(e -> removerDoCarrinho());
        
        JButton btnLimpar = criarBotao("🗑️ Limpar Carrinho", COR_SECUNDARIA);
        btnLimpar.addActionListener(e -> limparCarrinho());
        
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnLimpar);
        
       
        JPanel painelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTotal.setBackground(COR_FUNDO);
        
        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(COR_TEXTO);
        
        lblTotalVenda = new JLabel(formatoMoeda.format(0));
        lblTotalVenda.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalVenda.setForeground(COR_PRIMARIA);
        
        painelTotal.add(lblTotal);
        painelTotal.add(lblTotalVenda);
        
       
        JPanel painelCarrinhoTitulo = new JPanel(new BorderLayout());
        painelCarrinhoTitulo.setBackground(COR_FUNDO);
        painelCarrinhoTitulo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COR_DESTAQUE),
            "Carrinho de Compras", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16), COR_PRIMARIA
        ));
        painelCarrinhoTitulo.add(scrollCarrinho, BorderLayout.CENTER);
        
        painel.add(painelCarrinhoTitulo, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.NORTH);
        painel.add(painelTotal, BorderLayout.SOUTH);
        
        return painel;
    }

    private JPanel criarPainelInferior() {
        JPanel painel = new JPanel(new GridLayout(2, 1, 20, 0));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
       
        JPanel painelDados = new JPanel(new GridLayout(1, 6, 10, 10));
        painelDados.setBackground(COR_PAINEL);
        painelDados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbCliente =  new JTextField("");
        
        JLabel lblPagamento = new JLabel("Forma de Pagamento:");
        lblPagamento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        String[] formasPagamento = {"Dinheiro", "Transferencia"};
        cmbFormaPagamento = new JComboBox<>(formasPagamento);
        cmbFormaPagamento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel lblValor = new JLabel("valor:");
        lblValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        valorPago = new JTextField("0");
        valorPago.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valorPago.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                atualizarTotalVenda();
            }
        });
        
        painelDados.add(lblCliente);
        painelDados.add(cmbCliente);
        painelDados.add(lblPagamento);
        painelDados.add(cmbFormaPagamento);
        painelDados.add(lblValor);
        painelDados.add(valorPago);
        
        
        JPanel painelFinalizacao = new JPanel(new BorderLayout(10, 10));
        painelFinalizacao.setBackground(COR_PAINEL);
        painelFinalizacao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnFinalizarVenda = criarBotao("FINALIZAR VENDA", COR_SUCESSO);
        btnFinalizarVenda.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnFinalizarVenda.addActionListener(e -> finalizarVenda());
        
        JButton btnCancelar = criarBotao("CANCELAR", COR_ALERTA);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancelar.addActionListener(e -> limparTela());
        
        JPanel botoesFinalizacao = new JPanel(new GridLayout(1, 2, 10, 0));
        botoesFinalizacao.setOpaque(false);
        botoesFinalizacao.add(btnFinalizarVenda);
        botoesFinalizacao.add(btnCancelar);
       
     
        painelFinalizacao.add(botoesFinalizacao, BorderLayout.SOUTH);
        
       
        painel.add(painelDados);
        painel.add(painelFinalizacao);
        
        return painel;
    }

    private JButton criarBotao(String texto, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        botao.setFocusPainted(false);
        botao.setBackground(corFundo);
        botao.setForeground(COR_TEXTO_CLARO);
        botao.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        botao.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(corFundo.brighter());
            }
            
            public void mouseExited(MouseEvent e) {
                botao.setBackground(corFundo);
            }
        });
        
        return botao;
    }

    
    private void carregarDadosIniciais() {
        LivroService livroService = new LivroService();
    	 modeloTabelaLivros.setRowCount(0);
	        List<Livro> livros = livroService.listarTodos();
	        for (Livro livro : livros) {
	            modeloTabelaLivros.addRow(new Object[]{
	                    livro.getIsbn(), livro.getTitulo(), livro.getAutor(), livro.getCategoria(), livro.getQuantidadeEmEstoque(),
	                    String.format("%.2f", livro.getPreco()),
	            });
	        }
    }
    
    private void adicionarAoCarrinho() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um livro para adicionar ao carrinho", 
                "Nenhum livro selecionado", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        // Verificar estoque
        int estoque = Integer.parseInt(tabelaLivros.getValueAt(linhaSelecionada, 4).toString());
        if (estoque <= 0) {
            JOptionPane.showMessageDialog(this, 
                "Este livro nao possui estoque disponivel", 
                "Estoque Insuficiente", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String titulo = tabelaLivros.getValueAt(linhaSelecionada, 1).toString();
        String precoStr = tabelaLivros.getValueAt(linhaSelecionada, 5).toString();
        double preco = Double.parseDouble(precoStr.replace("MT ", ""));
        
        // Verificar se o livro já esta no carrinho
        for (int i = 0; i < modeloTabelaCarrinho.getRowCount(); i++) {
            if (modeloTabelaCarrinho.getValueAt(i, 0).equals(titulo)) {
                int qtdAtual = Integer.parseInt(modeloTabelaCarrinho.getValueAt(i, 1).toString());
                if (qtdAtual + 1 > estoque) {
                    JOptionPane.showMessageDialog(this, 
                        "Estoque insuficiente para adicionar mais unidades!", 
                        "Estoque Insuficiente", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                modeloTabelaCarrinho.setValueAt(qtdAtual + 1, i, 1); 
                double subtotal = (qtdAtual + 1) * preco;
                modeloTabelaCarrinho.setValueAt(formatoMoeda.format(subtotal), i, 3);
                atualizarTotalVenda();
                return;
            }
        }
        
       
        Object[] novoItem = {
            titulo,
            1,
            formatoMoeda.format(preco),
            formatoMoeda.format(preco)
        };
        modeloTabelaCarrinho.addRow(novoItem);
        atualizarTotalVenda();
    }
    
    private void removerDoCarrinho() {
        int linhaSelecionada = tabelaCarrinho.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um livro para remover do carrinho", 
                "Nenhum item selecionado", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        modeloTabelaCarrinho.removeRow(linhaSelecionada);
        atualizarTotalVenda();
    }
    
    private void limparCarrinho() {
        while (modeloTabelaCarrinho.getRowCount() > 0) {
            modeloTabelaCarrinho.removeRow(0);
        }
        atualizarTotalVenda();
    }
    
 
    private void atualizarTotalVenda() {
        double total = 0;
        for (int i = 0; i < modeloTabelaCarrinho.getRowCount(); i++) {
            String subtotalStr = modeloTabelaCarrinho.getValueAt(i, 3).toString();
            
            double subtotal = Double.parseDouble(subtotalStr.replace("MT ", "").replace(",", ""));
            total += subtotal;
        }
        
   
        
        lblTotalVenda.setText(formatoMoeda.format(total));
    }
    
    private void finalizarVenda() {
        if (modeloTabelaCarrinho.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "O carrinho esta vazio. Adicione livros antes de finalizar a venda.", 
                "Carrinho Vazio", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (cmbCliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um cliente para finalizar a venda.", 
                "Cliente nao selecionado", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Atualizar estoque dos livros
        for (int i = 0; i < modeloTabelaCarrinho.getRowCount(); i++) {
            String tituloCarrinho = modeloTabelaCarrinho.getValueAt(i, 0).toString();
            int quantidadeVendida = Integer.parseInt(modeloTabelaCarrinho.getValueAt(i, 1).toString());
            
            for (int j = 0; j < modeloTabelaLivros.getRowCount(); j++) {
                if (modeloTabelaLivros.getValueAt(j, 1).equals(tituloCarrinho)) {
                    int estoqueAtual = Integer.parseInt(modeloTabelaLivros.getValueAt(j, 4).toString());
                    modeloTabelaLivros.setValueAt(estoqueAtual - quantidadeVendida, j, 4);
                    break;
                }
            }
        }
        
       
        String formaPagamento = cmbFormaPagamento.getSelectedItem().toString();
        String cliente = cmbCliente.getText();
        String total = lblTotalVenda.getText();
        
        JOptionPane.showMessageDialog(this, 
            "Venda realizada com sucesso!\n\n" +
            "Cliente: " + cliente + "\n" +
            "Forma de Pagamento: " + formaPagamento + "\n" +
            "Total: " + total, 
            "Venda Finalizada", 
            JOptionPane.INFORMATION_MESSAGE);
        
       
        limparTela();
    }
    
    private void limparTela() {
        limparCarrinho();
        cmbCliente.setText("");;
        cmbFormaPagamento.setSelectedIndex(0);
        valorPago.setText("0");
        atualizarTotalVenda();
    }
}