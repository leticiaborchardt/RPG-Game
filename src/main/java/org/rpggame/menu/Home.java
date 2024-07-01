package org.rpggame.menu;

import org.fusesource.jansi.Ansi;
import org.rpggame.utils.ConsoleMessage;
import org.rpggame.utils.InputValidator;

public final class Home {
    public static void run() {
        ConsoleMessage.println(
                "---------------------------\n" +
                        "Bem-vindo(a) a batalha RPG!\n" +
                        "---------------------------",
                Ansi.Color.MAGENTA
        );

        while (true) {
            ConsoleMessage.println("Escolha uma opção abaixo:", Ansi.Color.BLUE);

            switch (InputValidator.getInteger(getOptions())) {
                case 1:
                    CharacterOptions.create();
                    break;
                case 2:
                    CharacterOptions.getExistentCharacter();
                    break;
                case 3:
                    return;
                default:
                    ConsoleMessage.printInvalidOptionMessage();
                    break;
            }
        }
    }

    private static String getOptions() {
        return "[1] Criar personagem\n" +
                "[2] Escolher personagem\n" +
                "[3] Sair";
    }
}
