package org.example.View;

import org.example.Model.ClanLeader;
import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Place.AbstractPlace;

import java.util.List;
import java.util.Scanner;

/**
 * View class for clan leader interactions.
 */
public class ClanLeaderView {

    private Scanner scanner;
    private static final String LINE = "-".repeat(50);

    /**
     * Constructor for ClanLeaderView.
     */
    public ClanLeaderView() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays clan leader information.
     *
     * @param leader the clan leader
     */
    public void displayLeaderInfo(ClanLeader leader) {
        System.out.println();
        System.out.println(LINE);
        System.out.println("Clan Leader: " + leader.getName());
        System.out.println("Place: " + leader.getPlace().getName());
        System.out.println("Characters: " + leader.getPlace().getCharacterCount());
        System.out.println(LINE);
    }

    /**
     * Displays remaining actions.
     *
     * @param actionsRemaining number of actions remaining
     */
    public void displayActionsRemaining(int actionsRemaining) {
        System.out.println("Actions remaining: " + actionsRemaining);
    }

    /**
     * Displays menu and gets user choice.
     *
     * @return the user's choice
     */
    public int displayMenuAndGetChoice() {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("  1. Examine place");
        System.out.println("  2. Create new character");
        System.out.println("  3. Heal all characters");
        System.out.println("  4. Feed all characters");
        System.out.println("  5. Make magic potion");
        System.out.println("  6. Give potion to character");
        System.out.println("  7. Transfer character to battlefield/enclosure");
        System.out.println("  0. End turn");
        System.out.print("Choice: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    /**
     * Displays place information.
     *
     * @param placeInfo the place information string
     */
    public void displayPlaceInfo(String placeInfo) {
        System.out.println();
        System.out.println(placeInfo);
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
     * Displays action failed message.
     */
    public void displayActionFailed() {
        System.out.println("  ❌ Action failed!");
    }

    /**
     * Displays turn end message.
     *
     * @param leaderName the clan leader's name
     */
    public void displayTurnEnd(String leaderName) {
        System.out.println();
        System.out.println(leaderName + "'s turn ended.");
        System.out.println(LINE);
    }

    /**
     * Displays list of characters.
     *
     * @param characters list of characters
     */
    public void displayCharacterList(List<AbstractCharacter> characters) {
        System.out.println();
        System.out.println("Available characters:");
        for (int i = 0; i < characters.size(); i++) {
            AbstractCharacter c = characters.get(i);
            System.out.println("  " + (i + 1) + ". " + c.getName() +
                    " [" + c.getClass().getSimpleName() + "]" +
                    " (HP: " + c.getHealth() + ", STR: " + c.getStrength() + ")");
        }
    }

    /**
     * Displays list of destinations.
     *
     * @param places list of places
     */
    public void displayDestinationList(List<AbstractPlace> places) {
        System.out.println();
        System.out.println("Available destinations:");
        for (int i = 0; i < places.size(); i++) {
            AbstractPlace p = places.get(i);
            System.out.println("  " + (i + 1) + ". " + p.getName() +
                    " [" + p.getClass().getSimpleName() + "]");
        }
    }

    /**
     * Displays confirmation prompt.
     *
     * @param message the confirmation message
     * @return true if user confirms
     */
    public boolean displayConfirmation(String message) {
        System.out.print(message + " (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }

    /**
     * Displays error message.
     *
     * @param error the error message
     */
    public void displayError(String error) {
        System.out.println("  ❌ " + error);
    }

    /**
     * Displays success message.
     *
     * @param message the success message
     */
    public void displaySuccess(String message) {
        System.out.println("  ✅ " + message);
    }
}