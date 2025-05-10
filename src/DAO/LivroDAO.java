package DAO;

import java.util.List;

import model.Livro;

public class LivroDAO extends DAOGenerico<Livro> {
	private final String FICHEIRO = "livros.dat";

	public void adicionarLivro(Livro livro) {
		List<Livro> livros = listarTodos();
		livros.add(livro);
		salvar(livros);
	}

	public void salvar(List<Livro> livros) {
		escreverFicheiro(FICHEIRO, livros);
	}
	
	public List<Livro> listarTodos() {
		return lerFicheiro(FICHEIRO);
	} 

	public Livro buscarPorIsbn(String isbn) {
		return listarTodos().stream().filter(l -> l.getIsbn().equals(isbn)).findFirst().orElse(null); 
	}

	public void atualizarEstoque(Livro livro, int novaQuantidade) {
		List<Livro> livros = listarTodos();
		for (Livro l : livros) {
			if (l.getIsbn().equals(livro.getIsbn())) {
				l.setQuantidadeEmEstoque(novaQuantidade);
				salvar(livros);
				break;
			}
		}
		
	}
	 
	public void atualizaPreco(Livro livro, double novoPreco) {
		List<Livro> livros = listarTodos();
		for (Livro l : livros) {
			if (l.getIsbn().equals(livro.getIsbn())) { 
				l.setPreco(novoPreco);
				salvar(livros);
				break;
			}
		}
		
	}
	
}
