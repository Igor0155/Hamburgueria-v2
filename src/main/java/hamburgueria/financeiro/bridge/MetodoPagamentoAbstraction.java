package hamburgueria.financeiro.bridge;

public abstract class MetodoPagamentoAbstraction {
    protected final IProcessadorAPI processadorAPI;

    protected MetodoPagamentoAbstraction(IProcessadorAPI processadorAPI) {
        if (processadorAPI == null)
            throw new IllegalArgumentException("Processador API obrigatório.");
        this.processadorAPI = processadorAPI;
    }

    public abstract boolean efetuarPagamento(double valorFinal);
}