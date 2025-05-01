package view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import model.Usuario;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;

public class Login {
    private JFrame frame;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel forgotPasswordLabel;
    
    // Cores personalizadas
    private final Color PRIMARY_COLOR = new Color(130, 82, 238); // Roxo do botão de login
    private final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private final Color ERROR_COLOR = new Color(220, 53, 69); // Vermelho para erros
    private final Color TEXT_COLOR = new Color(70, 70, 70);
    
    public Login() {
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame("Login");
        frame.setBounds(100, 100, 800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        // Desativa o redimensionamento da janela
        frame.setResizable(false);
        // Centraliza a janela na tela
        frame.setLocationRelativeTo(null);
        
        // Painel principal dividido em duas partes
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        
        // Painel esquerdo - Banner de boas-vindas
        JPanel welcomePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Criar um gradiente para o fundo
                int w = getWidth();
                int h = getHeight();
                
                // Gradiente com cores similares ao da imagem
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(215, 204, 200), // Rosa claro
                    w, h, new Color(93, 64, 55)   // Roxo
                );
                
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, w, h);
                
                // Adicionar formas curvas similares às da imagem
                g2d.setColor(new Color(255, 255, 255, 70)); // Branco semi-transparente
                g2d.fillOval(-100, h/2 - 150, 400, 400);
                g2d.draw(getBounds());
                
