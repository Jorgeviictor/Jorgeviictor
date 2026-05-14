package com.jorgeviictor.smartphone.interfaces;

import com.jorgeviictor.smartphone.exceptions.DispositivoDesligadoException;

/**
 * Interface que define o contrato para funcionalidades de reprodutor musical.
 *
 * <p>Qualquer dispositivo que implemente esta interface deve ser capaz de
 * gerenciar e reproduzir músicas de uma playlist.</p>
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
public interface ReprodutorMusical {

    /**
     * Seleciona uma música e a adiciona à fila de reprodução.
     *
     * @param musica o nome ou título da música a ser selecionada
     * @throws DispositivoDesligadoException se o dispositivo estiver desligado
     */
    void selecionarMusica(String musica) throws DispositivoDesligadoException;

    /**
     * Inicia ou retoma a reprodução da música atual.
     *
     * @throws DispositivoDesligadoException se o dispositivo estiver desligado
     */
    void tocar() throws DispositivoDesligadoException;

    /**
     * Pausa a reprodução da música atual.
     *
     * @throws DispositivoDesligadoException se o dispositivo estiver desligado
     */
    void pausar() throws DispositivoDesligadoException;

    /**
     * Exibe todas as músicas presentes na playlist atual.
     */
    void exibirPlaylist();
}
