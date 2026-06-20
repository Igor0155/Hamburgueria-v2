package hamburgueria.testes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import hamburgueria.integracao.adapter.*;

public class AdapterTest {
    private ApiExternaLoggi apiExterna;
    private AdaptadorLoggi adaptador;

    @BeforeEach
    public void setup() {
        apiExterna = new ApiExternaLoggi();
        adaptador = new AdaptadorLoggi(apiExterna);
    }

    @Test
    public void deveAprovarIntegracaoAdapterComPedidoCorreto() {
        IPedidoLogisticaInterno pedido = new PedidoInternoConcreto("TRACK-900", 15.5);
        assertTrue(adaptador.despachar(pedido));
    }

    @Test
    public void deveLancarExcecaoNoAdapterSePedidoInternoForNulo() {
        assertThrows(IllegalArgumentException.class, () -> adaptador.despachar(null));
    }

    @Test
    public void deveTraduzirIdentificadorCorretamenteDoConcreto() {
        IPedidoLogisticaInterno pedido = new PedidoInternoConcreto("ABC-12", 10.0);
        assertEquals("ABC-12", pedido.getIdentificadorFiscal());
    }

    @Test
    public void deveTraduzirVolumeCorretamenteDoConcreto() {
        IPedidoLogisticaInterno pedido = new PedidoInternoConcreto("ABC-12", 200.5);
        assertEquals(200.5, pedido.getVolumePacoteCubico());
    }

    @Test
    public void deveLancarExcecaoPelaApiExternaLoggiQuandoPayloadNulo() {
        assertThrows(IllegalArgumentException.class, () -> apiExterna.criarRotaEntregaJson(null));
    }

    @Test
    public void deveLancarExcecaoPelaApiExternaLoggiQuandoFaltaFechamentoChave() {
        assertThrows(IllegalArgumentException.class,
                () -> apiExterna.criarRotaEntregaJson("{ \"tracking_id\": \"1\" "));
    }

    @Test
    public void deveLancarExcecaoPelaApiExternaLoggiQuandoFaltaAberturaChave() {
        assertThrows(IllegalArgumentException.class,
                () -> apiExterna.criarRotaEntregaJson(" \"tracking_id\": \"1\" }"));
    }

    @Test
    public void deveAprovarFormatacaoJsonPuraNaApiExternaLoggi() {
        assertTrue(apiExterna.criarRotaEntregaJson("{\"tracking_id\": \"001\"}"));
    }
}