package org.rpggame.rewards.skills;

import lombok.*;
import org.rpggame.rewards.Reward;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill implements Reward {
    private String name;
    private SkillType type;
    private int baseDamage;
    private String description;

    @Override
    public String toString() {
        return this.getName() +
                "\n\"" + this.getDescription() + "\"" +
                "\nTipo: " + this.getType().getDescription() +
                " | Dano base: " + this.getBaseDamage();
    }
}
