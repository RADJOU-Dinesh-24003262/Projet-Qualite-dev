package org.example.model.theaterInvasion;

import org.example.model.character.gallic.Druid;
import org.example.model.character.gallic.Gallic;
import org.example.model.character.roman.Legionary;
import org.example.model.character.werewolf.Werewolf;
import org.example.model.clanLeader.ClanLeader;
import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.example.model.places.*;
import org.example.model.potion.Potion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests d'intégration pour le théâtre d'envahissement
 * Vérifie les interactions entre tous les composants du système
 */
class TheaterInvasionIntegrationTest {

    private TheaterInvasion theater;
    private GallicVillage gallicVillage;
    private RomanFortifiedCamp romanCamp;
    private Battlefield battlefield;
    private Enclosure enclosure;
    private ClanLeader gallicLeader;
    private ClanLeader romanLeader;

    @BeforeEach
    void setUp() {
        ArrayList<AbstractPlace> places = new ArrayList<>();
        ArrayList<ClanLeader> leaders = new ArrayList<>();

        // Créer village gaulois avec personnages
        Gallic asterix = new Gallic();
        asterix.setName("Asterix");
        asterix.setHealth(100);
        asterix.setStrength(30);
        asterix.setStamina(20);

        Druid panoramix = new Druid();
        panoramix.setName("Panoramix");
        panoramix.setHealth(80);
        panoramix.setStrength(10);

        ArrayList<org.example.model.character.AbstractCharacter> gauls = new ArrayList<>();
        gauls.add(asterix);
        gauls.add(panoramix);

        ArrayList<FoodItem> gallicFood = new ArrayList<>();
        gallicFood.add(new FoodItem(FoodItemType.BOAR));
        gallicFood.add(new FoodItem(FoodItemType.WINE));

        gallicVillage = new GallicVillage("Abraracourcix", "Village Gaulois", 1000,
                gauls, gallicFood);

        // Créer camp romain avec personnages
        Legionary brutus = new Legionary();
        brutus.setName("Brutus");
        brutus.setHealth(100);
        brutus.setStrength(25);
        brutus.setStamina(30);

        ArrayList<org.example.model.character.AbstractCharacter> romans = new ArrayList<>();
        romans.add(brutus);

        ArrayList<FoodItem> romanFood = new ArrayList<>();
        romanFood.add(new FoodItem(FoodItemType.MEAD));

        romanCamp = new RomanFortifiedCamp("Caius Bonus", "Babaorum", 2000,
                romans, romanFood);

        // Créer champ de bataille
        battlefield = new Battlefield("Plaines d'Armorique", 5000,
                new ArrayList<>(), new ArrayList<>());

        // Créer enclos pour loups-garous
        Werewolf wolfie = new Werewolf("Wolfie");
        wolfie.setHealth(120);
        wolfie.setStrength(40);

        ArrayList<Werewolf> werewolves = new ArrayList<>();
        werewolves.add(wolfie);

        enclosure = new Enclosure("Le Gardien", "Enclos des Loups", 500,
                werewolves, new ArrayList<>());

        // Ajouter les lieux
        places.add(gallicVillage);
        places.add(romanCamp);
        places.add(battlefield);
        places.add(enclosure);

        // Créer les chefs de clan
        gallicLeader = new ClanLeader("Abraracourcix", ClanLeader.Sex.MALE, 50, gallicVillage);
        romanLeader = new ClanLeader("Caius Bonus", ClanLeader.Sex.MALE, 45, romanCamp);

        leaders.add(gallicLeader);
        leaders.add(romanLeader);

        // Créer le théâtre
        theater = new TheaterInvasion("Armorique", 10, places, leaders);
    }

    // ========== TESTS DE STRUCTURE DU THÉÂTRE ==========

    @Test
    @DisplayName("Théâtre créé avec tous les lieux")
    void testTheaterCreationWithAllPlaces() {
        assertNotNull(theater);
        assertEquals("Armorique", theater.getTheaterName());
        assertEquals(4, theater.getExistantsPlaces().size());
    }

    @Test
    @DisplayName("Tous les types de lieux sont présents")
    void testAllPlaceTypesPresent() {
        ArrayList<AbstractPlace> places = theater.getExistantsPlaces();

        boolean hasGallicVillage = places.stream()
                .anyMatch(p -> p instanceof GallicVillage);
        boolean hasRomanCamp = places.stream()
                .anyMatch(p -> p instanceof RomanFortifiedCamp);
        boolean hasBattlefield = places.stream()
                .anyMatch(p -> p instanceof Battlefield);
        boolean hasEnclosure = places.stream()
                .anyMatch(p -> p instanceof Enclosure);

        assertTrue(hasGallicVillage);
        assertTrue(hasRomanCamp);
        assertTrue(hasBattlefield);
        assertTrue(hasEnclosure);
    }

