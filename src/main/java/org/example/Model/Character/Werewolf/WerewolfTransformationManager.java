package org.example.Model.Character.Werewolf;

import org.example.Model.Pack.Pack;

/**
 * Manages werewolf transformations between human and werewolf forms.
 * Handles the consequences of transformations including pack departure.
 */
public class WerewolfTransformationManager {

    private final Werewolf werewolf;

    private static final double STR_MULT = 0.5;
    private static final double STA_MULT = 0.7;
    private static final double LEVEL_THRESHOLD_LEAVE = 50.0;
    private static final double LEAVE_PROBABILITY = 0.3;

    public WerewolfTransformationManager(Werewolf w) {
        this.werewolf = w;
    }

    /**
     * Transforms the werewolf into human form.
     * High-level werewolves may leave their pack when transforming.
     */
    public void toHuman() {
        if (werewolf.isHuman()) {
            return;
        }

        werewolf.setHuman(true);
        werewolf.setStrength((int) (werewolf.getStrength() * STR_MULT));
        werewolf.setStamina((int) (werewolf.getStamina() * STA_MULT));

        System.out.println(werewolf.getName() + " transforms into human form.");

        // Check if werewolf should leave pack based on level
        checkPackDeparture();
    }

    /**
     * Transforms back into werewolf form.
     */
    public void toWerewolf() {
        if (!werewolf.isHuman()) {
            return;
        }

        werewolf.setHuman(false);
        werewolf.setStrength((int) (werewolf.getStrength() / STR_MULT));
        werewolf.setStamina((int) (werewolf.getStamina() / STA_MULT));

        System.out.println(werewolf.getName() + " transforms back into werewolf form!");
    }

    /**
     * Checks if the werewolf should leave the pack after transforming to human.
     * High-level werewolves have a chance to leave permanently.
     */
    private void checkPackDeparture() {
        Pack pack = werewolf.getPack();
        if (pack == null) {
            return;
        }

        double level = werewolf.calculateLevel();
        
        // Only high-level werewolves can leave
        if (level < LEVEL_THRESHOLD_LEAVE) {
            System.out.println("  → " + werewolf.getName() 
                + " is not strong enough to survive alone (level: " 
                + String.format("%.2f", level) + ")");
            return;
        }

        // Probability based departure
        if (Math.random() < LEAVE_PROBABILITY) {
            System.out.println("  → " + werewolf.getName() 
                + " decides to leave the pack permanently!");
            werewolf.leavePack();
            
            // This is equivalent to the werewolf's "death" in pack terms
            System.out.println("  → " + werewolf.getName() 
                + " is now living as a human, lost to the pack.");
        } else {
            System.out.println("  → " + werewolf.getName() 
                + " remains loyal to the pack despite transformation.");
        }
    }

    /**
     * Gets the probability for a werewolf to leave based on its level.
     * 
     * @return probability between 0 and 1
     */
    public double getLeaveProbability() {
        if (!werewolf.isHuman()) {
            return 0.0;
        }

        double level = werewolf.calculateLevel();
        if (level < LEVEL_THRESHOLD_LEAVE) {
            return 0.0;
        }

        // Higher level = higher probability to leave
        return Math.min(1.0, (level - LEVEL_THRESHOLD_LEAVE) / 100.0);
    }
}