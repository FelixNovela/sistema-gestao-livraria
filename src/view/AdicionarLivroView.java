package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AdicionarLivroController;
import view.cores.Cores;

public class AdicionarLivroView extends JDialog {

    private JTextField campoISBN;
    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JComboBox<String> comboCategoria; // Alterado para JComboBox
    private JTextField campoPreco;
    private JTextField campoQuantidade;
    private Cores cores;
    private AdicionarLivroController controller;;

    public AdicionarLivroView(Frame parent) {
        super(parent, "Adicionar Livro", true);
        inicializarComponentes();
    }
    
    public void setController(AdicionarLivroController controller) {
        this.controller = controller;
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setSize(500, 550);
        setLocationRelativeTo(null);
        
        cores = new Cores();
        getContentPane().setBackground(cores.COR_FUNDO);

        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(cores.COR_PRIMARIA);
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel labelTitulo = new JLabel("Novo Livro");
        labelTitulo.setFont(cores.FONTE_TITULO);
        labelTitulo.setForeground(cores.COR_TEXTO_CLARO);
        painelTitulo.add(labelTitulo, BorderLayout.CENTER);

       
        JPanel painelCampos = new JPanel(new GridLayout(6, 2, 10, 10));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelCampos.setBackground(cores.COR_FUNDO);

       
        painelCampos.add(criarLabel("ISBN:"));
        campoISBN = criarCampoTexto();
        painelCampos.add(campoISBN);

        painelCampos.add(criarLabel("Título:"));
        campoTitulo = criarCampoTexto();
        painelCampos.add(campoTitulo);

        painelCampos.add(criarLabel("Autor:"));
        campoAutor = criarCampoTexto();
        painelCampos.add(campoAutor);

        painelCampos.add(criarLabel("Categoria:"));
        
        comboCategoria = criarComboBoxCategorias();
        painelCampos.add(comboCategoria);

        painelCampos.add(criarLabel("Preco:"));
        campoPreco = criarCampoTexto();
        painelCampos.add(campoPreco);

        painelCampos.add(criarLabel("Quantidade:"));
        campoQuantidade = criarCampoTexto();
        painelCampos.add(campoQuantidade);

       
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBackground(cores.COR_FUNDO);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
        
        JButton botaoSalvar = criarBotao("Salvar");
        JButton botaoCancelar = criarBotao("Cancelar");

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

    
        add(painelTitulo, BorderLayout.NORTH);
        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

       
        botaoSalvar.addActionListener(e -> {
            if (controller != null) {
                controller.salvarLivro();
            }
        });
        
        botaoCancelar.addActionListener(e -> dispose());
    }
    
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(cores.FONTE_PADRAO);
        label.setForeground(cores.COR_TEXTO);
        return label;
    }
    
    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField(); 
        campo.setFont(cores.FONTE_PADRAO);
        campo.setBackground(cores.COR_PAINEL);
        campo.setForeground(cores.COR_TEXTO);
        campo.setCaretColor(cores.COR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cores.COR_BORDA, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return campo;
    }
    
    // Novo método para criar e configurar o ComboBox de categorias
    private JComboBox<String> criarComboBoxCategorias() {
        String[] categorias = {"Mangá", "Tecnologia",  "Ficção Científica","Ciência"};
        
        JComboBox<String> combo = new JComboBox<>(categorias);
        combo.setFont(cores.FONTE_PADRAO);
        combo.setBackground(cores.COR_PAINEL);
        combo.setForeground(cores.COR_TEXTO);
       
        combo.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? cores.COR_BOTAO : cores.COR_PAINEL);
                setForeground(isSelected ? cores.COR_TEXTO_CLARO : cores.COR_TEXTO);
                return this;
            }
        });
        return combo;
    }
    
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new java.awt.Dimension(130, 36));
        botao.setBackground(cores.COR_BOTAO);
        botao.setForeground(cores.COR_TEXTO_CLARO);
        botao.setFont(cores.FONTE_BOTAO);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        botao.setOpaque(true);

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(cores.COR_BOTAO_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(cores.COR_BOTAO);
            }
        });
        
        return botao;
    }
    
   
    public String getIsbn() {
        return campoISBN.getText().trim();
    }
    
    public String getTitulo() {
        return campoTitulo.getText().trim();
    }
    
    public String getAutor() {
        return campoAutor.getText().trim();
    }
    
    // Método atualizado para obter a categoria selecionada do ComboBox
    public String getCategoria() {
        return (String) comboCategoria.getSelectedItem();
    }
    
    public String getPreco() {
        return campoPreco.getText().trim();
    }
    
    public String getQuantidade() {
        return campoQuantidade.getText().trim();
    }
    
    public void fechar() {
        dispose();
    }
}