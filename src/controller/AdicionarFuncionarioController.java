package controller;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Funcionario;
import model.enums.TipoDocumento;
import service.FuncionarioService;
import view.AdicionarFuncionarioView;

public class AdicionarFuncionarioController {
    private AdicionarFuncionarioView view;
    private FuncionarioService funcionarioService;
    private DefaultTableModel tabelaFuncionarios;
    private String id;
    public AdicionarFuncionarioController(AdicionarFuncionarioView view, FuncionarioService funcionarioService, DefaultTableModel tabelaFuncionarios) {
        this.view = view;
        this.funcionarioService = funcionarioService;
        this.tabelaFuncionarios = tabelaFuncionarios;
        
        this.view.setController(this);
    }
    
    public void salvarFuncionario() {
        try {
           
        	
            String nome = view.getNome();
            String telefone = view.getTelefone();
            String numeroDocumento = view.getNumeroDoBI();
            String usuario = view.getUsuario();
            String senha = view.getSenha();
            String nivelAcesso = view.getNivelAcesso();
            
           
            if (nome.isEmpty() || telefone.isEmpty() || 
                numeroDocumento.isEmpty() || 
                usuario.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Todos os campos sao obrigatórios.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
           
            if (!funcionarioService.validarDocumento(numeroDocumento)) {
                JOptionPane.showMessageDialog(view, 
                    "Número de documento inválido",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            if (funcionarioService.buscarPorUsuario(usuario) != null) {
                JOptionPane.showMessageDialog(view, 
                    "Este nome de usuario ja exite.",
                    "Erro de Validacao", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            id = funcionarioService.gerarIdFuncionario();
            Funcionario novoFuncionario = new Funcionario(id,nome,telefone,numeroDocumento,usuario,senha,nivelAcesso);
            
           
            
            funcionarioService.adicionarFuncionario(novoFuncionario);

            
            tabelaFuncionarios.addRow(new Object[]{
                id, nome, telefone, numeroDocumento, usuario,senha,nivelAcesso
            });

            JOptionPane.showMessageDialog(view, 
                "Funcionário adicionado com sucesso",
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