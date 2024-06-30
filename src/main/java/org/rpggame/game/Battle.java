package org.rpggame.game;

import lombok.*;
import org.rpggame.entities.characters.Archer;
import org.rpggame.entities.characters.Character;
import org.rpggame.entities.characters.Mage;
import org.rpggame.entities.characters.Warrior;
import org.rpggame.entities.enemies.Boss;
import org.rpggame.entities.enemies.Enemy;
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
        System.out.println("Batalha iniciada entre " + character.getName() + " e " + enemy.getName());
        System.out.println(character.getName() + ": " + character.getLifePoints() + " HP");
        System.out.println(enemy.getName() + ": " + enemy.getLifePoints() + " HP");
    }

    private void newTurn(Character attacker, Character target) {
        if (attacker.isDead()) return;

        System.out.println("Turno de " + attacker.getName());

        int action;
        if (attacker instanceof Enemy) {
            Random random = new Random();
            action = random.nextInt(2) + 1;
        } else {
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
                } else {
                    return;
                }
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }

        System.out.println(attacker.getName() + ": " + attacker.getLifePoints() + " HP | Ataque: " + attacker.getAttack() + " | Defesa: " + attacker.getDefense());
        System.out.println(target.getName() + ": " + target.getLifePoints() + " HP | Ataque: " + target.getAttack() + " | Defesa: " + target.getDefense());

        if (attacker instanceof Mage) ((Mage) character).regenerateMana(5);
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
        System.out.println(defeated.getName() + " foi derrotado!");

        int totalXP = 0;
        if (defeated instanceof Enemy) {
            totalXP = ((Enemy) defeated).getRewardXP();
            winner.gainExperience(totalXP);
        }

        if (defeated instanceof Boss) {
            // TODO: lógica para ganhar os itens especiais do chefão
        }

        this.setBattleOver(true);

        System.out.println("A batalha terminou!");
        System.out.println("Vencedor: " + winner.getName());
        if (totalXP != 0) System.out.println("Total de XP ganho: " + totalXP);
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