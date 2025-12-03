package org.example.Model.Character;

import java.util.Objects;
import org.example.Model.Food.FoodItemType;
import org.example.Pack.Pack;

/**
 * Represents a werewolf (lycanthrope) character with specific attributes
 * related to dominance hierarchy, pack behavior, and transformation abilities.
 *
 * <p>A werewolf possesses the following characteristics:
 * <ul>
 *   <li>Sex (inherited from AbstractCharacter)</li>
 *   <li>Age category (young, adult, or old)</li>
 *   <li>Strength (inherited)</li>
 *   <li>Domination factor (difference between dominations exercised and suffered)</li>
 *   <li>Rank within the dominance hierarchy</li>
 *   <li>Level (quality criterion calculated from age, strength, domination, and rank)</li>
 *   <li>Impetuosity factor (behavioral trait)</li>
 *   <li>Pack membership (or solitary status)</li>
 * </ul>
 * </p>
 */
public class Werewolf extends AbstractCharacter {

    /**
     * Enumeration of age categories for werewolves.
     * Each category has a multiplier that affects the werewolf's level calculation.
     */
    public enum AgeCategory {
        /** Young werewolf with 80% level multiplier */
        YOUNG("Young", 0.8),
        /** Adult werewolf with 100% level multiplier */
        ADULT("Adult", 1.0),
        /** Old werewolf with 90% level multiplier */
        OLD("Old", 0.9);

        private final String label;
        private final double levelMultiplier;

        AgeCategory(String label, double levelMultiplier) {
            this.label = label;
            this.levelMultiplier = levelMultiplier;
        }

        public String getLabel() {
            return label;
        }

        public double getLevelMultiplier() {
            return levelMultiplier;
        }
    }

    /**
     * Enumeration of howl types for werewolf communication.
     * Each howl conveys a specific message to other pack members.
     */
    public enum HowlType {
        /** Howl asserting dominance and authority */
        DOMINATION("Domination howl - asserting authority"),
        /** Howl showing submission and respect */
        SUBMISSION("Submission howl - showing respect"),
        /** Howl warning of imminent attack */
        AGGRESSION("Aggression howl - warning of attack"),
        /** Howl calling to pack members */
        BELONGING("Belonging howl - calling to the pack"),
        /** Howl expressing happiness */
        JOY("Joy howl - expressing happiness"),
        /** Howl expressing sorrow */
        SADNESS("Sadness howl - expressing sorrow");

        private final String description;

        HowlType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    private static final double MIN_IMPETUOSITY = 0.0;
    private static final double MAX_IMPETUOSITY = 1.0;
    private static final int SICK_HEALTH_THRESHOLD = 20;
    private static final double HUMAN_STRENGTH_MULTIPLIER = 0.5;
    private static final double HUMAN_STAMINA_MULTIPLIER = 0.7;
    private static final int AGGRESSION_INCREASE = 10;

    // Level calculation weights
    private static final double STRENGTH_WEIGHT = 0.4;
    private static final double DOMINATION_WEIGHT = 0.3;
    private static final double RANK_WEIGHT = 0.2;
    private static final double HEALTH_WEIGHT = 0.1;

    private AgeCategory ageCategory;
    private int dominationFactor;
    private int rank;
    private double impetuosityFactor;
    private Pack pack;
    private boolean isHuman;

    /**
     * Default constructor for Werewolf.
     * Initializes with adult age category, neutral domination, and solitary status.
     */
    public Werewolf() {
        super();
        this.ageCategory = AgeCategory.ADULT;
        this.dominationFactor = 0;
        this.rank = 0;
        this.impetuosityFactor = 0.5;
        this.pack = null;
        this.isHuman = false;
        this.isAlive = true;
    }

    /**
     * Constructor with name parameter.
     *
     * @param name the name of the werewolf
     */
    public Werewolf(String name) {
        super(name);
        this.ageCategory = AgeCategory.ADULT;
        this.dominationFactor = 0;
        this.rank = 0;
        this.impetuosityFactor = 0.5;
        this.pack = null;
        this.isHuman = false;
        this.isAlive = true;
    }

    /**
     * Calculates the level of the werewolf based on age, strength,
     * domination factor, and rank.
     *
     * <p>The formula is:
     * level = (strength × 0.4 + dominationFactor × 0.3 + rank × 0.2 + health × 0.1)
     *         × ageCategoryMultiplier
     * </p>
     *
     * @return the calculated level as a double
     */
    public double calculateLevel() {
        double baseLevel = (this.strength * STRENGTH_WEIGHT)
                + (this.dominationFactor * DOMINATION_WEIGHT)
                + (this.rank * RANK_WEIGHT)
                + (this.health * HEALTH_WEIGHT);

        return baseLevel * ageCategory.getLevelMultiplier();
    }

