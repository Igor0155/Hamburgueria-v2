package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import hamburgueria.financeiro.strategy.IEstrategiaPrecificacao;
import hamburgueria.financeiro.strategy.PrecoTaxaNoturnaStrategy;

public class StrategyTest {

    @Test
    public void deveCalcularPrecoNoturnoComAcrescimo() {
        IEstrategiaPrecificacao strategy = new PrecoTaxaNoturnaStrategy();
        assertEquals(120.0, strategy.calcularTotal(100.0));
    }

    @Test
    public void deveCalcularPrecoNoturnoComZero() {
        IEstrategiaPrecificacao strategy = new PrecoTaxaNoturnaStrategy();
        assertEquals(0.0, strategy.calcularTotal(0.0));
    }

    @Test
    public void deveCalcularPrecoNoturnoDecimal() {
        IEstrategiaPrecificacao strategy = new PrecoTaxaNoturnaStrategy();
        assertEquals(60.0, strategy.calcularTotal(50.0));
    }

    @Test
    public void deveLancarExcecaoParaSubtotalNegativoStrategyNoturno() {
        IEstrategiaPrecificacao strategy = new PrecoTaxaNoturnaStrategy();
        assertThrows(IllegalArgumentException.class, () -> strategy.calcularTotal(-10.0));
    }

    @Test
    public void deveManterConsistenciaMatematicaNoturna() {
        IEstrategiaPrecificacao strategy = new PrecoTaxaNoturnaStrategy();
        assertEquals(12.0, strategy.calcularTotal(10.0));
    }

    @Test
    public void deveValidarInstanciaCorretaDaInterface() {
        IEstrategiaPrecificacao strategy = new PrecoTaxaNoturnaStrategy();
        assertInstanceOf(IEstrategiaPrecificacao.class, strategy);
    }

    @Test
    public void deveRetornarTipoDoubleNoStrategy() {
        IEstrategiaPrecificacao strategy = new PrecoTaxaNoturnaStrategy();
        Object resultado = strategy.calcularTotal(10.0);
        assertInstanceOf(Double.class, resultado);
    }

    @Test
    public void deveProcessarValoresAltosSemOverflowNoStrategy() {
        IEstrategiaPrecificacao strategy = new PrecoTaxaNoturnaStrategy();
        assertEquals(12000.0, strategy.calcularTotal(10000.0));
    }
}