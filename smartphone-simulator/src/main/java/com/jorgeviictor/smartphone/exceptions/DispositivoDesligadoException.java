package com.jorgeviictor.smartphone.exceptions;

/**
 * Exceção lançada quando uma operação é tentada em um dispositivo desligado.
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
public class DispositivoDesligadoException extends Exception {

    /**
     * @param mensagem descrição do erro
     */
    public DispositivoDesligadoException(String mensagem) {
        super(mensagem);
    }

    /**
     * @param mensagem descrição do erro
     * @param causa    exceção original que causou este erro
     */
    public DispositivoDesligadoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
