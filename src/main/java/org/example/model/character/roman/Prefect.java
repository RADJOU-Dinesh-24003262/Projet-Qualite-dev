package org.example.model.character.roman;

import org.example.model.character.Interface.Leader;

/**
 * Represents a Prefect, a type of Roman character who is a leader.
 * The prefect is an administrator responsible for maintaining order in a province.
 */
public class Prefect extends Roman implements Leader {

    /**
     * The leadership ability score of the prefect, from 0 to 100.
     */
    private int leadershipScore = 75;
    /**
     * The total number of administrative decisions made by the prefect.
     */
    private int decisionsMade = 0;

    /**
     * Performs a leadership action by administering the province and maintaining order.
     * This improves the prefect's leadership score and increases their count of decisions made.
     */
    @Override
    public void lead() {
        System.out.println(getName() + " administers the province and maintains order.");
        leadershipScore = Math.min(100, leadershipScore + 2);
        decisionsMade++;

        System.out.println("  → Order and discipline are maintained.");
    }

    /**
     * Gets the current leadership score of the prefect.
     * @return The leadership score (0-100).
     */
    @Override
    public int getLeadershipScore() {
        return leadershipScore;
    }

    /**
     * Gets the total number of decisions made by the prefect.
     * @return The count of made decisions.
     */
    public int getDecisionsMade() {
        return decisionsMade;
    }
}
