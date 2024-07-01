package org.rpggame.rewards.items;

import org.rpggame.entities.characters.Character;
import org.rpggame.rewards.items.effects.EffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ItemsManager {
    public static final ArrayList<Item> defaultItems = new ArrayList<>(List.of(
            new Item("Espada Envenenada", ItemRarity.UNCOMMON, EffectType.POISONING),
            new Item("Varinha do Sono", ItemRarity.RARE, EffectType.SLEEP),
            new Item("Anel Flamejante", ItemRarity.RARE, EffectType.BURN),
            new Item("Poção de Atordoamento", ItemRarity.UNCOMMON, EffectType.STUN),
            new Item("Amuleto da Maldição", ItemRarity.EPIC, EffectType.POISONING),
            new Item("Elmo do Sono Profundo", ItemRarity.EPIC, EffectType.SLEEP),
            new Item("Poção de Fogo", ItemRarity.COMMON, EffectType.BURN),
            new Item("Cajado do Trovão", ItemRarity.RARE, EffectType.STUN)
    ));

    /**
     * Returns a random item from default list excluding items the character already has
     *
     * @param itemsExcluded The character's item list.
     * @return A randomly selected item from the default list of items.
     */
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

    /**
     * Returns a random item from the character's available items.
     * Most used when an enemy is selecting an item to use during the battle.
     *
     * @param character The character.
     * @return A randomly selected item from the character's items.
     */
    public static Item getRandomItemFromCharacter(Character character) {
        ArrayList<Item> items = character.getItems();

        Random random = new Random();
        int choice = random.nextInt(items.size());

        return items.get(choice);
    }

    /**
     * Generates a list of items from a predefined list,
     * based on the number of items the character has.
     *
     * @param numberOfItems Number of items to generate.
     * @return An ArrayList of Items objects.
     */
    public static ArrayList<Item> generateRandomItems(int numberOfItems) {
        ArrayList<Item> availableSkills = ItemsManager.defaultItems;

        ArrayList<Item> items = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfItems; i++) {
            int index = random.nextInt(availableSkills.size());
            items.add(availableSkills.get(index));
        }

        return items;
    }
}
