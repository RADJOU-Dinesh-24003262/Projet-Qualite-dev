package org.example.model.theaterInvasion;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.gallic.Druid;
import org.example.model.character.gallic.Gallic;
import org.example.model.character.roman.Legionary;
import org.example.model.character.roman.Roman;
import org.example.model.character.werewolf.Werewolf;
import org.example.model.clanLeader.ClanLeader;
import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.example.model.places.AbstractPlace;
import org.example.model.places.Battlefield;
import org.example.model.places.Enclosure;
import org.example.model.potion.Potion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Manages the main logic of the "Theater Invasion" simulation.
 * <p>
 * This class acts as the game engine, controlling the flow of time,
 * the management of places and characters, and the interaction modes
 * (Turn-based vs. Real-time Simulation).
 * </p>
 */
public class TheaterInvasion {

    private String theaterName;
    private int maxPlaces;
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
     * @param maxPlaces       The maximum number of places allowed (if applicable).
     * @param existantsPlaces The list of initialized places (Villages, Camps, etc.).
     * @param clanLeaders     The list of Clan Leaders capable of performing actions.
     */
    public TheaterInvasion(String theaterName, int maxPlaces, ArrayList<AbstractPlace> existantsPlaces, ArrayList<ClanLeader> clanLeaders) {
        this.theaterName = theaterName;
        this.maxPlaces = maxPlaces;
        this.existantsPlaces = existantsPlaces;
        this.clanLeaders = clanLeaders;
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
    private void runGameCycle(int turn) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║        NOUVEAU CYCLE (Tour " + turn + ")        ║");
        System.out.println("╚════════════════════════════════════╝");

        handleCombats();
        updateCharactersState();
        spawnFood();
        rotFood();
    }

