package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import hamburgueria.financeiro.bridge.IProcessadorAPI;
import hamburgueria.financeiro.bridge.MetodoPagamentoAbstraction;
import hamburgueria.financeiro.bridge.PagamentoPix;
import hamburgueria.financeiro.bridge.StripeAPI;

public class BridgeTest {

    @Test
    public void deveProcessarPixViaStripe() {
        MetodoPagamentoAbstraction pix = new PagamentoPix(new StripeAPI());
        assertTrue(pix.efetuarPagamento(50.0));
    }

    @Test
    public void deveRetornarFalsoParaValorInvalidoStripe() {
        MetodoPagamentoAbstraction pix = new PagamentoPix(new StripeAPI());
        assertFalse(pix.efetuarPagamento(-50.0));
    }

    @Test
    public void deveValidarConstrutorAbstracaoAPI() {
        assertThrows(IllegalArgumentException.class, () -> new PagamentoPix(null));
    }

    @Test
    public void deveRetornarFalsoSeAPINegar() {
        IProcessadorAPI apiFalha = valor -> false; // Lambda simples garantindo o teste da interface
        MetodoPagamentoAbstraction pix = new PagamentoPix(apiFalha);
        assertFalse(pix.efetuarPagamento(50.0));
    }

    @Test
    public void deveGarantirHerancaBridge() {
        MetodoPagamentoAbstraction pix = new PagamentoPix(new StripeAPI());
        assertInstanceOf(MetodoPagamentoAbstraction.class, pix);
    }

    @Test
    public void deveGarantirInterfaceProcessadorAPI() {
        IProcessadorAPI stripe = new StripeAPI();
        assertInstanceOf(IProcessadorAPI.class, stripe);
    }

    @Test
    public void deveAceitarValorZeroComoInvalidoNaStripeAPI() {
        IProcessadorAPI stripe = new StripeAPI();
        assertFalse(stripe.processarTransacaoExterna(0.0));
    }

    @Test
    public void deveProcessarCorretamenteGrandesValoresStripe() {
        MetodoPagamentoAbstraction pix = new PagamentoPix(new StripeAPI());
        assertTrue(pix.efetuarPagamento(99999.0));
    }
}