package model;

import java.io.Serializable;

import model.enums.TipoDocumento;

public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String nome;
	private String telefone;
	
	private String numeroDoBI;

	private String usuario;
	private String senha;
	private String nivelAcesso;

	private boolean ativo;
	private boolean logado;

	public Funcionario() {
	
	}

	public Funcionario(String id, String nome, String telefone, String numeroDocumento,
			 String usuario, String senha, String nivelAcesso) {
		
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
	
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

}
