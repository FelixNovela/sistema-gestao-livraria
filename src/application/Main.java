package application;

import controller.LoginController;
import model.Funcionario;
import model.ItemVenda;
import model.Livro;
import model.Venda;
import model.enums.TipoDocumento;
import model.enums.TipoPagamento;
import service.FuncionarioService;
import service.LivroService;
import service.VendaService;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
    	LoginController loginController = new LoginController(new LoginView());
//        LivroService lv = new LivroService();
//      
//        lv.listarTodos();
//        for(Livro lista : lv.listarTodos()) {
//        	System.out.println(lista);
//        }
//        System.out.println();
//        
//        LivroService ls = new LivroService();
////        ls.adicionarLivro(new Livro("9788577876445", "Naruto", "Masashi Kishimoto", "Mangá", 1200, 18));
////        ls.adicionarLivro(new Livro("9788545702870", "Akira", "Katsuhiro Otomo", "Mangá", 650, 10));
////        ls.adicionarLivro(new Livro("9788573514709", "Watchmen", "Alan Moore", "Quadrinhos", 400, 8));
////        ls.adicionarLivro(new Livro("9788532530781", "V de Vingança", "Alan Moore", "Quadrinhos", 1000, 6));
////        ls.adicionarLivro(new Livro("9786555122151", "Fullmetal Alchemist", "Hiromu Arakawa", "Mangá",790.90, 12));
////        ls.adicionarLivro(new Livro("9788577874779", "Death Note", "Tsugumi Ohba", "Mangá", 700, 9));
////        ls.adicionarLivro(new Livro("9788582332106", "O Fim da Eternidade", "Isaac Asimov", "Ficção Científica", 420, 5));
////        ls.adicionarLivro(new Livro("9788535914847", "Neuromancer", "William Gibson", "Ficção Científica", 380.00, 7));
////        ls.adicionarLivro(new Livro("9788580443644", "Fundação", "Isaac Asimov", "Ficção Científica", 390.90, 10));
////        ls.adicionarLivro(new Livro("9781401285063", "Batman: A Piada Mortal", "Alan Moore", "Quadrinhos", 200.90, 6));
////        ls.adicionarLivro(new Livro("9788542629590", "One Piece ", "Eiichiro Oda", "Mangá", 990, 20));
////       
////        ls.adicionarLivro(new Livro("9788535909430", "2001: Uma Odisseia no Espaço", "Arthur C. Clarke", "Ficção Científica", 410.50, 7));
////        ls.adicionarLivro(new Livro("9788545702733", "Tokyo Ghoul", "Sui Ishida", "Mangá", 360.90, 14));
////        ls.adicionarLivro(new Livro("9788576573086", "Sandman", "Neil Gaiman", "Quadrinhos", 500, 5));
////        ls.adicionarLivro(new Livro("9788592795055", "O Guia do Mochileiro das Galáxias", "Douglas Adams", "Ficção Científica", 350, 11));
//
//        
//        //ls.atualizarEstoque("9780316066525", 30);
////        ls.cadastrarLivro(neLivro( "1939449","Bleach", "Ichigo","Mappa", 120, 4));
////        ls.cadastrarLivro(new Livro( "2738494","Black cover", "Asta","Kira", 100, 2));
////        ls.cadastrarLivro(new Livro( "8393263","Solo leveling", "Jin hoo","Mappa", 160, 6));
////        
//        //Funcionario cl = new Funcionario(1, "teste", "sgss", TipoDocumento.valueOf("BI"), "13131");
//       // FuncionarioService cls = new FuncionarioService();
//       // cls.cadastrarCliente(cl);
//        
////        
////        for(Funcionario lista : cls.listarClientes()) {
////        	System.out.println(lista);
////        }
//        
//        VendaService vds = new VendaService();
//       // vds.venderLivro(1, 1, "23", 1, "DINHEIRO", 7);
//       
//        
//        for(Venda lista :  vds.listarVendas()) {
//        	System.out.println(lista);
//        }
//        
////        System.out.println();
////        System.out.println();
////        VendaService vds1 = new VendaService();
////        vds1.adicionarLivroNaListaItemVenda("1939449", 2);
////        vds1.adicionarLivroNaListaItemVenda("2738494", 1);
////       
////        
////        System.out.println();
////        System.out.println();
////        for(ItemVenda l : vds1.listaItemVenda) {
////        	System.out.println(l);
////        }
////        System.out.println(vds1.calcularValorTotalAPagar());
////        vds1.efetuarPagamento("DINHEIRO", 300, 1);
////        
////        
////        VendaService vds2 = new VendaService();
////        vds2.adicionarLivroNaListaItemVenda("8393263", 3);
////        vds2.adicionarLivroNaListaItemVenda("2738494", 1);
////       
////        
////        System.out.println();
////        System.out.println();
////        
////        for(ItemVenda l : vds2.listaItemVenda) {
////        	System.out.println(l);
////        }
////        System.out.println(vds2.calcularValorTotalAPagar());
////        vds1.efetuarPagamento("DINHEIRO", 580, 1);
////        System.out.println();
        
    }
    
}

