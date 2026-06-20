package hamburgueria.monitoramento.observer;

public interface IStatusObserver {
    void atualizarStatusTela(String idPedido, String novoStatus);

    String getUltimaMensagemRecebida();
}