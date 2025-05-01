package service;

import model.Pagamento;
import model.enums.TipoPagamento;

public class PagamentoService {

    public Pagamento processarPagamento(String tipo, double totalDaVenda, double valorPago) {
        if (valorPago < totalDaVenda) {
            return null;   
        } 

        double troco = valorPago - totalDaVenda;
        return new Pagamento(TipoPagamento.valueOf(tipo.toUpperCase()), valorPago, troco); 
    }
}
