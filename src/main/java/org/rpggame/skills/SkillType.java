package org.rpggame.skills;

import lombok.Getter;

@Getter
public enum SkillType {
    FIRE("Fogo"),
    WATER("Água"),
    ELECTRIC("Elétrico"),
    EARTH("Terra"),
    AIR("Ar"),
    ICE("Gelo");

    private final String description;

    SkillType(String description) {
        this.description = description;
    }
}