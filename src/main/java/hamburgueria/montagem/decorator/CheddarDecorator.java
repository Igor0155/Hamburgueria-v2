package hamburgueria.montagem.decorator;

import hamburgueria.catalogo.factory.IHamburguer;

public class CheddarDecorator extends IngredienteDecorator {
    public CheddarDecorator(IHamburguer hamburguerBase) {
        super(hamburguerBase);
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + " + Creme de Cheddar";
    }

    @Override
    public double getCusto() {
        return super.getCusto() + 4.5;
    }
}