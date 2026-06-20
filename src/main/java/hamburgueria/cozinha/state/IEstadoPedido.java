package hamburgueria.cozinha.state;

public interface IEstadoPedido {
    void iniciarPreparo(PedidoCozinha pedido);

    void finalizarPreparo(PedidoCozinha pedido);

    void despachar(PedidoCozinha pedido);

    void cancelar(PedidoCozinha pedido);

    String getIdentificadorEstado();
}