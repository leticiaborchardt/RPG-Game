package org.rpggame.utils;

import org.rpggame.entities.characters.Character;
import org.rpggame.entities.enemies.Boss;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.entities.enemies.EnemyType;
import org.rpggame.rewards.Reward;
import org.rpggame.rewards.items.Item;
import org.rpggame.rewards.items.ItemsManager;
import org.rpggame.rewards.skills.Skill;
import org.rpggame.rewards.skills.SkillManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains methods for enemies generation based on the character's level.
 */
public final class EnemyGenerator {
    /**
     * Generates a random enemy based on the attributes of a given character.
     * Calculates random life points, attack, defense, experience, and level for the enemy,
     * as well as a random set of skills from predefined available skills.
     *
     * @param character The character object whose attributes influence the enemy's stats.
     * @return A randomly generated Enemy object with calculated attributes and skills.
     */
    public static Enemy generateRandomEnemy(Character character) {
        Random rand = new Random();

        EnemyType type = EnemyType.getRandomType();

        int lifePoints = (int) (character.getLifePoints() * (0.8 + 0.4 * rand.nextDouble()));
        int attack = (int) (character.getAttack() * (0.8 + 0.4 * rand.nextDouble()));
        int defense = (int) (character.getDefense() * (0.8 + 0.4 * rand.nextDouble()));
        int experience = character.getLevel() * 100;
        int levelVariation = rand.nextInt(3) - 1;
        int level = Math.max(1, character.getLevel() + levelVariation);
        int rewardXP = level * 50;
        String name = type.getDescription() + " Lv" + level;
        ArrayList<Skill> skills = SkillManager.generateRandomSkills(character.getSkills().size());
        ArrayList<Item> items = character.getItems().isEmpty() ? new ArrayList<>() : ItemsManager.generateRandomItems(character.getSkills().size());

        return new Enemy(name, lifePoints, attack, defense, experience, level, skills, items, type, rewardXP);
    }

    /**
     * Generates a boss enemy based on the attributes of a given character.
     * Calculates increased life points, attack, defense, experience, and level for the boss,
     * as well as a random set of skills from predefined available skills.
     *
     * @param character The character object whose attributes influence the boss's stats.
     * @return A boss Enemy object with increased attributes and random skills.
     */
    public static Enemy generateBoss(Character character) {
        Random rand = new Random();

        int lifePoints = (int) (character.getLifePoints() * (1.0 + 0.2 * rand.nextDouble()));
        int attack = (int) (character.getAttack() * (1.0 + 0.2 * rand.nextDouble()));
        int defense = (int) (character.getDefense() * (1.0 + 0.2 * rand.nextDouble()));
        int experience = character.getLevel() * 100;
        int level = character.getLevel();
        int rewardXP = level * 50;
        String name = EnemyType.BOSS.getDescription() + " Lv" + level;
        ArrayList<Skill> skills = SkillManager.generateRandomSkills(character.getSkills().size());
        ArrayList<Item> items = character.getItems().isEmpty() ? new ArrayList<>() : ItemsManager.generateRandomItems(character.getSkills().size());
        Reward reward = RewardGenerator.generateReward(character);

        return new Boss(name, lifePoints, attack, defense, experience, level, skills, items, EnemyType.BOSS, rewardXP, reward);
    }
}
