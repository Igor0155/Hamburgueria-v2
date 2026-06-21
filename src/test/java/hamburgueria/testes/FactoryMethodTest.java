package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import hamburgueria.catalogo.factory.BatataRustica;
import hamburgueria.catalogo.factory.ComboAgregado;
import hamburgueria.catalogo.factory.ComboArtesanalFactory;
import hamburgueria.catalogo.factory.ComboSmashFactory;
import hamburgueria.catalogo.factory.HamburguerArtesanal;
import hamburgueria.catalogo.factory.IHamburguer;
import hamburgueria.cozinha.factorymethod.EstacaoFactoryReal;
import hamburgueria.cozinha.factorymethod.IEstacaoFactory;
import hamburgueria.cozinha.factorymethod.ITipoAlimento;
import hamburgueria.cozinha.factorymethod.TipoAcompanhamento;
import hamburgueria.cozinha.factorymethod.TipoHamburguer;
import hamburgueria.cozinha.template.PreparoChapa;
import hamburgueria.cozinha.template.PreparoFritadeira;
import hamburgueria.cozinha.template.PreparoTemplate;

public class FactoryMethodTest {

    @Test
    public void deveRetornarClassificacaoCorretaParaHamburguer() {
        ITipoAlimento tipo = new TipoHamburguer();
        assertEquals("PROTEINA_MOLDE", tipo.classificarCorte());
    }

    @Test
    public void deveRetornarClassificacaoCorretaParaAcompanhamento() {
        ITipoAlimento tipo = new TipoAcompanhamento();
        assertEquals("VEGETAL_CORTE", tipo.classificarCorte());
    }

    @Test
    public void deveCriarInstanciaDaChapaParaHamburguer() {
        IEstacaoFactory factory = new EstacaoFactoryReal();
        PreparoTemplate estacao = factory.instanciarEstacao(new TipoHamburguer());
        assertInstanceOf(PreparoChapa.class, estacao);
    }

    @Test
    public void deveCriarInstanciaDaFritadeiraParaAcompanhamento() {
        IEstacaoFactory factory = new EstacaoFactoryReal();
        PreparoTemplate estacao = factory.instanciarEstacao(new TipoAcompanhamento());
        assertInstanceOf(PreparoFritadeira.class, estacao);
    }

    @Test
    public void deveLancarExcecaoSeAlimentoForNuloNaFactory() {
        IEstacaoFactory factory = new EstacaoFactoryReal();
        assertThrows(IllegalArgumentException.class, () -> factory.instanciarEstacao(null));
    }

    @Test
    public void deveGarantirInterfacesIsoladasDeTiposDiferentes() {
        assertNotSame(new TipoHamburguer().getClass(), new TipoAcompanhamento().getClass());
    }

    @Test
    public void deveGerarNovasInstanciasAChapaACadaChamada() {
        IEstacaoFactory factory = new EstacaoFactoryReal();
        PreparoTemplate e1 = factory.instanciarEstacao(new TipoHamburguer());
        PreparoTemplate e2 = factory.instanciarEstacao(new TipoHamburguer());
        assertNotSame(e1, e2);
    }

    @Test
    public void deveGerarNovasInstanciasAFritadeiraACadaChamada() {
        IEstacaoFactory factory = new EstacaoFactoryReal();
        PreparoTemplate e1 = factory.instanciarEstacao(new TipoAcompanhamento());
        PreparoTemplate e2 = factory.instanciarEstacao(new TipoAcompanhamento());
        assertNotSame(e1, e2);
    }

    @Test
    public void deveGarantirQueComboArtesanalFactoryGeraNovosHamburgueresSempre() {
        ComboArtesanalFactory factory = new ComboArtesanalFactory();
        assertNotSame(factory.criarHamburguer(), factory.criarHamburguer());
    }

    @Test
    public void deveGarantirQueComboArtesanalFactoryGeraNovasBatatasSempre() {
        ComboArtesanalFactory factory = new ComboArtesanalFactory();
        assertNotSame(factory.criarAcompanhamento(), factory.criarAcompanhamento());
    }

