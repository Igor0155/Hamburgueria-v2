package hamburgueria.relatorios;

import hamburgueria.relatorios.visitor.IVisitavel;
import hamburgueria.relatorios.visitor.IVisitorCardapio;

public class RelatorioGerencial {
    public void extrairRelatorio(IVisitorCardapio motorRelatorio, IVisitavel raizCardapio) {
        if (motorRelatorio == null || raizCardapio == null) {
            throw new IllegalArgumentException("Parâmetros do relatório inválidos.");
        }
        raizCardapio.aceitar(motorRelatorio);
    }
}