package org.example.model.character.werewolf;

/**
 * Defines the age categories for a Werewolf, which affects their abilities.
 * Each category has a specific multiplier that can be used to adjust stats like strength or stamina.
 */
public enum AgeCategory {
    /**
     * A young werewolf, typically less experienced but agile.
     * Multiplier: 0.8
     */
    YOUNG("Young", 0.8),
    /**
     * An adult werewolf in its prime.
     * Multiplier: 1.0
     */
    ADULT("Adult", 1.0),
    /**
     * An old werewolf, possibly weaker but more experienced.
     * Multiplier: 0.9
     */
    OLD("Old", 0.9);

    /**
     * The display name for the age category.
     */
    private final String label;
    /**
     * The multiplier associated with the age category, used to adjust character stats.
     */
    private final double multiplier;

    /**
     * Constructs an AgeCategory with a label and a stat multiplier.
     * @param label The display name of the category.
     * @param multiplier The multiplier for character stats.
     */
    AgeCategory(String label, double multiplier) {
        this.label = label;
        this.multiplier = multiplier;
    }

    /**
     * Gets the display name of the age category.
     * @return The label for the category.
     */
    public String getLabel() { return label; }

    /**
     * Gets the stat multiplier for the age category.
     * @return The multiplier value.
     */
    public double getMultiplier() { return multiplier; }
}
