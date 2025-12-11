package org.example;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.gallic.Druid;
import org.example.model.character.gallic.Gallic;
import org.example.model.character.roman.Legionary;
import org.example.model.character.werewolf.Werewolf;
import org.example.model.clanLeader.ClanLeader;
import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.example.model.places.AbstractPlace;
import org.example.model.places.Battlefield;
import org.example.model.places.Enclosure;
import org.example.model.places.GallicVillage;
import org.example.model.places.RomanFortifiedCamp;
import org.example.model.theaterInvasion.TheaterInvasion;

/**
 * Main entry point for the "Theater Invasion" application.
 * <p>
 * This class initializes the entire game state, including:
 * <ul>
 * <li>Characters (Gauls, Romans, Werewolves)</li>
 * <li>Places (Villages, Camps, Enclosures, Battlefields)</li>
 * <li>Initial food supplies</li>
 * <li>Clan Leaders</li>
 * </ul>
 * After initialization, it prompts the user to select the game mode
 * (Turn-Based or Simulation) and launches the engine.
 * </p>
 */
public class Main {

    /**
     * The main method.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // --- 1. Container Initialization ---
        ArrayList<AbstractPlace> places = new ArrayList<>();
        ArrayList<ClanLeader> leaders = new ArrayList<>();

        // --- 2. Initial Food Creation ---
        ArrayList<FoodItem> villageFood = new ArrayList<>();
        villageFood.add(new FoodItem(FoodItemType.BOAR));
        villageFood.add(new FoodItem(FoodItemType.BOAR));
        villageFood.add(new FoodItem(FoodItemType.WINE));
        villageFood.add(new FoodItem(FoodItemType.FISH));

        ArrayList<FoodItem> campFood = new ArrayList<>();
        campFood.add(new FoodItem(FoodItemType.BOAR));
        campFood.add(new FoodItem(FoodItemType.MEAD));
        campFood.add(new FoodItem(FoodItemType.HONEY));

        // --- 3. Character Creation ---

        // Gauls
        Gallic asterix = new Gallic("Asterix", 35, 50, 100);
        asterix.setStamina(80);

        Gallic obelix = new Gallic("Obelix", 36, 150, 200);
        obelix.setStamina(100);

        Druid panoramix = new Druid("Getafix", 65, 10, 80); // English name for Panoramix

        ArrayList<AbstractCharacter> gauls = new ArrayList<>();
        gauls.add(asterix);
        gauls.add(obelix);
        gauls.add(panoramix);

        // Romans
        Legionary brutus = new Legionary("Brutus", 30, 40, 100);
        brutus.setStamina(50);

        Legionary minus = new Legionary("Minus", 25, 30, 90);
        minus.setStamina(40);

        ArrayList<AbstractCharacter> romans = new ArrayList<>();
        romans.add(brutus);
        romans.add(minus);

        // Fantasy Creatures (Werewolves)
        Werewolf wolfie = new Werewolf("Wolfie", 5, 70, 120);
        wolfie.setStamina(60);

        ArrayList<Werewolf> werewolves = new ArrayList<>();
        werewolves.add(wolfie);

        // --- 4. Places Creation ---

        // Gallic Village
        GallicVillage village = new GallicVillage(
                "Vitalstatistix",
                "Village of the Indomitable",
                500,
                gauls,
                villageFood
        );
        places.add(village);

        // Roman Fortified Camp
        RomanFortifiedCamp camp = new RomanFortifiedCamp(
                "Caius Bonus",
                "Babaorum",
                1000,
                romans,
                campFood
        );
        places.add(camp);

        // Enclosure (for werewolves)
        Enclosure enclosure = new Enclosure(
                "The Keeper",
                "Wolf Enclosure",
                200,
                werewolves,
                new ArrayList<>()
        );
        places.add(enclosure);

        // Battlefield
        Battlefield battlefield = new Battlefield(
                "Plains of Armorica",
                5000,
                new ArrayList<>(),
                new ArrayList<>()
        );
        places.add(battlefield);

        // --- 5. Clan Leaders Creation (and assignment) ---

        ClanLeader vitalstatistix = new ClanLeader("Vitalstatistix", ClanLeader.Sex.MALE, 55, village);
        leaders.add(vitalstatistix);

        ClanLeader caiusBonus = new ClanLeader("Caius Bonus", ClanLeader.Sex.MALE, 45, camp);
        leaders.add(caiusBonus);

        ClanLeader keeper = new ClanLeader("The Keeper", ClanLeader.Sex.MALE, 30, enclosure);
        leaders.add(keeper);

        // --- 6. Theater of Invasion Initialization ---
        TheaterInvasion armorica = new TheaterInvasion("Armorica", 10, places, leaders);

        // --- 7. Start Simulation ---
        System.out.println("=========================================");
        System.out.println("   STARTING SIMULATION : " + armorica.getTheaterName());
        System.out.println("=========================================");

        armorica.displayPlaces();

        System.out.println("\nChoose the game mode:");
        System.out.println("1. 🔄 Turn-by-Turn Mode (Manual)");
        System.out.println("2. ⏩ Simulation Mode (Automatic with Pause)");
        System.out.print("Your choice: ");

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 1; // Default to turn-by-turn mode if input fails
        }

        if (choice == 2) {
            armorica.runSimulation();
        } else {
            armorica.runTurnBased();
        }

        scanner.close();

    }
}