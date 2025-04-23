package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.ClienteDAO;
import DAO.LivroDAO;
import DAO.VendaDAO;
import model.Cliente;
import model.ItemVenda;
import model.Livro;
import model.Pagamento;
import model.Venda;

public class VendaService {
	private VendaDAO vdao = new VendaDAO();
	private LivroDAO ldao = new LivroDAO();
	private ClienteDAO cdao = new ClienteDAO();
	private PagamentoService pagamentoService = new PagamentoService();

	public boolean venderLivro(int idVenda, int clienteId, String isbn, int quantidade, String tipoPagamento,
			double valorPago) {
		Cliente cliente = cdao.buscarPorId(clienteId);
		if (cliente == null) {
			return false;
		}

		Livro livro = ldao.buscarPorIsbn(isbn);
		if (livro == null || livro.getQuantidadeEmEstoque() < quantidade) {
			return false;
		}

		double total = livro.getPreco() * quantidade;
		Pagamento pagamento = pagamentoService.processarPagamento(tipoPagamento, total, valorPago);
		if (pagamento == null) {
			return false;
		}

		ldao.atualizarEstoque(livro, livro.getQuantidadeEmEstoque() - quantidade);

		List<ItemVenda> listaIV = new ArrayList<>();
		listaIV.add(new ItemVenda(livro, quantidade, livro.getPreco()));

		Venda venda = new Venda(idVenda, cliente, listaIV, pagamento, LocalDate.now(), total);
		vdao.adicionarVenda(venda);

		return true;
	}

	public List<Venda> listarVendas() {
		return vdao.listarTodos();
	}
}
