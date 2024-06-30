package org.rpggame.game;

import lombok.*;
import org.rpggame.entities.characters.Character;
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
    private Enemy enemy;
    private Character attacker;
    private Character target;
    private boolean isBattleOver;

    public Battle(Character character, Enemy enemy) {
        this.character = character;
        this.enemy = enemy;
        this.isBattleOver = false;
    }

    public void start() {
        listPlayers();
        getTurnOrder();

        while (!this.isBattleOver()) {
            newTurn(attacker, target);

            if (this.checkIfBattleIsOver()) {
                break;
            }

            newTurn(target, attacker);

            if (this.checkIfBattleIsOver()) {
                break;
            }
        }
    }

    private void listPlayers() {
        System.out.println("Batalha iniciada entre " + character.getName() + " e " + enemy.getName());
        System.out.println(character.getName() + ": " + character.getLifePoints() + " HP");
        System.out.println(enemy.getName() + ": " + enemy.getLifePoints() + " HP");
    }

    private void getTurnOrder() {
        if (character.getLifePoints() < enemy.getLifePoints()) {
            attacker = character;
            target = enemy;
        } else {
            attacker = enemy;
            target = character;
        }

        System.out.println("Ordem dos turnos determinada.");
    }

    private void newTurn(Character attacker, Character target) {
        if (attacker.isDead()) return;

        System.out.println("Turno de " + attacker.getName());

        int action;
        if (attacker instanceof Enemy) {
            Random random = new Random();
            action = random.nextInt(2) + 1;
        } else {
            action = InputValidator.getInteger(this.getBattleOptions());
        }

        // TODO: desenvolver mais ações da batalha
        switch (action) {
            case 1:
                attacker.attack(target);
                break;
            case 2:
                attacker.deffend();
                break;
            case 3:
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
    }

    private boolean checkIfBattleIsOver() {
        if (attacker.isDead() && attacker instanceof Enemy) {
            endBattle(target, (Enemy) attacker);
        } else if (target.isDead() && target instanceof Enemy) {
            endBattle(attacker, (Enemy) target);
        }

        return this.isBattleOver();
    }

    private void endBattle(Character winner, Enemy defeated) {
        System.out.println(defeated.getName() + " foi derrotado!");

        winner.gainExperience(defeated.getRewardXP());

        if (defeated instanceof Boss) {
            // TODO: lógica para ganhar os itens especiais do chefão
        }

        this.setBattleOver(true);

        System.out.println("A batalha terminou!");
        System.out.println("Vencedor: " + winner.getName());
        System.out.println("Total de XP ganho: " + defeated.getRewardXP());
    }

    private String getBattleOptions() {
        return "[1] Atacar\n" +
                "[2] Defender\n" +
                "[3] Tentar fugir";
    }
}