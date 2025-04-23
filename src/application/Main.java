package application;

import model.Cliente;
import model.Livro;
import model.Venda;
import model.enums.TipoDocumento;
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
        
    }
}

