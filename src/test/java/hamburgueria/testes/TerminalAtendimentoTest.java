package hamburgueria.testes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.carrinho.CarrinhoCompras;
import hamburgueria.carrinho.command.InvocadorCarrinho;
import hamburgueria.catalogo.composite.CategoriaComposite;
import hamburgueria.catalogo.factory.ComboArtesanalFactory;
import hamburgueria.catalogo.iterator.ColecaoItens;
import hamburgueria.catalogo.iterator.IIteradorCardapio;
import hamburgueria.catalogo.prototype.ComboHistorico;
import hamburgueria.catalogo.prototype.IPrototipoCombo;
import hamburgueria.checkout.facade.PedidoFacade;
import hamburgueria.financeiro.bridge.PagamentoPix;
import hamburgueria.financeiro.bridge.StripeAPI;
import hamburgueria.financeiro.proxy.GatewayReal;
import hamburgueria.financeiro.strategy.PrecoTaxaNoturnaStrategy;
import hamburgueria.relatorios.RelatorioGerencial;
import hamburgueria.relatorios.visitor.RelatorioLucratividadeVisitor;
import hamburgueria.terminal.TerminalAtendimento;

public class TerminalAtendimentoTest {
    private TerminalAtendimento terminal;
    private InvocadorCarrinho invocador;
    private CarrinhoCompras carrinho;

    @BeforeEach
    public void setup() {
        invocador = new InvocadorCarrinho();
        carrinho = new CarrinhoCompras();
        terminal = new TerminalAtendimento(invocador, carrinho);
    }

    @Test
    public void deveLancarExcecaoAoCriarTerminalComDependenciasNulas() {
        assertThrows(IllegalArgumentException.class, () -> new TerminalAtendimento(null, null));
    }

    @Test
    public void deveNavegarNoCardapioEExtrairTodosOsItens() {
        ColecaoItens menu = new ColecaoItens();
        menu.adicionarItem("Refrigerante");
        menu.adicionarItem("Fritas");
        IIteradorCardapio iterador = menu.criarIteradorPadrao();

        List<Object> itensNaTela = terminal.navegarCardapio(iterador);

        assertEquals(2, itensNaTela.size());
    }

    @Test
    public void deveLancarExcecaoParaIteradorNulo() {
        assertThrows(IllegalArgumentException.class, () -> terminal.navegarCardapio(null));
    }

    @Test
    public void deveAdicionarComboAoCarrinhoUsandoAFactory() {
        terminal.adicionarComboPronto(new ComboArtesanalFactory());
        assertEquals(1, terminal.getCarrinhoAtual().getItens().size());
    }

    @Test
    public void deveLancarExcecaoSeTentarAdicionarComboComFactoryNula() {
        assertThrows(IllegalArgumentException.class, () -> terminal.adicionarComboPronto(null));
    }

    @Test
    public void deveClonarUltimoPedidoComSucesso() {
        ComboHistorico pedidoAnterior = new ComboHistorico("Combo Casal", 80.0, "HASH123");
        IPrototipoCombo clone = terminal.repetirUltimoPedido(pedidoAnterior);
        assertNotSame(pedidoAnterior, clone);
    }

    @Test
    public void deveDesfazerAdicaoAoCarrinhoComSucesso() {
        terminal.adicionarComboPronto(new ComboArtesanalFactory());
        terminal.desfazerUltimaAcao();
        assertEquals(0, terminal.getCarrinhoAtual().getItens().size());
    }

    @Test
    public void deveFecharPedidoComSucessoOrquestrandoFacade() {
        terminal.adicionarComboPronto(new ComboArtesanalFactory());
        PedidoFacade facade = new PedidoFacade();
        PrecoTaxaNoturnaStrategy estrategia = new PrecoTaxaNoturnaStrategy();
        GatewayReal gateway = new GatewayReal(new PagamentoPix(new StripeAPI()));

        assertTrue(terminal.fecharPedido(facade, estrategia, gateway));
    }

    @Test
    public void deveRetornarListaVaziaAoNavegarComIteradorSemItens() {
        IIteradorCardapio iteradorVazio = new ColecaoItens().criarIteradorPadrao();
        List<Object> resultado = terminal.navegarCardapio(iteradorVazio);

        assertEquals(0, resultado.size());
    }

    @Test
    public void deveLancarExcecaoAoTentarRepetirPedidoNuloNoTerminal() {
        assertThrows(IllegalArgumentException.class, () -> terminal.repetirUltimoPedido(null));
    }

    @Test
    public void deveGarantirQueAcionarDesfazerComCarrinhoVazioGereExcecaoDoCommand() {
        assertThrows(IllegalStateException.class, () -> terminal.desfazerUltimaAcao());
    }

    @Test
    public void deveDelegarProcessamentoDeRelatorioSemErrosComParametrosValidos() {
        RelatorioGerencial gerencial = new RelatorioGerencial();
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();
        CategoriaComposite cardapio = new CategoriaComposite("Menu Principal");

        assertDoesNotThrow(() -> terminal.processarRelatorioCardapio(gerencial, visitor, cardapio));
    }

    @Test
    public void deveLancarNullPointerExceptionSeRelatorioGerencialForNuloNoProcessamento() {
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();
        CategoriaComposite cardapio = new CategoriaComposite("Menu Principal");

        assertThrows(NullPointerException.class, () -> terminal.processarRelatorioCardapio(null, visitor, cardapio));
    }

    @Test
    public void deveLancarExcecaoSeVisitorForNuloNoProcessamentoDoTerminal() {
        RelatorioGerencial gerencial = new RelatorioGerencial();
        CategoriaComposite cardapio = new CategoriaComposite("Menu Principal");

        // O RelatorioGerencial joga IllegalArgumentException se o visitor for nulo
        assertThrows(IllegalArgumentException.class,
                () -> terminal.processarRelatorioCardapio(gerencial, null, cardapio));
    }

    @Test
    public void deveLancarExcecaoSeCardapioForNuloNoProcessamentoDoTerminal() {
        RelatorioGerencial gerencial = new RelatorioGerencial();
        RelatorioLucratividadeVisitor visitor = new RelatorioLucratividadeVisitor();

        // O RelatorioGerencial joga IllegalArgumentException se a raiz for nula
        assertThrows(IllegalArgumentException.class,
                () -> terminal.processarRelatorioCardapio(gerencial, visitor, null));
    }

    @Test
    public void deveRetornarAInstanciaExataDoCarrinhoInjetadoNoConstrutor() {
        assertSame(carrinho, terminal.getCarrinhoAtual());
    }

    @Test
    public void deveConterExatamenteZeroItensAposExtracaoDoCarrinhoInicial() {
        assertEquals(0, terminal.getCarrinhoAtual().getItens().size());
    }

    @Test
    public void devePropagarIllegalArgumentExceptionAoTentarFecharPedidoComFacadeNula() {
        assertThrows(IllegalArgumentException.class, () -> terminal.fecharPedido(null, null, null));
    }

    @Test
    public void devePropagarNullPointerExceptionSeInvocadorForNuloNaCriacao() {
        assertThrows(IllegalArgumentException.class, () -> new TerminalAtendimento(null, carrinho));
    }
}