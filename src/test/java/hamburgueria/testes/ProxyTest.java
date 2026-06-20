package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.financeiro.bridge.MetodoPagamentoAbstraction;
import hamburgueria.financeiro.bridge.PagamentoPix;
import hamburgueria.financeiro.bridge.StripeAPI;
import hamburgueria.financeiro.proxy.GatewayPagamentoProxy;
import hamburgueria.financeiro.proxy.GatewayReal;
import hamburgueria.financeiro.proxy.IGatewayPagamento;

public class ProxyTest {
    private IGatewayPagamento proxy;
    private MetodoPagamentoAbstraction metodo;
    private MetodoPagamentoAbstraction metodoFalho;
    private MetodoPagamentoAbstraction metodoSucesso;

    @BeforeEach
    public void setup() {
        proxy = new GatewayPagamentoProxy(new GatewayReal());
        metodo = new PagamentoPix(new StripeAPI());
        metodoFalho = new PagamentoPix(v -> false);
        metodoSucesso = new PagamentoPix(v -> true);
    }

    @Test
    public void deveAprovarPagamentoNormal() {
        assertTrue(proxy.cobrar(metodo, 50.0));
    }

    @Test
    public void deveLancarExcecaoValorNegativoProxy() {
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(metodo, -10.0));
    }

    @Test
    public void deveLancarExcecaoValorZeroProxy() {
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(metodo, 0.0));
    }

    @Test
    public void deveBloquearPagamentoAposTresTentativasFalhas() {
        MetodoPagamentoAbstraction metodoFalho = new PagamentoPix(v -> false);
        proxy.cobrar(metodoFalho, 10.0);
        proxy.cobrar(metodoFalho, 10.0);
        proxy.cobrar(metodoFalho, 10.0);
        assertThrows(SecurityException.class, () -> proxy.cobrar(metodoFalho, 10.0));
    }

    @Test
    public void deveZerarTentativasAposSucesso() {
        MetodoPagamentoAbstraction metodoFalho = new PagamentoPix(v -> false);
        proxy.cobrar(metodoFalho, 10.0);
        proxy.cobrar(metodoFalho, 10.0);
        assertTrue(proxy.cobrar(metodo, 50.0)); // Sucesso reseta
    }

    @Test
    public void deveGarantirAcoplamentoCorretoGatewayReal() {
        IGatewayPagamento real = new GatewayReal();
        assertTrue(real.cobrar(metodo, 50.0));
    }

    @Test
    public void devePermitirDuasTentativasFalhasSemBloquear() {
        MetodoPagamentoAbstraction metodoFalho = new PagamentoPix(v -> false);
        proxy.cobrar(metodoFalho, 10.0);
        assertFalse(proxy.cobrar(metodoFalho, 10.0));
    }

    @Test
    public void deveManterInterfaceIgualEntreProxyEReal() {
        assertInstanceOf(IGatewayPagamento.class, new GatewayPagamentoProxy(new GatewayReal()));
    }

    @Test
    public void devePermitirExatamenteUmaFalhaSemBloquear() {
        proxy.cobrar(metodoFalho, 10.0);
        assertDoesNotThrow(() -> proxy.cobrar(metodoSucesso, 10.0));
    }

    @Test
    public void devePermitirExatamenteDuasFalhasSemBloquear() {
        proxy.cobrar(metodoFalho, 10.0);
        proxy.cobrar(metodoFalho, 10.0);
        assertDoesNotThrow(() -> proxy.cobrar(metodoSucesso, 10.0));
    }

    @Test
    public void deveBloquearExatamenteNaTerceiraTentativaFalha() {
        proxy.cobrar(metodoFalho, 10.0);
        proxy.cobrar(metodoFalho, 10.0);
        proxy.cobrar(metodoFalho, 10.0);
        assertThrows(SecurityException.class, () -> proxy.cobrar(metodoSucesso, 10.0));
    }

    @Test
    public void deveRecusarNovaCobrancaComValorInvalidoMesmoAposBloqueio() {
        proxy.cobrar(metodoFalho, 10.0);
        proxy.cobrar(metodoFalho, 10.0);
        proxy.cobrar(metodoFalho, 10.0);
        // A validação de valor (<= 0) vem antes do bloqueio por fraude no nosso proxy
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(metodoSucesso, 0.0));
    }

    @Test
    public void deveZerarContagemDeFraudeAoIntercalarFalhaESucesso() {
        proxy.cobrar(metodoFalho, 10.0); // 1ª falha
        proxy.cobrar(metodoFalho, 10.0); // 2ª falha
        proxy.cobrar(metodoSucesso, 10.0); // Sucesso! Zera contador.
        proxy.cobrar(metodoFalho, 10.0); // 1ª falha nova
        proxy.cobrar(metodoFalho, 10.0); // 2ª falha nova
        assertDoesNotThrow(() -> proxy.cobrar(metodoSucesso, 10.0));
    }

    @Test
    public void devePropagarExcecaoOriginalSeMetodoDePagamentoForNulo() {
        assertThrows(NullPointerException.class, () -> proxy.cobrar(null, 10.0));
    }

    @Test
    public void deveLancarExcecaoValorNegativoSemIncrementarTentativas() {
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(metodoFalho, -10.0));
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(metodoFalho, -10.0));
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(metodoFalho, -10.0));
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(metodoFalho, -10.0));
        // Se as exceções acima tivessem contado como fraude, a linha abaixo daria
        // SecurityException
        assertDoesNotThrow(() -> proxy.cobrar(metodoSucesso, 10.0));
    }

    @Test
    public void deveGarantirQuePagamentosValidosMultiplosNaoAcionamBloqueio() {
        proxy.cobrar(metodoSucesso, 10.0);
        proxy.cobrar(metodoSucesso, 10.0);
        proxy.cobrar(metodoSucesso, 10.0);
        proxy.cobrar(metodoSucesso, 10.0);
        assertDoesNotThrow(() -> proxy.cobrar(metodoSucesso, 10.0));
    }
}