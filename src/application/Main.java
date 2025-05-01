package application;

import model.Cliente;
import model.ItemVenda;
import model.Livro;
import model.Venda;
import model.enums.TipoDocumento;
import model.enums.TipoPagamento;
import service.ClienteService;
import service.LivroService;
import service.VendaService;

public class Main {
    public static void main(String[] args) {
        LivroService lv = new LivroService();
      
        lv.listarTodos();
        for(Livro lista : lv.listarTodos()) {
        	System.out.println(lista);
        }
        
        LivroService ls = new LivroService();
//        ls.cadastrarLivro(new Livro( "1939449","Bleach", "Ichigo","Mappa", 120, 4));
//        ls.cadastrarLivro(new Livro( "2738494","Black cover", "Asta","Kira", 100, 2));
//        ls.cadastrarLivro(new Livro( "8393263","Solo leveling", "Jin hoo","Mappa", 160, 6));
//        
        //Cliente cl = new Cliente(1, "teste", "sgss", TipoDocumento.valueOf("BI"), "13131");
        ClienteService cls = new ClienteService();
       // cls.cadastrarCliente(cl);
        
        
        for(Cliente lista : cls.listarClientes()) {
        	System.out.println(lista);
        }
        
        VendaService vds = new VendaService();
       // vds.venderLivro(1, 1, "23", 1, "DINHEIRO", 7);
       
        
        for(Venda lista :  vds.listarVendas()) {
        	System.out.println(lista);
        }
        
//        System.out.println();
//        System.out.println();
//        VendaService vds1 = new VendaService();
//        vds1.adicionarLivroNaListaItemVenda("1939449", 2);
//        vds1.adicionarLivroNaListaItemVenda("2738494", 1);
//       
//        
//        System.out.println();
//        System.out.println();
//        for(ItemVenda l : vds1.listaItemVenda) {
//        	System.out.println(l);
//        }
//        System.out.println(vds1.calcularValorTotalAPagar());
//        vds1.efetuarPagamento("DINHEIRO", 300, 1);
//        
//        
//        VendaService vds2 = new VendaService();
//        vds2.adicionarLivroNaListaItemVenda("8393263", 3);
//        vds2.adicionarLivroNaListaItemVenda("2738494", 1);
//       
//        
//        System.out.println();
//        System.out.println();
//        
//        for(ItemVenda l : vds2.listaItemVenda) {
//        	System.out.println(l);
//        }
//        System.out.println(vds2.calcularValorTotalAPagar());
//        vds1.efetuarPagamento("DINHEIRO", 580, 1);
//        System.out.println();
        
    }
}

