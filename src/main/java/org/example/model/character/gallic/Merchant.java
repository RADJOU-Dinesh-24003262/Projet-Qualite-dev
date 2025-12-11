package org.example.model.character.gallic;

import org.example.model.character.Interface.Worker;

/**
 * Represents a Merchant, a type of Gallic character who is a worker specializing in trade.
 * The merchant negotiates deals and their productivity increases with each completed transaction.
 */
public class Merchant extends Gallic implements Worker {

    /**
     * The productivity level of the merchant, from 0 to 100.
     */
    private int productivity = 85;
    /**
     * The total number of deals completed by the merchant.
     */
    private int dealsCompleted = 0;

    /**
     * Constructs a Merchant character with specified attributes.
     * @param name The name of the character.
     * @param age The age of the character.
     * @param strength The strength of the character.
     * @param health The health of the character.
     */
    public Merchant(String name, int age, int strength, int health) {
        super(name, age, strength, health);
    }

    /**
     * Performs the merchant's work action. This involves trading goods and negotiating deals,
     * which increases the number of deals completed and their productivity.
     */
    @Override
    public void work() {
        System.out.println(getName() + " trades goods and negotiates deals.");
        dealsCompleted++;
        productivity = Math.min(100, productivity + 3);
    }

    /**
     * Gets the current productivity level of the merchant.
     * @return The productivity level (0-100).
     */
    @Override
    public int getProductivity() {
        return productivity;
    }

    /**
     * Gets the type of work the merchant performs.
     * @return A string describing the work type.
     */
    @Override
    public String getWorkType() {
        return "Trading & Commerce";
    }

    /**
     * Gets the total number of deals completed by the merchant.
     * @return The count of completed deals.
     */
    public int getDealsCompleted() {
        return dealsCompleted;
    }
}
