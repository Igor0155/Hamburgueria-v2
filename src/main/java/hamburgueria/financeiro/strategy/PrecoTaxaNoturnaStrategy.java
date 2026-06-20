package hamburgueria.financeiro.strategy;

import hamburgueria.financeiro.interpreter.ContextoMatematicoReal;
import hamburgueria.financeiro.interpreter.IContextoMatematico;
import hamburgueria.financeiro.interpreter.IExpressao;
import hamburgueria.financeiro.interpreter.MultiplicacaoExpressao;
import hamburgueria.financeiro.interpreter.NumeroExpressao;
import hamburgueria.financeiro.interpreter.VariavelExpressao;

public class PrecoTaxaNoturnaStrategy implements IEstrategiaPrecificacao {
    @Override
    public double calcularTotal(double subtotal) {
        if (subtotal < 0)
            throw new IllegalArgumentException("Subtotal não pode ser negativo");

        IContextoMatematico contexto = new ContextoMatematicoReal();
        contexto.definirValor("PRECO", subtotal);

        // Equação Estrutural: PRECO * 1.2
        IExpressao equacao = new MultiplicacaoExpressao(
                new VariavelExpressao("PRECO"),
                new NumeroExpressao(1.2));

        return equacao.interpretar(contexto);
    }
}