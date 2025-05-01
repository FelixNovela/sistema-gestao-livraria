package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controller.PaginaInicialController;
import view.cores.Cores;

public class PaginaInicial extends JFrame {
   
	
 
    private JPanel painelDeConteudoPrincipal;
    private PaginaInicialController controller;
    private Cores cores;
    public PaginaInicial(String nomeUsuario) {
        controller = new PaginaInicialController(this);
        configurarJanela();
        criarATelaGeral(nomeUsuario);
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Sistema de Gestao de Livraria");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Cores.COR_FUNDO);
    }

    private void criarATelaGeral(String nomeUsuario) {
        add(criarSidebar(), BorderLayout.WEST); 
        add(criarHeader(nomeUsuario), BorderLayout.NORTH);
        painelDeConteudoPrincipal = new JPanel(new BorderLayout());
        painelDeConteudoPrincipal.setBackground(Cores.COR_FUNDO);
        painelDeConteudoPrincipal.add(criarPaginaInicial()); 
        add(painelDeConteudoPrincipal, BorderLayout.CENTER);
    }

    private JPanel criarSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Cores.COR_PRIMARIA);
        sidebar.setPreferredSize(new Dimension(200, getHeight()));

        String[] botoes = {"Home", "Livros", "Vendas", "Emprestimos","Clientes","Funcionarios"};

        for (String texto : botoes) {
            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(criarBotaoSidebar(texto));
        }
        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private JButton criarBotaoSidebar(String texto) {
        JButton botao = new JButton(texto);
        botao.setMaximumSize(new Dimension(180, 40));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setFocusPainted(false);
        botao.setForeground(Cores.COR_TEXTO_CLARO);
        botao.setBackground(Cores.COR_SECUNDARIA);
        botao.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addActionListener(e -> controller.tratarCliqueBotaoSidebar(texto));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(Cores.COR_PAINEL);
                botao.setForeground(Cores.COR_TEXTO);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(Cores.COR_SECUNDARIA);
                botao.setForeground(Cores.COR_TEXTO_CLARO);
            }
        });

        return botao;
    }

    private JPanel criarHeader(String nomeUsuario) {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(getWidth(), 50));
        header.setBackground(Cores.COR_PRIMARIA);

        JLabel welcome = new JLabel("Bem-vindo, " + nomeUsuario);
        welcome.setForeground(Cores.COR_TEXTO_CLARO);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcome.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        JPanel icons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        icons.setOpaque(false);
        JLabel bellIcon = new JLabel("🔔");
        bellIcon.setForeground(Cores.COR_TEXTO_CLARO);
        JLabel userIcon = new JLabel("👤");
        userIcon.setForeground(Cores.COR_TEXTO_CLARO);

        icons.add(bellIcon);
        icons.add(Box.createHorizontalStrut(10));
        icons.add(userIcon);

        header.add(welcome, BorderLayout.WEST);
        header.add(icons, BorderLayout.EAST);
        
        return header;
    }

    public JPanel criarPaginaInicial() {
        JPanel pagina = new JPanel(new BorderLayout(0, 20));
        pagina.setBackground(Cores.COR_FUNDO);
        pagina.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel cards = new JPanel(new GridLayout(1, 4, 20, 20));
        cards.setBackground(Cores.COR_FUNDO);
        cards.add(createCard("Total de Livros", "1.250"));
        cards.add(createCard("Total de Funcionarios", "350"));
        cards.add(createCard("Vendas do Mes", "45.000"));
        cards.add(createCard("Emprestimos", "20"));

        JPanel tabelas = new JPanel(new GridLayout(1, 2, 20, 0));
        tabelas.setBackground(Cores.COR_FUNDO);
        tabelas.add(criarTabelaUltimasVendas());
        tabelas.add(criarTabelaEstoqueBaixo());

        pagina.add(cards, BorderLayout.NORTH);
        pagina.add(tabelas, BorderLayout.CENTER);

        return pagina;
    }

    private JPanel createCard(String titulo, String valor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Cores.COR_PAINEL);
        card.setBorder(BorderFactory.createLineBorder(Cores.COR_DESTAQUE));
        card.setPreferredSize(new Dimension(200, 100));

        

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);
        JLabel lblTitulo = new JLabel(titulo);
        JLabel lblValor = new JLabel(valor);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Cores.COR_TEXTO);
        lblValor.setForeground(Cores.COR_TEXTO);
        textos.add(lblTitulo);
        textos.add(lblValor);

     
        card.add(textos, BorderLayout.CENTER);
        return card;
    }

    private JPanel criarTabelaUltimasVendas() {
        return criarTabelaPainel(
            "Últimas Vendas",
            new String[]{"Funcionario", "Livro", "Valor", "Data"},
            new Object[][]{
                {"Naruto", "Naruto", "45", "25/04/2025"},
                {"Kira", "Kira", "120", "24/04/2025"},
                {"Asta", "Black Cover", "70", "23/04/2025"},
                {"Ichigo", "Bleach", "90", "22/04/2025"}
            },
            false
        );
    }

    private JPanel criarTabelaEstoqueBaixo() {
        return criarTabelaPainel(
            "Estoque Baixo",
            new String[]{"Livro", "Unidades Disponíveis"},
            new Object[][]{
                {"Hunter X Hunter", "2"},
                {"Flash", "3"},
                {"Black Cover", "1"}
            },
            true
        );
    }

    private JPanel criarTabelaPainel(String titulo, String[] colunas, Object[][] dados, boolean estoqueBaixo) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Cores.COR_PAINEL);
        painel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Cores.COR_DESTAQUE),
            titulo, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16), Cores.COR_PRIMARIA
        ));

        JTable tabela = new JTable(new DefaultTableModel(dados, colunas)) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground((row % 2 == 0) ? Cores.COR_FUNDO : Cores.COR_DESTAQUE);
                    if (estoqueBaixo && col == 1) {
                        try {
                            int unidades = Integer.parseInt(getValueAt(row, col).toString());
                            if (unidades <= 3) c.setBackground(Cores.COR_ALERTA);
                        } catch (NumberFormatException ignored) {}
                    }
                } else {
                    c.setBackground(Cores.COR_DESTAQUE);
                }
                c.setForeground(Cores.COR_TEXTO);
                return c;
            }
        };

        tabela.setRowHeight(30);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setGridColor(Cores.COR_DESTAQUE);
        tabela.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Cores.COR_PAINEL);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        painel.add(scroll, BorderLayout.CENTER);
        return painel;
    }

    public void trocarConteudo(JPanel novoPainel) {
        painelDeConteudoPrincipal.removeAll();
        painelDeConteudoPrincipal.add(novoPainel);
        painelDeConteudoPrincipal.revalidate();
        painelDeConteudoPrincipal.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaginaInicial("Mr. Robot"));
    }
}
