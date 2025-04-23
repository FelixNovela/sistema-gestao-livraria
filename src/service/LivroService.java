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

	public void cadastrarLivro(Livro livro) {
		lvrdao.adicionarLivro(livro);
	}

	public Livro buscarPorIsbn(String isbn) {
		return lvrdao.buscarPorIsbn(isbn);
	}

	public List<Livro> listarTodos() {
		return lvrdao.listarTodos();
	}

	public void atualizarEstoque(Livro livro, int novaQuantidade) {
		lvrdao.atualizarEstoque(livro, novaQuantidade);
	}
}
