package org.rpggame.entities.enemies;

import lombok.*;
import org.rpggame.skills.Skill;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Boss extends Enemy {
    // TODO: implementar itens de recompensa
    private String itemReward;

    public Boss(String name, int lifePoints, int attack, int defense, int experience, int level, ArrayList<Skill> skills, EnemyType type, int rewardXP, String itemReward) {
        super(name, lifePoints, attack, defense, experience, level, skills, type, rewardXP);
        this.itemReward = itemReward;
    }
}
