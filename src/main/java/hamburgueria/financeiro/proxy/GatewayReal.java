package hamburgueria.financeiro.proxy;

import hamburgueria.financeiro.bridge.MetodoPagamentoAbstraction;

public class GatewayReal implements IGatewayPagamento {
    @Override
    public boolean cobrar(MetodoPagamentoAbstraction metodo, double valor) {
        return metodo.efetuarPagamento(valor);
    }
}