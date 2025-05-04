package model;

import java.io.Serializable;

import model.enums.NivelAcesso;

public class Usuario implements Serializable {
	private String username;
	private String email;
	private String senha;
	private int nivelAcesso;

	public Usuario(String username, String senha, String email, int nivelAcesso) {
		this.username = username;
		this.senha = senha;
		this.email = email;
		this.nivelAcesso = nivelAcesso;
	}

	public String getUsername() {
		return username;
	}

	public String getSenha() {
		return senha;
	}

	public int getNivelAcesso() {
		return nivelAcesso;
	}

	public String getEmail() {
		return email;
	}
}
