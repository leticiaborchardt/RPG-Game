package org.rpggame.entities.characters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Warrior extends Character {
    private int strength;
    private int maxStrength;
    private int endurance;
    private int maxEndurance;


    public Warrior(String name) {
        super(name, 100, 15, 10);
        this.maxStrength = 10;
        this.strength = this.maxStrength;
        this.maxEndurance = 5;
        this.endurance = this.maxEndurance;
    }

    public void printInformation() {
        super.printInformation();
        System.out.println("Força: " + this.getStrength());
        System.out.println("Resistência: " + this.getEndurance());
    }

    @Override
    public void specialAttack(Character opponent) {
        System.out.println(this.getName() + " está usando seu ataque especial!");

        int damage = Math.max(1, this.getStrength() * 3 - opponent.getDefense());
        opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.3));
    }

    @Override
    public void levelUp() {
        this.setLevel(this.getLevel() + 1);
        this.setExperience(this.getExperience() - this.getExperienceRequiredForNextLevel());

        this.increasePointsLevelUp();

        this.setStrength(this.getMaxStrength() + 5);
        this.setEndurance(this.getMaxEndurance() + 5);

        this.setReadyToFightBoss(true);

        System.out.println("Level Up! " + this.getName() + " subiu para o nível " + this.getLevel());
        printInformation();
        System.out.println(this.getName() + " está pronto para enfrentar o chefão!");
    }

    @Override
    public void regenerate() {
        this.setLifePoints(this.getMaxHealth());
        this.setAttack(this.getMaxAttack());
        this.setDefense(this.getMaxDefense());
        this.setEndurance(this.getMaxEndurance());
        this.setStrength(this.getMaxStrength());
    }
}
