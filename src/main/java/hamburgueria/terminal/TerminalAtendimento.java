package hamburgueria.terminal;

import java.util.ArrayList;
import java.util.List;

import hamburgueria.carrinho.CarrinhoCompras;
import hamburgueria.carrinho.command.AdicionarItemCommand;
import hamburgueria.carrinho.command.InvocadorCarrinho;
import hamburgueria.catalogo.factory.IComboFactory;
import hamburgueria.catalogo.factory.IHamburguer;
import hamburgueria.catalogo.iterator.IIteradorCardapio;
import hamburgueria.catalogo.prototype.IPrototipoCombo;
import hamburgueria.checkout.facade.PedidoFacade;
import hamburgueria.financeiro.bridge.MetodoPagamentoAbstraction;
import hamburgueria.financeiro.proxy.IGatewayPagamento;
import hamburgueria.financeiro.strategy.IEstrategiaPrecificacao;

public class TerminalAtendimento {
    private final InvocadorCarrinho invocador;
    private final CarrinhoCompras carrinho;

    public TerminalAtendimento(InvocadorCarrinho invocador, CarrinhoCompras carrinho) {
        if (invocador == null || carrinho == null) {
            throw new IllegalArgumentException("Terminal não pode iniciar sem invocador e carrinho.");
        }
        this.invocador = invocador;
        this.carrinho = carrinho;
    }

    public List<Object> navegarCardapio(IIteradorCardapio iterador) {
        if (iterador == null) {
            throw new IllegalArgumentException("Iterador não fornecido para navegação.");
        }
        List<Object> itensVisiveisNaTela = new ArrayList<>();
        // A lógica de repetição aqui é estrutural do padrão Iterator e pertence à
        // classe de produção, não ao teste.
        while (iterador.temProximo()) {
            itensVisiveisNaTela.add(iterador.proximo());
        }
        return itensVisiveisNaTela;
    }

    public void adicionarComboPronto(IComboFactory comboFactory) {
        if (comboFactory == null) {
            throw new IllegalArgumentException("Fábrica de combos não fornecida.");
        }
        // Aciona a Factory e converte imediatamente em um Command para o Carrinho
        IHamburguer burger = comboFactory.criarHamburguer();
        invocador.executarComando(new AdicionarItemCommand(this.carrinho, burger));
    }

    public IPrototipoCombo repetirUltimoPedido(IPrototipoCombo ultimoPedidoSalvo) {
        if (ultimoPedidoSalvo == null) {
            throw new IllegalArgumentException("Nenhum pedido anterior para repetir.");
        }
        // Aciona o Prototype para poupar o Builder
        return ultimoPedidoSalvo.clonar();
    }

    public void desfazerUltimaAcao() {
        // Delega para o Command/Invocador
        invocador.desfazerUltimoComando();
    }

    public boolean fecharPedido(PedidoFacade facade, IEstrategiaPrecificacao estrategia,
            IGatewayPagamento gateway, MetodoPagamentoAbstraction metodo) {
        if (facade == null) {
            throw new IllegalArgumentException("Sistema de checkout indisponível.");
        }
        // Delega para a Facade, que acionará o Strategy, o Proxy e a Bridge
        return facade.finalizarPedido(this.carrinho, estrategia, gateway, metodo);
    }

    public void processarRelatorioCardapio(hamburgueria.relatorios.RelatorioGerencial gerencial,
            hamburgueria.relatorios.visitor.IVisitorCardapio visitor,
            hamburgueria.catalogo.composite.IItemCardapio cardapioRaiz) {
        gerencial.extrairRelatorio(visitor, cardapioRaiz);
    }

    public CarrinhoCompras getCarrinhoAtual() {
        return this.carrinho;
    }
}