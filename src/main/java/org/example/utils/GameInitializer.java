package org.example.utils;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.gallic.Druid;
import org.example.model.character.gallic.Gallic;
import org.example.model.character.roman.Legionary;
import org.example.model.character.werewolf.Werewolf;
import org.example.model.clanLeader.ClanLeader;
import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.example.model.places.*;
import org.example.model.theaterInvasion.TheaterInvasion;

import java.util.ArrayList;

/**
 * Initializes the game world with default data (Places, Characters, Leaders).
 */
public class GameInitializer {

    public static TheaterInvasion initialize() {
        ArrayList<AbstractPlace> places = new ArrayList<>();
        ArrayList<ClanLeader> leaders = new ArrayList<>();

        // Food
        ArrayList<FoodItem> villageFood = new ArrayList<>();
        villageFood.add(new FoodItem(FoodItemType.BOAR));
        villageFood.add(new FoodItem(FoodItemType.WINE));

        ArrayList<FoodItem> campFood = new ArrayList<>();
        campFood.add(new FoodItem(FoodItemType.MEAD));

        // Characters
        Gallic asterix = new Gallic(); asterix.setName("Asterix"); asterix.setHealth(100); asterix.setStrength(50);
        Gallic obelix = new Gallic(); obelix.setName("Obelix"); obelix.setHealth(200); obelix.setStrength(150);
        Druid panoramix = new Druid(); panoramix.setName("Panoramix"); panoramix.setHealth(80);
        ArrayList<AbstractCharacter> gauls = new ArrayList<>();
        gauls.add(asterix); gauls.add(obelix); gauls.add(panoramix);

        Legionary brutus = new Legionary(); brutus.setName("Brutus"); brutus.setHealth(100);
        ArrayList<AbstractCharacter> romans = new ArrayList<>();
        romans.add(brutus);

        Werewolf wolfie = new Werewolf("Wolfie"); wolfie.setHealth(120);
        ArrayList<Werewolf> werewolves = new ArrayList<>();
        werewolves.add(wolfie);

        // Places
        GallicVillage village = new GallicVillage("Village", "Gallic Village", 500, gauls, villageFood);
        RomanFortifiedCamp camp = new RomanFortifiedCamp("Camp", "Babaorum", 1000, romans, campFood);
        Enclosure enclosure = new Enclosure("Enclosure", "Wolf Enclosure", 200, werewolves, new ArrayList<>());
        Battlefield bf = new Battlefield("Battlefield", 5000, new ArrayList<>(), new ArrayList<>());

        places.add(village); places.add(camp); places.add(enclosure); places.add(bf);

        // Leaders
        ClanLeader l1 = new ClanLeader("Abraracourcix", ClanLeader.Sex.MALE, 50, village);
        ClanLeader l2 = new ClanLeader("Caius Bonus", ClanLeader.Sex.MALE, 45, camp);
        ClanLeader l3 = new ClanLeader("The Keeper", ClanLeader.Sex.MALE, 30, enclosure);

        leaders.add(l1); leaders.add(l2); leaders.add(l3);

        return new TheaterInvasion("Armorica GUI", 10, places, leaders);
    }
}