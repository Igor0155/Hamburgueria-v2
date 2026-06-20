package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;

import hamburgueria.catalogo.prototype.ComboHistorico;
import hamburgueria.catalogo.prototype.IPrototipoCombo;

public class PrototypeTest {

    @Test
    public void deveClonarComboComSucesso() {
        ComboHistorico original = new ComboHistorico("Combo Casal", 80.0, "HASH_123");
        IPrototipoCombo clone = original.clonar();
        assertNotNull(clone);
    }

    @Test
    public void deveGarantirInstanciasDiferentesNaClonagem() {
        ComboHistorico original = new ComboHistorico("Combo Casal", 80.0, "HASH_123");
        IPrototipoCombo clone = original.clonar();
        assertNotSame(original, clone);
    }

    @Test
    public void devePreservarNomeOriginalAoClonar() {
        ComboHistorico original = new ComboHistorico("Combo Smash", 50.0, "HASH_A");
        IPrototipoCombo clone = original.clonar();
        assertEquals("Combo Smash", clone.getNomeCombo());
    }

    @Test
    public void devePreservarPrecoOriginalAoClonar() {
        ComboHistorico original = new ComboHistorico("Combo Smash", 50.0, "HASH_A");
        IPrototipoCombo clone = original.clonar();
        assertEquals(50.0, clone.getPrecoTotal());
    }

    @Test
    public void devePreservarHashDeMontagemOriginalAoClonar() {
        ComboHistorico original = new ComboHistorico("Combo Smash", 50.0, "HASH_A");
        ComboHistorico clone = (ComboHistorico) original.clonar();
        assertEquals("HASH_A", clone.getHashMontagemComplexa());
    }

    @Test
    public void alteracaoNoCloneNaoDeveAfetarNomeOriginal() {
        ComboHistorico original = new ComboHistorico("Combo Fit", 40.0, "HASH_F");
        IPrototipoCombo clone = original.clonar();
        clone.setNomeCombo("Combo Fit Alterado");
        assertEquals("Combo Fit", original.getNomeCombo());
    }

    @Test
    public void alteracaoNoCloneNaoDeveAfetarPrecoOriginal() {
        ComboHistorico original = new ComboHistorico("Combo Fit", 40.0, "HASH_F");
        IPrototipoCombo clone = original.clonar();
        clone.setPrecoTotal(60.0);
        assertEquals(40.0, original.getPrecoTotal());
    }

    @Test
    public void deveAtualizarPrecoNoCloneComSucesso() {
        ComboHistorico original = new ComboHistorico("Combo Fit", 40.0, "HASH_F");
        IPrototipoCombo clone = original.clonar();
        clone.setPrecoTotal(60.0);
        assertEquals(60.0, clone.getPrecoTotal());
    }
}