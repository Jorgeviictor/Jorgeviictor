package com.jorgeviictor.smartphone.model;

import com.jorgeviictor.smartphone.exceptions.DispositivoDesligadoException;
import com.jorgeviictor.smartphone.exceptions.NumeroInvalidoException;
import com.jorgeviictor.smartphone.exceptions.RedeIndisponivelException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link Smartphone}.
 *
 * <p>Valida comportamentos esperados e cenários de exceção para todas as
 * funcionalidades implementadas nas três interfaces.</p>
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
@DisplayName("Smartphone")
class SmartphoneTest {

    private Smartphone smartphone;

    @BeforeEach
    void setUp() {
        smartphone = SmartphoneFactory.criarIphone();
        smartphone.ligarDispositivo();
    }

    // =========================================================================
    // Estado do dispositivo
    // =========================================================================

    @Nested
    @DisplayName("Gerenciamento de Estado")
    class EstadoTests {

        @Test
        @DisplayName("Dispositivo criado deve iniciar DESLIGADO")
        void dispositivoCriadoDeveEstarDesligado() {
            Smartphone novo = new Smartphone("Modelo", "Marca", 2024);
            assertEquals(EstadoTelefone.DESLIGADO, novo.getEstado());
        }

        @Test
        @DisplayName("ligarDispositivo deve alterar estado para LIGADO")
        void deveAlterarEstadoParaLigado() {
            Smartphone novo = new Smartphone("Modelo", "Marca", 2024);
            novo.ligarDispositivo();
            assertEquals(EstadoTelefone.LIGADO, novo.getEstado());
        }

        @Test
        @DisplayName("desligarDispositivo deve alterar estado para DESLIGADO")
        void deveAlterarEstadoParaDesligado() {
            smartphone.desligarDispositivo();
            assertEquals(EstadoTelefone.DESLIGADO, smartphone.getEstado());
        }

        @Test
        @DisplayName("ativarModoAviao deve alterar estado para MODO_AVIAO")
        void deveAtivarModoAviao() {
            smartphone.ativarModoAviao();
            assertEquals(EstadoTelefone.MODO_AVIAO, smartphone.getEstado());
        }

        @Test
        @DisplayName("toString deve conter fabricante, modelo e estado")
        void toStringDeveConterInformacoesEssenciais() {
            String descricao = smartphone.toString();
            assertAll(
                    () -> assertTrue(descricao.contains(smartphone.getFabricante())),
                    () -> assertTrue(descricao.contains(smartphone.getModelo())),
                    () -> assertTrue(descricao.contains(smartphone.getEstado().getDescricao()))
            );
        }
    }

    // =========================================================================
    // ReprodutorMusical
    // =========================================================================

    @Nested
    @DisplayName("ReprodutorMusical")
    class ReprodutorMusicalTests {

        @Test
        @DisplayName("selecionarMusica deve adicionar à playlist")
        void deveAdicionarMusicaAPlaylist() throws DispositivoDesligadoException {
            smartphone.selecionarMusica("Bohemian Rhapsody");

            assertEquals(1, smartphone.getPlaylist().size());
            assertTrue(smartphone.getPlaylist().contains("Bohemian Rhapsody"));
        }

        @Test
        @DisplayName("primeira musica selecionada deve tornar-se a musica atual")
        void primeiraMusicaDeveTornarSeAtual() throws DispositivoDesligadoException {
            smartphone.selecionarMusica("Imagine");

            assertEquals("Imagine", smartphone.getMusicaAtual());
        }

        @Test
        @DisplayName("tocar deve ativar estado de reproducao")
        void deveReproduzirMusicaSelecionada() throws DispositivoDesligadoException {
            smartphone.selecionarMusica("Stairway to Heaven");
            smartphone.tocar();

            assertTrue(smartphone.isReproduzindo());
        }

        @Test
        @DisplayName("pausar deve desativar estado de reproducao")
        void devePausarMusicaEmReproducao() throws DispositivoDesligadoException {
            smartphone.selecionarMusica("Imagine");
            smartphone.tocar();
            smartphone.pausar();

            assertFalse(smartphone.isReproduzindo());
        }

        @Test
        @DisplayName("selecionarMusica deve lancar excecao com dispositivo desligado")
        void deveLancarExcecaoComDispositivoDesligado() {
            smartphone.desligarDispositivo();

            assertThrows(DispositivoDesligadoException.class,
                    () -> smartphone.selecionarMusica("Qualquer Musica"));
        }

        @Test
        @DisplayName("tocar sem musica selecionada nao deve lancar excecao")
        void tocarSemMusicaNaoDeveLancarExcecao() {
            assertDoesNotThrow(() -> smartphone.tocar());
            assertFalse(smartphone.isReproduzindo());
        }

        @Test
        @DisplayName("getPlaylist deve retornar lista imutavel")
        void playlistDeveSerImutavel() throws DispositivoDesligadoException {
            smartphone.selecionarMusica("Teste");

            assertThrows(UnsupportedOperationException.class,
                    () -> smartphone.getPlaylist().add("Invasao"));
        }

        @Test
        @DisplayName("multiplas musicas devem ser adicionadas corretamente")
        void deveAdicionarMultiplasMusicas() throws DispositivoDesligadoException {
            smartphone.selecionarMusica("Musica 1");
            smartphone.selecionarMusica("Musica 2");
            smartphone.selecionarMusica("Musica 3");

            assertEquals(3, smartphone.getPlaylist().size());
        }
    }

    // =========================================================================
    // AparelhoTelefonico
    // =========================================================================

