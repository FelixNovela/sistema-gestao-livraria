package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;

import model.Livro;
import model.Reserva;
import service.LivroService;
import service.ReservaService;
import view.ReservaView;

public class ReservaController {
    
    private ReservaView view;
    private ReservaService reservaService;
    private LivroService livroService;
    
    public ReservaController(ReservaView view) {
        this.view = view;
        this.reservaService = new ReservaService();
        this.livroService = new LivroService();
    }
    
    public void carregarReservas(String status) {
        List<Reserva> reservas = reservaService.listarReservasPorStatus(status);
        view.atualizarTabelaReservas(reservas);
    }
    
    public void carregarTodasReservas() {
        List<Reserva> reservas = reservaService.listarTodasReservas();
        view.atualizarTabelaReservas(reservas);
    }
    
    public void marcarComoPronta() {
        int linhaSelecionada = view.getTabelaReservas().getSelectedRow();
        if (linhaSelecionada == -1) {
            view.mostrarMensagem(
                "Selecione uma reserva para marcar como pronta", 
                "Nenhuma reserva selecionada", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        int idReserva = Integer.parseInt(view.getTabelaReservas().getValueAt(linhaSelecionada, 0).toString());
        String status = view.getTabelaReservas().getValueAt(linhaSelecionada, 6).toString();
        
        if (!status.equals("PENDENTE")) {
            view.mostrarMensagem(
                "Apenas reservas com status PENDENTE podem ser marcadas como prontas", 
                "Operação inválida", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        reservaService.atualizarStatusReserva(idReserva, "PRONTA");
        
        view.mostrarMensagem(
            "Reserva marcada como pronta com sucesso!", 
            "Operação concluída", 
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Atualizar tabela
        String statusFiltro = view.getCmbFiltroStatus().getSelectedItem().toString();
        if (statusFiltro.equals("TODAS")) {
            carregarTodasReservas();
        } else {
            carregarReservas(statusFiltro);
        }
    }
    
    public void concluirReserva() {
        int linhaSelecionada = view.getTabelaReservas().getSelectedRow();
        if (linhaSelecionada == -1) {
            view.mostrarMensagem(
                "Selecione uma reserva para concluir", 
                "Nenhuma reserva selecionada", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        int idReserva = Integer.parseInt(view.getTabelaReservas().getValueAt(linhaSelecionada, 0).toString());
        String status = view.getTabelaReservas().getValueAt(linhaSelecionada, 6).toString();
        
        if (!status.equals("PRONTA")) {
            view.mostrarMensagem(
                "Apenas reservas com status PRONTA podem ser concluídas", 
                "Operação inválida", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Verificar se o livro está disponível em estoque
        String isbn = view.getTabelaReservas().getValueAt(linhaSelecionada, 1).toString();
        int quantidadeReservada = Integer.parseInt(view.getTabelaReservas().getValueAt(linhaSelecionada, 4).toString());
        
        Livro livro = livroService.buscarPorIsbn(isbn);
        if (livro == null) {
            view.mostrarMensagem(
                "Livro não encontrado no estoque!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        if (livro.getQuantidadeEmEstoque() < quantidadeReservada) {
            view.mostrarMensagem(
                "Estoque insuficiente para concluir a reserva!\n" +
                "Quantidade em estoque: " + livro.getQuantidadeEmEstoque() + "\n" +
                "Quantidade reservada: " + quantidadeReservada,
                "Estoque insuficiente", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Atualizar estoque
        livroService.atualizarEstoque(isbn, livro.getQuantidadeEmEstoque() - quantidadeReservada);
        
        // Concluir reserva
        reservaService.concluirReserva(idReserva);
        
        view.mostrarMensagem(
            "Reserva concluída com sucesso!", 
            "Operação concluída", 
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Atualizar tabela
        String statusFiltro = view.getCmbFiltroStatus().getSelectedItem().toString();
        if (statusFiltro.equals("TODAS")) {
            carregarTodasReservas();
        } else {
            carregarReservas(statusFiltro);
        }
    }
    
    public void cancelarReserva() {
        int linhaSelecionada = view.getTabelaReservas().getSelectedRow();
        if (linhaSelecionada == -1) {
            view.mostrarMensagem(
                "Selecione uma reserva para cancelar", 
                "Nenhuma reserva selecionada", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        int idReserva = Integer.parseInt(view.getTabelaReservas().getValueAt(linhaSelecionada, 0).toString());
        String status = view.getTabelaReservas().getValueAt(linhaSelecionada, 6).toString();
        
        if (status.equals("CONCLUIDA") || status.equals("CANCELADA")) {
            view.mostrarMensagem(
                "Esta reserva já foi finalizada ou cancelada", 
                "Operação inválida", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(
            view, 
            "Tem certeza que deseja cancelar esta reserva?", 
            "Confirmar cancelamento", 
            JOptionPane.YES_NO_OPTION
        );
        
    }}