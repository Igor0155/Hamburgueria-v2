package hamburgueria.cozinha.chain;

import hamburgueria.cozinha.state.PedidoCozinha;

public class VerificadorEmbalagem extends BaseVerificadorQualidade {
    @Override
    public void avaliar(PedidoCozinha pedido) {
        if (!(pedido.getEstadoAtual().getIdentificadorEstado().equals("PRONTO"))) {
            throw new IllegalStateException("Não é possível aprovar embalagem de pedido incompleto.");
        }
        repassar(pedido);
    }
}