package hamburgueria.catalogo.flyweight;

public class IngredienteDetalhe {
    private final String imagemAltaResolucaoBase64;
    private final String tabelaNutricional;
    private final int calorias;

    public IngredienteDetalhe(String imagemAltaResolucaoBase64, String tabelaNutricional, int calorias) {
        this.imagemAltaResolucaoBase64 = imagemAltaResolucaoBase64;
        this.tabelaNutricional = tabelaNutricional;
        this.calorias = calorias;
    }

    public String getImagemAltaResolucaoBase64() {
        return imagemAltaResolucaoBase64;
    }

    public String getTabelaNutricional() {
        return tabelaNutricional;
    }

    public int getCalorias() {
        return calorias;
    }
}