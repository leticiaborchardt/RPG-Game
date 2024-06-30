package org.rpggame.entities.characters;

import lombok.*;

import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Character {
    protected String name;
    protected int lifePoints;
    protected int attack;
    protected int defense;
    protected int experience;
    protected int level;
    protected boolean isReadyToFightBoss;

    public Character(String name, int lifePoints, int attack, int defense) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.attack = attack;
        this.defense = defense;
        this.experience = 0;
        this.level = 1;
        this.isReadyToFightBoss = false;
    }

    public void gainExperience(int xp) {
        this.setReadyToFightBoss(false);
        this.setExperience(this.getExperience() + xp);

        while (this.getExperience() >= this.getExperienceRequiredForNextLevel()) {
            levelUp();
        }
    }

    public void attack(Character target) {
        System.out.println(this.getName() + " está atacando!");

        int damage = Math.max(1, this.getAttack() - target.getDefense());
        target.decreaseLifePoints(damage);
    }

    public void deffend() {
        this.setDefense(this.getDefense() + 5);

        System.out.println(this.getName() + " está se defendendo.");
    }

    // TODO: implementar chance de sucesso ou falha da fuga de acordo com características do personagem
    public boolean tryEscape() {
        Random random = new Random();
        boolean successfulEscape = random.nextBoolean();

        if (successfulEscape) {
            System.out.println(this.getName() + " está fugindo!");
            this.decreaseXP(50);
        } else {
            System.out.println(this.getName() + " tentou fugir porém fracassou.");
            this.decreaseLifePoints(5);
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

    private void decreaseLifePoints(int points) {
        this.setLifePoints(Math.max(0, this.getLifePoints() - points));
        System.out.println(this.getName() + " perdeu " + points + " pontos de vida.");
    }

    private void decreaseXP(int points) {
        this.setExperience(Math.max(0, this.getExperience() - points));
        System.out.println(this.getName() + " perdeu " + points + " pontos de XP.");
    }

    private void levelUp() {
        this.setLevel(this.getLevel() + 1);
        this.setExperience(this.getExperience() - this.getExperienceRequiredForNextLevel());

        this.setReadyToFightBoss(true);
        System.out.println("Level Up! " + this.getName() + " subiu para o nível " + this.getLevel() + " e está pronto para enfrentar o chefão!");
    }

    private int getExperienceRequiredForNextLevel() {
        return 100 * this.getLevel();
    }
}
