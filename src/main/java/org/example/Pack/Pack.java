package org.example.Pack;

import org.example.Model.Character.Werewolf.HowlType;
import org.example.Model.Character.Werewolf.Werewolf;

import java.util.ArrayList;
import java.util.List;

public class Pack {

    private final String name;

    // Managers
    private final HierarchyManager hierarchyManager;
    private final AlphaManager alphaManager;
    private final RankAssigner rankAssigner;
    private final ReproductionManager reproductionManager;
    private final DominationManager dominationManager;
    private final OmegaManager omegaManager;
    private final PackDisplay packDisplay;

    public Pack(String name, Werewolf alphaMale, Werewolf alphaFemale) {

        // Validate initial pack creation
        new PackValidator().validatePackCreation(name, alphaMale, alphaFemale);

        this.name = name;

        // Initialize internal managers
        this.hierarchyManager = new HierarchyManager();
        this.alphaManager = new AlphaManager(hierarchyManager, alphaMale, alphaFemale);
        this.rankAssigner = new RankAssigner(hierarchyManager);
        this.reproductionManager = new ReproductionManager(hierarchyManager, alphaManager);
        this.dominationManager = new DominationManager(hierarchyManager, alphaManager);
        this.omegaManager = new OmegaManager(hierarchyManager);
        this.packDisplay = new PackDisplay(hierarchyManager, alphaManager);
    }

    /*-------------------------------------------
     *  PUBLIC API — the pack façade
     *-------------------------------------------*/

    /** Display pack information (name, members, alphas, distribution...) */
    public void displayPack() {
        packDisplay.displayPack(name);
    }

    /** Display detailed information for every werewolf */
    public void displayMembers() {
        packDisplay.displayMembers();
    }

    /** Creates a fresh hierarchy (excluding alpha couple) */
    public void createHierarchy(List<Werewolf> werewolves) {
        rankAssigner.createHierarchy(werewolves,
                alphaManager.getAlphaMale(),
                alphaManager.getAlphaFemale());
    }

    /** Declare the omega werewolves */
    public void identifyOmegas() {
        omegaManager.identifyOmegaWerewolves();
    }

    /** Perform a domination attempt */
    public boolean attemptDomination(Werewolf aggressor, Werewolf target) {
        return dominationManager.attemptDomination(aggressor, target);
    }

    /** Trigger reproduction (alpha couple only). Returns newborn cubs. */
    public List<Werewolf> reproduce() {
        return reproductionManager.reproduce();
    }

    /** Create a new alpha couple by dethroning the current male alpha (if needed). */
    public void formNewAlphaMale(Werewolf newAlphaMale) {
        alphaManager.formNewAlphaCouple(newAlphaMale);
    }

    /** Add a new werewolf to the pack */
    public void addWerewolf(Werewolf w, Rank rank) {
        hierarchyManager.addMember(w, rank);
    }

    /** Remove a werewolf from the pack */
    public void removeWerewolf(Werewolf w) {
        hierarchyManager.removeMember(w);
    }

    /** Get all members of the pack */
    public List<Werewolf> getMembers() {
        return new ArrayList<>(hierarchyManager.getMembers());
    }

    public Werewolf getAlphaMale() {
        return alphaManager.getAlphaMale();
    }

    public Werewolf getAlphaFemale() {
        return alphaManager.getAlphaFemale();
    }

    public String getName() {
        return name;
    }

    public void notifyHowl(Werewolf w, HowlType type) {
        for (Werewolf member : hierarchyManager.getMembers()) {
            member.hearHowl(w, type);
        }
    }
}
