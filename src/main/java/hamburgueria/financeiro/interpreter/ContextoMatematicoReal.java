package hamburgueria.financeiro.interpreter;

import java.util.HashMap;
import java.util.Map;

public class ContextoMatematicoReal implements IContextoMatematico {
    private final Map<String, Double> variaveis = new HashMap<>();

    @Override
    public double obterValor(String variavel) {
        if (!variaveis.containsKey(variavel)) {
            throw new IllegalArgumentException("Variável não definida: " + variavel);
        }
        return variaveis.get(variavel);
    }

    @Override
    public void definirValor(String variavel, double valor) {
        variaveis.put(variavel, valor);
    }
}