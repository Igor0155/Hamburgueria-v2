package hamburgueria.catalogo.factory;

public class ComboArtesanalFactory implements IComboFactory {

    @Override
    public IHamburguer criarHamburguer() {
        return new HamburguerArtesanal();
    }

    @Override
    public IAcompanhamento criarAcompanhamento() {
        return new BatataRustica();
    }
}