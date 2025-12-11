package org.example.model.character.gallic;

import org.example.model.character.Interface.Combatant;
import org.example.model.character.Interface.Leader;
import org.example.model.character.Interface.Worker;

/**
 * Represents a Druid, a versatile Gallic character who can fight, lead, and work.
 * Druids are known for their magical abilities, wisdom, and skill in brewing potions.
 */
public class Druid extends Gallic implements Combatant, Leader, Worker {

    /**
     * The leadership ability score of the druid, from 0 to 100.
     */
    private int leadershipScore = 80;
    /**
     * The productivity level of the druid in their work, from 0 to 100.
     */
    private int productivity = 90;

    /**
     * Constructs a Druid character with specified attributes.
     * @param name The name of the character.
     * @param age The age of the character.
     * @param strength The strength of the character.
     * @param health The health of the character.
     */
    public Druid(String name, int age, int strength, int health) {
        super(name, age, strength, health);
    }

    /**
     * Performs a combat action using magical techniques.
     * This increases the druid's belligerence.
     */
    @Override
    public void combat() {
        System.out.println(getName() + " uses magical combat techniques!");
        setBelligerence(getBelligerence() + 5);
    }

    /**
     * Performs a leadership action by providing guidance.
     * This improves the druid's leadership score.
     */
    @Override
    public void lead() {
        System.out.println(getName() + " provides wise guidance to the village.");
        leadershipScore = Math.min(100, leadershipScore + 2);
    }

    /**
     * Gets the current leadership score of the druid.
     * @return The leadership score (0-100).
     */
    @Override
    public int getLeadershipScore() {
        return leadershipScore;
    }

    /**
     * Performs the druid's work action, which involves brewing potions and gathering herbs.
     * This increases their productivity.
     */
    @Override
    public void work() {
        System.out.println(getName() + " brews magic potions and gathers herbs.");
        productivity = Math.min(100, productivity + 3);
    }

    /**
     * Gets the current productivity level of the druid.
     * @return The productivity level (0-100).
     */
    @Override
    public int getProductivity() {
        return productivity;
    }

    /**
     * Gets the type of work the druid performs.
     * @return A string describing the work type.
     */
    @Override
    public String getWorkType() {
        return "Potion Brewing & Herb Gathering";
    }
}