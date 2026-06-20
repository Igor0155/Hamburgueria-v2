package hamburgueria.carrinho.memento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hamburgueria.catalogo.factory.IHamburguer;

public class CarrinhoMemento {
    private final List<IHamburguer> estadoItens;

    public CarrinhoMemento(List<IHamburguer> itens) {
        this.estadoItens = new ArrayList<>(itens); // Cópia defensiva
    }

    public List<IHamburguer> getEstadoSalvo() {
        return Collections.unmodifiableList(estadoItens);
    }
}