package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.cozinha.state.PedidoCozinha;

public class StateTest {
    private PedidoCozinha pedidoNormal;
    private PedidoCozinha pedidoExaustivo;

    @BeforeEach
    public void setup() {
        pedidoNormal = new PedidoCozinha("PEDIDO_01");
        pedidoExaustivo = new PedidoCozinha("PED-EXAUSTIVO");
    }

    @Test
    public void deveNascerNoEstadoPendente() {
        assertEquals("PENDENTE", pedidoNormal.getEstadoAtual().getIdentificadorEstado());
    }

    @Test
    public void deveTransitarDePendenteParaEmPreparo() {
        pedidoNormal.iniciarPreparo();
        assertEquals("EM_PREPARO", pedidoNormal.getEstadoAtual().getIdentificadorEstado());
    }

    @Test
    public void deveTransitarDePendenteParaCancelado() {
        pedidoNormal.cancelar();
        assertEquals("CANCELADO", pedidoNormal.getEstadoAtual().getIdentificadorEstado());
    }

    @Test
    public void naoDeveFinalizarPedidoPendente() {
        assertThrows(IllegalStateException.class, () -> pedidoNormal.finalizarPreparo());
    }

    @Test
    public void deveTransitarDeEmPreparoParaPronto() {
        pedidoNormal.iniciarPreparo();
        pedidoNormal.finalizarPreparo();
        assertEquals("PRONTO", pedidoNormal.getEstadoAtual().getIdentificadorEstado());
    }

    @Test
    public void naoDeveCancelarPedidoEmPreparo() {
        pedidoNormal.iniciarPreparo();
        assertThrows(IllegalStateException.class, () -> pedidoNormal.cancelar());
    }

    @Test
    public void deveTransitarDeProntoParaDespachado() {
        pedidoNormal.iniciarPreparo();
        pedidoNormal.finalizarPreparo();
        pedidoNormal.despachar();
        assertEquals("DESPACHADO", pedidoNormal.getEstadoAtual().getIdentificadorEstado());
    }

    @Test
    public void naoDeveRealizarAcaoAposDespachado() {
        pedidoNormal.iniciarPreparo();
        pedidoNormal.finalizarPreparo();
        pedidoNormal.despachar();
        assertThrows(IllegalStateException.class, () -> pedidoNormal.cancelar());
    }

    @Test
    public void naoDeveIniciarPreparoSeCancelado() {
        pedidoExaustivo.cancelar();
        assertThrows(IllegalStateException.class, () -> pedidoExaustivo.iniciarPreparo());
    }

    @Test
    public void naoDeveFinalizarPreparoSeCancelado() {
        pedidoExaustivo.cancelar();
        assertThrows(IllegalStateException.class, () -> pedidoExaustivo.finalizarPreparo());
    }

    @Test
    public void naoDeveDespacharSeCancelado() {
        pedidoExaustivo.cancelar();
        assertThrows(IllegalStateException.class, () -> pedidoExaustivo.despachar());
    }

    @Test
    public void naoDeveCancelarNovamenteSeJaCancelado() {
        pedidoExaustivo.cancelar();
        assertThrows(IllegalStateException.class, () -> pedidoExaustivo.cancelar());
    }

    @Test
    public void naoDeveIniciarPreparoSeJaDespachado() {
        pedidoExaustivo.iniciarPreparo();
        pedidoExaustivo.finalizarPreparo();
        pedidoExaustivo.despachar();
        assertThrows(IllegalStateException.class, () -> pedidoExaustivo.iniciarPreparo());
    }

    @Test
    public void naoDeveFinalizarPreparoSeJaDespachado() {
        pedidoExaustivo.iniciarPreparo();
        pedidoExaustivo.finalizarPreparo();
        pedidoExaustivo.despachar();
        assertThrows(IllegalStateException.class, () -> pedidoExaustivo.finalizarPreparo());
    }

    @Test
    public void naoDeveDespacharNovamenteSeJaDespachado() {
        pedidoExaustivo.iniciarPreparo();
        pedidoExaustivo.finalizarPreparo();
        pedidoExaustivo.despachar();
        assertThrows(IllegalStateException.class, () -> pedidoExaustivo.despachar());
    }

    @Test
    public void naoDeveCancelarSeJaDespachado() {
        pedidoExaustivo.iniciarPreparo();
        pedidoExaustivo.finalizarPreparo();
        pedidoExaustivo.despachar();
        assertThrows(IllegalStateException.class, () -> pedidoExaustivo.cancelar());
    }
}