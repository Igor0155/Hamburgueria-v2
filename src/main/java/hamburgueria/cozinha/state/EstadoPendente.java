package hamburgueria.cozinha.state;

public class EstadoPendente implements IEstadoPedido {
    @Override
    public void iniciarPreparo(PedidoCozinha pedido) {
        pedido.mudarEstado(new EstadoEmPreparo());
    }

    @Override
    public void finalizarPreparo(PedidoCozinha pedido) {
        throw new IllegalStateException("Pedido ainda não começou a ser preparado.");
    }

    @Override
    public void despachar(PedidoCozinha pedido) {
        throw new IllegalStateException("Pedido pendente não pode ser despachado.");
    }

    @Override
    public void cancelar(PedidoCozinha pedido) {
        pedido.mudarEstado(new EstadoCancelado());
    }

    @Override
    public String getIdentificadorEstado() {
        return "PENDENTE";
    }
}