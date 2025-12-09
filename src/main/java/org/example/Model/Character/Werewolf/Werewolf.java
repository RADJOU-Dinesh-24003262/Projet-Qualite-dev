package org.example.Model.Character.Werewolf;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Interface.Combatant;
import org.example.Model.Food.FoodItemType;
import org.example.Model.Pack.Pack;

public class Werewolf extends AbstractCharacter implements Combatant {

    private AgeCategory ageCategory = AgeCategory.ADULT;
    private boolean isHuman = false;
    private int dominationFactor = 0;
    private int rank = 0;
    private double impetuosity;
    private Pack pack;

    // Managers
    private final WerewolfStats stats = new WerewolfStats(this);
    private final WerewolfHowlManager howlManager = new WerewolfHowlManager(this);
    private final WerewolfTransformationManager transformManager = new WerewolfTransformationManager(this);
    private final WerewolfPackBehaviour packBehaviour = new WerewolfPackBehaviour(this);
    private final WerewolfDisplay display = new WerewolfDisplay(this);

    public Werewolf() { super(); }

    public Werewolf(String name) { super(name); }

    // Delegate behaviours
    public void howl(HowlType type) { howlManager.howl(type); }
    public void hearHowl(Werewolf sender, HowlType type) { howlManager.hearHowl(sender, type); }
    public void transformToHuman() { transformManager.toHuman(); }
    public void transformToWerewolf() { transformManager.toWerewolf(); }
    public void leavePack() { packBehaviour.leavePack(); }
    public void displayCharacteristics() { display.show(); }

    // Level calculation
    public double calculateLevel() { return stats.calculateLevel(); }

    // Food
    @Override
    protected FoodItemType[] getFoodEdibleList() { return FoodItemType.values(); }

    // Getters / setters
    public AgeCategory getAgeCategory() { return ageCategory; }
    public void setAgeCategory(AgeCategory c) { this.ageCategory = c; }

    public int getDominationFactor() { return dominationFactor; }
    public void setDominationFactor(int v) { dominationFactor = v; }

    public int getRank() { return rank; }
    public void setRank(int v) { rank = v; }

    public boolean isHuman() { return isHuman; }
    public void setHuman(boolean value) { isHuman = value; }

    public Pack getPack() { return pack; }
    public void setPack(Pack pack) { this.pack = pack; }

    public WerewolfStats getStats() { return stats; }
    public WerewolfPackBehaviour getPackBehaviour() { return packBehaviour; }

    public double getImpetuosityFactor() {
        return impetuosity;
    }

    public void setImpetuosityFactor(double impetuosity) {
        this.impetuosity = impetuosity;
    }

    @Override
    public void combat() {
        if (isHuman) {
            System.out.println(getName() + " cannot fight effectively in human form!");
            return;
        }

        System.out.println(getName() + " fights with savage werewolf ferocity!");
        setBelligerence(getBelligerence() + 20);

        // Werewolves are naturally more aggressive in combat
        if (calculateLevel() > 50) {
            System.out.println("  → " + getName() + " unleashes devastating attacks!");
        }
    }

}