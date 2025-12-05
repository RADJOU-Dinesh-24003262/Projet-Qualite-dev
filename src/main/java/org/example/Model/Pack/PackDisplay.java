package org.example.Model.Pack;

import org.example.Model.Character.Werewolf.Werewolf;

import java.util.List;

public class PackDisplay {

    private final HierarchyManager hierarchy;
    private final AlphaManager alphaManager;

    public PackDisplay(HierarchyManager hierarchy, AlphaManager alphaManager) {
        this.hierarchy = hierarchy;
        this.alphaManager = alphaManager;
    }

    public void displayPack(String packName) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           PACK INFORMATION           ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.println("🏷 Pack Name : " + packName);
        System.out.println("🐺 Members   : " + hierarchy.getMembers().size());

        System.out.println("♂ Alpha Male   : " + alphaManager.getAlphaMale().getName()
                + " (Strength: " + alphaManager.getAlphaMale().getStrength() + ")");

        System.out.println("♀ Alpha Female : " + alphaManager.getAlphaFemale().getName()
                + " (Strength: " + alphaManager.getAlphaFemale().getStrength() + ")");

        System.out.println("\n----- Hierarchy Distribution -----");
        hierarchy.getHierarchy().forEach((rank, list) -> {
            if (!list.isEmpty()) {
                System.out.println(rank.getSymbol() + " (" + rank.name() + ") : " + list.size() + " members");
            }
        });

        System.out.println("══════════════════════════════════════════\n");
    }

    public void displayMembers() {

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         PACK MEMBERS DETAILS         ║");
        System.out.println("╚══════════════════════════════════════╝");

        for (Rank rank : Rank.values()) {
            List<Werewolf> list = hierarchy.getHierarchy().get(rank);

            if (!list.isEmpty()) {
                System.out.println("\n--- Rank " + rank.getSymbol() + " (" + rank.name() + ") ---");

                for (Werewolf w : list) {
                    System.out.println("• " + w.getName()
                            + " | " + w.getSex()
                            + " | Strength: " + w.getStrength()
                            + " | Level: " + String.format("%.2f", w.calculateLevel())
                            + " | Domination: " + w.getDominationFactor());
                }
            }
        }

        System.out.println("══════════════════════════════════════════\n");
    }
}
