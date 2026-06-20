package hamburgueria.cozinha.chain;

import hamburgueria.cozinha.state.PedidoCozinha;

public abstract class BaseVerificadorQualidade implements IVerificadorQualidade {
    protected IVerificadorQualidade proximoElo;

    @Override
    public void setProximo(IVerificadorQualidade proximoElo) {
        this.proximoElo = proximoElo;
    }

    protected void repassar(PedidoCozinha pedido) {
        if (this.proximoElo != null) {
            this.proximoElo.avaliar(pedido);
        }
    }
}