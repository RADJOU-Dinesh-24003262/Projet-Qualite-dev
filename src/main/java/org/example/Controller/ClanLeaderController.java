package org.example.Controller;

import org.example.Model.ClanLeader;
import org.example.Model.Character.AbstractCharacter;
import org.example.Model.InvasionTheater;
import org.example.Model.Place.AbstractPlace;
import org.example.Model.Place.Battlefield;
import org.example.Model.Place.Enclosure;
import org.example.Model.Potion.Potion;
import org.example.View.ClanLeaderView;

import java.util.List;
import java.util.Scanner;

/**
 * Controller managing clan leader actions and interactions.
 */
public class ClanLeaderController {

    private InvasionTheater theater;
    private ClanLeaderView view;
    private Scanner scanner;

    /**
     * Constructor for ClanLeaderController.
     *
     * @param theater the invasion theater
     */
    public ClanLeaderController(InvasionTheater theater) {
        this.theater = theater;
        this.view = new ClanLeaderView();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Handles a single turn for a clan leader.
     *
     * @param leader the clan leader
     */
    public void handleClanLeaderTurn(ClanLeader leader) {
        view.displayLeaderInfo(leader);

        while (leader.getActionsRemainingThisTurn() > 0) {
            view.displayActionsRemaining(leader.getActionsRemainingThisTurn());
            int choice = view.displayMenuAndGetChoice();

            boolean actionPerformed = processLeaderAction(leader, choice);

            if (!actionPerformed) {
                view.displayActionFailed();
            }

            if (choice == 0) {
                break;
            }
        }

        view.displayTurnEnd(leader.getName());
    }

    /**
     * Processes a clan leader action based on menu choice.
     *
     * @param leader the clan leader
     * @param choice the menu choice
     * @return true if action was successful
     */
    private boolean processLeaderAction(ClanLeader leader, int choice) {
        switch (choice) {
            case 0:
                return true; // End turn
            case 1:
                return examinePlace(leader);
            case 2:
                return createNewCharacter(leader);
            case 3:
                return healCharacters(leader);
            case 4:
                return feedCharacters(leader);
            case 5:
                return makeMagicPotion(leader);
            case 6:
                return givePotionToCharacter(leader);
            case 7:
                return transferCharacter(leader);
            default:
                return false;
        }
    }

    /**
     * Examines the leader's place.
     *
     * @param leader the clan leader
     * @return true if successful
     */
    private boolean examinePlace(ClanLeader leader) {
        String info = leader.examinePlace();
        view.displayPlaceInfo(info);
        return true; // Free action
    }

    /**
     * Creates a new character in the place.
     *
     * @param leader the clan leader
     * @return true if successful
     */
    private boolean createNewCharacter(ClanLeader leader) {
        view.displayMessage("Character creation not yet implemented.");
        return false;
    }

    /**
     * Heals all characters in the place.
     *
     * @param leader the clan leader
     * @return true if successful
     */
    private boolean healCharacters(ClanLeader leader) {
        boolean success = leader.healCharacters();
        if (success) {
            view.displayMessage("All characters have been healed!");
        }
        return success;
    }

    /**
     * Feeds all characters in the place.
     *
     * @param leader the clan leader
     * @return true if successful
     */
    private boolean feedCharacters(ClanLeader leader) {
        boolean success = leader.feedCharacters();
        if (success) {
            view.displayMessage("Characters have been fed!");
        } else {
            view.displayMessage("No food available!");
        }
        return success;
    }

    /**
     * Makes a magic potion.
     *
     * @param leader the clan leader
     * @return true if successful
     */
    private boolean makeMagicPotion(ClanLeader leader) {
        Potion potion = leader.requestMagicPotion();
        if (potion != null) {
            view.displayMessage("Magic potion created successfully!");
            view.displayMessage(potion.toString());
            return true;
        }
        return false;
    }

    /**
     * Gives a potion to a character.
     *
     * @param leader the clan leader
     * @return true if successful
     */
    private boolean givePotionToCharacter(ClanLeader leader) {
        List<AbstractCharacter> characters = leader.getPlace().getCharacters();

        if (characters.isEmpty()) {
            view.displayMessage("No characters available!");
            return false;
        }

        view.displayCharacterList(characters);
        System.out.print("Select character number: ");

        try {
            int index = scanner.nextInt() - 1;
            scanner.nextLine();

            if (index >= 0 && index < characters.size()) {
                AbstractCharacter character = characters.get(index);
                Potion potion = new Potion();
                boolean success = leader.givePotionToCharacter(potion, character);

                if (success) {
                    view.displayMessage(character.getName() + " drank the magic potion!");
                    return true;
                }
            }
        } catch (Exception e) {
            scanner.nextLine();
        }

        return false;
    }

    /**
     * Transfers a character to a battlefield or enclosure.
     *
     * @param leader the clan leader
     * @return true if successful
     */
    private boolean transferCharacter(ClanLeader leader) {
        List<AbstractCharacter> characters = leader.getPlace().getCharacters();

        if (characters.isEmpty()) {
            view.displayMessage("No characters to transfer!");
            return false;
        }

        // Get battlefields and enclosures
        List<AbstractPlace> validDestinations = theater.getPlaces().stream()
                .filter(p -> p instanceof Battlefield || p instanceof Enclosure)
                .toList();

        if (validDestinations.isEmpty()) {
            view.displayMessage("No valid destinations available!");
            return false;
        }

        view.displayCharacterList(characters);
        System.out.print("Select character to transfer: ");

        try {
            int charIndex = scanner.nextInt() - 1;
            scanner.nextLine();

            if (charIndex >= 0 && charIndex < characters.size()) {
                AbstractCharacter character = characters.get(charIndex);

                view.displayDestinationList(validDestinations);
                System.out.print("Select destination: ");

                int destIndex = scanner.nextInt() - 1;
                scanner.nextLine();

                if (destIndex >= 0 && destIndex < validDestinations.size()) {
                    AbstractPlace destination = validDestinations.get(destIndex);
                    boolean success = leader.transferCharacter(character, destination);

                    if (success) {
                        view.displayMessage(character.getName() + " transferred to " + destination.getName());
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            scanner.nextLine();
        }

        return false;
    }
}