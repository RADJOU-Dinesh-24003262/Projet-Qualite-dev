package org.example.model.theaterInvasion;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.gallic.Druid;
import org.example.model.character.gallic.Gallic;
import org.example.model.character.roman.Legionary;
import org.example.model.clanLeader.ClanLeader;
import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.example.model.places.AbstractPlace;
import org.example.model.places.Battlefield;
import org.example.model.places.Enclosure;
import org.example.model.potion.Potion;

/**
 * Manages the main logic of the "Theater Invasion" simulation.
 * <p>
 * This class acts as the game engine, controlling the flow of time,
 * the management of places and characters, and the interaction modes
 * (Turn-based vs. Real-time Simulation).
 * </p>
 */
public class TheaterInvasion {

    private static final Random RANDOM = new Random();
    private String theaterName;
    private ArrayList<AbstractPlace> existantsPlaces;
    private ArrayList<ClanLeader> clanLeaders;

    /**
     * Flag to control the main loop of the simulation thread.
     * Declared volatile to ensure visibility across threads.
     */
    private volatile boolean isSimulationRunning = true;

    /**
     * Flag to pause the logic execution within the simulation thread without stopping it.
     * Declared volatile to ensure visibility across threads.
     */
    private volatile boolean isSimulationPaused = false;

    /**
     * Constructs a new TheaterInvasion instance.
     *
     * @param theaterName     The name of the war theater (e.g., "Armorica").
     * @param maxPlaces       The maximum number of places allowed (kept for API compatibility).
     * @param existantsPlaces The list of initialized places (Villages, Camps, etc.).
     * @param clanLeaders     The list of Clan Leaders capable of performing actions.
     */
    @SuppressWarnings("PMD.UnusedFormalParameter")
    public TheaterInvasion(String theaterName, int maxPlaces, ArrayList<AbstractPlace> existantsPlaces, ArrayList<ClanLeader> clanLeaders) {
        this.theaterName = theaterName;
        // maxPlaces parameter kept for API compatibility but not stored
        this.existantsPlaces = new ArrayList<>(existantsPlaces);
        this.clanLeaders = new ArrayList<>(clanLeaders);
    }


    /**
     * Executes all automatic actions for a single game cycle.
     * <p>
     * This includes:
     * <ul>
     * <li>Handling random combats between characters in the same location.</li>
     * <li>Updating character states (hunger, potion effects).</li>
     * <li>Spawning new food items naturally.</li>
     * <li>Aging and rotting existing food items.</li>
     * </ul>
     * </p>
     *
     * @param turn The current turn number (for display purposes).
     */
    public void runGameCycle(int turn) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║        NOUVEAU CYCLE (Tour " + turn + ")        ║");
        System.out.println("╚════════════════════════════════════╝");

