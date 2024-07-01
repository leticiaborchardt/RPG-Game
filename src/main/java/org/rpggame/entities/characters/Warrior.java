package org.rpggame.entities.characters;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import lombok.*;
import org.fusesource.jansi.Ansi;
import org.rpggame.utils.ConsoleMessage;

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

    @Override
    public String getFormattedName() {
        return this.getName() + " - Guerreiro Lv" + this.getLevel();
    }

    @Override
    public void specialAttack(Character opponent) {
        ConsoleMessage.println(this.getName() + " está usando seu ataque especial!", Ansi.Color.YELLOW);

        int damage = Math.max(1, this.getStrength() * 3 - opponent.getDefense());
        opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.3));
    }

    @Override
    public void levelUp() {
        this.increasePointsLevelUp();

        this.setStrength(this.getMaxStrength() + 5);
        this.setEndurance(this.getMaxEndurance() + 5);
        this.regenerate();

        this.setReadyToFightBoss(true);
        this.showLevelUpMessage();
    }

    @Override
    public void regenerate() {
        this.setLifePoints(this.getMaxHealth());
        this.setAttack(this.getMaxAttack());
        this.setDefense(this.getMaxDefense());
        this.setEndurance(this.getMaxEndurance());
        this.setStrength(this.getMaxStrength());
        this.setActiveEffect(null);
        this.setPreventedFromFighting(false);
    }

    @Override
    protected AsciiTable getAsciiTable() {
        AsciiTable asciiTable = new AsciiTable();

        asciiTable.addRule();
        asciiTable.addRow("Nome", "Classe", "Vida", "Ataque", "Defesa", "Força", "Resistência");
        asciiTable.addRule();
        asciiTable.addRow(
                this.getName(), "Guerreiro Lv" + this.getLevel(), this.getLifePoints(), this.getAttack(),
                this.getDefense(), this.getStrength(), this.getEndurance()
        );
        asciiTable.addRule();

        asciiTable.setTextAlignment(TextAlignment.CENTER);
        return asciiTable;
    }
}
