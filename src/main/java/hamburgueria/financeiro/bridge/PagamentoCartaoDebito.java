package hamburgueria.financeiro.bridge;

public class PagamentoCartaoDebito extends MetodoPagamentoAbstraction {

    public PagamentoCartaoDebito(IProcessadorAPI processadorAPI) {
        super(processadorAPI);
    }

    @Override
    public boolean efetuarPagamento(double valorFinal) {
        return this.processadorAPI.processarTransacaoExterna(valorFinal);
    }
}