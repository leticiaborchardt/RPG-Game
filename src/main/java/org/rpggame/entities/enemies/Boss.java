package org.rpggame.entities.enemies;

import lombok.*;
import org.rpggame.rewards.Reward;
import org.rpggame.rewards.items.Item;
import org.rpggame.rewards.skills.Skill;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Boss extends Enemy {
    private Reward reward;

    public Boss(String name, int lifePoints, int attack, int defense, int experience, int level, ArrayList<Skill> skills, ArrayList<Item> items, EnemyType type, int rewardXP, Reward reward) {
        super(name, lifePoints, attack, defense, experience, level, skills, items, type, rewardXP);
        this.reward = reward;
    }
}
