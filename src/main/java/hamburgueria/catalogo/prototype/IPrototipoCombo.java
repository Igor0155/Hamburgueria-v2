package hamburgueria.catalogo.prototype;

public interface IPrototipoCombo {
    IPrototipoCombo clonar();

    String getNomeCombo();

    void setNomeCombo(String nome);

    double getPrecoTotal();

    void setPrecoTotal(double preco);
}