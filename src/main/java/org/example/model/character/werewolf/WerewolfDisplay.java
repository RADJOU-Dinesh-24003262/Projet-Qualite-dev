package org.example.model.character.werewolf;

/**
 * A helper class responsible for displaying the characteristics of a {@link Werewolf}.
 * This class formats and prints the werewolf's attributes to the console in a readable way.
 */
public class WerewolfDisplay {

    /**
     * The werewolf instance whose characteristics will be displayed.
     */
    private final Werewolf w;

    /**
     * Constructs a display manager for a specific werewolf.
     * @param w The werewolf to be displayed.
     */
    public WerewolfDisplay(Werewolf w) {
        this.w = w;
    }

    /**
     * Prints the formatted characteristics of the werewolf to the standard output.
     * Includes details such as name, stats, rank, pack, and current form.
     */
    public void show() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║        Werewolf Characteristics     ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("Name: " + w.getName());
        System.out.println("Sex : " + w.getSex());
        System.out.println("Age Category: " + w.getAgeCategory().getLabel());
        System.out.println("Strength: " + w.getStrength());
        System.out.println("Stamina: " + w.getStamina());
        System.out.println("Health: " + w.getHealth());
        System.out.println("Domination: " + w.getDominationFactor());
        System.out.println("Rank: " + w.getRank());
        System.out.println("Level: " + String.format("%.2f", w.calculateLevel()));
        System.out.println("Pack: " + (w.getPack() != null ? w.getPack().getName() : "Solitary"));
        System.out.println("Form: " + (w.isHuman() ? "Human" : "Werewolf"));
        System.out.println("╚════════════════════════════════════╝\n");
    }
}
