package hamburgueria.cozinha.chain;

import hamburgueria.cozinha.state.PedidoCozinha;

public class VerificadorApresentacao extends BaseVerificadorQualidade {
    @Override
    public void avaliar(PedidoCozinha pedido) {
        if (pedido.getIdentificador() == null)
            throw new IllegalStateException("Apresentação reprovada. Ausência de rótulo.");
        repassar(pedido);
    }
}