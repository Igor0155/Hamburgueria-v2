package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.cozinha.chain.BaseVerificadorQualidade;
import hamburgueria.cozinha.chain.IVerificadorQualidade;
import hamburgueria.cozinha.chain.VerificadorApresentacao;
import hamburgueria.cozinha.chain.VerificadorEmbalagem;
import hamburgueria.cozinha.chain.VerificadorTemperatura;
import hamburgueria.cozinha.state.PedidoCozinha;

public class ChainTest {
    private IVerificadorQualidade vTemp;
    private IVerificadorQualidade vApres;
    private IVerificadorQualidade vEmb;

    @BeforeEach
    public void setup() {
        vTemp = new VerificadorTemperatura();
        vApres = new VerificadorApresentacao();
        vEmb = new VerificadorEmbalagem();

        vTemp.setProximo(vApres);
        vApres.setProximo(vEmb);
    }

    @Test
    public void devePassarPorTodaACadeiaComSucesso() {
        PedidoCozinha pedido = new PedidoCozinha("RÓTULO_OK");
        pedido.iniciarPreparo();
        pedido.finalizarPreparo();
        assertDoesNotThrow(() -> vTemp.avaliar(pedido));
    }

    @Test
    public void deveFalharTemperaturaSePedidoNulo() {
        assertThrows(IllegalArgumentException.class, () -> vTemp.avaliar(null));
    }

    @Test
    public void deveFalharApresentacaoSeRotuloNulo() {
        PedidoCozinha pedidoInvalido = new PedidoCozinha(null);
        assertThrows(IllegalStateException.class, () -> vTemp.avaliar(pedidoInvalido));
    }

    @Test
    public void deveFalharEmbalagemSeEstadoNaoForPronto() {
        PedidoCozinha pedidoIncompleto = new PedidoCozinha("RÓTULO");
        assertThrows(IllegalStateException.class, () -> vTemp.avaliar(pedidoIncompleto));
    }

    @Test
    public void deveAceitarValidacaoUnitariaIsoladaDaEmbalagem() {
        PedidoCozinha pedido = new PedidoCozinha("1");
        pedido.iniciarPreparo();
        pedido.finalizarPreparo();
        assertDoesNotThrow(() -> vEmb.avaliar(pedido));
    }

    @Test
    public void deveAceitarValidacaoUnitariaIsoladaDaApresentacao() {
        PedidoCozinha pedido = new PedidoCozinha("1");
        pedido.iniciarPreparo();
        pedido.finalizarPreparo();
        assertDoesNotThrow(() -> vApres.avaliar(pedido));
    }

    @Test
    public void deveGarantirHerdancaEmComumParaTemperatura() {
        assertInstanceOf(BaseVerificadorQualidade.class, vTemp);
    }

    @Test
    public void deveGarantirHerdancaEmComumParaApresentacao() {
        assertInstanceOf(BaseVerificadorQualidade.class, vApres);
    }

    @Test
    public void deveEncerrarCadeiaSemErroQuandoProximoForNuloNaTemperatura() {
        VerificadorTemperatura temperatura = new VerificadorTemperatura();
        temperatura.setProximo(null);
        assertDoesNotThrow(() -> temperatura.avaliar(new PedidoCozinha("PED-1")));
    }

    @Test
    public void deveEncerrarCadeiaSemErroQuandoProximoForNuloNaApresentacao() {
        VerificadorApresentacao apresentacao = new VerificadorApresentacao();
        apresentacao.setProximo(null);
        assertDoesNotThrow(() -> apresentacao.avaliar(new PedidoCozinha("PED-2")));
    }

    @Test
    public void deveEncerrarCadeiaSemErroQuandoProximoForNuloNaEmbalagem() {
        VerificadorEmbalagem embalagem = new VerificadorEmbalagem();
        embalagem.setProximo(null);
        PedidoCozinha pedido = new PedidoCozinha("PED-3");
        pedido.iniciarPreparo();
        pedido.finalizarPreparo();
        assertDoesNotThrow(() -> embalagem.avaliar(pedido));
    }

    @Test
    public void devePermitirAlterarOProximoEloDinamicamenteNaTemperatura() {
        VerificadorTemperatura temperatura = new VerificadorTemperatura();
        VerificadorEmbalagem embalagem = new VerificadorEmbalagem();
        assertDoesNotThrow(() -> temperatura.setProximo(embalagem));
    }

    @Test
    public void deveLancarExcecaoAoRepassarParaEloDesconectadoEIncompativel() {
        VerificadorTemperatura temperatura = new VerificadorTemperatura();
        VerificadorEmbalagem embalagem = new VerificadorEmbalagem(); // Exige estado PRONTO
        temperatura.setProximo(embalagem);
        PedidoCozinha pedidoPendente = new PedidoCozinha("PED-4");
        // A temperatura vai aprovar e repassar para a embalagem que vai estourar a
        // exceção
        assertThrows(IllegalStateException.class, () -> temperatura.avaliar(pedidoPendente));
    }

    @Test
    public void deveValidarApresentacaoIsoladamenteComPedidoValidoSemProximo() {
        VerificadorApresentacao apresentacao = new VerificadorApresentacao();
        PedidoCozinha pedido = new PedidoCozinha("ID-VALIDO");
        assertDoesNotThrow(() -> apresentacao.avaliar(pedido));
    }

    @Test
    public void deveValidarTemperaturaIsoladamenteComPedidoValidoSemProximo() {
        VerificadorTemperatura temperatura = new VerificadorTemperatura();
        PedidoCozinha pedido = new PedidoCozinha("ID-VALIDO");
        assertDoesNotThrow(() -> temperatura.avaliar(pedido));
    }

    @Test
    public void deveValidarEmbalagemIsoladamenteComPedidoValidoEProntoSemProximo() {
        VerificadorEmbalagem embalagem = new VerificadorEmbalagem();
        PedidoCozinha pedido = new PedidoCozinha("ID-VALIDO");
        pedido.iniciarPreparo();
        pedido.finalizarPreparo();
        assertDoesNotThrow(() -> embalagem.avaliar(pedido));
    }
}