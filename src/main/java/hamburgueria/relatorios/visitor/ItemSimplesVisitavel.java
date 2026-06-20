package hamburgueria.relatorios.visitor;

public class ItemSimplesVisitavel implements IVisitavel {
    private final double custoProducao;
    private final double precoVenda;

    public ItemSimplesVisitavel(double custoProducao, double precoVenda) {
        this.custoProducao = custoProducao;
        this.precoVenda = precoVenda;
    }

    public double getCustoProducao() {
        return custoProducao;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    @Override
    public void aceitar(IVisitorCardapio visitor) {
        visitor.visitarItemSimples(this);
    }
}