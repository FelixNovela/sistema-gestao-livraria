package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.ClienteDAO;
import DAO.LivroDAO;
import DAO.VendaDAO;
import model.ItemVenda;
import model.Livro;
import model.Pagamento;
import model.Venda;

public class VendaService {
	private VendaDAO vdao = new VendaDAO();
	private LivroDAO ldao = new LivroDAO();
	private PagamentoService pagamentoService = new PagamentoService();
	public List<ItemVenda> listaItemVenda = new ArrayList<>();
	private Pagamento pagamento;

	public boolean adicionarLivroNaListaItemVenda(String isbn, int quantidade) {
		Livro livro = ldao.buscarPorIsbn(isbn);
		if (livro == null || livro.getQuantidadeEmEstoque() < quantidade) {
			return false;
		}

		listaItemVenda.add(new ItemVenda(livro, quantidade));

		return true;
	}
	
	public double calcularValorTotalDasVendas() { 
		double soma = 0;
		for(Venda v : vdao.listarTodos()) {
			soma += v.getValorTotal();
		}
		return soma;
	}

	public double calcularValorTotalAPagar() {
		double soma = 0;
		for (ItemVenda lista : listaItemVenda) {
			soma += lista.getLivro().getPreco() * lista.getQuantidade();
		}
		return soma;
	}

	public boolean efetuarPagamento(String formaDePagamento, double valorPago, int idVenda) {
		double valorAPagar = calcularValorTotalAPagar();

	    pagamento = pagamentoService.processarPagamento(formaDePagamento, valorAPagar, valorPago);
		if (pagamento == null) {
			return false;
		} 
		
		Venda venda = new Venda(idVenda, listaItemVenda, pagamento, LocalDate.now(), valorAPagar);
		vdao.adicionarVenda(venda);
		return true; 
	}


	public List<Venda> listarVendas() {
		return vdao.listarTodos();
	}
}
