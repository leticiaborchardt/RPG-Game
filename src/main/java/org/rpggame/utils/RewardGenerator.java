package org.rpggame.utils;

import org.rpggame.entities.characters.Character;
import org.rpggame.rewards.Reward;
import org.rpggame.rewards.items.ItemsManager;
import org.rpggame.rewards.skills.SkillManager;

import java.util.Random;

public class RewardGenerator {
    /**
     * Generates a random reward for a given character.
     *
     * @param character The character for whom the reward.
     * @return A Reward object which can be either an item or a skill.
     */
    public static Reward generateReward(Character character) {
        Random random = new Random();
        boolean isItemReward = random.nextBoolean();

        if (isItemReward) {
            return ItemsManager.getRandomItem(character.getItems());
        }

        return SkillManager.getRandomSkill(character.getSkills());
    }
}
