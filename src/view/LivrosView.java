package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.LivrosController;
import service.LivroService;
import view.cores.Cores;

public class LivrosView extends JPanel {

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
	private final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 26);
	private final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 13);

	private JTextField campoPesquisa;
	private DefaultTableModel modeloTabela;
	private JTable tabela;

	private LivrosController controller;

	public LivrosView() {
		setLayout(new BorderLayout(10, 10));
		setBackground(COR_FUNDO);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		add(criarHeader(), BorderLayout.NORTH);
		add(criarPainelPrincipal(), BorderLayout.CENTER);
		LivrosController controller = new LivrosController(this, new LivroService());
	}

	public void setController(LivrosController controller) {
		this.controller = controller;
	}

	private JPanel criarHeader() {
		JPanel header = new JPanel(new BorderLayout());
		header.setPreferredSize(new Dimension(1000, 80));
		header.setBackground(COR_PRIMARIA);
		header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JLabel titulo = new JLabel("Livros");
		titulo.setFont(FONTE_TITULO);
		titulo.setForeground(COR_TEXTO_CLARO);

		JPanel painelDireita = new JPanel();
		painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.X_AXIS));
		painelDireita.setBackground(COR_PRIMARIA);

		campoPesquisa = new JTextField("Pesquisar...");
		campoPesquisa.setPreferredSize(new Dimension(180, 30));
		campoPesquisa.setMaximumSize(new Dimension(180, 30));
		campoPesquisa.setFont(FONTE_PADRAO);
		campoPesquisa.setBackground(COR_PAINEL);
		campoPesquisa.setForeground(Color.GRAY);
		campoPesquisa.setCaretColor(COR_TEXTO);
		campoPesquisa.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(COR_BORDA, 1),
				BorderFactory.createEmptyBorder(5, 15, 5, 15)));

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

		campoPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				if (controller != null) {
					controller.filtrarLivros(campoPesquisa.getText().trim().toLowerCase());
				}
			}
		});

		JButton botaoAdicionar = criarBotao("Adicionar");
		botaoAdicionar.addActionListener(e -> {
			if (controller != null) {
				controller.abrirDialogAdicionarLivro();
			}
		});

		painelDireita.add(campoPesquisa);
		painelDireita.add(Box.createRigidArea(new Dimension(8, 0)));
		painelDireita.add(Box.createRigidArea(new Dimension(8, 0)));
		painelDireita.add(botaoAdicionar);

		header.add(titulo, BorderLayout.WEST);
		header.add(painelDireita, BorderLayout.EAST);

		return header;
	}

	private JButton criarBotao(String texto) {
		JButton botao = new JButton(texto);
		botao.setPreferredSize(new Dimension(130, 36));
		botao.setMaximumSize(new Dimension(130, 36));
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

	private JPanel criarPainelPrincipal() {
		JPanel painel = new JPanel(new BorderLayout());
		painel.setBackground(COR_FUNDO);
		painel.add(criarTabelaLivros(), BorderLayout.CENTER);
		return painel;
	}

	private JScrollPane criarTabelaLivros() {
		String[] colunas = { "ISBN", "Título", "Autor", "Categoria", "Preco", "Estoque", "Acoes" };

		modeloTabela = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 6;
			}
		};

		tabela = new JTable(modeloTabela) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!isRowSelected(row)) {
					c.setBackground(row % 2 == 0 ? COR_FUNDO : COR_DESTAQUE);
					
					if (column == 5) {
						Object valor = getValueAt(row, column);
						if (valor != null) {
							try {
								int unidades = Integer.parseInt(valor.toString());
								if (unidades <= 5) {
									c.setBackground(Cores.COR_ALERTA);
								}
							} catch (NumberFormatException ignored) {

							}
						}
					}
				} else {
					
					c.setBackground(Cores.COR_PAINEL);

				}
				c.setForeground(COR_TEXTO);
				return c;
			}
		};

		tabela.setRowHeight(40);
		tabela.setFont(FONTE_PADRAO);
		tabela.setGridColor(COR_BORDA);
		tabela.setAutoCreateRowSorter(true);

		JScrollPane scroll = new JScrollPane(tabela);
		scroll.getViewport().setBackground(COR_FUNDO);
		scroll.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		return scroll;
	}

	public DefaultTableModel getModeloTabela() {
		return modeloTabela;
	}

	public JTable getTabela() {
		return tabela;
	}

	public JTextField getCampoPesquisa() {
		return campoPesquisa;
	}

	public void atualizarTabela(Object[][] dados) {
		modeloTabela.setRowCount(0);
		for (Object[] linha : dados) {
			modeloTabela.addRow(linha);
		}
	}
}