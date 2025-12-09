package org.example.Model.Character.Gallic;

import org.example.Model.Character.Interface.Combatant;
import org.example.Model.Character.Interface.Leader;
import org.example.Model.Character.Interface.Worker;

/**
 * Druid - Can fight, lead, and work (brewing potions)
 */
public class Druid extends Gallic implements Combatant, Leader, Worker {

    private int leadershipScore = 80;
    private int productivity = 90;

    @Override
    public void combat() {
        System.out.println(getName() + " uses magical combat techniques!");
        setBelligerence(getBelligerence() + 5);
    }

    @Override
    public void lead() {
        System.out.println(getName() + " provides wise guidance to the village.");
        leadershipScore = Math.min(100, leadershipScore + 2);
    }

    @Override
    public int getLeadershipScore() {
        return leadershipScore;
    }

    @Override
    public void work() {
        System.out.println(getName() + " brews magic potions and gathers herbs.");
        productivity = Math.min(100, productivity + 3);
    }

    @Override
    public int getProductivity() {
        return productivity;
    }

    @Override
    public String getWorkType() {
        return "Potion Brewing & Herb Gathering";
    }
}