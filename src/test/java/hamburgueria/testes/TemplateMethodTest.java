package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import hamburgueria.cozinha.state.PedidoCozinha;
import hamburgueria.cozinha.template.PreparoChapa;
import hamburgueria.cozinha.template.PreparoFritadeira;
import hamburgueria.cozinha.template.PreparoTemplate;

public class TemplateMethodTest {

    @Test
    public void deveExecutarFluxoCompletoDaChapa() {
        PreparoTemplate chapa = new PreparoChapa();
        chapa.executarPreparoPadrao(new PedidoCozinha("CTX"));
        assertTrue(chapa.isConcluido());
    }

    @Test
    public void deveExecutarFluxoCompletoDaFritadeira() {
        PreparoTemplate fritadeira = new PreparoFritadeira();
        fritadeira.executarPreparoPadrao(new PedidoCozinha("CTX"));
        assertTrue(fritadeira.isConcluido());
    }

    @Test
    public void deveLancarExcecaoSemContextoNoPreparoDaChapa() {
        PreparoTemplate chapa = new PreparoChapa();
        assertThrows(IllegalArgumentException.class, () -> chapa.executarPreparoPadrao(null));
    }

    @Test
    public void deveNascerComoNaoConcluidoAChapa() {
        PreparoTemplate chapa = new PreparoChapa();
        assertFalse(chapa.isConcluido());
    }

    @Test
    public void deveNascerComoNaoConcluidoAFritadeira() {
        PreparoTemplate fritadeira = new PreparoFritadeira();
        assertFalse(fritadeira.isConcluido());
    }

    @Test
    public void deveGarantirHerdancaParaAChapa() {
        assertInstanceOf(PreparoTemplate.class, new PreparoChapa());
    }

    @Test
    public void deveGarantirHerdancaParaAFritadeira() {
        assertInstanceOf(PreparoTemplate.class, new PreparoFritadeira());
    }

    @Test
    public void devePermitirExecucoesMultiplasDeAcordoComInstancias() {
        PreparoTemplate instance1 = new PreparoChapa();
        PreparoTemplate instance2 = new PreparoChapa();
        instance1.executarPreparoPadrao(new PedidoCozinha("C"));
        assertNotEquals(instance1.isConcluido(), instance2.isConcluido());
    }
}