package hamburgueria.cozinha.singleton;

public class EventoPedidoPago implements IEvento {
    private final Object dadosPedido;

    public EventoPedidoPago(Object dadosPedido) {
        this.dadosPedido = dadosPedido;
    }

    @Override
    public String getNomeEvento() {
        return "PEDIDO_PAGO";
    }

    @Override
    public Object getCargaDados() {
        return this.dadosPedido;
    }
}