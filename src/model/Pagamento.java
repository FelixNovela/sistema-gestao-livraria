package model;

import java.io.Serializable;

import model.enums.TipoPagamento;

public class Pagamento implements Serializable {
	private TipoPagamento tipoPagamento;
	private double valorPago;
	private double troco;

	public Pagamento(TipoPagamento tipoPagamento, double valorPago, double troco) {
		this.tipoPagamento = tipoPagamento;
		this.valorPago = valorPago;
		this.troco = troco;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public double getValorPago() {
		return valorPago;
	}
 
	public double getTroco() {
		return troco;
	}

	@Override
	public String toString() {
		return "Pagamento [tipoPagamento=" + tipoPagamento + ", valorPago=" + valorPago + ", troco=" + troco + "]";
	}
	
}
