package org.example.model.Character.gallic;

import org.example.model.Character.Interface.Worker;

/**
 * Innkeeper - Can work (serving food and managing the inn)
 */
public class Innkeeper extends Gallic implements Worker {

    private int productivity = 75;
    private int customersServed = 0;

    @Override
    public void work() {
        System.out.println(getName() + " serves food and welcomes guests at the inn.");
        customersServed++;
        productivity = Math.min(100, productivity + 2);
    }

    @Override
    public int getProductivity() {
        return productivity;
    }

    @Override
    public String getWorkType() {
        return "Inn Management & Hospitality";
    }

    public int getCustomersServed() {
        return customersServed;
    }
}
