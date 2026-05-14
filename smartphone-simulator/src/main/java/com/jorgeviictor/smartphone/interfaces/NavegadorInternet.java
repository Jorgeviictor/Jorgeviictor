package com.jorgeviictor.smartphone.interfaces;

import com.jorgeviictor.smartphone.exceptions.DispositivoDesligadoException;
import com.jorgeviictor.smartphone.exceptions.RedeIndisponivelException;

/**
 * Interface que define o contrato para funcionalidades de navegador de internet.
 *
 * <p>Dispositivos que implementam esta interface devem ser capazes de
 * exibir páginas web, gerenciar abas e atualizar conteúdo.</p>
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
public interface NavegadorInternet {

    /**
     * Exibe uma página web a partir da URL fornecida.
     *
     * @param url a URL da página a ser exibida
     * @throws DispositivoDesligadoException se o dispositivo estiver desligado
     * @throws RedeIndisponivelException     se não houver conexão com a internet
     */
    void exibirPagina(String url) throws DispositivoDesligadoException, RedeIndisponivelException;

    /**
     * Abre uma nova aba no navegador.
     *
     * @throws DispositivoDesligadoException se o dispositivo estiver desligado
     */
    void adicionarNovaAba() throws DispositivoDesligadoException;

    /**
     * Atualiza a página atualmente exibida no navegador.
     *
     * @throws DispositivoDesligadoException se o dispositivo estiver desligado
     * @throws RedeIndisponivelException     se não houver conexão com a internet
     */
    void atualizarPagina() throws DispositivoDesligadoException, RedeIndisponivelException;
}
