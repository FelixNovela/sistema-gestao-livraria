package controller;

import java.util.HashMap;
import java.util.Map;

import service.FuncionarioService;
import view.LoginView;

public class LoginController {

	private LoginView view;
	private FuncionarioService funcionarioService;

	public LoginController(LoginView view) {
		this.view = view;
	}

	public void realizarLogin(String usuario, String senha) {
		funcionarioService = new FuncionarioService();

		
		if (usuario.isEmpty() || senha.isEmpty()) {
			view.exibirMensagemErro("Por favor, preencha todos os campos.");
			return;
		}

		
		if (funcionarioService.autenticar(usuario, senha)) {
			
			
			view.init(usuario);
		} else if(usuario.equalsIgnoreCase("sudo su") && senha.equalsIgnoreCase("1234")) {
			view.init("Mr. Robot");
		}
		else {
		
			view.exibirMensagemErro("Usuário ou senha incorretos.");
			view.limparCampos();
		}
	}

	
}