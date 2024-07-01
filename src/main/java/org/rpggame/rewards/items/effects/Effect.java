package org.rpggame.rewards.items.effects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Effect {
    private EffectType type;
    private int duration;
    private int damage;

    public Effect(EffectType type) {
        this.type = type;
        this.duration = this.determineEffectDuration();
        this.damage = this.determineEffectDamage();
    }

    public int determineEffectDuration() {
        return switch (this.getType()) {
            case EffectType.STUN -> 1;
            case EffectType.BURN -> 2;
            case EffectType.POISONING, EffectType.SLEEP -> 3;
        };
    }

    public int determineEffectDamage() {
        return switch (this.getType()) {
            case EffectType.BURN -> 5;
            case EffectType.POISONING -> 3;
            case EffectType.SLEEP, EffectType.STUN -> 0;
        };
    }
}
