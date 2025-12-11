package org.example.model.character.gallic;

import org.example.model.character.Interface.Worker;

/**
 * Represents a Blacksmith, a type of Gallic character who is a skilled worker.
 * The blacksmith can forge weapons, repair armor, and their productivity increases with work.
 */
public class Blacksmith extends Gallic implements Worker {

    /**
     * The productivity level of the blacksmith, from 0 to 100.
     */
    private int productivity = 70;
    /**
     * The total number of items crafted by the blacksmith.
     */
    private int itemsCrafted = 0;

    /**
     * Performs the blacksmith's work action. This involves forging weapons and repairing armor,
     * which increases the number of items crafted, productivity, and slightly increases strength.
     */
    @Override
    public void work() {
        System.out.println(getName() + " forges weapons and repairs armor.");
        itemsCrafted++;
        productivity = Math.min(100, productivity + 2);

        // Work increases strength slightly
        setStrength(getStrength() + 1);
    }

    /**
     * Gets the current productivity level of the blacksmith.
     * @return The productivity level (0-100).
     */
    @Override
    public int getProductivity() {
        return productivity;
    }

    /**
     * Gets the type of work the blacksmith performs.
     * @return A string describing the work type.
     */
    @Override
    public String getWorkType() {
        return "Blacksmithing & Metalwork";
    }

    /**
     * Gets the total number of items crafted by the blacksmith.
     * @return The count of crafted items.
     */
    public int getItemsCrafted() {
        return itemsCrafted;
    }
}