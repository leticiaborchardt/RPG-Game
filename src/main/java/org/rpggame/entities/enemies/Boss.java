package org.rpggame.entities.enemies;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Boss extends Enemy {
    // TODO: implementar itens de recompensa
    private String itemReward;

    public Boss(String name, int lifePoints, int attack, int defense, int experience, int level, EnemyType type, int rewardXP, String itemReward) {
        super(name, lifePoints, attack, defense, experience, level, type, rewardXP);
        this.itemReward = itemReward;
    }
}
