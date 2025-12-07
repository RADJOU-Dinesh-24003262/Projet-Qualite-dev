package org.example.Model.Character.Werewolf;

public enum AgeCategory {
    YOUNG("Young", 0.8),
    ADULT("Adult", 1.0),
    OLD("Old", 0.9);

    private final String label;
    private final double multiplier;

    AgeCategory(String label, double multiplier) {
        this.label = label;
        this.multiplier = multiplier;
    }

    public String getLabel() { return label; }
    public double getMultiplier() { return multiplier; }
}
