package model;
import java.io.Serializable;

import model.enums.TipoDocumento;

public class Cliente implements Serializable {
    private int id;
    private String nome;
    private String email;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;

    public Cliente(int id, String nome, String email, TipoDocumento tipoDocumento, String numeroDocumento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

    
}

