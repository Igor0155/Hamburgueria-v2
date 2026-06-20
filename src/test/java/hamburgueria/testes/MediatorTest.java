// Nome: MediatorTest.java
// Caminho: hamburgueria/testes/MediatorTest.java

package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import hamburgueria.cozinha.chain.IVerificadorQualidade;
import hamburgueria.cozinha.chain.VerificadorTemperatura;
import hamburgueria.cozinha.factorymethod.EstacaoFactoryReal;
import hamburgueria.cozinha.factorymethod.IEstacaoFactory;
import hamburgueria.cozinha.factorymethod.TipoHamburguer;
import hamburgueria.cozinha.mediator.CozinhaMediatorReal;
import hamburgueria.cozinha.mediator.ICozinhaMediator;
import hamburgueria.cozinha.singleton.EventoPedidoPago;
import hamburgueria.cozinha.state.PedidoCozinha;
import hamburgueria.integracao.adapter.AdaptadorLoggi;
import hamburgueria.integracao.adapter.ApiExternaLoggi;

public class MediatorTest {

    @Test
    public void deveOrquestrarFluxoDePreparoCompletoEMudarEstado() {
        IEstacaoFactory factory = new EstacaoFactoryReal();
        IVerificadorQualidade qc = new VerificadorTemperatura();
        AdaptadorLoggi adapter = new AdaptadorLoggi(new ApiExternaLoggi());
        ICozinhaMediator mediator = new CozinhaMediatorReal(factory, qc, adapter);
        PedidoCozinha pedido = new PedidoCozinha("123");

        mediator.orquestrarNovoPedido(pedido);

        assertEquals("DESPACHADO", pedido.getEstadoAtual().getIdentificadorEstado());
    }

    @Test
    public void deveAtuarCorretamenteAoReceberEventoDePagamento() {
        IEstacaoFactory factory = new EstacaoFactoryReal();
        IVerificadorQualidade qc = new VerificadorTemperatura();
        AdaptadorLoggi adapter = new AdaptadorLoggi(new ApiExternaLoggi());
        ICozinhaMediator mediator = new CozinhaMediatorReal(factory, qc, adapter);
        PedidoCozinha pedido = new PedidoCozinha("123");

        mediator.aoReceberEvento(new EventoPedidoPago(pedido));

        assertEquals("DESPACHADO", pedido.getEstadoAtual().getIdentificadorEstado());
    }

    @Test
    public void naoDeveFazerNadaSeReceberEventoIndesejado() {
        IEstacaoFactory factory = new EstacaoFactoryReal();
        IVerificadorQualidade qc = new VerificadorTemperatura();
        AdaptadorLoggi adapter = new AdaptadorLoggi(new ApiExternaLoggi());
        ICozinhaMediator mediator = new CozinhaMediatorReal(factory, qc, adapter);
        PedidoCozinha pedido = new PedidoCozinha("123");

        // Simulando um evento que não é PEDIDO_PAGO por interface anônima
        mediator.aoReceberEvento(new hamburgueria.cozinha.singleton.IEvento() {
            public String getNomeEvento() {
                return "OUTRO_EVENTO";
            }

            public Object getCargaDados() {
                return pedido;
            }
        });

        assertEquals("PENDENTE", pedido.getEstadoAtual().getIdentificadorEstado());
    }

    @Test
    public void deveLancarErroSeValidacaoDaCadeiaFalharPeloMediator() {
        IEstacaoFactory factory = new EstacaoFactoryReal();
        // IllegalStateException se ID for nulo
        hamburgueria.cozinha.chain.IVerificadorQualidade qc = new hamburgueria.cozinha.chain.VerificadorApresentacao();
        hamburgueria.integracao.adapter.AdaptadorLoggi adapter = new hamburgueria.integracao.adapter.AdaptadorLoggi(
                new hamburgueria.integracao.adapter.ApiExternaLoggi());

        ICozinhaMediator mediator = new CozinhaMediatorReal(factory, qc, adapter);

        PedidoCozinha pedidoFalho = new PedidoCozinha(null);

        assertThrows(IllegalStateException.class, () -> mediator.orquestrarNovoPedido(pedidoFalho));
    }

    @Test
    public void deveGarantirInterfaceOuvinteNoMediator() {
        AdaptadorLoggi adapter = new AdaptadorLoggi(new ApiExternaLoggi());
        ICozinhaMediator mediator = new CozinhaMediatorReal(new EstacaoFactoryReal(), new VerificadorTemperatura(),
                adapter);
        assertInstanceOf(hamburgueria.cozinha.singleton.IListener.class, mediator);
    }

    @Test
    public void deveTratarPedidoValidoManualmenteNaSubmissao() {
        AdaptadorLoggi adapter = new AdaptadorLoggi(new ApiExternaLoggi());
        ICozinhaMediator mediator = new CozinhaMediatorReal(new EstacaoFactoryReal(), new VerificadorTemperatura(),
                adapter);
        PedidoCozinha pedido = new PedidoCozinha("A");
        pedido.iniciarPreparo();
        pedido.finalizarPreparo();

        assertDoesNotThrow(() -> mediator.submeterParaControleQualidade(pedido));
    }

    @Test
    public void deveConcluirDespachoAposSubmissaoManual() {
        AdaptadorLoggi adapter = new AdaptadorLoggi(new ApiExternaLoggi());
        ICozinhaMediator mediator = new CozinhaMediatorReal(new EstacaoFactoryReal(), new VerificadorTemperatura(),
                adapter);
        PedidoCozinha pedido = new PedidoCozinha("A");
        pedido.iniciarPreparo();
        pedido.finalizarPreparo();
        mediator.submeterParaControleQualidade(pedido);

        assertEquals("DESPACHADO", pedido.getEstadoAtual().getIdentificadorEstado());
    }

    @Test
    public void naoDeveSubmeterPedidoEmBranco() {
        AdaptadorLoggi adapter = new AdaptadorLoggi(new ApiExternaLoggi());
        ICozinhaMediator mediator = new CozinhaMediatorReal(new EstacaoFactoryReal(), new VerificadorTemperatura(),
                adapter);
        assertThrows(IllegalArgumentException.class, () -> mediator.submeterParaControleQualidade(null));
    }

    @Test
    public void deveDespacharLogisticaAposControleDeQualidade() {
        AdaptadorLoggi adapter = new AdaptadorLoggi(new ApiExternaLoggi());
        ICozinhaMediator mediator = new CozinhaMediatorReal(new EstacaoFactoryReal(), new VerificadorTemperatura(),
                adapter);
        PedidoCozinha pedido = new PedidoCozinha("TRACK-001");
        pedido.iniciarPreparo();
        pedido.finalizarPreparo();

        assertDoesNotThrow(() -> mediator.submeterParaControleQualidade(pedido));
    }

    @Test
    public void deveLerListaDeAlimentosDoPedidoParaAcionarFactory() {
        PedidoCozinha pedido = new PedidoCozinha("TRACK-002");
        pedido.adicionarAlimentoParaPreparo(new TipoHamburguer());
        assertEquals(1, pedido.getAlimentos().size());
    }
}