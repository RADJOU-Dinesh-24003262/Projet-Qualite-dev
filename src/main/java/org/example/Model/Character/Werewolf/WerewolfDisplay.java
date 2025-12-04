package org.example.Model.Character.Werewolf;

public class WerewolfDisplay {

    private final Werewolf w;

    public WerewolfDisplay(Werewolf w) {
        this.w = w;
    }

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
