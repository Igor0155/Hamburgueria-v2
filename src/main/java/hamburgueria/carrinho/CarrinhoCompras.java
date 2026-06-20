package hamburgueria.carrinho;

import java.util.ArrayList;
import java.util.List;

import hamburgueria.carrinho.memento.CarrinhoMemento;
import hamburgueria.catalogo.factory.IHamburguer;

public class CarrinhoCompras {
    private List<IHamburguer> itens = new ArrayList<>();

    public void adicionarItem(IHamburguer item) {
        this.itens.add(item);
    }

    public void removerItem(IHamburguer item) {
        if (!this.itens.contains(item)) {
            throw new IllegalArgumentException("Item não encontrado no carrinho.");
        }
        this.itens.remove(item);
    }

    public List<IHamburguer> getItens() {
        return this.itens;
    }

    public void esvaziar() {
        this.itens.clear();
    }

    public double getSubtotal() {
        double total = 0;
        for (IHamburguer item : itens) {
            total += item.getCusto();
        }
        return total;
    }

    // Memento Originator Methods
    public CarrinhoMemento salvarEstado() {
        return new CarrinhoMemento(this.itens);
    }

    public void restaurarEstado(CarrinhoMemento memento) {
        this.itens = new ArrayList<>(memento.getEstadoSalvo());
    }
}