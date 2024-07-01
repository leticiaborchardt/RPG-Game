package org.rpggame.game;

import lombok.*;
import org.fusesource.jansi.Ansi;
import org.rpggame.entities.characters.Archer;
import org.rpggame.entities.characters.Character;
import org.rpggame.entities.characters.Mage;
import org.rpggame.entities.characters.Warrior;
import org.rpggame.entities.enemies.Boss;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.rewards.Reward;
import org.rpggame.rewards.skills.Skill;
import org.rpggame.utils.ConsoleMessage;
import org.rpggame.utils.InputValidator;
import org.rpggame.utils.RewardGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a battle between a player character and an enemy.
 * Handles the flow of combat actions and outcomes.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class Battle {
    private Character character;
    private Character enemy;
    private boolean isBattleOver;

    public Battle(Character character, Enemy enemy) {
        this.character = character;
        this.enemy = enemy;
        this.isBattleOver = false;
    }

    /**
     * Initiates the battle sequence between the player character and the enemy.
     * Manages turn-based combat mechanics until one of the participants is defeated.
     */
    public void start() {
        listCharacters();

        while (!this.isBattleOver()) {
            newTurn(character, enemy);

            if (this.checkIfBattleIsOver()) {
                break;
            }

            newTurn(enemy, character);

            if (this.checkIfBattleIsOver()) {
                break;
            }
        }
    }

    private void listCharacters() {
        ConsoleMessage.println("--------------------------------------------------------------------------------");
        ConsoleMessage.print("Batalha iniciada entre ");
        ConsoleMessage.print(character.getName(), Ansi.Color.BLUE);
        ConsoleMessage.print(" e ");
        ConsoleMessage.println(enemy.getName(), Ansi.Color.RED);
        ConsoleMessage.println("--------------------------------------------------------------------------------");

        character.printInformation();
        enemy.printInformation();
    }

    /**
     * Executes a new turn in the battle sequence between two characters.
     * Manages actions based on the type of the attacker.
     *
     * @param attacker The character initiating the turn.
     * @param opponent The character being targeted by the attacker's actions.
     */
    private void newTurn(Character attacker, Character opponent) {
        if (attacker.isDead()) return;

        ConsoleMessage.println("\nNOVO TURNO: Turno de " + attacker.getName() + "\n", Ansi.Color.MAGENTA);

        int action;
        if (attacker instanceof Enemy) {
            try {
                ConsoleMessage.println(attacker.getName() + " está escolhendo sua ação...");
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Random random = new Random();
            action = random.nextInt(3) + 1;
        } else {
            ConsoleMessage.println("Escolha uma ação:", Ansi.Color.BLUE);
            action = InputValidator.getInteger(this.getBattleOptions(attacker));
        }

        switch (action) {
            case 1:
                attacker.attack(opponent);
                break;
            case 2:
                attacker.specialAttack(opponent);
                break;
            case 3:
                Skill choosenSkill;
                Skill opponentSkill;

                if (attacker instanceof Enemy) {
                    choosenSkill = chooseRandomSkill(attacker);
                    opponentSkill = chooseRandomSkill(opponent);
                } else {
                    choosenSkill = chooseSkill(attacker);
                    opponentSkill = chooseSkill(opponent);
                }

                attacker.skillAttack(opponent, opponentSkill, choosenSkill);
                break;
            case 4:
                attacker.deffend();
                break;
            case 5:
                if (attacker.tryEscape()) {
                    this.setBattleOver(true);
                    ConsoleMessage.println("\nA batalha terminou!" , Ansi.Color.RED);
                } else {
                    return;
                }
                break;
            default:
                ConsoleMessage.printInvalidOptionMessage();
        }

        ConsoleMessage.println("\nFIM DE TURNO!\n", Ansi.Color.MAGENTA);
        if (attacker instanceof Mage) ((Mage) character).regenerateMana(5);

        attacker.printInformation();
        opponent.printInformation();
    }

    /**
     * Allows the character to choose a skill to use in battle.
     *
     * @param character The character choosing the skill.
     * @return The selected skill from the character's available skills.
     */
    private Skill chooseSkill(Character character) {
        if (character instanceof Enemy) {
            ConsoleMessage.println("Escolha uma fraqueza do inimigo para atacar:", Ansi.Color.BLUE);
        } else {
            ConsoleMessage.println(character.getName() + ", escolha uma habilidade para usar:", Ansi.Color.BLUE);
        }

        ArrayList<Skill> skills = character.getSkills();

        for (int i = 0; i < skills.size(); i++) {
            ConsoleMessage.println("\n[" +(i + 1) + "] " + skills.get(i).toString(), Ansi.Color.CYAN);
        }

        int choice = InputValidator.getInteger("Escolha uma habilidade:") - 1;

        if (choice >= 0 && choice < skills.size()) {
            return skills.get(choice);
        } else {
            ConsoleMessage.printInvalidOptionMessage();
            return chooseSkill(character);
        }
    }

    /**
     * Chooses a random skill from the character's available skills.
     * Used when the enemy character is selecting a skill to use during attacks.
     *
     * @param character The character from which to choose a skill randomly.
     * @return A randomly selected skill from the character's skills.
     */
    private Skill chooseRandomSkill(Character character) {
        ArrayList<Skill> skills = character.getSkills();

        Random random = new Random();
        int choice = random.nextInt(skills.size());

        return skills.get(choice);
    }

    /**
     * Checks if the battle is over by verifying if either character or enemy is dead.
     *
     * @return True if the battle is over; false otherwise.
     */
    private boolean checkIfBattleIsOver() {
        if (character.isDead()) {
            endBattle(enemy, character);
        } else if (enemy.isDead()) {
            endBattle(character, enemy);
        }

        return this.isBattleOver();
    }

    /**
     * Ends the battle when a character is defeated, declaring the winner and handling rewards.
     *
     * @param winner   The character who won the battle.
     * @param defeated The character who was defeated in the battle.
     */
    private void endBattle(Character winner, Character defeated) {
        ConsoleMessage.println(defeated.getName() + " foi derrotado!", Ansi.Color.YELLOW);

        int totalXP = 0;
        if (defeated instanceof Enemy) {
            totalXP = ((Enemy) defeated).getRewardXP();
            winner.gainExperience(totalXP);
        }

        if (defeated instanceof Boss) {
            Reward reward = RewardGenerator.generateReward(winner);

            System.out.println("RECOMPENSA GERADA: " + reward.getClass().toString());
            // TODO: lógica para ganhar os itens especiais do chefão
        }

        this.setBattleOver(true);

        ConsoleMessage.println("\nA batalha terminou!" , Ansi.Color.RED);
        ConsoleMessage.println("Vencedor: " + winner.getName(), Ansi.Color.BLUE);
        if (totalXP != 0) ConsoleMessage.println("Total de XP ganho: " + totalXP);
    }

    /**
     * Generates the battle options menu based on the type of attacker.
     *
     * @param attacker The character initiating the battle turn.
     * @return A string representing the available battle options.
     */
    private String getBattleOptions(Character attacker) {
        String options = "[1] Ataque padrão\n";

        if (attacker instanceof Archer) options = options + "[2] Ataque especial (Arqueiro)\n";
        if (attacker instanceof Mage) options = options + "[2] Ataque especial (Mago)\n";
        if (attacker instanceof Warrior) options = options + "[2] Ataque especial (Guerreiro)\n";

        options = options + "[3] Usar habilidade\n" +
                "[4] Defender-se\n" +
                "[5] Tentar fugir";

        return options;
    }
}