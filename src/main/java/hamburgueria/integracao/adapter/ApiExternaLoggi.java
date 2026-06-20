package hamburgueria.integracao.adapter;

public class ApiExternaLoggi {
    public boolean criarRotaEntregaJson(String jsonPayload) {
        // Simula a recepção rígida da API de terceiros
        if (jsonPayload == null || !jsonPayload.contains("{") || !jsonPayload.contains("}")) {
            throw new IllegalArgumentException("Payload Invalido - 400 Bad Request");
        }
        return jsonPayload.contains("tracking_id");
    }
}