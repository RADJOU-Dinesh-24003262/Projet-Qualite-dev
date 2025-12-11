package org.example.model.pack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.werewolf.AgeCategory;
import org.example.model.character.werewolf.Werewolf;

/**
 * The {@code RankAssigner} class is responsible for assigning ranks within a werewolf pack hierarchy.
 * It sorts werewolves into different ranks based on their sex, age category, and overall level,
 * while ensuring that the alpha werewolves (both male and female) remain at the top of the hierarchy.
 */
public class RankAssigner {

    private final HierarchyManager hierarchy;

    /**
     * Constructs a {@code RankAssigner} instance with the given {@code HierarchyManager}.
     *
     * @param hierarchy The {@code HierarchyManager} that manages the werewolf hierarchy.
     */
    public RankAssigner(HierarchyManager hierarchy) {
        this.hierarchy = hierarchy;
    }

    /**
     * Creates and populates the hierarchy by assigning ranks to werewolves.
     * Clears any existing ranks (except for the alpha werewolves), then sorts and assigns ranks
     * based on werewolf's sex, age, and level.
     *
     * @param werewolves The list of werewolves to be sorted and ranked.
     * @param alphaMale  The male alpha werewolf.
     * @param alphaFemale The female alpha werewolf.
     */
    public void createHierarchy(List<Werewolf> werewolves, Werewolf alphaMale, Werewolf alphaFemale) {
        // Clears all members except the alphas
        hierarchy.clearNonAlphaRanks();
        hierarchy.retainOnlyAlphas(alphaMale, alphaFemale);

        // Filter adult males and females, and sort them by level
        List<Werewolf> adultMales = filterAdults(werewolves, AbstractCharacter.Sex.MALE, alphaMale);
        List<Werewolf> adultFemales = filterAdults(werewolves, AbstractCharacter.Sex.FEMALE, alphaFemale);
        List<Werewolf> young = werewolves.stream()
                .filter(w -> w.getAgeCategory() == AgeCategory.YOUNG)
                .collect(Collectors.toList());

        // Assign ranks to adult males, adult females, and young werewolves
        assignRanks(adultMales);
        assignRanks(adultFemales);

        // All young werewolves are assigned the Gamma rank
        young.forEach(w -> hierarchy.addMember(w, Rank.GAMMA));
    }

    /**
     * Filters and returns the list of adult werewolves of the specified sex, excluding the alpha.
     * The werewolves are sorted in descending order based on their level.
     *
     * @param list The list of werewolves to filter.
     * @param sex The sex to filter by (male or female).
     * @param alpha The alpha werewolf to exclude from the list.
     * @return A sorted list of adult werewolves of the specified sex, excluding the alpha.
     */
    private List<Werewolf> filterAdults(List<Werewolf> list, AbstractCharacter.Sex sex, Werewolf alpha) {
        List<Werewolf> filteredList = new ArrayList<>(); // This will store the filtered werewolves.

        // Step 1: Filter the werewolves manually by iterating over the list.
        for (Werewolf w : list) {
            // Exclude the alpha werewolf, check sex, and age category.
            if (w != alpha && w.getSex() == sex && w.getAgeCategory() == AgeCategory.ADULT) {
                filteredList.add(w); // Add the werewolf to the filtered list if it matches criteria.
            }
        }

        // Step 2: Selection Sort to sort by calculated level in descending order
        for (int i = 0; i < filteredList.size() - 1; i++) {
            // Find the index of the werewolf with the maximum level in the unsorted part
            int maxIndex = i;

            for (int j = i + 1; j < filteredList.size(); j++) {
                Werewolf current = filteredList.get(maxIndex);
                Werewolf next = filteredList.get(j);

                // If the next werewolf has a higher level, update maxIndex
                if (next.calculateLevel() > current.calculateLevel()) {
                    maxIndex = j; // Update maxIndex to the index of the werewolf with the higher level
                }
            }

            // If the maximum level is not at the current position, swap the werewolves
            if (maxIndex != i) {
                Werewolf temp = filteredList.get(i);
                filteredList.set(i, filteredList.get(maxIndex));
                filteredList.set(maxIndex, temp);
            }
        }

        // Return the sorted list
        return filteredList;
    }

    /**
     * Assigns ranks to a list of werewolves based on their level. The ranks are assigned in the following order:
     * {@code BETA}, {@code GAMMA}, {@code DELTA}, {@code EPSILON}. The werewolves are divided equally
     * among the ranks as much as possible.
     *
     * @param group The list of werewolves to assign ranks to.
     */
    private void assignRanks(List<Werewolf> group) {
        Rank[] ranks = {Rank.BETA, Rank.GAMMA, Rank.DELTA, Rank.EPSILON};
        int perRank = Math.max(1, group.size() / ranks.length);

        int index = 0;
        int count = 0;

        // Assign ranks to each werewolf in the group
        for (Werewolf w : group) {
            hierarchy.addMember(w, ranks[index]);
            count++;

            // Move to the next rank when the current rank is filled
            if (count >= perRank && index < ranks.length - 1) {
                index++;
                count = 0;
            }
        }
    }
}