    @Test
    public void deveGarantirQueComboSmashFactoryGeraNovosHamburgueresSempre() {
        ComboSmashFactory factory = new ComboSmashFactory();
        assertNotSame(factory.criarHamburguer(), factory.criarHamburguer());
    }

    @Test
    public void deveGarantirQueComboSmashFactoryGeraNovasBatatasSempre() {
        ComboSmashFactory factory = new ComboSmashFactory();
        assertNotSame(factory.criarAcompanhamento(), factory.criarAcompanhamento());
    }

    @Test
    public void deveRetornarFalsoParaConcluidoAntesDeExecutarChapaDiretamente() {
        PreparoChapa chapa = new PreparoChapa();
        assertFalse(chapa.isConcluido());
    }

    @Test
    public void deveRetornarVerdadeiroParaConcluidoAposExecutarChapaDiretamente() {
        PreparoChapa chapa = new PreparoChapa();
        chapa.executarPreparoPadrao(new hamburgueria.cozinha.state.PedidoCozinha("1"));
        assertTrue(chapa.isConcluido());
    }

    @Test
    public void deveRetornarFalsoParaConcluidoAntesDeExecutarFritadeiraDiretamente() {
        PreparoFritadeira fritadeira = new PreparoFritadeira();
        assertFalse(fritadeira.isConcluido());
    }

    @Test
    public void deveRetornarVerdadeiroParaConcluidoAposExecutarFritadeiraDiretamente() {
        PreparoFritadeira fritadeira = new PreparoFritadeira();
        fritadeira.executarPreparoPadrao(new hamburgueria.cozinha.state.PedidoCozinha("2"));
        assertTrue(fritadeira.isConcluido());
    }

    @Test
    public void deveRetornarDescricaoConcatenadaCorretamenteNoCombo() {
        ComboAgregado combo = new ComboAgregado(new HamburguerArtesanal(), new BatataRustica(), 10.0);
        assertEquals("COMBO: Hambúrguer Artesanal 200g + Batata Rústica com Alecrim", combo.getDescricao());
    }

    @Test
    public void deveRetornarCustoSomadoCorretamenteNoCombo() {
        // Hambúrguer Artesanal custa 35.0. Somando 12.0 do adicional.
        ComboAgregado combo = new ComboAgregado(new HamburguerArtesanal(), new BatataRustica(), 12.0);
        assertEquals(47.0, combo.getCusto());
    }

    @Test
    public void deveLancarExcecaoSeHamburguerForNuloNaMontagemDoCombo() {
        assertThrows(IllegalArgumentException.class, () -> new ComboAgregado(null, new BatataRustica(), 10.0));
    }

    @Test
    public void deveLancarExcecaoSeAcompanhamentoForNuloNaMontagemDoCombo() {
        assertThrows(IllegalArgumentException.class, () -> new ComboAgregado(new HamburguerArtesanal(), null, 10.0));
    }

    @Test
    public void deveLancarExcecaoSeValorAdicionalDoComboForNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new ComboAgregado(new HamburguerArtesanal(), new BatataRustica(), -5.0));
    }

    @Test
    public void deveRetornarInstanciaCorretaDoHamburguerPrincipalEncapsulado() {
        HamburguerArtesanal artesanal = new HamburguerArtesanal();
        ComboAgregado combo = new ComboAgregado(artesanal, new BatataRustica(), 10.0);
        assertSame(artesanal, combo.getHamburguerPrincipal());
    }

    @Test
    public void deveRetornarInstanciaCorretaDoAcompanhamentoEncapsulado() {
        BatataRustica batata = new BatataRustica();
        ComboAgregado combo = new ComboAgregado(new HamburguerArtesanal(), batata, 10.0);
        assertSame(batata, combo.getAcompanhamento());
    }

    @Test
    public void deveGarantirQueOComboPolimorficoImplementaInterfaceIHamburguer() {
        ComboAgregado combo = new ComboAgregado(new HamburguerArtesanal(), new BatataRustica(), 10.0);
        assertInstanceOf(IHamburguer.class, combo);
    }
}