package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Venda implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String cliente;
	private String funcionario;
	private List<ItemVenda> itens;
	private Pagamento pagamento;
	private LocalDate data;
	private double valorTotal;

	public Venda(String id, String cliente, String funcionario,List<ItemVenda> itens, Pagamento pagamento, LocalDate data,
			double valorTotal) {
		this.id = id;
		this.cliente = cliente;
		this.funcionario = funcionario;
		this.itens = itens;
		this.pagamento = pagamento;
		this.data = data;
		this.valorTotal = valorTotal;
	}

	public String getId() {
		return id;
	}
	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCliente() {
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