        handleCombats();
        updateCharactersState();
        spawnFood();
        rotFood();
    }

    // --- MODE 2 : SIMULATION (Temps réel avec Pause) ---

    /**
     * Starts the game in <strong>Turn-Based Mode</strong>.
     * <p>
     * In this mode, the game waits for user input (Enter key) to advance to the next turn.
     * The user is prompted to perform actions via Clan Leaders at the end of every automatic cycle.
     * </p>
     */
    public void runTurnBased() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        int turn = 0;

        System.out.println(">> MODE TOUR PAR TOUR ACTIVÉ.");

        while (true) {
            turn++;
            runGameCycle(turn); // Automatic logic
            handleUserTurn(scanner); // Mandatory user interaction

            System.out.println("... Fin du tour. Appuyez sur Entrée pour le tour suivant ...");
            scanner.nextLine();
        }
    }

    // --- MODE 2 : SIMULATION (Temps réel avec Pause) ---

    /**
     * Starts the game in <strong>Simulation Mode</strong>.
     * <p>
     * In this mode, a separate thread executes the game cycles automatically at a fixed interval.
     * The main thread listens for user input to toggle the PAUSE state.
     * When paused, the user can access the Clan Leader menu to perform actions.
     * </p>
     */
    public void runSimulation() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.println(">> MODE SIMULATION ACTIVÉ.");
        System.out.println(">> Le jeu tourne tout seul. Appuyez sur [ENTRÉE] à tout moment pour mettre en PAUSE.");

        // Launch a separate thread for game logic
        Thread gameThread = new Thread(() -> {
            int turn = 0;
            while (isSimulationRunning) {
                if (!isSimulationPaused) {
                    turn++;
                    runGameCycle(turn);

                    try {
                        // Simulation speed: 2 seconds per turn
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    // While paused, the thread sleeps briefly to save CPU resources
                    try { Thread.sleep(200); } catch (InterruptedException e) {}
                }
            }
        });

        gameThread.start();

        // The Main Thread listens to the keyboard for the Pause command
        while (isSimulationRunning) {
            // Blocks here until the user presses Enter
            scanner.nextLine();

            // Trigger Pause
            isSimulationPaused = true;

            // Small delay to let the display thread finish its current print
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            System.out.println("\n⏸️  SIMULATION EN PAUSE ⏸️");
            System.out.println("1. 🛠️ Faire des modifications (Menu Chefs de Clan)");
            System.out.println("2. ▶️ Reprendre la simulation");
            System.out.println("0. 🚪 Quitter le jeu");
            System.out.print("Choix : ");

            int choice = safeReadInt(scanner);

            if (choice == 1) {
                handleUserTurn(scanner); // Access standard menu
            } else if (choice == 0) {
                System.out.println("Arrêt du jeu...");
                isSimulationRunning = false;
                return; // Sort de la boucle et de la méthode proprement
            }

            System.out.println("▶️  Reprise de la simulation...");
            isSimulationPaused = false;
        }
    }

    /**
     * Safely reads an integer from the scanner, handling non-integer inputs.
     *
     * @param scanner The input scanner.
     * @return The parsed integer, or -1 if the input was invalid.
     */
    private int safeReadInt(Scanner scanner) {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // If it's not a number, return -1 (error code or invalid choice)
            System.out.println("❌ Invalid input. Please enter a number.");
            return -1;
        }
    }

    /**
     * Displays the characteristics of all places in the theater.
     */
    public void displayPlaces() {
        System.out.println("--- Places in Theater " + theaterName + " ---");
        for (AbstractPlace place : existantsPlaces) {
            place.displayCharacteristics();
        }
    }

    public ArrayList<AbstractPlace> getExistantsPlaces() {
        return new ArrayList<>(existantsPlaces);
    }

    public String getTheaterName() {
        return theaterName;
    }

    public ArrayList<ClanLeader> getClanLeaders() {
        return new ArrayList<>(clanLeaders);
    }

    /**
     * Handles random combat logic within each place.
     * <p>
     * Iterates through all places. If multiple characters are present,
     * a fight may occur based on a probability (higher in Battlefields).
     * Uses defensive copying to allow modification of character lists during iteration.
     * </p>
     */
    private void handleCombats() {
        System.out.println(">> Sounds of battle...");

        for (AbstractPlace place : existantsPlaces) {
            boolean isBattlefield = place instanceof Battlefield;
            // Slight increase in combat chances to make the simulation more dynamic
            int chanceOfFight = isBattlefield ? 80 : 30;

            if (RANDOM.nextInt(100) < chanceOfFight) {
                List<AbstractCharacter> chars = place.getPresentCharacters();

                if (chars.size() >= 2) {
                    AbstractCharacter c1 = chars.get(RANDOM.nextInt(chars.size()));
                    AbstractCharacter c2 = chars.get(RANDOM.nextInt(chars.size()));

                    // Ensure they don't fight themselves and are alive
                    if (c1 != c2 && c1.isAlive() && c2.isAlive()) {
                        System.out.println("⚔️ Fight in " + place.getName() + " : " + c1.getName() + " vs " + c2.getName());

                        // Call the existing combat method
                        c1.mutualFight(c2);

                        // --- COMBAT LOGIC CORRECTION ---
                        // Force damage here in case mutualFight didn't do it correctly.
                        // Assuming characters have getHealth/setHealth methods.
                        // Random damage between 5 and 15.
                        int dmg1 = RANDOM.nextInt(11) + 5;
                        int dmg2 = RANDOM.nextInt(11) + 5;

                        c1.setHealth(c1.getHealth() - dmg1);
                        c2.setHealth(c2.getHealth() - dmg2);

                        System.out.println("   -> " + c1.getName() + " takes " + dmg1 + " dmg (HP: " + Math.max(0, c1.getHealth()) + ")");
                        System.out.println("   -> " + c2.getName() + " takes " + dmg2 + " dmg (HP: " + Math.max(0, c2.getHealth()) + ")");
                        // ------------------------------------

                        handlePostFight(place, c1, isBattlefield);
                        handlePostFight(place, c2, isBattlefield);
                    }
                }
            }
        }
    }

    /**
     * Processes the aftermath of a fight for a specific character.
     * Handle death or fleeing (if on a battlefield).
     *
     * @param currentPlace  The place where the fight occurred.
     * @param character     The character involved.
     * @param isBattlefield True if the location is a designated battlefield.
     */
    private void handlePostFight(AbstractPlace currentPlace, AbstractCharacter character, boolean isBattlefield) {
        if (!character.isAlive()) {
            System.out.println("💀 " + character.getName() + " has passed away.");
            currentPlace.deleteCharacter(character);
        } else if (isBattlefield) {
            AbstractPlace origin = character.getOriginPlace();
            if (origin != null && origin != currentPlace) {
                System.out.println("🏃 " + character.getName() + " flees back to " + origin.getName());
                currentPlace.deleteCharacter(character);
                try {
                    origin.addCharacter(character);
                } catch (Exception e) {
                    System.out.println("   (But couldn't enter: " + e.getMessage() + ")");
                }
            }
        }
    }

    /**
     * Updates biological states of characters (Hunger, Potion effects).
     */
    private void updateCharactersState() {
        System.out.println(">> Time passes (Hunger increases, Potion fades)...");
        for (AbstractPlace place : existantsPlaces) {
            for (AbstractCharacter c : place.getPresentCharacters()) {
                if (RANDOM.nextBoolean()) {
                    c.setHunger(c.getHunger() + 5);
                }
                if (c.getLevelMagicPotion() > 0) {
                    c.setLevelMagicPotion(Math.max(0, c.getLevelMagicPotion() - 5));
                }
            }
        }
    }

    /**
     * Ages the food and removes expired food.
     * Added an iterator to avoid errors when removing elements within a loop.
     */
    private void rotFood() {
        System.out.println(">> Food is aging...");
        for (AbstractPlace place : existantsPlaces) {
            // Using an iterator to safely remove elements
            Iterator<FoodItem> it = place.getPresentFoods().iterator();
            while (it.hasNext()) {
                FoodItem food = it.next();
                if (food.freshnessApplicable() && food.isFresh()) {
                    food.age();
                }
                // If the food is no longer fresh, we can choose to remove it here
                // or just let it rot. For cleanliness, let's remove what is unusable.
                if (food.freshnessApplicable() && !food.isFresh()) {
                    System.out.println("   (Rotten " + food.getName() + " removed from " + place.getName() + ")");
                    it.remove();
                }
            }
        }
    }

    /**
     * Randomly spawns new food items in non-battlefield locations.
     */
    private void spawnFood() {
        System.out.println(">> Nature offers its gifts...");
        FoodItemType[] allowedTypes = FoodItemType.values();

        for (AbstractPlace place : existantsPlaces) {
            if (!(place instanceof Battlefield) && RANDOM.nextInt(100) < 30) {
                FoodItemType randomType = allowedTypes[RANDOM.nextInt(allowedTypes.length)];
                FoodItem newItem = new FoodItem(randomType);
                place.addFood(newItem);
                System.out.println("🍎 A " + newItem.getName() + " appeared in " + place.getName());
            }
        }
    }

    /**
     * Displays the menu for Clan Leaders to perform actions.
     * Used in both Turn-Based mode (every turn) and Simulation mode (on pause).
     *
     * @param scanner The input scanner.
     */
    private void handleUserTurn(Scanner scanner) {
        if (clanLeaders.isEmpty()) return;

        System.out.println("\n>> YOUR TURN! Choose a Clan Leader to control:");
        for (int i = 0; i < clanLeaders.size(); i++) {
            System.out.println(i + ". " + clanLeaders.get(i).getName() + " (" + clanLeaders.get(i).getPlace().getName() + ")");
        }
        System.out.println("-1. Skip turn");
        System.out.print("Choice: ");

        // Using the secure method
        int choice = safeReadInt(scanner);

        if (choice >= 0 && choice < clanLeaders.size()) {
            ClanLeader selectedLeader = clanLeaders.get(choice);
            menuLeaderAction(selectedLeader, scanner);
        } else {
            System.out.println("Turn skipped.");
        }
    }

    /**
     * Sub-menu for a specific Clan Leader's actions.
     *
     * @param leader  The selected leader.
     * @param scanner The input scanner.
     */
    private void menuLeaderAction(ClanLeader leader, Scanner scanner) {
        boolean acting = true;
        while (acting) {
            System.out.println("\n--- Chief " + leader.getName() + ", what is your command? ---");
            System.out.println("1. 🔍 Examine Place");
            System.out.println("2. ➕ Create New Character");
            System.out.println("3. 💚 Heal Characters");
            System.out.println("4. 🍖 Feed Characters");
            System.out.println("5. 🧪 Ask Druid for Potion");
            System.out.println("6. 🍺 Give Potion to Character");
            System.out.println("7. 🚚 Transfer Character");
            System.out.println("0. ↩️ Back / End Turn");
            System.out.print("Action: ");

            // Using the secure method
            int action = safeReadInt(scanner);

            switch (action) {
                case 1 -> leader.examinePlace();
                case 2 -> performCreateCharacter(leader, scanner);
                case 3 -> leader.healAllCharacters();
                case 4 -> performFeedCharacters(leader);
                case 5 -> performAskPotion(leader);
                case 6 -> performGivePotion(leader, scanner);
                case 7 -> performTransfer(leader, scanner);
                case 0 -> acting = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void performCreateCharacter(ClanLeader leader, Scanner scanner) {
        System.out.println("Type of character to create?");
        System.out.println("1. Gallic");
        System.out.println("2. Roman Legionary");
        System.out.println("3. Werewolf");
        System.out.print("Choice: ");

        // Using the secure method
        int type = safeReadInt(scanner);

        System.out.print("Name: ");
        String name = scanner.nextLine();

        switch (type) {
            case 1 -> leader.createGallicCharacter(name, Gallic.class);
            case 2 -> leader.createRomanCharacter(name, Legionary.class);
            case 3 -> leader.createWerewolf(name);
            default -> System.out.println("Unknown type.");
        }
    }

    /**
     * Corrected subroutine to feed characters.
     * <p>
     * Strictly checks if food is available.
     * If the list is empty, displays the message "No food available currently in the camp".
     * If food is present, feeds the characters, then removes the food item
     * from the list to avoid reusing it in the next turn.
     * </p>
     *
     * @param leader The clan leader ordering the meal.
     */
    private void performFeedCharacters(ClanLeader leader) {
        AbstractPlace place = leader.getPlace();
        List<FoodItem> foods = place.getPresentFoods();

        // CORRECTION: Strict verification and requested English message
        if (foods.isEmpty()) {
            System.out.println("No food available currently in the camp.");
            return;
        }

        // Pick the first available food
        FoodItem food = foods.get(0);

        // Perform the feed action
        leader.feedAllCharacters(food);

        // LOGIC CORRECTION: Once used, we consider the food consumed.
        // Here, we systematically remove it to avoid the "0 people fed" bug on next click
        // (Assuming feedAllCharacters consumes the entire stock of this item).
        foods.remove(0);
        System.out.println("(The food item " + food.getName() + " was consumed and removed from storage.)");
    }

    private void performAskPotion(ClanLeader leader) {
        Druid druid = null;
        for (AbstractCharacter c : leader.getPlace().getPresentCharacters()) {
            if (c instanceof Druid) {
                druid = (Druid) c;
                break;
            }
        }
        if (druid == null) {
            System.out.println("No Druid available to brew potion!");
        } else {
            Potion p = leader.askDruidForPotion(druid);
            if (p != null) {
                System.out.println("The Druid brewed a potion!");
            }
        }
    }

    private void performGivePotion(ClanLeader leader, Scanner scanner) {
        Druid druid = null;
        for (AbstractCharacter c : leader.getPlace().getPresentCharacters()) {
            if (c instanceof Druid) {
                druid = (Druid) c;
                break;
            }
        }

        if (druid == null) {
            System.out.println("No Druid to brew the potion first!");
            return;
        }

        System.out.println("Which character should drink?");
        List<AbstractCharacter> chars = leader.getPlace().getPresentCharacters();
        for (int i = 0; i < chars.size(); i++) {
            System.out.println(i + ". " + chars.get(i).getName());
        }

        System.out.print("Choice: ");
        // Using the secure method
        int choice = safeReadInt(scanner);

        if (choice >= 0 && choice < chars.size()) {
            Potion p = leader.askDruidForPotion(druid);
            leader.givePotionToCharacter(chars.get(choice), p);
        }
    }

    private void performTransfer(ClanLeader leader, Scanner scanner) {
        System.out.println("Transfer which character?");
        List<AbstractCharacter> chars = leader.getPlace().getPresentCharacters();
        for (int i = 0; i < chars.size(); i++) {
            System.out.println(i + ". " + chars.get(i).getName());
        }

        System.out.print("Choice: ");
        // Using the secure method
        int charChoice = safeReadInt(scanner);

        if (charChoice < 0 || charChoice >= chars.size()) return;
        AbstractCharacter character = chars.get(charChoice);

        System.out.println("Transfer to where?");
        List<AbstractPlace> destinations = new ArrayList<>();
        for (AbstractPlace p : existantsPlaces) {
            if (p instanceof Battlefield || p instanceof Enclosure) {
                destinations.add(p);
            }
        }

        for (int i = 0; i < destinations.size(); i++) {
            System.out.println(i + ". " + destinations.get(i).getName() + " (" + destinations.get(i).getType() + ")");
        }

        System.out.print("Choice: ");
        // Using the secure method
        int destChoice = safeReadInt(scanner);

        if (destChoice >= 0 && destChoice < destinations.size()) {
            AbstractPlace dest = destinations.get(destChoice);
            // Explicit casts to avoid compilation errors
            if (dest instanceof Battlefield) {
                leader.transferToBattlefield(character, (Battlefield) dest);
            } else if (dest instanceof Enclosure) {
                leader.transferToEnclosure(character, (Enclosure) dest);
            }
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        int turn = 0;

        while (true) {
            turn++;
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║        NEW CYCLE (Turn " + turn + ")        ║");
            System.out.println("╚════════════════════════════════════╝");

            handleCombats();
            updateCharactersState();
            spawnFood();
            rotFood();
            handleUserTurn(scanner);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}