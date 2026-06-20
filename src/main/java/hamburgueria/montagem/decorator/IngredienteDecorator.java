package hamburgueria.montagem.decorator;

import hamburgueria.catalogo.factory.IHamburguer;

public abstract class IngredienteDecorator implements IHamburguer {
    protected final IHamburguer hamburguerBase;

    public IngredienteDecorator(IHamburguer hamburguerBase) {
        this.hamburguerBase = hamburguerBase;
    }

    @Override
    public String getDescricao() {
        return hamburguerBase.getDescricao();
    }

    @Override
    public double getCusto() {
        return hamburguerBase.getCusto();
    }
}