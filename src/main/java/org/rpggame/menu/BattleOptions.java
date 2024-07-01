package org.rpggame.menu;

import org.fusesource.jansi.Ansi;
import org.rpggame.entities.characters.Character;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.game.Battle;
import org.rpggame.utils.ConsoleMessage;
import org.rpggame.utils.EnemyGenerator;
import org.rpggame.utils.InputValidator;

public class BattleOptions {
    public static void run(Character character) {
        while (true) {
            ConsoleMessage.println("\n" + character.getName() + ", escolha uma opção abaixo:", Ansi.Color.BLUE);

            switch (InputValidator.getInteger(getBattleOptions())) {
                case 1:
                    character.regenerate();

                    Enemy enemy;
                    if (character.isReadyToFightBoss()) {
                        enemy = EnemyGenerator.generateBoss(character);
                    } else {
                        enemy = EnemyGenerator.generateRandomEnemy(character);
                    }

                    new Battle(character, enemy).start();
                    break;
                case 2:
                    return;
                default:
                    ConsoleMessage.printInvalidOptionMessage();
                    break;
            }
        }
    }

    private static String getBattleOptions() {
        return "[1] Iniciar nova batalha\n" +
                "[2] Voltar";
    }
}
