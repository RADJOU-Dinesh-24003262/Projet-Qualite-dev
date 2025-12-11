package org.example.model.pack;

import java.util.Comparator;
import org.example.model.character.AbstractCharacter;
import org.example.model.character.werewolf.Werewolf;
import org.example.model.character.werewolf.AgeCategory;

/**
 * Manages the alpha couple (male and female) of a werewolf pack.
 * This class is responsible for establishing the initial alpha pair and handling the
 * process of forming a new alpha couple, which involves dethroning the previous leaders.
 */
public class AlphaManager {

    /** The hierarchy manager for the pack, used to update ranks. */
    private final HierarchyManager hierarchy;
    /** The current alpha male of the pack. */
    private Werewolf alphaMale;
    /** The current alpha female of the pack. */
    private Werewolf alphaFemale;

    /**
     * Constructs an AlphaManager and establishes the initial alpha couple for the pack.
     * @param hierarchy The hierarchy manager for the pack.
     * @param male The initial alpha male.
     * @param female The initial alpha female.
     */
    public AlphaManager(HierarchyManager hierarchy, Werewolf male, Werewolf female) {
        this.hierarchy = hierarchy;
        this.alphaMale = male;
        this.alphaFemale = female;

        hierarchy.addMember(male, Rank.ALPHA);
        hierarchy.addMember(female, Rank.ALPHA);
    }

    /**
     * Dethrones the current alpha couple and forms a new one.
     * The previous alpha couple is demoted to Beta rank. The new alpha male is specified,
     * and the new alpha female is chosen from the pack based on her level.
     *
     * @param newMale The werewolf who will become the new alpha male.
     * @throws IllegalArgumentException if the new male is null, not male, or not an adult.
     */
    public void formNewAlphaCouple(Werewolf newMale) {
        if (newMale == null ||
            newMale.getSex() != AbstractCharacter.Sex.MALE ||
            newMale.getAgeCategory() != AgeCategory.ADULT)
            throw new IllegalArgumentException("Invalid new alpha male");

        // Dethrone the old alpha couple by demoting them to Beta
        hierarchy.removeMember(alphaMale);
        hierarchy.removeMember(alphaFemale);
        hierarchy.addMember(alphaMale, Rank.BETA);
        hierarchy.addMember(alphaFemale, Rank.BETA);

        // Set the new alpha male
        alphaMale = newMale;

        // Find the strongest adult female to be the new alpha female
        Werewolf newFemale = hierarchy.getMembers().stream()
                .filter(w -> w.getSex() == AbstractCharacter.Sex.FEMALE &&
                             w.getAgeCategory() == AgeCategory.ADULT)
                .max(Comparator.comparingDouble(Werewolf::calculateLevel))
                .orElse(alphaFemale); // Fallback to the old alpha female if no other is found

        alphaFemale = newFemale;

        // Promote the new couple to Alpha rank
        hierarchy.addMember(alphaMale, Rank.ALPHA);
        hierarchy.addMember(alphaFemale, Rank.ALPHA);
    }

    /**
     * Gets the current alpha male of the pack.
     * @return The alpha male werewolf.
     */
    public Werewolf getAlphaMale() { return alphaMale; }

    /**
     * Gets the current alpha female of the pack.
     * @return The alpha female werewolf.
     */
    public Werewolf getAlphaFemale() { return alphaFemale; }
}
