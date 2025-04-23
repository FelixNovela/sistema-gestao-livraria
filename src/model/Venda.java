package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Venda implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Cliente cliente;
	private List<ItemVenda> itens;
	private Pagamento pagamento;
	private LocalDate data;
	private double valorTotal;

	public Venda(int id, Cliente cliente, List<ItemVenda> itens, Pagamento pagamento, LocalDate data,
			double valorTotal) {
		this.id = id;
		this.cliente = cliente;
		this.itens = itens;
		this.pagamento = pagamento;
		this.data = data;
		this.valorTotal = valorTotal;
	}

	public int getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public LocalDate getData() {
		return data;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	@Override
	public String toString() {
		return "Venda [id=" + id + ", cliente=" + cliente + ", itens=" + itens + ", pagamento=" + pagamento + ", data="
				+ data + ", valorTotal=" + valorTotal + "]";
	}
	
	
}
