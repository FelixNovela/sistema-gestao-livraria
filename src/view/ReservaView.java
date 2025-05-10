package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controller.ReservaController;
import model.Reserva;
import view.cores.Cores;

public class ReservaView extends JPanel {
    
    private JTable tabelaReservas;
    private DefaultTableModel modeloTabelaReservas;
    private JComboBox<String> cmbFiltroStatus;
    private JButton btnAtualizar, btnMarcarPronta, btnConcluir, btnCancelar, btnVoltar;
    private Cores cores;
    private ReservaController controller;
    private String nomeUsuario;
    
    public ReservaView(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        cores = new Cores();
        controller = new ReservaController(this);
        
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(cores.COR_FUNDO);

        add(criarPainelSuperior(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);
        add(criarPainelInferior(), BorderLayout.SOUTH);
        
        controller.carregarReservas("PENDENTE");
    }
    
    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout(10, 0));
        painel.setBackground(cores.COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel titulo = new JLabel("Gestão de Reservas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(cores.COR_PRIMARIA);

        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelFiltro.setBackground(cores.COR_FUNDO);
        
        JLabel lblFiltro = new JLabel("Filtrar por status:");
        lblFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFiltro.setForeground(cores.COR_TEXTO);
        
        String[] statusOptions = {"PENDENTE", "PRONTA", "CONCLUIDA", "CANCELADA", "TODAS"};
        cmbFiltroStatus = new JComboBox<>(statusOptions);
        cmbFiltroStatus.setFont(cores.FONTE_PADRAO);
        cmbFiltroStatus.setBackground(cores.COR_FUNDO);
        cmbFiltroStatus.setForeground(cores.COR_TEXTO);
        cmbFiltroStatus.addActionListener(e -> {
            String statusSelecionado = cmbFiltroStatus.getSelectedItem().toString();
            if (statusSelecionado.equals("TODAS")) {
                controller.carregarTodasReservas();
            } else {
                controller.carregarReservas(statusSelecionado);
            }
        });
        
        btnAtualizar = criarBotao("Atualizar", cores.COR_PRIMARIA);
        btnAtualizar.addActionListener(e -> {
            String statusSelecionado = cmbFiltroStatus.getSelectedItem().toString();
            if (statusSelecionado.equals("TODAS")) {
                controller.carregarTodasReservas();
            } else {
                controller.carregarReservas(statusSelecionado);
            }
        });
        
        painelFiltro.add(lblFiltro);
        painelFiltro.add(cmbFiltroStatus);
        painelFiltro.add(btnAtualizar);
        
        painel.add(titulo, BorderLayout.WEST);
        painel.add(painelFiltro, BorderLayout.EAST);
        
        return painel;
    }
    
    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(cores.COR_FUNDO);
        
        String[] colunas = {
            "ID", "ISBN", "Título", "Cliente", "Quantidade", 
            "Data da Reserva", "Status", "Funcionário"
        };
        
        modeloTabelaReservas = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaReservas = new JTable(modeloTabelaReservas) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground((row % 2 == 0) ? cores.COR_FUNDO : cores.COR_DESTAQUE);
                    
                    // Colorir status
                    if (col == 6) {
                        String status = getValueAt(row, col).toString();
                        if (status.equals("PENDENTE")) {
                            c.setForeground(cores.COR_ALERTA);
                        } else if (status.equals("PRONTA")) {
                            c.setForeground(cores.COR_SUCESSO);
                        } else if (status.equals("CONCLUIDA")) {
                            c.setForeground(cores.COR_PRIMARIA);
                        } else if (status.equals("CANCELADA")) {
                            c.setForeground(Color.RED);
                        }
                    } else {
                        c.setForeground(cores.COR_TEXTO);
                    }
                } else {
                    c.setBackground(cores.COR_DESTAQUE);
                    c.setForeground(cores.COR_TEXTO);
                }
                return c;
            }
        };
        
        tabelaReservas.setRowHeight(30);
        tabelaReservas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaReservas.setGridColor(cores.COR_DESTAQUE);
        tabelaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Ajustar largura das colunas
        tabelaReservas.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabelaReservas.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabelaReservas.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabelaReservas.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabelaReservas.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabelaReservas.getColumnModel().getColumn(5).setPreferredWidth(150);
        tabelaReservas.getColumnModel().getColumn(6).setPreferredWidth(100);
        tabelaReservas.getColumnModel().getColumn(7).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tabelaReservas);
        scrollPane.getViewport().setBackground(cores.COR_PAINEL);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        painel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(cores.COR_DESTAQUE),
            "Lista de Reservas", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16), cores.COR_PRIMARIA
        ));
        
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    private JPanel criarPainelInferior() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(cores.COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelBotoes.setBackground(cores.COR_FUNDO);
        
        btnMarcarPronta = criarBotao("Marcar como Pronta", cores.COR_SUCESSO);
        btnMarcarPronta.addActionListener(e -> controller.marcarComoPronta());
        
        btnConcluir = criarBotao("Concluir Reserva", cores.COR_PRIMARIA);
        btnConcluir.addActionListener(e -> controller.concluirReserva());
        
        btnCancelar = criarBotao("Cancelar Reserva", cores.COR_ALERTA);
        btnCancelar.addActionListener(e -> controller.cancelarReserva());
        
        btnVoltar = criarBotao("Voltar", cores.COR_SECUNDARIA);
        btnVoltar.addActionListener(e -> {
            // Este código será chamado pelo MainFrame ou outro container
            // para voltar à tela anterior
        });
        
        painelBotoes.add(btnMarcarPronta);
        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnVoltar);
        
        painel.add(painelBotoes, BorderLayout.CENTER);
        
        return painel;
    }
    
    private JButton criarBotao(String texto, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        botao.setFocusPainted(false);
        botao.setBackground(corFundo);
        botao.setForeground(cores.COR_TEXTO_CLARO);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        botao.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(corFundo.brighter());
            }
            
            public void mouseExited(MouseEvent e) {
                botao.setBackground(corFundo);
            }
        });
        
        return botao;
    }
    
    public void atualizarTabelaReservas(List<Reserva> listaReservas) {
        modeloTabelaReservas.setRowCount(0);
        
        for (Reserva reserva : listaReservas) {
            modeloTabelaReservas.addRow(new Object[] {
                reserva.getId(),
                reserva.getIsbn(),
                reserva.getTituloLivro(),
                reserva.getNomeCliente(),
                reserva.getQuantidade(),
               // controller.formatarData(reserva.getDataReserva()),
                reserva.getStatus(),
                reserva.getUsuario()
            });
        }
    }
    
    public JTable getTabelaReservas() {
        return tabelaReservas;
    }
    
    public DefaultTableModel getModeloTabelaReservas() {
        return modeloTabelaReservas;
    }
    
    public JComboBox<String> getCmbFiltroStatus() {
        return cmbFiltroStatus;
    }
    
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    
    public void setOnVoltarListener(Runnable action) {
        btnVoltar.addActionListener(e -> action.run());
    }
    
    public void mostrarMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
    
    
}