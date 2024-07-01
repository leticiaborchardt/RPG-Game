package org.rpggame.utils;

import org.fusesource.jansi.Ansi;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe auxiliar para validar a entrada de dados do usuário através do Scanner.
 */
public final class InputValidator {
    static Scanner sc = new Scanner(System.in);

    public static int getInteger(String message) {
        ConsoleMessage.println(message);

        while (true) {
            try {
                int value = sc.nextInt();

                if (value <= 0) {
                    ConsoleMessage.println("Insira somente valores inteiros positivos:", Ansi.Color.RED);
                    sc.nextLine();
                } else {
                    sc.nextLine();
                    return value;
                }
            } catch (InputMismatchException e) {
                ConsoleMessage.println("Valor inválido! Insira somente valores inteiros positivos:", Ansi.Color.RED);
                sc.nextLine();
            }
        }
    }

    public static String getString(String message) {
        ConsoleMessage.println(message);

        while (true) {
            String input = sc.nextLine();
            return input;
        }
    }
}