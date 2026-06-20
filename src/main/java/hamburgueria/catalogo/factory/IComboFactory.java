package hamburgueria.catalogo.factory;

public interface IComboFactory {
    IHamburguer criarHamburguer();

    IAcompanhamento criarAcompanhamento();
}