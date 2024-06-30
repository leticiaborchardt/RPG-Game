package org.rpggame.entities.characters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Archer extends Character {
    private int precision;
    private int maxPrecision;
    private int agility;
    private int maxAgility;

    public Archer(String name) {
        super(name, 90, 18, 7);
        this.maxPrecision = 10;
        this.precision = this.maxPrecision;
        this.maxAgility = 15;
        this.agility = this.maxAgility;
    }

    public void printInformation() {
        super.printInformation();
        System.out.println("Precisão: " + this.getPrecision());
        System.out.println("Agilidade: " + this.getAgility());
    }

    @Override
    public void specialAttack(Character opponent) {
        System.out.println(this.getName() + " está usando seu ataque especial!");

        int damage = Math.max(1, this.getAttack() + this.getPrecision() - opponent.getDefense());
        opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.3));
    }

    @Override
    public void levelUp() {
        this.setLevel(this.getLevel() + 1);
        this.setExperience(this.getExperience() - this.getExperienceRequiredForNextLevel());

        this.increasePointsLevelUp();

        this.setMaxPrecision(this.getMaxPrecision() + 5);
        this.setMaxAgility(this.getMaxAgility() + 5);

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
        this.setAgility(this.getMaxAgility());
        this.setPrecision(this.getMaxPrecision());
    }
}
