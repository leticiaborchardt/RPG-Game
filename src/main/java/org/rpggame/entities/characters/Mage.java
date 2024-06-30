package org.rpggame.entities.characters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mage extends Character {
    private int mana;
    private int magicPower;
    private int magicResistance;

    public Mage(String name) {
        super(name, 30, 20, 20);
        this.magicPower = 20;
        this.magicResistance = 5;
        this.mana = 100;
    }

    public void printInformation() {
        super.printInformation();
        System.out.println("Mana: " + this.getMana());
        System.out.println("Poder Mágico: " + this.getMagicPower());
        System.out.println("Resistência à magia: " + this.getMagicResistance());
    }
}