    @Nested
    @DisplayName("AparelhoTelefonico")
    class AparelhoTelefonicoTests {

        @ParameterizedTest(name = "Numero valido: [{0}]")
        @ValueSource(strings = {"(11) 99999-8888", "+55 (11) 98765-4321", "11988887777"})
        @DisplayName("ligar deve aceitar formatos de numero validos")
        void deveAceitarNumerosValidos(String numero) {
            assertDoesNotThrow(() -> smartphone.ligar(numero));
        }

        @ParameterizedTest(name = "Numero invalido: [{0}]")
        @ValueSource(strings = {"abc", "123", "numero-errado", "!@#$"})
        @DisplayName("ligar deve rejeitar formatos de numero invalidos")
        void deveRejeitarNumerosInvalidos(String numero) {
            assertThrows(NumeroInvalidoException.class, () -> smartphone.ligar(numero));
        }

        @Test
        @DisplayName("NumeroInvalidoException deve preservar o numero informado")
        void excecaoDeveConterNumeroInformado() {
            String numeroInvalido = "invalido-123";

            NumeroInvalidoException ex = assertThrows(NumeroInvalidoException.class,
                    () -> smartphone.ligar(numeroInvalido));

            assertEquals(numeroInvalido, ex.getNumeroInformado());
        }

        @Test
        @DisplayName("ligar deve lancar excecao com dispositivo desligado")
        void deveLancarExcecaoComDispositivoDesligado() {
            smartphone.desligarDispositivo();

            assertThrows(DispositivoDesligadoException.class,
                    () -> smartphone.ligar("(11) 99999-8888"));
        }

        @Test
        @DisplayName("atender deve executar sem excecao com dispositivo ligado")
        void deveAtenderComDispositivoLigado() {
            assertDoesNotThrow(() -> smartphone.atender());
        }

        @Test
        @DisplayName("iniciarCorreioVoz deve executar sem excecao com dispositivo ligado")
        void deveIniciarCorreioVozComDispositivoLigado() {
            assertDoesNotThrow(() -> smartphone.iniciarCorreioVoz());
        }
    }

    // =========================================================================
    // NavegadorInternet
    // =========================================================================

    @Nested
    @DisplayName("NavegadorInternet")
    class NavegadorInternetTests {

        @Test
        @DisplayName("exibirPagina deve carregar URL com conexao disponivel")
        void deveExibirPaginaComConexao() {
            assertDoesNotThrow(() ->
                    smartphone.exibirPagina("https://github.com/jorgeviictor"));
        }

        @Test
        @DisplayName("exibirPagina deve lancar RedeIndisponivelException em Modo Aviao")
        void deveLancarExcecaoEmModoAviao() {
            smartphone.ativarModoAviao();

            assertThrows(RedeIndisponivelException.class,
                    () -> smartphone.exibirPagina("https://google.com"));
        }

        @Test
        @DisplayName("exibirPagina deve lancar DispositivoDesligadoException quando desligado")
        void deveLancarExcecaoQuandoDesligado() {
            smartphone.desligarDispositivo();

            assertThrows(DispositivoDesligadoException.class,
                    () -> smartphone.exibirPagina("https://google.com"));
        }

        @Test
        @DisplayName("adicionarNovaAba deve executar sem excecao com dispositivo ligado")
        void deveAdicionarNovaAba() {
            assertDoesNotThrow(() -> {
                smartphone.adicionarNovaAba();
                smartphone.adicionarNovaAba();
            });
        }

        @Test
        @DisplayName("atualizarPagina deve funcionar apos carregar uma URL")
        void deveAtualizarPaginaCarregada() {
            assertDoesNotThrow(() -> {
                smartphone.exibirPagina("https://github.com");
                smartphone.atualizarPagina();
            });
        }

        @Test
        @DisplayName("atualizarPagina deve lancar RedeIndisponivelException em Modo Aviao")
        void deveBloquearAtualizacaoEmModoAviao() throws DispositivoDesligadoException, RedeIndisponivelException {
            smartphone.exibirPagina("https://github.com");
            smartphone.ativarModoAviao();

            assertThrows(RedeIndisponivelException.class,
                    () -> smartphone.atualizarPagina());
        }
    }

    // =========================================================================
    // SmartphoneFactory
    // =========================================================================

    @Nested
    @DisplayName("SmartphoneFactory")
    class FactoryTests {

        @Test
        @DisplayName("criarIphone deve retornar dispositivo Apple")
        void criarIphoneDeveRetornarDispositivo() {
            Smartphone iphone = SmartphoneFactory.criarIphone();

            assertAll(
                    () -> assertEquals("Apple", iphone.getFabricante()),
                    () -> assertNotNull(iphone.getModelo()),
                    () -> assertEquals(EstadoTelefone.DESLIGADO, iphone.getEstado())
            );
        }

        @Test
        @DisplayName("criarSamsungGalaxy deve retornar dispositivo Samsung")
        void criarSamsungGalaxyDeveRetornarDispositivo() {
            Smartphone samsung = SmartphoneFactory.criarSamsungGalaxy();

            assertEquals("Samsung", samsung.getFabricante());
        }

        @Test
        @DisplayName("criarPersonalizado deve respeitar os parametros fornecidos")
        void criarPersonalizadoDeveUsarParametros() {
            Smartphone custom = SmartphoneFactory.criarPersonalizado("Pixel 8", "Google", 2023);

            assertAll(
                    () -> assertEquals("Pixel 8", custom.getModelo()),
                    () -> assertEquals("Google", custom.getFabricante()),
                    () -> assertEquals(2023, custom.getAnoFabricacao())
            );
        }
    }
}
