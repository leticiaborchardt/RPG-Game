package org.rpggame.menu;

import org.rpggame.entities.characters.Character;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.game.Battle;
import org.rpggame.utils.EnemyGenerator;
import org.rpggame.utils.InputValidator;

public class BattleOptions {
    public static void run(Character character) {
        boolean runBattle = true;

        System.out.println(character.getName() + ", escolha uma opção abaixo:");

        while (runBattle) {
            switch (InputValidator.getInteger(showBattleOptions())) {
                case 1:
                    Enemy enemy;
                    if (character.isReadyToFightBoss()) {
                        enemy = EnemyGenerator.generateBoss(character);
                    } else {
                        enemy = EnemyGenerator.generateRandomEnemy(character);
                    }

                    new Battle(character, enemy).start();
                    break;
                case 2:
                    runBattle = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }
    }

    private static String showBattleOptions() {
        return "[1] Iniciar nova batalha\n" +
                "[2] Sair";
    }
}
