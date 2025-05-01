package model;

import java.io.Serializable;

public class Livro implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isbn;
    private String titulo;
    private String autor;
    private String categoria;
    private double preco;
    private int quantidadeEmEstoque;
    private boolean status;

    public Livro() {
    	
    }
    public Livro(String isbn, String titulo, String autor, String editora, double preco, int quantidadeEmEstoque) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = editora;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.status = true;
    }

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String editora) {
		this.categoria = editora;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}

	public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
		this.quantidadeEmEstoque = quantidadeEmEstoque;
	}

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Livro [isbn=" + isbn + ", titulo=" + titulo + ", autor=" + autor + ", editora=" + categoria + ", preco="
				+ preco + ", quantidadeEmEstoque=" + quantidadeEmEstoque + "]";
	}

    
}
