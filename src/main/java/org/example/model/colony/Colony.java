package org.example.model.colony;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.werewolf.AgeCategory;
import org.example.model.character.werewolf.HowlType;
import org.example.model.character.werewolf.Werewolf;
import org.example.model.pack.Pack;
import org.example.model.pack.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a colony of werewolf packs.
 * Manages multiple packs and simulates temporal evolution including:
 * - Pack creation
 * - Mating seasons
 * - Hierarchy evolution
 * - Aging
 * - Random howling
 * - Human transformations
 */
public class Colony {

    private final String name;
    private final List<Pack> packs;
    private final Random random;
    private int seasonCounter;

    private static final int MATING_SEASON_INTERVAL = 12;
    private static final int PACK_CREATION_THRESHOLD = 15;
    private static final double AGING_PROBABILITY = 0.1;
    private static final double HOWL_PROBABILITY = 0.2;
    private static final double TRANSFORM_PROBABILITY = 0.05;

    /**
     * Creates a new colony of werewolves.
     *
     * @param name The name of the colony
     */
    public Colony(String name) {
        this.name = name;
        this.packs = new ArrayList<>();
        this.random = new Random();
        this.seasonCounter = 0;
    }

    /**
     * Adds a pack to the colony.
     *
     * @param pack The pack to add
     */
    public void addPack(Pack pack) {
        if (pack != null && !packs.contains(pack)) {
            packs.add(pack);
            System.out.println("✅ Pack '" + pack.getName() + "' added to colony '" + name + "'");
        }
    }

    /**
     * Removes a pack from the colony.
     *
     * @param pack The pack to remove
     */
    public void removePack(Pack pack) {
        if (packs.remove(pack)) {
            System.out.println("❌ Pack '" + pack.getName() + "' removed from colony");
        }
    }

    /**
     * Displays all werewolves across all packs in the colony.
     */
    public void displayAllWerewolves() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║        COLONY: " + name + " - ALL WEREWOLVES        ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println("Number of packs: " + packs.size());
        System.out.println("Total werewolves: " + getTotalWerewolfCount());
        System.out.println();

