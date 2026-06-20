package hamburgueria.financeiro.bridge;

public class PagamentoPix extends MetodoPagamentoAbstraction {
    public PagamentoPix(IProcessadorAPI processadorAPI) {
        super(processadorAPI);
    }

    @Override
    public boolean efetuarPagamento(double valorFinal) {
        // Lógica específica do Pix antes de bater na API externa
        return processadorAPI.processarTransacaoExterna(valorFinal);
    }
}