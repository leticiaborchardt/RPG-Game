package org.rpggame.entities.characters;

import lombok.*;
import org.rpggame.skills.Skill;

import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Character {
    protected String name;
    protected int lifePoints;
    protected int maxHealth;
    protected int attack;
    protected int maxAttack;
    protected int defense;
    protected int maxDefense;
    protected int experience;
    protected int level;
    protected boolean isReadyToFightBoss;
    protected ArrayList<Skill> skills;

    public Character(String name, int maxHealth, int maxAttack, int maxDefense) {
        this.name = name;
        this.lifePoints = maxHealth;
        this.maxHealth = maxHealth;
        this.attack = maxAttack;
        this.maxAttack = maxAttack;
        this.defense = maxDefense;
        this.maxDefense = maxDefense;
        this.experience = 0;
        this.level = 1;
        this.isReadyToFightBoss = false;
        this.skills = new ArrayList<>();
    }

    public Character(String name, int maxHealth, int maxAttack, int maxDefense, int experience, int level) {
        this.name = name;
        this.lifePoints = maxHealth;
        this.maxHealth = maxHealth;
        this.attack = maxAttack;
        this.maxAttack = maxAttack;
        this.defense = maxDefense;
        this.maxDefense = maxDefense;
        this.experience = experience;
        this.level = level;
    }

    public void gainExperience(int xp) {
        this.setReadyToFightBoss(false);
        this.setExperience(this.getExperience() + xp);

        while (this.getExperience() >= this.getExperienceRequiredForNextLevel()) {
            levelUp();
        }
    }

    public void attack(Character opponent) {
        System.out.println(this.getName() + " está atacando!");

        int damage = Math.max(1, this.getAttack() - opponent.getDefense());
        opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.3));
    }

    public void deffend() {
        this.setDefense(this.getDefense() + 3);

        System.out.println(this.getName() + " está se defendendo.");
    }

    public boolean tryEscape() {
        Random random = new Random();
        boolean successfulEscape = random.nextBoolean();

        if (successfulEscape) {
            System.out.println(this.getName() + " está fugindo!");
            this.decreaseXP(50);
        } else {
            System.out.println(this.getName() + " tentou fugir porém fracassou.");
            this.decreaseLifePoints((int) (this.getLifePoints() * 1.2));
        }

        return successfulEscape;
    }

    public boolean isDead() {
        return this.getLifePoints() <= 0;
    }

    public void printInformation() {
        System.out.println("Nome: " + this.getName());
        System.out.println("Vida: " + this.getLifePoints());
        System.out.println("Ataque: " + this.getAttack());
        System.out.println("Defesa: " + this.getDefense());
        System.out.println("XP: " + this.getExperience());
        System.out.println("Nível: " + this.getLevel());
    }

    protected void decreaseLifePoints(int points) {
        this.setLifePoints(Math.max(0, this.getLifePoints() - points));
        System.out.println(this.getName() + " perdeu " + points + " pontos de vida.");
    }

    protected void decreaseXP(int points) {
        this.setExperience(Math.max(0, this.getExperience() - points));
        System.out.println(this.getName() + " perdeu " + points + " pontos de XP.");
    }

    protected void levelUp() {
        this.setLevel(this.getLevel() + 1);
        this.setExperience(this.getExperience() - this.getExperienceRequiredForNextLevel());

        this.increasePointsLevelUp();
    }

    protected void increasePointsLevelUp() {
        this.setMaxHealth(this.getMaxHealth() + 15);
        this.setMaxAttack(this.getMaxAttack() + 5);
        this.setMaxDefense(this.getMaxDefense() + 5);
    }

    protected int getExperienceRequiredForNextLevel() {
        return 100 * this.getLevel();
    }

    public void specialAttack(Character opponent) {
        System.out.println(this.getName() + " está usando seu ataque especial!");

        int damage = Math.max(1, (int)(this.getAttack() * 1.2) - opponent.getDefense());
        opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.6));
    }

    public int tryCriticalAttack(int damage, double chance) {
        Random random = new Random();
        double randomValue = random.nextDouble();

        if (randomValue < chance) {
            System.out.println("ATAQUE CRÍTICO!");
            return damage * 2;
        }

        return damage;
    }

    public void regenerate() {
        this.setLifePoints(this.getMaxHealth());
        this.setAttack(this.getMaxAttack());
        this.setDefense(this.getMaxDefense());
    }
}
