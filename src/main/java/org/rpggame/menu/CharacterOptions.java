package org.rpggame.menu;

import org.rpggame.entities.characters.Archer;
import org.rpggame.entities.characters.Character;
import org.rpggame.entities.characters.Mage;
import org.rpggame.entities.characters.Warrior;
import org.rpggame.utils.InputValidator;

public final class CharacterOptions {
    public static void create() {
        String name = InputValidator.getString("Informe o nome do seu personagem:");
        System.out.println("Escolha um tipo de personagem:");

        Character character;

        switch (InputValidator.getInteger(showCharacterOptions())) {
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
                System.out.println("Opção inválida! Tente novamente.");
                return;
        }

        if (character != null) {
            System.out.println("Personagem criado!\n------- Informações -------");
            character.printInformation();
            System.out.println("---------------------------");

            initBattleMenu(character);
        }
    }

    // TODO: escolha de personagem carregado (banco de dados)
    public static void getExistentCharacter() {
//        initBattleMenu();
    }

    private static void initBattleMenu(Character choosenCharacter) {
        BattleOptions.run(choosenCharacter);
    }

    private static String showCharacterOptions() {
        return "[1] Mago\n" +
                "[2] Arqueiro\n" +
                "[3] Warrior";
    }
}
