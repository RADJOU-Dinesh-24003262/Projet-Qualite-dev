package org.example.model.character.gallic;

import org.example.model.character.Interface.Worker;

/**
 * Represents an Innkeeper, a type of Gallic character who is a worker.
 * The innkeeper manages an inn, serves customers, and their productivity increases with their work.
 */
public class Innkeeper extends Gallic implements Worker {

    /**
     * The productivity level of the innkeeper, from 0 to 100.
     */
    private int productivity = 75;
    /**
     * The total number of customers served by the innkeeper.
     */
    private int customersServed = 0;

    /**
     * Performs the innkeeper's work action. This involves serving food and welcoming guests,
     * which increases the number of customers served and their productivity.
     */
    @Override
    public void work() {
        System.out.println(getName() + " serves food and welcomes guests at the inn.");
        customersServed++;
        productivity = Math.min(100, productivity + 2);
    }

    /**
     * Gets the current productivity level of the innkeeper.
     * @return The productivity level (0-100).
     */
    @Override
    public int getProductivity() {
        return productivity;
    }

    /**
     * Gets the type of work the innkeeper performs.
     * @return A string describing the work type.
     */
    @Override
    public String getWorkType() {
        return "Inn Management & Hospitality";
    }

    /**
     * Gets the total number of customers served by the innkeeper.
     * @return The count of served customers.
     */
    public int getCustomersServed() {
        return customersServed;
    }
}
