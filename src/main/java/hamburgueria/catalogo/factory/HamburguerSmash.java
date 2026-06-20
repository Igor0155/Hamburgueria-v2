package hamburgueria.catalogo.factory;

public class HamburguerSmash implements IHamburguer {
    @Override
    public String getDescricao() {
        return "Smash Duplo 90g";
    }

    @Override
    public double getCusto() {
        return 28.0;
    }
}