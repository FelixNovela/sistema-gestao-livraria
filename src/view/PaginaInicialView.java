package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controller.PaginaInicialController;
import model.Livro;
import model.Venda;
import service.UsuarioService;
import service.LivroService;
import service.VendaService;
import view.cores.Cores;

public class PaginaInicialView extends JFrame {
   
    private Point pontoInicial;
    private JPanel painelDeConteudoPrincipal;
    private PaginaInicialController controller;
    private Cores cores;
    private String nomeUsuario;
    
  
    private JTable tabelaUltimasVendas;
    private JTable tabelaEstoqueBaixo;
    private DefaultTableModel modeloUltimasVendas;
    private DefaultTableModel modeloEstoqueBaixo;
    public PaginaInicialView() {
    	
    }
    public PaginaInicialView(String nomeUsuario) {
        controller = new PaginaInicialController(this);
        this.nomeUsuario = nomeUsuario;
        configurarMinhaFrame();
        criarATelaGeral(nomeUsuario);
        setUndecorated(true);
        setVisible(true);
    }

    private void configurarMinhaFrame() {
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

        String[] botoes = {"Home", "Livros", "Usuarios","Vendas", "Lista Vendas","Relatorios","Meus Dados","Sair"};

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

        JLabel mensagemBoasVindas = new JLabel("Bem-vindo, " + nomeUsuario);
        mensagemBoasVindas.setForeground(Cores.COR_TEXTO_CLARO);
        mensagemBoasVindas.setFont(new Font("Segoe UI", Font.BOLD, 16));
        mensagemBoasVindas.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        JPanel icons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        icons.setOpaque(false);
        JLabel bellIcon = new JLabel("🔔");
        bellIcon.setForeground(Cores.COR_TEXTO_CLARO);
        JLabel userIcon = new JLabel("👤");
        userIcon.setForeground(Cores.COR_TEXTO_CLARO);

        icons.add(bellIcon);
        icons.add(Box.createHorizontalStrut(10));
        icons.add(userIcon);

        header.add(mensagemBoasVindas, BorderLayout.WEST);
        header.add(icons, BorderLayout.EAST); 
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pontoInicial = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point pontoAtual = SwingUtilities.convertPoint(header, e.getPoint(), getParent());
               
                int novoX = e.getXOnScreen() - pontoInicial.x;
                int novoY = e.getYOnScreen() - pontoInicial.y;
                setLocation(novoX, novoY);
            }
        });
        
        return header;
    }

    public JPanel criarPaginaInicial() {
        LivroService livros = new LivroService();
        VendaService vendaService = new VendaService();
        UsuarioService funcionarioService = new UsuarioService();
        String totalLivros = String.valueOf(livros.totalLivros());
        String totalFuncionarios = String.valueOf(funcionarioService.totalFuncionarios());
        
        String totalVendas = String.format("%.2f", vendaService.totalVendas());
        
        int somaLivrosV = 0;
        
        for(int i = 0; i < vendaService.listarVendas().size(); i++) {
        	somaLivrosV += vendaService.listarVendas().get(i).getItens().size();
        }
        
        JPanel pagina = new JPanel(new BorderLayout(0, 20));
        pagina.setBackground(Cores.COR_FUNDO);
        pagina.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel cards = new JPanel(new GridLayout(1, 4, 20, 20));
        cards.setBackground(Cores.COR_FUNDO);
        cards.add(createCard("Total de Livros", totalLivros));
        cards.add(createCard("Total de Usuarios", totalFuncionarios));
        cards.add(createCard("Vendas do Mes", totalVendas));
        cards.add(createCard("Livros Vendidos", String.valueOf(somaLivrosV)));

        JPanel tabelas = new JPanel(new GridLayout(1, 2, 20, 0));
        tabelas.setBackground(Cores.COR_FUNDO);
        

        List<Venda> ultimasVendasOriginal = vendaService.ultimasVendas();
        List<Venda> ultimasVendas = new ArrayList<>(ultimasVendasOriginal);
        Collections.reverse(ultimasVendas);

        List<Livro> livrosComEstoqueBaixo = livros.livrosComEstoqueBaixo();
        
        
       


        tabelas.add(criarTabelaUltimasVendas(ultimasVendas));
        tabelas.add(criarTabelaEstoqueBaixo(livrosComEstoqueBaixo));

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

    
    private JPanel criarTabelaUltimasVendas(List<Venda> vendas) {
        String[] colunas = {"Usuario", "Livro", "Valor", "Data"};
        
        
        modeloUltimasVendas = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        if (vendas != null && !vendas.isEmpty()) {
            for (Venda venda : vendas) {
            	
                modeloUltimasVendas.addRow(new Object[]{
                    venda.getFuncionario().getNome(),
                    venda.getItens().size(),
                    String.format("%.2f", venda.getPagamento().getValorTotalDaVenda()),
                    venda.getData().format(formatter)
                    
                });
            }
        } 
        
        return criarTabelaPainel(
            "Últimas Vendas",
            modeloUltimasVendas,
            false
        );
    }

    
    private JPanel criarTabelaEstoqueBaixo(List<Livro> livros) {
        String[] colunas = {"Livro", "Unidades Disponíveis"};
        
       
        modeloEstoqueBaixo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        
        if (livros != null && !livros.isEmpty()) {
            for (Livro livro : livros) {
                modeloEstoqueBaixo.addRow(new Object[]{
                    livro.getTitulo(),
                    livro.getQuantidadeEmEstoque()
                });
            }
        } 
        
        return criarTabelaPainel(
            "Estoque Baixo",
            modeloEstoqueBaixo,
            true
        );
    }
     
   
    public void atualizarDadosTabela() {
        LivroService livroService = new LivroService();
        VendaService vendaService = new VendaService();
        
       
        modeloUltimasVendas.setRowCount(0);
        modeloEstoqueBaixo.setRowCount(0);
        
        
        List<Venda> ultimasVendas = vendaService.ultimasVendas();
        List<Livro> livrosComEstoqueBaixo = livroService.livrosComEstoqueBaixo();
        
        if (ultimasVendas != null && !ultimasVendas.isEmpty()) {
            for (Venda venda : ultimasVendas) {
                modeloUltimasVendas.addRow(new Object[]{
                    venda.getFuncionario().getNome(),
                    venda.getItens().size(),
                    String.format("%.2f", venda.getPagamento().getValorTotalDaVenda()),
                    venda.getData()
                });
            }
        }
        
        
        if (livrosComEstoqueBaixo != null && !livrosComEstoqueBaixo.isEmpty()) {
            for (Livro livro : livrosComEstoqueBaixo) {
                modeloEstoqueBaixo.addRow(new Object[]{
                    livro.getTitulo(),
                   livro.getQuantidadeEmEstoque()
               });
           }
       }
   }

   
    private JPanel criarTabelaPainel(String titulo, DefaultTableModel modelo, boolean estoqueBaixo) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Cores.COR_PAINEL);
        painel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Cores.COR_DESTAQUE),
            titulo, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16), Cores.COR_PRIMARIA
        ));

        JTable tabela = new JTable(modelo) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);

                if (!isRowSelected(row)) {
                   
                    c.setBackground((row % 2 == 0) ? Cores.COR_FUNDO : Cores.COR_DESTAQUE);

                    // Verifica se estamos na coluna de estoque baixo e se é a coluna correta
                    if (estoqueBaixo && col == 1) {
                        Object valor = getValueAt(row, col);
                        if (valor != null) {
                            try {
                                int unidades = Integer.parseInt(valor.toString());
                                if (unidades <= 4) {
                                    c.setBackground(Cores.COR_ALERTA);
                                }
                            } catch (NumberFormatException ignored) {
                                
                            }
                        }
                    }
                } else {
                 
                	c.setBackground(Cores.COR_PAINEL);
                }

                c.setForeground(Cores.COR_TEXTO);

                return c;
            }

        };

        tabela.setRowHeight(30);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setGridColor(Cores.COR_DESTAQUE);
        tabela.setFillsViewportHeight(true);
        
       
        if (estoqueBaixo) {
            tabelaEstoqueBaixo = tabela;
        } else {
            tabelaUltimasVendas = tabela;
        }

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(Cores.COR_PAINEL);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        painel.add(scroll, BorderLayout.CENTER);
        return painel;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void trocarConteudo(JPanel novoPainel) {
        painelDeConteudoPrincipal.removeAll();
        painelDeConteudoPrincipal.add(novoPainel);
        painelDeConteudoPrincipal.revalidate();
        painelDeConteudoPrincipal.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaginaInicialView("Mr. Robot"));
    }
}