package org.rpggame.utils;

import org.rpggame.entities.characters.Character;
import org.rpggame.entities.enemies.Boss;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.entities.enemies.EnemyType;
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
        ArrayList<Skill> skills = generateRandomSkills(character.getSkills().size());

        return new Enemy(name, lifePoints, attack, defense, experience, level, skills, type, rewardXP);
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
        int experience = character.getLevel() * 150;
        int level = character.getLevel() + 1;
        int rewardXP = level * 50;
        String name = EnemyType.BOSS.getDescription() + " Lv" + level;
        ArrayList<Skill> skills = generateRandomSkills(character.getSkills().size());

        return new Boss(name, lifePoints, attack, defense, experience, level, skills, EnemyType.BOSS, rewardXP, "");
    }

    /**
     * Generates a list of skills by selecting three skills randomly from a predefined list of skills.
     *
     * @param numberOfSkills Number of skills to generate.
     * @return An ArrayList of Skill objects.
     */
    private static ArrayList<Skill> generateRandomSkills(int numberOfSkills) {
        ArrayList<Skill> availableSkills = SkillManager.defaultSkills;

        ArrayList<Skill> skills = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfSkills; i++) {
            int index = random.nextInt(availableSkills.size());
            skills.add(availableSkills.get(index));
        }

        return skills;
    }
}
