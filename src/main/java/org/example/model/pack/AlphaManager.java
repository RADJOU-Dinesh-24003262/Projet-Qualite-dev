package org.example.model.pack;

import java.util.Comparator;
import org.example.model.Character.AbstractCharacter;
import org.example.model.Character.werewolf.Werewolf;
import org.example.model.Character.werewolf.AgeCategory;

public class AlphaManager {

    private final HierarchyManager hierarchy;
    private Werewolf alphaMale;
    private Werewolf alphaFemale;

    public AlphaManager(HierarchyManager hierarchy, Werewolf male, Werewolf female) {
        this.hierarchy = hierarchy;
        this.alphaMale = male;
        this.alphaFemale = female;

        hierarchy.addMember(male, Rank.ALPHA);
        hierarchy.addMember(female, Rank.ALPHA);
    }

    public void formNewAlphaCouple(Werewolf newMale) {
        if (newMale == null ||
            newMale.getSex() != AbstractCharacter.Sex.MALE ||
            newMale.getAgeCategory() != AgeCategory.ADULT)
            throw new IllegalArgumentException("Invalid new alpha male");

        // Dethroning
        hierarchy.removeMember(alphaMale);
        hierarchy.removeMember(alphaFemale);

        hierarchy.addMember(alphaMale, Rank.BETA);
        hierarchy.addMember(alphaFemale, Rank.BETA);

        alphaMale = newMale;

        Werewolf newFemale = hierarchy.getMembers().stream()
                .filter(w -> w.getSex() == AbstractCharacter.Sex.FEMALE &&
                             w.getAgeCategory() == AgeCategory.ADULT)
                .max(Comparator.comparingDouble(Werewolf::calculateLevel))
                .orElse(alphaFemale);

        alphaFemale = newFemale;

        hierarchy.addMember(alphaMale, Rank.ALPHA);
        hierarchy.addMember(alphaFemale, Rank.ALPHA);
    }

    public Werewolf getAlphaMale() { return alphaMale; }
    public Werewolf getAlphaFemale() { return alphaFemale; }
}
