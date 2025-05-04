package view;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controller.VendasController;
import view.cores.Cores;

public class VendasView extends JPanel {
    
    private JTextField campoPesquisa;
    private Cores cores;
    private JTable tabelaLivros;
    private DefaultTableModel modeloTabelaLivros;
    private JTable tabelaCarrinho;
    private DefaultTableModel modeloTabelaCarrinho;
    private JLabel lblTotalVenda;
    private JTextField cmbCliente;
    private JComboBox<String> cmbFormaPagamento;
    private JTextField valorPago;
    private JButton btnFinalizarVenda;
    private DecimalFormat formatoMoeda = new DecimalFormat("MT 0.00");
    private String nomeUsuario;
   
    private VendasController controller;
    
    public VendasView(String nomeUsuario) {
        cores = new Cores();
        controller = new VendasController(this);
        
        this.nomeUsuario = nomeUsuario;
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(cores.COR_FUNDO);

        add(criarPainelSuperior(), BorderLayout.NORTH);
        add(criarPainelLivros(), BorderLayout.CENTER);
        add(criarPainelCarrinho(), BorderLayout.EAST);
        add(criarPainelInferior(), BorderLayout.SOUTH);
        
        controller.carregarDadosIniciais();
    }

    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout(10, 0));
        painel.setBackground(cores.COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel titulo = new JLabel("Realizar Venda");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(cores.COR_PRIMARIA);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        JLabel data = new JLabel("Data: " + sdf.format(new Date()));
        data.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        data.setForeground(cores.COR_TEXTO);

        painel.add(titulo, BorderLayout.WEST);
        painel.add(data, BorderLayout.EAST);

        return painel;
    }

    private JPanel criarPainelLivros() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(cores.COR_FUNDO);
        
        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelPesquisa.setBackground(cores.COR_FUNDO);
        
        campoPesquisa = new JTextField("Pesquisar...");
        campoPesquisa.setPreferredSize(new Dimension(530, 30));
        campoPesquisa.setMaximumSize(new Dimension(180, 30));
        campoPesquisa.setFont(cores.FONTE_PADRAO);
        campoPesquisa.setBackground(cores.COR_PAINEL);
        campoPesquisa.setForeground(Color.GRAY);
        campoPesquisa.setCaretColor(cores.COR_TEXTO);
        campoPesquisa.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cores.COR_BORDA, 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        campoPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (campoPesquisa.getText().equals("Pesquisar...")) {
                    campoPesquisa.setText("");
                    campoPesquisa.setForeground(cores.COR_TEXTO);
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
                    c.setBackground((row % 2 == 0) ? cores.COR_FUNDO : cores.COR_DESTAQUE);
                    
                    if (col == 4) {
                        try {
                            int estoque = Integer.parseInt(getValueAt(row, col).toString());
                            if (estoque <= 3) c.setBackground(cores.COR_ALERTA);
                        } catch (NumberFormatException ignored) {}
                    }
                } else {
                    c.setBackground(cores.COR_DESTAQUE);
                }
                c.setForeground(cores.COR_TEXTO);
                return c;
            }
        };
        
        tabelaLivros.setRowHeight(30);
        tabelaLivros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaLivros.setGridColor(cores.COR_DESTAQUE);
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaLivros.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabelaLivros.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaLivros.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabelaLivros.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelaLivros.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabelaLivros.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        JScrollPane scrollLivros = new JScrollPane(tabelaLivros);
        scrollLivros.getViewport().setBackground(cores.COR_PAINEL);
        scrollLivros.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.setBackground(cores.COR_FUNDO);
        
        JButton btnAdicionar = criarBotao("Adicionar ao Carrinho", cores.COR_SUCESSO);
        btnAdicionar.addActionListener(e -> controller.adicionarAoCarrinho());
        
        painelBotoes.add(btnAdicionar);
        
        painel.add(painelPesquisa, BorderLayout.NORTH);
        
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBackground(cores.COR_FUNDO);
        painelCentral.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(cores.COR_DESTAQUE),
            "Livros Disponíveis", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16), cores.COR_PRIMARIA
        ));
        painelCentral.add(scrollLivros, BorderLayout.CENTER);
        
        painel.add(painelCentral, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.SOUTH);
        
        return painel;
    }

    private JPanel criarPainelCarrinho() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(cores.COR_FUNDO);
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
        tabelaCarrinho.setGridColor(cores.COR_DESTAQUE);
        
        JTextField editorQtd = new JTextField();
        editorQtd.setHorizontalAlignment(JTextField.CENTER);
        tabelaCarrinho.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(editorQtd));
        
        modeloTabelaCarrinho.addTableModelListener(e -> {
            if (e.getColumn() == 1) { 
                controller.atualizarQuantidadeItem(e.getFirstRow());
            }
        });
        
        JScrollPane scrollCarrinho = new JScrollPane(tabelaCarrinho);
        scrollCarrinho.getViewport().setBackground(cores.COR_PAINEL);
        scrollCarrinho.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBackground(cores.COR_FUNDO);
        
        JButton btnRemover = criarBotao("Remover Item", cores.COR_ALERTA);
        btnRemover.addActionListener(e -> controller.removerDoCarrinho());
        
        JButton btnLimpar = criarBotao("Limpar Carrinho", cores.COR_SECUNDARIA);
        btnLimpar.addActionListener(e -> controller.limparCarrinho());
        
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnLimpar);
        
        JPanel painelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTotal.setBackground(cores.COR_FUNDO);
        
        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(cores.COR_TEXTO);
        
        lblTotalVenda = new JLabel(formatoMoeda.format(0));
        lblTotalVenda.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalVenda.setForeground(cores.COR_PRIMARIA);
        
        painelTotal.add(lblTotal);
        painelTotal.add(lblTotalVenda);
        
        JPanel painelCarrinhoTitulo = new JPanel(new BorderLayout());
        painelCarrinhoTitulo.setBackground(cores.COR_FUNDO);
        painelCarrinhoTitulo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(cores.COR_DESTAQUE),
            "Carrinho de Compras", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16), cores.COR_PRIMARIA
        ));
        painelCarrinhoTitulo.add(scrollCarrinho, BorderLayout.CENTER);
        
        painel.add(painelCarrinhoTitulo, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.NORTH);
        painel.add(painelTotal, BorderLayout.SOUTH);
        
        return painel;
    }

    private JPanel criarPainelInferior() {
        JPanel painel = new JPanel(new GridLayout(2, 1, 20, 0));
        painel.setBackground(cores.COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JPanel painelDados = new JPanel(new GridLayout(1, 6, 10, 10));
        painelDados.setBackground(cores.COR_PAINEL);
        painelDados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbCliente = new JTextField("");
        cmbCliente.setFont(cores.FONTE_PADRAO);
        cmbCliente.setBackground(cores.COR_FUNDO);
        cmbCliente.setForeground(cores.COR_TEXTO);
        
        JLabel lblPagamento = new JLabel("Forma de Pagamento:");
        lblPagamento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        String[] formasPagamento = {"Dinheiro", "Transferencia"};
        cmbFormaPagamento = new JComboBox<>(formasPagamento);
        cmbFormaPagamento.setFont(cores.FONTE_PADRAO);
        cmbFormaPagamento.setBackground(cores.COR_FUNDO);
        cmbFormaPagamento.setForeground(cores.COR_TEXTO);
        
       
        
        JLabel lblValor = new JLabel("valor:");
        lblValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        valorPago = new JTextField("0");
        valorPago.setFont(cores.FONTE_PADRAO);
        valorPago.setBackground(cores.COR_FUNDO);
        valorPago.setForeground(cores.COR_TEXTO);
        valorPago.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                controller.atualizarTotalVenda();
            }
        });
        
        painelDados.add(lblCliente);
        painelDados.add(cmbCliente);
        painelDados.add(lblPagamento);
        painelDados.add(cmbFormaPagamento);
        painelDados.add(lblValor);
        painelDados.add(valorPago);
        
        JPanel painelFinalizacao = new JPanel(new BorderLayout(10, 10));
        painelFinalizacao.setBackground(cores.COR_PAINEL);
        painelFinalizacao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnFinalizarVenda = criarBotao("FINALIZAR VENDA", cores.COR_SUCESSO);
        btnFinalizarVenda.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnFinalizarVenda.addActionListener(e -> controller.finalizarVenda());
        
        JButton btnCancelar = criarBotao("CANCELAR", cores.COR_ALERTA);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancelar.addActionListener(e -> controller.limparTela());
        
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
        botao.setForeground(cores.COR_TEXTO_CLARO);
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
    
    
    public JTable getTabelaLivros() {
        return tabelaLivros;
    }
    
    public DefaultTableModel getModeloTabelaLivros() {
        return modeloTabelaLivros;
    }
    
    public JTable getTabelaCarrinho() {
        return tabelaCarrinho;
    }
    
    public DefaultTableModel getModeloTabelaCarrinho() {
        return modeloTabelaCarrinho;
    }
    
    public JLabel getLblTotalVenda() {
        return lblTotalVenda;
    }
    
    
    
    public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public JTextField getCampoCliente() {
        return cmbCliente;
    }
    
    public JComboBox<String> getCmbFormaPagamento() {
        return cmbFormaPagamento;
    }
    
    public JTextField getValorPago() {
        return valorPago;
    } 
    
    public DecimalFormat getFormatoMoeda() {
        return formatoMoeda;
    }
    
   
    public void mostrarMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
}