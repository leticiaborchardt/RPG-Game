package org.rpggame.utils;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleMessage {

    public static void print(String message) {
        System.out.print(message);
    }

    public static void print(String message, Ansi.Color color) {
        System.out.print(ansi().eraseScreen().fg(color).a(message).reset());
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void println(String message, Ansi.Color color) {
        System.out.println(ansi().eraseScreen().fg(color).a(message).reset());
    }

    public static void printInvalidOptionMessage() {
        ConsoleMessage.println("Opção inválida! Tente novamente.", Ansi.Color.RED);
    }
}
