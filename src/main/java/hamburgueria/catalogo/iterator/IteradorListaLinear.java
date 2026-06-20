package hamburgueria.catalogo.iterator;

import java.util.List;

public class IteradorListaLinear implements IIteradorCardapio {
    private final List<Object> colecao;
    private int posicaoAtual = 0;

    public IteradorListaLinear(List<Object> colecao) {
        this.colecao = colecao;
    }

    @Override
    public boolean temProximo() {
        return posicaoAtual < colecao.size();
    }

    @Override
    public Object proximo() {
        if (!temProximo()) {
            throw new IndexOutOfBoundsException("Não há mais itens na coleção.");
        }
        Object item = colecao.get(posicaoAtual);
        posicaoAtual++;
        return item;
    }
}