package hamburgueria.cozinha.chain;

import hamburgueria.cozinha.state.PedidoCozinha;

public class VerificadorTemperatura extends BaseVerificadorQualidade {
    @Override
    public void avaliar(PedidoCozinha pedido) {
        if (pedido == null)
            throw new IllegalArgumentException("Avaliação nula falhou.");
        // Mock de verificação: se o pedido for válido, passa.
        repassar(pedido);
    }
}