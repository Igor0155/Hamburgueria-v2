package hamburgueria.integracao.adapter;

public class PedidoInternoConcreto implements IPedidoLogisticaInterno {
    private final String id;
    private final double volume;

    public PedidoInternoConcreto(String id, double volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String getIdentificadorFiscal() {
        return this.id;
    }

    @Override
    public double getVolumePacoteCubico() {
        return this.volume;
    }
}