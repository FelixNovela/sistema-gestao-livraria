package service;

import java.util.List;

import DAO.ClienteDAO;
import model.Cliente;


public class ClienteService {
	private ClienteDAO cldao = new ClienteDAO();
	
	public List<Cliente> listarClientes(){
		return cldao.listarTodos();
	}

	public boolean validarDocumento(String tipo, String numero) {
		if (tipo.equals("BI")) {
			if (numero.length() != 12)
				return false;
			for (int i = 0; i < numero.length(); i++) {
				if (!Character.isDigit(numero.charAt(i)))
					return false;  
			} 
			return true;
		} else if (tipo.equals("Passaporte")) {
			if (numero.length() != 9)
				return false;

			String letras = numero.substring(0, 2);
			String numeros = numero.substring(2);

			for (int i = 0; i < letras.length(); i++) {
				if (!Character.isUpperCase(letras.charAt(i)))
					return false;
			}
			for (int i = 0; i < numeros.length(); i++) {
				if (!Character.isDigit(numeros.charAt(i)))
					return false;
			}
			return true;
		} 
		return false; 
	}

	public void cadastrarCliente(Cliente cliente) {
		cldao.adicionarCliente(cliente);
	}
}