    // ========== TESTS D'AFFICHAGE ==========

    @Test
    @DisplayName("Display places ne lance pas d'exception")
    void testDisplayPlaces() {
        assertDoesNotThrow(() -> theater.displayPlaces());
    }

    // ========== TESTS CHEF DE CLAN - CRÉATION PERSONNAGES ==========

    @Test
    @DisplayName("Chef gaulois crée un gaulois dans son village")
    void testGallicLeaderCreateCharacter() {
        int initialCount = gallicVillage.getNumberPresentCharacters();

        Gallic obelix = gallicLeader.createGallicCharacter("Obelix", Gallic.class);

        assertNotNull(obelix);
        assertEquals(initialCount + 1, gallicVillage.getNumberPresentCharacters());
        assertTrue(gallicVillage.getPresentCharacters().contains(obelix));
    }

    @Test
    @DisplayName("Chef romain crée un légionnaire dans son camp")
    void testRomanLeaderCreateCharacter() {
        int initialCount = romanCamp.getNumberPresentCharacters();

        Legionary minus = (Legionary) romanLeader.createRomanCharacter("Minus", Legionary.class);

        assertNotNull(minus);
        assertEquals(initialCount + 1, romanCamp.getNumberPresentCharacters());
        assertTrue(romanCamp.getPresentCharacters().contains(minus));
    }

    // ========== TESTS CHEF DE CLAN - SOINS ==========

    @Test
    @DisplayName("Chef gaulois soigne ses troupes")
    void testGallicLeaderHealsCharacters() {
        // Blesser un personnage
        Gallic asterix = (Gallic) gallicVillage.getPresentCharacters().get(0);
        asterix.setHealth(30);
        asterix.setStrength(20);

        int healthBefore = asterix.getHealth();
        gallicLeader.healAllCharacters();

        assertTrue(asterix.getHealth() > healthBefore);
    }

    // ========== TESTS CHEF DE CLAN - NOURRITURE ==========

    @Test
    @DisplayName("Chef gaulois nourrit ses troupes")
    void testGallicLeaderFeedsCharacters() {
        Gallic asterix = (Gallic) gallicVillage.getPresentCharacters().get(0);
        FoodItem boar = gallicVillage.getPresentFoods().get(0);

        int hungerBefore = asterix.getHunger();
        int foodCountBefore = gallicVillage.getNumberPresentFoods();

        gallicLeader.feedCharacter(asterix, boar);

        assertTrue(asterix.getHunger() > hungerBefore);
        assertEquals(foodCountBefore - 1, gallicVillage.getNumberPresentFoods());
    }

    // ========== TESTS CHEF DE CLAN - POTIONS ==========

    @Test
    @DisplayName("Chef gaulois demande potion au druide et la donne à un personnage")
    void testGallicLeaderGivesPotionToCharacter() {
        Druid panoramix = (Druid) gallicVillage.getPresentCharacters().stream()
                .filter(c -> c instanceof Druid)
                .findFirst()
                .orElse(null);

        assertNotNull(panoramix);

        Gallic asterix = (Gallic) gallicVillage.getPresentCharacters().stream()
                .filter(c -> c instanceof Gallic && !(c instanceof Druid))
                .findFirst()
                .orElse(null);

        assertNotNull(asterix);

        Potion potion = gallicLeader.askDruidForPotion(panoramix);
        assertNotNull(potion);

        int magicBefore = asterix.getLevelMagicPotion();
        gallicLeader.givePotionToCharacter(asterix, potion);

        assertTrue(asterix.getLevelMagicPotion() > magicBefore);
    }

    // ========== TESTS TRANSFERTS ==========

    @Test
    @DisplayName("Chef gaulois transfère un personnage au champ de bataille")
    void testTransferCharacterToBattlefield() {
        Gallic asterix = (Gallic) gallicVillage.getPresentCharacters().get(0);

        int villageCountBefore = gallicVillage.getNumberPresentCharacters();
        int battlefieldCountBefore = battlefield.getNumberPresentCharacters();

        gallicLeader.transferToBattlefield(asterix, battlefield);

        assertEquals(villageCountBefore - 1, gallicVillage.getNumberPresentCharacters());
        assertEquals(battlefieldCountBefore + 1, battlefield.getNumberPresentCharacters());
        assertTrue(battlefield.getPresentCharacters().contains(asterix));
    }

