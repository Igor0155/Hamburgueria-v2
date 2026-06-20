package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.financeiro.interpreter.ContextoMatematicoReal;
import hamburgueria.financeiro.interpreter.IContextoMatematico;
import hamburgueria.financeiro.interpreter.IExpressao;
import hamburgueria.financeiro.interpreter.MultiplicacaoExpressao;
import hamburgueria.financeiro.interpreter.NumeroExpressao;
import hamburgueria.financeiro.interpreter.VariavelExpressao;

public class InterpreterTest {
    private IContextoMatematico contexto;

    @BeforeEach
    public void setup() {
        contexto = new ContextoMatematicoReal();
    }

    @Test
    public void deveInterpretarNumeroFixo() {
        IExpressao numero = new NumeroExpressao(15.5);
        assertEquals(15.5, numero.interpretar(contexto));
    }

    @Test
    public void deveInterpretarVariavelSimples() {
        contexto.definirValor("TAXA", 2.0);
        IExpressao var = new VariavelExpressao("TAXA");
        assertEquals(2.0, var.interpretar(contexto));
    }

    @Test
    public void deveInterpretarMultiplicacaoSimples() {
        IExpressao mult = new MultiplicacaoExpressao(new NumeroExpressao(10), new NumeroExpressao(2));
        assertEquals(20.0, mult.interpretar(contexto));
    }

    @Test
    public void deveLancarExcecaoVariavelNaoDefinida() {
        IExpressao var = new VariavelExpressao("CUPOM");
        assertThrows(IllegalArgumentException.class, () -> var.interpretar(contexto));
    }

    @Test
    public void deveInterpretarEquacaoComVariavelDinamicamenteAlterada() {
        contexto.definirValor("PRECO", 50.0);
        IExpressao eq = new MultiplicacaoExpressao(new VariavelExpressao("PRECO"), new NumeroExpressao(0.5));
        contexto.definirValor("PRECO", 100.0);
        assertEquals(50.0, eq.interpretar(contexto));
    }

    @Test
    public void deveInterpretarAcrescimoDeTaxaUsandoMultiplicacao() {
        contexto.definirValor("BASE", 10.0);
        IExpressao eq = new MultiplicacaoExpressao(new VariavelExpressao("BASE"), new NumeroExpressao(1.2));
        assertEquals(12.0, eq.interpretar(contexto));
    }

    @Test
    public void deveSobrescreverVariavelNoContexto() {
        contexto.definirValor("A", 1.0);
        contexto.definirValor("A", 2.0);
        assertEquals(2.0, contexto.obterValor("A"));
    }

    @Test
    public void deveProcessarMultiplicacaoComZero() {
        IExpressao mult = new MultiplicacaoExpressao(new NumeroExpressao(10), new NumeroExpressao(0));
        assertEquals(0.0, mult.interpretar(contexto));
    }

    @Test
    public void deveInterpretarExpressaoAninhadaComplexa() {
        // (2 * 3) * (4 * 5) = 120
        IExpressao ramoEsquerdo = new MultiplicacaoExpressao(new NumeroExpressao(2), new NumeroExpressao(3));
        IExpressao ramoDireito = new MultiplicacaoExpressao(new NumeroExpressao(4), new NumeroExpressao(5));
        IExpressao raiz = new MultiplicacaoExpressao(ramoEsquerdo, ramoDireito);

        assertEquals(120.0, raiz.interpretar(contexto));
    }

    @Test
    public void deveProcessarVariaveisComValoresNegativos() {
        contexto.definirValor("DESCONTO_FIXO", -15.0);
        IExpressao var = new VariavelExpressao("DESCONTO_FIXO");

        assertEquals(-15.0, var.interpretar(contexto));
    }

    @Test
    public void deveMultiplicarNumeroPositivoComVariavelNegativa() {
        contexto.definirValor("TAXA_REVERSA", -2.0);
        IExpressao eq = new MultiplicacaoExpressao(new NumeroExpressao(50.0), new VariavelExpressao("TAXA_REVERSA"));

        assertEquals(-100.0, eq.interpretar(contexto));
    }

    @Test
    public void devePermitirDefinirValorZeroAbsolutoNaVariavel() {
        assertDoesNotThrow(() -> contexto.definirValor("TAXA_ZERO", 0.0));
    }

    @Test
    public void deveRecuperarValorZeroAbsolutoDaVariavel() {
        contexto.definirValor("TAXA_ZERO", 0.0);
        assertEquals(0.0, contexto.obterValor("TAXA_ZERO"));
    }

    @Test
    public void devePermitirDefinirValorExtremamenteAlto() {
        assertDoesNotThrow(() -> contexto.definirValor("VALOR_MAX", Double.MAX_VALUE));
    }

    @Test
    public void deveRecuperarValorExtremamenteAlto() {
        contexto.definirValor("VALOR_MAX", Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, contexto.obterValor("VALOR_MAX"));
    }

    @Test
    public void deveLancarExcecaoAoObterVariavelNuncaDefinidaAnteriormente() {
        assertThrows(IllegalArgumentException.class, () -> contexto.obterValor("CUPOM_FANTASMA"));
    }

    @Test
    public void devePermitirReatribuirAMesmaVariavelMultiplasVezesSemErro() {
        contexto.definirValor("FRETE", 10.0);
        assertDoesNotThrow(() -> contexto.definirValor("FRETE", 15.0));
    }

    @Test
    public void deveDiferenciarVariaveisComNomesSimilaresCaseSensitiveMinusculo() {
        contexto.definirValor("taxa", 5.0);
        assertThrows(IllegalArgumentException.class, () -> contexto.obterValor("TAXA"));
    }

    @Test
    public void deveDiferenciarVariaveisComNomesSimilaresCaseSensitiveMaiusculo() {
        contexto.definirValor("TAXA", 15.0);
        assertEquals(15.0, contexto.obterValor("TAXA"));
    }
}