    /**
     * Displays all characteristics of the werewolf to the console.
     * This includes physical attributes, social standing, and current state.
     */
    public void displayCharacteristics() {
        System.out.println("=== Werewolf Characteristics ===");
        System.out.println("Name: " + this.name);
        System.out.println("Sex: " + (this.sex != null ? this.sex : "Not defined"));
        System.out.println("Age Category: " + this.ageCategory.getLabel());
        System.out.println("Age: " + this.age + " years");
        System.out.println("Height: " + this.height + " m");
        System.out.println("Strength: " + this.strength);
        System.out.println("Stamina: " + this.stamina);
        System.out.println("Health: " + this.health);
        System.out.println("Hunger: " + this.hunger);
        System.out.println("Belligerence: " + this.belligerence);
        System.out.println("Domination Factor: " + this.dominationFactor);
        System.out.println("Rank: " + this.rank);
        System.out.println("Level: " + String.format("%.2f", calculateLevel()));
        System.out.println("Impetuosity Factor: " + String.format("%.2f", this.impetuosityFactor));
        System.out.println("Pack: " + (this.pack != null ? this.pack.getName() : "Solitary"));
        System.out.println("Form: " + (this.isHuman ? "Human" : "Werewolf"));
        System.out.println("Status: " + (this.isAlive ? "Alive" : "Dead"));
        System.out.println("================================\n");
    }

    /**
     * Allows the werewolf to howl with a specific type.
     * The howl is broadcast to all pack members if the werewolf belongs to a pack.
     *
     * <p>Requirements for howling:
     * <ul>
     *   <li>Werewolf must be alive</li>
     *   <li>Werewolf must be in werewolf form (not human)</li>
     * </ul>
     * </p>
     *
     * @param howlType the type of howl to emit
     * @throws IllegalArgumentException if howlType is null
     */
    public void howl(HowlType howlType) {
        if (howlType == null) {
            throw new IllegalArgumentException("Howl type cannot be null");
        }

        if (!this.isAlive) {
            System.out.println(this.name + " cannot howl (dead).");
            return;
        }

        if (this.isHuman) {
            System.out.println(this.name + " cannot howl in human form.");
            return;
        }

        System.out.println(this.name + " howls: " + howlType.getDescription());

        if (this.pack != null) {
            this.pack.notifyHowl(this, howlType);
        }
    }

    /**
     * Processes a howl heard from another werewolf.
     * The response depends on the health status, form, and the type of howl.
     *
     * <p>A werewolf cannot hear howls if:
     * <ul>
     *   <li>It is dead</li>
     *   <li>It is too sick (health below threshold)</li>
     * </ul>
     * </p>
     *
     * @param sender the werewolf who emitted the howl
     * @param howlType the type of howl heard
     * @throws IllegalArgumentException if sender or howlType is null
     */
    public void hearHowl(Werewolf sender, HowlType howlType) {
        if (sender == null) {
            throw new IllegalArgumentException("Sender cannot be null");
        }
        if (howlType == null) {
            throw new IllegalArgumentException("Howl type cannot be null");
        }

        if (!this.isAlive) {
            return;
        }

        if (this.health < SICK_HEALTH_THRESHOLD) {
            System.out.println(this.name + " is too sick to respond to the howl.");
            return;
        }

        if (this.isHuman) {
            System.out.println(this.name + " (in human form) hears a distant howl...");
            return;
        }

        System.out.println(this.name + " hears " + sender.getName()
                + "'s " + howlType.name().toLowerCase() + " howl and reacts accordingly.");

        switch (howlType) {
            case DOMINATION:
                handleDominationHowl(sender);
                break;
            case SUBMISSION:
                handleSubmissionHowl(sender);
                break;
            case AGGRESSION:
                handleAggressionHowl(sender);
                break;
            case BELONGING:
                handleBelongingHowl(sender);
                break;
            case JOY:
            case SADNESS:
                handleEmotionalHowl(sender, howlType);
                break;
            default:
                // No action for unknown howl types
                break;
        }
    }

    /**
     * Handles the response to a domination howl.
     * Lower-ranked werewolves acknowledge dominance, while higher-ranked ones
     * may challenge based on impetuosity.
     *
     * @param sender the werewolf asserting dominance
     */
    private void handleDominationHowl(Werewolf sender) {
        if (this.rank < sender.getRank()) {
            System.out.println(this.name + " acknowledges " + sender.getName() + "'s dominance.");
            this.dominationFactor--;
        } else if (Math.random() < this.impetuosityFactor) {
            System.out.println(this.name + " challenges " + sender.getName() + "'s dominance!");
        }
    }

    /**
     * Handles the response to a submission howl.
     * Higher-ranked werewolves accept the submission and increase their domination.
     *
     * @param sender the werewolf showing submission
     */
    private void handleSubmissionHowl(Werewolf sender) {
        if (this.rank > sender.getRank()) {
            System.out.println(this.name + " accepts " + sender.getName() + "'s submission.");
            this.dominationFactor++;
        }
    }

    /**
     * Handles the response to an aggression howl.
     * Response depends on the werewolf's impetuosity factor.
     *
     * @param sender the aggressive werewolf
     */
    private void handleAggressionHowl(Werewolf sender) {
        if (Math.random() < this.impetuosityFactor) {
            System.out.println(this.name + " prepares for confrontation with " + sender.getName());
            this.belligerence += AGGRESSION_INCREASE;
        } else {
            System.out.println(this.name + " backs away from " + sender.getName() + "'s aggression.");
        }
    }

