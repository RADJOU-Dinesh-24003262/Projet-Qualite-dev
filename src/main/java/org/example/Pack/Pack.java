package org.example.Pack;

import java.util.*;
import java.util.stream.Collectors;
import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Werewolf;

/**
 * Represents a pack of werewolves with a strict dominance hierarchy.
 * 
 * <p>A pack is organized around an alpha couple (male α and female α) and follows
 * a hierarchical structure using Greek letters: α, β, γ, δ, ε, ..., ω.</p>
 * 
 * <p>Key characteristics:</p>
 * <ul>
 *   <li>Only one pack can exist in a given place</li>
 *   <li>Werewolves never kill each other</li>
 *   <li>Only the alpha couple can reproduce during mating season</li>
 *   <li>ω-ranked werewolves are the scapegoats of the pack</li>
 * </ul>
 */
public class Pack {

    /**
     * Enumeration of dominance ranks in the pack hierarchy.
     * Ranks follow lexicographic order: α > β > γ > δ > ε > ζ > η > θ > ω
     */
    public enum Rank {
        ALPHA('α', 8),
        BETA('β', 7),
        GAMMA('γ', 6),
        DELTA('δ', 5),
        EPSILON('ε', 4),
        ZETA('ζ', 3),
        ETA('η', 2),
        THETA('θ', 1),
        OMEGA('ω', 0);

        private final char symbol;
        private final int hierarchyLevel;

        Rank(char symbol, int hierarchyLevel) {
            this.symbol = symbol;
            this.hierarchyLevel = hierarchyLevel;
        }

        public char getSymbol() {
            return symbol;
        }

        public int getHierarchyLevel() {
            return hierarchyLevel;
        }

        /**
         * Gets the next lower rank.
         * @return the next lower rank, or OMEGA if already at the bottom
         */
        public Rank getLowerRank() {
            int currentIndex = this.ordinal();
            if (currentIndex < Rank.values().length - 1) {
                return Rank.values()[currentIndex + 1];
            }
            return OMEGA;
        }
    }

    private static final int MIN_LITTER_SIZE = 1;
    private static final int MAX_LITTER_SIZE = 7;
    private static final int DOMINATION_THRESHOLD = -5;
    private static final double OMEGA_STRENGTH_RATIO = 0.7;

    private String name;
    private Werewolf alphaWerewolfMale;
    private Werewolf alphaWerewolfFemale;
    private Map<Rank, List<Werewolf>> hierarchy;
    private List<Werewolf> members;

