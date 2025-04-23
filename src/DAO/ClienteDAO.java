package DAO;

import java.util.List;

import model.Cliente;

public class ClienteDAO extends DAOGenerico<Cliente> {
	private final String FICHEIRO = "clientes.dat";

	public void adicionarCliente(Cliente cliente) {
		List<Cliente> clientes = listarTodos();
		clientes.add(cliente);
		escreverFicheiro(FICHEIRO, clientes);
	}

	public List<Cliente> listarTodos() {
		return lerFicheiro(FICHEIRO);
	}

	public Cliente buscarPorId(int id) {
		return listarTodos().stream().filter(c -> c.getId() == id).findFirst().orElse(null);
	}
}
