package com.jorgeviictor.smartphone.exceptions;

/**
 * Exceção lançada quando um número de telefone com formato inválido é fornecido.
 *
 * <p>Preserva o valor que causou o erro para facilitar o diagnóstico.</p>
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
public class NumeroInvalidoException extends Exception {

    private final String numeroInformado;

    /**
     * @param mensagem        descrição do erro de validação
     * @param numeroInformado o valor original que falhou na validação
     */
    public NumeroInvalidoException(String mensagem, String numeroInformado) {
        super(mensagem);
        this.numeroInformado = numeroInformado;
    }

    /**
     * Retorna o número que falhou na validação.
     *
     * @return o valor inválido informado pelo chamador
     */
    public String getNumeroInformado() {
        return numeroInformado;
    }
}
