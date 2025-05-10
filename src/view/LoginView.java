package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import controller.LoginController;
import view.cores.Cores;
import view.vendedor.PaginaInicialVendedorView;

public class LoginView extends JFrame {
    
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    private LoginController controller;
    
    public LoginView() {
        controller = new LoginController(this);
        configurarFrame();
        criarTelaLogin();
        FlatMacLightLaf.setup();
        addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		
        	}
		});
        setVisible(true);
        
     
    }
    
    private void configurarFrame() {
        setTitle("Mr. Robot - Sistema de Gestão");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Cores.LOGIN_COR_FUNDO);
        setResizable(false);
    }
    
    private void criarTelaLogin() {
        
        JPanel painelPrincipal = new JPanel(new GridLayout(1, 2, 20, 0));
        painelPrincipal.setBackground(Cores.LOGIN_COR_FUNDO);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
       
        JPanel painelEsquerdo = new JPanel(new BorderLayout());
        painelEsquerdo.setBackground(Cores.LOGIN_COR_PRIMARIA);
        painelEsquerdo.setBorder(BorderFactory.createLineBorder(Cores.LOGIN_COR_SECUNDARIA, 3));
        
       
        JPanel painelTextoBoasVindas = new JPanel();
        painelTextoBoasVindas.setLayout(new BoxLayout(painelTextoBoasVindas, BoxLayout.Y_AXIS));
        painelTextoBoasVindas.setBackground(Cores.LOGIN_COR_PRIMARIA);
        painelTextoBoasVindas.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        ImageIcon icone = new ImageIcon("C:\\temp\\projetos-java\\sistema-gestao-livraria\\icone\\book (1).png");
        JLabel iconeLivro = new JLabel(icone);
        iconeLivro.setFont(new Font("Segoe UI", Font.PLAIN, 72));
        iconeLivro.setForeground(Cores.COR_TEXTO);
        iconeLivro.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        JLabel tituloBoasVindas = new JLabel("Bem-vindo à Livraria");
        tituloBoasVindas.setFont(new Font("Serif", Font.BOLD, 28));
        tituloBoasVindas.setForeground(Cores.LOGIN_COR_FUNDO);
        tituloBoasVindas.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       
        JLabel nomeLivraria = new JLabel("Mr. Robot");
        nomeLivraria.setFont(new Font("Serif", Font.BOLD, 36));
        nomeLivraria.setForeground(Cores.LOGIN_COR_FUNDO);
        nomeLivraria.setAlignmentX(Component.CENTER_ALIGNMENT);
        
      
        
       
        painelTextoBoasVindas.add(Box.createVerticalGlue());
        painelTextoBoasVindas.add(iconeLivro);
        painelTextoBoasVindas.add(Box.createVerticalStrut(20));
        painelTextoBoasVindas.add(tituloBoasVindas);
        painelTextoBoasVindas.add(Box.createVerticalStrut(10));
        painelTextoBoasVindas.add(nomeLivraria);
        painelTextoBoasVindas.add(Box.createVerticalStrut(20));
        
        painelTextoBoasVindas.add(Box.createVerticalGlue());
        
        painelEsquerdo.add(painelTextoBoasVindas, BorderLayout.CENTER);
        
       
        JPanel painelDireito = new JPanel();
        painelDireito.setLayout(new BoxLayout(painelDireito, BoxLayout.Y_AXIS));
        painelDireito.setBackground(Cores.LOGIN_COR_FUNDO);
        painelDireito.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        
        JLabel tituloLogin = new JLabel("Acesso ao Sistema");
        tituloLogin.setFont(new Font("Serif", Font.BOLD, 28));
        tituloLogin.setForeground(Cores.LOGIN_COR_PRIMARIA);
        tituloLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       
        JLabel subtituloLogin = new JLabel("Entre com suas credenciais");
        subtituloLogin.setFont(new Font("Serif", Font.PLAIN, 16));
        subtituloLogin.setForeground(Cores.LOGIN_COR_TEXTO);
        subtituloLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       
        JPanel formularioPanel = new JPanel();
        formularioPanel.setLayout(new BoxLayout(formularioPanel, BoxLayout.Y_AXIS));
        formularioPanel.setBackground(Cores.LOGIN_COR_FUNDO);
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 0, 20));
        formularioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       
        JLabel labelUsuario = new JLabel("Usuario:");
        labelUsuario.setFont(new Font("Serif", Font.BOLD, 16));
        labelUsuario.setForeground(Cores.LOGIN_COR_TEXTO);
        labelUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campoUsuario = new JTextField();
        campoUsuario.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoUsuario.setForeground(Cores.LOGIN_COR_TEXTO);
        campoUsuario.setBackground(Cores.LOGIN_COR_CAMPO_TEXTO);
        campoUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Cores.LOGIN_COR_SECUNDARIA, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        campoUsuario.setMaximumSize(new Dimension(300, 40));
        campoUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        
      
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Serif", Font.BOLD, 16));
        labelSenha.setForeground(Cores.LOGIN_COR_TEXTO);
        labelSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campoSenha = new JPasswordField();
        campoSenha.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoSenha.setForeground(Cores.LOGIN_COR_TEXTO);
        campoSenha.setBackground(Cores.LOGIN_COR_CAMPO_TEXTO);
        campoSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Cores.LOGIN_COR_SECUNDARIA, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        campoSenha.setMaximumSize(new Dimension(300, 40));
        campoSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        
      
        JLabel esqueciSenha = new JLabel("Esqueci minha senha");
        esqueciSenha.setFont(new Font("Serif", Font.ITALIC, 14));
        esqueciSenha.setForeground(Cores.LOGIN_COR_SECUNDARIA);
        esqueciSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        esqueciSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        
       
        botaoLogin = new JButton("Entrar");
        botaoLogin.setFont(new Font("Serif", Font.BOLD, 16));
        botaoLogin.setForeground(Color.WHITE);
        botaoLogin.setBackground(Cores.LOGIN_COR_PRIMARIA);
        botaoLogin.setFocusPainted(false);
        botaoLogin.setBorderPainted(false);
        botaoLogin.setMaximumSize(new Dimension(300, 45));
        botaoLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        botaoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botaoLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botaoLogin.setBackground(Cores.LOGIN_COR_SECUNDARIA);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                botaoLogin.setBackground(Cores.LOGIN_COR_PRIMARIA);
            }
        });
        
      
        botaoLogin.addActionListener(e -> {
            controller.realizarLogin(campoUsuario.getText(), new String(campoSenha.getPassword()));
        });
        
        
        campoSenha.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    controller.realizarLogin(campoUsuario.getText(), new String(campoSenha.getPassword()));
                }
            }
        });
        
      
        formularioPanel.add(labelUsuario);
        formularioPanel.add(Box.createVerticalStrut(8));
        formularioPanel.add(campoUsuario);
        formularioPanel.add(Box.createVerticalStrut(20));
        formularioPanel.add(labelSenha);
        formularioPanel.add(Box.createVerticalStrut(8));
        formularioPanel.add(campoSenha);
        formularioPanel.add(Box.createVerticalStrut(12));
        formularioPanel.add(esqueciSenha);
        formularioPanel.add(Box.createVerticalStrut(30));
        formularioPanel.add(botaoLogin);
        
       
        painelDireito.add(Box.createVerticalGlue());
        painelDireito.add(tituloLogin);
        painelDireito.add(Box.createVerticalStrut(5));
        painelDireito.add(subtituloLogin);
        painelDireito.add(formularioPanel);
        painelDireito.add(Box.createVerticalGlue());
        
        
        painelPrincipal.add(painelEsquerdo);
        painelPrincipal.add(painelDireito);
        
       
        add(painelPrincipal, BorderLayout.CENTER);
        
       
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rodape.setBackground(Cores.LOGIN_COR_FUNDO);
        
        JLabel textoRodape = new JLabel("© 2025 Mr. Robot - Sistema de Gestão de Livraria v1.0");
        textoRodape.setFont(new Font("SansSerif", Font.PLAIN, 11));
        textoRodape.setForeground(Cores.LOGIN_COR_TEXTO);
        
        rodape.add(textoRodape);
        add(rodape, BorderLayout.SOUTH);
    }
    
    public void exibirMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro de Login", JOptionPane.ERROR_MESSAGE);
    }
    
    public void limparCampos() {
        campoUsuario.setText("");
        campoSenha.setText("");
        campoUsuario.requestFocus();
    }
    
   
    public void init(String nomeUsuario) {
        dispose();
        SwingUtilities.invokeLater(() -> new PaginaInicialView(nomeUsuario));
    }
    
    public void initVendedor(String nomeUsuario) {
    	dispose();
        SwingUtilities.invokeLater(() -> new PaginaInicialVendedorView(nomeUsuario));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView());
    }
}