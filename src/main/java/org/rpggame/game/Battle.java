package org.rpggame.game;

import lombok.*;
import org.fusesource.jansi.Ansi;
import org.rpggame.entities.characters.Archer;
import org.rpggame.entities.characters.Character;
import org.rpggame.entities.characters.Mage;
import org.rpggame.entities.characters.Warrior;
import org.rpggame.entities.enemies.Boss;
import org.rpggame.entities.enemies.Enemy;
import org.rpggame.utils.ConsoleMessage;
import org.rpggame.utils.InputValidator;

import java.util.Random;

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

    private void newTurn(Character attacker, Character target) {
        if (attacker.isDead()) return;

        ConsoleMessage.println("\nNOVO TURNO: Turno de " + attacker.getName() + "\n", Ansi.Color.MAGENTA);

        int action;
        if (attacker instanceof Enemy) {
            try {
                ConsoleMessage.println(attacker.getName() + " está escolhendo sua ação...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Random random = new Random();
            action = random.nextInt(2) + 1;
        } else {
            ConsoleMessage.println("Escolha uma ação:", Ansi.Color.BLUE);
            action = InputValidator.getInteger(this.getBattleOptions(attacker));
        }

        switch (action) {
            case 1:
                attacker.attack(target);
                break;
            case 2:
                attacker.specialAttack(target);
                break;
            case 3:
                attacker.deffend();
                break;
            case 4:
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
        target.printInformation();
    }

    private boolean checkIfBattleIsOver() {
        if (character.isDead()) {
            endBattle(enemy, character);
        } else if (enemy.isDead()) {
            endBattle(character, enemy);
        }

        return this.isBattleOver();
    }

    private void endBattle(Character winner, Character defeated) {
        ConsoleMessage.println(defeated.getName() + " foi derrotado!", Ansi.Color.YELLOW);

        int totalXP = 0;
        if (defeated instanceof Enemy) {
            totalXP = ((Enemy) defeated).getRewardXP();
            winner.gainExperience(totalXP);
        }

        if (defeated instanceof Boss) {
            // TODO: lógica para ganhar os itens especiais do chefão
        }

        this.setBattleOver(true);

        ConsoleMessage.println("\nA batalha terminou!" , Ansi.Color.RED);
        ConsoleMessage.println("Vencedor: " + winner.getName(), Ansi.Color.BLUE);
        if (totalXP != 0) ConsoleMessage.println("Total de XP ganho: " + totalXP);
    }

    private String getBattleOptions(Character attacker) {
        String options = "[1] Ataque padrão\n";

        if (attacker instanceof Archer) options = options + "[2] Atirar flecha\n";
        if (attacker instanceof Mage) options = options + "[2] Lançar feitiço\n";
        if (attacker instanceof Warrior) options = options + "[2] Soco poderoso\n";

        options = options + "[3] Defender-se\n" +
                "[4] Tentar fugir";

        return options;
    }
}