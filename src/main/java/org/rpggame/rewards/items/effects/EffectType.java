package org.rpggame.rewards.items.effects;

import lombok.Getter;

@Getter
public enum EffectType {
    POISONING("Envenenamento"),
    STUN("Atordoação"),
    BURN("Queimadura"),
    SLEEP("Dormir");

    private final String description;

    EffectType(String description) {
        this.description = description;
    }
}
