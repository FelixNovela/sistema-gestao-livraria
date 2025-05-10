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
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import service.VendaService;

public class BotaoVerListaDeLivrosVendidos extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private JPanel painel;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private String isbn;
    private VendaService vendaService;
    private DefaultTableModel modelo;
    private JTable tabela;

    private static final Color COR_BOTAO_EDITAR = new Color(0x00897B);
    private static final Color COR_BOTAO_EDITAR_HOVER = new Color(0x00695C);
    private static final Color COR_BOTAO_EXCLUIR = new Color(0xD32F2F);
    private static final Color COR_BOTAO_EXCLUIR_HOVER = new Color(0xB71C1C);
    private static final Color COR_TEXTO_BOTAO = new Color(0xFFFFFF);
    private static final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 12);

    public BotaoVerListaDeLivrosVendidos(JTable tabela, VendaService vendaService, DefaultTableModel modelo) {
        this.tabela = tabela;
        this.vendaService = vendaService;
        this.modelo = modelo;
        
        
        painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.X_AXIS));
        painel.setOpaque(false);
        
       
        botaoEditar = new JButton("Livros");
        configurarBotao(botaoEditar, COR_BOTAO_EDITAR, COR_BOTAO_EDITAR_HOVER);
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarLivro();
            }
        });
        
       
       
        
        
        painel.add(Box.createHorizontalGlue());
        painel.add(botaoEditar);
        painel.add(Box.createRigidArea(new Dimension(10, 0)));
      
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
            Object pegarId = modelo.getValueAt(linha, 0);
            String id = pegarId.toString();
           // Livro livro = vendaService.buscarPorIsbn(isbn);
            Frame framePai = (Frame) SwingUtilities.getWindowAncestor(tabela);
            TabelaDeLivrosVendidosPorCliente dialog = new TabelaDeLivrosVendidosPorCliente(framePai, vendaService, id);
            dialog.setVisible(true);
//            if (livro != null) {
//                Frame framePai = (Frame) SwingUtilities.getWindowAncestor(tabela);
//                TabelaDeLivrosVendidosPorCliente dialog = new TabelaDeLivrosVendidosPorCliente(framePai);
//                dialog.setVisible(true);
//            }
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