package org.example.Model.Character.Werewolf;

public class WerewolfStats {

    private final Werewolf w;

    private static final double W_STRENGTH = 0.4;
    private static final double W_DOMINATION = 0.3;
    private static final double W_RANK = 0.2;
    private static final double W_HEALTH = 0.1;

    public WerewolfStats(Werewolf w) {
        this.w = w;
    }

    /** Compute the werewolf’s overall level */
    public double calculateLevel() {
        double base = (w.getStrength() * W_STRENGTH)
                + (w.getDominationFactor() * W_DOMINATION)
                + (w.getRank() * W_RANK)
                + (w.getHealth() * W_HEALTH);

        return base * w.getAgeCategory().getMultiplier();
    }
}
