package org.example.Pack;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Werewolf.AgeCategory;
import org.example.Model.Character.Werewolf.Werewolf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReproductionManager {

    private static final int MIN_LITTER_SIZE = 1;
    private static final int MAX_LITTER_SIZE = 7;

    private final HierarchyManager hierarchy;
    private final AlphaManager alphaManager;

    public ReproductionManager(HierarchyManager hierarchy, AlphaManager alphaManager) {
        this.hierarchy = hierarchy;
        this.alphaManager = alphaManager;
    }

    public List<Werewolf> reproduce() {
        Random random = new Random();
        int litterSize = random.nextInt(MAX_LITTER_SIZE - MIN_LITTER_SIZE + 1) + MIN_LITTER_SIZE;

        System.out.println("🐺 The alpha couple is reproducing! Litter size: " + litterSize);

        List<Werewolf> cubs = new ArrayList<>();

        // If no BETA yet → cubs are BETA, else GAMMA
        Rank cubRank = hierarchy.getHierarchy().get(Rank.BETA).isEmpty()
                ? Rank.BETA
                : Rank.GAMMA;

        for (int i = 0; i < litterSize; i++) {

            Werewolf cub = new Werewolf("Cub-" + System.currentTimeMillis() + "-" + i);

            // Assign random characteristics
            cub.setAge(0);
            cub.setAgeCategory(AgeCategory.YOUNG);
            cub.setSex(random.nextBoolean() ? AbstractCharacter.Sex.MALE : AbstractCharacter.Sex.FEMALE);
            cub.setStrength(10 + random.nextInt(20));
            cub.setStamina(5 + random.nextInt(15));
            cub.setHealth(100);
            cub.setHunger(50);

            hierarchy.addMember(cub, cubRank);
            cubs.add(cub);
        }

        System.out.println("🐾 " + litterSize + " cubs have been born and assigned rank "
                + cubRank.getSymbol() + "!");

        return cubs;
    }
}
