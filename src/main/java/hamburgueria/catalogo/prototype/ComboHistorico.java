package hamburgueria.catalogo.prototype;

public class ComboHistorico implements IPrototipoCombo {
    private String nomeCombo;
    private double precoTotal;
    private String hashMontagemComplexa;

    public ComboHistorico(String nomeCombo, double precoTotal, String hashMontagemComplexa) {
        this.nomeCombo = nomeCombo;
        this.precoTotal = precoTotal;
        this.hashMontagemComplexa = hashMontagemComplexa;
    }

    @Override
    public IPrototipoCombo clonar() {
        // Clonagem exata evitando recálculos pesados de montagem
        return new ComboHistorico(this.nomeCombo, this.precoTotal, this.hashMontagemComplexa);
    }

    @Override
    public String getNomeCombo() {
        return this.nomeCombo;
    }

    @Override
    public void setNomeCombo(String nome) {
        this.nomeCombo = nome;
    }

    @Override
    public double getPrecoTotal() {
        return this.precoTotal;
    }

    @Override
    public void setPrecoTotal(double preco) {
        this.precoTotal = preco;
    }

    public String getHashMontagemComplexa() {
        return this.hashMontagemComplexa;
    }
}