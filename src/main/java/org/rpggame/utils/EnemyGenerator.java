package org.rpggame.utils;

import org.rpggame.entities.characters.Character;
import org.rpggame.entities.enemies.Boss;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.entities.enemies.EnemyType;

import java.util.Random;

public final class EnemyGenerator {
    public static Enemy generateRandomEnemy(Character character) {
        Random rand = new Random();

        EnemyType type = EnemyType.getRandomType();

        int lifePoints = (int) (character.getLifePoints() * (0.8 + 0.4 * rand.nextDouble()));
        int attack = (int) (character.getAttack() * (0.8 + 0.4 * rand.nextDouble()));
        int defense = (int) (character.getDefense() * (0.8 + 0.4 * rand.nextDouble()));
        int experience = character.getLevel() * 100;

        int levelVariation = rand.nextInt(3) - 1;
        int level = character.getLevel() + levelVariation;
        if (level < 1) level = 1;

        int rewardXP = level * 50;

        String name = type.getDescription() + " Lv" + level;

        return new Enemy(name, lifePoints, attack, defense, experience, level, type, rewardXP);
    }

    public static Enemy generateBoss(Character character) {
        Random rand = new Random();

        int lifePoints = (int) (character.getLifePoints() * (1.2 + 0.4 * rand.nextDouble()));
        int attack = (int) (character.getAttack() * (1.2 + 0.4 * rand.nextDouble()));
        int defense = (int) (character.getDefense() * (1.2 + 0.4 * rand.nextDouble()));
        int experience = character.getLevel() * 150;
        int level = character.getLevel() + 1;
        int rewardXP = level * 100;

        String name = EnemyType.BOSS.getDescription() + " Lv" + level;

        return new Boss(name, lifePoints, attack, defense, experience, level, EnemyType.BOSS, rewardXP, "");
    }
}
