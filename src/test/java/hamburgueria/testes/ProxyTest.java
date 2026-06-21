package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.financeiro.bridge.PagamentoPix;
import hamburgueria.financeiro.bridge.StripeAPI;
import hamburgueria.financeiro.proxy.GatewayPagamentoProxy;
import hamburgueria.financeiro.proxy.GatewayReal;
import hamburgueria.financeiro.proxy.IGatewayPagamento;

public class ProxyTest {
    private IGatewayPagamento proxy;

    @BeforeEach
    public void setup() {
        // Mock Dinâmico: Retorna 'true' (sucesso) se o valor for 50.0, e 'false'
        // (falha) para qualquer outro (ex: 10.0).
        proxy = new GatewayPagamentoProxy(new GatewayReal(new PagamentoPix(v -> v == 50.0)));
    }

    @Test
    public void deveAprovarPagamentoNormal() {
        assertTrue(proxy.cobrar(50.0));
    }

    @Test
    public void deveLancarExcecaoValorNegativoProxy() {
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(-10.0));
    }

    @Test
    public void deveLancarExcecaoValorZeroProxy() {
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(0.0));
    }

    @Test
    public void deveBloquearPagamentoAposTresTentativasFalhas() {
        proxy.cobrar(10.0); // 1ª falha
        proxy.cobrar(10.0); // 2ª falha
        proxy.cobrar(10.0); // 3ª falha
        assertThrows(SecurityException.class, () -> proxy.cobrar(10.0));
    }

    @Test
    public void deveZerarTentativasAposSucesso() {
        proxy.cobrar(10.0); // 1ª falha
        proxy.cobrar(10.0); // 2ª falha
        assertTrue(proxy.cobrar(50.0)); // Sucesso 50.0 reseta o contador
    }

    @Test
    public void deveGarantirAcoplamentoCorretoGatewayReal() {
        assertTrue(proxy.cobrar(50.0));
    }

    @Test
    public void devePermitirDuasTentativasFalhasSemBloquear() {
        proxy.cobrar(10.0);
        assertFalse(proxy.cobrar(10.0));
    }

    @Test
    public void deveManterInterfaceIgualEntreProxyEReal() {
        assertInstanceOf(IGatewayPagamento.class,
                new GatewayPagamentoProxy(new GatewayReal(new PagamentoPix(new StripeAPI()))));
    }

    @Test
    public void devePermitirExatamenteUmaFalhaSemBloquear() {
        proxy.cobrar(10.0); // 1ª falha
        assertDoesNotThrow(() -> proxy.cobrar(50.0));
    }

    @Test
    public void devePermitirExatamenteDuasFalhasSemBloquear() {
        proxy.cobrar(10.0); // 1ª falha
        proxy.cobrar(10.0); // 2ª falha
        assertDoesNotThrow(() -> proxy.cobrar(50.0));
    }

    @Test
    public void deveBloquearExatamenteNaTerceiraTentativaFalha() {
        proxy.cobrar(10.0); // 1ª falha
        proxy.cobrar(10.0); // 2ª falha
        proxy.cobrar(10.0); // 3ª falha
        assertThrows(SecurityException.class, () -> proxy.cobrar(50.0));
    }

    @Test
    public void deveRecusarNovaCobrancaComValorInvalidoMesmoAposBloqueio() {
        proxy.cobrar(10.0);
        proxy.cobrar(10.0);
        proxy.cobrar(10.0);
        // A validação de valor (<= 0) vem antes do bloqueio por fraude no nosso proxy
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(0.0));
    }

    @Test
    public void deveZerarContagemDeFraudeAoIntercalarFalhaESucesso() {
        proxy.cobrar(10.0); // 1ª falha
        proxy.cobrar(10.0); // 2ª falha
        proxy.cobrar(50.0); // Sucesso! Zera contador.
        proxy.cobrar(10.0); // 1ª falha nova
        proxy.cobrar(10.0); // 2ª falha nova
        assertDoesNotThrow(() -> proxy.cobrar(50.0));
    }

    @Test
    public void devePropagarExcecaoOriginalSeMetodoDePagamentoForNulo() {
        // A injeção nula dispara IllegalArgumentException direto no construtor do
        // GatewayReal
        assertThrows(IllegalArgumentException.class, () -> new GatewayPagamentoProxy(new GatewayReal(null)));
    }

    @Test
    public void deveLancarExcecaoValorNegativoSemIncrementarTentativas() {
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(-10.0));
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(-10.0));
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(-10.0));
        assertThrows(IllegalArgumentException.class, () -> proxy.cobrar(-10.0));
        // Se as exceções acima tivessem contado como fraude, a linha abaixo daria
        // SecurityException
        assertDoesNotThrow(() -> proxy.cobrar(50.0));
    }

    @Test
    public void deveGarantirQuePagamentosValidosMultiplosNaoAcionamBloqueio() {
        proxy.cobrar(50.0);
        proxy.cobrar(50.0);
        proxy.cobrar(50.0);
        proxy.cobrar(50.0);
        assertDoesNotThrow(() -> proxy.cobrar(50.0));
    }
}