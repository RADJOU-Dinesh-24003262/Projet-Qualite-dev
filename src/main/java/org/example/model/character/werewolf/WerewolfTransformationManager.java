package org.example.model.character.werewolf;

import org.example.model.pack.Pack;

/**
 * Manages the transformation process for a {@link Werewolf} between its human and wolf forms.
 * <p>
 * This class handles the statistical changes that occur during transformation and includes
 * complex behavioral consequences, such as the possibility of a high-level werewolf
 * permanently leaving its pack upon turning human.
 * </p>
 */
public class WerewolfTransformationManager {

    /** The werewolf instance managed by this class. */
    private final Werewolf werewolf;

    /** The multiplier applied to strength when transforming to human form. */
    private static final double STR_MULT = 0.5;
    /** The multiplier applied to stamina when transforming to human form. */
    private static final double STA_MULT = 0.7;
    /** The level threshold above which a werewolf might consider leaving its pack. */
    private static final double LEVEL_THRESHOLD_LEAVE = 50.0;
    /** The base probability for a high-level werewolf to leave its pack upon transformation. */
    private static final double LEAVE_PROBABILITY = 0.3;

    /**
     * Constructs a transformation manager for a specific werewolf.
     * @param w The werewolf whose transformations are to be managed.
     */
    public WerewolfTransformationManager(Werewolf w) {
        this.werewolf = w;
    }

    /**
     * Transforms the werewolf into its human form. This reduces its strength and stamina.
     * After transforming, there is a check to see if a high-level werewolf will decide
     * to permanently leave its pack.
     */
    public void toHuman() {
        if (werewolf.isHuman()) {
            return;
        }

        werewolf.setHuman(true);
        werewolf.setStrength((int) (werewolf.getStrength() * STR_MULT));
        werewolf.setStamina((int) (werewolf.getStamina() * STA_MULT));

        System.out.println(werewolf.getName() + " transforms into human form.");

        // Check if the werewolf should leave the pack based on its level
        checkPackDeparture();
    }

    /**
     * Transforms the werewolf back into its wolf form from human form.
     * This restores its strength and stamina to their original values.
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
     * Checks if the werewolf should leave its pack after transforming to human form.
     * A werewolf will only consider leaving if its level exceeds a certain threshold.
     * The decision to leave is based on a random chance.
     */
    private void checkPackDeparture() {
        Pack pack = werewolf.getPack();
        if (pack == null) {
            return;
        }

        double level = werewolf.calculateLevel();
        
        // Only high-level werewolves have the option to leave
        if (level < LEVEL_THRESHOLD_LEAVE) {
            System.out.println("  → " + werewolf.getName() 
                + " is not strong enough to survive alone (level: " 
                + String.format("%.2f", level) + ")");
            return;
        }

        // A probability-based chance to leave the pack
        if (Math.random() < LEAVE_PROBABILITY) {
            System.out.println("  → " + werewolf.getName() 
                + " decides to leave the pack permanently!");
            werewolf.leavePack();
            
            // This signifies the werewolf is now considered "lost" to the pack community
            System.out.println("  → " + werewolf.getName() 
                + " is now living as a human, lost to the pack.");
        } else {
            System.out.println("  → " + werewolf.getName() 
                + " remains loyal to the pack despite transformation.");
        }
    }

    /**
     * Calculates the probability of a werewolf leaving its pack.
     * The probability is zero if the werewolf is not in human form or if its level
     * is below the required threshold. Otherwise, the probability increases with its level.
     *
     * @return A probability value between 0.0 and 1.0.
     */
    public double getLeaveProbability() {
        if (!werewolf.isHuman()) {
            return 0.0;
        }

        double level = werewolf.calculateLevel();
        if (level < LEVEL_THRESHOLD_LEAVE) {
            return 0.0;
        }

        // Higher level equals a higher probability of leaving
        return Math.min(1.0, (level - LEVEL_THRESHOLD_LEAVE) / 100.0);
    }
}