package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import hamburgueria.montagem.builder.HamburguerCustomizado;
import hamburgueria.montagem.builder.MonsterWheyBuilder;
import hamburgueria.montagem.builder.MontadorDiretor;

public class BuilderTest {

    @Test
    public void deveAtribuirPaoCorretoNoBuilderManual() {
        HamburguerCustomizado burger = new HamburguerCustomizado();
        burger.setPao("Pao Brioche");
        assertEquals("Customizado: Pao Brioche, null, null", burger.getDescricao());
    }

    @Test
    public void deveAtribuirProteinaCorretaNoBuilderManual() {
        HamburguerCustomizado burger = new HamburguerCustomizado();
        burger.setProteina("Blend D3");
        assertEquals("Customizado: null, Blend D3, null", burger.getDescricao());
    }

    @Test
    public void deveAtribuirQueijoCorretoNoBuilderManual() {
        HamburguerCustomizado burger = new HamburguerCustomizado();
        burger.setQueijo("Prato");
        assertEquals("Customizado: null, null, Prato", burger.getDescricao());
    }

    @Test
    public void deveAtribuirPrecoBaseCorretoNoBuilderManual() {
        HamburguerCustomizado burger = new HamburguerCustomizado();
        burger.setPrecoBase(15.0);
        assertEquals(15.0, burger.getCusto());
    }

    @Test
    public void deveConstruirDescricaoCompletaMonsterWhey() {
        MonsterWheyBuilder builder = new MonsterWheyBuilder();
        MontadorDiretor diretor = new MontadorDiretor();
        diretor.setBuilder(builder);
        diretor.construirHamburguerFit();
        assertEquals("Customizado: Pão Integral Proteico, Hamburguer de Frango 250g, Queijo Minas Padrão",
                builder.obterResultado().getDescricao());
    }

    @Test
    public void deveConstruirPrecoCorretoMonsterWhey() {
        MonsterWheyBuilder builder = new MonsterWheyBuilder();
        MontadorDiretor diretor = new MontadorDiretor();
        diretor.setBuilder(builder);
        diretor.construirHamburguerFit();
        assertEquals(42.0, builder.obterResultado().getCusto());
    }

    @Test
    public void deveLimparEstadoAoChamarReset() {
        MonsterWheyBuilder builder = new MonsterWheyBuilder();
        builder.escolherPao();
        builder.reset();
        assertEquals("Customizado: null, null, null", builder.obterResultado().getDescricao());
    }

    @Test
    public void devePermitirMontagemSemDiretor() {
        MonsterWheyBuilder builder = new MonsterWheyBuilder();
        builder.escolherQueijo();
        assertEquals("Customizado: null, null, Queijo Minas Padrão", builder.obterResultado().getDescricao());
    }
}