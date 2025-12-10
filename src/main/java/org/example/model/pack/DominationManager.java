package org.example.model.pack;

import org.example.model.Character.werewolf.Werewolf;

public class DominationManager {

    private final HierarchyManager hierarchy;
    private final AlphaManager alphaManager;

    public DominationManager(HierarchyManager hierarchy, AlphaManager alphaManager) {
        this.hierarchy = hierarchy;
        this.alphaManager = alphaManager;
    }

    public boolean attemptDomination(Werewolf aggressor, Werewolf target) {

        if (aggressor == null || target == null) return false;
        if (aggressor == target) return false;

        if (!hierarchy.getMembers().contains(aggressor) ||
            !hierarchy.getMembers().contains(target)) {
            System.out.println("❌ Both werewolves must belong to the same pack.");
            return false;
        }

        // Cannot dominate alpha female
        if (target == alphaManager.getAlphaFemale()) {
            System.out.println("⚠ " + aggressor.getName() + " cannot dominate the alpha female!");
            return false;
        }

        // Check impetuosity + strength
        if (target.getStrength() > aggressor.getStrength() &&
            Math.random() > aggressor.getImpetuosityFactor()) {
            System.out.println("⚠ " + aggressor.getName() + " lacks confidence to dominate " + target.getName());
            return false;
        }

        boolean success = aggressor.calculateLevel() > target.calculateLevel()
                       || isOmega(target);

        if (success) {
            handleSuccess(aggressor, target);
            return true;
        } else {
            handleFailure(aggressor, target);
            return false;
        }
    }


    private void handleSuccess(Werewolf aggressor, Werewolf target) {
        System.out.println("💥 " + aggressor.getName() + " successfully dominates " + target.getName());

        aggressor.setDominationFactor(aggressor.getDominationFactor() + 1);
        target.setDominationFactor(target.getDominationFactor() - 1);

        Rank rankA = hierarchy.getRankOf(aggressor);
        Rank rankT = hierarchy.getRankOf(target);

        if (rankA != null && rankT != null && rankA != rankT) {
            hierarchy.removeMember(aggressor);
            hierarchy.removeMember(target);

            hierarchy.addMember(aggressor, rankT);
            hierarchy.addMember(target, rankA);

            System.out.println("📌 Rank swap: "
                    + aggressor.getName() + " is now " + rankT.getSymbol() + ", "
                    + target.getName() + " is now " + rankA.getSymbol());
        }
    }

    private void handleFailure(Werewolf aggressor, Werewolf target) {
        System.out.println("❌ Domination failed: " + aggressor.getName() + " → " + target.getName());
        System.out.println("🔥 " + target.getName() + " becomes aggressive!");

        target.setBelligerence(target.getBelligerence() + 15);
        aggressor.setDominationFactor(aggressor.getDominationFactor() - 1);
    }

    private boolean isOmega(Werewolf w) {
        return hierarchy.getHierarchy().get(Rank.OMEGA).contains(w);
    }
}
