package hamburgueria.monitoramento.observer;

public class TelaClienteApp implements IStatusObserver {
    private String ultimaNotificacao;

    @Override
    public void atualizarStatusTela(String idPedido, String novoStatus) {
        this.ultimaNotificacao = "Push: Seu pedido " + idPedido + " agora está " + novoStatus;
    }

    @Override
    public String getUltimaMensagemRecebida() {
        return this.ultimaNotificacao;
    }
}