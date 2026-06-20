package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.catalogo.flyweight.IngredienteDetalhe;
import hamburgueria.catalogo.flyweight.IngredienteFlyweightFactory;

public class FlyweightTest {

    @BeforeEach
    public void setup() {
        IngredienteFlyweightFactory.limparCache();
    }

    @Test
    public void deveCriarInstanciaBaconNoCache() {
        IngredienteFlyweightFactory.obterDetalhe("Bacon");
        assertEquals(1, IngredienteFlyweightFactory.getTamanhoCache());
    }

    @Test
    public void deveReutilizarInstanciaBaconDoCache() {
        IngredienteFlyweightFactory.obterDetalhe("Bacon");
        IngredienteFlyweightFactory.obterDetalhe("Bacon");
        assertEquals(1, IngredienteFlyweightFactory.getTamanhoCache());
    }

    @Test
    public void deveCriarMultiplasInstanciasDiferentes() {
        IngredienteFlyweightFactory.obterDetalhe("Bacon");
        IngredienteFlyweightFactory.obterDetalhe("Carne Smash");
        assertEquals(2, IngredienteFlyweightFactory.getTamanhoCache());
    }

    @Test
    public void deveRetornarCaloriasCorretasBacon() {
        IngredienteDetalhe detalhe = IngredienteFlyweightFactory.obterDetalhe("Bacon");
        assertEquals(300, detalhe.getCalorias());
    }

    @Test
    public void deveRetornarCaloriasCorretasWhey() {
        IngredienteDetalhe detalhe = IngredienteFlyweightFactory.obterDetalhe("Whey Protein");
        assertEquals(120, detalhe.getCalorias());
    }

    @Test
    public void deveRetornarImagemCorreta() {
        IngredienteDetalhe detalhe = IngredienteFlyweightFactory.obterDetalhe("Bacon");
        assertEquals("img_base64_pesada_Bacon", detalhe.getImagemAltaResolucaoBase64());
    }

    @Test
    public void deveRetornarTabelaNutricionalCorreta() {
        IngredienteDetalhe detalhe = IngredienteFlyweightFactory.obterDetalhe("Carne Smash");
        assertEquals("Tabela de Carne Smash", detalhe.getTabelaNutricional());
    }

    @Test
    public void deveManterMesmaReferenciaDeObjeto() {
        IngredienteDetalhe ref1 = IngredienteFlyweightFactory.obterDetalhe("Cheddar");
        IngredienteDetalhe ref2 = IngredienteFlyweightFactory.obterDetalhe("Cheddar");
        assertSame(ref1, ref2);
    }

    @Test
    public void deveManterTamanhoZeroAposMultiplasLimpezas() {
        IngredienteFlyweightFactory.limparCache();
        IngredienteFlyweightFactory.limparCache();
        assertEquals(0, IngredienteFlyweightFactory.getTamanhoCache());
    }

    @Test
    public void deveCriarExatamenteUmaInstanciaParaCemChamadasIguais() {
        // Sem usar for() - vamos testar chamadas encadeadas/repetidas pontualmente
        IngredienteFlyweightFactory.obterDetalhe("Picles");
        IngredienteFlyweightFactory.obterDetalhe("Picles");
        IngredienteFlyweightFactory.obterDetalhe("Picles");
        IngredienteFlyweightFactory.obterDetalhe("Picles");
        IngredienteFlyweightFactory.obterDetalhe("Picles");
        assertEquals(1, IngredienteFlyweightFactory.getTamanhoCache());
    }

    @Test
    public void deveLimparCacheCorretamenteAposPopular() {
        IngredienteFlyweightFactory.obterDetalhe("Picles");
        IngredienteFlyweightFactory.obterDetalhe("Mostarda");
        IngredienteFlyweightFactory.limparCache();
        assertEquals(0, IngredienteFlyweightFactory.getTamanhoCache());
    }

    @Test
    public void deveGerarNovasReferenciasAposLimpezaDoCache() {
        IngredienteDetalhe refAntiga = IngredienteFlyweightFactory.obterDetalhe("Ketchup");
        IngredienteFlyweightFactory.limparCache();
        IngredienteDetalhe refNova = IngredienteFlyweightFactory.obterDetalhe("Ketchup");
        assertNotSame(refAntiga, refNova);
    }

    @Test
    public void deveRetornarReferenciaDiferenteParaNomesComCaseDiferente() {
        IngredienteDetalhe ref1 = IngredienteFlyweightFactory.obterDetalhe("cebola");
        IngredienteDetalhe ref2 = IngredienteFlyweightFactory.obterDetalhe("Cebola");
        assertNotSame(ref1, ref2);
    }

    @Test
    public void deveRegistrarDuasInstanciasParaNomesComCaseDiferente() {
        IngredienteFlyweightFactory.obterDetalhe("cebola");
        IngredienteFlyweightFactory.obterDetalhe("Cebola");
        assertEquals(2, IngredienteFlyweightFactory.getTamanhoCache());
    }

    @Test
    public void deveLancarNullPointerExceptionSeNomeDoIngredienteForNulo() {
        assertThrows(NullPointerException.class, () -> IngredienteFlyweightFactory.obterDetalhe(null));
    }

    @Test
    public void deveRetornarCaloriasPadraoParaIngredienteDesconhecido() {
        IngredienteDetalhe detalhe = IngredienteFlyweightFactory.obterDetalhe("Ingrediente Extraterrestre");
        assertEquals(100, detalhe.getCalorias());
    }
}