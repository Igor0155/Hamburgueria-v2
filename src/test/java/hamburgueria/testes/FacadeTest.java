package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.carrinho.CarrinhoCompras;
import hamburgueria.catalogo.factory.HamburguerArtesanal;
import hamburgueria.checkout.facade.PedidoFacade;
import hamburgueria.cozinha.singleton.EventBus;
import hamburgueria.financeiro.bridge.PagamentoPix;
import hamburgueria.financeiro.bridge.StripeAPI;
import hamburgueria.financeiro.proxy.GatewayReal;
import hamburgueria.financeiro.strategy.PrecoTaxaNoturnaStrategy;

public class FacadeTest {
    private PedidoFacade facade;
    private CarrinhoCompras carrinho;

    @BeforeEach
    public void setup() {
        facade = new PedidoFacade();
        carrinho = new CarrinhoCompras();
    }

    @Test
    public void deveFinalizarPedidoComSucesso() {
        carrinho.adicionarItem(new HamburguerArtesanal());
        assertTrue(facade.finalizarPedido(carrinho, new PrecoTaxaNoturnaStrategy(), new GatewayReal(),
                new PagamentoPix(new StripeAPI())));
    }

    @Test
    public void deveRecusarPedidoComCarrinhoVazio() {
        assertThrows(IllegalStateException.class, () -> facade.finalizarPedido(carrinho, new PrecoTaxaNoturnaStrategy(),
                new GatewayReal(), new PagamentoPix(new StripeAPI())));
    }

    @Test
    public void deveRecusarPedidoComCarrinhoNulo() {
        assertThrows(IllegalStateException.class, () -> facade.finalizarPedido(null, new PrecoTaxaNoturnaStrategy(),
                new GatewayReal(), new PagamentoPix(new StripeAPI())));
    }

    @Test
    public void deveLancarExcecaoSeGatewayNulo() {
        carrinho.adicionarItem(new HamburguerArtesanal());
        assertThrows(IllegalArgumentException.class, () -> facade.finalizarPedido(carrinho,
                new PrecoTaxaNoturnaStrategy(), null, new PagamentoPix(new StripeAPI())));
    }

    @Test
    public void deveEsvaziarCarrinhoAposSucesso() {
        carrinho.adicionarItem(new HamburguerArtesanal());
        facade.finalizarPedido(carrinho, new PrecoTaxaNoturnaStrategy(), new GatewayReal(),
                new PagamentoPix(new StripeAPI()));
        assertEquals(0, carrinho.getItens().size());
    }

    @Test
    public void deveManterCarrinhoSePagamentoFalhar() {
        carrinho.adicionarItem(new HamburguerArtesanal());
        PagamentoPix pixFalho = new PagamentoPix(v -> false);
        facade.finalizarPedido(carrinho, new PrecoTaxaNoturnaStrategy(), new GatewayReal(), pixFalho);
        assertEquals(1, carrinho.getItens().size());
    }

    @Test
    public void deveDispararEventoPedidoPagoAposSucessoNoPagamento() {
        EventBus.getInstancia().limparParaTestes();
        final boolean[] eventoRecebido = { false };
        EventBus.getInstancia().inscrever(e -> eventoRecebido[0] = e.getNomeEvento().equals("PEDIDO_PAGO"));

        carrinho.adicionarItem(new HamburguerArtesanal());
        facade.finalizarPedido(carrinho, new PrecoTaxaNoturnaStrategy(), new GatewayReal(),
                new PagamentoPix(new StripeAPI()));

        assertTrue(eventoRecebido[0]);
    }

    @Test
    public void deveRetornarFalsoSePagamentoFalhar() {
        carrinho.adicionarItem(new HamburguerArtesanal());
        PagamentoPix pixFalho = new PagamentoPix(v -> false);
        assertFalse(facade.finalizarPedido(carrinho, new PrecoTaxaNoturnaStrategy(), new GatewayReal(), pixFalho));
    }

    @Test
    public void deveGarantirUsoDaInterfaceFacade() {
        assertInstanceOf(PedidoFacade.class, facade);
    }
}