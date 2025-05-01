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
		for (Venda v : vdao.listarTodos()) {
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

	public Pagamento verificarPagamento(String formaDePagamento, double totalDaVenda, double valorPago) {
		pagamento = pagamentoService.processarPagamento(formaDePagamento, totalDaVenda, valorPago);
		if (pagamento == null) {
			return null;
		} 
		return pagamento;
	}
 
	public void efetuarPagamento(Pagamento pagamento) {
		Venda venda = new Venda(1, listaItemVenda, pagamento, LocalDate.now(), pagamento.getValorPago());
		vdao.adicionarVenda(venda);

	}

	public List<Venda> listarVendas() {
		return vdao.listarTodos();
	}
}
