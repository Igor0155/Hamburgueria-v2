package hamburgueria.checkout.facade;

import hamburgueria.carrinho.CarrinhoCompras;
import hamburgueria.catalogo.factory.IHamburguer;
import hamburgueria.cozinha.factorymethod.TipoHamburguer;
import hamburgueria.cozinha.singleton.EventBus;
import hamburgueria.cozinha.singleton.EventoPedidoPago;
import hamburgueria.cozinha.state.PedidoCozinha;
import hamburgueria.financeiro.proxy.IGatewayPagamento;
import hamburgueria.financeiro.strategy.IEstrategiaPrecificacao;

public class PedidoFacade {
    public boolean finalizarPedido(CarrinhoCompras carrinho,
            IEstrategiaPrecificacao estrategia,
            IGatewayPagamento gateway) {

        if (carrinho == null || carrinho.getItens().isEmpty()) {
            throw new IllegalStateException("O carrinho está vazio.");
        }

        if (gateway == null) {
            throw new IllegalArgumentException("Gateway não configurado.");
        }

        double subtotal = carrinho.getSubtotal();
        double valorFinal = estrategia.calcularTotal(subtotal);
        boolean pago = gateway.cobrar(valorFinal);

        if (pago) {
            PedidoCozinha pedidoCozinha = new PedidoCozinha("PED-" + System.nanoTime());

            // Repassa os tipos de alimento (Ponte do Grupo 3)
            for (IHamburguer item : carrinho.getItens()) {
                pedidoCozinha.adicionarAlimentoParaPreparo(new TipoHamburguer());
            }

            // PONTE DO GRUPO 4: Aciona o EventBus
            EventBus.getInstancia().publicar(new EventoPedidoPago(pedidoCozinha));
            carrinho.esvaziar();
        }
        return pago;
    }
}