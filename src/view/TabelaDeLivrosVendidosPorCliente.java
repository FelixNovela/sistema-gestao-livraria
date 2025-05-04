
package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.AdicionarLivroController;
import model.ItemVenda;
import model.Livro;
import model.Venda;
import service.VendaService;
import view.cores.Cores;

public class TabelaDeLivrosVendidosPorCliente extends JDialog {

	private JTable tabelaLivro;
	private DefaultTableModel modeloTabela;
	private Cores cores;
	private VendaService vendaService;
	private String id;
	private final String[] colunas = { "Título", "Quantidade", "Preço", "Subtotal" };


	public TabelaDeLivrosVendidosPorCliente(Frame parent, VendaService vendaService, String id) {
		super(parent, "Livros Vendidos", true);
		cores = new Cores();
		this.vendaService = vendaService;
		this.id = id;
		
		inicializarComponentes();
		atualizarDadosTabela();

	}

	private void inicializarComponentes() {
		setLayout(new BorderLayout(10, 10));
		setSize(450, 400);
		setLocationRelativeTo(null);
		getContentPane().setBackground(cores.COR_FUNDO);

		
		JPanel painelTitulo = new JPanel(new BorderLayout());
		painelTitulo.setBackground(cores.COR_PRIMARIA);
		painelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		JLabel labelTitulo = new JLabel("Livros Vendidos");
		labelTitulo.setFont(cores.FONTE_TITULO);
		labelTitulo.setForeground(cores.COR_TEXTO_CLARO);
		painelTitulo.add(labelTitulo, BorderLayout.CENTER);

		
		modeloTabela = new DefaultTableModel(colunas, 0) {

		};

		

		tabelaLivro = new JTable(modeloTabela);
		tabelaLivro.setFont(cores.FONTE_PADRAO);
		tabelaLivro.setRowHeight(30);
		tabelaLivro.setBackground(cores.COR_PAINEL);
		tabelaLivro.setForeground(cores.COR_TEXTO);
		tabelaLivro.setGridColor(cores.COR_BORDA);
		tabelaLivro.getTableHeader().setFont(cores.FONTE_PADRAO);
		tabelaLivro.getTableHeader().setBackground(cores.COR_PRIMARIA);
		tabelaLivro.getTableHeader().setForeground(cores.COR_TEXTO_CLARO);


		tabelaLivro.getColumnModel().getColumn(0).setPreferredWidth(150);
		tabelaLivro.getColumnModel().getColumn(1).setPreferredWidth(80);
		tabelaLivro.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabelaLivro.getColumnModel().getColumn(3).setPreferredWidth(100);

		JScrollPane scrollPane = new JScrollPane(tabelaLivro);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrollPane.setBackground(cores.COR_FUNDO);

	
		JPanel painelCentral = new JPanel(new BorderLayout());
		painelCentral.setBackground(cores.COR_FUNDO);
		painelCentral.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		painelCentral.add(scrollPane, BorderLayout.CENTER);

		
		add(painelTitulo, BorderLayout.NORTH);
		add(painelCentral, BorderLayout.CENTER);

	}

	
	public void adicionarLinha(String titulo, String quantidade, String preco, String subtotal) {
		modeloTabela.addRow(new Object[] { titulo, quantidade, preco, subtotal });
	}

	public void atualizarDadosTabela() {
		List<Venda> vendas = vendaService.listarVendas();
		List<ItemVenda> itv = null;
		while (modeloTabela.getRowCount() > 0) {
            modeloTabela.removeRow(0);
        }

		for (int i = 0; i < vendas.size(); i++) {
			if (vendas.get(i).getId().equals(id)) {
				itv = vendas.get(i).getItens();
			}
		}
		for (ItemVenda iv : itv) {

			adicionarLinha(iv.getLivro().getTitulo(), String.valueOf(iv.getQuantidade()),
					String.valueOf(iv.getLivro().getPreco()),
					String.format("%.2f", (iv.getLivro().getPreco() * iv.getQuantidade())));

		}
	}

	public void fechar() {
		dispose();
	}

}