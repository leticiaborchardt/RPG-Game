package org.rpggame.menu;

import org.fusesource.jansi.Ansi;
import org.rpggame.rewards.skills.Skill;
import org.rpggame.rewards.skills.SkillManager;
import org.rpggame.utils.ConsoleMessage;
import org.rpggame.utils.InputValidator;

import java.util.ArrayList;

/**
 * This class is used to manipulate the choice of skills for the character in the game.
 */
public final class SkillsOptions {
    /**
     * Allows the user to choose one skill from a predefined list.
     * Displays available skills and prompts the user to select one skill.
     *
     * @return The choosen skill.
     */
    public static Skill chooseSkill() {
        ArrayList<Skill> availableSkills = SkillManager.defaultSkills;

        while (true) {
            ConsoleMessage.println("Escolha sua habilidade inicial:", Ansi.Color.BLUE);

            for (int i = 0; i < availableSkills.size(); i++) {
                ConsoleMessage.println("\n[" + (i + 1) + "] " + availableSkills.get(i).toString());
            }

            int choice = InputValidator.getInteger("\nEscolha uma habilidade") - 1;

            if (choice >= 0 && choice < availableSkills.size()) {
                return availableSkills.get(choice);
            } else {
                ConsoleMessage.printInvalidOptionMessage();
            }
        }
    }
}
