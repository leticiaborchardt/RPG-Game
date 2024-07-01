package org.rpggame.rewards.items;

import lombok.*;
import org.rpggame.rewards.Reward;
import org.rpggame.rewards.items.effects.EffectType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Reward {
    private String name;
    private ItemRarity rarity;
    private EffectType effectType;

    @Override
    public String toString() {
        return this.getName() +
                "\nRaridade: " + this.getRarity().getDescription() +
                " | Efeito: " + this.getEffectType().getDescription();
    }
}
