package hamburgueria.catalogo.iterator;

import java.util.ArrayList;
import java.util.List;

public class ColecaoItens {
    private final List<Object> itens = new ArrayList<>();

    public void adicionarItem(Object item) {
        this.itens.add(item);
    }

    public IIteradorCardapio criarIteradorPadrao() {
        return new IteradorListaLinear(this.itens);
    }
}