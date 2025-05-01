package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AdicionarLivroController;

public class AdicionarLivroView extends JDialog {
   
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

    private final Font FONTE_PADRAO = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 13);

    private JTextField campoISBN;
    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JTextField campoEditora;
    private JTextField campoPreco;
    private JTextField campoQuantidade;
    
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
        setSize(400, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);

       
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(COR_PRIMARIA);
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel labelTitulo = new JLabel("Novo Livro");
        labelTitulo.setFont(FONTE_TITULO);
        labelTitulo.setForeground(COR_TEXTO_CLARO);
        painelTitulo.add(labelTitulo, BorderLayout.CENTER);

       
        JPanel painelCampos = new JPanel(new GridLayout(6, 2, 10, 10));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelCampos.setBackground(COR_FUNDO);

       
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
        campoEditora = criarCampoTexto();
        painelCampos.add(campoEditora);

        painelCampos.add(criarLabel("Preco:"));
        campoPreco = criarCampoTexto();
        painelCampos.add(campoPreco);

        painelCampos.add(criarLabel("Quantidade:"));
        campoQuantidade = criarCampoTexto();
        painelCampos.add(campoQuantidade);

       
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(COR_FUNDO);
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
        label.setFont(FONTE_PADRAO);
        label.setForeground(COR_TEXTO);
        return label;
    }
    
    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField(); 
        campo.setFont(FONTE_PADRAO);
        campo.setBackground(COR_PAINEL);
        campo.setForeground(COR_TEXTO);
        campo.setCaretColor(COR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_BORDA, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return campo;
    }
    
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new java.awt.Dimension(130, 36));
        botao.setBackground(COR_BOTAO);
        botao.setForeground(COR_TEXTO_CLARO);
        botao.setFont(FONTE_BOTAO);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        botao.setOpaque(true);

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(COR_BOTAO_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(COR_BOTAO);
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
    
    public String getEditora() {
        return campoEditora.getText().trim();
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