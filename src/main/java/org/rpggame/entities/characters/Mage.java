package org.rpggame.entities.characters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mage extends Character {
    private int mana;
    private int maxMana;
    private int magicPower;
    private int maxMagicPower;
    private int magicResistance;
    private int maxMagicResistance;

    public Mage(String name) {
        super(name, 80, 20, 5);
        this.maxMagicPower = 20;
        this.magicPower = this.maxMagicPower;
        this.maxMagicResistance = 5;
        this.magicResistance = this.maxMagicResistance;
        this.maxMana = 100;
        this.mana = this.maxMana;
    }

    public void printInformation() {
        super.printInformation();
        System.out.println("Mana: " + this.getMana());
        System.out.println("Poder Mágico: " + this.getMagicPower());
        System.out.println("Resistência à magia: " + this.getMagicResistance());
    }

    @Override
    public void specialAttack(Character opponent) {
        if (this.getMana() >= 20) {
            System.out.println(this.getName() + " está usando seu ataque especial!");

            int damage = Math.max(1, this.getAttack() + this.getMagicPower() - opponent.getDefense());

            opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.3));

            this.decreaseMana(20);
        } else {
            System.out.println(this.getName() + " não possui mana o suficiente para atacar!");
        }
    }

    @Override
    public void levelUp() {
        this.setLevel(this.getLevel() + 1);
        this.setExperience(this.getExperience() - this.getExperienceRequiredForNextLevel());

        this.increasePointsLevelUp();

        this.setMaxMagicPower(this.getMaxMagicPower() + 5);
        this.setMaxMagicResistance(this.getMaxMagicResistance() + 3);
        this.setMaxMana(this.getMaxMana() + 10);

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
        this.setMana(this.getMaxMana());
        this.setMagicPower(this.getMaxMagicPower());
        this.setMagicResistance(this.getMaxMagicResistance());
    }

    public void regenerateMana(int points) {
        this.setMana(this.getMana() + points);
        System.out.println(this.getName() + " regenerou " + points + " de mana.");
    }

    private void decreaseMana(int points) {
        this.setMana(Math.max(0, this.getMana() - points));
        System.out.println(this.getName() + " perdeu " + points + " pontos de mana.");
    }
}
