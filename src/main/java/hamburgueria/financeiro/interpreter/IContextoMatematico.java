package hamburgueria.financeiro.interpreter;

public interface IContextoMatematico {
    double obterValor(String variavel);

    void definirValor(String variavel, double valor);
}