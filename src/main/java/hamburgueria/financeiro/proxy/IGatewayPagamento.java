package hamburgueria.financeiro.proxy;

public interface IGatewayPagamento {
    boolean cobrar(double valor);
}