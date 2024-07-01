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

    @Override
    public String getFormattedName() {
        return this.getName() + " - Arqueiro Lv" + this.getLevel();
    }

    @Override
    public void specialAttack(Character opponent) {
        ConsoleMessage.println(this.getName() + " está usando seu ataque especial!", Ansi.Color.YELLOW);

        int damage = Math.max(1, this.getAttack() + this.getPrecision() - opponent.getDefense());
        opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.3));
    }

    @Override
    public void levelUp() {
        this.increasePointsLevelUp();

        this.setMaxPrecision(this.getMaxPrecision() + 5);
        this.setMaxAgility(this.getMaxAgility() + 5);

        this.setReadyToFightBoss(true);
        this.showLevelUpMessage();
    }

    @Override
    public void regenerate() {
        this.setLifePoints(this.getMaxHealth());
        this.setAttack(this.getMaxAttack());
        this.setDefense(this.getMaxDefense());
        this.setAgility(this.getMaxAgility());
        this.setPrecision(this.getMaxPrecision());
    }

    @Override
    protected AsciiTable getAsciiTable() {
        AsciiTable asciiTable = new AsciiTable();

        asciiTable.addRule();
        asciiTable.addRow("Nome", "Classe", "Vida", "Ataque", "Defesa", "Precisão", "Agilidade");
        asciiTable.addRule();
        asciiTable.addRow(
                this.getName(), "Arqueiro Lv" + this.getLevel(), this.getLifePoints(), this.getAttack(),
                this.getDefense(), this.getPrecision(),this.getAgility()
        );
        asciiTable.addRule();

        asciiTable.setTextAlignment(TextAlignment.CENTER);
        return asciiTable;
    }
}
