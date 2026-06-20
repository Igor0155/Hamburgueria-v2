package hamburgueria.cozinha.mediator;

import hamburgueria.cozinha.singleton.IListener;
import hamburgueria.cozinha.state.PedidoCozinha;

public interface ICozinhaMediator extends IListener {
    void orquestrarNovoPedido(PedidoCozinha pedido);

    void submeterParaControleQualidade(PedidoCozinha pedido);
}