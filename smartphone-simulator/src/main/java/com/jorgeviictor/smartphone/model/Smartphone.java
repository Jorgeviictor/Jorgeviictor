package com.jorgeviictor.smartphone.model;

import com.jorgeviictor.smartphone.exceptions.DispositivoDesligadoException;
import com.jorgeviictor.smartphone.exceptions.NumeroInvalidoException;
import com.jorgeviictor.smartphone.exceptions.RedeIndisponivelException;
import com.jorgeviictor.smartphone.interfaces.AparelhoTelefonico;
import com.jorgeviictor.smartphone.interfaces.NavegadorInternet;
import com.jorgeviictor.smartphone.interfaces.ReprodutorMusical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Representa um smartphone moderno com funcionalidades de reprodutor musical,
 * aparelho telefônico e navegador de internet.
 *
 * <p>Demonstra polimorfismo: a mesma instância pode ser referenciada por qualquer
 * uma de suas interfaces, conforme o princípio de substituição de Liskov (LSP):</p>
 *
 * <pre>{@code
 * ReprodutorMusical reprodutor = SmartphoneFactory.criarIphone();
 * AparelhoTelefonico telefone  = SmartphoneFactory.criarIphone();
 * NavegadorInternet  navegador = SmartphoneFactory.criarIphone();
 * }</pre>
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 * @see ReprodutorMusical
 * @see AparelhoTelefonico
 * @see NavegadorInternet
 */
