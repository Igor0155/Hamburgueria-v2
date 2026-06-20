package hamburgueria.catalogo.composite;

import java.util.ArrayList;
import java.util.List;

import hamburgueria.relatorios.visitor.CategoriaVisitavel;
import hamburgueria.relatorios.visitor.IVisitorCardapio;

public class CategoriaComposite implements IItemCardapio {
    private final String nome;
    private final List<IItemCardapio> itens;

    public CategoriaComposite(String nome) {
        this.nome = nome;
        this.itens = new ArrayList<>();
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public double getPreco() {
        double precoTotal = 0.0;
        for (IItemCardapio item : itens) {
            precoTotal += item.getPreco();
        }
        return precoTotal;
    }

    @Override
    public void adicionar(IItemCardapio item) {
        this.itens.add(item);
    }

    @Override
    public void remover(IItemCardapio item) {
        this.itens.remove(item);
    }

    @Override
    public void aceitar(IVisitorCardapio visitor) {
        visitor.visitarCategoria(new CategoriaVisitavel(this.nome));
        for (IItemCardapio item : itens) {
            item.aceitar(visitor);
        }
    }
}