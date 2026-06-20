package hamburgueria.cozinha.template;

import hamburgueria.cozinha.state.PedidoCozinha;

public class PreparoFritadeira extends PreparoTemplate {
    @Override
    protected void prepararEspecialidade(PedidoCozinha contexto) {
        // Simula mergulhar em óleo 180°C e escorrer
    }
}