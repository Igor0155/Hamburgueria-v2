package hamburgueria.catalogo.flyweight;

import java.util.HashMap;
import java.util.Map;

public class IngredienteFlyweightFactory {
    private static final Map<String, IngredienteDetalhe> cache = new HashMap<>();

    public static IngredienteDetalhe obterDetalhe(String nomeIngrediente) {
        if (!cache.containsKey(nomeIngrediente)) {
            // Simulamento de carregamento pesado.
            IngredienteDetalhe detalhePesado = new IngredienteDetalhe(
                    "img_base64_pesada_" + nomeIngrediente,
                    "Tabela de " + nomeIngrediente,
                    calcularCaloriasSimulada(nomeIngrediente));
            cache.put(nomeIngrediente, detalhePesado);
        }
        return cache.get(nomeIngrediente);
    }

    private static int calcularCaloriasSimulada(String nome) {
        if (nome.equals("Bacon"))
            return 300;
        if (nome.equals("Carne Smash"))
            return 250;
        if (nome.equals("Whey Protein"))
            return 120;
        return 100;
    }

    public static int getTamanhoCache() {
        return cache.size();
    }

    public static void limparCache() {
        cache.clear();
    }
}