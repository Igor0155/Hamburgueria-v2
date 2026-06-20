package hamburgueria.montagem.builder;

public class MonsterWheyBuilder implements IHamburguerBuilder {
    private HamburguerCustomizado hamburguer;

    public MonsterWheyBuilder() {
        this.reset();
    }

    @Override
    public void reset() {
        this.hamburguer = new HamburguerCustomizado();
    }

    @Override
    public void escolherPao() {
        this.hamburguer.setPao("Pão Integral Proteico");
    }

    @Override
    public void escolherProteina() {
        this.hamburguer.setProteina("Hamburguer de Frango 250g");
    }

    @Override
    public void escolherQueijo() {
        this.hamburguer.setQueijo("Queijo Minas Padrão");
    }

    @Override
    public void definirPrecoBase() {
        this.hamburguer.setPrecoBase(42.0);
    }

    @Override
    public HamburguerCustomizado obterResultado() {
        return this.hamburguer;
    }
}