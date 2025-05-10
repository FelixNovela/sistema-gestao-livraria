package controller;

import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.validator.EmailValidator;

import model.Usuario;
import service.UsuarioService;
import view.AdicionarFuncionarioView;

public class AdicionarUsuarioController {
    private AdicionarFuncionarioView view;
    private UsuarioService usuarioService;
    private DefaultTableModel tabelaUsuarios;
    private String id;
    public AdicionarUsuarioController(AdicionarFuncionarioView view, UsuarioService usuarioService, DefaultTableModel tabelaUsuarios) {
        this.view = view;
        this.usuarioService = usuarioService;
        this.tabelaUsuarios = tabelaUsuarios;
        
        this.view.setController(this);
    }
    
    @SuppressWarnings("deprecation")
	public void salvarFuncionario() {
        try {
           
        	
            String nome = view.getNome();
            String email = view.getEmail();
            String numeroDocumento = view.getNumeroDoBI();
            String usuario = view.getUsuario();
            String senha = view.getSenha();
            String nivelAcesso = view.getNivelAcesso();
            
           
            EmailValidator validator = EmailValidator.getInstance();

            if (!validator.isValid(email)){
            	 JOptionPane.showMessageDialog(view, 
                         "E-mail inválido",
                         "Erro de Validacao", 
                         JOptionPane.ERROR_MESSAGE);
                     return;
            }
            
            
         
            
            if (nome.isEmpty() || email.isEmpty() || 
                numeroDocumento.isEmpty() || 
                usuario.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Todos os campos sao obrigatórios.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
           
            if (!usuarioService.validarEDocumento(numeroDocumento)) {
                JOptionPane.showMessageDialog(view, 
                    "Número de documento inválido",
                    "Erro de Validacao", 
                    0);
                return;
            }

            if (!usuarioService.verificarBI(numeroDocumento)) {
                JOptionPane.showMessageDialog(view, 
                    "Número de BI ja cadastrado",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (usuarioService.buscarPorUsuario(usuario) != null) {
                JOptionPane.showMessageDialog(view, 
                    "Este nome de usuario ja exite.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            id = usuarioService.gerarIdUsuario();
            Usuario novoUsuario = new Usuario(id,nome,email,numeroDocumento.toUpperCase(),usuario,senha,nivelAcesso);
            
           
            
            usuarioService.adicionarUsuario(novoUsuario);

            
            tabelaUsuarios.addRow(new Object[]{
                numeroDocumento.toUpperCase(), nome, email, usuario,senha,nivelAcesso
            });

            JOptionPane.showMessageDialog(view, 
                "Usuario adicionado com sucesso",
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
                
            view.fechar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                "Erro ao salvar: " + ex.getMessage(),
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}