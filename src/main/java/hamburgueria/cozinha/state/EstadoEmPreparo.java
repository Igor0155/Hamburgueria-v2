package hamburgueria.cozinha.state;

public class EstadoEmPreparo implements IEstadoPedido {
    @Override
    public void iniciarPreparo(PedidoCozinha pedido) {
        throw new IllegalStateException("O preparo já foi iniciado.");
    }

    @Override
    public void finalizarPreparo(PedidoCozinha pedido) {
        pedido.mudarEstado(new EstadoPronto());
    }

    @Override
    public void despachar(PedidoCozinha pedido) {
        throw new IllegalStateException("Pedido ainda em preparo, não pode despachar.");
    }

    @Override
    public void cancelar(PedidoCozinha pedido) {
        throw new IllegalStateException("Não é possível cancelar um pedido já na chapa. Desperdício.");
    }

    @Override
    public String getIdentificadorEstado() {
        return "EM_PREPARO";
    }
}