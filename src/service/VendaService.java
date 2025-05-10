package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import DAO.LivroDAO;
import DAO.VendaDAO;
import model.Usuario;
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
	private String nomeUser;
	UsuarioService funcionarioService;
	Usuario funcionario;

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

	
	public double totalVendas() {
		List<Venda> vendas = listarVendas();
		double valorTotal = 0;
		for(Venda venda : vendas) {
			valorTotal += venda.getPagamento().getValorTotalDaVenda();
		}
		return valorTotal;
	}
	public String gerarIdDaVenda() {
	    List<Venda> vendas = listarVendas();

	    int ultimoId = 0;

	    if (!vendas.isEmpty()) {
	        Venda ultimaVenda = vendas.get(vendas.size() - 1);
	        String ultimoIdString = ultimaVenda.getId();

	        if (ultimoIdString != null && !ultimoIdString.isEmpty()) {
	            ultimoId = Integer.parseInt(ultimoIdString);
	        }
	    }

	    ultimoId++;

	    return String.format("%04d", ultimoId);
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
	
	public void setNomeUser(String nomeUser) {
		
		this.nomeUser = nomeUser;
		
		 
	}
	public Usuario retornarFuncionario() {
		
		funcionarioService = new UsuarioService();
		
		this.funcionario = funcionarioService.buscarPorUsuario(nomeUser);
		return this.funcionario;
	}
	
	public List<Venda> ultimasVendas(){
		List<Venda> ultimasVendas = listarVendas().stream()
			    .skip(Math.max(0, listarVendas().size() - 5))
			    .collect(Collectors.toList());
		return ultimasVendas;

	}
 
	public void efetuarPagamento(Pagamento pagamento, String nomeCliente) {
		
		Usuario funcionario = retornarFuncionario();
		
		UsuarioService usuarioService = new UsuarioService();
		Venda venda = new Venda(gerarIdDaVenda(),nomeCliente,funcionario,listaItemVenda, pagamento, LocalDateTime.now(), pagamento.getValorPago());
		funcionario.adicionarVenda(venda);
		usuarioService.atualizarFuncionario(funcionario);
		vdao.adicionarVenda(venda);

	}

	public List<Venda> listarVendas() {
		return vdao.listarTodos();
	}
}
