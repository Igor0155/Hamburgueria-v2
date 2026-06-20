package hamburgueria.cozinha.factorymethod;

public class TipoHamburguer implements ITipoAlimento {
    @Override
    public String classificarCorte() {
        return "PROTEINA_MOLDE";
    }
}