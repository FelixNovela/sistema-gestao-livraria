package DAO;

import java.util.List;

import model.Usuario;

public class UsuarioDAO extends DAOGenerico<Usuario> {
	private final String FICHEIRO = "usuarios.dat";

	public void adicionarUsuario(Usuario usuario) { 
		List<Usuario> usuarios = listarTodos();
		usuarios.add(usuario);
		escreverFicheiro(FICHEIRO, usuarios);
	}

	public List<Usuario> listarTodos() {
		return lerFicheiro(FICHEIRO);
	}

	public Usuario verificar(String username, String senha) {
		return listarTodos().stream().filter(u -> u.getUsername().equals(username) && u.getSenha().equals(senha))
				.findFirst().orElse(null);
	}
}
