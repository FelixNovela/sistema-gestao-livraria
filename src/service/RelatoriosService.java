package service;

import model.ItemVenda;
import model.Venda;
import model.Livro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


public class RelatoriosService {
    private VendaService vendaService;
    
    public RelatoriosService() {
        this.vendaService = new VendaService();
    }

    public List<Venda> getVendasPorPeriodo(Date dataInicio, Date dataFim) {
        LocalDate inicio = dataInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fim = dataFim.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        return vendaService.listarVendas().stream()
            .filter(venda -> {
                LocalDate dataVenda = venda.getData().toLocalDate();
                return !dataVenda.isBefore(inicio) && !dataVenda.isAfter(fim);
            })
            .collect(Collectors.toList());
    }
    
    // Extrair todos os itens de venda de um período
    public List<ItemVenda> getItensVendaPorPeriodo(Date dataInicio, Date dataFim) {
        List<Venda> vendasNoPeriodo = getVendasPorPeriodo(dataInicio, dataFim);
        List<ItemVenda> todosItens = new ArrayList<>();
        
        for (Venda venda : vendasNoPeriodo) {
            todosItens.addAll(venda.getItens());
        }
        
        return todosItens;
    }
  
    public List<Map.Entry<String, Integer>> getLivrosMaisVendidos(Date dataInicio, Date dataFim) {
        List<Venda> vendasNoPeriodo = getVendasPorPeriodo(dataInicio, dataFim);
        Map<String, Integer> qtdPorLivro = new HashMap<>();
        
        for (Venda venda : vendasNoPeriodo) {
            for (ItemVenda item : venda.getItens()) {
                String tituloLivro = item.getLivro().getTitulo();
                qtdPorLivro.put(tituloLivro, qtdPorLivro.getOrDefault(tituloLivro, 0) + item.getQuantidade());
            }
        }
        
        List<Map.Entry<String, Integer>> livrosOrdenados = new ArrayList<>(qtdPorLivro.entrySet());
        livrosOrdenados.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        
        return livrosOrdenados;
    }
    
    public List<ItemVenda> getItensPorCategoria(Date dataInicio, Date dataFim, String categoria) {
        List<Venda> vendasNoPeriodo = getVendasPorPeriodo(dataInicio, dataFim);
        List<ItemVenda> itensFiltrados = new ArrayList<>();
        
        for (Venda venda : vendasNoPeriodo) {
            for (ItemVenda item : venda.getItens()) {
                // Add only items that match the category or if "Todas" is selected
                if ("Todas".equals(categoria) || categoria.equals(item.getLivro().getCategoria())) {
                    itensFiltrados.add(item);
                }
            }
        }
        
        return itensFiltrados;
    }
    
    public Object[][] converterItensParaLinhasTabela(List<ItemVenda> itens) {
        Object[][] linhas = new Object[itens.size()][7];
        
        for (int i = 0; i < itens.size(); i++) {
            ItemVenda item = itens.get(i);
            Livro livro = item.getLivro();
            double total = livro.getPreco() * item.getQuantidade();
            
            linhas[i][0] = livro.getIsbn();
            linhas[i][1] = livro.getTitulo();
            linhas[i][2] = livro.getAutor();
            linhas[i][3] = livro.getCategoria();
            linhas[i][4] = String.format("MT %.2f", livro.getPreco());
            linhas[i][5] = item.getQuantidade();
            linhas[i][6] = String.format("MT %.2f", total);
        }
        
        return linhas;
    }
    
    public Object[][] converterLivrosMaisVendidosParaLinhasTabela(List<Map.Entry<String, Integer>> livrosMaisVendidos) {
        List<Object[]> linhasValidas = new ArrayList<>();
        
        for (Map.Entry<String, Integer> entry : livrosMaisVendidos) {
            String titulo = entry.getKey();
            Integer quantidade = entry.getValue();
            
            // Só inclui livros com quantidade maior que 5
            if (quantidade > 5) {
                // Encontrar o primeiro livro com este título
                Livro livroEncontrado = null;
                
                List<Venda> vendas = vendaService.listarVendas();
                for (Venda venda : vendas) {
                    for (ItemVenda item : venda.getItens()) {
                        if (titulo.equals(item.getLivro().getTitulo())) {
                            livroEncontrado = item.getLivro();
                            break;
                        }
                    }
                    if (livroEncontrado != null) break;
                }
                
                if (livroEncontrado != null) {
                    Object[] linha = new Object[7];
                    linha[0] = livroEncontrado.getIsbn();
                    linha[1] = titulo;
                    linha[2] = livroEncontrado.getAutor();
                    linha[3] = livroEncontrado.getCategoria();
                    linha[4] = String.format("MT %.2f", livroEncontrado.getPreco());
                    linha[5] = quantidade;
                    linha[6] = String.format("MT %.2f", livroEncontrado.getPreco() * quantidade);
                    
                    linhasValidas.add(linha);
                }
            }
        }
        
        return linhasValidas.toArray(new Object[0][0]);
    }
}