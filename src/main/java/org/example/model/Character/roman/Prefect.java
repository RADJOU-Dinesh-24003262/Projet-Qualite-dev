package org.example.model.Character.roman;

import org.example.model.Character.Interface.Leader;

/**
 * Prefect - Can lead
 */
public class Prefect extends Roman implements Leader {

    private int leadershipScore = 75;
    private int decisionsMade = 0;

    @Override
    public void lead() {
        System.out.println(getName() + " administers the province and maintains order.");
        leadershipScore = Math.min(100, leadershipScore + 2);
        decisionsMade++;

        System.out.println("  → Order and discipline are maintained.");
    }

    @Override
    public int getLeadershipScore() {
        return leadershipScore;
    }

    public int getDecisionsMade() {
        return decisionsMade;
    }
}
