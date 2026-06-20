package hamburgueria.cozinha.singleton;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private static EventBus instancia;
    private final List<IListener> inscritos;

    private EventBus() {
        this.inscritos = new ArrayList<>();
    }

    public static synchronized EventBus getInstancia() {
        if (instancia == null) {
            instancia = new EventBus();
        }
        return instancia;
    }

    public void inscrever(IListener listener) {
        if (!this.inscritos.contains(listener)) {
            this.inscritos.add(listener);
        }
    }

    public void desinscrever(IListener listener) {
        this.inscritos.remove(listener);
    }

    public void publicar(IEvento evento) {
        for (IListener ouvinte : new ArrayList<>(this.inscritos)) {
            ouvinte.aoReceberEvento(evento);
        }
    }

    public void limparParaTestes() {
        this.inscritos.clear();
    }

    public int getTotalInscritos() {
        return this.inscritos.size();
    }
}