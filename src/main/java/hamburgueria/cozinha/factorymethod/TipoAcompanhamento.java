package hamburgueria.cozinha.factorymethod;

public class TipoAcompanhamento implements ITipoAlimento {
    @Override
    public String classificarCorte() {
        return "VEGETAL_CORTE";
    }
}