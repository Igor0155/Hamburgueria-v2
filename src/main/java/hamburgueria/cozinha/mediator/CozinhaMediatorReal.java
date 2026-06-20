package hamburgueria.cozinha.mediator;

import hamburgueria.cozinha.chain.IVerificadorQualidade;
import hamburgueria.cozinha.factorymethod.IEstacaoFactory;
import hamburgueria.cozinha.factorymethod.ITipoAlimento;
import hamburgueria.cozinha.singleton.IEvento;
import hamburgueria.cozinha.state.PedidoCozinha;
import hamburgueria.cozinha.template.PreparoTemplate;
import hamburgueria.integracao.adapter.AdaptadorLoggi;
import hamburgueria.integracao.adapter.IPedidoLogisticaInterno;
import hamburgueria.integracao.adapter.PedidoInternoConcreto;

public class CozinhaMediatorReal implements ICozinhaMediator {
    private final IEstacaoFactory factory;
    private final IVerificadorQualidade qcFinal;
    private final AdaptadorLoggi adaptadorLogistica;

    public CozinhaMediatorReal(IEstacaoFactory factory, IVerificadorQualidade qcFinal,
            AdaptadorLoggi adaptadorLogistica) {
        this.factory = factory;
        this.qcFinal = qcFinal;
        this.adaptadorLogistica = adaptadorLogistica;
    }

    @Override
    public void aoReceberEvento(IEvento evento) {
        if (evento.getNomeEvento().equals("PEDIDO_PAGO") && evento.getCargaDados() instanceof PedidoCozinha) {
            orquestrarNovoPedido((PedidoCozinha) evento.getCargaDados());
        }
    }

    @Override
    public void orquestrarNovoPedido(PedidoCozinha pedido) {
        pedido.iniciarPreparo();

        // PONTE DO GRUPO 3: Usa os ITipoAlimento reais contidos no pedido
        for (ITipoAlimento alimento : pedido.getAlimentos()) {
            PreparoTemplate estacao = factory.instanciarEstacao(alimento);
            estacao.executarPreparoPadrao(pedido);
        }

        pedido.finalizarPreparo();
        submeterParaControleQualidade(pedido);
    }

    @Override
    public void submeterParaControleQualidade(PedidoCozinha pedido) {
        this.qcFinal.avaliar(pedido);
        pedido.despachar();

        IPedidoLogisticaInterno payloadLogistica = new PedidoInternoConcreto(
                pedido.getIdentificador().toString(),
                15.0 // Volume padrão simulado
        );
        this.adaptadorLogistica.despachar(payloadLogistica);
    }
}