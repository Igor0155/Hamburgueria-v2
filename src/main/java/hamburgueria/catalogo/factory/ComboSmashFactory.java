package hamburgueria.catalogo.factory;

public class ComboSmashFactory implements IComboFactory {
    @Override
    public IHamburguer criarHamburguer() {
        return new HamburguerSmash();
    }

    @Override
    public IAcompanhamento criarAcompanhamento() {
        return new BatataPalito();
    }
}