package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import model.Livro;
import service.LivroService;

public class BotoesEditarLivro extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private JPanel painel;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private String isbn;
    private LivroService livroService;
    private DefaultTableModel modelo;
    private JTable tabela;

    private static final Color COR_BOTAO_EDITAR = new Color(0x00897B);
    private static final Color COR_BOTAO_EDITAR_HOVER = new Color(0x00695C);
    private static final Color COR_BOTAO_EXCLUIR = new Color(0xD32F2F);
    private static final Color COR_BOTAO_EXCLUIR_HOVER = new Color(0xB71C1C);
    private static final Color COR_TEXTO_BOTAO = new Color(0xFFFFFF);
    private static final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 12);

    public BotoesEditarLivro(JTable tabela, LivroService livroService, DefaultTableModel modelo) {
        this.tabela = tabela;
        this.livroService = livroService;
        this.modelo = modelo;
        
        
        painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.X_AXIS));
        painel.setOpaque(false);
        
       
        botaoEditar = new JButton("Editar");
        configurarBotao(botaoEditar, COR_BOTAO_EDITAR, COR_BOTAO_EDITAR_HOVER);
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarLivro();
            }
        });
        
       
        botaoExcluir = new JButton("Excluir");
        configurarBotao(botaoExcluir, COR_BOTAO_EXCLUIR, COR_BOTAO_EXCLUIR_HOVER);
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerLivro();
            }
        });
        
        
        painel.add(Box.createHorizontalGlue());
        painel.add(botaoEditar);
        painel.add(Box.createRigidArea(new Dimension(10, 0)));
        painel.add(botaoExcluir);
        painel.add(Box.createHorizontalGlue());
    }
    
    private void configurarBotao(JButton botao, Color corNormal, Color corHover) {
        botao.setFont(FONTE_BOTAO);
        botao.setForeground(COR_TEXTO_BOTAO);
        botao.setBackground(corNormal);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setOpaque(true);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(80, 30));
        botao.setMaximumSize(new Dimension(80, 30));
        
      
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(corHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(corNormal);
            }
        });
    }
    
    private void editarLivro() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            linha = tabela.convertRowIndexToModel(linha);
            String isbn = (String) modelo.getValueAt(linha, 0);
            Livro livro = livroService.buscarPorIsbn(isbn);
            
            if (livro != null) {
                Frame framePai = (Frame) SwingUtilities.getWindowAncestor(tabela);
                EditarLivroView dialog = new EditarLivroView(framePai, livro, livroService, modelo);
                dialog.setVisible(true);
            }
        }
        fireEditingStopped();
    }
    
    private void removerLivro() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            linha = tabela.convertRowIndexToModel(linha);
            String isbn = (String) modelo.getValueAt(linha, 0);
            
            Frame framePai = (Frame) SwingUtilities.getWindowAncestor(tabela);
            int confirmacao = JOptionPane.showConfirmDialog(
                framePai,
                "Tem certeza que deseja excluir o livro com ISBN: " + isbn + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
            	livroService.excluir(isbn);
                modelo.removeRow(linha);
                JOptionPane.showMessageDialog(
                    framePai,
                    "Livro excluido com sucesso",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
        fireEditingStopped();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
       
        this.isbn = (String) table.getModel().getValueAt(row, 0);
        
        
        if (isSelected) {
            painel.setBackground(table.getSelectionBackground());
        } else {
            painel.setBackground(row % 2 == 0 ? new Color(0xEFEBE9) : new Color(0xBCAAA4));
        }
        
        return painel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        this.isbn = (String) table.getModel().getValueAt(row, 0);
        
       
        painel.setBackground(table.getSelectionBackground());
        
        return painel;
    }

    @Override
    public Object getCellEditorValue() {
        return "Acoes";
    }
}