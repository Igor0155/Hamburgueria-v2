package hamburgueria.financeiro.bridge;

public class PagamentoCartaoCredito extends MetodoPagamentoAbstraction {

    public PagamentoCartaoCredito(IProcessadorAPI processadorAPI) {
        super(processadorAPI);
    }

    @Override
    public boolean efetuarPagamento(double valorFinal) {
        return this.processadorAPI.processarTransacaoExterna(valorFinal);
    }
}