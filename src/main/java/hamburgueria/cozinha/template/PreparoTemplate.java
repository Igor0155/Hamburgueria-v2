package hamburgueria.cozinha.template;

import hamburgueria.cozinha.state.PedidoCozinha;

public abstract class PreparoTemplate {
    private boolean concluido = false;

    public final void executarPreparoPadrao(PedidoCozinha contexto) {
        if (contexto == null)
            throw new IllegalArgumentException("Contexto de pedido ausente.");
        separarIngredientes(contexto);
        prepararEspecialidade(contexto);
        embalar(contexto);
        this.concluido = true;
    }

    protected void separarIngredientes(PedidoCozinha contexto) {
        // Lógica universal de separar insumos
    }

    protected abstract void prepararEspecialidade(PedidoCozinha contexto);

    protected void embalar(PedidoCozinha contexto) {
        // Lógica universal de embalar para envio
    }

    public boolean isConcluido() {
        return this.concluido;
    }
}