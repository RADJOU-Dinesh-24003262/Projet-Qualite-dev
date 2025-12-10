package org.example.model.pack;

import java.util.*;
import org.example.model.Character.werewolf.Werewolf;

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

    public Map<Rank, List<Werewolf>> getHierarchy() { return hierarchy; }
    public List<Werewolf> getMembers() { return members; }

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
