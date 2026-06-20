package hamburgueria.financeiro.interpreter;

public class NumeroExpressao implements IExpressao {
    private final double numero;

    public NumeroExpressao(double numero) {
        this.numero = numero;
    }

    @Override
    public double interpretar(IContextoMatematico contexto) {
        return this.numero;
    }
}