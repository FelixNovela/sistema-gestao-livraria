package model;

import java.io.Serializable;

public class ItemVenda implements Serializable {
	private Livro livro;
	private int quantidade;

	public ItemVenda(Livro livro, int quantidade) {
		this.livro = livro;
		this.quantidade = quantidade;  
	}

	public Livro getLivro() {
		return livro;
	}

	public int getQuantidade() {
		return quantidade;
	}


	@Override
	public String toString() {
		return "ItemVenda [livro=" + livro + ", quantidade=" + quantidade + ", preco=" + livro.getPreco() + "]";
	}
	
}
