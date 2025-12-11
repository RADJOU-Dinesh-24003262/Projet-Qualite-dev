package org.example.model.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.werewolf.AgeCategory;
import org.example.model.character.werewolf.Werewolf;

public class ReproductionManager {

    private static final int MIN_LITTER_SIZE = 1;
    private static final int MAX_LITTER_SIZE = 7;
    private static final Random RANDOM = new Random();
    private static final List<String> CUB_NAMES = Arrays.asList(
            "Lupa", "Fenris", "Skoll", "Hati", "Geri", "Freki", "Amarok", "Raksha", "Akela",
            "Timber", "Shadow", "Ghost", "Nyx", "Luna", "Sol"
    );

    private final HierarchyManager hierarchy;

    public ReproductionManager(HierarchyManager hierarchy) {
        this.hierarchy = hierarchy;
    }

    public List<Werewolf> reproduce() {
        int litterSize = RANDOM.nextInt(MAX_LITTER_SIZE - MIN_LITTER_SIZE + 1) + MIN_LITTER_SIZE;

        System.out.println("🐺 The alpha couple is reproducing! Litter size: " + litterSize);

        List<Werewolf> cubs = new ArrayList<>();

        // If no BETA yet → cubs are BETA, else GAMMA
        Rank cubRank = hierarchy.getWerewolvesByRank(Rank.BETA).isEmpty()
                ? Rank.BETA
                : Rank.GAMMA;

        for (int i = 0; i < litterSize; i++) {
            String name = CUB_NAMES.get(RANDOM.nextInt(CUB_NAMES.size()));
            Werewolf cub = new Werewolf(name + " (Cub)");

            // Assign random characteristics
            cub.setAge(0);
            cub.setAgeCategory(AgeCategory.YOUNG);
            cub.setSex(RANDOM.nextBoolean() ? AbstractCharacter.Sex.MALE : AbstractCharacter.Sex.FEMALE);
            cub.setStrength(10 + RANDOM.nextInt(20));
            cub.setStamina(5 + RANDOM.nextInt(15));
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
