package org.example.model.character.roman;

import org.example.model.character.Interface.Combatant;
import org.example.model.character.Interface.Leader;

/**
 * Represents a General, a type of Roman character who is both a combatant and a leader.
 * The general leads legions into battle and their leadership skills improve with command.
 */
public class General extends Roman implements Combatant, Leader {

    /**
     * The leadership ability score of the general, from 0 to 100.
     */
    private int leadershipScore = 90;
    /**
     * The total number of battles won by the general.
     */
    private int battlesWon = 0;

    /**
     * Constructs a General character with specified attributes.
     * @param name The name of the character.
     * @param age The age of the character.
     * @param strength The strength of the character.
     * @param health The health of the character.
     */
    public General(String name, int age, int strength, int health) {
        super(name, age, strength, health);
    }

    /**
     * Performs a combat action, leading a charge into battle.
     * This increases the general's belligerence and the count of battles won.
     */
    @Override
    public void combat() {
        System.out.println(getName() + " leads the charge into battle!");
        setBelligerence(getBelligerence() + 15);
        battlesWon++;
    }

    /**
     * Performs a leadership action by commanding Roman legions.
     * This improves the general's leadership score and rallies the troops.
     */
    @Override
    public void lead() {
        System.out.println(getName() + " commands the Roman legions with authority.");
        leadershipScore = Math.min(100, leadershipScore + 3);

        // Leadership reduces belligerence of subordinates
        System.out.println("  → Troops rally under " + getName() + "'s command!");
    }

    /**
     * Gets the current leadership score of the general.
     * @return The leadership score (0-100).
     */
    @Override
    public int getLeadershipScore() {
        return leadershipScore;
    }

    /**
     * Gets the total number of battles won by the general.
     * @return The count of won battles.
     */
    public int getBattlesWon() {
        return battlesWon;
    }
}
