package hamburgueria.financeiro.proxy;

import hamburgueria.financeiro.bridge.MetodoPagamentoAbstraction;

public interface IGatewayPagamento {
    boolean cobrar(MetodoPagamentoAbstraction metodo, double valor);
}