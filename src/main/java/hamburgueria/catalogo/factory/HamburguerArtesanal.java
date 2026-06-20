package hamburgueria.catalogo.factory;

public class HamburguerArtesanal implements IHamburguer {
    @Override
    public String getDescricao() {
        return "Hambúrguer Artesanal 200g";
    }

    @Override
    public double getCusto() {
        return 35.0;
    }
}