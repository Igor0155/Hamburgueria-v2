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
        for (IStatusObserver observer : new ArrayList<>(this.inscritos)) {
            observer.atualizarStatusTela(idPedido, novoStatus);
        }
    }

    public int getTotalInscritos() {
        return this.inscritos.size();
    }
}