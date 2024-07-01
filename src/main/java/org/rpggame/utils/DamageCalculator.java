package org.rpggame.utils;

import org.rpggame.skills.Skill;
import org.rpggame.skills.SkillType;

public final class DamageCalculator {
    public static int calculateDamage(Skill attackerSkill, Skill opponentSkill) {
        double damageMultiplier = getDamageMultiplier(attackerSkill.getType(), opponentSkill.getType());

        return (int) (attackerSkill.getBaseDamage() * damageMultiplier);
    }

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
