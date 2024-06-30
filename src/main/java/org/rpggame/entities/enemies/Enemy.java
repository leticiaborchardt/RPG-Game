package org.rpggame.entities.enemies;

import org.rpggame.entities.characters.Character;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Enemy extends Character {
    private EnemyType type;
    private int rewardXP;

    public Enemy(String name, int maxHealth, int maxAttack, int maxDefense, int experience, int level, EnemyType type, int rewardXP) {
        super(name, maxHealth, maxAttack, maxDefense, experience, level);
        this.type = type;
        this.rewardXP = rewardXP;
    }
}
