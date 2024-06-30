package org.rpggame.entities.characters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Warrior extends Character {
    private int strength;
    private int endurance;

    public Warrior(String name) {
        super(name, 50, 10, 15);
        this.strength = 15;
        this.endurance = 10;
    }

    public void printInformation() {
        super.printInformation();
        System.out.println("Força: " + this.getStrength());
        System.out.println("Resistência: " + this.getEndurance());
    }
}
