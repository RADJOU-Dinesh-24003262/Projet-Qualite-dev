package org.example.model.theaterInvasion;

import org.example.model.Character.AbstractCharacter;
import org.example.model.clanLeader.ClanLeader;
import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.example.model.places.AbstractPlace;
import org.example.model.places.Battlefield;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents the "Theater of Invasion" (The Armorica region).
 * This class acts as the main engine for the simulation.
 * It manages the timeline, the places, the characters, and the global events (combats, food spawning, etc.).
 *
 * <p>Refers to section 7 of the specifications.</p>
 */
public class TheaterInvasion {

    private String theaterName;
    private int maxPlaces;
    private ArrayList<AbstractPlace> existantsPlaces;
    private ArrayList<ClanLeader> clanLeaders;

    /**
     * Constructs a new Theater of Invasion.
     *
     * @param theaterName     The name of the theater (e.g., "Armorica").
     * @param maxPlaces       The maximum number of places allowed.
     * @param existantsPlaces The list of initialized places.
     * @param clanLeaders     The list of clan leaders managing the places.
     */
    public TheaterInvasion(String theaterName, int maxPlaces, ArrayList<AbstractPlace> existantsPlaces, ArrayList<ClanLeader> clanLeaders) {
        this.theaterName = theaterName;
        this.maxPlaces = maxPlaces;
        this.existantsPlaces = existantsPlaces;
        this.clanLeaders = clanLeaders;
    }

    /**
     * Displays all places within the theater and their characteristics.
     */
    public void displayPlaces() {
        System.out.println("--- Lieux du Théâtre " + theaterName + " ---");
        for (AbstractPlace place : existantsPlaces) {
            place.displayCharacteristics();
        }
    }

    /**
     * Calculates the total number of characters present in the entire theater.
     *
     * @return The total count of characters.
     */
    public int getNumberPresentCharacter() {
        int total = 0;
        for (AbstractPlace place : existantsPlaces) {
            total += place.getNumberPresentCharacters();
        }
        return total;
    }

    /**
     * Retrieves a list of all characters present in the theater, across all places.
     *
     * @return An ArrayList containing every AbstractCharacter in the simulation.
     */
    public ArrayList<AbstractCharacter> getAllCharactersInTheater() {
        ArrayList<AbstractCharacter> allCharacters = new ArrayList<>();
        for (AbstractPlace place : existantsPlaces) {
            allCharacters.addAll(place.getPresentCharacters());
        }
        return allCharacters;
    }

    // --- Getters and Setters ---

