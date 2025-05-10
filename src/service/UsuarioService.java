package service;

import java.util.List;

import DAO.UsuarioDAO;
import model.Usuario;
import model.Venda;

public class UsuarioService {
	private UsuarioDAO funcionarioDAO = new UsuarioDAO();

	public List<Usuario> listarTodos() {
		return funcionarioDAO.listarTodos();
	}

	
	public boolean verificarEmail(String email) {
		Usuario funcionario = listarTodos().stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
		if(funcionario == null) {
			return true;
		}
		return false;
	}
	
	public void adicionarFuncionario(Usuario funcionario) {
		funcionarioDAO.adicionarFuncionario(funcionario);
	}

	public Usuario buscarPorId(String id) {
		return funcionarioDAO.buscarPorId(id);
	}
	
	public int totalFuncionarios() {
		int total = 0;
		for(Usuario func : listarTodos()) {
			if(func.isAtivo()) {
				total += 1;
			}
		}
		
		return total;
	}

	
	public Usuario buscarPorBI(String numeroBi) {
		return listarTodos().stream().filter(u -> u.getNumeroDoBI().equalsIgnoreCase(numeroBi)).findFirst().orElse(null);
	}
	public Usuario buscarPorUsuario(String usuario) { 
		return funcionarioDAO.buscarPorUsuario(usuario);
	}
	public boolean verificarUsername(String username) {
		Usuario funcionario = listarTodos().stream().filter(u -> u.getUsuario().equalsIgnoreCase(username)).findFirst().orElse(null);
		if(funcionario != null) {
			return false;
		}
		return true;
	}
	public boolean verificarBI(String bi) {
		Usuario funcionario = listarTodos().stream().filter(u -> u.getNumeroDoBI().equalsIgnoreCase(bi)).findFirst().orElse(null);
		if(funcionario != null) {
			return false;
		}
		return true;
	} 

	public boolean validarEDocumento(String numero) {
	    if (numero == null || numero.trim().isEmpty()) {
	        return false;
	    }

	    if (numero.length() != 13) {
	        return false;
	    }

	    for (int i = 0; i < 12; i++) {
	        if (!Character.isDigit(numero.charAt(i))) {
	            return false;
	        }
	    }

	    char ultimaLetra = numero.charAt(12);
	    if (!Character.isLetter(ultimaLetra)) {
	        return false;
	    }

	    
	    return true;
	}


	
	public String gerarIdFuncionario() {
	    List<Usuario> funcionarios = listarTodos();
	    int ultimoNumero = 0;

	    if (!funcionarios.isEmpty()) {
	        Usuario ultimo = funcionarios.get(funcionarios.size() - 1);
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


	public void removerFuncionario(String numeroBi) {
		List<Usuario> funcionario = listarTodos();
		for(int i = 0; i < funcionario.size(); i++) {
			if(funcionario.get(i).getNumeroDoBI().equalsIgnoreCase(numeroBi)) {
				funcionario.get(i).setAtivo(false);
				funcionario.set(i, funcionario.get(i)); 
				funcionarioDAO.salvar(funcionario);
				break;
			}
		}
	}

	
	
	
	
	public String retornarNomeFuncionario(String usuario) {
		Usuario funcionario = listarTodos().stream().filter(f -> f.getUsuario().equalsIgnoreCase(usuario)).findFirst().orElse(null);
		return funcionario.getNome();
	}
	public void atualizarFuncionario(Usuario funcionarioAtualizado) {
		funcionarioDAO.atualizarFuncionario(funcionarioAtualizado);
	}

	public List<Usuario> buscarPorNome(String nome) {
		return funcionarioDAO.buscarPorNome(nome);
	}


	public boolean autenticar(String usuario, String senha) {
		
		return funcionarioDAO.autenticar(usuario, senha);
	}
}