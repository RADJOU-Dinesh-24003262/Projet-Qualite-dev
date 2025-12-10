package org.example.model.clanLeader;

import org.example.model.Character.gallic.Druid;
import org.example.model.Character.gallic.Gallic;
import org.example.model.Character.roman.Legionary;
import org.example.model.Character.roman.Roman;
import org.example.model.Character.werewolf.Werewolf;
import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.example.model.places.Battlefield;
import org.example.model.places.Enclosure;
import org.example.model.places.GallicVillage;
import org.example.model.places.RomanCity;
import org.example.model.potion.Potion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClanLeaderTest {

    private ClanLeader clanLeader;
    private GallicVillage village;
    private ArrayList<FoodItem> foods;

    @BeforeEach
    void setUp() {
        foods = new ArrayList<>();
        foods.add(new FoodItem(FoodItemType.BOAR));
        foods.add(new FoodItem(FoodItemType.FISH));
        foods.add(new FoodItem(FoodItemType.WINE));

        village = new GallicVillage("TestChief", "Test Village", 1000, 
                                    new ArrayList<>(), foods);
        
        clanLeader = new ClanLeader("Vitalstatistix", ClanLeader.Sex.MALE, 45, village);
    }

    // ========== TESTS DE CONSTRUCTION ==========

    @Test
    @DisplayName("Construction valide d'un clan leader")
    void testValidConstruction() {
        assertNotNull(clanLeader);
        assertEquals("Vitalstatistix", clanLeader.getName());
        assertEquals(ClanLeader.Sex.MALE, clanLeader.getSex());
        assertEquals(45, clanLeader.getAge());
        assertEquals(village, clanLeader.getPlace());
    }

    @Test
    @DisplayName("Construction avec nom null lance exception")
    void testConstructionWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> 
            new ClanLeader(null, ClanLeader.Sex.MALE, 45, village));
    }

    @Test
    @DisplayName("Construction avec nom vide lance exception")
    void testConstructionWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> 
            new ClanLeader("", ClanLeader.Sex.MALE, 45, village));
    }

    @Test
    @DisplayName("Construction avec sexe null lance exception")
    void testConstructionWithNullSex() {
        assertThrows(IllegalArgumentException.class, () -> 
            new ClanLeader("Test", null, 45, village));
    }

    @Test
    @DisplayName("Construction avec âge négatif lance exception")
    void testConstructionWithNegativeAge() {
        assertThrows(IllegalArgumentException.class, () -> 
            new ClanLeader("Test", ClanLeader.Sex.MALE, -1, village));
    }

    @Test
    @DisplayName("Construction avec place null lance exception")
    void testConstructionWithNullPlace() {
        assertThrows(IllegalArgumentException.class, () -> 
            new ClanLeader("Test", ClanLeader.Sex.MALE, 45, null));
    }

    // ========== TESTS DE CRÉATION DE PERSONNAGES ==========

    @Test
    @DisplayName("Création d'un personnage Gallic réussie")
    void testCreateGallicCharacter() {
        Gallic gallic = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        
        assertNotNull(gallic);
        assertEquals("Asterix", gallic.getName());
        assertTrue(village.getPresentCharacters().contains(gallic));
        assertEquals(1, village.getNumberPresentCharacters());
    }

    @Test
    @DisplayName("Création d'un Druide réussie")
    void testCreateDruid() {
        Druid druid = (Druid) clanLeader.createGallicCharacter("Panoramix", Druid.class);
        
        assertNotNull(druid);
        assertEquals("Panoramix", druid.getName());
        assertInstanceOf(Druid.class, druid);
        assertTrue(village.getPresentCharacters().contains(druid));
    }

    @Test
    @DisplayName("Création d'un Werewolf réussie")
    void testCreateWerewolf() {
        Werewolf werewolf = clanLeader.createWerewolf("Fenrir");
        
        assertNotNull(werewolf);
        assertEquals("Fenrir", werewolf.getName());
        assertTrue(village.getPresentCharacters().contains(werewolf));
    }

    @Test
    @DisplayName("Création de personnage Roman dans village Gallic échoue")
    void testCreateRomanInGallicVillage() {
        Roman roman = clanLeader.createRomanCharacter("Julius", Legionary.class);
        
        assertNull(roman);
        assertEquals(0, village.getNumberPresentCharacters());
    }

    @Test
    @DisplayName("Création de personnage Roman dans ville Romaine réussie")
    void testCreateRomanInRomanCity() {
        RomanCity romanCity = new RomanCity("Caesar", "Rome", 5000, 
                                           new ArrayList<>(), new ArrayList<>());
        ClanLeader romanLeader = new ClanLeader("Caesar", ClanLeader.Sex.MALE, 50, romanCity);
        
        Roman roman = romanLeader.createRomanCharacter("Julius", Legionary.class);
        
        assertNotNull(roman);
        assertEquals("Julius", roman.getName());
        assertTrue(romanCity.getPresentCharacters().contains(roman));
    }

    // ========== TESTS DE SOINS ==========

    @Test
    @DisplayName("Soigner tous les personnages")
    void testHealAllCharacters() {
        Gallic gallic = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        gallic.setHealth(50);
        
        int healthBefore = gallic.getHealth();
        clanLeader.healAllCharacters();
        
        assertTrue(gallic.getHealth() > healthBefore);
    }

    @Test
    @DisplayName("Soigner un personnage spécifique")
    void testHealSpecificCharacter() {
        Gallic gallic = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        gallic.setHealth(30);
        
        int healthBefore = gallic.getHealth();
        clanLeader.healCharacter(gallic);
        
        assertTrue(gallic.getHealth() > healthBefore);
    }

    @Test
    @DisplayName("Soigner personnage null ne fait rien")
    void testHealNullCharacter() {
        assertDoesNotThrow(() -> clanLeader.healCharacter(null));
    }

    @Test
    @DisplayName("Soigner personnage absent ne fait rien")
    void testHealAbsentCharacter() {
        Gallic outsider = new Gallic();
        outsider.setName("Outsider");
        outsider.setHealth(30);
        
        int healthBefore = outsider.getHealth();
        clanLeader.healCharacter(outsider);
        
        assertEquals(healthBefore, outsider.getHealth());
    }

    // ========== TESTS DE NOURRISSAGE ==========

    @Test
    @DisplayName("Nourrir un personnage avec nourriture compatible")
    void testFeedCharacter() {
        Gallic gallic = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        FoodItem boar = village.getPresentFoods().get(0);
        
        int hungerBefore = gallic.getHunger();
        clanLeader.feedCharacter(gallic, boar);
        
        assertTrue(gallic.getHunger() > hungerBefore);
    }

    @Test
    @DisplayName("Nourrir personnage avec food null ne fait rien")
    void testFeedCharacterWithNullFood() {
        Gallic gallic = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        
        assertDoesNotThrow(() -> clanLeader.feedCharacter(gallic, null));
    }

    @Test
    @DisplayName("Nourrir personnage absent ne fait rien")
    void testFeedAbsentCharacter() {
        Gallic outsider = new Gallic();
        outsider.setName("Outsider");
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        
        assertDoesNotThrow(() -> clanLeader.feedCharacter(outsider, boar));
    }

    @Test
    @DisplayName("Nourrir avec nourriture absente du lieu ne fait rien")
    void testFeedWithAbsentFood() {
        Gallic gallic = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        FoodItem honey = new FoodItem(FoodItemType.HONEY); // Not in village
        
        int hungerBefore = gallic.getHunger();
        clanLeader.feedCharacter(gallic, honey);
        
        assertEquals(hungerBefore, gallic.getHunger());
    }

    // ========== TESTS DE POTIONS ==========

    @Test
    @DisplayName("Demander potion à un druide réussit")
    void testAskDruidForPotion() {
        Druid druid = (Druid) clanLeader.createGallicCharacter("Panoramix", Druid.class);
        
        Potion potion = clanLeader.askDruidForPotion(druid);
        
        assertNotNull(potion);
        assertEquals(10, potion.getDoses());
    }

    @Test
    @DisplayName("Demander potion à druide null retourne null")
    void testAskNullDruidForPotion() {
        Potion potion = clanLeader.askDruidForPotion(null);
        
        assertNull(potion);
    }

    @Test
    @DisplayName("Demander potion à druide absent retourne null")
    void testAskAbsentDruidForPotion() {
        Druid druid = new Druid();
        druid.setName("Outsider Druid");
        
        Potion potion = clanLeader.askDruidForPotion(druid);
        
        assertNull(potion);
    }

    @Test
    @DisplayName("Donner potion à personnage augmente son niveau magique")
    void testGivePotionToCharacter() {
        Druid druid = (Druid) clanLeader.createGallicCharacter("Panoramix", Druid.class);
        Gallic asterix = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        
        Potion potion = clanLeader.askDruidForPotion(druid);
        int magicBefore = asterix.getLevelMagicPotion();
        
        clanLeader.givePotionToCharacter(asterix, potion);
        
        assertTrue(asterix.getLevelMagicPotion() > magicBefore);
    }

    @Test
    @DisplayName("Donner potion null ne fait rien")
    void testGiveNullPotion() {
        Gallic gallic = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        
        assertDoesNotThrow(() -> clanLeader.givePotionToCharacter(gallic, null));
    }

    // ========== TESTS DE TRANSFERTS ==========

    @Test
    @DisplayName("Transférer personnage vers champ de bataille réussit")
    void testTransferToBattlefield() {
        Gallic gallic = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        Battlefield battlefield = new Battlefield("Gergovia", 5000, 
                                                   new ArrayList<>(), new ArrayList<>());
        
        assertEquals(1, village.getNumberPresentCharacters());
        assertEquals(0, battlefield.getNumberPresentCharacters());
        
        clanLeader.transferToBattlefield(gallic, battlefield);
        
        assertEquals(0, village.getNumberPresentCharacters());
        assertEquals(1, battlefield.getNumberPresentCharacters());
        assertTrue(battlefield.getPresentCharacters().contains(gallic));
    }

    @Test
    @DisplayName("Transférer werewolf vers enclos réussit")
    void testTransferWerewolfToEnclosure() {
        Werewolf werewolf = clanLeader.createWerewolf("Fenrir");
        Enclosure enclosure = new Enclosure("Keeper", "Moonlight", 2000, 
                                            new ArrayList<>(), new ArrayList<>());
        
        assertEquals(1, village.getNumberPresentCharacters());
        assertEquals(0, enclosure.getNumberPresentCharacters());
        
        clanLeader.transferToEnclosure(werewolf, enclosure);
        
        assertEquals(0, village.getNumberPresentCharacters());
        assertEquals(1, enclosure.getNumberPresentCharacters());
        assertTrue(enclosure.getPresentCharacters().contains(werewolf));
    }

    @Test
    @DisplayName("Transférer non-werewolf vers enclos échoue")
    void testTransferNonWerewolfToEnclosure() {
        Gallic gallic = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        Enclosure enclosure = new Enclosure("Keeper", "Moonlight", 2000, 
                                            new ArrayList<>(), new ArrayList<>());
        
        clanLeader.transferToEnclosure(gallic, enclosure);
        
        // Should remain in village
        assertEquals(1, village.getNumberPresentCharacters());
        assertEquals(0, enclosure.getNumberPresentCharacters());
    }

    @Test
    @DisplayName("Transférer personnage null ne fait rien")
    void testTransferNullCharacter() {
        Battlefield battlefield = new Battlefield("Gergovia", 5000, 
                                                   new ArrayList<>(), new ArrayList<>());
        
        assertDoesNotThrow(() -> clanLeader.transferToBattlefield(null, battlefield));
    }

    @Test
    @DisplayName("Transférer vers battlefield null ne fait rien")
    void testTransferToNullBattlefield() {
        Gallic gallic = clanLeader.createGallicCharacter("Asterix", Gallic.class);
        
        assertDoesNotThrow(() -> clanLeader.transferToBattlefield(gallic, null));
        assertEquals(1, village.getNumberPresentCharacters());
    }

    // ========== TESTS D'EXAMEN DU LIEU ==========

    @Test
    @DisplayName("Examiner le lieu ne lance pas d'exception")
    void testExaminePlace() {
        assertDoesNotThrow(() -> clanLeader.examinePlace());
    }

    @Test
    @DisplayName("Examiner lieu avec plusieurs personnages")
    void testExaminePlaceWithMultipleCharacters() {
        clanLeader.createGallicCharacter("Asterix", Gallic.class);
        clanLeader.createGallicCharacter("Obelix", Gallic.class);
        clanLeader.createWerewolf("Fenrir");
        
        assertDoesNotThrow(() -> clanLeader.examinePlace());
        assertEquals(3, village.getNumberPresentCharacters());
    }

    // ========== TESTS GETTERS/SETTERS ==========

    @Test
    @DisplayName("SetName avec nom valide")
    void testSetValidName() {
        clanLeader.setName("Abraracourcix");
        assertEquals("Abraracourcix", clanLeader.getName());
    }

    @Test
    @DisplayName("SetName avec nom null lance exception")
    void testSetNullName() {
        assertThrows(IllegalArgumentException.class, () -> clanLeader.setName(null));
    }

    @Test
    @DisplayName("SetAge avec âge valide")
    void testSetValidAge() {
        clanLeader.setAge(50);
        assertEquals(50, clanLeader.getAge());
    }

    @Test
    @DisplayName("SetAge avec âge négatif lance exception")
    void testSetNegativeAge() {
        assertThrows(IllegalArgumentException.class, () -> clanLeader.setAge(-1));
    }

    @Test
    @DisplayName("SetPlace avec place valide")
    void testSetValidPlace() {
        GallicVillage newVillage = new GallicVillage("NewChief", "New Village", 2000, 
                                                      new ArrayList<>(), new ArrayList<>());
        clanLeader.setPlace(newVillage);
        assertEquals(newVillage, clanLeader.getPlace());
    }

    @Test
    @DisplayName("SetPlace avec place null lance exception")
    void testSetNullPlace() {
        assertThrows(IllegalArgumentException.class, () -> clanLeader.setPlace(null));
    }

    // ========== TESTS TOSTRING ==========

    @Test
    @DisplayName("ToString retourne représentation non-null")
    void testToString() {
        String result = clanLeader.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("Vitalstatistix"));
        assertTrue(result.contains("MALE"));
        assertTrue(result.contains("45"));
    }
}