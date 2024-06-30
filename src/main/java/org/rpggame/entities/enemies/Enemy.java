package org.rpggame.entities.enemies;

import org.rpggame.entities.characters.Character;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Enemy extends Character {
    private EnemyType type;
    private int rewardXP;

    public Enemy(String name, int lifePoints, int attack, int defense, int experience, int level, EnemyType type, int rewardXP) {
        super(name, lifePoints, attack, defense, experience, level, false);
        this.type = type;
        this.rewardXP = rewardXP;
    }
}
