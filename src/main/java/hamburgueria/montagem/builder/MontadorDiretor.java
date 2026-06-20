package hamburgueria.montagem.builder;

public class MontadorDiretor {
    private IHamburguerBuilder builder;

    public void setBuilder(IHamburguerBuilder builder) {
        this.builder = builder;
    }

    public void construirHamburguerFit() {
        builder.reset();
        builder.escolherPao();
        builder.escolherProteina();
        builder.escolherQueijo();
        builder.definirPrecoBase();
    }
}