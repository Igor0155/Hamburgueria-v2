package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;

import hamburgueria.catalogo.factory.HamburguerArtesanal;
import hamburgueria.catalogo.factory.IHamburguer;
import hamburgueria.montagem.decorator.BaconDecorator;
import hamburgueria.montagem.decorator.CheddarDecorator;

public class DecoratorTest {

    @Test
    public void deveRetornarPrecoBaseSemDecorador() {
        IHamburguer burger = new HamburguerArtesanal();
        assertEquals(35.0, burger.getCusto());
    }

    @Test
    public void deveSomarPrecoDoBaconDecorado() {
        IHamburguer burger = new BaconDecorator(new HamburguerArtesanal());
        assertEquals(40.0, burger.getCusto());
    }

    @Test
    public void deveSomarPrecoDoCheddarDecorado() {
        IHamburguer burger = new CheddarDecorator(new HamburguerArtesanal());
        assertEquals(39.5, burger.getCusto());
    }

    @Test
    public void deveSomarPrecoBaconECheddarEmpilhados() {
        IHamburguer burger = new CheddarDecorator(new BaconDecorator(new HamburguerArtesanal()));
        assertEquals(44.5, burger.getCusto());
    }

    @Test
    public void deveConcatenarDescricaoBacon() {
        IHamburguer burger = new BaconDecorator(new HamburguerArtesanal());
        assertEquals("Hambúrguer Artesanal 200g + Extra Bacon", burger.getDescricao());
    }

    @Test
    public void deveConcatenarDescricaoCheddar() {
        IHamburguer burger = new CheddarDecorator(new HamburguerArtesanal());
        assertEquals("Hambúrguer Artesanal 200g + Creme de Cheddar", burger.getDescricao());
    }

    @Test
    public void deveConcatenarMultiplasDescricoes() {
        IHamburguer burger = new CheddarDecorator(new BaconDecorator(new HamburguerArtesanal()));
        assertEquals("Hambúrguer Artesanal 200g + Extra Bacon + Creme de Cheddar", burger.getDescricao());
    }

    @Test
    public void devePermitirDecorarOpcionaisRepetidos() {
        IHamburguer burger = new BaconDecorator(new BaconDecorator(new HamburguerArtesanal()));
        assertEquals(45.0, burger.getCusto());
    }

    @Test
    public void deveSomarPrecoDoTriploBaconCorretamente() {
        IHamburguer burger = new BaconDecorator(new BaconDecorator(new BaconDecorator(new HamburguerArtesanal())));
        // 35.0 (Artesanal) + 5.0 + 5.0 + 5.0
        assertEquals(50.0, burger.getCusto());
    }

    @Test
    public void deveConcatenarDescricaoBaconCheddarBaconIntercalado() {
        IHamburguer burger = new BaconDecorator(new CheddarDecorator(new BaconDecorator(new HamburguerArtesanal())));
        assertEquals("Hambúrguer Artesanal 200g + Extra Bacon + Creme de Cheddar + Extra Bacon", burger.getDescricao());
    }

    @Test
    public void devePermitirDecorarUmHamburguerJaCustomizadoSemPerderReferencia() {
        IHamburguer base = new HamburguerArtesanal();
        IHamburguer decorado1 = new BaconDecorator(base);
        IHamburguer decorado2 = new CheddarDecorator(decorado1);

        assertNotSame(base, decorado2);
    }
}