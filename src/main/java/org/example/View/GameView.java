package org.example.View;

import org.example.Model.InvasionTheater;

/**
 * View class for displaying main game information.
 */
public class GameView {

    private static final String SEPARATOR = "=".repeat(60);
    private static final String LINE = "-".repeat(60);

    /**
     * Displays welcome message.
     *
     * @param theaterName name of the theater
     */
    public void displayWelcome(String theaterName) {
        System.out.println(SEPARATOR);
        System.out.println("  WELCOME TO THE INVASION OF ARMORICA");
        System.out.println("  Theater: " + theaterName);
        System.out.println("  Year 50 BC - Gaul under Roman occupation");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    /**
     * Displays turn start information.
     *
     * @param turnNumber the current turn number
     */
    public void displayTurnStart(int turnNumber) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("  TURN " + turnNumber);
        System.out.println(SEPARATOR);
    }

    /**
     * Displays turn end information.
     *
     * @param turnNumber the current turn number
     */
    public void displayTurnEnd(int turnNumber) {
        System.out.println(LINE);
        System.out.println("  End of turn " + turnNumber);
        System.out.println(LINE);
    }

    /**
     * Displays battle start notification.
     *
     * @param battlefieldName name of the battlefield
     * @param fighterCount number of fighters
     */
    public void displayBattleStart(String battlefieldName, int fighterCount) {
        System.out.println();
        System.out.println("⚔️  BATTLE at " + battlefieldName);
        System.out.println("   Fighters: " + fighterCount);
    }

    /**
     * Displays battle end notification.
     *
     * @param battlefieldName name of the battlefield
     */
    public void displayBattleEnd(String battlefieldName) {
        System.out.println("   Battle at " + battlefieldName + " concluded.");
    }

    /**
     * Displays clan leader's turn notification.
     *
     * @param leaderName name of the clan leader
     */
    public void displayClanLeaderTurn(String leaderName) {
        System.out.println();
        System.out.println(LINE);
        System.out.println("  👑 " + leaderName + "'s Turn");
        System.out.println(LINE);
    }

    /**
     * Displays a generic message.
     *
     * @param message the message to display
     */
    public void displayMessage(String message) {
        System.out.println("  " + message);
    }

    /**
     * Displays game over screen.
     *
     * @param finalTurn the final turn number
     */
    public void displayGameOver(int finalTurn) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("  GAME OVER");
        System.out.println("  Total turns played: " + finalTurn);
        System.out.println(SEPARATOR);
    }

    /**
     * Displays game statistics.
     *
     * @param theater the invasion theater
     * @param turnNumber current turn number
     */
    public void displayStatistics(InvasionTheater theater, int turnNumber) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("  GAME STATISTICS");
        System.out.println(SEPARATOR);
        System.out.println("  Turn: " + turnNumber);
        System.out.println("  Places: " + theater.getPlaces().size());
        System.out.println("  Clan Leaders: " + theater.getClanLeaders().size());
        System.out.println("  Total Characters: " + theater.getTotalCharacterCount());
        System.out.println(SEPARATOR);
    }

    /**
     * Displays error message.
     *
     * @param error the error message
     */
    public void displayError(String error) {
        System.out.println("  ❌ ERROR: " + error);
    }

    /**
     * Displays success message.
     *
     * @param message the success message
     */
    public void displaySuccess(String message) {
        System.out.println("  ✅ " + message);
    }

    /**
     * Displays info message.
     *
     * @param info the info message
     */
    public void displayInfo(String info) {
        System.out.println("  ℹ️  " + info);
    }
}