package hamburgueria.relatorios.visitor;

public class RelatorioLucratividadeVisitor implements IVisitorCardapio {
    private double lucroProjetadoTotal = 0.0;
    private int setoresAnalisados = 0;

    @Override
    public void visitarItemSimples(ItemSimplesVisitavel item) {
        this.lucroProjetadoTotal += (item.getPrecoVenda() - item.getCustoProducao());
    }

    @Override
    public void visitarCategoria(CategoriaVisitavel categoria) {
        this.setoresAnalisados++;
    }

    public double getLucroProjetadoTotal() {
        return this.lucroProjetadoTotal;
    }

    public int getSetoresAnalisados() {
        return this.setoresAnalisados;
    }
}