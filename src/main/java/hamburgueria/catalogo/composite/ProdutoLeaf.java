package hamburgueria.catalogo.composite;

import hamburgueria.catalogo.flyweight.IngredienteDetalhe;
import hamburgueria.catalogo.flyweight.IngredienteFlyweightFactory;
import hamburgueria.relatorios.visitor.IVisitorCardapio;
import hamburgueria.relatorios.visitor.ItemSimplesVisitavel;

public class ProdutoLeaf implements IItemCardapio {
    private final String nome;
    private final double preco;
    private final IngredienteDetalhe detalheNutricional;

    public ProdutoLeaf(String nome, double preco, String nomeIngredienteBase) {
        this.nome = nome;
        this.preco = preco;
        this.detalheNutricional = IngredienteFlyweightFactory.obterDetalhe(nomeIngredienteBase);
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public double getPreco() {
        return this.preco;
    }

    public int getCaloriasTotais() {
        return this.detalheNutricional.getCalorias();
    }

    @Override
    public void adicionar(IItemCardapio item) {
        throw new UnsupportedOperationException("Não é possível adicionar itens a uma folha (Produto).");
    }

    @Override
    public void remover(IItemCardapio item) {
        throw new UnsupportedOperationException("Não é possível remover itens de uma folha (Produto).");
    }

    @Override
    public void aceitar(IVisitorCardapio visitor) {
        visitor.visitarItemSimples(new ItemSimplesVisitavel(this.preco * 0.4, this.preco));
    }
}