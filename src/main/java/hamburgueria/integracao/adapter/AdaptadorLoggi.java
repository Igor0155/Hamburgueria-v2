package hamburgueria.integracao.adapter;

public class AdaptadorLoggi {
    private final ApiExternaLoggi apiExterna;

    public AdaptadorLoggi(ApiExternaLoggi apiExterna) {
        this.apiExterna = apiExterna;
    }

    public boolean despachar(IPedidoLogisticaInterno pedidoLocal) {
        if (pedidoLocal == null)
            throw new IllegalArgumentException("Pedido interno nulo");

        // Conversão do Objeto Interno para o Payload JSON da API Externa
        String jsonFormatado = String.format(
                "{\"tracking_id\": \"%s\", \"volume_cm3\": %f}",
                pedidoLocal.getIdentificadorFiscal(),
                pedidoLocal.getVolumePacoteCubico());

        return this.apiExterna.criarRotaEntregaJson(jsonFormatado);
    }
}