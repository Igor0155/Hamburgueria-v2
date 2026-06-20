package hamburgueria.cozinha.factorymethod;

import hamburgueria.cozinha.template.PreparoTemplate;

public interface IEstacaoFactory {
    PreparoTemplate instanciarEstacao(ITipoAlimento alimento);
}