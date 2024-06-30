package org.rpggame.menu;

import org.rpggame.utils.InputValidator;

public final class Home {
    public static void run() {
        boolean runGame = true;

        System.out.println("Bem-vindo(a) a batalha RPG!\nEscolha uma opção abaixo:");

        while (runGame) {
            switch (InputValidator.getInteger(showOptions())) {
                case 1:
                    CharacterOptions.create();
                    break;
                case 2:
                    CharacterOptions.getExistentCharacter();
                    break;
                case 3:
                    runGame = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }
    }

    private static String showOptions() {
        return "[1] Criar personagem\n" +
                "[2] Escolher personagem existente\n" +
                "[3] Sair";
    }
}
