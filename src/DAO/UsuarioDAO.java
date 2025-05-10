package DAO;

import java.util.List;
import java.util.stream.Collectors;

import model.Usuario;

public class UsuarioDAO extends DAOGenerico<Usuario> {
    private final String FICHEIRO = "usuarios.dat";
    
    public void adicionarFuncionario(Usuario funcionario) {
        List<Usuario> funcionarios = listarTodos();
        funcionarios.add(funcionario);
        escreverFicheiro(FICHEIRO, funcionarios);
    }
    
    public List<Usuario> listarTodos() {
        return lerFicheiro(FICHEIRO);
    }
    
    public void salvar(List<Usuario> funcionarios) {
    	escreverFicheiro(FICHEIRO, funcionarios);
    }
    public Usuario buscarPorId(String id) {
        return listarTodos().stream()
                .filter(f -> f.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
    
    public Usuario buscarPorUsuario(String usuario) {
        return listarTodos().stream()
                .filter(f -> f.getUsuario().equalsIgnoreCase(usuario))
                .findFirst()
                .orElse(null);
    } 
    
    public void removerFuncionario(String id) {
        List<Usuario> funcionarios = listarTodos();
        List<Usuario> atualizados = funcionarios.stream()
                .filter(f -> f.getId() != id)
                .collect(Collectors.toList());
        escreverFicheiro(FICHEIRO, atualizados);
    }
    
    public void atualizarFuncionario(Usuario funcionarioAtualizado) {
        List<Usuario> funcionarios = listarTodos();
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId().equalsIgnoreCase(funcionarioAtualizado.getId())) {
                funcionarios.set(i, funcionarioAtualizado);
                break;
            }
        }
        escreverFicheiro(FICHEIRO, funcionarios);
    }
    
    
    
    public List<Usuario> buscarPorNome(String nome) {
        return listarTodos().stream()
                .filter(f -> f.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    
    public boolean autenticar(String usuario, String senha) {
        return listarTodos().stream()
                .anyMatch(f -> f.getUsuario().equals(usuario) && f.getSenha().equals(senha));
    }
}