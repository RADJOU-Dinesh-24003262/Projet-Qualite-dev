package org.example;

import org.example.Controller.GameController;
import org.example.Model.ClanLeader;
import org.example.Model.Character.Gallic.*;
import org.example.Model.Character.Roman.*;
import org.example.Model.Character.Werewolf;
import org.example.Model.InvasionTheater;
import org.example.Model.Place.*;
import org.example.Model.Food.FoodItem;
import org.example.Model.Food.FoodItemType;

/**
 * Main entry point for the Invasion of Armorica simulation.
 * Initializes the game world and starts the simulation.
 */
public class GameMain {

    /**
     * Main method to start the game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create the invasion theater
        InvasionTheater armorica = new InvasionTheater("Armorica", 10);

        // Initialize places
        GallicVillage gallicVillage = initializeGallicVillage();
        RomanCamp romanCamp = initializeRomanCamp();
        RomanCity romanCity = initializeRomanCity();
        GalloRomanTown galloRomanTown = initializeGalloRomanTown();
        Battlefield battlefield1 = new Battlefield("Plains of Destiny", 5000);
        Battlefield battlefield2 = new Battlefield("Forest Clearing", 3000);
        Enclosure enclosure = new Enclosure("Creature Pen", 1000);

        // Add places to theater
        armorica.addPlace(gallicVillage);
        armorica.addPlace(romanCamp);
        armorica.addPlace(romanCity);
        armorica.addPlace(galloRomanTown);
        armorica.addPlace(battlefield1);
        armorica.addPlace(battlefield2);
        armorica.addPlace(enclosure);

        // Create clan leaders
        ClanLeader gallicLeader = new ClanLeader(
                "Vitalstatistix",
                ClanLeader.Sex.MALE,
                45,
                gallicVillage
        );

        ClanLeader romanLeader = new ClanLeader(
                "Centurion Marcus",
                ClanLeader.Sex.MALE,
                40,
                romanCamp
        );

        armorica.addClanLeader(gallicLeader);
        armorica.addClanLeader(romanLeader);

        // Create and start game controller
        GameController gameController = new GameController(armorica);

        // Display initial statistics
        System.out.println(armorica.displayPlaces());
        System.out.println(armorica.displayAllCharacters());

        // Start the game
        gameController.startGame();
    }

    /**
     * Initializes the Gallic village with characters and food.
     *
     * @return the initialized Gallic village
     */
    private static GallicVillage initializeGallicVillage() {
        GallicVillage village = new GallicVillage("Indomitable Gallic Village", 2000);

        // Create Gallic characters
        Druid druid = createDruid("Getafix", 60, 150, 15, 10);
        Blacksmith blacksmith = createBlacksmith("Fulliautomatix", 35, 200, 30, 25);
        Merchant merchant = createMerchant("Unhygienix", 40, 180, 20, 15);
        Innkeeper innkeeper = createInnkeeper("Mrs. Geriatrix", 50, 160, 18, 12);

        village.addCharacter(druid);
        village.addCharacter(blacksmith);
        village.addCharacter(merchant);
        village.addCharacter(innkeeper);

        // Add food
        village.addFood(new FoodItem(FoodItemType.BOAR));
        village.addFood(new FoodItem(FoodItemType.BOAR));
        village.addFood(new FoodItem(FoodItemType.FISH));
        village.addFood(new FoodItem(FoodItemType.WINE));

        return village;
    }

    /**
     * Initializes the Roman camp with characters and food.
     *
     * @return the initialized Roman camp
     */
    private static RomanCamp initializeRomanCamp() {
        RomanCamp camp = new RomanCamp("Camp Babaorum", 3000);

        // Create Roman characters
        General general = createGeneral("Julius Caesar", 45, 220, 35, 30);
        Legionary legionary1 = createLegionary("Legatus", 28, 180, 25, 20);
        Legionary legionary2 = createLegionary("Quintus", 30, 190, 27, 22);
        Prefect prefect = createPrefect("Praetorius", 38, 200, 30, 25);

        camp.addCharacter(general);
        camp.addCharacter(legionary1);
        camp.addCharacter(legionary2);
        camp.addCharacter(prefect);

        // Add food
        camp.addFood(new FoodItem(FoodItemType.BOAR));
        camp.addFood(new FoodItem(FoodItemType.HONEY));
        camp.addFood(new FoodItem(FoodItemType.WINE));
        camp.addFood(new FoodItem(FoodItemType.MEAD));

        return camp;
    }

    /**
     * Initializes the Roman city with characters and food.
     *
     * @return the initialized Roman city
     */
    private static RomanCity initializeRomanCity() {
        RomanCity city = new RomanCity("Lutetia", 5000);

        Prefect prefect = createPrefect("Governor Maximus", 50, 210, 28, 23);
        Legionary legionary = createLegionary("Guard Titus", 25, 170, 22, 18);

        city.addCharacter(prefect);
        city.addCharacter(legionary);

        city.addFood(new FoodItem(FoodItemType.HONEY));
        city.addFood(new FoodItem(FoodItemType.MEAD));

        return city;
    }

    /**
     * Initializes the Gallo-Roman town with characters.
     *
     * @return the initialized town
     */
    private static GalloRomanTown initializeGalloRomanTown() {
        GalloRomanTown town = new GalloRomanTown("Trading Post", 2500);

        Merchant merchant = createMerchant("Economix", 42, 170, 18, 14);
        Legionary legionary = createLegionary("Guard Flavius", 32, 185, 24, 19);

        town.addCharacter(merchant);
        town.addCharacter(legionary);

        town.addFood(new FoodItem(FoodItemType.BOAR));
        town.addFood(new FoodItem(FoodItemType.WINE));

        return town;
    }

    // Character creation helper methods

    private static Druid createDruid(String name, int age, int health, int strength, int stamina) {
        Druid druid = new Druid();
        initializeCharacter(druid, name, age, health, strength, stamina);
        return druid;
    }

    private static Blacksmith createBlacksmith(String name, int age, int health, int strength, int stamina) {
        Blacksmith blacksmith = new Blacksmith();
        initializeCharacter(blacksmith, name, age, health, strength, stamina);
        return blacksmith;
    }

    private static Merchant createMerchant(String name, int age, int health, int strength, int stamina) {
        Merchant merchant = new Merchant();
        initializeCharacter(merchant, name, age, health, strength, stamina);
        return merchant;
    }

    private static Innkeeper createInnkeeper(String name, int age, int health, int strength, int stamina) {
        Innkeeper innkeeper = new Innkeeper();
        initializeCharacter(innkeeper, name, age, health, strength, stamina);
        return innkeeper;
    }

    private static General createGeneral(String name, int age, int health, int strength, int stamina) {
        General general = new General();
        initializeCharacter(general, name, age, health, strength, stamina);
        return general;
    }

    private static Legionary createLegionary(String name, int age, int health, int strength, int stamina) {
        Legionary legionary = new Legionary();
        initializeCharacter(legionary, name, age, health, strength, stamina);
        return legionary;
    }

    private static Prefect createPrefect(String name, int age, int health, int strength, int stamina) {
        Prefect prefect = new Prefect();
        initializeCharacter(prefect, name, age, health, strength, stamina);
        return prefect;
    }

    private static void initializeCharacter(
            org.example.Model.Character.AbstractCharacter character,
            String name,
            int age,
            int health,
            int strength,
            int stamina
    ) {
        character.setName(name);
        character.setAge(age);
        character.setHealth(health);
        character.setStrength(strength);
        character.setStamina(stamina);
        character.setHunger(50);
        character.setBelligerence(30);
    }
}