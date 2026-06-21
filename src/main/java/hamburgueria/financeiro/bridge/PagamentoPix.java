package hamburgueria.financeiro.bridge;

public class PagamentoPix extends MetodoPagamentoAbstraction {
    public PagamentoPix(IProcessadorAPI processadorAPI) {
        super(processadorAPI);
    }

    @Override
    public boolean efetuarPagamento(double valorFinal) {
        return processadorAPI.processarTransacaoExterna(valorFinal);
    }
}