package hamburgueria.catalogo.factory;

public class ComboAgregado implements IHamburguer {
    private final IHamburguer hamburguerPrincipal;
    private final IAcompanhamento acompanhamento;
    private final double valorAdicionalAcompanhamento;

    public ComboAgregado(IHamburguer hamburguerPrincipal, IAcompanhamento acompanhamento,
            double valorAdicionalAcompanhamento) {
        if (hamburguerPrincipal == null || acompanhamento == null) {
            throw new IllegalArgumentException("Combo deve possuir hambúrguer e acompanhamento.");
        }
        if (valorAdicionalAcompanhamento < 0) {
            throw new IllegalArgumentException("Valor do acompanhamento no combo não pode ser negativo.");
        }
        this.hamburguerPrincipal = hamburguerPrincipal;
        this.acompanhamento = acompanhamento;
        this.valorAdicionalAcompanhamento = valorAdicionalAcompanhamento;
    }

    @Override
    public String getDescricao() {
        return "COMBO: " + hamburguerPrincipal.getDescricao() + " + " + acompanhamento.getTipoPorcao();
    }

    @Override
    public double getCusto() {
        return hamburguerPrincipal.getCusto() + this.valorAdicionalAcompanhamento;
    }

    public IHamburguer getHamburguerPrincipal() {
        return this.hamburguerPrincipal;
    }

    public IAcompanhamento getAcompanhamento() {
        return this.acompanhamento;
    }
}