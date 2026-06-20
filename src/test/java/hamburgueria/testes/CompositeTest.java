package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import hamburgueria.catalogo.composite.CategoriaComposite;
import hamburgueria.catalogo.composite.ProdutoLeaf;

public class CompositeTest {

    @Test
    public void deveRetornarNomeProdutoLeaf() {
        ProdutoLeaf produto = new ProdutoLeaf("Combo Jeanne", 45.0, "Bacon");
        assertEquals("Combo Jeanne", produto.getNome());
    }

    @Test
    public void deveRetornarPrecoProdutoLeaf() {
        ProdutoLeaf produto = new ProdutoLeaf("Combo D3", 55.0, "Carne Smash");
        assertEquals(55.0, produto.getPreco());
    }

    @Test
    public void deveRetornarCaloriasProdutoLeafIntegradoComFlyweight() {
        ProdutoLeaf produto = new ProdutoLeaf("Bacon Burger", 35.0, "Bacon");
        assertEquals(300, produto.getCaloriasTotais());
    }

    @Test
    public void deveLancarExcecaoAoAdicionarEmLeaf() {
        ProdutoLeaf produto = new ProdutoLeaf("Bacon Burger", 35.0, "Bacon");
        assertThrows(UnsupportedOperationException.class,
                () -> produto.adicionar(new ProdutoLeaf("Batata", 10.0, "Batata")));
    }

    @Test
    public void deveLancarExcecaoAoRemoverDeLeaf() {
        ProdutoLeaf produto = new ProdutoLeaf("Bacon Burger", 35.0, "Bacon");
        assertThrows(UnsupportedOperationException.class,
                () -> produto.remover(new ProdutoLeaf("Batata", 10.0, "Batata")));
    }

    @Test
    public void deveRetornarNomeCategoriaComposite() {
        CategoriaComposite categoria = new CategoriaComposite("Combos Promocionais");
        assertEquals("Combos Promocionais", categoria.getNome());
    }

    @Test
    public void deveSomarPrecosNaCategoriaComposite() {
        CategoriaComposite categoria = new CategoriaComposite("Menu");
        categoria.adicionar(new ProdutoLeaf("Lanche 1", 20.0, "Carne Smash"));
        categoria.adicionar(new ProdutoLeaf("Lanche 2", 30.0, "Bacon"));
        assertEquals(50.0, categoria.getPreco());
    }

    @Test
    public void deveSomarPrecosEmCategoriasAninhadas() {
        CategoriaComposite raiz = new CategoriaComposite("Cardapio");
        CategoriaComposite sub = new CategoriaComposite("Bebidas");
        sub.adicionar(new ProdutoLeaf("Refrigerante", 8.0, "Refri"));
        raiz.adicionar(new ProdutoLeaf("Lanche", 20.0, "Carne Smash"));
        raiz.adicionar(sub);
        assertEquals(28.0, raiz.getPreco());
    }

    @Test
    public void deveRetornarPrecoZeroParaCategoriaTotalmenteVazia() {
        CategoriaComposite categoria = new CategoriaComposite("Menu Inativo");
        assertEquals(0.0, categoria.getPreco());
    }

    @Test
    public void deveManterPrecoInalteradoAoTentarRemoverItemInexistente() {
        CategoriaComposite categoria = new CategoriaComposite("Menu");
        categoria.adicionar(new ProdutoLeaf("Lanche", 25.0, "Carne Smash"));
        categoria.remover(new ProdutoLeaf("Fantasma", 10.0, "Batata"));
        assertEquals(25.0, categoria.getPreco());
    }

    @Test
    public void devePermitirArvoreComMultiplasCategoriasVaziasAninhadas() {
        CategoriaComposite raiz = new CategoriaComposite("Raiz");
        CategoriaComposite sub1 = new CategoriaComposite("Sub 1");
        CategoriaComposite sub2 = new CategoriaComposite("Sub 2");
        sub1.adicionar(sub2);
        raiz.adicionar(sub1);
        assertEquals(0.0, raiz.getPreco());
    }
}