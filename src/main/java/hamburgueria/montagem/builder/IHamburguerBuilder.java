package hamburgueria.montagem.builder;

public interface IHamburguerBuilder {
    void reset();

    void escolherPao();

    void escolherProteina();

    void escolherQueijo();

    void definirPrecoBase();

    HamburguerCustomizado obterResultado();
}