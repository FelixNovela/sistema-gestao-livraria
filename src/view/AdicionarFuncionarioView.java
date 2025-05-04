package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.AdicionarFuncionarioController;
import model.enums.TipoDocumento;
import view.cores.Cores;

public class AdicionarFuncionarioView extends JDialog {

   
    private JTextField campoNome;
    private JTextField campoTelefone;
    
    private JTextField numeroDoBI;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JComboBox<String> comboNivelAcesso;
    private Cores cores;
    private AdicionarFuncionarioController controller;

    public AdicionarFuncionarioView(Frame parent) {
        super(parent, "Adicionar Funcionário", true);
        cores = new Cores();
        inicializarComponentes();
    }
    
    public void setController(AdicionarFuncionarioController controller) {
        this.controller = controller;
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setSize(500, 550);
        setLocationRelativeTo(null);
        getContentPane().setBackground(cores.COR_FUNDO);

        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(cores.COR_PRIMARIA);
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel labelTitulo = new JLabel("Novo Funcionário");
        labelTitulo.setFont(cores.FONTE_TITULO);
        labelTitulo.setForeground(cores.COR_TEXTO_CLARO);
        painelTitulo.add(labelTitulo, BorderLayout.CENTER);

        JPanel painelCampos = new JPanel(new GridLayout(6, 2, 10, 10));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelCampos.setBackground(cores.COR_FUNDO);

        painelCampos.add(criarLabel("Nome:"));
        campoNome = criarCampoTexto();
        painelCampos.add(campoNome);

        painelCampos.add(criarLabel("Telefone:"));
        campoTelefone = criarCampoTexto();
        painelCampos.add(campoTelefone);

        painelCampos.add(criarLabel("Número Do BI:"));
        numeroDoBI = criarCampoTexto();
        painelCampos.add(numeroDoBI);

        painelCampos.add(criarLabel("Usuario:"));
        campoUsuario = criarCampoTexto();
        painelCampos.add(campoUsuario);

        painelCampos.add(criarLabel("Senha:"));
        campoSenha = criarCampoSenha();
        painelCampos.add(campoSenha);

        painelCampos.add(criarLabel("Nível de Acesso:"));
        comboNivelAcesso = criarComboBoxNivelAcesso();
        painelCampos.add(comboNivelAcesso);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBackground(cores.COR_FUNDO);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
    
        
        JButton botaoSalvar = criarBotao("Salvar");
        JButton botaoCancelar = criarBotao("Cancelar");

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        add(painelTitulo, BorderLayout.NORTH);
        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        botaoSalvar.addActionListener(e -> {
            if (controller != null) {
                controller.salvarFuncionario();
            }
        });
        
        botaoCancelar.addActionListener(e -> dispose());
    }
    
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(cores.FONTE_PADRAO);
        label.setForeground(cores.COR_TEXTO);
        return label;
    }
    
    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField(); 
        campo.setFont(cores.FONTE_PADRAO);
        campo.setBackground(cores.COR_PAINEL);
        campo.setForeground(cores.COR_TEXTO);
        campo.setCaretColor(cores.COR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cores.COR_BORDA, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return campo;
    }
    
    private JPasswordField criarCampoSenha() {
        JPasswordField campo = new JPasswordField(); 
        campo.setFont(cores.FONTE_PADRAO);
        campo.setBackground(cores.COR_PAINEL);
        campo.setForeground(cores.COR_TEXTO);
        campo.setCaretColor(cores.COR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cores.COR_BORDA, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return campo;
    }
    
    
    private JComboBox<String> criarComboBoxNivelAcesso() {
        String[] niveisAcesso = {"Administrador", "Gerente", "Vendedor"};
        JComboBox<String> comboBox = new JComboBox<>(niveisAcesso);
        comboBox.setFont(cores.FONTE_PADRAO);
        comboBox.setBackground(cores.COR_PAINEL);
        comboBox.setForeground(cores.COR_TEXTO);
       
        return comboBox;
    }
    
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new java.awt.Dimension(130, 36));
        botao.setBackground(cores.COR_BOTAO);
        botao.setForeground(cores.COR_TEXTO_CLARO);
        botao.setFont(cores.FONTE_BOTAO);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        botao.setOpaque(true);

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(cores.COR_BOTAO_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(cores.COR_BOTAO);
            }
        });
        
        return botao;
    }
    
    
    public String getNome() {
        return campoNome.getText().trim();
    }
    
    public String getTelefone() {
        return campoTelefone.getText().trim();
    }
    
    public String getNumeroDoBI() {
        return numeroDoBI.getText().trim();
    }
    
    public String getUsuario() {
        return campoUsuario.getText().trim();
    }
    
    public String getSenha() {
        return new String(campoSenha.getPassword());
    }
    
    public String getNivelAcesso() {
        return (String) comboNivelAcesso.getSelectedItem();
    }
    
    public void fechar() {
        dispose();
    }
}