package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;

import model.Funcionario;
import model.enums.TipoDocumento;
import service.FuncionarioService;
import view.cores.Cores;

public class EditarFuncionarioView extends JDialog {

	private Cores cores;

	private Funcionario funcionario;
	private FuncionarioService funcionarioService;
	private DefaultTableModel modeloTabela;

	private JTextField campoNome;
	private JTextField campoTelefone;
	private JComboBox<TipoDocumento> comboTipoDocumento;
	private JTextField campoNumeroDoBI;
	private JComboBox<String> campoCargo;

	private JTextField campoUsuario;
	private JPasswordField campoSenha;
	private JComboBox<String> comboNivelAcesso;

	public EditarFuncionarioView(Frame framePai, Funcionario funcionario, FuncionarioService funcionarioService,
			DefaultTableModel modeloTabela) {
		super(framePai, "Editar Funcionário", true);
		this.funcionario = funcionario;
		this.funcionarioService = funcionarioService;
		this.modeloTabela = modeloTabela;
		cores = new Cores();
		inicializarComponentes();
		preencherCampos();
	}

	private void inicializarComponentes() {
		setLayout(new BorderLayout(10, 10));
		setSize(500, 600);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Cores.COR_FUNDO);

		JPanel painelTitulo = new JPanel(new BorderLayout());
		painelTitulo.setBackground(Cores.COR_PRIMARIA);
		painelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		JLabel labelTitulo = new JLabel("Editar Funcionário");
		labelTitulo.setFont(cores.FONTE_TITULO);
		labelTitulo.setForeground(cores.COR_TEXTO_CLARO);
		painelTitulo.add(labelTitulo, BorderLayout.WEST);
		
		JLabel labelBI = new JLabel("BI: " + funcionario.getNumeroDoBI());
        labelBI.setFont(cores.FONTE_PADRAO);
        labelBI.setForeground(Cores.COR_TEXTO_CLARO);
        painelTitulo.add(labelBI, BorderLayout.EAST);

		JPanel painelCampos = new JPanel(new GridLayout(8, 2, 10, 10));
		painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		painelCampos.setBackground(Cores.COR_FUNDO);

		painelCampos.add(criarLabel("Nome:"));
		campoNome = criarCampoTexto();
		campoNome.setEditable(false);
		painelCampos.add(campoNome);

		painelCampos.add(criarLabel("Telefone:"));
		campoTelefone = criarCampoTexto();
		painelCampos.add(campoTelefone);

		painelCampos.add(criarLabel("Usuario:"));
		campoUsuario = criarCampoTexto();
		painelCampos.add(campoUsuario);

		painelCampos.add(criarLabel("Senha:"));
		campoSenha = new JPasswordField();
		campoSenha.setFont(cores.FONTE_PADRAO);
		campoSenha.setBackground(Cores.COR_PAINEL);
		campoSenha.setForeground(Cores.COR_TEXTO);
		campoSenha.setCaretColor(Cores.COR_TEXTO);
		campoSenha.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Cores.COR_BORDA, 1),
				BorderFactory.createEmptyBorder(5, 8, 5, 8)));
		painelCampos.add(campoSenha);

		painelCampos.add(criarLabel("Nível de Acesso:"));
		comboNivelAcesso = new JComboBox<>(new String[] { "ADMIN", "GERENTE", "FUNCIONARIO" });
		comboNivelAcesso.setBackground(Cores.COR_PAINEL);
		comboNivelAcesso.setForeground(Cores.COR_TEXTO);
		comboNivelAcesso.setFont(cores.FONTE_PADRAO);
		painelCampos.add(comboNivelAcesso);

		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelBotoes.setBackground(Cores.COR_FUNDO);
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
		label.setFont(cores.FONTE_PADRAO);
		label.setForeground(Cores.COR_TEXTO);
		return label;
	}

	private JTextField criarCampoTexto() {
		JTextField campo = new JTextField();
		campo.setFont(cores.FONTE_PADRAO);
		campo.setBackground(Cores.COR_PAINEL);
		campo.setForeground(Cores.COR_TEXTO);
		campo.setCaretColor(Cores.COR_TEXTO);
		campo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Cores.COR_BORDA, 1),
				BorderFactory.createEmptyBorder(5, 8, 5, 8)));
		return campo;
	}

	private JButton criarBotao(String texto) {
		JButton botao = new JButton(texto);
		botao.setPreferredSize(new java.awt.Dimension(130, 36));
		botao.setBackground(Cores.COR_BOTAO);
		botao.setForeground(Cores.COR_TEXTO_CLARO);
		botao.setFont(cores.FONTE_BOTAO);
		botao.setFocusPainted(false);
		botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botao.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
		botao.setOpaque(true);

		botao.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				botao.setBackground(Cores.COR_BOTAO_HOVER);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				botao.setBackground(Cores.COR_BOTAO);
			}
		});

		return botao;
	}

	private void preencherCampos() {
		campoNome.setText(funcionario.getNome());
		campoTelefone.setText(funcionario.getTelefone());

		

		campoUsuario.setText(funcionario.getUsuario());
		campoSenha.setText(funcionario.getSenha());
		comboNivelAcesso.setSelectedItem(funcionario.getNivelAcesso());
	}

	private void salvar() {
		try {
			String nome = campoNome.getText().trim();
			String telefone = campoTelefone.getText().trim();

			String numeroDoBI = funcionario.getNumeroDoBI();

			String usuario = campoUsuario.getText().trim();
			String senha = new String(campoSenha.getPassword());
			String nivelAcesso = (String) comboNivelAcesso.getSelectedItem();

			if (nome.isEmpty() || telefone.isEmpty() || usuario.isEmpty() || senha.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro de Validação",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			funcionario.setTelefone(telefone); 
			funcionario.setUsuario(usuario);
			funcionario.setSenha(senha);
			funcionario.setNivelAcesso(nivelAcesso);

			funcionarioService.atualizarFuncionario(funcionario);

			atualizarTabela();

			JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);

			dispose();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void atualizarTabela() {
		for (int i = 0; i < modeloTabela.getRowCount(); i++) {
			if (modeloTabela.getValueAt(i, 0).equals(funcionario.getId())) {
				modeloTabela.setValueAt(funcionario.getNome(), i, 1);
				modeloTabela.setValueAt(funcionario.getTelefone(), i, 2);
				modeloTabela.setValueAt(funcionario.getNumeroDoBI(), i, 3);
				modeloTabela.setValueAt(funcionario.getUsuario(),i, 4);
				modeloTabela.setValueAt(funcionario.getSenha(), i, 5);
				modeloTabela.setValueAt(funcionario.getNivelAcesso(), i, 6);
				break;
			}
		}
		modeloTabela.fireTableDataChanged();
	}
}