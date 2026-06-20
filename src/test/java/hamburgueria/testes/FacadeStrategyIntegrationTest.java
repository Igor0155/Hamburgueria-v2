package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.carrinho.CarrinhoCompras;
import hamburgueria.catalogo.factory.HamburguerArtesanal;
import hamburgueria.checkout.facade.PedidoFacade;
import hamburgueria.financeiro.bridge.PagamentoPix;
import hamburgueria.financeiro.bridge.StripeAPI;
import hamburgueria.financeiro.proxy.GatewayReal;
import hamburgueria.financeiro.strategy.IEstrategiaPrecificacao;

public class FacadeStrategyIntegrationTest {
    private PedidoFacade facade;
    private CarrinhoCompras carrinho;
    private GatewayReal gateway;
    private PagamentoPix pix;

    @BeforeEach
    public void setup() {
        facade = new PedidoFacade();
        carrinho = new CarrinhoCompras();
        carrinho.adicionarItem(new HamburguerArtesanal());
        gateway = new GatewayReal();
        pix = new PagamentoPix(new StripeAPI());
    }

    @Test
    public void deveRecusarPedidoSeEstrategiaZerarOValorTotal() {
        IEstrategiaPrecificacao strategyDescontoTotal = subtotal -> 0.0;
        // A StripeAPI (via GatewayReal) recusa valores <= 0, então a Facade deve
        // retornar false
        assertFalse(facade.finalizarPedido(carrinho, strategyDescontoTotal, gateway, pix));
    }

    @Test
    public void deveLancarExcecaoSeEstrategiaForNulaNaFronteiraDaFacade() {
        assertThrows(NullPointerException.class, () -> facade.finalizarPedido(carrinho, null, gateway, pix));
    }

    @Test
    public void deveLancarExcecaoSeMetodoDePagamentoForNuloNaFronteiraDaFacade() {
        IEstrategiaPrecificacao strategyNormal = subtotal -> subtotal;
        assertThrows(NullPointerException.class, () -> facade.finalizarPedido(carrinho, strategyNormal, gateway, null));
    }

    @Test
    public void deveRecusarPedidoSeEstrategiaGerarValorNegativo() {
        IEstrategiaPrecificacao strategyBugada = subtotal -> -50.0;
        assertFalse(facade.finalizarPedido(carrinho, strategyBugada, gateway, pix));
    }

    @Test
    public void deveManterItensNoCarrinhoQuandoStrategyGeraValorRecusado() {
        IEstrategiaPrecificacao strategyZero = subtotal -> 0.0;
        facade.finalizarPedido(carrinho, strategyZero, gateway, pix);
        assertEquals(1, carrinho.getItens().size());
    }

    @Test
    public void deveManterItensNoCarrinhoQuandoPagamentoEhRecusadoDiretoNaAPI() {
        IEstrategiaPrecificacao strategyNormal = subtotal -> subtotal;
        PagamentoPix pixFalho = new PagamentoPix(v -> false); // Mock fail
        facade.finalizarPedido(carrinho, strategyNormal, gateway, pixFalho);
        assertEquals(1, carrinho.getItens().size());
    }

    @Test
    public void deveEsvaziarCarrinhoSeStrategyAumentarValorEPagamentoAprovar() {
        IEstrategiaPrecificacao strategyAcrescimo = subtotal -> subtotal + 100.0;
        facade.finalizarPedido(carrinho, strategyAcrescimo, gateway, pix);
        assertEquals(0, carrinho.getItens().size());
    }

    @Test
    public void deveRetornarVerdadeiroSeStrategyAplicarDescontoParcialAprovado() {
        IEstrategiaPrecificacao strategyDescontoParcial = subtotal -> subtotal * 0.5; // 50% off
        assertTrue(facade.finalizarPedido(carrinho, strategyDescontoParcial, gateway, pix));
    }
}