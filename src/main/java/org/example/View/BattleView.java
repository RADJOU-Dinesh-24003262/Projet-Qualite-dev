package org.example.View;

/**
 * View class for displaying battle information.
 */
public class BattleView {

    /**
     * Displays faction battle notification.
     *
     * @param faction1 name of first faction
     * @param faction2 name of second faction
     */
    public void displayFactionBattle(String faction1, String faction2) {
        System.out.println("   ⚔️  " + faction1 + " vs " + faction2);
    }

    /**
     * Displays a fight between two characters.
     *
     * @param fighter1Name name of first fighter
     * @param fighter2Name name of second fighter
     */
    public void displayFight(String fighter1Name, String fighter2Name) {
        System.out.println("      " + fighter1Name + " ⚔️  " + fighter2Name);
    }

    /**
     * Displays damage dealt to a character.
     *
     * @param characterName name of the character
     * @param damage amount of damage taken
     * @param remainingHealth remaining health
     */
    public void displayDamage(String characterName, int damage, int remainingHealth) {
        if (damage > 0) {
            System.out.println("         " + characterName + " took " + damage + " damage (HP: " + remainingHealth + ")");
        }
    }

    /**
     * Displays death notification.
     *
     * @param characterName name of the deceased character
     */
    public void displayDeath(String characterName) {
        System.out.println("         💀 " + characterName + " has fallen!");
    }

    /**
     * Displays number of dead characters removed.
     *
     * @param count number of removed characters
     */
    public void displayRemovedDead(int count) {
        System.out.println("   " + count + " fallen warrior(s) removed from battlefield");
    }

    /**
     * Displays duel start.
     *
     * @param fighter1Name first fighter name
     * @param fighter2Name second fighter name
     */
    public void displayDuelStart(String fighter1Name, String fighter2Name) {
        System.out.println();
        System.out.println("   ⚔️  DUEL: " + fighter1Name + " vs " + fighter2Name);
    }

    /**
     * Displays duel winner.
     *
     * @param winnerName name of the winner
     */
    public void displayDuelWinner(String winnerName) {
        System.out.println("   🏆 Winner: " + winnerName);
    }

    /**
     * Displays battle summary.
     *
     * @param survivors number of survivors
     * @param casualties number of casualties
     */
    public void displayBattleSummary(int survivors, int casualties) {
        System.out.println();
        System.out.println("   Battle Summary:");
        System.out.println("      Survivors: " + survivors);
        System.out.println("      Casualties: " + casualties);
    }
}