        for (Pack pack : packs) {
            System.out.println("--- Pack: " + pack.getName() + " ---");
            pack.displayMembers();
        }
    }

    /**
     * Displays colony statistics.
     */
    public void displayColonyStats() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║         COLONY STATISTICS: " + name + "              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println("Total packs: " + packs.size());
        System.out.println("Total werewolves: " + getTotalWerewolfCount());
        System.out.println("Season counter: " + seasonCounter);
        System.out.println();

        for (Pack pack : packs) {
            pack.displayPack();
        }
    }

    /**
     * Main simulation loop - advances time by one interval.
     * This is the temporal management method that should be called regularly.
     */
    public void advanceTime() {
        seasonCounter++;
        System.out.println("\n" + "=".repeat(70));
        System.out.println("⏰ COLONY TIME ADVANCE - Season " + seasonCounter);
        System.out.println("=".repeat(70));

        // 1. Check if new pack should be created
        checkAndCreateNewPack();

        // 2. Check for mating season
        if (seasonCounter % MATING_SEASON_INTERVAL == 0) {
            triggerMatingSession();
        }

        // 3. Evolve pack hierarchies naturally
        evolveHierarchies();

        // 4. Age some werewolves
        ageWerewolves();

        // 5. Generate random howls
        generateRandomHowls();

        // 6. Transform some werewolves to humans
        transformWerewolves();

        // 7. Clean up empty packs
        cleanUpEmptyPacks();

        System.out.println("=".repeat(70) + "\n");
    }

    /**
     * Checks if a new pack should be created based on population.
     */
    private void checkAndCreateNewPack() {
        System.out.println("\n🔍 Checking for pack creation...");

        for (Pack pack : new ArrayList<>(packs)) {
            if (pack.getMembers().size() > PACK_CREATION_THRESHOLD) {
                System.out.println("📦 Pack '" + pack.getName() 
                    + "' has grown too large (" + pack.getMembers().size() + " members)");
                
                // Find suitable alpha candidates
                List<Werewolf> candidates = findAlphaCandidates(pack);
                
                if (candidates.size() >= 2) {
                    createNewPackFromSplit(pack, candidates);
                } else {
                    System.out.println("  → Not enough alpha candidates for split");
                }
            }
        }
    }

    /**
     * Finds suitable candidates for alpha positions.
     */
    private List<Werewolf> findAlphaCandidates(Pack pack) {
        List<Werewolf> candidates = new ArrayList<>();
        
        for (Werewolf w : pack.getMembers()) {
            if (w.getAgeCategory() == AgeCategory.ADULT 
                && w.calculateLevel() > 30
                && w != pack.getAlphaMale() 
                && w != pack.getAlphaFemale()) {
                candidates.add(w);
            }
        }
        
        return candidates;
    }

    /**
     * Creates a new pack by splitting an existing one.
     */
    private void createNewPackFromSplit(Pack originalPack, List<Werewolf> candidates) {
        // Select new alphas
        Werewolf newAlphaMale = null;
        Werewolf newAlphaFemale = null;

        for (Werewolf w : candidates) {
            if (newAlphaMale == null && w.getSex() == AbstractCharacter.Sex.MALE) {
                newAlphaMale = w;
            } else if (newAlphaFemale == null && w.getSex() == AbstractCharacter.Sex.FEMALE) {
                newAlphaFemale = w;
            }

            if (newAlphaMale != null && newAlphaFemale != null) {
                break;
            }
        }

        if (newAlphaMale == null || newAlphaFemale == null) {
            return;
        }

        // Create new pack
        String newPackName = originalPack.getName() + "-Split-" + seasonCounter;
        Pack newPack = new Pack(newPackName, newAlphaMale, newAlphaFemale);

        // Move werewolves to new pack (approximately half)
        List<Werewolf> toTransfer = new ArrayList<>();
        List<Werewolf> members = new ArrayList<>(originalPack.getMembers());
        
        int transferCount = members.size() / 2;
        for (int i = 0; i < transferCount && i < members.size(); i++) {
            Werewolf w = members.get(i);
            if (w != originalPack.getAlphaMale() && w != originalPack.getAlphaFemale()) {
                toTransfer.add(w);
            }
        }

        // Transfer werewolves
        for (Werewolf w : toTransfer) {
            originalPack.removeWerewolf(w);
            newPack.addWerewolf(w, Rank.GAMMA);
        }

        // Reorganize both packs
        newPack.createHierarchy(newPack.getMembers());
        originalPack.createHierarchy(originalPack.getMembers());

        addPack(newPack);
        System.out.println("✅ New pack '" + newPackName + "' created with " 
            + newPack.getMembers().size() + " members!");
    }

    /**
     * Triggers mating season for all packs.
     */
    private void triggerMatingSession() {
        System.out.println("\n💕 MATING SEASON HAS ARRIVED!");
        
        for (Pack pack : packs) {
            System.out.println("\n--- Pack: " + pack.getName() + " ---");
            List<Werewolf> cubs = pack.reproduce();
            System.out.println("  → " + cubs.size() + " cubs born!");
        }
    }

    /**
     * Evolves pack hierarchies through domination attempts.
     */
    private void evolveHierarchies() {
        System.out.println("\n⚔️ Evolving pack hierarchies...");
        
        for (Pack pack : packs) {
            List<Werewolf> members = new ArrayList<>(pack.getMembers());
            
            // Random domination attempts
            for (int i = 0; i < 3; i++) {
                if (members.size() < 2) {
                    break;
                }
                
                Werewolf aggressor = members.get(random.nextInt(members.size()));
                Werewolf target = members.get(random.nextInt(members.size()));
                
                if (aggressor != target && random.nextDouble() < 0.3) {
                    pack.attemptDomination(aggressor, target);
                }
            }
            
            // Identify omega werewolves
            pack.identifyOmegas();
        }
    }

    /**
     * Ages some werewolves randomly.
     */
    private void ageWerewolves() {
        System.out.println("\n🎂 Aging process...");
        int agedCount = 0;
        
        for (Pack pack : packs) {
            for (Werewolf w : pack.getMembers()) {
                if (random.nextDouble() < AGING_PROBABILITY) {
                    int currentAge = w.getAge();
                    w.setAge(currentAge + 1);
                    
                    // Update age category
                    if (currentAge < 2 && w.getAge() >= 2) {
                        w.setAgeCategory(AgeCategory.YOUNG);
                    } else if (currentAge < 10 && w.getAge() >= 10) {
                        w.setAgeCategory(AgeCategory.ADULT);
                    } else if (currentAge < 20 && w.getAge() >= 20) {
                        w.setAgeCategory(AgeCategory.OLD);
                    }
                    
                    agedCount++;
                }
            }
        }
        
        System.out.println("  → " + agedCount + " werewolves aged");
    }

    /**
     * Generates random howls between werewolves.
     */
    private void generateRandomHowls() {
        System.out.println("\n🐺 Random howling...");
        int howlCount = 0;
        
        for (Pack pack : packs) {
            List<Werewolf> members = pack.getMembers();
            
            for (Werewolf w : members) {
                if (random.nextDouble() < HOWL_PROBABILITY) {
                    HowlType[] types = HowlType.values();
                    HowlType randomType = types[random.nextInt(types.length)];
                    w.howl(randomType);
                    howlCount++;
                }
            }
        }
        
        System.out.println("  → " + howlCount + " howls generated");
    }

    /**
     * Transforms some werewolves into humans.
     */
    private void transformWerewolves() {
        System.out.println("\n🌙 Transformation phase...");
        int transformCount = 0;
        
        List<Werewolf> toRemove = new ArrayList<>();
        
        for (Pack pack : packs) {
            for (Werewolf w : new ArrayList<>(pack.getMembers())) {
                if (!w.isHuman() && random.nextDouble() < TRANSFORM_PROBABILITY) {
                    w.transformToHuman();
                    transformCount++;
                    
                    // Check if werewolf left pack
                    if (w.getPack() == null) {
                        toRemove.add(w);
                    }
                }
            }
        }
        
        System.out.println("  → " + transformCount + " werewolves transformed");
        System.out.println("  → " + toRemove.size() + " werewolves left their packs");
    }

    /**
     * Removes empty packs from the colony.
     */
    private void cleanUpEmptyPacks() {
        List<Pack> toRemove = new ArrayList<>();
        
        for (Pack pack : packs) {
            if (pack.getMembers().isEmpty()) {
                toRemove.add(pack);
            }
        }
        
        for (Pack pack : toRemove) {
            removePack(pack);
        }
    }

    /**
     * Gets the total number of werewolves in the colony.
     *
     * @return total count
     */
    public int getTotalWerewolfCount() {
        int count = 0;
        for (Pack pack : packs) {
            count += pack.getMembers().size();
        }
        return count;
    }

    /**
     * Gets all packs in the colony.
     *
     * @return list of packs
     */
    public List<Pack> getPacks() {
        return new ArrayList<>(packs);
    }

    /**
     * Gets the colony name.
     *
     * @return colony name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current season counter.
     *
     * @return season count
     */
    public int getSeasonCounter() {
        return seasonCounter;
    }
}