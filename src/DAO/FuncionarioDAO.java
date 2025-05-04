package DAO;

import java.util.List;
import java.util.stream.Collectors;
import model.Funcionario;

public class FuncionarioDAO extends DAOGenerico<Funcionario> {
    private final String FICHEIRO = "funcionarios.dat";
    
    public void adicionarFuncionario(Funcionario funcionario) {
        List<Funcionario> funcionarios = listarTodos();
        funcionarios.add(funcionario);
        escreverFicheiro(FICHEIRO, funcionarios);
    }
    
    public List<Funcionario> listarTodos() {
        return lerFicheiro(FICHEIRO);
    }
    
    public void salvar(List<Funcionario> funcionarios) {
    	escreverFicheiro(FICHEIRO, funcionarios);
    }
    public Funcionario buscarPorId(String id) {
        return listarTodos().stream()
                .filter(f -> f.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
    
    public Funcionario buscarPorUsuario(String usuario) {
        return listarTodos().stream()
                .filter(f -> f.getUsuario().equalsIgnoreCase(usuario))
                .findFirst()
                .orElse(null);
    }
    
    public void removerFuncionario(String id) {
        List<Funcionario> funcionarios = listarTodos();
        List<Funcionario> atualizados = funcionarios.stream()
                .filter(f -> f.getId() != id)
                .collect(Collectors.toList());
        escreverFicheiro(FICHEIRO, atualizados);
    }
    
    public void atualizarFuncionario(Funcionario funcionarioAtualizado) {
        List<Funcionario> funcionarios = listarTodos();
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId().equalsIgnoreCase(funcionarioAtualizado.getId())) {
                funcionarios.set(i, funcionarioAtualizado);
                break;
            }
        }
        escreverFicheiro(FICHEIRO, funcionarios);
    }
    
    
    
    public List<Funcionario> buscarPorNome(String nome) {
        return listarTodos().stream()
                .filter(f -> f.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    
    public boolean autenticar(String usuario, String senha) {
        return listarTodos().stream()
                .anyMatch(f -> f.getUsuario().equals(usuario) && f.getSenha().equals(senha));
    }
}