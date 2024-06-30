package org.rpggame.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe auxiliar para validar a entrada de dados do usuário através do Scanner.
 */
public class InputValidator {
    static Scanner sc = new Scanner(System.in);

    public static int getInteger(String message) {
        System.out.println(message);

        while (true) {
            try {
                int value = sc.nextInt();

                if (value <= 0) {
                    System.out.println("Insira somente valores inteiros positivos: ");
                    sc.nextLine();
                } else {
                    sc.nextLine();
                    return value;
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido! Insira somente valores inteiros positivos: ");
                sc.nextLine();
            }
        }
    }

    public static String getString(String message) {
        System.out.println(message);

        while (true) {
            String input = sc.nextLine();
            return input;
        }
    }
}