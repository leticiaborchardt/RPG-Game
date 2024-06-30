package org.rpggame.entities.characters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Archer extends Character {
    private int precision;
    private int agility;

    public Archer(String name) {
        super(name, 40, 15, 10);
        this.precision = 10;
        this.agility = 15;
    }

    public void printInformation() {
        super.printInformation();
        System.out.println("Precisão: " + this.getPrecision());
        System.out.println("Agilidade: " + this.getAgility());
    }
}