    @Test
    @DisplayName("Chef gaulois transfère un loup-garou à l'enclos")
    void testTransferWerewolfToEnclosure() {
        // Créer un loup-garou dans le village
        Werewolf werewolf = gallicLeader.createWerewolf("NewWolf");
        assertNotNull(werewolf);

        int villageCountBefore = gallicVillage.getNumberPresentCharacters();
        int enclosureCountBefore = enclosure.getNumberPresentCharacters();

        gallicLeader.transferToEnclosure(werewolf, enclosure);

        assertEquals(villageCountBefore - 1, gallicVillage.getNumberPresentCharacters());
        assertEquals(enclosureCountBefore + 1, enclosure.getNumberPresentCharacters());
        assertTrue(enclosure.getPresentCharacters().contains(werewolf));
    }

    // ========== TESTS COMBAT ==========

    @Test
    @DisplayName("Combat entre deux personnages réduit leur santé")
    void testCombatBetweenCharacters() {
        // Transférer des personnages au champ de bataille
        Gallic asterix = (Gallic) gallicVillage.getPresentCharacters().get(0);
        Legionary brutus = (Legionary) romanCamp.getPresentCharacters().get(0);

        gallicLeader.transferToBattlefield(asterix, battlefield);
        romanLeader.transferToBattlefield(brutus, battlefield);

        int asterixHealthBefore = asterix.getHealth();
        int brutusHealthBefore = brutus.getHealth();

        // Combat
        asterix.mutualFight(brutus);

        assertTrue(asterix.getHealth() <= asterixHealthBefore);
        assertTrue(brutus.getHealth() <= brutusHealthBefore);
    }

    // ========== TESTS CYCLE COMPLET ==========

    @Test
    @DisplayName("Scénario complet: création, nourrissage, combat, soins")
    void testCompleteScenario() {
        // 1. Créer un nouveau personnage
        Gallic obelix = gallicLeader.createGallicCharacter("Obelix", Gallic.class);
        assertNotNull(obelix);

        // 2. Blesser le personnage
        obelix.setHealth(50);

        // 3. Nourrir le personnage
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        gallicVillage.addFood(boar);
        gallicLeader.feedCharacter(obelix, boar);
        assertTrue(obelix.getHunger() > 0);

        // 4. Soigner le personnage
        gallicLeader.healCharacter(obelix);
        assertTrue(obelix.getHealth() > 50);

        // 5. Transférer au champ de bataille
        gallicLeader.transferToBattlefield(obelix, battlefield);
        assertTrue(battlefield.getPresentCharacters().contains(obelix));

        // 6. Combat
        Legionary brutus = (Legionary) romanCamp.getPresentCharacters().get(0);
        romanLeader.transferToBattlefield(brutus, battlefield);

        int obelixHealthBefore = obelix.getHealth();
        obelix.mutualFight(brutus);
        assertTrue(obelix.getHealth() <= obelixHealthBefore);
    }

    // ========== TESTS MULTI-LIEUX ==========

    @Test
    @DisplayName("Interactions entre plusieurs lieux simultanément")
    void testMultiPlaceInteractions() {
        // Actions dans le village gaulois
        Gallic newGallic = gallicLeader.createGallicCharacter("NewGallic", Gallic.class);
        assertNotNull(newGallic);

        // Actions dans le camp romain
        Legionary newRoman = (Legionary) romanLeader.createRomanCharacter("NewRoman", Legionary.class);
        assertNotNull(newRoman);

        // Transferts vers le champ de bataille
        gallicLeader.transferToBattlefield(newGallic, battlefield);
        romanLeader.transferToBattlefield(newRoman, battlefield);

        // Vérifier que les deux sont sur le champ de bataille
        assertEquals(2, battlefield.getNumberPresentCharacters());
        assertTrue(battlefield.getPresentCharacters().contains(newGallic));
        assertTrue(battlefield.getPresentCharacters().contains(newRoman));
    }

    // ========== TESTS VALIDATION RÈGLES MÉTIER ==========

    @Test
    @DisplayName("Village gaulois refuse les romains (règle métier)")
    void testGallicVillageRejectsRomans() {
        Legionary roman = (Legionary) romanCamp.getPresentCharacters().get(0);

        assertThrows(IllegalArgumentException.class,
                () -> gallicVillage.addCharacter(roman));
    }

    @Test
    @DisplayName("Enclos accepte uniquement loups-garous (règle métier)")
    void testEnclosureAcceptsOnlyWerewolves() {
        Gallic gallic = (Gallic) gallicVillage.getPresentCharacters().get(0);

        assertThrows(IllegalArgumentException.class,
                () -> enclosure.addCharacter(gallic));
    }

    @Test
    @DisplayName("Champ de bataille accepte tous les types (règle métier)")
    void testBattlefieldAcceptsAll() {
        Gallic gallic = (Gallic) gallicVillage.getPresentCharacters().get(0);
        Legionary roman = (Legionary) romanCamp.getPresentCharacters().get(0);
        Werewolf werewolf = (Werewolf) enclosure.getPresentCharacters().get(0);

        assertDoesNotThrow(() -> battlefield.addCharacter(gallic));
        assertDoesNotThrow(() -> battlefield.addCharacter(roman));
        assertDoesNotThrow(() -> battlefield.addCharacter(werewolf));
    }

