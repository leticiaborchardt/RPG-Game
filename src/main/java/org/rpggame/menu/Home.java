package org.rpggame.menu;

import org.fusesource.jansi.Ansi;
import org.rpggame.utils.ConsoleMessage;
import org.rpggame.utils.InputValidator;

/**
 * Input class for all RPG game logic, from there the user accesses all the game's features.
 */
public final class Home {
    /**
     * Displays the game's home menu options and asks the user where to go.
     */
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
