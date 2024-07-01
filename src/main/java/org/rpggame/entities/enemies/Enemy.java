package org.rpggame.entities.enemies;

import org.rpggame.entities.characters.Character;

import lombok.*;
import org.rpggame.skills.Skill;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Enemy extends Character {
    private EnemyType type;
    private int rewardXP;

    public Enemy(String name, int maxHealth, int maxAttack, int maxDefense, int experience, int level, ArrayList<Skill> skills, EnemyType type, int rewardXP) {
        super(name, maxHealth, maxAttack, maxDefense, experience, level, skills);
        this.type = type;
        this.rewardXP = rewardXP;
    }
}
