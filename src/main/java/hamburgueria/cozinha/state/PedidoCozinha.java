package hamburgueria.cozinha.state;

import java.util.ArrayList;
import java.util.List;

import hamburgueria.cozinha.factorymethod.ITipoAlimento;

public class PedidoCozinha {
    private IEstadoPedido estadoAtual;
    private final Object identificador;
    private final List<ITipoAlimento> alimentosParaPreparo;

    public PedidoCozinha(Object identificador) {
        this.identificador = identificador;
        this.estadoAtual = new EstadoPendente();
        this.alimentosParaPreparo = new ArrayList<>();
    }

    public void adicionarAlimentoParaPreparo(ITipoAlimento alimento) {
        this.alimentosParaPreparo.add(alimento);
    }

    public List<ITipoAlimento> getAlimentos() {
        return this.alimentosParaPreparo;
    }

    public void mudarEstado(IEstadoPedido novoEstado) {
        this.estadoAtual = novoEstado;
    }

    public IEstadoPedido getEstadoAtual() {
        return this.estadoAtual;
    }

    public Object getIdentificador() {
        return this.identificador;
    }

    public void iniciarPreparo() {
        this.estadoAtual.iniciarPreparo(this);
    }

    public void finalizarPreparo() {
        this.estadoAtual.finalizarPreparo(this);
    }

    public void despachar() {
        this.estadoAtual.despachar(this);
    }

    public void cancelar() {
        this.estadoAtual.cancelar(this);
    }
}