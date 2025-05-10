package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.toedter.calendar.JDateChooser;

import model.ItemVenda;
import model.Venda;
import service.RelatoriosService;

public class RelatoriosView extends JPanel {

    private static final Color COR_PRIMARIA = new Color(0x5D4037);       
    private static final Color COR_SECUNDARIA = new Color(0x8D6E63);     
    private static final Color COR_FUNDO = new Color(0xF5F5F5);          
    private static final Color COR_TEXTO = new Color(0x212121);         

    private static final Font FONTE_TITULO = new Font("Arial", Font.BOLD, 20);
    private static final Font FONTE_NORMAL = new Font("Arial", Font.PLAIN, 14);
    private static final Font FONTE_NEGRITO = new Font("Arial", Font.BOLD, 14);

    private JComboBox<String> cbTipoRelatorio;
    private JComboBox<String> cbCategoria;
    private JPanel painelCategoria;
    private JDateChooser dataInicio;
    private JDateChooser dataFim;
    private JButton btnGerar;
    private JButton btnExportar;
   
    private JTable tabelaResultados;
    private DefaultTableModel modeloTabela;
    private JLabel lblTotalRegistros;
    private JLabel lblTotalVendido;

    private RelatoriosService relatoriosService;
  

    public RelatoriosView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(COR_FUNDO);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Inicializar o serviço de relatórios
        relatoriosService = new RelatoriosService();
        
        
        

        JLabel titulo = new JLabel("Relatorios", JLabel.CENTER);
        titulo.setFont(FONTE_TITULO);
        titulo.setForeground(COR_PRIMARIA);
        add(titulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new BorderLayout(0, 10));
        painelCentral.setBackground(COR_FUNDO);

        painelCentral.add(criarPainelFiltros(), BorderLayout.NORTH);
        painelCentral.add(criarPainelTabela(), BorderLayout.CENTER);

