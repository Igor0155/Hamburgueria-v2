package hamburgueria.financeiro.proxy;

import hamburgueria.financeiro.bridge.MetodoPagamentoAbstraction;

public class GatewayReal implements IGatewayPagamento {
    private final MetodoPagamentoAbstraction metodoPagamento;

    public GatewayReal(MetodoPagamentoAbstraction metodoPagamento) {
        if (metodoPagamento == null) {
            throw new IllegalArgumentException("Método de pagamento não pode ser nulo no Gateway.");
        }
        this.metodoPagamento = metodoPagamento;
    }

    @Override
    public boolean cobrar(double valor) {
        return this.metodoPagamento.efetuarPagamento(valor);
    }
}