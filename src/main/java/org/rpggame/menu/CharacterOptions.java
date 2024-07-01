package org.rpggame.menu;

import org.fusesource.jansi.Ansi;
import org.rpggame.entities.characters.Archer;
import org.rpggame.entities.characters.Character;
import org.rpggame.entities.characters.Mage;
import org.rpggame.entities.characters.Warrior;
import org.rpggame.skills.Skill;
import org.rpggame.utils.ConsoleMessage;
import org.rpggame.utils.InputValidator;

import java.util.ArrayList;

/**
 * Contains the methods responsible for creating or choosing the character.
 */
public final class CharacterOptions {
    private static final ArrayList<Character> characters = new ArrayList<>();

    /**
     * Creates a new character based on user choice and redirects to battle menu options.
     */
    public static void create() {
        String name = InputValidator.getString("Informe o nome do seu personagem:");
        ConsoleMessage.println("Escolha um tipo de personagem:", Ansi.Color.BLUE);

        Character character;

        switch (InputValidator.getInteger(getCharacterMenuOptions())) {
            case 1:
                character = new Mage(name);
                break;
            case 2:
                character = new Archer(name);
                break;
            case 3:
                character = new Warrior(name);
                break;
            default:
                ConsoleMessage.printInvalidOptionMessage();
                return;
        }

        ArrayList<Skill> chosenSkills = SkillsOptions.chooseSkills();
        character.setSkills(chosenSkills);

        characters.add(character);
        ConsoleMessage.println("Personagem criado! Informações:", Ansi.Color.GREEN);
        character.printInformation();

        BattleOptions.run(character);
    }

    /**
     * Displays all existing characters and asks the user to choose one,
     * then goes to the battle menu options
     */
    public static void getExistentCharacter() {
        if (characters.isEmpty()) {
            ConsoleMessage.println("Nenhum personagem encontrado!", Ansi.Color.RED);
            return;
        }

        while (true) {
            showCharacterOptions();

            int cancelOption = characters.size() + 1;
            ConsoleMessage.println("[" + cancelOption + "] Voltar");

            int choice = InputValidator.getInteger("");

            if (choice == cancelOption) {
                return;
            }

            if (choice >= 1 && choice <= characters.size()) {
                Character choosenCharacter = characters.get(choice - 1);
                ConsoleMessage.println("Personagem escolhido:", Ansi.Color.GREEN);
                choosenCharacter.printInformation();

                BattleOptions.run(choosenCharacter);

                return;
            } else {
                ConsoleMessage.printInvalidOptionMessage();
            }
        }
    }

    private static void showCharacterOptions() {
        ConsoleMessage.println("Escolha o seu personagem:");

        for (int i = 0; i < characters.size(); i++) {
            Character character = characters.get(i);
            ConsoleMessage.println("[" + (i + 1) + "] " + character.getFormattedName());
        }
    }

    private static String getCharacterMenuOptions() {
        return "[1] Mago\n" +
                "[2] Arqueiro\n" +
                "[3] Guerreiro";
    }
}