public class Smartphone extends SmartDevice
        implements ReprodutorMusical, AparelhoTelefonico, NavegadorInternet {

    /** Regex que aceita formatos brasileiros: (XX) XXXXX-XXXX, +55 (XX) 9XXXX-XXXX etc. */
    private static final Pattern PADRAO_NUMERO = Pattern.compile(
            "^(\\+?\\d{1,3})?[\\s.-]?\\(?\\d{2}\\)?[\\s.-]?\\d{4,5}[\\s.-]?\\d{4}$"
    );

    // --- Reprodutor Musical ---
    private final List<String> playlist;
    private String musicaAtual;
    private boolean reproduzindo;

    // --- Navegador ---
    private final List<String> abas;
    private String paginaAtual;

    /**
     * @param modelo        o modelo do dispositivo
     * @param fabricante    o fabricante
     * @param anoFabricacao o ano de fabricação
     */
    public Smartphone(String modelo, String fabricante, int anoFabricacao) {
        super(modelo, fabricante, anoFabricacao);
        this.playlist = new ArrayList<>();
        this.abas = new ArrayList<>();
        this.reproduzindo = false;
    }

    // =========================================================================
    // ReprodutorMusical
    // =========================================================================

    /**
     * {@inheritDoc}
     *
     * <p>A música é adicionada à playlist. Se nenhuma estiver selecionada, esta
     * passa a ser a música atual automaticamente.</p>
     */
    @Override
    public void selecionarMusica(String musica) throws DispositivoDesligadoException {
        verificarEstadoLigado();
        if (musica == null || musica.isBlank()) {
            System.out.println("[Reprodutor] Nome da musica invalido.");
            return;
        }
        playlist.add(musica);
        if (musicaAtual == null) {
            musicaAtual = musica;
        }
        System.out.printf("[Reprodutor] Adicionada a playlist: \"%s\"%n", musica);
    }

    /** {@inheritDoc} */
    @Override
    public void tocar() throws DispositivoDesligadoException {
        verificarEstadoLigado();
        if (musicaAtual == null) {
            System.out.println("[Reprodutor] Nenhuma musica selecionada. Use selecionarMusica() primeiro.");
            return;
        }
        reproduzindo = true;
        System.out.printf("[Reprodutor] >> Reproduzindo: \"%s\"%n", musicaAtual);
    }

    /** {@inheritDoc} */
    @Override
    public void pausar() throws DispositivoDesligadoException {
        verificarEstadoLigado();
        if (!reproduzindo) {
            System.out.println("[Reprodutor] Nenhuma musica em reproducao.");
            return;
        }
        reproduzindo = false;
        System.out.printf("[Reprodutor] || Pausado: \"%s\"%n", musicaAtual);
    }

    /** {@inheritDoc} */
    @Override
    public void exibirPlaylist() {
        if (playlist.isEmpty()) {
            System.out.println("[Reprodutor] A playlist esta vazia.");
            return;
        }
        System.out.println("[Reprodutor] === Playlist ===");
        for (int i = 0; i < playlist.size(); i++) {
            String prefixo = playlist.get(i).equals(musicaAtual) ? ">>" : "  ";
            System.out.printf("  %s %d. %s%n", prefixo, i + 1, playlist.get(i));
        }
    }

    // =========================================================================
    // AparelhoTelefonico
    // =========================================================================

    /**
     * {@inheritDoc}
     *
     * <p>Valida o formato do número via regex antes de realizar a chamada.</p>
     */
    @Override
    public void ligar(String numero) throws DispositivoDesligadoException, NumeroInvalidoException {
        verificarEstadoLigado();
        if (numero == null || !PADRAO_NUMERO.matcher(numero.trim()).matches()) {
            throw new NumeroInvalidoException(
                    "Numero invalido. Formatos aceitos: (XX) XXXXX-XXXX ou +55 (XX) XXXXX-XXXX",
                    numero
            );
        }
        System.out.printf("[Telefone] Ligando para: %s...%n", numero);
    }

    /** {@inheritDoc} */
    @Override
    public void atender() throws DispositivoDesligadoException {
        verificarEstadoLigado();
        System.out.println("[Telefone] Chamada atendida.");
    }

    /** {@inheritDoc} */
    @Override
    public void iniciarCorreioVoz() throws DispositivoDesligadoException {
        verificarEstadoLigado();
        System.out.println("[Telefone] Correio de voz iniciado. Deixe sua mensagem apos o sinal...");
    }

    // =========================================================================
    // NavegadorInternet
    // =========================================================================

    /**
     * {@inheritDoc}
     *
     * <p>Requer conectividade de rede — bloqueado em Modo Avião.</p>
     */
    @Override
    public void exibirPagina(String url) throws DispositivoDesligadoException, RedeIndisponivelException {
        verificarEstadoLigado();
        verificarConexao();
        this.paginaAtual = url;
        System.out.printf("[Navegador] Carregando pagina: %s%n", url);
    }

    /** {@inheritDoc} */
    @Override
    public void adicionarNovaAba() throws DispositivoDesligadoException {
        verificarEstadoLigado();
        String novaAba = "Nova Aba " + (abas.size() + 1);
        abas.add(novaAba);
        System.out.printf("[Navegador] Nova aba aberta. Total de abas: %d%n", abas.size());
    }

    /** {@inheritDoc} */
    @Override
    public void atualizarPagina() throws DispositivoDesligadoException, RedeIndisponivelException {
        verificarEstadoLigado();
        verificarConexao();
        if (paginaAtual == null) {
            System.out.println("[Navegador] Nenhuma pagina carregada para atualizar.");
            return;
        }
        System.out.printf("[Navegador] Atualizando: %s%n", paginaAtual);
    }

    // =========================================================================
    // Helpers privados
    // =========================================================================

    private void verificarConexao() throws RedeIndisponivelException {
        if (!temConexaoInternet()) {
            throw new RedeIndisponivelException(
                    "Rede indisponivel. Verifique se o Modo Aviao esta desativado."
            );
        }
    }

    // =========================================================================
    // Getters
    // =========================================================================

    /**
     * Retorna uma visão imutável da playlist para evitar modificações externas.
     *
     * @return lista imutável das músicas na playlist
     */
    public List<String> getPlaylist() {
        return Collections.unmodifiableList(playlist);
    }

    /**
     * @return nome da música atual, ou {@code null} se nenhuma estiver selecionada
     */
    public String getMusicaAtual() {
        return musicaAtual;
    }

    /**
     * @return {@code true} se o reprodutor estiver em modo de reprodução ativa
     */
    public boolean isReproduzindo() {
        return reproduzindo;
    }
}
