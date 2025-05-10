package controller;

import java.awt.Frame;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.Usuario;
import service.UsuarioService;
import view.AdicionarFuncionarioView;
import view.BotoesEditarUsuario;
import view.UsuarioView;

public class UsuarioController {

	private UsuarioView view;
	private UsuarioService usuarioService;

	public UsuarioController(UsuarioView view, UsuarioService usuarioService) {
		this.view = view;
		this.usuarioService = usuarioService;

		
		view.setController(this);

		
		configurarTabelaFuncionarios();

		
		atualizarDadosTabela();
	}

	
	private void configurarTabelaFuncionarios() {
		DefaultTableModel modeloTabela = view.getModeloTabela();
		JTable tabela = view.getTabela();

		
		tabela.getColumnModel().getColumn(6)
				.setCellRenderer(new BotoesEditarUsuario(tabela, usuarioService, this));
		tabela.getColumnModel().getColumn(6)
				.setCellEditor(new BotoesEditarUsuario(tabela, usuarioService, this));
	}

	
	public void atualizarDadosTabela() {
		List<Usuario> usuarios = usuarioService.listarTodos();
		DefaultTableModel modeloTabela = view.getModeloTabela();
		modeloTabela.setRowCount(0);

		for (Usuario u : usuarios) {
			if (u.isAtivo()) {
				Object[] dados = new Object[] { u.getNumeroDoBI(), u.getNome(), u.getEmail(),
						u.getUsuario(), u.getSenha(),u.getNivelAcesso(),
						"Acoes"
				};
				modeloTabela.addRow(dados);
			}
		}
	}

	
	public void filtrarFuncionarios(String textoPesquisa) {
		List<Usuario> usuarios = usuarioService.listarTodos();

		
		String pesquisa = textoPesquisa.toLowerCase();

		
		Object[][] dadosFiltrados = usuarios.stream()
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
				usuarioService, view.getModeloTabela());
		dialog.setController(adicionarFuncionarioController);
		dialog.setVisible(true);

	}


}