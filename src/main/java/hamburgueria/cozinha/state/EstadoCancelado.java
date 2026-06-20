package hamburgueria.cozinha.state;

public class EstadoCancelado implements IEstadoPedido {
    @Override
    public void iniciarPreparo(PedidoCozinha pedido) {
        throw new IllegalStateException("Pedido cancelado.");
    }

    @Override
    public void finalizarPreparo(PedidoCozinha pedido) {
        throw new IllegalStateException("Pedido cancelado.");
    }

    @Override
    public void despachar(PedidoCozinha pedido) {
        throw new IllegalStateException("Pedido cancelado.");
    }

    @Override
    public void cancelar(PedidoCozinha pedido) {
        throw new IllegalStateException("Pedido já está cancelado.");
    }

    @Override
    public String getIdentificadorEstado() {
        return "CANCELADO";
    }
}