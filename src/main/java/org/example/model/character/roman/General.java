package org.example.model.character.roman;

import org.example.model.character.Interface.Combatant;
import org.example.model.character.Interface.Leader;

/**
 * General - Can fight and lead
 */
public class General extends Roman implements Combatant, Leader {

    private int leadershipScore = 90;
    private int battlesWon = 0;

    @Override
    public void combat() {
        System.out.println(getName() + " leads the charge into battle!");
        setBelligerence(getBelligerence() + 15);
        battlesWon++;
    }

    @Override
    public void lead() {
        System.out.println(getName() + " commands the Roman legions with authority.");
        leadershipScore = Math.min(100, leadershipScore + 3);

        // Leadership reduces belligerence of subordinates
        System.out.println("  → Troops rally under " + getName() + "'s command!");
    }

    @Override
    public int getLeadershipScore() {
        return leadershipScore;
    }

    public int getBattlesWon() {
        return battlesWon;
    }
}
