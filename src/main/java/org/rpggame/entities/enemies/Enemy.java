package org.rpggame.entities.enemies;

import org.rpggame.entities.characters.Character;

import lombok.*;
import org.rpggame.rewards.items.Item;
import org.rpggame.rewards.skills.Skill;

import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
public class Enemy extends Character {
    private EnemyType type;
    private int rewardXP;

    public Enemy(String name, int maxHealth, int maxAttack, int maxDefense, int experience, int level, ArrayList<Skill> skills, ArrayList<Item> items, EnemyType type, int rewardXP) {
        super(name, maxHealth, maxAttack, maxDefense, experience, level, skills, items);
        this.type = type;
        this.rewardXP = rewardXP;
    }
}
