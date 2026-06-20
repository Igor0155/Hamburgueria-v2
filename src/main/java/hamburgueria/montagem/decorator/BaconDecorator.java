package hamburgueria.montagem.decorator;

import hamburgueria.catalogo.factory.IHamburguer;

public class BaconDecorator extends IngredienteDecorator {
    public BaconDecorator(IHamburguer hamburguerBase) {
        super(hamburguerBase);
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + " + Extra Bacon";
    }

    @Override
    public double getCusto() {
        return super.getCusto() + 5.0;
    }
}