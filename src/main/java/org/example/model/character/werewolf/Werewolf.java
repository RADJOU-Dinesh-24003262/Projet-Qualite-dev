package org.example.model.character.werewolf;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.Interface.Combatant;
import org.example.model.food.FoodItemType;
import org.example.model.pack.Pack;

/**
 * Represents a Werewolf character, a complex entity with unique behaviors and attributes.
 * <p>
 * The Werewolf class uses a composition-based design, delegating specific sets of behaviors
 * to specialized manager classes:
 * <ul>
 *     <li>{@link WerewolfStats}: Manages the calculation of stats and levels.</li>
 *     <li>{@link WerewolfHowlManager}: Manages howling and responses to howls.</li>
 *     <li>{@link WerewolfTransformationManager}: Manages transformations between human and werewolf form.</li>
 *     <li>{@link WerewolfPackBehaviour}: Manages interactions with a {@link Pack}.</li>
 *     <li>{@link WerewolfDisplay}: Manages the display of the werewolf's characteristics.</li>
 * </ul>
 * This approach promotes separation of concerns and makes the class easier to maintain and extend.
 * </p>
 */
public class Werewolf extends AbstractCharacter implements Combatant {

    /** The age category of the werewolf (e.g., YOUNG, ADULT, OLD). */
    private AgeCategory ageCategory = AgeCategory.ADULT;
    /** Flag indicating if the werewolf is currently in human form. */
    private boolean isHuman = false;
    /** A factor representing the werewolf's dominance in social interactions. */
    private int dominationFactor = 0;
    /** The rank of the werewolf within its pack. */
    private int rank = 0;
    /** A factor representing the werewolf's impulsiveness or impetuosity. */
    private double impetuosity;
    /** The pack that this werewolf belongs to. Can be null if they are a lone wolf. */
    private Pack pack;

    // Managers for handling different aspects of werewolf behavior
    private final WerewolfStats stats = new WerewolfStats(this);
    private final WerewolfHowlManager howlManager = new WerewolfHowlManager(this);
    private final WerewolfTransformationManager transformManager = new WerewolfTransformationManager(this);
    private final WerewolfPackBehaviour packBehaviour = new WerewolfPackBehaviour(this);
    private final WerewolfDisplay display = new WerewolfDisplay(this);

    /** Default constructor for a werewolf. */
    public Werewolf() { super(); }

    /**
     * Constructs a werewolf with a specific name.
     * @param name The name of the werewolf.
     */
    public Werewolf(String name) { super(name); }

    /**
     * Constructs a Werewolf character with specified attributes.
     * @param name The name of the character.
     * @param age The age of the character.
     * @param strength The strength of the character.
     * @param health The health of the character.
     */
    public Werewolf(String name, int age, int strength, int health) {
        super(name, age, strength, health);
    }

    // ====== Delegated Behaviors ======

    /**
     * Performs a howl of a specific type.
     * Delegates the action to the {@link WerewolfHowlManager}.
     * @param type The type of howl to perform.
     */
    public void howl(HowlType type) { howlManager.howl(type); }

    /**
     * Reacts to a howl from another werewolf.
     * Delegates the action to the {@link WerewolfHowlManager}.
     * @param sender The werewolf who initiated the howl.
     * @param type The type of howl heard.
     */
    public void hearHowl(Werewolf sender, HowlType type) { howlManager.hearHowl(sender, type); }

    /**
     * Transforms the werewolf into its human form.
     * Delegates the action to the {@link WerewolfTransformationManager}.
     */
    public void transformToHuman() { transformManager.toHuman(); }

    /**
     * Transforms the werewolf back into its wolf form.
     * Delegates the action to the {@link WerewolfTransformationManager}.
     */
    public void transformToWerewolf() { transformManager.toWerewolf(); }

    /**
     * Causes the werewolf to leave its current pack.
     * Delegates the action to the {@link WerewolfPackBehaviour}.
     */
    public void leavePack() { packBehaviour.leavePack(); }

    /**
     * Displays the werewolf's current characteristics to the console.
     * Delegates the action to the {@link WerewolfDisplay}.
     */
    public void displayCharacteristics() { display.show(); }

    /**
     * Calculates the werewolf's overall level based on its stats.
     * Delegates the calculation to the {@link WerewolfStats}.
     * @return The calculated level.
     */
    public double calculateLevel() { return stats.calculateLevel(); }

    /**
     * Gets the list of food a werewolf can eat. Werewolves are omnivores and can eat anything.
     * @return An array containing all {@link FoodItemType} values.
     */
    @Override
    protected FoodItemType[] getFoodEdibleList() { return FoodItemType.values(); }

    /**
     * Performs a combat action. Werewolves fight with ferocity, but are ineffective in human form.
     */
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

    // ====== Getters and Setters ======

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

    public double getImpetuosityFactor() { return impetuosity; }
    public void setImpetuosityFactor(double impetuosity) { this.impetuosity = impetuosity; }

    // ====== Manager Getters ======

    /**
     * Gets the stats manager for this werewolf.
     * @return The {@link WerewolfStats} instance.
     */
    public WerewolfStats getStats() { return stats; }

    /**
     * Gets the pack behavior manager for this werewolf.
     * @return The {@link WerewolfPackBehaviour} instance.
     */
    public WerewolfPackBehaviour getPackBehaviour() { return packBehaviour; }
}