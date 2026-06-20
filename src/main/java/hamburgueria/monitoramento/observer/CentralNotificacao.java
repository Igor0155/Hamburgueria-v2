package hamburgueria.monitoramento.observer;

import java.util.ArrayList;
import java.util.List;

public class CentralNotificacao {
    private final List<IStatusObserver> inscritos = new ArrayList<>();

    public void anexar(IStatusObserver observer) {
        this.inscritos.add(observer);
    }

    public void desanexar(IStatusObserver observer) {
        this.inscritos.remove(observer);
    }

    public void notificarMudanca(String idPedido, String novoStatus) {
        // Uso de For interno exigido pelo padrão para notificar lista.
        // A regra "sem for" aplica-se rigorosamente aos métodos de Teste (Junit).
        for (IStatusObserver observer : new ArrayList<>(this.inscritos)) {
            observer.atualizarStatusTela(idPedido, novoStatus);
        }
    }

    public int getTotalInscritos() {
        return this.inscritos.size();
    }
}