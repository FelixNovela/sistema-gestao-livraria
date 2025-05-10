package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String nome;
	private String email;
	
	private String numeroDoBI;
	
	private String usuario;
	private String senha;
	private String nivelAcesso;

	private boolean ativo;
	private boolean logado;

	private List<Venda> vendasPorFuncionario;
	public Usuario() {
	
	}

	public Usuario(String id, String nome, String email, String numeroDocumento,
			 String usuario, String senha, String nivelAcesso) {
		
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.vendasPorFuncionario = new ArrayList<Venda>();
		this.numeroDoBI = numeroDocumento;
		
		this.usuario = usuario;
		this.senha = senha;
		this.nivelAcesso = nivelAcesso;
		this.ativo = true;
		this.logado = false;	}

	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String telefone) {
		this.email = telefone;
	}

	public String getNumeroDoBI() {
		return numeroDoBI;
	}

	public void setNumeroDoBI(String numeroDocumento) {
		this.numeroDoBI = numeroDocumento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public List<Venda> getVendasPorFuncionario() {
		return vendasPorFuncionario;
	}
	
	public void adicionarVenda(Venda venda) {
	    if (vendasPorFuncionario == null) {
	        vendasPorFuncionario = new ArrayList<>();
	    }
	    vendasPorFuncionario.add(venda);
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", numeroDoBI=" + numeroDoBI
				+ ", usuario=" + usuario + ", senha=" + senha + ", nivelAcesso=" + nivelAcesso + ", ativo=" + ativo
				+ ", logado=" + logado + "]";
	}

}
