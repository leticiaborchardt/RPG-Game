package org.rpggame.utils;

import org.rpggame.entities.characters.Character;
import org.rpggame.rewards.Reward;
import org.rpggame.rewards.items.ItemsManager;
import org.rpggame.rewards.skills.SkillManager;

import java.util.Random;

public class RewardGenerator {
    public static Reward generateReward(Character character) {
        Random random = new Random();
        boolean isItemReward = random.nextBoolean();

        if (isItemReward) {
            return ItemsManager.getRandomItem(character.getItems());
        }

        return SkillManager.getRandomSkill(character.getSkills());
    }
}
