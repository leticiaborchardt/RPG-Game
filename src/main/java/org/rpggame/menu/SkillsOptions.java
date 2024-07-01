package org.rpggame.menu;

import org.fusesource.jansi.Ansi;
import org.rpggame.skills.Skill;
import org.rpggame.skills.SkillType;
import org.rpggame.utils.ConsoleMessage;
import org.rpggame.utils.InputValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to manipulate the choice of skills for the character in the game.
 */
public final class SkillsOptions {
    /**
     * Allows the user to choose skills from a predefined list.
     * Displays available skills and prompts the user to select up to three skills.
     *
     * @return An ArrayList of Skill objects, the choosen skills.
     */
    public static ArrayList<Skill> chooseSkills() {
        ArrayList<Skill> availableSkills = new ArrayList<>(List.of(
                new Skill("Inferno Ardente", SkillType.FIRE, 20, "Uma explosão infernal que incinera os inimigos."),
                new Skill("Tsunami Devastador", SkillType.WATER, 20, "Uma onda gigantesca que engole e destrói tudo em seu caminho."),
                new Skill("Raio Fulminante", SkillType.ELECTRIC, 20, "Um poderoso raio que eletrocuta e paralisa os oponentes."),
                new Skill("Fúria da Terra", SkillType.EARTH, 20, "Um violento tremor que rasga o chão e devasta os inimigos."),
                new Skill("Tempestade Veloz", SkillType.AIR, 20, "Um furacão de vento cortante que derruba os adversários."),
                new Skill("Garras do Ártico", SkillType.ICE, 20, "Garras geladas que congelam e destroem os inimigos.")
        ));

        ConsoleMessage.println("Escolha suas habilidades:", Ansi.Color.BLUE);
        ArrayList<Skill> chosenSkills = new ArrayList<>();

        while (chosenSkills.size() < 3) {
            for (int i = 0; i < availableSkills.size(); i++) {
                ConsoleMessage.println("\n[" + (i + 1) + "] " + availableSkills.get(i).toString());
            }

            int choice = InputValidator.getInteger("\nEscolha uma habilidade (" + (chosenSkills.size() + 1) + "/3):") - 1;

            if (choice >= 0 && choice < availableSkills.size()) {
                chosenSkills.add(availableSkills.get(choice));
                availableSkills.remove(choice);
            } else {
                ConsoleMessage.printInvalidOptionMessage();
            }
        }

        return chosenSkills;
    }
}
