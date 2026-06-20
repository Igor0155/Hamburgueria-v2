package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import hamburgueria.catalogo.composite.CategoriaComposite;
import hamburgueria.catalogo.composite.ProdutoLeaf;
import hamburgueria.relatorios.RelatorioGerencial;
import hamburgueria.relatorios.visitor.CategoriaVisitavel;
import hamburgueria.relatorios.visitor.ItemSimplesVisitavel;
import hamburgueria.relatorios.visitor.RelatorioLucratividadeVisitor;

public class VisitorTest {

    @Test
    public void deveCalcularLucratividadeExataDeItemUnico() {
        ItemSimplesVisitavel item = new ItemSimplesVisitavel(10.0, 35.0);
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();
        item.aceitar(visitor);
        assertEquals(25.0, visitor.getLucroProjetadoTotal());
    }

    @Test
    public void deveCalcularLucratividadeZeroParaItemSemMargem() {
        ItemSimplesVisitavel item = new ItemSimplesVisitavel(20.0, 20.0);
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();
        item.aceitar(visitor);
        assertEquals(0.0, visitor.getLucroProjetadoTotal());
    }

    @Test
    public void devePercorrerArvoreCompositeUsandoRelatorioGerencial() {
        RelatorioGerencial gerencial = new RelatorioGerencial();
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();
        CategoriaComposite raiz = new CategoriaComposite("Menu");
        raiz.adicionar(new ProdutoLeaf("Lanche", 100.0, "Carne Smash"));

        gerencial.extrairRelatorio(visitor, raiz);

        assertEquals(1, visitor.getSetoresAnalisados());
    }

    @Test
    public void deveAcumularLucroDaArvoreCompositePeloRelatorioGerencial() {
        RelatorioGerencial gerencial = new RelatorioGerencial();
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();
        CategoriaComposite raiz = new CategoriaComposite("Menu");
        raiz.adicionar(new ProdutoLeaf("Lanche", 100.0, "Carne Smash")); // Custo é 40% do preco (40.0). Lucro = 60.0

        gerencial.extrairRelatorio(visitor, raiz);

        assertEquals(60.0, visitor.getLucroProjetadoTotal());
    }

    @Test
    public void deveAcumularLucratividadeSequencialmente() {
        ItemSimplesVisitavel item1 = new ItemSimplesVisitavel(10.0, 20.0);
        ItemSimplesVisitavel item2 = new ItemSimplesVisitavel(5.0, 15.0);
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();
        item1.aceitar(visitor);
        item2.aceitar(visitor);
        assertEquals(20.0, visitor.getLucroProjetadoTotal());
    }

    @Test
    public void deveContabilizarVisitaNaCategoria() {
        CategoriaVisitavel categoria = new CategoriaVisitavel("Bebidas");
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();
        categoria.aceitar(visitor);
        assertEquals(1, visitor.getSetoresAnalisados());
    }

    @Test
    public void deveContabilizarMultiplasCategorias() {
        CategoriaVisitavel cat1 = new CategoriaVisitavel("Bebidas");
        CategoriaVisitavel cat2 = new CategoriaVisitavel("Sobremesas");
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();
        cat1.aceitar(visitor);
        cat2.aceitar(visitor);
        assertEquals(2, visitor.getSetoresAnalisados());
    }

    @Test
    public void deveManterLucratividadeIntactaAoVisitarCategoria() {
        CategoriaVisitavel categoria = new CategoriaVisitavel("Bebidas");
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();
        categoria.aceitar(visitor);
        assertEquals(0.0, visitor.getLucroProjetadoTotal());
    }

    @Test
    public void deveRetornarCustoCorretoAposInstancia() {
        ItemSimplesVisitavel item = new ItemSimplesVisitavel(12.5, 30.0);
        assertEquals(12.5, item.getCustoProducao());
    }

    @Test
    public void deveRetornarNomeSetorCorretoAposInstancia() {
        CategoriaVisitavel categoria = new CategoriaVisitavel("Cortes");
        assertEquals("Cortes", categoria.getNomeSetor());
    }
}