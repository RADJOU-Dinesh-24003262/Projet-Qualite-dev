package org.example.Pack;

import java.util.*;
import java.util.stream.Collectors;
import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Werewolf.Werewolf;
import org.example.Model.Character.Werewolf.AgeCategory;

public class RankAssigner {

    private final HierarchyManager hierarchy;

    public RankAssigner(HierarchyManager hierarchy) {
        this.hierarchy = hierarchy;
    }

    public void createHierarchy(List<Werewolf> werewolves, Werewolf alphaMale, Werewolf alphaFemale) {
        // nettoyage sauf alphas
        hierarchy.getHierarchy().forEach((rank, list) -> {
            if (rank != Rank.ALPHA) list.clear();
        });

        hierarchy.getMembers().removeIf(w -> w != alphaMale && w != alphaFemale);

        // tri
        List<Werewolf> adultMales = filterAdults(werewolves, AbstractCharacter.Sex.MALE, alphaMale);
        List<Werewolf> adultFemales = filterAdults(werewolves, AbstractCharacter.Sex.FEMALE, alphaFemale);
        List<Werewolf> young = werewolves.stream()
                .filter(w -> w.getAgeCategory() == AgeCategory.YOUNG)
                .collect(Collectors.toList());

        assignRanks(adultMales);
        assignRanks(adultFemales);

        young.forEach(w -> hierarchy.addMember(w, Rank.GAMMA));
    }

    private List<Werewolf> filterAdults(List<Werewolf> list, AbstractCharacter.Sex sex, Werewolf alpha) {
        return list.stream()
                .filter(w -> w != alpha &&
                             w.getSex() == sex &&
                             w.getAgeCategory() == AgeCategory.ADULT)
                .sorted(Comparator.comparingDouble(Werewolf::calculateLevel).reversed())
                .collect(Collectors.toList());
    }

    private void assignRanks(List<Werewolf> group) {
        Rank[] ranks = {Rank.BETA, Rank.GAMMA, Rank.DELTA, Rank.EPSILON};
        int perRank = Math.max(1, group.size() / ranks.length);

        int index = 0;
        int count = 0;

        for (Werewolf w : group) {
            hierarchy.addMember(w, ranks[index]);
            count++;

            if (count >= perRank && index < ranks.length - 1) {
                index++;
                count = 0;
            }
        }
    }
}