    /**
     * Handles the response to a belonging howl.
     * Pack members respond to calls from their own pack.
     *
     * @param sender the werewolf calling to the pack
     */
    private void handleBelongingHowl(Werewolf sender) {
        if (this.pack != null && this.pack.equals(sender.getPack())) {
            System.out.println(this.name + " responds to " + sender.getName() + "'s belonging call.");
        }
    }

    /**
     * Handles the response to emotional howls (joy or sadness).
     *
     * @param sender the werewolf expressing emotion
     * @param howlType the type of emotional howl
     */
    private void handleEmotionalHowl(Werewolf sender, HowlType howlType) {
        System.out.println(this.name + " empathizes with " + sender.getName()
                + "'s " + howlType.name().toLowerCase() + ".");
    }

    /**
     * Separates the werewolf from its pack, making it solitary.
     * This resets the werewolf's rank and domination factor.
     *
     * <p>If the werewolf is already solitary, this method has no effect.</p>
     */
    public void leavesPack() {
        if (this.pack == null) {
            System.out.println(this.name + " is already solitary.");
            return;
        }

        Pack formerPack = this.pack;
        this.pack.removeMember(this);
        this.pack = null;
        this.rank = 0;
        this.dominationFactor = 0;

        System.out.println(this.name + " has left " + formerPack.getName()
                + " and is now solitary.");
    }

    /**
     * Transforms the werewolf into human form.
     * This reduces strength and stamina significantly.
     *
     * <p>If already in human form, this method has no effect.</p>
     */
    public void transformToHuman() {
        if (this.isHuman) {
            System.out.println(this.name + " is already in human form.");
            return;
        }

        this.isHuman = true;
        this.strength = (int) (this.strength * HUMAN_STRENGTH_MULTIPLIER);
        this.stamina = (int) (this.stamina * HUMAN_STAMINA_MULTIPLIER);

        System.out.println(this.name + " transforms into human form.");
    }

    /**
     * Transforms the human back into werewolf form.
     * This restores full werewolf strength and stamina.
     *
     * <p>If already in werewolf form, this method has no effect.</p>
     */
    public void transformToWerewolf() {
        if (!this.isHuman) {
            System.out.println(this.name + " is already in werewolf form.");
            return;
        }

        this.isHuman = false;
        this.strength = (int) (this.strength / HUMAN_STRENGTH_MULTIPLIER);
        this.stamina = (int) (this.stamina / HUMAN_STAMINA_MULTIPLIER);

        System.out.println(this.name + " transforms back into werewolf form!");
    }

    /**
     * Returns the list of food items that a werewolf can eat.
     * Werewolves are omnivorous and can consume all food types.
     *
     * @return array containing all food item types
     */
    @Override
    protected FoodItemType[] getFoodEdibleList() {
        return FoodItemType.values();
    }

    // Getters and Setters

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        if (ageCategory == null) {
            throw new IllegalArgumentException("Age category cannot be null");
        }
        this.ageCategory = ageCategory;
    }

    public int getDominationFactor() {
        return dominationFactor;
    }

    public void setDominationFactor(int dominationFactor) {
        this.dominationFactor = dominationFactor;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        if (rank < 0) {
            throw new IllegalArgumentException("Rank cannot be negative");
        }
        this.rank = rank;
    }

    public double getImpetuosityFactor() {
        return impetuosityFactor;
    }

    public void setImpetuosityFactor(double impetuosityFactor) {
        if (impetuosityFactor < MIN_IMPETUOSITY || impetuosityFactor > MAX_IMPETUOSITY) {
            throw new IllegalArgumentException(
                    "Impetuosity factor must be between " + MIN_IMPETUOSITY + " and " + MAX_IMPETUOSITY
            );
        }
        this.impetuosityFactor = impetuosityFactor;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public boolean isSolitary() {
        return this.pack == null;
    }

    @Override
    public String toString() {
        return "Werewolf{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", ageCategory=" + ageCategory +
                ", age=" + age +
                ", strength=" + strength +
                ", health=" + health +
                ", dominationFactor=" + dominationFactor +
                ", rank=" + rank +
                ", level=" + String.format("%.2f", calculateLevel()) +
                ", impetuosityFactor=" + String.format("%.2f", impetuosityFactor) +
                ", pack=" + (pack != null ? pack.getName() : "Solitary") +
                ", form=" + (isHuman ? "Human" : "Werewolf") +
                ", isAlive=" + isAlive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        if (!(o instanceof Werewolf)) {
            return false;
        }
        Werewolf werewolf = (Werewolf) o;
        return dominationFactor == werewolf.dominationFactor
                && rank == werewolf.rank
                && Double.compare(werewolf.impetuosityFactor, impetuosityFactor) == 0
                && isHuman == werewolf.isHuman
                && ageCategory == werewolf.ageCategory
                && Objects.equals(pack, werewolf.pack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ageCategory, dominationFactor,
                rank, impetuosityFactor, pack, isHuman);
    }
}