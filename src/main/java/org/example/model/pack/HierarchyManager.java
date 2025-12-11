package org.example.model.pack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.example.model.character.werewolf.Werewolf;

public class HierarchyManager {

    private final Map<Rank, List<Werewolf>> hierarchy;
    private final List<Werewolf> members;

    public HierarchyManager() {
        hierarchy = new EnumMap<>(Rank.class);
        members = new ArrayList<>();

        for (Rank rank : Rank.values()) {
            hierarchy.put(rank, new ArrayList<>());
        }
    }

    /**
     * Returns an unmodifiable view of the hierarchy map.
     * Use dedicated methods for modifications.
     */
    public Map<Rank, List<Werewolf>> getHierarchy() {
        // Return a deep unmodifiable view
        Map<Rank, List<Werewolf>> result = new EnumMap<>(Rank.class);
        for (Map.Entry<Rank, List<Werewolf>> entry : hierarchy.entrySet()) {
            result.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
        }
        return Collections.unmodifiableMap(result);
    }

    /**
     * Returns an unmodifiable view of the members list.
     * Use dedicated methods for modifications.
     */
    public List<Werewolf> getMembers() {
        return Collections.unmodifiableList(members);
    }

    /**
     * Returns werewolves of a specific rank.
     */
    public List<Werewolf> getWerewolvesByRank(Rank rank) {
        return Collections.unmodifiableList(hierarchy.get(rank));
    }

    /**
     * Checks if a werewolf is in the hierarchy.
     */
    public boolean containsMember(Werewolf w) {
        return members.contains(w);
    }

    /**
     * Returns the number of members.
     */
    public int getMemberCount() {
        return members.size();
    }

    /**
     * Clears all ranks except ALPHA.
     */
    public void clearNonAlphaRanks() {
        for (Map.Entry<Rank, List<Werewolf>> entry : hierarchy.entrySet()) {
            if (entry.getKey() != Rank.ALPHA) {
                entry.getValue().clear();
            }
        }
    }

    /**
     * Removes all members except the specified alpha pair.
     */
    public void retainOnlyAlphas(Werewolf alphaMale, Werewolf alphaFemale) {
        members.removeIf(w -> w != alphaMale && w != alphaFemale);
    }

    public void addMember(Werewolf w, Rank r) {
        w.setRank(r.getHierarchyLevel());
        hierarchy.get(r).add(w);
        if (!members.contains(w)) members.add(w);
    }

    public void removeMember(Werewolf w) {
        hierarchy.values().forEach(list -> list.remove(w));
        members.remove(w);
    }

    public Rank getRankOf(Werewolf w) {
        return hierarchy.entrySet().stream()
                .filter(e -> e.getValue().contains(w))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
