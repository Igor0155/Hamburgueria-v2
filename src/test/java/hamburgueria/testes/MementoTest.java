
package hamburgueria.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hamburgueria.carrinho.CarrinhoCompras;
import hamburgueria.carrinho.memento.CarrinhoMemento;
import hamburgueria.carrinho.memento.ZeladorCarrinho;
import hamburgueria.catalogo.factory.HamburguerArtesanal;

public class MementoTest {
    private CarrinhoCompras carrinho;
    private ZeladorCarrinho zelador;

    @BeforeEach
    public void setup() {
        carrinho = new CarrinhoCompras();
        zelador = new ZeladorCarrinho();
    }

    @Test
    public void deveSalvarEstadoInicial() {
        carrinho.adicionarItem(new HamburguerArtesanal());
        zelador.salvar(carrinho.salvarEstado());
        assertEquals(1, zelador.getTamanhoHistorico());
    }

    @Test
    public void deveRestaurarEstadoAnterior() {
        carrinho.adicionarItem(new HamburguerArtesanal());
        zelador.salvar(carrinho.salvarEstado());
        carrinho.esvaziar();
        carrinho.restaurarEstado(zelador.restaurar());
        assertEquals(1, carrinho.getItens().size());
    }

    @Test
    public void deveGarantirImutabilidadeDoEstadoSalvo() {
        carrinho.adicionarItem(new HamburguerArtesanal());
        CarrinhoMemento memento = carrinho.salvarEstado();
        assertThrows(UnsupportedOperationException.class, () -> memento.getEstadoSalvo().clear());
    }

    @Test
    public void deveLancarExcecaoRestaurarHistoricoVazio() {
        assertThrows(IllegalStateException.class, () -> zelador.restaurar());
    }

    @Test
    public void deveSalvarMultiplosEstadosSequenciais() {
        zelador.salvar(carrinho.salvarEstado());
        carrinho.adicionarItem(new HamburguerArtesanal());
        zelador.salvar(carrinho.salvarEstado());
        assertEquals(2, zelador.getTamanhoHistorico());
    }

    @Test
    public void deveRestaurarEstadoVazioCorretamente() {
        zelador.salvar(carrinho.salvarEstado());
        carrinho.adicionarItem(new HamburguerArtesanal());
        carrinho.restaurarEstado(zelador.restaurar());
        assertEquals(0, carrinho.getItens().size());
    }

    @Test
    public void deveVerificarTamanhoCorretoAposRestaurar() {
        zelador.salvar(carrinho.salvarEstado());
        zelador.restaurar();
        assertEquals(0, zelador.getTamanhoHistorico());
    }

    @Test
    public void deveGarantirIndependenciaDeEstadoModificadoAposSalvar() {
        zelador.salvar(carrinho.salvarEstado());
        carrinho.adicionarItem(new HamburguerArtesanal());
        CarrinhoMemento memento = zelador.restaurar();
        assertEquals(0, memento.getEstadoSalvo().size());
    }

    @Test
    public void deveSalvarERestaurarCarrinhoAbsolutamenteVazio() {
        CarrinhoCompras carrinho = new CarrinhoCompras();
        ZeladorCarrinho zelador = new ZeladorCarrinho();

        zelador.salvar(carrinho.salvarEstado());
        carrinho.restaurarEstado(zelador.restaurar());

        assertEquals(0, carrinho.getItens().size());
    }

    @Test
    public void deveGarantirQueMetodoRestaurarDoZeladorRemoveOMementoDaPilha() {
        CarrinhoCompras carrinho = new CarrinhoCompras();
        ZeladorCarrinho zelador = new ZeladorCarrinho();

        zelador.salvar(carrinho.salvarEstado());
        zelador.restaurar();

        assertEquals(0, zelador.getTamanhoHistorico());
    }

    @Test
    public void deveValidarQueEstadoSalvoNaoEhAfetadoPeloEsvaziamentoDoCarrinhoOriginator() {
        CarrinhoCompras carrinho = new CarrinhoCompras();
        carrinho.adicionarItem(new hamburgueria.catalogo.factory.HamburguerSmash());
        CarrinhoMemento memento = carrinho.salvarEstado();

        carrinho.esvaziar();

        assertEquals(1, memento.getEstadoSalvo().size());
    }
}