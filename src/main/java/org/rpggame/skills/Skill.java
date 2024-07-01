package org.rpggame.skills;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
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
