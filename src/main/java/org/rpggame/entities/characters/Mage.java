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
public class Mage extends Character {
    private int mana;
    private int maxMana;
    private int magicPower;
    private int maxMagicPower;

    public Mage(String name) {
        super(name, 80, 20, 5);
        this.maxMagicPower = 20;
        this.magicPower = this.maxMagicPower;
        this.maxMana = 100;
        this.mana = this.maxMana;
    }

    @Override
    public String getFormattedName() {
        return this.getName() + " - Mago Lv" + this.getLevel();
    }

    public void regenerateMana(int points) {
        this.setMana(this.getMana() + points);
        ConsoleMessage.println(this.getName() + " regenerou " + points + " de mana.", Ansi.Color.GREEN);
    }

    private void decreaseMana(int points) {
        this.setMana(Math.max(0, this.getMana() - points));
        ConsoleMessage.println(this.getName() + " perdeu " + points + " pontos de mana.", Ansi.Color.RED);
    }

    @Override
    public void specialAttack(Character opponent) {
        if (this.getMana() >= 20) {
            ConsoleMessage.println(this.getName() + " está usando seu ataque especial!", Ansi.Color.YELLOW);

            int damage = Math.max(1, this.getAttack() + this.getMagicPower() - opponent.getDefense());

            opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.3));

            this.decreaseMana(20);
        } else {
            ConsoleMessage.println(this.getName() + " não possui mana o suficiente para atacar!", Ansi.Color.RED);
        }
    }

    @Override
    public void levelUp() {
        this.increasePointsLevelUp();

        this.setMaxMagicPower(this.getMaxMagicPower() + 5);
        this.setMaxMana(this.getMaxMana() + 10);

        this.setReadyToFightBoss(true);
        this.showLevelUpMessage();
    }

    @Override
    public void regenerate() {
        this.setLifePoints(this.getMaxHealth());
        this.setAttack(this.getMaxAttack());
        this.setDefense(this.getMaxDefense());
        this.setMana(this.getMaxMana());
        this.setMagicPower(this.getMaxMagicPower());
    }

    @Override
    protected AsciiTable getAsciiTable() {
        AsciiTable asciiTable = new AsciiTable();

        asciiTable.addRule();
        asciiTable.addRow("Nome", "Classe", "Vida", "Ataque", "Defesa", "Mana", "Poder Mágico");
        asciiTable.addRule();
        asciiTable.addRow(
                this.getName(), "Mago Lv" + this.getLevel(), this.getLifePoints(), this.getAttack(),
                this.getDefense(), this.getMana(), this.getMagicPower()
        );
        asciiTable.addRule();

        asciiTable.setTextAlignment(TextAlignment.CENTER);
        return asciiTable;
    }
}