                // Texto de boas-vindas
                g2d.setColor(Color.getHSBColor(75, 46, 46));
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                g2d.drawString("Welcome", w/4 - 50, h/2 - 30);
               
            }
        };
        
        // Painel direito - Formulário
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);
        
        // Título do formulário
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setBounds(100, 60, 200, 30);
        
        // Subtítulo
        JLabel subtitleLabel = new JLabel("Please login to your account!");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(150, 150, 150));
        subtitleLabel.setBounds(100, 90, 300, 30);
        
        // Campos do formulário
        JLabel userNameLabel = new JLabel("Email");
        userNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userNameLabel.setForeground(TEXT_COLOR);
        userNameLabel.setBounds(100, 140, 200, 20);
        
        userNameField = new JTextField();
        userNameField.setBounds(100, 160, 220, 40);
        userNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JLabel emailErrorLabel = new JLabel("");
        emailErrorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        emailErrorLabel.setForeground(ERROR_COLOR);
        emailErrorLabel.setBounds(100, 200, 220, 20);
        
        // Validação de email quando o campo perde o foco
        userNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validateEmail();
            }
        });
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setBounds(100, 220, 200, 20);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(100, 240, 220, 40);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JLabel passwordErrorLabel = new JLabel("");
        passwordErrorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordErrorLabel.setForeground(ERROR_COLOR);
        passwordErrorLabel.setBounds(100, 280, 220, 20);
        
        // Validação de senha quando o texto muda
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validatePassword();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                validatePassword();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                validatePassword();
            }
            
            private void validatePassword() {
                String password = new String(passwordField.getPassword());
                if (password.length() != 8) {
                    passwordField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ERROR_COLOR), 
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                    ));
                    passwordErrorLabel.setText("A senha deve ter exatamente 8 caracteres");
                } else {
                    passwordField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)), 
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                    ));
                    passwordErrorLabel.setText("");
                }
            }
        });
        
        // Link "Esqueci a senha"
        forgotPasswordLabel = new JLabel("Forgot Password?");
        forgotPasswordLabel.setBounds(220, 300, 120, 30);
        forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotPasswordLabel.setForeground(PRIMARY_COLOR);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Botão de login
        loginButton = new JButton("Login");
        loginButton.setBounds(100, 340, 220, 40);
        loginButton.setBackground(PRIMARY_COLOR);
        loginButton.setForeground(Color.white);
        loginButton.setFont(new Font("Arial",Font.BOLD, 18));
        loginButton.setBorder(new RoundedBorder(10));
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setOpaque(true); 
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean emailValid = validateEmail();
                boolean passwordValid = validatePassword();
                
                if (!emailValid) {
                    JOptionPane.showMessageDialog(frame, "Email inválido! Por favor, insira um email correto.", "Erro de Email", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!passwordValid) {
                    JOptionPane.showMessageDialog(frame, "Senha deve ter exatamente 8 caracteres!", "Erro de Senha", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (emailValid && passwordValid) {
                	char[] password = passwordField.getPassword();
                	// Convertendo para String se necessário (ex: para comparar)
                	String passStr = new String(password);
                	
                	verificarLogin(userNameField.getText(),passStr);
                	
                	Arrays.fill(password, '0');
                }
            }
        });
        
        // Adicionar componentes ao painel do formulário
        formPanel.add(titleLabel);
        formPanel.add(subtitleLabel);
        formPanel.add(userNameLabel);
        formPanel.add(userNameField);
        formPanel.add(emailErrorLabel);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(passwordErrorLabel);
        formPanel.add(forgotPasswordLabel);
        formPanel.add(loginButton);
        
        // Adicionar painéis ao painel principal
        mainPanel.add(welcomePanel);
        mainPanel.add(formPanel);
        
        // Centralizar o painel principal
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        // Adicionar painel principal ao frame
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
    
    // Método para validar o formato do email
    private boolean validateEmail() {
        String email = userNameField.getText().trim();
        // Expressão regular mais robusta para validação de email
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        
        if (!matcher.matches()) {
            userNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ERROR_COLOR), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            
            // Atualiza a mensagem de erro abaixo do campo
            Component[] components = ((JPanel)userNameField.getParent()).getComponents();
            for (Component comp : components) {
                if (comp instanceof JLabel && ((JLabel)comp).getText().isEmpty() && 
                    comp.getBounds().y > userNameField.getBounds().y && 
                    comp.getBounds().y < userNameField.getBounds().y + 60) {
                    ((JLabel)comp).setText("Por favor, insira um email válido");
                }
            }
            return false;
        } else {
            userNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            
            // Limpa a mensagem de erro
            Component[] components = ((JPanel)userNameField.getParent()).getComponents();
            for (Component comp : components) {
                if (comp instanceof JLabel && 
                    comp.getBounds().y > userNameField.getBounds().y && 
                    comp.getBounds().y < userNameField.getBounds().y + 60) {
                    ((JLabel)comp).setText("");
                }
            }
            return true;
        }
    }
    
    // Método para validar a senha
    private boolean validatePassword() {
        String password = new String(passwordField.getPassword());
        if (password.length() != 8) {
            passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ERROR_COLOR), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            
            // Atualiza a mensagem de erro abaixo do campo
            Component[] components = ((JPanel)passwordField.getParent()).getComponents();
            for (Component comp : components) {
                if (comp instanceof JLabel && 
                    comp.getBounds().y > passwordField.getBounds().y && 
                    comp.getBounds().y < passwordField.getBounds().y + 60) {
                    ((JLabel)comp).setText("A senha deve ter exatamente 8 caracteres");
                }
            }
            return false;
        } else {
            passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            
            // Limpa a mensagem de erro
            Component[] components = ((JPanel)passwordField.getParent()).getComponents();
            for (Component comp : components) {
                if (comp instanceof JLabel && 
                    comp.getBounds().y > passwordField.getBounds().y && 
                    comp.getBounds().y < passwordField.getBounds().y + 60) {
                    ((JLabel)comp).setText("");
                }
            }
            return true;
        }
    }
    
    // Classe para criar bordas arredondadas
    private class RoundedBorder implements Border {
        private int radius;
        
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }
        
        @Override
        public boolean isBorderOpaque() {
            return true;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
    
    /**
     * Verifica as credenciais de login e direciona para a interface adequada
     * baseado no nível de acesso do usuário
     * @param email Email do usuário
     * @param senha Senha do usuário
     */
    public void verificarLogin(String email, String senha) {
        boolean emailValid = false;
        boolean passwordValid = false;
        
        // Carregar os dados do usuário do arquivo de objeto
        try {
            FileInputStream fileIn = new FileInputStream("usuarios.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
            // Supondo que o arquivo contém uma lista de usuários
            ArrayList<Usuario> usuarios = (ArrayList<Usuario>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            
            // Verificar se o email e senha correspondem a algum usuário
            Usuario usuarioAutenticado = null;
            for (Usuario usuario : usuarios) {
                if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                    emailValid = true;
                    passwordValid = true;
                    usuarioAutenticado = usuario;
                    break;
                }
            }
            
            // Verificar resultado da validação
            if (emailValid && passwordValid) {
                int nivelAcesso = usuarioAutenticado.getNivelAcesso();
                String mensagem = "Login efetuado com sucesso como " + (nivelAcesso == 2 ? "Administrador" : "Usuário");
                
                JOptionPane.showMessageDialog(frame, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Fecha a janela de login
                
                // Abre a tela correspondente ao nível de acesso
                if (nivelAcesso == 2) {
                    // Tela de administrador
                } else {
                    // Tela de usuário comum
		                } 
            } else {
		             JOptionPane.showMessageDialog(frame,"Dados de acesso incorretos!", "Erro", JOptionPane.ERROR_MESSAGE); 
		           }
		  } catch (IOException | ClassNotFoundException e) {
				  JOptionPane.showMessageDialog(frame,"Erro ao acessar o arquivo de usuários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				            e.printStackTrace();
				    }
    }
    
    // Método para mostrar a janela
    public void show() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Centraliza a janela na tela
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public static void main(String[] args) {
        try {
            // Usar o look and feel do sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Login loginSystem = new Login();
        loginSystem.show();

    }
}