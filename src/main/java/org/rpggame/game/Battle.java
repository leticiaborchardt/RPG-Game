package org.rpggame.game;

import lombok.*;
import org.fusesource.jansi.Ansi;
import org.rpggame.entities.characters.Archer;
import org.rpggame.entities.characters.Character;
import org.rpggame.entities.characters.Mage;
import org.rpggame.entities.characters.Warrior;
import org.rpggame.entities.enemies.Boss;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.rewards.items.Item;
import org.rpggame.rewards.items.ItemsManager;
import org.rpggame.rewards.skills.Skill;
import org.rpggame.utils.ConsoleMessage;
import org.rpggame.utils.InputValidator;

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
            showEndOfTurnMessage();

            if (this.checkIfBattleIsOver()) {
                break;
            }

            newTurn(enemy, character);
            showEndOfTurnMessage();

            if (this.checkIfBattleIsOver()) {
                break;
            }
        }
    }

    /**
     * List on the console the participants of the battle.
     */
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

        attacker.receiveEffect();
        opponent.receiveEffect();

        if (attacker.isPreventedFromFighting()) {
            return;
        }

        attacker.checkEffectDuration();
        opponent.checkEffectDuration();

        while (true) {
            switch (getTurnAction(attacker)) {
                case 1:
                    attacker.attack(opponent);
                    return;
                case 2:
                    attacker.specialAttack(opponent);
                    return;
                case 3:
                    attacker.deffend();
                    return;
                case 4:
                    Skill choosenSkill = attacker instanceof Enemy ? attacker.getRandomSkill() : chooseSkill(attacker);
                    Skill opponentSkill = attacker instanceof Enemy ? opponent.getRandomSkill() : chooseSkill(opponent);

                    attacker.skillAttack(opponent, opponentSkill, choosenSkill);
                    return;
                case 5:
                    if (attacker.getItems().isEmpty()) {
                        if (!(attacker instanceof Enemy)) {
                            ConsoleMessage.println("Você não possui nenhum item!", Ansi.Color.RED);
                        }

                        break;
                    }

                    Item item = attacker instanceof Enemy ? ItemsManager.getRandomItemFromCharacter(attacker) : chooseItem();

                    attacker.itemAttack(opponent, item);
                    return;
                case 6:
                    if (attacker.tryEscape()) {
                        this.setBattleOver(true);
                        ConsoleMessage.println("\nA batalha terminou!", Ansi.Color.RED);
                    }

                    return;
                default:
                    ConsoleMessage.printInvalidOptionMessage();
                    break;
            }
        }
    }

    /**
     * Prints an end turn message on the console.
     */
    private void showEndOfTurnMessage() {
        ConsoleMessage.println("\nFIM DE TURNO!\n", Ansi.Color.MAGENTA);
        if (character instanceof Mage) ((Mage) character).regenerateMana(5);

        character.printInformation();
        enemy.printInformation();
    }

    /**
     * Returns the choosen action for the turn, based on the attacker type of character.
     *
     * @param attacker The character of the current turn.
     * @return The number of the chosen option.
     */
    private int getTurnAction(Character attacker) {
        int action;

        if (attacker instanceof Enemy) {
            try {
                ConsoleMessage.println(attacker.getName() + " está escolhendo sua ação...");
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                ConsoleMessage.println("Não foi possível completar a ação.");
            }

            Random random = new Random();

            // Excludes the option to use an item if the enemy has none
            action = random.nextInt(attacker.getItems().isEmpty() ? 4 : 5) + 1;
        } else {
            ConsoleMessage.println("Escolha uma ação:", Ansi.Color.BLUE);
            action = InputValidator.getInteger(this.getBattleOptions());
        }

        return action;
    }

    /**
     * Allows the character to choose a skill to use or attack in battle.
     *
     * @param character The character himself or the enemy.
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
            ConsoleMessage.println("\n[" + (i + 1) + "] " + skills.get(i).toString(), Ansi.Color.CYAN);
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
     * Allows the character to choose an item to use in battle.
     *
     * @return The selected item from the character's available items.
     */
    private Item chooseItem() {
        ConsoleMessage.println(character.getName() + ", escolha um item para usar:", Ansi.Color.BLUE);

        ArrayList<Item> items = character.getItems();

        for (int i = 0; i < items.size(); i++) {
            ConsoleMessage.println("\n[" + (i + 1) + "] " + items.get(i).toString(), Ansi.Color.CYAN);
        }

        int choice = InputValidator.getInteger("Escolha um item:") - 1;

        if (choice >= 0 && choice < items.size()) {
            return items.get(choice);
        } else {
            ConsoleMessage.printInvalidOptionMessage();
            return chooseItem();
        }
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
        }

        ConsoleMessage.println("\n------------------------------------------");
        ConsoleMessage.println("FIM DA BATALHA!", Ansi.Color.RED);
        ConsoleMessage.println("Vencedor: " + winner.getName(), Ansi.Color.BLUE);
        if (totalXP != 0) ConsoleMessage.println("Total de XP ganho: " + totalXP);
        ConsoleMessage.println("------------------------------------------");

        winner.gainExperience(totalXP);

        if (defeated instanceof Boss) {
            winner.gainRewards((Boss) defeated);
        }

        this.setBattleOver(true);
    }

    /**
     * Generates the battle options menu based on the type of attacker.
     *
     * @return A string representing the available battle options.
     */
    private String getBattleOptions() {
        String options = "[1] Ataque padrão\n";

        if (character instanceof Archer) options = options + "[2] Ataque especial (Arqueiro)\n";
        if (character instanceof Mage) options = options + "[2] Ataque especial (Mago)\n";
        if (character instanceof Warrior) options = options + "[2] Ataque especial (Guerreiro)\n";

        options = options + "[3] Defender-se\n" +
                "[4] Usar habilidade\n" +
                "[5] Usar item\n" +
                "[6] Tentar fugir";

        return options;
    }
}