package com.jorgeviictor.smartphone.main;

import com.jorgeviictor.smartphone.exceptions.DispositivoDesligadoException;
import com.jorgeviictor.smartphone.exceptions.NumeroInvalidoException;
import com.jorgeviictor.smartphone.exceptions.RedeIndisponivelException;
import com.jorgeviictor.smartphone.interfaces.AparelhoTelefonico;
import com.jorgeviictor.smartphone.interfaces.NavegadorInternet;
import com.jorgeviictor.smartphone.interfaces.ReprodutorMusical;
import com.jorgeviictor.smartphone.model.Smartphone;
import com.jorgeviictor.smartphone.model.SmartphoneFactory;

/**
 * Classe principal que demonstra o uso polimórfico do Smartphone.
 *
 * <p>Pilares da POO demonstrados:</p>
 * <ul>
 *   <li><b>Polimorfismo</b>  — mesma instância referenciada via interfaces distintas</li>
 *   <li><b>Encapsulamento</b> — acesso controlado ao estado interno</li>
 *   <li><b>Abstração</b>     — interação via contratos (interfaces)</li>
 *   <li><b>Herança</b>       — Smartphone estende SmartDevice</li>
 * </ul>
 *
 * @author Jorge Victor
 * @version 1.0
 * @since 2024
 */
public class Main {

    public static void main(String[] args) {
        separator("SIMULADOR DE SMARTPHONE - Demo Principal");

        // Factory Pattern: criacao via fabrica centralizada
        Smartphone smartphone = SmartphoneFactory.criarIphone();
        smartphone.ligarDispositivo();
        System.out.println("Dispositivo: " + smartphone);
        System.out.println();

        // POLIMORFISMO: mesma instancia referenciada por tres interfaces diferentes
        ReprodutorMusical reprodutor = smartphone;
        AparelhoTelefonico telefone  = smartphone;
        NavegadorInternet  navegador = smartphone;

        section("REPRODUTOR MUSICAL");
        demonstrarReprodutorMusical(reprodutor);

        section("APARELHO TELEFONICO");
        demonstrarAparelhoTelefonico(telefone);

        section("NAVEGADOR DE INTERNET");
        demonstrarNavegadorInternet(navegador);

        section("TESTE: MODO AVIAO bloqueia rede");
        smartphone.ativarModoAviao();
        try {
            navegador.exibirPagina("https://github.com/jorgeviictor");
        } catch (RedeIndisponivelException e) {
            System.out.println("[ERRO ESPERADO] " + e.getMessage());
        } catch (DispositivoDesligadoException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }

        section("TESTE: Operacao com dispositivo desligado");
        smartphone.desligarDispositivo();
        try {
            reprodutor.tocar();
        } catch (DispositivoDesligadoException e) {
            System.out.println("[ERRO ESPERADO] " + e.getMessage());
        }

        System.out.println();
        separator("Simulacao concluida!");
    }

    private static void demonstrarReprodutorMusical(ReprodutorMusical reprodutor) {
        try {
            reprodutor.selecionarMusica("Bohemian Rhapsody - Queen");
            reprodutor.selecionarMusica("Stairway to Heaven - Led Zeppelin");
            reprodutor.selecionarMusica("Imagine - John Lennon");
            reprodutor.exibirPlaylist();
            reprodutor.tocar();
            reprodutor.pausar();
        } catch (DispositivoDesligadoException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    private static void demonstrarAparelhoTelefonico(AparelhoTelefonico telefone) {
        try {
            telefone.ligar("(11) 99999-8888");
            telefone.atender();
            telefone.iniciarCorreioVoz();

            System.out.println("\n[Teste] Tentando numero invalido...");
            telefone.ligar("numero-errado");
        } catch (NumeroInvalidoException e) {
            System.out.printf("[ERRO ESPERADO] %s | Numero informado: \"%s\"%n",
                    e.getMessage(), e.getNumeroInformado());
        } catch (DispositivoDesligadoException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    private static void demonstrarNavegadorInternet(NavegadorInternet navegador) {
        try {
            navegador.exibirPagina("https://github.com/jorgeviictor");
            navegador.adicionarNovaAba();
            navegador.adicionarNovaAba();
            navegador.atualizarPagina();
        } catch (DispositivoDesligadoException | RedeIndisponivelException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    private static void separator(String titulo) {
        String linha = "=".repeat(55);
        System.out.println(linha);
        System.out.printf("  %s%n", titulo);
        System.out.println(linha);
    }

    private static void section(String titulo) {
        System.out.println();
        System.out.printf("--- %s ---%n", titulo);
    }
}
