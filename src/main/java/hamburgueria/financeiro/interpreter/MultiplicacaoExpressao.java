package hamburgueria.financeiro.interpreter;

public class MultiplicacaoExpressao implements IExpressao {
    private final IExpressao esquerda;
    private final IExpressao direita;

    public MultiplicacaoExpressao(IExpressao esquerda, IExpressao direita) {
        this.esquerda = esquerda;
        this.direita = direita;
    }

    @Override
    public double interpretar(IContextoMatematico contexto) {
        return esquerda.interpretar(contexto) * direita.interpretar(contexto);
    }
}