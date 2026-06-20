package hamburgueria.catalogo.composite;

import hamburgueria.relatorios.visitor.IVisitavel;

public interface IItemCardapio extends IVisitavel {
    String getNome();

    double getPreco();

    void adicionar(IItemCardapio item);

    void remover(IItemCardapio item);
}