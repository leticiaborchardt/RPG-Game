package org.rpggame.utils;

import org.rpggame.entities.characters.Character;
import org.rpggame.entities.enemies.Boss;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.entities.enemies.EnemyType;
import org.rpggame.skills.Skill;
import org.rpggame.skills.SkillType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class EnemyGenerator {
    static ArrayList<Skill> availableSkills = new ArrayList<>(List.of(
            new Skill("Inferno Ardente", SkillType.FIRE, 20, "Uma explosão infernal que incinera os inimigos."),
            new Skill("Tsunami Devastador", SkillType.WATER, 20, "Uma onda gigantesca que engole e destrói tudo em seu caminho."),
            new Skill("Raio Fulminante", SkillType.ELECTRIC, 20, "Um poderoso raio que eletrocuta e paralisa os oponentes."),
            new Skill("Fúria da Terra", SkillType.EARTH, 20, "Um violento tremor que rasga o chão e devasta os inimigos."),
            new Skill("Tempestade Veloz", SkillType.AIR, 20, "Um furacão de vento cortante que derruba os adversários."),
            new Skill("Garras do Ártico", SkillType.ICE, 20, "Garras geladas que congelam e destroem os inimigos.")
    ));

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

        ArrayList<Skill> skills = generateRandomSkills();

        return new Enemy(name, lifePoints, attack, defense, experience, level, skills, type, rewardXP);
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

        ArrayList<Skill> skills = generateRandomSkills();

        return new Boss(name, lifePoints, attack, defense, experience, level, skills, EnemyType.BOSS, rewardXP, "");
    }

    private static ArrayList<Skill> generateRandomSkills() {
        ArrayList<Skill> skills = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(availableSkills.size());
            skills.add(availableSkills.get(index));
        }

        return skills;
    }
}
