package model;

import java.io.Serializable;

import model.enums.NivelAcesso;

public class Usuario implements Serializable {
	private String username;
	private String senha;
	private NivelAcesso nivelAcesso;

	public Usuario(String username, String senha, NivelAcesso nivelAcesso) {
		this.username = username;
		this.senha = senha;
		this.nivelAcesso = nivelAcesso;
	}

	public String getUsername() {
		return username;
	}

	public String getSenha() {
		return senha;
	}

	public NivelAcesso getNivelAcesso() {
		return nivelAcesso;
	}
}
