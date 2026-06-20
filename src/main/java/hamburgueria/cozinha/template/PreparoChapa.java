package hamburgueria.cozinha.template;

import hamburgueria.cozinha.state.PedidoCozinha;

public class PreparoChapa extends PreparoTemplate {
    @Override
    protected void prepararEspecialidade(PedidoCozinha contexto) {
        // Simula ligar a chapa, smash, crosta e derreter queijo
    }
}