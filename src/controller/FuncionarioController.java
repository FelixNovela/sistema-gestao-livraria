package controller;

import java.awt.Frame;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.Usuario;
import service.UsuarioService;
import view.AdicionarFuncionarioView;
import view.BotoesEditarFuncionario;
import view.UsuarioView;

public class FuncionarioController {

	private UsuarioView view;
	private UsuarioService funcionarioService;

	public FuncionarioController(UsuarioView view, UsuarioService funcionarioService) {
		this.view = view;
		this.funcionarioService = funcionarioService;

		
		view.setController(this);

		
		configurarTabelaFuncionarios();

		
		atualizarDadosTabela();
	}

	
	private void configurarTabelaFuncionarios() {
		DefaultTableModel modeloTabela = view.getModeloTabela();
		JTable tabela = view.getTabela();

		
		tabela.getColumnModel().getColumn(6)
				.setCellRenderer(new BotoesEditarFuncionario(tabela, funcionarioService, this));
		tabela.getColumnModel().getColumn(6)
				.setCellEditor(new BotoesEditarFuncionario(tabela, funcionarioService, this));
	}

	
	public void atualizarDadosTabela() {
		List<Usuario> funcionarios = funcionarioService.listarTodos();
		DefaultTableModel modeloTabela = view.getModeloTabela();
		modeloTabela.setRowCount(0);

		for (Usuario f : funcionarios) {
			if (f.isAtivo()) {
				Object[] dados = new Object[] { f.getNumeroDoBI(), f.getNome(), f.getEmail(),
						f.getUsuario(), f.getSenha(),f.getNivelAcesso(),
						"Acoes"
				};
				modeloTabela.addRow(dados);
			}
		}
	}

	
	public void filtrarFuncionarios(String textoPesquisa) {
		List<Usuario> funcionarios = funcionarioService.listarTodos();

		
		String pesquisa = textoPesquisa.toLowerCase();

		
		Object[][] dadosFiltrados = funcionarios.stream()
				.filter(f -> f.isAtivo() && (f.getNome().toLowerCase().contains(pesquisa)
						
						|| f.getNumeroDoBI().toLowerCase().contains(pesquisa)
						|| (f.getUsuario() != null && f.getUsuario().toLowerCase().contains(pesquisa))))
				.map(f -> new Object[] { f.getNumeroDoBI(), f.getNome(), f.getEmail(),
						f.getUsuario(),f.getSenha(),f.getNivelAcesso(), "Acoes" })
				.toArray(Object[][]::new);

		view.atualizarTabela(dadosFiltrados);
	}

	public void abrirDialogAdicionarFuncionario() {

		Frame framePai = (Frame) SwingUtilities.getWindowAncestor(view);
		AdicionarFuncionarioView dialog = new AdicionarFuncionarioView(framePai);
		AdicionarUsuarioController adicionarFuncionarioController = new AdicionarUsuarioController(dialog,
				funcionarioService, view.getModeloTabela());
		dialog.setController(adicionarFuncionarioController);
		dialog.setVisible(true);

	}


}