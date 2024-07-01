package org.rpggame.entities.enemies;

import lombok.Getter;

import java.util.Random;

@Getter
public enum EnemyType {
    GOBLIN("Goblin"),
    ORC("Orc"),
    TROLL("Troll"),
    DRAGON("Dragão"),
    GHOST("Fantasma"),
    BOSS("Chefão");

    private final String description;

    EnemyType(String description) {
        this.description = description;
    }

    public static EnemyType getRandomType() {
        Random rand = new Random();
        EnemyType[] values = {GOBLIN, ORC, TROLL, DRAGON, GHOST};
        return values[rand.nextInt(values.length)];
    }
}

