package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.catalogo.iterator.ColecaoItens;
import hamburgueria.catalogo.iterator.IIteradorCardapio;

public class IteratorTest {
    private ColecaoItens colecao;

    @BeforeEach
    public void setup() {
        colecao = new ColecaoItens();
    }

    @Test
    public void deveRetornarFalsoTemProximoEmColecaoVazia() {
        IIteradorCardapio iterador = colecao.criarIteradorPadrao();
        assertFalse(iterador.temProximo());
    }

    @Test
    public void deveLancarExcecaoAoChamarProximoEmVazia() {
        IIteradorCardapio iterador = colecao.criarIteradorPadrao();
        assertThrows(IndexOutOfBoundsException.class, () -> iterador.proximo());
    }

    @Test
    public void deveRetornarVerdadeiroTemProximoComUmItem() {
        colecao.adicionarItem("Burger");
        IIteradorCardapio iterador = colecao.criarIteradorPadrao();
        assertTrue(iterador.temProximo());
    }

    @Test
    public void deveExtrairPrimeiroItemCorretamente() {
        colecao.adicionarItem("Batata");
        IIteradorCardapio iterador = colecao.criarIteradorPadrao();
        assertEquals("Batata", iterador.proximo());
    }

    @Test
    public void deveAvancarIteradorAposPrimeiraExtracao() {
        colecao.adicionarItem("Batata");
        IIteradorCardapio iterador = colecao.criarIteradorPadrao();
        iterador.proximo();
        assertFalse(iterador.temProximo());
    }

    @Test
    public void devePreservarOrdemDaFilaAoIterar() {
        colecao.adicionarItem("Item 1");
        colecao.adicionarItem("Item 2");
        IIteradorCardapio iterador = colecao.criarIteradorPadrao();
        iterador.proximo(); // Descarta 1
        assertEquals("Item 2", iterador.proximo()); // Avalia 2
    }

    @Test
    public void deveLancarExcecaoAposEsgotarIterador() {
        colecao.adicionarItem("Item Único");
        IIteradorCardapio iterador = colecao.criarIteradorPadrao();
        iterador.proximo();
        assertThrows(IndexOutOfBoundsException.class, () -> iterador.proximo());
    }

    @Test
    public void deveGarantirInterfaceIIterador() {
        assertInstanceOf(IIteradorCardapio.class, colecao.criarIteradorPadrao());
    }
}