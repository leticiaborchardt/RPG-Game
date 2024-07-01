package org.rpggame.utils;

import org.fusesource.jansi.Ansi;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Assistant class to validate user data input through the Scanner.
 */
public final class InputValidator {
    static Scanner sc = new Scanner(System.in);

    /**
     * Requests and retrieves a positive integer value from the user via the console.
     * Displays a message to prompt the user for input.
     * Returns the positive integer value entered by the user after validation.
     *
     * @param message The message to display prompting the user for input.
     * @return The positive integer value entered by the user.
     */
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

    /**
     * Requests and retrieves a string input from the user via the console.
     * Displays a message to prompt the user for input.
     * Returns the string entered by the user.
     *
     * @param message The message to display prompting the user for input.
     * @return The string entered by the user.
     */
    public static String getString(String message) {
        ConsoleMessage.println(message);

        return sc.nextLine();
    }
}