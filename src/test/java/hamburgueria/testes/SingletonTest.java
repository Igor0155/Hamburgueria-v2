package hamburgueria.testes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.cozinha.singleton.EventBus;
import hamburgueria.cozinha.singleton.EventoPedidoPago;
import hamburgueria.cozinha.singleton.IEvento;
import hamburgueria.cozinha.singleton.IListener;

import static org.junit.jupiter.api.Assertions.*;

public class SingletonTest {
    @BeforeEach
    public void setup() {
        EventBus.getInstancia().limparParaTestes();
    }

    @Test
    public void deveGarantirApenasUmaInstanciaDoEventBus() {
        assertSame(EventBus.getInstancia(), EventBus.getInstancia());
    }

    @Test
    public void deveAdicionarInscritoNoBarramento() {
        EventBus.getInstancia().inscrever(evento -> {
        });
        assertEquals(1, EventBus.getInstancia().getTotalInscritos());
    }

    @Test
    public void deveRemoverInscritoDoBarramento() {
        IListener ouvinte = evento -> {
        };
        EventBus.getInstancia().inscrever(ouvinte);
        EventBus.getInstancia().desinscrever(ouvinte);
        assertEquals(0, EventBus.getInstancia().getTotalInscritos());
    }

    @Test
    public void deveLimparTodosOsInscritosCorretamente() {
        EventBus.getInstancia().inscrever(evento -> {
        });
        EventBus.getInstancia().limparParaTestes();
        assertEquals(0, EventBus.getInstancia().getTotalInscritos());
    }

    @Test
    public void naoDeveDuplicarOuvinteComMesmaReferencia() {
        IListener ouvinte = evento -> {
        };
        EventBus.getInstancia().inscrever(ouvinte);
        EventBus.getInstancia().inscrever(ouvinte);
        assertEquals(1, EventBus.getInstancia().getTotalInscritos());
    }

    @Test
    public void deveNotificarOuvinteSobreEvento() {
        final boolean[] flag = { false };
        EventBus.getInstancia().inscrever(evento -> flag[0] = true);
        EventBus.getInstancia().publicar(new EventoPedidoPago(null));
        assertTrue(flag[0]);
    }

    @Test
    public void deveRecuperarCargaDeDadosDoEvento() {
        IEvento evento = new EventoPedidoPago("DADOS_MOCK");
        assertEquals("DADOS_MOCK", evento.getCargaDados());
    }

    @Test
    public void deveRecuperarNomeDoEvento() {
        IEvento evento = new EventoPedidoPago(null);
        assertEquals("PEDIDO_PAGO", evento.getNomeEvento());
    }
}