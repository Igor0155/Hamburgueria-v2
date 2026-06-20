package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.monitoramento.observer.CentralNotificacao;
import hamburgueria.monitoramento.observer.TelaClienteApp;

public class ObserverTest {
    private CentralNotificacao central;
    private TelaClienteApp tela;

    @BeforeEach
    public void setup() {
        central = new CentralNotificacao();
        tela = new TelaClienteApp();
    }

    @Test
    public void deveAnexarObservadorComSucesso() {
        central.anexar(tela);
        assertEquals(1, central.getTotalInscritos());
    }

    @Test
    public void deveDesanexarObservadorComSucesso() {
        central.anexar(tela);
        central.desanexar(tela);
        assertEquals(0, central.getTotalInscritos());
    }

    @Test
    public void deveAtualizarStringRecebidaNoObservadorAnexado() {
        central.anexar(tela);
        central.notificarMudanca("PED-55", "PRONTO");
        assertEquals("Push: Seu pedido PED-55 agora está PRONTO", tela.getUltimaMensagemRecebida());
    }

    @Test
    public void naoDeveAtualizarObservadorDesanexado() {
        central.anexar(tela);
        central.desanexar(tela);
        central.notificarMudanca("PED-55", "PRONTO");
        assertNull(tela.getUltimaMensagemRecebida());
    }

    @Test
    public void deveGarantirRecebimentoNuloAntesDaNotificacao() {
        central.anexar(tela);
        assertNull(tela.getUltimaMensagemRecebida());
    }

    @Test
    public void deveAtualizarMultiplosObservadoresIsoladosSimultaneamente() {
        TelaClienteApp tela2 = new TelaClienteApp();
        central.anexar(tela);
        central.anexar(tela2);
        central.notificarMudanca("PED-01", "SAIU_PARA_ENTREGA");
        assertEquals(tela.getUltimaMensagemRecebida(), tela2.getUltimaMensagemRecebida());
    }

    @Test
    public void deveSobrescreverNotificacaoAnteriorComNova() {
        central.anexar(tela);
        central.notificarMudanca("PED-99", "PREPARO");
        central.notificarMudanca("PED-99", "PRONTO");
        assertEquals("Push: Seu pedido PED-99 agora está PRONTO", tela.getUltimaMensagemRecebida());
    }

    @Test
    public void deveManterInscritosMesmoSemNotificacaoAtiva() {
        central.anexar(tela);
        central.anexar(new TelaClienteApp());
        assertEquals(2, central.getTotalInscritos());
    }

    @Test
    public void deveIgnorarTentativaDeDesanexarObservadorNaoInscrito() {
        assertDoesNotThrow(() -> central.desanexar(tela));
    }

    @Test
    public void naoDeveFalharAoNotificarSistemaSemNenhumInscrito() {
        assertDoesNotThrow(() -> central.notificarMudanca("PED-000", "TESTE"));
    }

    @Test
    public void deveManterInscritosInalteradosAoDesanexarObjetoDiferente() {
        TelaClienteApp telaFantasma = new TelaClienteApp();
        central.anexar(tela);
        central.desanexar(telaFantasma);

        assertEquals(1, central.getTotalInscritos());
    }
}