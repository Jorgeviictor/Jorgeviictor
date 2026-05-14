package com.jorgeviictor.smartphone.exceptions;

/**
 * Exceção lançada quando uma operação requer internet mas a rede está indisponível.
 *
 * <p>Ocorre tipicamente quando o dispositivo está em Modo Avião ou sem sinal de rede.</p>
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
public class RedeIndisponivelException extends Exception {

    /**
     * @param mensagem descrição do erro de conectividade
     */
    public RedeIndisponivelException(String mensagem) {
        super(mensagem);
    }

    /**
     * @param mensagem descrição do erro de conectividade
     * @param causa    exceção original que causou este erro
     */
    public RedeIndisponivelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
