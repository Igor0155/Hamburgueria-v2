package hamburgueria.carrinho.memento;

import java.util.Stack;

public class ZeladorCarrinho {
    private final Stack<CarrinhoMemento> historico = new Stack<>();

    public void salvar(CarrinhoMemento memento) {
        historico.push(memento);
    }

    public CarrinhoMemento restaurar() {
        if (historico.isEmpty()) {
            throw new IllegalStateException("Histórico de estados vazio.");
        }
        return historico.pop();
    }

    public int getTamanhoHistorico() {
        return historico.size();
    }
}