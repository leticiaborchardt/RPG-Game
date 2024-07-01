package org.rpggame.rewards.items;

import lombok.*;
import org.rpggame.rewards.Reward;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Reward {
    private String name;
    private ItemRarity rarity;
    private ItemEffect effect;

    @Override
    public String toString() {
        return this.getName() +
                "\nRaridade: " + this.getRarity().getDescription() +
                " | Efeito: " + this.getEffect().getDescription();
    }
}
