package org.rpggame.rewards.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SkillManager {
    public static final ArrayList<Skill> defaultSkills = new ArrayList<>(List.of(
            new Skill("Inferno Ardente", SkillType.FIRE, 20, "Uma explosão infernal que incinera os inimigos."),
            new Skill("Tsunami Devastador", SkillType.WATER, 20, "Uma onda gigantesca que engole e destrói tudo em seu caminho."),
            new Skill("Raio Fulminante", SkillType.ELECTRIC, 20, "Um poderoso raio que eletrocuta e paralisa os oponentes."),
            new Skill("Fúria da Terra", SkillType.EARTH, 20, "Um violento tremor que rasga o chão e devasta os inimigos."),
            new Skill("Tempestade Veloz", SkillType.AIR, 20, "Um furacão de vento cortante que derruba os adversários."),
            new Skill("Garras do Ártico", SkillType.ICE, 20, "Garras geladas que congelam e destroem os inimigos.")
    ));

    public static Skill getRandomSkill(ArrayList<Skill> skillsExcluded) {
        List<Skill> availableItems = new ArrayList<>(defaultSkills);
        availableItems.removeAll(skillsExcluded);

        if (availableItems.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int index = random.nextInt(availableItems.size());

        return availableItems.get(index);
    }
}
