package controller;

import java.awt.Frame;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.Funcionario;
import model.enums.TipoDocumento;
import service.FuncionarioService;
import view.AdicionarFuncionarioView;
import view.BotoesEditarFuncionario;
import view.FuncionarioView;

public class FuncionarioController {

	private FuncionarioView view;
	private FuncionarioService funcionarioService;

	public FuncionarioController(FuncionarioView view, FuncionarioService funcionarioService) {
		this.view = view;
		this.funcionarioService = funcionarioService;

		
		view.setController(this);

		
		configurarTabelaFuncionarios();

		
		atualizarDadosTabela();
	}

	
	private void configurarTabelaFuncionarios() {
		DefaultTableModel modeloTabela = view.getModeloTabela();
		JTable tabela = view.getTabela();

		
		tabela.getColumnModel().getColumn(7)
				.setCellRenderer(new BotoesEditarFuncionario(tabela, funcionarioService, this));
		tabela.getColumnModel().getColumn(7)
				.setCellEditor(new BotoesEditarFuncionario(tabela, funcionarioService, this));
	}

	
	public void atualizarDadosTabela() {
		List<Funcionario> funcionarios = funcionarioService.listarTodos();
		DefaultTableModel modeloTabela = view.getModeloTabela();
		modeloTabela.setRowCount(0);

		for (Funcionario f : funcionarios) {
			if (f.isAtivo()) {
				Object[] dados = new Object[] { f.getId(), f.getNome(), f.getTelefone(),
						f.getNumeroDoBI(),f.getUsuario(), f.getSenha(),f.getNivelAcesso(),
						"Acoes"
				};
				modeloTabela.addRow(dados);
			}
		}
	}

	
	public void filtrarFuncionarios(String textoPesquisa) {
		List<Funcionario> funcionarios = funcionarioService.listarTodos();

		
		String pesquisa = textoPesquisa.toLowerCase();

		
		Object[][] dadosFiltrados = funcionarios.stream()
				.filter(f -> f.isAtivo() && (f.getId().toLowerCase().contains(pesquisa) ||f .getNome().toLowerCase().contains(pesquisa)
						
						|| f.getNumeroDoBI().toLowerCase().contains(pesquisa)
						|| (f.getUsuario() != null && f.getUsuario().toLowerCase().contains(pesquisa))))
				.map(f -> new Object[] { f.getId(), f.getNome(), f.getTelefone(),
						f.getNumeroDoBI(),f.getUsuario(),f.getSenha(),f.getNivelAcesso(), "Acoes" })
				.toArray(Object[][]::new);

		view.atualizarTabela(dadosFiltrados);
	}

	public void abrirDialogAdicionarFuncionario() {

		Frame framePai = (Frame) SwingUtilities.getWindowAncestor(view);
		AdicionarFuncionarioView dialog = new AdicionarFuncionarioView(framePai);
		AdicionarFuncionarioController adicionarFuncionarioController = new AdicionarFuncionarioController(dialog,
				funcionarioService, view.getModeloTabela());
		dialog.setController(adicionarFuncionarioController);
		dialog.setVisible(true);

	}


}