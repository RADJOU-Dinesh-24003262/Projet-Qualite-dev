package org.example.model.pack;

import org.example.model.Character.werewolf.Werewolf;
import org.example.model.Character.werewolf.AgeCategory;

import java.util.List;

public class OmegaManager {

    private static final double OMEGA_STRENGTH_RATIO = 0.7;

    private final HierarchyManager hierarchy;

    public OmegaManager(HierarchyManager hierarchy) {
        this.hierarchy = hierarchy;
    }

    public void identifyOmegaWerewolves() {

        List<Werewolf> members = hierarchy.getMembers();

        double avg = members.stream()
                .filter(w -> w.getAgeCategory() == AgeCategory.ADULT)
                .mapToInt(Werewolf::getStrength)
                .average()
                .orElse(0);

        double threshold = avg * OMEGA_STRENGTH_RATIO;

        System.out.println("🔍 Identifying omega werewolves (strength < " + String.format("%.2f", threshold) + ")");

        for (Rank rank : Rank.values()) {
            if (rank == Rank.ALPHA || rank == Rank.OMEGA)
                continue;

            for (Werewolf w : List.copyOf(hierarchy.getHierarchy().get(rank))) {
                if (w.getAgeCategory() == AgeCategory.ADULT &&
                    w.getStrength() < threshold) {

                    hierarchy.removeMember(w);
                    hierarchy.addMember(w, Rank.OMEGA);

                    System.out.println("⬇ " + w.getName() + " is now " + Rank.OMEGA.getSymbol() + " (omega)");
                }
            }
        }
    }
}
