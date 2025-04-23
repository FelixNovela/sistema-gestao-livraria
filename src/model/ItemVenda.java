package model;

import java.io.Serializable;

public class ItemVenda implements Serializable {
	private Livro livro;
	private int quantidade;
	private double precoUnitario;

	public ItemVenda(Livro livro, int quantidade, double precoUnitario) {
		this.livro = livro;
		this.quantidade = quantidade; 
		this.precoUnitario = precoUnitario;
	}

	public Livro getLivro() {
		return livro;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public double getPrecoUnitario() {
		return precoUnitario;
	}

	public double calcularSubtotal() {
		return quantidade * precoUnitario;
	}
}
