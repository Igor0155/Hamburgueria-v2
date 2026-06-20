package hamburgueria.carrinho.command;

import hamburgueria.carrinho.CarrinhoCompras;
import hamburgueria.catalogo.factory.IHamburguer;

public class AdicionarItemCommand implements ICarrinhoCommand {
    private final CarrinhoCompras carrinho;
    private final IHamburguer item;

    public AdicionarItemCommand(CarrinhoCompras carrinho, IHamburguer item) {
        this.carrinho = carrinho;
        this.item = item;
    }

    @Override
    public void executar() {
        carrinho.adicionarItem(item);
    }

    @Override
    public void desfazer() {
        carrinho.removerItem(item);
    }
}