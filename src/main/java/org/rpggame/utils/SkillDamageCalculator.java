package org.rpggame.utils;

import org.rpggame.rewards.skills.Skill;
import org.rpggame.rewards.skills.SkillType;

/**
 * Contains methods to perform damage calculation based on the skill chosen and the one faced.
 */
public final class SkillDamageCalculator {
    /**
     * Calculates the damage inflicted by the attacker's skill on the opponent, based on their skill types.
     * Uses a damage multiplier determined by the types of the attacker's and opponent's skills.
     *
     * @param attackerSkill The skill used by the attacker to calculate damage.
     * @param opponentSkill The skill used by the opponent to determine damage resistance.
     * @return The calculated damage value as an integer.
     */
    public static int calculateDamage(Skill attackerSkill, Skill opponentSkill) {
        double damageMultiplier = getDamageMultiplier(attackerSkill.getType(), opponentSkill.getType());

        return (int) (attackerSkill.getBaseDamage() * damageMultiplier);
    }

    /**
     * Determines the damage multiplier based on the types of the attacker's skill and the opponent's skill.
     * Adjusts damage calculation based on elemental strengths and weaknesses.
     *
     * @param attackerSkillType The type of skill used by the attacker.
     * @param opponentSkillType The type of skill used by the opponent to resist damage.
     * @return The damage multiplier as a double value.
     */
    private static double getDamageMultiplier(SkillType attackerSkillType, SkillType opponentSkillType) {
        switch (attackerSkillType) {
            case FIRE:
                if (opponentSkillType == SkillType.WATER) return 0.5;
                if (opponentSkillType == SkillType.ICE) return 1.5;
                break;
            case WATER:
                if (opponentSkillType == SkillType.FIRE) return 1.5;
                if (opponentSkillType == SkillType.ELECTRIC) return 0.5;
                break;
            case ELECTRIC:
                if (opponentSkillType == SkillType.WATER) return 1.5;
                if (opponentSkillType == SkillType.EARTH) return 0.5;
                break;
            case EARTH:
                if (opponentSkillType == SkillType.ELECTRIC) return 1.5;
                if (opponentSkillType == SkillType.AIR) return 0.5;
                break;
            case AIR:
                if (opponentSkillType == SkillType.EARTH) return 1.5;
                if (opponentSkillType == SkillType.FIRE) return 0.5;
                break;
            case ICE:
                if (opponentSkillType == SkillType.WATER) return 1.5;
                if (opponentSkillType == SkillType.FIRE) return 0.5;
                break;
            default:
                return 1.0;
        }

        return 1.0;
    }
}
