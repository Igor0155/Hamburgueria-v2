package hamburgueria.financeiro.proxy;

public class GatewayPagamentoProxy implements IGatewayPagamento {
    private final IGatewayPagamento gatewayReal;
    private int tentativas = 0;

    public GatewayPagamentoProxy(IGatewayPagamento gatewayReal) {
        this.gatewayReal = gatewayReal;
    }

    @Override
    public boolean cobrar(double valor) {
        if (valor <= 0)
            throw new IllegalArgumentException("Valor inválido para cobrança.");
        if (tentativas >= 3)
            throw new SecurityException("Muitas tentativas falhas. Bloqueado por fraude.");

        boolean sucesso = gatewayReal.cobrar(valor);
        if (!sucesso) {
            tentativas++;
        } else {
            tentativas = 0; // Reseta após sucesso
        }
        return sucesso;
    }
}