    /**
     * Starts the game in <strong>Turn-Based Mode</strong>.
     * <p>
     * In this mode, the game waits for user input (Enter key) to advance to the next turn.
     * The user is prompted to perform actions via Clan Leaders at the end of every automatic cycle.
     * </p>
     */
    public void runTurnBased() {
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);

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
                System.exit(0);
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
            System.out.println("❌ Entrée invalide.");
            return -1;
        }
    }

    /**
     * Displays the characteristics of all places in the theater.
     */
    public void displayPlaces() {
        System.out.println("--- Lieux dans le théâtre " + theaterName + " ---");
        for (AbstractPlace place : existantsPlaces) {
            place.displayCharacteristics();
        }
    }

    public ArrayList<AbstractPlace> getExistantsPlaces() {
        return existantsPlaces;
    }

    public String getTheaterName() {
        return theaterName;
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
        System.out.println(">> Bruits de bataille...");
        Random rand = new Random();

        // Use a copy of the list to avoid ConcurrentModificationException in threaded mode
        for (AbstractPlace place : new ArrayList<>(existantsPlaces)) {
            boolean isBattlefield = place instanceof Battlefield;
            int chanceOfFight = isBattlefield ? 80 : 30;

            if (rand.nextInt(100) < chanceOfFight) {
                List<AbstractCharacter> chars = new ArrayList<>(place.getPresentCharacters());

                if (chars.size() >= 2) {
                    AbstractCharacter c1 = chars.get(rand.nextInt(chars.size()));
                    AbstractCharacter c2 = chars.get(rand.nextInt(chars.size()));

                    if (c1 != c2 && c1.isAlive() && c2.isAlive()) {
                        System.out.println("⚔️ Combat à " + place.getName() + " : " + c1.getName() + " vs " + c2.getName());

                        c1.mutualFight(c2);

                        // Force damage for simulation dynamism
                        int dmg1 = rand.nextInt(11) + 5;
                        int dmg2 = rand.nextInt(11) + 5;
                        c1.setHealth(c1.getHealth() - dmg1);
                        c2.setHealth(c2.getHealth() - dmg2);

                        System.out.println("   -> " + c1.getName() + " prend " + dmg1 + " dégâts (PV: " + Math.max(0, c1.getHealth()) + ")");
                        System.out.println("   -> " + c2.getName() + " prend " + dmg2 + " dégâts (PV: " + Math.max(0, c2.getHealth()) + ")");

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
            System.out.println("💀 " + character.getName() + " est mort au combat.");
            currentPlace.deleteCharacter(character);
        } else if (isBattlefield) {
            AbstractPlace origin = character.getOriginPlace();
            if (origin != null && origin != currentPlace) {
                System.out.println("🏃 " + character.getName() + " fuit vers " + origin.getName());
                currentPlace.deleteCharacter(character);
                try {
                    origin.addCharacter(character);
                } catch (Exception e) {
                    // Ignore if return is impossible
                }
            }
        }
    }

    /**
     * Updates biological states of characters (Hunger, Potion effects).
     */
    private void updateCharactersState() {
        Random rand = new Random();
        for (AbstractPlace place : existantsPlaces) {
            for (AbstractCharacter c : new ArrayList<>(place.getPresentCharacters())) {
                if (rand.nextBoolean()) {
                    c.setHunger(c.getHunger() + 5);
                }
                if (c.getLevelMagicPotion() > 0) {
                    c.setLevelMagicPotion(Math.max(0, c.getLevelMagicPotion() - 5));
                }
            }
        }
    }

    /**
     * Ages food items and removes rotten ones.
     */
    private void rotFood() {
        System.out.println(">> Vieillissement de la nourriture...");
        for (AbstractPlace place : existantsPlaces) {
            Iterator<FoodItem> it = place.getPresentFoods().iterator();
            while (it.hasNext()) {
                FoodItem food = it.next();
                if (food.freshnessApplicable() && food.isFresh()) {
                    food.age();
                }
                if (food.freshnessApplicable() && !food.isFresh()) {
                    it.remove();
                }
            }
        }
    }

    /**
     * Randomly spawns new food items in non-battlefield locations.
     */
    private void spawnFood() {
        Random rand = new Random();
        FoodItemType[] allowedTypes = FoodItemType.values();

        for (AbstractPlace place : getExistantsPlaces()) {
            if (!(place instanceof Battlefield)) {
                if (rand.nextInt(100) < 30) {
                    FoodItemType randomType = allowedTypes[rand.nextInt(allowedTypes.length)];
                    FoodItem newItem = new FoodItem(randomType);
                    place.addFood(newItem);
                    System.out.println("🍎 Un " + newItem.getName() + " est apparu à " + place.getName());
                }
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

        System.out.println("\n>> 👑 GESTION DES CHEFS DE CLAN");
        for (int i = 0; i < clanLeaders.size(); i++) {
            System.out.println(i + ". " + clanLeaders.get(i).getName() + " (" + clanLeaders.get(i).getPlace().getName() + ")");
        }
        System.out.println("-1. Retour / Ne rien faire");
        System.out.print("Choix : ");

        int choice = safeReadInt(scanner);

        if (choice >= 0 && choice < clanLeaders.size()) {
            ClanLeader selectedLeader = clanLeaders.get(choice);
            menuLeaderAction(selectedLeader, scanner);
        } else {
            System.out.println("Aucune action effectuée.");
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
            System.out.println("\n--- Chef " + leader.getName() + ", quels sont vos ordres ? ---");
            System.out.println("1. 🔍 Examiner le lieu");
            System.out.println("2. ➕ Créer un nouveau personnage");
            System.out.println("3. 💚 Soigner les personnages");
            System.out.println("4. 🍖 Nourrir les personnages");
            System.out.println("5. 🧪 Demander une potion au Druide");
            System.out.println("6. 🍺 Donner une potion à un personnage");
            System.out.println("7. 🚚 Transférer un personnage");
            System.out.println("0. ↩️ Retour");
            System.out.print("Action : ");

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
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void performCreateCharacter(ClanLeader leader, Scanner scanner) {
        System.out.println("Quel type de personnage ?");
        System.out.println("1. Gaulois");
        System.out.println("2. Légionnaire Romain");
        System.out.println("3. Loup-Garou");
        System.out.print("Choix : ");
        int type = safeReadInt(scanner);
        System.out.print("Nom : ");
        String name = scanner.nextLine();

        switch (type) {
            case 1 -> leader.createGallicCharacter(name, Gallic.class);
            case 2 -> leader.createRomanCharacter(name, Legionary.class);
            case 3 -> leader.createWerewolf(name);
            default -> System.out.println("Type inconnu.");
        }
    }

    private void performFeedCharacters(ClanLeader leader) {
        AbstractPlace place = leader.getPlace();
        List<FoodItem> foods = place.getPresentFoods();
        if (foods.isEmpty()) {
            System.out.println("Pas de nourriture disponible.");
            return;
        }
        FoodItem food = foods.get(0);
        leader.feedAllCharacters(food);
        foods.remove(0);
    }

    private void performAskPotion(ClanLeader leader) {
        Druid druid = null;
        for (AbstractCharacter c : leader.getPlace().getPresentCharacters()) {
            if (c instanceof Druid) { druid = (Druid) c; break; }
        }
        if (druid == null) System.out.println("Pas de druide ici !");
        else leader.askDruidForPotion(druid);
    }

    private void performGivePotion(ClanLeader leader, Scanner scanner) {
        Druid druid = null;
        for (AbstractCharacter c : leader.getPlace().getPresentCharacters()) {
            if (c instanceof Druid) { druid = (Druid) c; break; }
        }
        if (druid == null) { System.out.println("Pas de druide !"); return; }

        System.out.println("À qui donner la potion ? (Entrez l'index)");
        List<AbstractCharacter> chars = leader.getPlace().getPresentCharacters();
        for(int i=0; i<chars.size(); i++) System.out.println(i + ". " + chars.get(i).getName());

        int choice = safeReadInt(scanner);
        if(choice >= 0 && choice < chars.size()) {
            Potion p = leader.askDruidForPotion(druid);
            leader.givePotionToCharacter(chars.get(choice), p);
        }
    }

    private void performTransfer(ClanLeader leader, Scanner scanner) {
        System.out.println("Qui transférer ? (Index)");
        List<AbstractCharacter> chars = leader.getPlace().getPresentCharacters();
        for(int i=0; i<chars.size(); i++) System.out.println(i + ". " + chars.get(i).getName());
        int charIdx = safeReadInt(scanner);
        if(charIdx < 0 || charIdx >= chars.size()) return;

        System.out.println("Vers où ?");
        List<AbstractPlace> dests = new ArrayList<>();
        for(AbstractPlace p : existantsPlaces) {
            if(p instanceof Battlefield || p instanceof Enclosure) dests.add(p);
        }
        for(int i=0; i<dests.size(); i++) System.out.println(i + ". " + dests.get(i).getName());
        int destIdx = safeReadInt(scanner);

        if(destIdx >= 0 && destIdx < dests.size()) {
            AbstractCharacter c = chars.get(charIdx);
            AbstractPlace d = dests.get(destIdx);
            if(d instanceof Battlefield) leader.transferToBattlefield(c, (Battlefield)d);
            else if(d instanceof Enclosure) leader.transferToEnclosure(c, (Enclosure)d);
        }
    }
}