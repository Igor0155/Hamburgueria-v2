package hamburgueria.cozinha.factorymethod;

import hamburgueria.cozinha.template.PreparoChapa;
import hamburgueria.cozinha.template.PreparoFritadeira;
import hamburgueria.cozinha.template.PreparoTemplate;

public class EstacaoFactoryReal implements IEstacaoFactory {
    @Override
    public PreparoTemplate instanciarEstacao(ITipoAlimento alimento) {
        if (alimento == null)
            throw new IllegalArgumentException("Alimento nulo.");

        if (alimento instanceof TipoHamburguer) {
            return new PreparoChapa();
        } else if (alimento instanceof TipoAcompanhamento) {
            return new PreparoFritadeira();
        }
        throw new IllegalArgumentException("Equipamento inexistente para o tipo de alimento.");
    }
}