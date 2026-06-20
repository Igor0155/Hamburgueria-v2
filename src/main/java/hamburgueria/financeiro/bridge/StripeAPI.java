package hamburgueria.financeiro.bridge;

public class StripeAPI implements IProcessadorAPI {
    @Override
    public boolean processarTransacaoExterna(double valor) {
        return valor > 0; // Simulação de sucesso da API
    }
}