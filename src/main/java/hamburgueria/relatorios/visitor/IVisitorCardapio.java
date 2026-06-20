package hamburgueria.relatorios.visitor;

public interface IVisitorCardapio {
    void visitarItemSimples(ItemSimplesVisitavel item);

    void visitarCategoria(CategoriaVisitavel categoria);
}