    /**
     * Constructor for creating a pack with an alpha couple.
     *
     * @param name the name of the pack
     * @param alphaWerewolfMale the male alpha werewolf
     * @param alphaWerewolfFemale the female alpha werewolf
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Pack(String name, Werewolf alphaWerewolfMale, Werewolf alphaWerewolfFemale) {
        validatePackCreation(name, alphaWerewolfMale, alphaWerewolfFemale);
        
        this.name = name;
        this.alphaWerewolfMale = alphaWerewolfMale;
        this.alphaWerewolfFemale = alphaWerewolfFemale;
        this.hierarchy = new EnumMap<>(Rank.class);
        this.members = new ArrayList<>();

        initializeHierarchy();
        assignAlphaCouple();
    }

    /**
     * Validates pack creation parameters.
     */
    private void validatePackCreation(String name, Werewolf alphaMale, Werewolf alphaFemale) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Pack name cannot be null or empty");
        }
        if (alphaMale == null || alphaFemale == null) {
            throw new IllegalArgumentException("Alpha couple cannot be null");
        }
        if (alphaMale.getSex() != AbstractCharacter.Sex.MALE) {
            throw new IllegalArgumentException("Alpha male must be MALE");
        }
        if (alphaFemale.getSex() != AbstractCharacter.Sex.FEMALE) {
            throw new IllegalArgumentException("Alpha female must be FEMALE");
        }
        if (alphaMale.getAgeCategory() != Werewolf.AgeCategory.ADULT) {
            throw new IllegalArgumentException("Alpha male must be ADULT");
        }
        if (alphaFemale.getAgeCategory() != Werewolf.AgeCategory.ADULT) {
            throw new IllegalArgumentException("Alpha female must be ADULT");
        }
    }

    /**
     * Initializes the hierarchy map with empty lists for each rank.
     */
    private void initializeHierarchy() {
        for (Rank rank : Rank.values()) {
            hierarchy.put(rank, new ArrayList<>());
        }
    }

    /**
     * Assigns the alpha couple to the pack.
     */
    private void assignAlphaCouple() {
        alphaWerewolfMale.setPack(this);
        alphaWerewolfMale.setRank(Rank.ALPHA.getHierarchyLevel());
        hierarchy.get(Rank.ALPHA).add(alphaWerewolfMale);
        members.add(alphaWerewolfMale);

        alphaWerewolfFemale.setPack(this);
        alphaWerewolfFemale.setRank(Rank.ALPHA.getHierarchyLevel());
        hierarchy.get(Rank.ALPHA).add(alphaWerewolfFemale);
        members.add(alphaWerewolfFemale);
    }

    /**
     * Displays the characteristics of the pack.
     */
    public void displayCharacteristics() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║        PACK CHARACTERISTICS                ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("Pack Name: " + name);
        System.out.println("Total Members: " + members.size());
        System.out.println("Alpha Male: " + alphaWerewolfMale.getName() + " (Strength: " + alphaWerewolfMale.getStrength() + ")");
        System.out.println("Alpha Female: " + alphaWerewolfFemale.getName() + " (Strength: " + alphaWerewolfFemale.getStrength() + ")");
        System.out.println("\n--- Hierarchy Distribution ---");
        
        for (Rank rank : Rank.values()) {
            List<Werewolf> membersAtRank = hierarchy.get(rank);
            if (!membersAtRank.isEmpty()) {
                System.out.println(rank.getSymbol() + " (" + rank.name() + "): " + membersAtRank.size() + " members");
            }
        }
        System.out.println("══════════════════════════════════════════════\n");
    }

    /**
     * Displays the characteristics of all werewolves in the pack.
     */
    public void displayMembersCharacteristics() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║        PACK MEMBERS                        ║");
        System.out.println("╚════════════════════════════════════════════╝");
        
        for (Rank rank : Rank.values()) {
            List<Werewolf> membersAtRank = hierarchy.get(rank);
            if (!membersAtRank.isEmpty()) {
                System.out.println("\n--- " + rank.getSymbol() + " Rank (" + rank.name() + ") ---");
                for (Werewolf werewolf : membersAtRank) {
                    System.out.println(werewolf.getName() + " - " + werewolf.getSex() + 
                                     " - Strength: " + werewolf.getStrength() +
                                     " - Level: " + String.format("%.2f", werewolf.calculateLevel()) +
                                     " - Domination: " + werewolf.getDominationFactor());
                }
            }
        }
        System.out.println("══════════════════════════════════════════════\n");
    }

    /**
     * Creates a new hierarchy from a set of werewolves.
     * Automatically assigns ranks based on strength and level.
     *
     * @param werewolves the list of werewolves to organize
     * @throws IllegalArgumentException if werewolves list is invalid
     */
    public void createHierarchy(List<Werewolf> werewolves) {
        if (werewolves == null || werewolves.isEmpty()) {
            throw new IllegalArgumentException("Werewolf list cannot be null or empty");
        }

        // Clear current hierarchy (except alpha couple)
        for (Rank rank : Rank.values()) {
            if (rank != Rank.ALPHA) {
                hierarchy.get(rank).clear();
            }
        }
        members.removeIf(w -> w != alphaWerewolfMale && w != alphaWerewolfFemale);

        // Separate by sex and age
        List<Werewolf> adultMales = werewolves.stream()
            .filter(w -> w.getSex() == AbstractCharacter.Sex.MALE && 
                        w.getAgeCategory() == Werewolf.AgeCategory.ADULT &&
                        w != alphaWerewolfMale)
            .sorted((w1, w2) -> Double.compare(w2.calculateLevel(), w1.calculateLevel()))
            .collect(Collectors.toList());

        List<Werewolf> adultFemales = werewolves.stream()
            .filter(w -> w.getSex() == AbstractCharacter.Sex.FEMALE && 
                        w.getAgeCategory() == Werewolf.AgeCategory.ADULT &&
                        w != alphaWerewolfFemale)
            .sorted((w1, w2) -> Double.compare(w2.calculateLevel(), w1.calculateLevel()))
            .collect(Collectors.toList());

        List<Werewolf> youngWerewolves = werewolves.stream()
            .filter(w -> w.getAgeCategory() == Werewolf.AgeCategory.YOUNG)
            .collect(Collectors.toList());

        // Assign ranks to adults
        assignRanksToGroup(adultMales);
        assignRanksToGroup(adultFemales);

        // Assign gamma rank to young werewolves
        for (Werewolf young : youngWerewolves) {
            addMemberWithRank(young, Rank.GAMMA);
        }

        // Identify omega werewolves
        identifyOmegaWerewolves();

        System.out.println("New hierarchy created for pack: " + name);
    }

    /**
     * Assigns appropriate ranks to a group of werewolves.
     */
    private void assignRanksToGroup(List<Werewolf> group) {
        Rank[] ranksToAssign = {Rank.BETA, Rank.GAMMA, Rank.DELTA, Rank.EPSILON};
        int rankIndex = 0;
        int membersPerRank = Math.max(1, group.size() / ranksToAssign.length);
        int assigned = 0;

        for (Werewolf werewolf : group) {
            if (assigned >= membersPerRank && rankIndex < ranksToAssign.length - 1) {
                rankIndex++;
                assigned = 0;
            }
            addMemberWithRank(werewolf, ranksToAssign[rankIndex]);
            assigned++;
        }
    }

    /**
     * Adds a member to the pack with a specific rank.
     */
    private void addMemberWithRank(Werewolf werewolf, Rank rank) {
        werewolf.setPack(this);
        werewolf.setRank(rank.getHierarchyLevel());
        hierarchy.get(rank).add(werewolf);
        if (!members.contains(werewolf)) {
            members.add(werewolf);
        }
    }

    /**
     * Forms a new alpha couple with the specified male alpha.
     * The previous alpha couple is dethroned if applicable.
     *
     * @param newAlphaMale the new male alpha
     * @throws IllegalArgumentException if the new alpha is invalid
     */
    public void formNewAlphaCouple(Werewolf newAlphaMale) {
        if (newAlphaMale == null) {
            throw new IllegalArgumentException("New alpha male cannot be null");
        }
        if (newAlphaMale.getSex() != AbstractCharacter.Sex.MALE) {
            throw new IllegalArgumentException("New alpha must be MALE");
        }
        if (newAlphaMale.getAgeCategory() != Werewolf.AgeCategory.ADULT) {
            throw new IllegalArgumentException("New alpha must be ADULT");
        }
        if (!members.contains(newAlphaMale)) {
            throw new IllegalArgumentException("New alpha must be a member of the pack");
        }

        // Dethrone old alpha couple
        Werewolf oldAlphaMale = this.alphaWerewolfMale;
        Werewolf oldAlphaFemale = this.alphaWerewolfFemale;

        // Remove old alphas from alpha rank
        hierarchy.get(Rank.ALPHA).remove(oldAlphaMale);
        hierarchy.get(Rank.ALPHA).remove(oldAlphaFemale);

        // Demote old alpha male to the same rank as old alpha female will get
        addMemberWithRank(oldAlphaMale, Rank.BETA);

        // Find new alpha female (highest ranked adult female)
        Werewolf newAlphaFemale = findStrongestAdultFemale();
        
        if (newAlphaFemale == oldAlphaFemale) {
            // Old alpha female keeps her position
            hierarchy.get(Rank.BETA).remove(oldAlphaFemale);
        } else {
            // Demote old alpha female
            addMemberWithRank(oldAlphaFemale, Rank.BETA);
            
            // Remove new alpha female from her old rank
            for (Rank rank : Rank.values()) {
                hierarchy.get(rank).remove(newAlphaFemale);
            }
        }

        // Remove new alpha male from his old rank
        for (Rank rank : Rank.values()) {
            hierarchy.get(rank).remove(newAlphaMale);
        }

        // Assign new alpha couple
        this.alphaWerewolfMale = newAlphaMale;
        this.alphaWerewolfFemale = newAlphaFemale;
        
        addMemberWithRank(newAlphaMale, Rank.ALPHA);
        addMemberWithRank(newAlphaFemale, Rank.ALPHA);

        System.out.println("New alpha couple formed: " + newAlphaMale.getName() + 
                         " and " + newAlphaFemale.getName());
    }

    /**
     * Finds the strongest adult female in the pack.
     */
    private Werewolf findStrongestAdultFemale() {
        return members.stream()
            .filter(w -> w.getSex() == AbstractCharacter.Sex.FEMALE && 
                        w.getAgeCategory() == Werewolf.AgeCategory.ADULT)
            .max(Comparator.comparingDouble(Werewolf::calculateLevel))
            .orElse(alphaWerewolfFemale);
    }

    /**
     * Initiates reproduction during mating season.
     * Only the alpha couple can reproduce.
     *
     * @return the list of newborn werewolf cubs
     */
    public List<Werewolf> reproduce() {
        Random random = new Random();
        int litterSize = random.nextInt(MAX_LITTER_SIZE - MIN_LITTER_SIZE + 1) + MIN_LITTER_SIZE;
        
        List<Werewolf> cubs = new ArrayList<>();
        Rank cubRank = hierarchy.get(Rank.BETA).isEmpty() ? Rank.BETA : Rank.GAMMA;

        System.out.println("The alpha couple is reproducing! Litter size: " + litterSize);

        for (int i = 0; i < litterSize; i++) {
            AbstractCharacter.Sex cubSex = random.nextBoolean() ? 
                AbstractCharacter.Sex.MALE : AbstractCharacter.Sex.FEMALE;
            
            Werewolf cub = new Werewolf("Cub-" + System.currentTimeMillis() + "-" + i);
            cub.setSex(cubSex);
            cub.setAgeCategory(Werewolf.AgeCategory.YOUNG);
            cub.setAge(0);
            cub.setStrength(random.nextInt(20) + 10);
            cub.setStamina(random.nextInt(15) + 5);
            cub.setHealth(100);
            cub.setHunger(50);
            
            addMemberWithRank(cub, cubRank);
            cubs.add(cub);
        }

        System.out.println(litterSize + " cubs have been born and assigned " + 
                         cubRank.getSymbol() + " rank!");
        return cubs;
    }

    /**
     * Naturally decreases dominance ranks based on domination factors.
     * Werewolves with low domination factors may lose rank.
     */
    public void naturalRankDecay() {
        System.out.println("Processing natural rank decay for pack: " + name);
        
        for (Rank rank : Rank.values()) {
            if (rank == Rank.ALPHA || rank == Rank.OMEGA) {
                continue; // Alpha and Omega don't decay naturally
            }

            List<Werewolf> rankMembers = new ArrayList<>(hierarchy.get(rank));
            
            for (Werewolf werewolf : rankMembers) {
                if (werewolf.getDominationFactor() < DOMINATION_THRESHOLD) {
                    // Check if this werewolf is the last of their sex at this rank
                    long sameRankSameSex = hierarchy.get(rank).stream()
                        .filter(w -> w.getSex() == werewolf.getSex())
                        .count();

                    if (sameRankSameSex > 1) {
                        // Demote the werewolf
                        Rank lowerRank = rank.getLowerRank();
                        hierarchy.get(rank).remove(werewolf);
                        addMemberWithRank(werewolf, lowerRank);
                        
                        System.out.println(werewolf.getName() + " has been demoted from " + 
                                         rank.getSymbol() + " to " + lowerRank.getSymbol());
                    }
                }
            }
        }
    }

    /**
     * Identifies and designates omega werewolves.
     * Adult werewolves with insufficient strength become omega.
     */
    public void identifyOmegaWerewolves() {
        double averageStrength = members.stream()
            .filter(w -> w.getAgeCategory() == Werewolf.AgeCategory.ADULT)
            .mapToInt(Werewolf::getStrength)
            .average()
            .orElse(0.0);

        double omegaThreshold = averageStrength * OMEGA_STRENGTH_RATIO;

        System.out.println("Identifying omega werewolves (threshold: " + 
                         String.format("%.2f", omegaThreshold) + ")");

        for (Rank rank : Rank.values()) {
            if (rank == Rank.ALPHA || rank == Rank.OMEGA) {
                continue;
            }

            List<Werewolf> rankMembers = new ArrayList<>(hierarchy.get(rank));
            
            for (Werewolf werewolf : rankMembers) {
                if (werewolf.getAgeCategory() == Werewolf.AgeCategory.ADULT && 
                    werewolf.getStrength() < omegaThreshold) {
                    
                    hierarchy.get(rank).remove(werewolf);
                    addMemberWithRank(werewolf, Rank.OMEGA);
                    
                    System.out.println(werewolf.getName() + " has been designated as " + 
                                     Rank.OMEGA.getSymbol() + " (omega)");
                }
            }
        }
    }

    /**
     * Adds a werewolf to the pack.
     * Assigns an appropriate rank based on their characteristics.
     *
     * @param werewolf the werewolf to add
     * @throws IllegalArgumentException if werewolf is null or already in a pack
     */
    public void addMember(Werewolf werewolf) {
        if (werewolf == null) {
            throw new IllegalArgumentException("Werewolf cannot be null");
        }
        if (werewolf.getPack() != null) {
            throw new IllegalArgumentException("Werewolf already belongs to a pack");
        }

        // Determine appropriate rank
        Rank assignedRank;
        if (werewolf.getAgeCategory() == Werewolf.AgeCategory.YOUNG) {
            assignedRank = Rank.GAMMA;
        } else {
            assignedRank = Rank.DELTA; // Default for adult newcomers
        }

        addMemberWithRank(werewolf, assignedRank);
        System.out.println(werewolf.getName() + " has joined the pack as " + 
                         assignedRank.getSymbol());
    }

    /**
     * Removes a werewolf from the pack.
     *
     * @param werewolf the werewolf to remove
     * @throws IllegalArgumentException if trying to remove an alpha
     */
    public void removeMember(Werewolf werewolf) {
        if (werewolf == null) {
            return;
        }
        if (werewolf == alphaWerewolfMale || werewolf == alphaWerewolfFemale) {
            throw new IllegalArgumentException("Cannot remove alpha werewolves directly");
        }

        for (Rank rank : Rank.values()) {
            hierarchy.get(rank).remove(werewolf);
        }
        members.remove(werewolf);
        werewolf.setPack(null);
        werewolf.setRank(0);

        System.out.println(werewolf.getName() + " has left the pack");
    }

    /**
     * Attempts domination of one werewolf over another.
     *
     * @param aggressor the werewolf attempting to dominate
     * @param target the werewolf being dominated
     * @return true if domination was successful
     */
    public boolean attemptDomination(Werewolf aggressor, Werewolf target) {
        if (!validateDominationAttempt(aggressor, target)) {
            return false;
        }

        // Check if target is female alpha (protected)
        if (target == alphaWerewolfFemale) {
            System.out.println(aggressor.getName() + " cannot dominate the alpha female!");
            return false;
        }

        // Check impetuosity and strength
        if (target.getStrength() > aggressor.getStrength() && 
            Math.random() > aggressor.getImpetuosityFactor()) {
            System.out.println(aggressor.getName() + " doesn't feel confident enough to dominate " + 
                             target.getName());
            return false;
        }

        // Determine success
        boolean success = aggressor.calculateLevel() > target.calculateLevel() || 
                         isOmega(target);

        if (success) {
            handleSuccessfulDomination(aggressor, target);
            return true;
        } else {
            handleFailedDomination(aggressor, target);
            return false;
        }
    }

    /**
     * Validates if a domination attempt is allowed.
     */
    private boolean validateDominationAttempt(Werewolf aggressor, Werewolf target) {
        if (aggressor == null || target == null) {
            return false;
        }
        if (!members.contains(aggressor) || !members.contains(target)) {
            System.out.println("Both werewolves must be pack members");
            return false;
        }
        if (aggressor == target) {
            return false;
        }
        return true;
    }

    /**
     * Handles a successful domination.
     */
    private void handleSuccessfulDomination(Werewolf aggressor, Werewolf target) {
        System.out.println(aggressor.getName() + " successfully dominates " + target.getName() + "!");
        
        aggressor.setDominationFactor(aggressor.getDominationFactor() + 1);
        target.setDominationFactor(target.getDominationFactor() - 1);

        // Exchange ranks
        Rank aggressorRank = getRankOfWerewolf(aggressor);
        Rank targetRank = getRankOfWerewolf(target);

        if (aggressorRank != null && targetRank != null && aggressorRank != targetRank) {
            hierarchy.get(aggressorRank).remove(aggressor);
            hierarchy.get(targetRank).remove(target);

            addMemberWithRank(aggressor, targetRank);
            addMemberWithRank(target, aggressorRank);

            System.out.println("Ranks exchanged: " + aggressor.getName() + " is now " + 
                             targetRank.getSymbol() + " and " + target.getName() + 
                             " is now " + aggressorRank.getSymbol());
        }
    }

    /**
     * Handles a failed domination.
     */
    private void handleFailedDomination(Werewolf aggressor, Werewolf target) {
        System.out.println(aggressor.getName() + " failed to dominate " + target.getName() + "!");
        System.out.println(target.getName() + " becomes aggressive!");
        
        target.setBelligerence(target.getBelligerence() + 15);
        aggressor.setDominationFactor(aggressor.getDominationFactor() - 1);
    }

    /**
     * Gets the rank of a specific werewolf.
     */
    private Rank getRankOfWerewolf(Werewolf werewolf) {
        for (Map.Entry<Rank, List<Werewolf>> entry : hierarchy.entrySet()) {
            if (entry.getValue().contains(werewolf)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Checks if a werewolf is omega rank.
     */
    private boolean isOmega(Werewolf werewolf) {
        return hierarchy.get(Rank.OMEGA).contains(werewolf);
    }

    /**
     * Notifies all pack members of a howl.
     *
     * @param sender the werewolf who howled
     * @param howlType the type of howl
     */
    public void notifyHowl(Werewolf sender, Werewolf.HowlType howlType) {
        for (Werewolf member : members) {
            if (member != sender) {
                member.hearHowl(sender, howlType);
            }
        }
    }

    // Getters

    public String getName() {
        return name;
    }

    public Werewolf getAlphaMale() {
        return alphaWerewolfMale;
    }

    public Werewolf getAlphaFemale() {
        return alphaWerewolfFemale;
    }

    public List<Werewolf> getMembers() {
        return new ArrayList<>(members);
    }

    public int getMemberCount() {
        return members.size();
    }

    public Map<Rank, List<Werewolf>> getHierarchy() {
        return new EnumMap<>(hierarchy);
    }

    @Override
    public String toString() {
        return "Pack{" +
                "name='" + name + '\'' +
                ", members=" + members.size() +
                ", alphaMale=" + alphaWerewolfMale.getName() +
                ", alphaFemale=" + alphaWerewolfFemale.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pack)) {
            return false;
        }
        Pack pack = (Pack) o;
        return Objects.equals(name, pack.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}