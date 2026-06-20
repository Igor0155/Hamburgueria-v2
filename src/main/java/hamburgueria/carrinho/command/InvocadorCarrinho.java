package hamburgueria.carrinho.command;

import java.util.Stack;

public class InvocadorCarrinho {
    private final Stack<ICarrinhoCommand> historicoComandos = new Stack<>();

    public void executarComando(ICarrinhoCommand comando) {
        comando.executar();
        historicoComandos.push(comando);
    }

    public void desfazerUltimoComando() {
        if (!historicoComandos.isEmpty()) {
            ICarrinhoCommand comando = historicoComandos.pop();
            comando.desfazer();
        } else {
            throw new IllegalStateException("Não há comandos para desfazer.");
        }
    }
}