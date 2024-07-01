package org.rpggame.rewards.items;

import lombok.Getter;

@Getter
public enum ItemRarity {
    COMMON("Comum"),
    UNCOMMON("Incomum"),
    RARE("Raro"),
    EPIC("Épico");

    private final String description;

    ItemRarity(String description) {
        this.description = description;
    }
}
