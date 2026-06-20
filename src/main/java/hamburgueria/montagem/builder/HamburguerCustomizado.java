package hamburgueria.montagem.builder;

import hamburgueria.catalogo.factory.IHamburguer;

public class HamburguerCustomizado implements IHamburguer {
    private String pao = null;
    private String proteina = null;
    private String queijo = null;
    private double precoBase = 0.0;

    public void setPao(String pao) {
        this.pao = pao;
    }

    public void setProteina(String proteina) {
        this.proteina = proteina;
    }

    public void setQueijo(String queijo) {
        this.queijo = queijo;
    }

    public void setPrecoBase(double preco) {
        this.precoBase = preco;
    }

    @Override
    public String getDescricao() {
        return "Customizado: " + pao + ", " + proteina + ", " + queijo;
    }

    @Override
    public double getCusto() {
        return this.precoBase;
    }
}