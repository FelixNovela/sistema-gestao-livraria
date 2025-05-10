package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PerfilView extends JPanel {

    // Paleta de cores moderna
    private static final Color COR_PRIMARIA = new Color(0x5D4037);       // Marrom escuro
    private static final Color COR_SECUNDARIA = new Color(0x8D6E63);     // Marrom médio
    private static final Color COR_DESTAQUE = new Color(0xD7CCC8);       // Bege claro
    private static final Color COR_FUNDO = new Color(0xF5F5F5);          // Cinza muito claro
    private static final Color COR_TEXTO = new Color(0x212121);          // Quase preto
    private static final Color COR_TEXTO_SECUNDARIO = new Color(0x757575); // Cinza escuro

    // Fontes
    private static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FONTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FONTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONTE_NEGRITO = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONTE_PEQUENA = new Font("Segoe UI", Font.PLAIN, 12);

    // Componentes
    private JButton btnEditarPerfil, btnAlterarSenha;
    private JPanel cardFotoPerfil;
    private JLabel lblNome, lblUsername, lblCargo;
    private JLabel tempoOnline, statusAtividade;
    private Timer temporizador;

    public PerfilView() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COR_FUNDO);
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Barra superior
        add(criarBarraSuperior(), BorderLayout.NORTH);
        
        // Painel central dividido em duas colunas
        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 20, 0));
        painelCentral.setBackground(COR_FUNDO);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        painelCentral.add(criarPainelEsquerdo());
        painelCentral.add(criarPainelDireito());
        
        add(painelCentral, BorderLayout.CENTER);
        
        // Barra inferior com botões
        add(criarBarraInferior(), BorderLayout.SOUTH);
        
        // Iniciar temporizador para atualizar o tempo online
        iniciarTemporizador();
    }

    private JPanel criarBarraSuperior() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(COR_FUNDO);
        
        JLabel titulo = new JLabel("Meu Perfil", JLabel.CENTER);
        titulo.setFont(FONTE_TITULO);
        titulo.setForeground(COR_PRIMARIA);
        
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelTitulo.setBackground(COR_FUNDO);
        painelTitulo.add(titulo);
        
        barra.add(painelTitulo, BorderLayout.CENTER);
        
        
       
        
        
        
        return barra;
    }

    private JPanel criarPainelEsquerdo() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(COR_FUNDO);
        
        // Card com foto de perfil
        cardFotoPerfil = criarCardFotoPerfil();
        painel.add(cardFotoPerfil);
        
        painel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Status de atividade
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COR_DESTAQUE, 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblStatus = new JLabel("Status de Atividade");
        lblStatus.setFont(FONTE_SUBTITULO);
        lblStatus.setForeground(COR_SECUNDARIA);
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        statusAtividade = new JLabel("● Online");
        statusAtividade.setFont(FONTE_NORMAL);
        statusAtividade.setForeground(new Color(0x4CAF50));  // Verde
        statusAtividade.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        tempoOnline = new JLabel("Ativo há: 1h 27m");
        tempoOnline.setFont(FONTE_PEQUENA);
        tempoOnline.setForeground(COR_TEXTO_SECUNDARIO);
        tempoOnline.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        statusPanel.add(lblStatus);
        statusPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        statusPanel.add(statusAtividade);
        statusPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        statusPanel.add(tempoOnline);
        
        statusPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusPanel.getPreferredSize().height));
        painel.add(statusPanel);
        
        return painel;
    }

    private JPanel criarCardFotoPerfil() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COR_DESTAQUE, 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Placeholder para foto de perfil (círculo)
        JPanel fotoPerfil = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Desenha círculo preenchido
                g2d.setColor(COR_DESTAQUE);
                g2d.fillOval(0, 0, getWidth(), getHeight());
                
                // Desenha borda
                g2d.setColor(COR_SECUNDARIA);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(1, 1, getWidth()-2, getHeight()-2);
                
                // Desenha ícone de usuário
                g2d.setColor(COR_SECUNDARIA);
                int headSize = getWidth()/3;
                int headY = getHeight()/4;
                g2d.fillOval(getWidth()/2 - headSize/2, headY, headSize, headSize);
                
                // Corpo
                g2d.fillOval(getWidth()/2 - getWidth()/4, getHeight()/2, getWidth()/2, getHeight()/2);
                
                g2d.dispose();
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(120, 120);
            }
        };
        
        fotoPerfil.setMaximumSize(new Dimension(120, 120));
        fotoPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Nome e cargo
        lblNome = new JLabel("João M. Alberto");
        lblNome.setFont(FONTE_NEGRITO);
        lblNome.setForeground(COR_TEXTO);
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblUsername = new JLabel("@joao.alberto");
        lblUsername.setFont(FONTE_PEQUENA);
        lblUsername.setForeground(COR_TEXTO_SECUNDARIO);
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblCargo = new JLabel("Vendedor");
        lblCargo.setFont(FONTE_NORMAL);
        lblCargo.setForeground(COR_SECUNDARIA);
        lblCargo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adicionar um badge ao cargo
        JPanel badgeCargo = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        badgeCargo.setBackground(Color.WHITE);
        JLabel lblBadge = new JLabel("Vendedor");
        lblBadge.setFont(FONTE_PEQUENA);
        lblBadge.setForeground(Color.WHITE);
        lblBadge.setBackground(COR_SECUNDARIA);
        lblBadge.setOpaque(true);
        lblBadge.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        badgeCargo.add(lblBadge);
        badgeCargo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(fotoPerfil);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(lblNome);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(lblUsername);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(badgeCargo);
        
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, card.getPreferredSize().height));
        
        return card;
    }

    private JPanel criarPainelDireito() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COR_DESTAQUE, 1, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        // Título do painel
        JLabel lblInfoTitulo = new JLabel("Informações do Usuário");
        lblInfoTitulo.setFont(FONTE_SUBTITULO);
        lblInfoTitulo.setForeground(COR_PRIMARIA);
        
        // Separador
        JSeparator separador = new JSeparator();
        separador.setForeground(COR_DESTAQUE);
        separador.setBackground(COR_DESTAQUE);
        
        painel.add(lblInfoTitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(separador);
        painel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Informações do usuário
        painel.add(criarCampoInfo("📬 Email", "joao.alberto@livraria.co.mz"));
        painel.add(criarCampoInfo("🆔 Username", "joao.alberto"));
        painel.add(criarCampoInfo("🔑 Nível de Acesso", "VENDAS"));
        
        // Segunda seção - Estatísticas
        painel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel lblEstTitulo = new JLabel("Estatísticas de Acesso");
        lblEstTitulo.setFont(FONTE_SUBTITULO);
        lblEstTitulo.setForeground(COR_PRIMARIA);
        
        JSeparator separador2 = new JSeparator();
        separador2.setForeground(COR_DESTAQUE);
        separador2.setBackground(COR_DESTAQUE);
        
        painel.add(lblEstTitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(separador2);
        painel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        painel.add(criarCampoInfo("📅 Criado em", "12/03/2025 às 09:34"));
        painel.add(criarCampoInfo("🕒 Último acesso", "07/05/2025 às 13:12"));
        painel.add(criarCampoInfo("📊 Total de logins", "47"));
        painel.add(criarCampoInfo("📈 Vendas realizadas", "128"));
        
        return painel;
    }

    private JPanel criarCampoInfo(String label, String valor) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(FONTE_NEGRITO);
        lblLabel.setForeground(COR_SECUNDARIA);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(FONTE_NORMAL);
        lblValor.setForeground(COR_TEXTO);
        
        panel.add(lblLabel, BorderLayout.WEST);
        panel.add(lblValor, BorderLayout.EAST);
        
        // Adicionar um separador sutil
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.add(panel, BorderLayout.CENTER);
        
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0xF0F0F0));
        wrapperPanel.add(sep, BorderLayout.SOUTH);
        
        return wrapperPanel;
    }

    private JPanel criarBarraInferior() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        barra.setBackground(COR_FUNDO);
        barra.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        btnEditarPerfil = criarBotao("Editar Perfil", COR_SECUNDARIA);
        btnAlterarSenha = criarBotao("Alterar Senha", COR_PRIMARIA);
        
        barra.add(btnEditarPerfil);
        barra.add(btnAlterarSenha);
        
        return barra;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(FONTE_NEGRITO);
        botao.setForeground(Color.WHITE);
        botao.setBackground(cor);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(cor.darker(), 1, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // Efeito hover
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor);
            }
        });
        
        return botao;
    }
    
    private void iniciarTemporizador() {
        // Temporizador que atualiza o tempo online a cada minuto
        temporizador = new Timer(60000, e -> {
            // Simular contagem de tempo online
            String tempoAtual = tempoOnline.getText();
            String[] partes = tempoAtual.split(":");
            String horasMinutos = partes[1].trim();
            String[] hm = horasMinutos.split("h ");
            
            int horas = Integer.parseInt(hm[0]);
            int minutos = Integer.parseInt(hm[1].replace("m", ""));
            
            minutos++;
            if (minutos >= 60) {
                horas++;
                minutos = 0;
            }
            
            tempoOnline.setText("Ativo há: " + horas + "h " + minutos + "m");
        });
        temporizador.start();
    }

    // Método para atualizar as informações do usuário
    public void atualizarDadosUsuario(String nome, String username, String email, String cargo, String nivelAcesso) {
        lblNome.setText(nome);
        lblUsername.setText("@" + username);
        
        // Atualizar badge do cargo
        Component[] components = cardFotoPerfil.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                for (Component innerComp : panel.getComponents()) {
                    if (innerComp instanceof JLabel && innerComp.getBackground().equals(COR_SECUNDARIA)) {
                        ((JLabel) innerComp).setText(cargo);
                        break;
                    }
                }
            }
        }
    }

    // Getters para usar nas ações
    public JButton getBtnEditarPerfil() {
        return btnEditarPerfil;
    }

    public JButton getBtnAlterarSenha() {
        return btnAlterarSenha;
    }
    
    // Métodos para gerenciar o ciclo de vida
    public void fechar() {
        if (temporizador != null && temporizador.isRunning()) {
            temporizador.stop();
        }
    }
}