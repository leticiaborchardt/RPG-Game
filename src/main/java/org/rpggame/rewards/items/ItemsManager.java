package org.rpggame.rewards.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ItemsManager {
    public static final ArrayList<Item> defaultItems = new ArrayList<>(List.of(
            new Item("Espada Envenenada", ItemRarity.UNCOMMON, ItemEffect.POISONING),
            new Item("Varinha do Sono", ItemRarity.RARE, ItemEffect.SLEEP),
            new Item("Anel Flamejante", ItemRarity.RARE, ItemEffect.BURN),
            new Item("Poção de Atordoamento", ItemRarity.UNCOMMON, ItemEffect.STUN),
            new Item("Amuleto da Maldição", ItemRarity.EPIC, ItemEffect.POISONING),
            new Item("Elmo do Sono Profundo", ItemRarity.EPIC, ItemEffect.SLEEP),
            new Item("Poção de Fogo", ItemRarity.COMMON, ItemEffect.BURN),
            new Item("Cajado do Trovão", ItemRarity.RARE, ItemEffect.STUN)
    ));

    public static Item getRandomItem(ArrayList<Item> itemsExcluded) {
        List<Item> availableItems = new ArrayList<>(defaultItems);
        availableItems.removeAll(itemsExcluded);

        if (availableItems.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int index = random.nextInt(availableItems.size());

        return availableItems.get(index);
    }
}
