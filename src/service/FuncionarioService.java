package service;

import java.util.List;
import DAO.FuncionarioDAO;
import model.Funcionario;
import model.enums.TipoDocumento;

public class FuncionarioService {
	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

	public List<Funcionario> listarTodos() {
		return funcionarioDAO.listarTodos();
	}

	public void adicionarFuncionario(Funcionario funcionario) {
		funcionarioDAO.adicionarFuncionario(funcionario);
	}

	public Funcionario buscarPorId(String id) {
		return funcionarioDAO.buscarPorId(id);
	}
	
	public int totalFuncionarios() {
		int total = 0;
		for(Funcionario func : listarTodos()) {
			if(func.isAtivo()) {
				total += 1;
			}
		}
		
		return total;
	}

	public Funcionario buscarPorUsuario(String usuario) {
		return funcionarioDAO.buscarPorUsuario(usuario);
	}

	public boolean validarDocumento(String numero) {
		if (numero == null || numero.trim().isEmpty()) {
			return false;
		}

		if (numero.length() != 12) {
			return false;
		}

		for (int i = 0; i < numero.length(); i++) {
			if (!Character.isDigit(numero.charAt(i))) {
				return false;
			}

		}
		return true;

	}
	
	public String gerarIdFuncionario() {
	    List<Funcionario> funcionarios = listarTodos();
	    int ultimoNumero = 0;

	    if (!funcionarios.isEmpty()) {
	        Funcionario ultimo = funcionarios.get(funcionarios.size() - 1);
	        String ultimoId = ultimo.getId();

	        if (ultimoId != null && ultimoId.startsWith("FUNC-")) {
	            String parteNumerica = ultimoId.substring(5);
	            try {
	                ultimoNumero = Integer.parseInt(parteNumerica);
	            } catch (NumberFormatException e) {
	                
	                ultimoNumero = 0;
	            }
	        }
	    }

	    ultimoNumero++;

	    return String.format("FUNC-%03d", ultimoNumero);
	}


	public void removerFuncionario(String id) {
		List<Funcionario> funcionario = listarTodos();
		for(int i = 0; i < funcionario.size(); i++) {
			if(funcionario.get(i).getId().equalsIgnoreCase(id)) {
				funcionario.get(i).setAtivo(false);
				funcionario.set(i, funcionario.get(i)); 
				funcionarioDAO.salvar(funcionario);
				break;
			}
		}
	}

	
	public String retornarNomeFuncionario(String usuario) {
		Funcionario funcionario = listarTodos().stream().filter(f -> f.getUsuario().equalsIgnoreCase(usuario)).findFirst().orElse(null);
		return funcionario.getNome();
	}
	public void atualizarFuncionario(Funcionario funcionarioAtualizado) {
		funcionarioDAO.atualizarFuncionario(funcionarioAtualizado);
	}

	public List<Funcionario> buscarPorNome(String nome) {
		return funcionarioDAO.buscarPorNome(nome);
	}


	public boolean autenticar(String usuario, String senha) {
		
		return funcionarioDAO.autenticar(usuario, senha);
	}
}