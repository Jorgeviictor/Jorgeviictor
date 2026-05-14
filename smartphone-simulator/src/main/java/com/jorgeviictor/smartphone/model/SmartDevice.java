package com.jorgeviictor.smartphone.model;

import com.jorgeviictor.smartphone.exceptions.DispositivoDesligadoException;

/**
 * Classe abstrata base que representa um dispositivo inteligente (Smart Device).
 *
 * <p>Define os atributos e comportamentos comuns a todos os dispositivos inteligentes,
 * como gerenciamento de estado (ligado/desligado/modo avião) e informações de hardware.</p>
 *
 * <p>Segue o princípio de responsabilidade única (SRP): esta classe é responsável
 * exclusivamente pelo ciclo de vida e estado do dispositivo.</p>
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
public abstract class SmartDevice {

    private final String modelo;
    private final String fabricante;
    private final int anoFabricacao;
    private EstadoTelefone estado;

    /**
     * Inicializa o dispositivo no estado {@link EstadoTelefone#DESLIGADO}.
     *
     * @param modelo        o modelo do dispositivo
     * @param fabricante    o fabricante do dispositivo
     * @param anoFabricacao o ano de fabricação
     */
    protected SmartDevice(String modelo, String fabricante, int anoFabricacao) {
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.anoFabricacao = anoFabricacao;
        this.estado = EstadoTelefone.DESLIGADO;
    }

    /**
     * Liga o dispositivo, alterando o estado para {@link EstadoTelefone#LIGADO}.
     */
    public void ligarDispositivo() {
        this.estado = EstadoTelefone.LIGADO;
        System.out.printf("[%s] Dispositivo ligado com sucesso.%n", modelo);
    }

    /**
     * Desliga o dispositivo, alterando o estado para {@link EstadoTelefone#DESLIGADO}.
     */
    public void desligarDispositivo() {
        this.estado = EstadoTelefone.DESLIGADO;
        System.out.printf("[%s] Dispositivo desligado.%n", modelo);
    }

    /**
     * Ativa o modo avião, desabilitando todas as conexões de rede.
     */
    public void ativarModoAviao() {
        this.estado = EstadoTelefone.MODO_AVIAO;
        System.out.printf("[%s] Modo Aviao ativado. Conectividade desabilitada.%n", modelo);
    }

    /**
     * Verifica se o dispositivo está ligado, lançando exceção caso contrário.
     *
     * @throws DispositivoDesligadoException se o estado atual for {@link EstadoTelefone#DESLIGADO}
     */
    protected void verificarEstadoLigado() throws DispositivoDesligadoException {
        if (estado == EstadoTelefone.DESLIGADO) {
            throw new DispositivoDesligadoException(
                    "Operacao nao permitida: o dispositivo '" + modelo + "' esta desligado."
            );
        }
    }

    /**
     * Indica se o dispositivo possui conexão com a internet.
     * Modo Avião e estado desligado bloqueiam a conectividade.
     *
     * @return {@code true} somente se o estado for {@link EstadoTelefone#LIGADO}
     */
    protected boolean temConexaoInternet() {
        return estado == EstadoTelefone.LIGADO;
    }

    public String getModelo() {
        return modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public EstadoTelefone getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d) | Estado: %s",
                fabricante, modelo, anoFabricacao, estado.getDescricao());
    }
}
