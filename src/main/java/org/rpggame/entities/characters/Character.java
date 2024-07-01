package org.rpggame.entities.characters;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import lombok.*;
import org.fusesource.jansi.Ansi;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.skills.Skill;
import org.rpggame.utils.ConsoleMessage;
import org.rpggame.utils.SkillDamageCalculator;

import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Character {
    protected String name;
    protected int lifePoints;
    protected int maxHealth;
    protected int attack;
    protected int maxAttack;
    protected int defense;
    protected int maxDefense;
    protected int experience;
    protected int level;
    protected boolean isReadyToFightBoss;
    protected ArrayList<Skill> skills = new ArrayList<>();

    public Character(String name, int maxHealth, int maxAttack, int maxDefense) {
        this.name = name;
        this.lifePoints = maxHealth;
        this.maxHealth = maxHealth;
        this.attack = maxAttack;
        this.maxAttack = maxAttack;
        this.defense = maxDefense;
        this.maxDefense = maxDefense;
        this.experience = 0;
        this.level = 1;
        this.isReadyToFightBoss = false;
    }

    public Character(String name, int maxHealth, int maxAttack, int maxDefense, int experience, int level, ArrayList<Skill> skills) {
        this.name = name;
        this.lifePoints = maxHealth;
        this.maxHealth = maxHealth;
        this.attack = maxAttack;
        this.maxAttack = maxAttack;
        this.defense = maxDefense;
        this.maxDefense = maxDefense;
        this.experience = experience;
        this.level = level;
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        this.getSkills().add(skill);
    }

    public void gainExperience(int xp) {
        this.setReadyToFightBoss(false);
        this.setExperience(this.getExperience() + xp);

        while (this.getExperience() >= this.getExperienceRequiredForNextLevel()) {
            levelUp();
        }
    }

    public void attack(Character opponent) {
        ConsoleMessage.println(this.getName() + " está atacando!", Ansi.Color.YELLOW);

        int damage = Math.max(1, this.getAttack() - opponent.getDefense());
        opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.3));
    }

    public void skillAttack(Character opponent, Skill opponentSkill, Skill choosenSkill) {
        ConsoleMessage.println(
                this.getName() + " usou a habilidade \"" + choosenSkill.getName() + "\" contra \"" + opponentSkill.getName() + "\"!",
                Ansi.Color.YELLOW
        );

        int damage = SkillDamageCalculator.calculateDamage(choosenSkill, opponentSkill);
        opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.3));
    }

    public void deffend() {
        this.setDefense(this.getDefense() + 3);

        ConsoleMessage.println(this.getName() + " está se defendendo.", Ansi.Color.YELLOW);
    }

    public boolean tryEscape() {
        Random random = new Random();
        boolean successfulEscape = random.nextBoolean();

        if (successfulEscape) {
            ConsoleMessage.println(this.getName() + " está fugindo!", Ansi.Color.GREEN);
            this.decreaseXP(50);
        } else {
            ConsoleMessage.println(this.getName() + " tentou fugir porém fracassou.", Ansi.Color.RED);
            this.decreaseLifePoints((int) (this.getLifePoints() * 1.2));
        }

        return successfulEscape;
    }

    public boolean isDead() {
        return this.getLifePoints() <= 0;
    }

    public void printInformation() {
        AsciiTable asciiTable = this.getAsciiTable();
        String render = asciiTable.render();

        ConsoleMessage.println(render, this instanceof Enemy ? Ansi.Color.RED : Ansi.Color.GREEN);

        if (!this.getSkills().isEmpty()) {
            ConsoleMessage.println("Habilidades", this instanceof Enemy ? Ansi.Color.RED : Ansi.Color.GREEN);
            for (Skill skill : this.getSkills()) {
                ConsoleMessage.println(skill.getName() + " | Tipo: " + skill.getType().getDescription() + " | Dano base: " + skill.getBaseDamage());
            }
        }

        if (!(this instanceof Enemy)) {
            ConsoleMessage.println("Total XP: " + this.getExperience(), Ansi.Color.GREEN);
            ConsoleMessage.println("XP necessária para o próximo nível: " + this.getExperienceRequiredForNextLevel() + "\n", Ansi.Color.WHITE);
        }
    }

    protected AsciiTable getAsciiTable() {
        AsciiTable asciiTable = new AsciiTable();

        asciiTable.addRule();
        asciiTable.addRow("Nome", "Vida", "Ataque", "Defesa", "Level");
        asciiTable.addRule();
        asciiTable.addRow(this.getName(), this.getLifePoints(), this.getAttack(), this.getDefense(), this.getLevel());
        asciiTable.addRule();

        asciiTable.setTextAlignment(TextAlignment.CENTER);
        return asciiTable;
    }

    protected void decreaseLifePoints(int points) {
        this.setLifePoints(Math.max(0, this.getLifePoints() - points));
        ConsoleMessage.println(this.getName() + " perdeu " + points + " pontos de vida.", Ansi.Color.RED);
    }

    protected void decreaseXP(int points) {
        this.setExperience(Math.max(0, this.getExperience() - points));
        ConsoleMessage.println(this.getName() + " perdeu " + points + " pontos de XP.", Ansi.Color.RED);
    }

    protected void levelUp() {
        this.increasePointsLevelUp();
        this.showLevelUpMessage();
    }

    protected void increasePointsLevelUp() {
        this.setLevel(this.getLevel() + 1);
        this.setExperience(this.getExperience() + this.getExperienceRequiredForNextLevel());

        this.setMaxHealth(this.getMaxHealth() + 15);
        this.setMaxAttack(this.getMaxAttack() + 5);
        this.setMaxDefense(this.getMaxDefense() + 5);
    }

    protected void showLevelUpMessage() {
        ConsoleMessage.println("Level Up! " + this.getName() + " subiu para o nível " + this.getLevel(), Ansi.Color.GREEN);
        printInformation();
        ConsoleMessage.println(this.getName() + " está pronto para enfrentar o chefão!", Ansi.Color.MAGENTA);
    }

    protected int getExperienceRequiredForNextLevel() {
        return 100 * this.getLevel();
    }

    public String getFormattedName() {
        return this.getName() + " Lv" + this.getLevel();
    }

    public void specialAttack(Character opponent) {
        ConsoleMessage.println(this.getName() + " está usando seu ataque especial!", Ansi.Color.YELLOW);

        int damage = Math.max(1, (int) (this.getAttack() * 1.2) - opponent.getDefense());
        opponent.decreaseLifePoints(this.tryCriticalAttack(damage, 0.6));
    }

    public int tryCriticalAttack(int damage, double chance) {
        Random random = new Random();
        double randomValue = random.nextDouble();

        if (randomValue < chance) {
            ConsoleMessage.println("ATAQUE CRÍTICO!", Ansi.Color.RED);
            return damage * 2;
        }

        return damage;
    }

    public void regenerate() {
        this.setLifePoints(this.getMaxHealth());
        this.setAttack(this.getMaxAttack());
        this.setDefense(this.getMaxDefense());
    }
}
