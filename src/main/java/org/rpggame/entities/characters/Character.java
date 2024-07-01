package org.rpggame.entities.characters;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import lombok.*;
import org.fusesource.jansi.Ansi;
import org.rpggame.entities.enemies.Boss;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.rewards.Reward;
import org.rpggame.rewards.items.Item;
import org.rpggame.rewards.items.effects.Effect;
import org.rpggame.rewards.items.effects.EffectType;
import org.rpggame.rewards.skills.Skill;
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
    protected boolean isPreventedFromFighting;
    protected ArrayList<Skill> skills = new ArrayList<>();
    protected ArrayList<Item> items = new ArrayList<>();
    protected Effect activeEffect;

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
        this.isPreventedFromFighting = false;
        this.activeEffect = null;
    }

    public Character(String name, int maxHealth, int maxAttack, int maxDefense, int experience, int level, ArrayList<Skill> skills, ArrayList<Item> items) {
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
        this.items = items;
        this.isPreventedFromFighting = false;
        this.activeEffect = null;
    }

    public void addSkill(Skill skill) {
        this.getSkills().add(skill);
    }

    public void addItem(Item item) {
        this.getItems().add(item);
    }

    /**
     * Returns a random skill from the character's available skills.
     * Most used when an enemy is selecting a skill to use/attack during the battle.
     *
     * @return A randomly selected skill from the character's skills.
     */
    public Skill getRandomSkill() {
        ArrayList<Skill> skills = this.getSkills();

        Random random = new Random();
        int choice = random.nextInt(skills.size());

        return skills.get(choice);
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

        if (!this.getItems().isEmpty()) {
            ConsoleMessage.println("Itens", this instanceof Enemy ? Ansi.Color.RED : Ansi.Color.GREEN);
            for (Item item : this.getItems()) {
                ConsoleMessage.println(item.getName() + " | Raridade: " + item.getRarity().getDescription() + " | Efeito: " + item.getEffectType().getDescription());
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
        asciiTable.addRow("Nome", "Vida", "Ataque", "Defesa");
        asciiTable.addRule();
        asciiTable.addRow(this.getName(), this.getLifePoints(), this.getAttack(), this.getDefense());
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
        this.regenerate();
        this.showLevelUpMessage();
    }

    protected void increasePointsLevelUp() {
        this.setLevel(this.getLevel() + 1);
        this.setExperience(Math.max(0, this.getExperience() - this.getExperienceRequiredForNextLevel()));

        this.setMaxHealth(this.getMaxHealth() + 15);
        this.setMaxAttack(this.getMaxAttack() + 5);
        this.setMaxDefense(this.getMaxDefense() + 5);
    }

    protected void showLevelUpMessage() {
        ConsoleMessage.println("\nLevel Up! " + this.getName() + " subiu para o nível " + this.getLevel(), Ansi.Color.GREEN);
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
        this.setActiveEffect(null);
        this.setPreventedFromFighting(false);
    }

    public void gainRewards(Boss boss) {
        Reward reward = boss.getReward();

        if (reward != null) {
            if (reward instanceof Item) {
                this.addItem((Item) reward);
                ConsoleMessage.println("Parabéns! Você ganhou um novo item:", Ansi.Color.BLUE);
            } else {
                this.addSkill((Skill) reward);
                ConsoleMessage.println("Parabéns! Você ganhou uma nova habilidade:", Ansi.Color.BLUE);
            }

            ConsoleMessage.println(reward.toString(), Ansi.Color.MAGENTA);
        }
    }

    public void itemAttack(Character opponent, Item item) {
        ConsoleMessage.println(this.getName() + " usou o item \"" + item.getName() + "\"!", Ansi.Color.YELLOW);

        Effect effect = new Effect(item.getEffectType());
        opponent.setActiveEffect(effect);

        ConsoleMessage.println(
                opponent.getName() + " está sobre o efeito de \"" + effect.getType().getDescription() + "\" durante " + effect.getDuration() + " rodadas!",
                Ansi.Color.RED
        );
    }

    public void receiveEffect() {
        if (this.hasActiveEffect()) {
            Effect effect = this.getActiveEffect();

            if (effect.getType() == EffectType.STUN || effect.getType() == EffectType.SLEEP) {
                this.setPreventedFromFighting(true);

                if (effect.getDuration() > 0) {
                    ConsoleMessage.println(this.getName() + " está impedido de realizar ações neste turno devido ao efeito de " + effect.getType().getDescription());
                }
            } else {
                this.setPreventedFromFighting(false);
            }

            if (effect.getDamage() > 0) {
                this.setLifePoints(Math.max(0, this.getLifePoints() - effect.getDamage()));
                ConsoleMessage.println(
                        this.getName() + " perdeu " + effect.getDamage() + " pontos de vida devido ao efeito de " + effect.getType().getDescription(),
                        Ansi.Color.RED
                );
            }
        }
    }

    public void checkEffectDuration() {
        if (this.hasActiveEffect()) {
            this.getActiveEffect().setDuration(this.getActiveEffect().getDuration() - 1);

            if (this.getActiveEffect().getDuration() <= 0) {
                this.setActiveEffect(null);
                this.setPreventedFromFighting(false);
            } else {
                ConsoleMessage.println("Duração restante: " + this.getActiveEffect().getDuration() + " turnos");
            }
        }
    }

    protected boolean hasActiveEffect() {
        return this.getActiveEffect() != null;
    }
}

