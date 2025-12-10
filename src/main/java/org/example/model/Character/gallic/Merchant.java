package org.example.model.Character.gallic;

import org.example.model.Character.Interface.Worker;

/**
 * Merchant - Can work (trading goods)
 */
public class Merchant extends Gallic implements Worker {

    private int productivity = 85;
    private int dealsCompleted = 0;

    @Override
    public void work() {
        System.out.println(getName() + " trades goods and negotiates deals.");
        dealsCompleted++;
        productivity = Math.min(100, productivity + 3);
    }

    @Override
    public int getProductivity() {
        return productivity;
    }

    @Override
    public String getWorkType() {
        return "Trading & Commerce";
    }

    public int getDealsCompleted() {
        return dealsCompleted;
    }
}
