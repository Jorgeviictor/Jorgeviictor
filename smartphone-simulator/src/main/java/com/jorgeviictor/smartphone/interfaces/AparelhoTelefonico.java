package com.jorgeviictor.smartphone.interfaces;

import com.jorgeviictor.smartphone.exceptions.DispositivoDesligadoException;
import com.jorgeviictor.smartphone.exceptions.NumeroInvalidoException;

/**
 * Interface que define o contrato para funcionalidades de aparelho telefônico.
 *
 * <p>Dispositivos que implementam esta interface devem suportar operações
 * básicas de telefonia, como realizar e receber chamadas.</p>
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
public interface AparelhoTelefonico {

    /**
     * Realiza uma chamada telefônica para o número especificado.
     *
     * @param numero o número de telefone para o qual a chamada será realizada
     * @throws DispositivoDesligadoException se o dispositivo estiver desligado
     * @throws NumeroInvalidoException       se o formato do número for inválido
     */
    void ligar(String numero) throws DispositivoDesligadoException, NumeroInvalidoException;

    /**
     * Atende uma chamada telefônica recebida.
     *
     * @throws DispositivoDesligadoException se o dispositivo estiver desligado
     */
    void atender() throws DispositivoDesligadoException;

    /**
     * Inicia o serviço de correio de voz para gravar ou ouvir mensagens.
     *
     * @throws DispositivoDesligadoException se o dispositivo estiver desligado
     */
    void iniciarCorreioVoz() throws DispositivoDesligadoException;
}