    public int getMaxPlaces() {
        return maxPlaces;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public ArrayList<AbstractPlace> getExistantsPlaces() {
        return existantsPlaces;
    }

    public ArrayList<ClanLeader> getClanLeaders() {
        return clanLeaders;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public void setMaxPlaces(int maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public void setExistantsPlaces(ArrayList<AbstractPlace> existantsPlaces) {
        this.existantsPlaces = existantsPlaces;
    }

    public void setClanLeaders(ArrayList<ClanLeader> clanLeaders) {
        this.clanLeaders = clanLeaders;
    }

    /**
     * Handles the combat phase of the simulation cycle.
     * Iterates through places and triggers fights based on probability.
     * Battlefields have a higher chance of combat than regular places.
     */
    private void handleCombats() {
        System.out.println(">> Bruits de bataille...");
        Random rand = new Random();

        for (AbstractPlace place : getExistantsPlaces()) {
            // Determine if the place is a battlefield using instanceof
            boolean isBattlefield = place instanceof Battlefield;

            // Combat probability: 80% for Battlefields, 20% for others
            int chanceOfFight = isBattlefield ? 80 : 20;

            // Attempt to trigger a fight
            if (rand.nextInt(100) < chanceOfFight) {
                List<AbstractCharacter> chars = place.getPresentCharacters();

                // Need at least 2 characters to fight
                if (chars.size() >= 2) {
                    AbstractCharacter c1 = chars.get(rand.nextInt(chars.size()));
                    AbstractCharacter c2 = chars.get(rand.nextInt(chars.size()));

                    // Ensure characters are different and alive
                    if (c1 != c2 && c1.isAlive() && c2.isAlive()) {
                        System.out.println("Combat dans " + place.getName() + " : " + c1.getName() + " vs " + c2.getName());

                        // Characters fight each other
                        c1.mutualFight(c2);

                        // Handle the aftermath (death or retreat)
                        handlePostFight(place, c1, isBattlefield);
                        handlePostFight(place, c2, isBattlefield);
                    }
                }
            }
        }
    }

    /**
     * Handles the outcome of a fight for a specific character.
     * If the character died, they are removed.
     * If they survived on a Battlefield, they retreat to their origin place.
     *
     * @param currentPlace  The place where the fight happened.
     * @param character     The character involved.
     * @param isBattlefield True if the current place is a battlefield.
     */
    private void handlePostFight(AbstractPlace currentPlace, AbstractCharacter character, boolean isBattlefield) {
        if (!character.isAlive()) {
            // Case 1: Character died
            System.out.println(character.getName() + " a trépassé.");
            currentPlace.deleteCharacter(character);
        } else if (isBattlefield) {
            // Case 2: Character survived on a battlefield -> Return to origin
            AbstractPlace origin = character.getOriginPlace();
            if (origin != null) {
                System.out.println(character.getName() + " fuit vers " + origin.getName());
                currentPlace.deleteCharacter(character); // Remove from battlefield
                origin.addCharacter(character);          // Add back to home
            } else {
                // Edge case: Character has nowhere to go
                System.out.println(character.getName() + " a survécu mais ne sait pas où rentrer !");
            }
        }
    }

    /**
     * Randomly updates the state of characters (hunger, magic potion duration).
     */
    private void updateCharactersState() {
        System.out.println(">> Le temps passe (Faim +, Potion -)...");
        Random rand = new Random();
        for (AbstractPlace place : existantsPlaces) {
            for (AbstractCharacter c : place.getPresentCharacters()) {
                // 50% chance to increase hunger
                if (rand.nextBoolean()) {
                    c.setHunger(c.getHunger() + 5);
                }
                // Decrease magic potion level if active
                if (c.getLevelMagicPotion() > 0) {
                    c.setLevelMagicPotion(Math.max(0, c.getLevelMagicPotion() - 5));
                }
            }
        }
    }

    /**
     * Ages the food present in the places.
     * Fresh food becomes "Passably Fresh", then "Not Fresh".
     */
    private void rotFood() {
        System.out.println(">> Les aliments vieillissent...");
        for (AbstractPlace place : existantsPlaces) {
            for (FoodItem food : place.getPresentFoods()) {
                // Only age food if freshness is applicable (e.g., Meat, Fish)
                if (food.freshnessApplicable() && food.isFresh()) {
                    food.age();
                }
            }
        }
    }

    /**
     * Spawns new food items in random places (excluding Battlefields).
     */
    private void spawnFood() {
        System.out.println(">> La nature offre ses dons...");
        Random rand = new Random();

        // Retrieve all available food types from the Enum
        FoodItemType[] allowedTypes = FoodItemType.values();

        for (AbstractPlace place : getExistantsPlaces()) {

            // Food does not spawn on battlefields
            if (!(place instanceof Battlefield)) {

                // 30% chance for food to spawn in this place
                if (rand.nextInt(100) < 30) {

                    // 1. Pick a random type
                    FoodItemType randomType = allowedTypes[rand.nextInt(allowedTypes.length)];

                    // 2. Create the food item
                    FoodItem newItem = new FoodItem(randomType);

                    // 3. Add to place
                    place.addFood(newItem);

                    System.out.println("Un(e) " + newItem.getName() + " est apparu(e) à " + place.getName());
                }
            }
        }
    }

    /**
     * Manages the user interaction turn.
     * Allows the user to select a Clan Leader and perform actions.
     *
     * @param scanner The input scanner.
     */
    private void handleUserTurn(Scanner scanner) {
        if (clanLeaders.isEmpty()) return;

        System.out.println("\n>> A VOUS DE JOUER ! Choisissez un chef de clan :");
        for (int i = 0; i < clanLeaders.size(); i++) {
            System.out.println(i + ". " + clanLeaders.get(i).getName() + " (" + clanLeaders.get(i).getPlace().getName() + ")");
        }
        System.out.print("Choix : ");

        int choice = -1;
        try {
            if (scanner.hasNextInt()) choice = scanner.nextInt();
        } catch (Exception e) {
            scanner.next(); // Consume invalid input
        }

        if (choice >= 0 && choice < clanLeaders.size()) {
            ClanLeader selectedLeader = clanLeaders.get(choice);
            menuLeaderAction(selectedLeader, scanner);
        }
    }

    /**
     * Displays the action menu for a specific Clan Leader.
     *
     * @param leader  The selected Clan Leader.
     * @param scanner The input scanner.
     */
    private void menuLeaderAction(ClanLeader leader, Scanner scanner) {
        System.out.println("\nChef " + leader.getName() + ", que voulez-vous faire ?");
        System.out.println("1. Examiner le lieu [cite: 86]");
        System.out.println("2. Soigner les personnages [cite: 88]");
        System.out.println("3. Passer le tour");
        System.out.print("Action : ");

        int action = -1;
        try {
            if (scanner.hasNextInt()) action = scanner.nextInt();
        } catch (Exception e) {
            scanner.next();
        }

        switch (action) {
            case 1 -> leader.examinePlace();
            case 2 -> leader.healAllCharacters();
            default -> System.out.println("Vous vous reposez.");
        }
    }

    /**
     * The main simulation loop.
     * Controls the flow of time and executes phases in order.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int turn = 0;

        while (true) {
            turn++;
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║       NOUVEAU CYCLE (Tour " + turn + ")      ║");
            System.out.println("╚════════════════════════════════════╝");

            // 1. Combat Phase
            handleCombats();

            // 2. Character State Update Phase (Hunger/Magic)
            updateCharactersState();

            // 3. Food Spawning Phase
            spawnFood();

            // 4. Food Rotting Phase
            rotFood();

            // 5. User/Clan Leader Turn
            handleUserTurn(scanner);

            // Simulation delay for readability
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}