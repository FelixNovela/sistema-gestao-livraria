package DAO;

import java.util.List;

import model.Venda;

public class VendaDAO extends DAOGenerico<Venda> {
	private final String FICHEIRO = "vendas.dat";

	public void adicionarVenda(Venda venda) {
		List<Venda> vendas = listarTodos();
		vendas.add(venda);
		escreverFicheiro(FICHEIRO, vendas); 
	}

	public List<Venda> listarTodos() {
		return lerFicheiro(FICHEIRO);
	}

	public Venda buscarPorId(String id) {
		return listarTodos().stream().filter(v -> v.getId() == id).findFirst().orElse(null);
	}
}
