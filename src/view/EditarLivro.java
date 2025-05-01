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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Livro;
import service.LivroService;

public class EditarLivro extends JDialog {
   
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
    
    private Livro livro;
    private LivroService livroService;
    private DefaultTableModel modeloTabela;

    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JTextField campoCategoria;
    private JTextField campoPreco;
    private JTextField campoEstoque;

    public EditarLivro(Frame owner, Livro livro, LivroService livroService, DefaultTableModel modeloTabela) {
        super(owner, "Editar Livro", true);
        this.livro = livro;
        this.livroService = livroService;
        this.modeloTabela = modeloTabela;

        inicializarComponentes();
        preencherCampos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setSize(400, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);

       
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(COR_PRIMARIA);
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel labelTitulo = new JLabel("Editar Livro");
        labelTitulo.setFont(FONTE_TITULO);
        labelTitulo.setForeground(COR_TEXTO_CLARO);
        painelTitulo.add(labelTitulo, BorderLayout.WEST);
        
        JLabel labelIsbn = new JLabel("ISBN: " + livro.getIsbn());
        labelIsbn.setFont(FONTE_PADRAO);
        labelIsbn.setForeground(COR_TEXTO_CLARO);
        painelTitulo.add(labelIsbn, BorderLayout.EAST);

    
        JPanel painelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelCampos.setBackground(COR_FUNDO);


        painelCampos.add(criarLabel("Título:"));
        campoTitulo = criarCampoTexto();
        campoTitulo.setEditable(false);
        painelCampos.add(campoTitulo);

        painelCampos.add(criarLabel("Autor:"));
        campoAutor = criarCampoTexto();
        campoAutor.setEditable(false);
        painelCampos.add(campoAutor);

        painelCampos.add(criarLabel("Categoria:"));
        campoCategoria = criarCampoTexto();
        campoCategoria.setEditable(false);
        painelCampos.add(campoCategoria);

        painelCampos.add(criarLabel("Preco:"));
        campoPreco = criarCampoTexto();
        painelCampos.add(campoPreco);

        painelCampos.add(criarLabel("Estoque:"));
        campoEstoque = criarCampoTexto();
        painelCampos.add(campoEstoque);

        
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

       
        botaoSalvar.addActionListener(e -> salvar());
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

    private void preencherCampos() {
        campoTitulo.setText(livro.getTitulo());
        campoAutor.setText(livro.getAutor());
        campoCategoria.setText(livro.getCategoria());
        campoPreco.setText(String.valueOf(livro.getPreco()));
        campoEstoque.setText(String.valueOf(livro.getQuantidadeEmEstoque()));
    }

    private void salvar() {
        try {
            String titulo = campoTitulo.getText().trim();
            String autor = campoAutor.getText().trim();
            String editora = campoCategoria.getText().trim();
            String precoTexto = campoPreco.getText().trim();
            String estoqueTexto = campoEstoque.getText().trim();
            
            
            if (titulo.isEmpty() || autor.isEmpty() || editora.isEmpty() || 
                precoTexto.isEmpty() || estoqueTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Todos os campos sao obrigatorios.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double preco;
            int estoque;
            
            try {
                preco = Double.parseDouble(precoTexto);
                if (preco < 0) {
                    JOptionPane.showMessageDialog(this, 
                        "O preço não pode ser negativo.",
                        "Erro de Validação", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "O preco deve ser um número valido.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                estoque = Integer.parseInt(estoqueTexto);
                if (estoque < 0) {
                    JOptionPane.showMessageDialog(this, 
                        "O estoque nao pode ser negativo.",
                        "Erro de Validacao", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "O estoque deve ser um número inteiro valido.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            
            livro.setPreco(preco);
            livro.setQuantidadeEmEstoque(estoque);

            // Atualize no service
            //livroService.atualizar(livro);
            
           
            atualizarTabela();
            
            JOptionPane.showMessageDialog(this, 
                "Livro atualizado com sucesso!",
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao salvar: " + ex.getMessage(),
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarTabela() {
        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            if (modeloTabela.getValueAt(i, 0).equals(livro.getIsbn())) {
                modeloTabela.setValueAt(livro.getTitulo(), i, 1);
                modeloTabela.setValueAt(livro.getAutor(), i, 2);
                modeloTabela.setValueAt(livro.getCategoria(), i, 3);
                modeloTabela.setValueAt(String.format("%.2f", livro.getPreco()), i, 4);
                modeloTabela.setValueAt(livro.getQuantidadeEmEstoque(), i, 5);
                break;
            }
        }
        modeloTabela.fireTableDataChanged();
    }
}