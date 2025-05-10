package service;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Livro;
import model.Reserva;

public class ReservaService {
    private static final String ARQUIVO_RESERVAS = "reservas.dat";
    private List<Reserva> reservas;
    private int proximoId = 1;
    
    public ReservaService() {
        reservas = new ArrayList<>();
        carregarReservas();
    }
    
    public boolean adicionarReserva(String isbn, String tituloLivro, String nomeCliente, int quantidade, String usuario) {
        // Verificar se já existe uma reserva pendente para o mesmo livro e cliente
        for (Reserva r : reservas) {
            if (r.getIsbn().equals(isbn) && r.getNomeCliente().equals(nomeCliente) 
                    && r.getStatus().equals("PENDENTE")) {
                // Atualizar a quantidade da reserva existente
                r.setQuantidade(r.getQuantidade() + quantidade);
                salvarReservas();
                return true;
            }
        }
        
        // Criar nova reserva
        Reserva reserva = new Reserva(isbn, tituloLivro, nomeCliente, quantidade, usuario);
        reserva.setId(proximoId++);
        reserva.setDataReserva(LocalDateTime.now());
        
        reservas.add(reserva);
        salvarReservas();
        return true;
    }
    
    public List<Reserva> listarTodasReservas() {
        return new ArrayList<>(reservas);
    }
    
    public List<Reserva> listarReservasPorStatus(String status) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getStatus().equals(status)) {
                resultado.add(reserva);
            }
        }
        return resultado;
    }
    
    public void atualizarStatusReserva(int idReserva, String novoStatus) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                reserva.setStatus(novoStatus);
                salvarReservas();
                break;
            }
        }
    }
    
    public boolean cancelarReserva(int idReserva) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId() == idReserva) {
                reservas.get(i).setStatus("CANCELADA");
                salvarReservas();
                return true;
            }
        }
        return false;
    }
    
    public boolean concluirReserva(int idReserva) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                reserva.setStatus("CONCLUIDA");
                salvarReservas();
                return true;
            }
        }
        return false;
    }
    
    public List<Reserva> verificarReservasPorLivro(String isbn) {
        List<Reserva> reservasLivro = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getIsbn().equals(isbn) && reserva.getStatus().equals("PENDENTE")) {
                reservasLivro.add(reserva);
            }
        }
        return reservasLivro;
    }
    
    public void atualizarStatusPorLivro(String isbn, String novoStatus) {
        for (Reserva reserva : reservas) {
            if (reserva.getIsbn().equals(isbn) && reserva.getStatus().equals("PENDENTE")) {
                reserva.setStatus(novoStatus);
            }
        }
        salvarReservas();
    }
    
    private void carregarReservas() {
        File arquivo = new File(ARQUIVO_RESERVAS);
        if (!arquivo.exists()) {
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            while (true) {
                try {
                    Reserva reserva = (Reserva) ois.readObject();
                    reservas.add(reserva);
                    
                    // Atualizar o próximo ID
                    if (reserva.getId() >= proximoId) {
                        proximoId = reserva.getId() + 1;
                    }
                } catch (EOFException e) {
                    break; // Fim do arquivo
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao carregar reservas: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarReservas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_RESERVAS))) {
            for (Reserva reserva : reservas) {
                oos.writeObject(reserva);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao salvar reservas: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String formatarData(LocalDateTime data) {
        if (data == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return data.format(formatter);
    }
}