    // ========== TESTS COHÉRENCE DES DONNÉES ==========

    @Test
    @DisplayName("Nombre total de personnages cohérent après opérations")
    void testTotalCharacterCountConsistency() {
        int totalBefore = gallicVillage.getNumberPresentCharacters()
                + romanCamp.getNumberPresentCharacters()
                + battlefield.getNumberPresentCharacters()
                + enclosure.getNumberPresentCharacters();

        // Créer des personnages
        gallicLeader.createGallicCharacter("New1", Gallic.class);
        romanLeader.createRomanCharacter("New2", Legionary.class);

        int totalAfter = gallicVillage.getNumberPresentCharacters()
                + romanCamp.getNumberPresentCharacters()
                + battlefield.getNumberPresentCharacters()
                + enclosure.getNumberPresentCharacters();

        assertEquals(totalBefore + 2, totalAfter);
    }

    @Test
    @DisplayName("Transfert ne perd pas de personnages")
    void testTransferDoesNotLoseCharacters() {
        Gallic asterix = (Gallic) gallicVillage.getPresentCharacters().get(0);

        int totalBefore = gallicVillage.getNumberPresentCharacters()
                + battlefield.getNumberPresentCharacters();

        gallicLeader.transferToBattlefield(asterix, battlefield);

        int totalAfter = gallicVillage.getNumberPresentCharacters()
                + battlefield.getNumberPresentCharacters();

        assertEquals(totalBefore, totalAfter);
    }

    // ========== TESTS NOURRITURE ==========

    @Test
    @DisplayName("Vieillissement de la nourriture")
    void testFoodAging() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        gallicVillage.addFood(fish);

        assertTrue(fish.isFresh());

        fish.age();

        assertFalse(fish.isFresh());
    }

    @Test
    @DisplayName("Personnage ne peut pas manger nourriture incompatible")
    void testCharacterCannotEatIncompatibleFood() {
        Gallic gallic = (Gallic) gallicVillage.getPresentCharacters().get(0);
        FoodItem honey = new FoodItem(FoodItemType.HONEY);

        assertFalse(gallic.canEat(honey));
    }

    // ========== TESTS ROBUSTESSE ==========

    @Test
    @DisplayName("Système reste stable après multiples opérations")
    void testSystemStabilityAfterMultipleOperations() {
        // Exécuter de nombreuses opérations
        for (int i = 0; i < 10; i++) {
            gallicLeader.createGallicCharacter("Gallic" + i, Gallic.class);
            romanLeader.createRomanCharacter("Roman" + i, Legionary.class);
        }

        // Vérifier que le système est toujours cohérent
        assertDoesNotThrow(() -> theater.displayPlaces());

        assertTrue(gallicVillage.getNumberPresentCharacters() >= 2);
        assertTrue(romanCamp.getNumberPresentCharacters() >= 1);
    }

    @Test
    @DisplayName("Gestion correcte des listes vides")
    void testHandlingOfEmptyLists() {
        // Créer un lieu vide
        GallicVillage emptyVillage = new GallicVillage("Chief", "Empty", 1000,
                new ArrayList<>(), new ArrayList<>());

        assertEquals(0, emptyVillage.getNumberPresentCharacters());
        assertEquals(0, emptyVillage.getNumberPresentFoods());

        assertDoesNotThrow(() -> emptyVillage.displayCharacteristics());
    }

    // ========== TEST CONFORMITÉ SPÉCIFICATIONS TD3 ==========

    @Test
    @DisplayName("Conformité avec les spécifications du TD3")
    void testComplianceWithTD3Specifications() {
        // Le théâtre doit avoir:
        // - un nom
        assertNotNull(theater.getTheaterName());

        // - des lieux avec des types différents
        assertTrue(theater.getExistantsPlaces().size() >= 4);

        // - des chefs de clan
        assertNotNull(gallicLeader);
        assertNotNull(romanLeader);

        // Les lieux doivent respecter les règles d'occupation
        // (déjà testé dans les autres tests)

        // Les personnages doivent avoir les caractéristiques requises
        Gallic gallic = (Gallic) gallicVillage.getPresentCharacters().get(0);
        assertNotNull(gallic.getName());
        assertTrue(gallic.getHealth() > 0);
        assertTrue(gallic.getStrength() > 0);

        // Les druides doivent pouvoir créer des potions
        Druid druid = (Druid) gallicVillage.getPresentCharacters().stream()
                .filter(c -> c instanceof Druid)
                .findFirst()
                .orElse(null);
        assertNotNull(druid);

        Potion potion = gallicLeader.askDruidForPotion(druid);
        assertNotNull(potion);
    }
}