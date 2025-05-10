package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private int idLivro;
    private String isbn;
    private String tituloLivro;
    private String nomeCliente;
    private int quantidade;
    private LocalDateTime dataReserva;
    private String status; // "PENDENTE", "PRONTA", "CONCLUIDA", "CANCELADA"
    private String usuario; // funcionário que registrou
    
    public Reserva() {
        this.dataReserva = LocalDateTime.now();
        this.status = "PENDENTE";
    }
    
    public Reserva(String isbn, String tituloLivro, String nomeCliente, int quantidade, String usuario) {
        this.isbn = isbn;
        this.tituloLivro = tituloLivro;
        this.nomeCliente = nomeCliente;
        this.quantidade = quantidade;
        this.usuario = usuario;
        this.dataReserva = LocalDateTime.now();
        this.status = "PENDENTE";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}