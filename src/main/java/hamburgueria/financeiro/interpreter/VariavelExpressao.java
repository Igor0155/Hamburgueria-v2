package hamburgueria.financeiro.interpreter;

public class VariavelExpressao implements IExpressao {
    private final String nome;

    public VariavelExpressao(String nome) {
        this.nome = nome;
    }

    @Override
    public double interpretar(IContextoMatematico contexto) {
        return contexto.obterValor(this.nome);
    }
}