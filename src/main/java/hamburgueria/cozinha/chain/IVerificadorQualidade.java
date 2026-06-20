package hamburgueria.cozinha.chain;

import hamburgueria.cozinha.state.PedidoCozinha;

public interface IVerificadorQualidade {
    void setProximo(IVerificadorQualidade proximoElo);

    void avaliar(PedidoCozinha pedido);
}