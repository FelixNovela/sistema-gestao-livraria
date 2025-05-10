package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Venda implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String cliente;
	private Usuario usuario;
	private List<ItemVenda> itens;
	private Pagamento pagamento;
	private LocalDateTime data;
	private double valorTotal;

	public Venda(String id, String cliente, Usuario funcionario,List<ItemVenda> itens, Pagamento pagamento, LocalDateTime data,
			double valorTotal) {
		this.id = id;
		this.cliente = cliente;
		this.usuario = funcionario;
		this.itens = itens;
		this.pagamento = pagamento;
		this.data = data;
		this.valorTotal = valorTotal;
	}

	public String getId() {
		return id;
	}
	public Usuario getFuncionario() {
		return usuario;
	}

	public void setFuncionario(Usuario funcionario) {
		this.usuario = funcionario;
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

	public LocalDateTime getData() {
		return data;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	

	@Override
	public String toString() {
		return "Venda [id=" + id +"Func "+usuario.getNome()+", cliente=" + cliente + ", itens=" + itens + ", pagamento=" + pagamento + ", data="
				+ data + ", valorTotal=" + valorTotal + "]";
	}
	
	
}
