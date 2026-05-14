package com.jorgeviictor.smartphone.model;

/**
 * Enum que representa os possíveis estados operacionais de um dispositivo móvel.
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
public enum EstadoTelefone {

    /** Dispositivo ligado com conectividade total. */
    LIGADO("Ligado"),

    /** Dispositivo completamente desligado. */
    DESLIGADO("Desligado"),

    /** Dispositivo ligado, porém com conectividade de rede desabilitada. */
    MODO_AVIAO("Modo Avião");

    private final String descricao;

    EstadoTelefone(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição legível do estado.
     *
     * @return descrição formatada do estado atual
     */
    public String getDescricao() {
        return descricao;
    }
}
