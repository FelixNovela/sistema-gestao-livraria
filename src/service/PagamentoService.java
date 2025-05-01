package service;

import model.Pagamento;
import model.enums.TipoPagamento;

public class PagamentoService {

    public Pagamento processarPagamento(String tipo, double total, double valorPago) {
        if (valorPago < total) {
            return null;  
        } 

        double troco = valorPago - total;
        return new Pagamento(TipoPagamento.valueOf(tipo), valorPago, troco); 
    }
}