        add(painelCentral, BorderLayout.CENTER);
    }

    private JPanel criarPainelFiltros() {
        JPanel painelFiltros = new JPanel();
        painelFiltros.setLayout(new BoxLayout(painelFiltros, BoxLayout.Y_AXIS));
        painelFiltros.setBackground(Color.WHITE);
        painelFiltros.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTitulo = new JLabel("Filtros");
        lblTitulo.setFont(FONTE_NEGRITO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator separador = new JSeparator();
        separador.setAlignmentX(Component.LEFT_ALIGNMENT);
        separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        painelFiltros.add(lblTitulo);
        painelFiltros.add(Box.createRigidArea(new Dimension(0, 5)));
        painelFiltros.add(separador);
        painelFiltros.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel gridFiltros = new JPanel(new GridLayout(2, 3, 10, 10));
        gridFiltros.setBackground(Color.WHITE);
        gridFiltros.setAlignmentX(Component.LEFT_ALIGNMENT);
        gridFiltros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        JPanel painelTipo = new JPanel(new BorderLayout());
        painelTipo.setBackground(Color.WHITE);
        JLabel lblTipo = new JLabel("Tipo de Relatório");
        lblTipo.setFont(FONTE_NORMAL);

        String[] tiposRelatorio = {
            "Vendas por Período", 
            "Livros Mais Vendidos", 
            "Vendas por Categoria"
        };
        cbTipoRelatorio = new JComboBox<>(tiposRelatorio);
        cbTipoRelatorio.setFont(FONTE_NORMAL);
        cbTipoRelatorio.setBackground(Color.WHITE);
        
        // Adicionar listener para controlar a visibilidade da ComboBox de categorias
        cbTipoRelatorio.addActionListener(e -> {
            boolean mostrarCategoria = cbTipoRelatorio.getSelectedItem().equals("Vendas por Categoria");
            painelCategoria.setVisible(mostrarCategoria);
            
            // Reorganizar o layout se necessário
            gridFiltros.revalidate();
            gridFiltros.repaint();
        });

        painelTipo.add(lblTipo, BorderLayout.NORTH);
        painelTipo.add(cbTipoRelatorio, BorderLayout.CENTER);

        JPanel painelDataInicio = new JPanel(new BorderLayout());
        painelDataInicio.setBackground(Color.WHITE);
        JLabel lblDataInicio = new JLabel("Data Início");
        lblDataInicio.setFont(FONTE_NORMAL);

        dataInicio = new JDateChooser();
        dataInicio.setDate(new Date());

        painelDataInicio.add(lblDataInicio, BorderLayout.NORTH);
        painelDataInicio.add(dataInicio, BorderLayout.CENTER);

        JPanel painelDataFim = new JPanel(new BorderLayout());
        painelDataFim.setBackground(Color.WHITE);
        JLabel lblDataFim = new JLabel("Data Fim");
        lblDataFim.setFont(FONTE_NORMAL);

        dataFim = new JDateChooser();
        dataFim.setDate(new Date());

        painelDataFim.add(lblDataFim, BorderLayout.NORTH);
        painelDataFim.add(dataFim, BorderLayout.CENTER);
        
        painelCategoria = new JPanel(new BorderLayout());
        painelCategoria.setBackground(Color.WHITE);
        JLabel lblCategoria = new JLabel("Selecione a Categoria");
        lblCategoria.setFont(FONTE_NORMAL);
        
        String[] categorias = {
            "Todas", "Mangá", "Tecnologia",  "Ficção Científica","Ciência"
        };
        cbCategoria = new JComboBox<>(categorias);
        cbCategoria.setFont(FONTE_NORMAL);
        cbCategoria.setBackground(Color.WHITE);
        
        painelCategoria.add(lblCategoria, BorderLayout.NORTH);
        painelCategoria.add(cbCategoria, BorderLayout.CENTER);
        painelCategoria.setVisible(false);

        gridFiltros.add(painelTipo);
        gridFiltros.add(painelDataInicio);
        gridFiltros.add(painelDataFim);
        gridFiltros.add(painelCategoria);
        
        gridFiltros.add(new JPanel() {{ setBackground(Color.WHITE); }});
        gridFiltros.add(new JPanel() {{ setBackground(Color.WHITE); }});

        painelFiltros.add(gridFiltros);
        painelFiltros.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(Color.WHITE);
        painelBotoes.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnGerar = new JButton("Gerar Relatorio");
        btnGerar.setFont(FONTE_NORMAL);
        btnGerar.setBackground(COR_PRIMARIA);
        btnGerar.setForeground(Color.WHITE);
        btnGerar.addActionListener(e -> gerarRelatorio());
        
        painelBotoes.add(btnGerar);
        painelFiltros.add(painelBotoes);

        return painelFiltros;
    }
    
    private void exportarParaPDF() {
        try {
            if (tabelaResultados.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, 
                    "Nao ha dados para exportar. Gere um relatorio primeiro.");
                return;
            }
            
            Document documento = new Document();
            String nomeArquivo = "relatorio_" + System.currentTimeMillis() + ".pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(nomeArquivo));
    
            documento.open();
    
            String tipoRelatorio = (String) cbTipoRelatorio.getSelectedItem();
            com.itextpdf.text.Font tituloFonte = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD);
            Paragraph titulo = new Paragraph("Relatório de " + tipoRelatorio + "\n\n", tituloFonte);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            
            com.itextpdf.text.Font normalFonte = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12);
            Paragraph periodo = new Paragraph("Período: " + 
                formatarData(dataInicio.getDate()) + " a " + 
                formatarData(dataFim.getDate()) + "\n\n", normalFonte);
            periodo.setAlignment(Element.ALIGN_CENTER);
            documento.add(periodo);
    
            if ("Vendas por Categoria".equals(tipoRelatorio)) {
                String categoriaSelecionada = (String) cbCategoria.getSelectedItem();
                Paragraph categoriaParagrafo = new Paragraph("Categoria: " + categoriaSelecionada + "\n\n", normalFonte);
                categoriaParagrafo.setAlignment(Element.ALIGN_CENTER);
                documento.add(categoriaParagrafo);
            }
    
            PdfPTable tabelaPDF = new PdfPTable(tabelaResultados.getColumnCount());
            tabelaPDF.setWidthPercentage(100);
    
            for (int i = 0; i < tabelaResultados.getColumnCount(); i++) {
                PdfPCell header = new PdfPCell(new Phrase(tabelaResultados.getColumnName(i)));
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setPadding(5);
                tabelaPDF.addCell(header);
            }
    
            for (int i = 0; i < tabelaResultados.getRowCount(); i++) {
                for (int j = 0; j < tabelaResultados.getColumnCount(); j++) {
                    Object valor = tabelaResultados.getValueAt(i, j);
                    PdfPCell cell = new PdfPCell(new Phrase(valor != null ? valor.toString() : ""));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5);
                    tabelaPDF.addCell(cell);
                }
            }
    
            documento.add(tabelaPDF);
            
            Paragraph totalInfo = new Paragraph("\n" + lblTotalRegistros.getText() + "\n" + lblTotalVendido.getText(), normalFonte);
            totalInfo.setAlignment(Element.ALIGN_RIGHT);
            documento.add(totalInfo);
    
            documento.close();
    
            JOptionPane.showMessageDialog(this, 
                "Relatorio exportado com sucesso para: " + nomeArquivo);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Erro ao exportar PDF: " + e.getMessage());
        }
    }
    
    private String formatarData(Date data) {
        return new java.text.SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    private JPanel criarPainelTabela() {
        JPanel painelTabela = new JPanel(new BorderLayout(0, 10));
        painelTabela.setBackground(Color.WHITE);
        painelTabela.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTitulo = new JLabel("Resultados");
        lblTitulo.setFont(FONTE_NEGRITO);
        lblTitulo.setForeground(COR_PRIMARIA);

        String[] colunas = {"ISBN", "Título", "Autor", "Categoria", "Preco", "Quantidade", "Total"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        
        tabelaResultados = new JTable(modeloTabela);
        tabelaResultados.setFont(FONTE_NORMAL);
        tabelaResultados.setRowHeight(30);
        tabelaResultados.setShowGrid(true);
        tabelaResultados.setGridColor(Color.LIGHT_GRAY);
        tabelaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabelaResultados.setFillsViewportHeight(true);

        JTableHeader header = tabelaResultados.getTableHeader();
        header.setFont(FONTE_NEGRITO);
        header.setBackground(COR_SECUNDARIA);
        header.setForeground(Color.WHITE);
        
        tabelaResultados.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xF5F5F5));
                }
                
                return c;
            }
        });

        JScrollPane scrollTabela = new JScrollPane(tabelaResultados);
        scrollTabela.setPreferredSize(new Dimension(800, 350));

        JPanel painelAcoes = new JPanel(new BorderLayout());
        painelAcoes.setBackground(Color.WHITE);

        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setBackground(Color.WHITE);
        
        lblTotalRegistros = new JLabel("Total: 0 registros");
        lblTotalRegistros.setFont(FONTE_NORMAL);
        
        lblTotalVendido = new JLabel("Total Vendido: MT 0,00");
        lblTotalVendido.setFont(FONTE_NEGRITO);
        lblTotalVendido.setForeground(COR_PRIMARIA);
        
        painelInfo.add(lblTotalRegistros);
        painelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        painelInfo.add(lblTotalVendido);
        
        btnExportar = new JButton("Exportar");
        btnExportar.setFont(FONTE_NORMAL);
        btnExportar.setBackground(COR_SECUNDARIA);
        btnExportar.setForeground(Color.WHITE);
        btnExportar.addActionListener(e -> exportarParaPDF());

        JPanel painelExportar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelExportar.setBackground(Color.WHITE);
        painelExportar.add(btnExportar);

        painelAcoes.add(painelInfo, BorderLayout.WEST);
        painelAcoes.add(painelExportar, BorderLayout.EAST);

        painelTabela.add(lblTitulo, BorderLayout.NORTH);
        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelTabela.add(painelAcoes, BorderLayout.SOUTH);

        return painelTabela;
    }

    private void gerarRelatorio() {
        if (dataInicio.getDate() == null || dataFim.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione datas validas para o relatorio.");
            return;
        }
        
        if (dataInicio.getDate().after(dataFim.getDate())) {
            JOptionPane.showMessageDialog(this, "A data de início deve ser anterior a data de fim.");
            return;
        }
        
        modeloTabela.setRowCount(0);
        
        String tipoRelatorio = (String) cbTipoRelatorio.getSelectedItem();
        
        try {
            switch (tipoRelatorio) {
                case "Vendas por Período":
                    gerarRelatorioPorPeriodo();
                    break;
                case "Livros Mais Vendidos":
                    gerarRelatorioLivrosMaisVendidos();
                    break;
                case "Vendas por Categoria":
                    String categoriaSelecionada = (String) cbCategoria.getSelectedItem();
                    gerarRelatorioPorCategoria(categoriaSelecionada);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Tipo de relatorio não implementado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatorio: " + e.getMessage());
        }
    }
    
    private void gerarRelatorioPorPeriodo() {
        // Obter itens de venda em vez de vendas completas
        List<ItemVenda> itens = relatoriosService.getItensVendaPorPeriodo(dataInicio.getDate(), dataFim.getDate());
        
        if (itens.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nao foram encontradas vendas no período selecionado.");
            return;
        }
        
        Object[][] dados = relatoriosService.converterItensParaLinhasTabela(itens);
        
        for (Object[] linha : dados) {
            modeloTabela.addRow(linha);
        }
        
        atualizarTotais(itens);
    }
    
    private void gerarRelatorioLivrosMaisVendidos() {
        List<Map.Entry<String, Integer>> livrosMaisVendidos = 
            relatoriosService.getLivrosMaisVendidos(dataInicio.getDate(), dataFim.getDate());
        
        if (livrosMaisVendidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nao foram encontradas vendas no período selecionado.");
            return;
        }
        
        Object[][] dados = relatoriosService.converterLivrosMaisVendidosParaLinhasTabela(livrosMaisVendidos);
        
        if (dados.length == 0) {
            JOptionPane.showMessageDialog(this, "Não foram encontrados livros com mais de 5 vendas no período.");
            return;
        }
        
        for (Object[] linha : dados) {
            modeloTabela.addRow(linha);
        }
        
        // Para este tipo de relatório, precisamos obter os itens de venda para calcular o total vendido
        List<ItemVenda> itens = relatoriosService.getItensVendaPorPeriodo(dataInicio.getDate(), dataFim.getDate());
        atualizarTotais(itens);
    }
    
    private void gerarRelatorioPorCategoria(String categoria) {
        // Clear existing data first
        modeloTabela.setRowCount(0);
        
        // Get filtered items instead of full sales
        List<ItemVenda> itens = relatoriosService.getItensPorCategoria(
            dataInicio.getDate(), dataFim.getDate(), categoria);
        
        if (itens.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Não foram encontradas vendas para a categoria '" + categoria + "' no período selecionado.");
            return;
        }
        
        // Convert filtered items to table rows
        Object[][] dados = relatoriosService.converterItensParaLinhasTabela(itens);
        
        for (Object[] linha : dados) {
            modeloTabela.addRow(linha);
        }
        
        atualizarTotais(itens);
    }
    
    private void atualizarTotais(List<ItemVenda> itens) {
        int totalRegistros = itens.size();
        double totalVendido = 0.0;
        
        for (ItemVenda item : itens) {
            totalVendido += item.getLivro().getPreco() * item.getQuantidade();
        }
        
        lblTotalRegistros.setText("Total: " + totalRegistros + " registros");
        lblTotalVendido.setText("Total Vendido: " +totalVendido);
    }

    public JButton getBtnGerar() {
        return btnGerar;
    }

    public JButton getBtnExportar() {
        return btnExportar;
    }

    public JComboBox<String> getCbTipoRelatorio() {
        return cbTipoRelatorio;
    }
    
    public JComboBox<String> getCbCategoria() {
        return cbCategoria;
    }

    public JDateChooser getDataInicio() {
        return dataInicio;
    }

    public JDateChooser getDataFim() {
        return dataFim;
    }
}