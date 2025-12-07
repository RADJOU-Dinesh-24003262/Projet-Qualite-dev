package org.example.Model.Character.Gallic;

import org.example.Model.Character.Interface.Worker;

/**
 * Blacksmith - Can work (forging weapons and tools)
 */
public class Blacksmith extends Gallic implements Worker {

    private int productivity = 70;
    private int itemsCrafted = 0;

    @Override
    public void work() {
        System.out.println(getName() + " forges weapons and repairs armor.");
        itemsCrafted++;
        productivity = Math.min(100, productivity + 2);

        // Work increases strength slightly
        setStrength(getStrength() + 1);
    }

    @Override
    public int getProductivity() {
        return productivity;
    }

    @Override
    public String getWorkType() {
        return "Blacksmithing & Metalwork";
    }

    public int getItemsCrafted() {
        return itemsCrafted;
    }
}