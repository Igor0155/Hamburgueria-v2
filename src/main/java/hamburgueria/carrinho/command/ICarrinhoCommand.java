package hamburgueria.carrinho.command;

public interface ICarrinhoCommand {
    void executar();

    void desfazer();
}