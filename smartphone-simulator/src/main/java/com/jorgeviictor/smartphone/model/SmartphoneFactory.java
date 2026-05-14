package com.jorgeviictor.smartphone.model;

/**
 * Factory responsável por criar instâncias pré-configuradas de {@link Smartphone}.
 *
 * <p>Implementa o padrão de projeto <b>Factory Method</b>, centralizando a lógica
 * de criação e permitindo o uso polimórfico via interfaces:</p>
 *
 * <pre>{@code
 * ReprodutorMusical meuIphone = SmartphoneFactory.criarIphone();
 * AparelhoTelefonico telefone = SmartphoneFactory.criarSamsungGalaxy();
 * }</pre>
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
public final class SmartphoneFactory {

    private SmartphoneFactory() {
        // Utility class — não deve ser instanciada
    }

    /**
     * Cria um Smartphone configurado como iPhone 15 Pro.
     *
     * @return nova instância de {@link Smartphone} representando um iPhone
     */
    public static Smartphone criarIphone() {
        return new Smartphone("iPhone 15 Pro", "Apple", 2023);
    }

    /**
     * Cria um Smartphone configurado como Samsung Galaxy S24 Ultra.
     *
     * @return nova instância de {@link Smartphone} representando um Samsung Galaxy
     */
    public static Smartphone criarSamsungGalaxy() {
        return new Smartphone("Galaxy S24 Ultra", "Samsung", 2024);
    }

    /**
     * Cria um Smartphone com especificações personalizadas.
     *
     * @param modelo        o modelo do dispositivo
     * @param fabricante    o fabricante
     * @param anoFabricacao o ano de fabricação
     * @return nova instância de {@link Smartphone} com as especificações fornecidas
     */
    public static Smartphone criarPersonalizado(String modelo, String fabricante, int anoFabricacao) {
        return new Smartphone(modelo, fabricante, anoFabricacao);
    }
}
