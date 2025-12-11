package org.example.model.character.werewolf;

/**
 * A helper class responsible for calculating statistics for a {@link Werewolf}.
 * This class encapsulates the logic for determining a werewolf's overall level based on its attributes.
 */
public class WerewolfStats {

    /** The werewolf instance whose stats are being calculated. */
    private final Werewolf w;

    // Weights for calculating the werewolf's level
    /** Weight of the strength attribute in the level calculation. */
    private static final double W_STRENGTH = 0.4;
    /** Weight of the domination factor in the level calculation. */
    private static final double W_DOMINATION = 0.3;
    /** Weight of the pack rank in the level calculation. */
    private static final double W_RANK = 0.2;
    /** Weight of the health attribute in the level calculation. */
    private static final double W_HEALTH = 0.1;

    /**
     * Constructs a stats calculator for a specific werewolf.
     * @param w The werewolf for which to calculate stats.
     */
    public WerewolfStats(Werewolf w) {
        this.w = w;
    }

    /**
     * Computes the werewolf’s overall level based on a weighted sum of its attributes.
     * The formula is:
     * <p>
     * {@code (Strength * W_STRENGTH + Domination * W_DOMINATION + Rank * W_RANK + Health * W_HEALTH) * AgeMultiplier}
     * </p>
     * @return The calculated overall level of the werewolf.
     */
    public double calculateLevel() {
        double base = w.getStrength() * W_STRENGTH
                + w.getDominationFactor() * W_DOMINATION
                + w.getRank() * W_RANK
                + w.getHealth() * W_HEALTH;

        return base * w.getAgeCategory().getMultiplier();
    }
}
