package service;

import java.util.List;

import DAO.LivroDAO;
import model.Livro;

public class LivroService {
	private LivroDAO lvrdao = new LivroDAO();

	public boolean validarIsbn(String isbn) {
		
		if (isbn.length() != 13)
			return false;
		for (int i = 0; i < isbn.length(); i++) {
			if (!Character.isDigit(isbn.charAt(i)))
				return false;
		} 
		return true;
	}

	
	public void verificarCadastro(String isbn) {
		lvrdao.listarTodos().stream().filter(i -> i.getIsbn().equals(isbn)).findFirst().orElse(null);
		
	}
	public void adicionarLivro(Livro livro) {
		lvrdao.adicionarLivro(livro);
	}

	public int totalLivros() {
		int total = 0;
		for(Livro livro : listarTodos()) {
			if(livro.isStatus()) {
				total += 1;
			}
		}
		return total;
	}
	
	public Livro buscarPorIsbn(String isbn) {
		return lvrdao.buscarPorIsbn(isbn);
	}

	public List<Livro> listarTodos() {
		
		return lvrdao.listarTodos();
	}

	public void atualizarEstoque(String isbn, int novaQuantidade) {
		Livro livro = lvrdao.buscarPorIsbn(isbn);
		lvrdao.atualizarEstoque(livro, novaQuantidade);
	}
	
	public void atualizarPreco(String isbn, double novoPreco) {
		Livro livro = lvrdao.buscarPorIsbn(isbn);
		lvrdao.atualizaPreco(livro, novoPreco);
	}
	
	public void excluir(String isbn) {
		List<Livro> livros = listarTodos();
		
		for(int i = 0; i < livros.size(); i++) {
			if(livros.get(i).getIsbn().equals(isbn)) {
				livros.get(i).setStatus(false);
				livros.set(i, livros.get(i));
				lvrdao.salvar(livros);
				break;
			}
			
		}
	}
}
