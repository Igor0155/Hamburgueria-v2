package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.carrinho.CarrinhoCompras;
import hamburgueria.catalogo.factory.HamburguerArtesanal;
import hamburgueria.catalogo.factory.HamburguerSmash;
import hamburgueria.catalogo.factory.IHamburguer;

public class CarrinhoComprasDirectTest {
    private CarrinhoCompras carrinho;
    private IHamburguer burgerArtesanal;
    private IHamburguer burgerSmash;

    @BeforeEach
    public void setup() {
        carrinho = new CarrinhoCompras();
        burgerArtesanal = new HamburguerArtesanal(); // Custo: 35.0
        burgerSmash = new HamburguerSmash(); // Custo: 28.0
    }

    @Test
    public void deveRetornarSubtotalZeroParaCarrinhoAbsolutamenteVazio() {
        assertEquals(0.0, carrinho.getSubtotal());
    }

    @Test
    public void deveRetornarSubtotalCorretoParaApenasUmItem() {
        carrinho.adicionarItem(burgerArtesanal);
        assertEquals(35.0, carrinho.getSubtotal());
    }

    @Test
    public void deveRetornarSubtotalCorretoParaMultiplosItensDiferentes() {
        carrinho.adicionarItem(burgerArtesanal);
        carrinho.adicionarItem(burgerSmash);
        assertEquals(63.0, carrinho.getSubtotal());
    }

    @Test
    public void deveLancarExcecaoAoRemoverItemQueNaoFoiAdicionado() {
        assertThrows(IllegalArgumentException.class, () -> carrinho.removerItem(burgerArtesanal));
    }

    @Test
    public void deveRemoverItemExistenteComSucessoDiretamente() {
        carrinho.adicionarItem(burgerSmash);
        carrinho.removerItem(burgerSmash);
        assertEquals(0, carrinho.getItens().size());
    }

    @Test
    public void deveGarantirQueEsvaziarLimpaTodosOsItensSimultaneamente() {
        carrinho.adicionarItem(burgerArtesanal);
        carrinho.adicionarItem(burgerSmash);
        carrinho.esvaziar();
        assertEquals(0, carrinho.getItens().size());
    }

    @Test
    public void deveRetornarListaDeItensVaziaInicialmente() {
        assertEquals(0, carrinho.getItens().size());
    }

    @Test
    public void deveAtualizarSubtotalCorretamenteAposRemocao() {
        carrinho.adicionarItem(burgerArtesanal);
        carrinho.adicionarItem(burgerSmash);
        carrinho.removerItem(burgerArtesanal);
        assertEquals(28.0, carrinho.getSubtotal());
    }
}