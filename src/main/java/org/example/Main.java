package org.example;

import java.util.ArrayList;

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
 * Entry point for the Invasion Simulation.
 * Configures the world (Armorica), places, characters, and launches the temporal simulation.
 */
public class Main {
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
        Gallic asterix = new Gallic();
        asterix.setName("Asterix");
        asterix.setHealth(100);
        asterix.setStrength(50);
        asterix.setStamina(80);

        Gallic obelix = new Gallic();
        obelix.setName("Obelix");
        obelix.setHealth(200);
        obelix.setStrength(150);
        obelix.setStamina(100);

        Druid panoramix = new Druid();
        panoramix.setName("Getafix"); // English name for Panoramix
        panoramix.setHealth(80);
        panoramix.setStrength(10);

        ArrayList<AbstractCharacter> gauls = new ArrayList<>();
        gauls.add(asterix);
        gauls.add(obelix);
        gauls.add(panoramix);

        // Romans
        Legionary brutus = new Legionary();
        brutus.setName("Brutus");
        brutus.setHealth(100);
        brutus.setStrength(40);
        brutus.setStamina(50);

        Legionary minus = new Legionary();
        minus.setName("Minus");
        minus.setHealth(90);
        minus.setStrength(30);
        minus.setStamina(40);

        ArrayList<AbstractCharacter> romans = new ArrayList<>();
        romans.add(brutus);
        romans.add(minus);

        // Fantasy Creatures (Werewolves)
        Werewolf wolfie = new Werewolf("Wolfie");
        wolfie.setHealth(120);
        wolfie.setStrength(70);
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

        // Starts the main loop (Combats, Food, User Turn)
        armorica.run();
    }
}