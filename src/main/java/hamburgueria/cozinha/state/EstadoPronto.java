package hamburgueria.cozinha.state;

public class EstadoPronto implements IEstadoPedido {
    @Override
    public void iniciarPreparo(PedidoCozinha pedido) {
        throw new IllegalStateException("Pedido já está pronto.");
    }

    @Override
    public void finalizarPreparo(PedidoCozinha pedido) {
        throw new IllegalStateException("Pedido já foi finalizado.");
    }

    @Override
    public void despachar(PedidoCozinha pedido) {
        pedido.mudarEstado(new EstadoDespachado());
    }

    @Override
    public void cancelar(PedidoCozinha pedido) {
        throw new IllegalStateException("Pedido pronto não pode ser cancelado.");
    }

    @Override
    public String getIdentificadorEstado() {
        return "PRONTO";
    }
}