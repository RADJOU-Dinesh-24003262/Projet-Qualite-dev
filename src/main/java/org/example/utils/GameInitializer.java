package org.example.utils;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.gallic.Druid;
import org.example.model.character.gallic.Gallic;
import org.example.model.character.roman.Legionary;
import org.example.model.character.werewolf.AgeCategory;
import org.example.model.character.werewolf.Werewolf;
import org.example.model.clanLeader.ClanLeader;
import org.example.model.colony.Colony;
import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.example.model.pack.Pack;
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
        Gallic asterix = new Gallic("Asterix", 35, 50, 100);
        Gallic obelix = new Gallic("Obelix", 36, 150, 200);
        Druid panoramix = new Druid("Panoramix", 65, 20, 80);
        ArrayList<AbstractCharacter> gauls = new ArrayList<>();
        gauls.add(asterix); gauls.add(obelix); gauls.add(panoramix);

        Legionary brutus = new Legionary("Brutus", 30, 60, 100);
        ArrayList<AbstractCharacter> romans = new ArrayList<>();
        romans.add(brutus);

        Werewolf alphaMale = new Werewolf("Fenrir (α)", 5, 80, 200);
        alphaMale.setSex(AbstractCharacter.Sex.MALE);
        alphaMale.setAgeCategory(AgeCategory.ADULT);


        Werewolf alphaFemale = new Werewolf("Luna (α)", 4, 75, 200);
        alphaFemale.setSex(AbstractCharacter.Sex.FEMALE);
        alphaFemale.setAgeCategory(AgeCategory.ADULT);


        Werewolf beta = new Werewolf("Remus (β)", 3, 60, 150);

        Werewolf omega = new Werewolf("Dobby (ω)", 2, 10, 100);

        ArrayList<Werewolf> packMembers = new ArrayList<>();
        packMembers.add(alphaMale);
        packMembers.add(alphaFemale);
        packMembers.add(beta);
        packMembers.add(omega);

        Pack mainPack = new Pack("La Meute du Clair de Lune", alphaMale, alphaFemale);
        mainPack.addWerewolf(beta, org.example.model.pack.Rank.BETA);
        mainPack.addWerewolf(omega, org.example.model.pack.Rank.OMEGA);

        Colony colony = new Colony("Colonie d'Armorique");
        colony.addPack(mainPack);


        // Places
        GallicVillage village = new GallicVillage("Village", "Gallic Village", 500, gauls, villageFood);
        RomanFortifiedCamp camp = new RomanFortifiedCamp("Camp", "Babaorum", 1000, romans, campFood);
        Enclosure enclosure = new Enclosure("Enclos", "Tanière des Loups", 200, packMembers, new ArrayList<>());
        Battlefield bf = new Battlefield("Battlefield", 5000, new ArrayList<>(), new ArrayList<>());

        places.add(village); places.add(camp); places.add(enclosure); places.add(bf);

        // Leaders
        ClanLeader l1 = new ClanLeader("Abraracourcix", ClanLeader.Sex.MALE, 50, village);
        ClanLeader l2 = new ClanLeader("Caius Bonus", ClanLeader.Sex.MALE, 45, camp);
        ClanLeader l3 = new ClanLeader("The Keeper", ClanLeader.Sex.MALE, 30, enclosure);

        leaders.add(l1); leaders.add(l2); leaders.add(l3);

        TheaterInvasion game = new TheaterInvasion("Armorica GUI", 10, places, leaders);
        game.setColony(colony);


        return game;
    }
}