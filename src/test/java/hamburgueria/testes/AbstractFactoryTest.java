package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.Test;

import hamburgueria.catalogo.factory.BatataPalito;
import hamburgueria.catalogo.factory.BatataRustica;
import hamburgueria.catalogo.factory.ComboArtesanalFactory;
import hamburgueria.catalogo.factory.ComboSmashFactory;
import hamburgueria.catalogo.factory.HamburguerArtesanal;
import hamburgueria.catalogo.factory.HamburguerSmash;
import hamburgueria.catalogo.factory.IAcompanhamento;
import hamburgueria.catalogo.factory.IComboFactory;
import hamburgueria.catalogo.factory.IHamburguer;

public class AbstractFactoryTest {

    @Test
    public void deveCriarHamburguerArtesanalPelaFactory() {
        IComboFactory factory = new ComboArtesanalFactory();
        IHamburguer burger = factory.criarHamburguer();
        assertInstanceOf(HamburguerArtesanal.class, burger);
    }

    @Test
    public void deveCriarBatataRusticaPelaFactory() {
        IComboFactory factory = new ComboArtesanalFactory();
        IAcompanhamento batata = factory.criarAcompanhamento();
        assertInstanceOf(BatataRustica.class, batata);
    }

    @Test
    public void deveCriarHamburguerSmashPelaFactory() {
        IComboFactory factory = new ComboSmashFactory();
        IHamburguer burger = factory.criarHamburguer();
        assertInstanceOf(HamburguerSmash.class, burger);
    }

    @Test
    public void deveCriarBatataPalitoPelaFactory() {
        IComboFactory factory = new ComboSmashFactory();
        IAcompanhamento batata = factory.criarAcompanhamento();
        assertInstanceOf(BatataPalito.class, batata);
    }

    @Test
    public void deveRetornarDescricaoCorretaHamburguerArtesanal() {
        IHamburguer burger = new HamburguerArtesanal();
        assertEquals("Hambúrguer Artesanal 200g", burger.getDescricao());
    }

    @Test
    public void deveRetornarPrecoCorretoHamburguerArtesanal() {
        IHamburguer burger = new HamburguerArtesanal();
        assertEquals(35.0, burger.getCusto());
    }

    @Test
    public void deveRetornarDescricaoCorretaHamburguerSmash() {
        IHamburguer burger = new HamburguerSmash();
        assertEquals("Smash Duplo 90g", burger.getDescricao());
    }

    @Test
    public void deveRetornarDescricaoCorretaBatataRustica() {
        IAcompanhamento batata = new BatataRustica();
        assertEquals("Batata Rústica com Alecrim", batata.getTipoPorcao());
    }
}