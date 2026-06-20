package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.carrinho.CarrinhoCompras;
import hamburgueria.carrinho.command.AdicionarItemCommand;
import hamburgueria.carrinho.command.ICarrinhoCommand;
import hamburgueria.carrinho.command.InvocadorCarrinho;
import hamburgueria.catalogo.factory.HamburguerArtesanal;
import hamburgueria.catalogo.factory.IHamburguer;

public class CommandTest {
    private CarrinhoCompras carrinho;
    private InvocadorCarrinho invocador;
    private IHamburguer burger;

    @BeforeEach
    public void setup() {
        carrinho = new CarrinhoCompras();
        invocador = new InvocadorCarrinho();
        burger = new HamburguerArtesanal(); // Mock estrutural
    }

    @Test
    public void deveExecutarAdicionarItem() {
        ICarrinhoCommand cmd = new AdicionarItemCommand(carrinho, burger);
        invocador.executarComando(cmd);
        assertEquals(1, carrinho.getItens().size());
    }

    @Test
    public void deveDesfazerAdicionarItem() {
        ICarrinhoCommand cmd = new AdicionarItemCommand(carrinho, burger);
        invocador.executarComando(cmd);
        invocador.desfazerUltimoComando();
        assertEquals(0, carrinho.getItens().size());
    }

    @Test
    public void deveAcumularDoisComandos() {
        invocador.executarComando(new AdicionarItemCommand(carrinho, burger));
        invocador.executarComando(new AdicionarItemCommand(carrinho, burger));
        assertEquals(2, carrinho.getItens().size());
    }

    @Test
    public void deveRetornarErroDesfazerSemHistorico() {
        assertThrows(IllegalStateException.class, () -> invocador.desfazerUltimoComando());
    }

    @Test
    public void deveManterEstadoCorretoAposMultiplosDesfazeres() {
        invocador.executarComando(new AdicionarItemCommand(carrinho, burger));
        invocador.executarComando(new AdicionarItemCommand(carrinho, burger));
        invocador.desfazerUltimoComando();
        invocador.desfazerUltimoComando();
        assertEquals(0, carrinho.getItens().size());
    }

    @Test
    public void deveLancarExcecaoAoRemoverItemInexistentePeloComandoDesfazer() {
        ICarrinhoCommand cmd = new AdicionarItemCommand(carrinho, burger);
        assertThrows(IllegalArgumentException.class, () -> cmd.desfazer());
    }

    @Test
    public void deveGarantirMesmaReferenciaAoAdicionarItemViaComando() {
        ICarrinhoCommand cmd = new AdicionarItemCommand(carrinho, burger);
        invocador.executarComando(cmd);
        assertSame(burger, carrinho.getItens().get(0));
    }

    @Test
    public void deveExecutarAcaoIsoladaSemInvocador() {
        ICarrinhoCommand cmd = new AdicionarItemCommand(carrinho, burger);
        cmd.executar();
        assertEquals(1, carrinho.getItens().size());
    }
}