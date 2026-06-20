package hamburgueria.relatorios.visitor;

public class CategoriaVisitavel implements IVisitavel {
    private final String nomeSetor;

    public CategoriaVisitavel(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    @Override
    public void aceitar(IVisitorCardapio visitor) {
        visitor.visitarCategoria(this);
    }
}