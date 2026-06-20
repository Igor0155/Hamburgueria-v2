package hamburgueria.cozinha.state;

public class EstadoDespachado implements IEstadoPedido {
    @Override
    public void iniciarPreparo(PedidoCozinha pedido) {
        throw new IllegalStateException("Transição inválida.");
    }

    @Override
    public void finalizarPreparo(PedidoCozinha pedido) {
        throw new IllegalStateException("Transição inválida.");
    }

    @Override
    public void despachar(PedidoCozinha pedido) {
        throw new IllegalStateException("Transição inválida.");
    }

    @Override
    public void cancelar(PedidoCozinha pedido) {
        throw new IllegalStateException("Transição inválida.");
    }

    @Override
    public String getIdentificadorEstado() {
        return "DESPACHADO";
    }
}