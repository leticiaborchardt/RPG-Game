package org.rpggame.rewards.items;

import lombok.Getter;

@Getter
public enum ItemEffect {
    POISONING("Envenenamento"),
    STUN("Atordoação"),
    BURN("Queimadura"),
    SLEEP("Dormir");

    private final String description;

    ItemEffect(String description) {
        this.description = description;
    }
}
