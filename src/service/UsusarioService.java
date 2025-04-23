package service;

import DAO.UsuarioDAO;
import model.Usuario;

public class UsusarioService { 
    private UsuarioDAO usdao = new UsuarioDAO();

    public Usuario verificar(String username, String senha) {
        return usdao.verificar(username, senha); 
    }

    public void cadastrarUsuario(Usuario usuario) {
    	usdao.adicionarUsuario(usuario); 
    }
}
