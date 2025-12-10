package org.example.model.colony;

import org.example.model.Character.AbstractCharacter;
import org.example.model.Character.werewolf.AgeCategory;
import org.example.model.Character.werewolf.Werewolf;
import org.example.model.pack.Pack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Colony class.
 */
class ColonyTest {

    private Colony colony;
    private Pack pack1;
    private Pack pack2;

    @BeforeEach
    void setUp() {
        colony = new Colony("Test Colony");

        // Create first pack
        Werewolf alpha1Male = createWerewolf("Alpha1M", AbstractCharacter.Sex.MALE, 50);
        Werewolf alpha1Female = createWerewolf("Alpha1F", AbstractCharacter.Sex.FEMALE, 45);
        pack1 = new Pack("Pack1", alpha1Male, alpha1Female);

        // Create second pack
        Werewolf alpha2Male = createWerewolf("Alpha2M", AbstractCharacter.Sex.MALE, 48);
        Werewolf alpha2Female = createWerewolf("Alpha2F", AbstractCharacter.Sex.FEMALE, 43);
        pack2 = new Pack("Pack2", alpha2Male, alpha2Female);
    }

    private Werewolf createWerewolf(String name, AbstractCharacter.Sex sex, int strength) {
        Werewolf w = new Werewolf(name);
        w.setSex(sex);
        w.setAgeCategory(AgeCategory.ADULT);
        w.setStrength(strength);
        w.setStamina(30);
        w.setHealth(100);
        w.setAge(10);
        w.setHunger(50);
        w.setImpetuosityFactor(0.5);
        return w;
    }

    // ========== CONSTRUCTION TESTS ==========

    @Test
    @DisplayName("Colony creation with valid name")
    void testColonyCreation() {
        assertNotNull(colony);
        assertEquals("Test Colony", colony.getName());
        assertEquals(0, colony.getPacks().size());
        assertEquals(0, colony.getSeasonCounter());
    }

    // ========== PACK MANAGEMENT TESTS ==========

    @Test
    @DisplayName("Add pack to colony")
    void testAddPack() {
        colony.addPack(pack1);
        
        assertEquals(1, colony.getPacks().size());
        assertTrue(colony.getPacks().contains(pack1));
    }

    @Test
    @DisplayName("Add multiple packs to colony")
    void testAddMultiplePacks() {
        colony.addPack(pack1);
        colony.addPack(pack2);
        
        assertEquals(2, colony.getPacks().size());
        assertTrue(colony.getPacks().contains(pack1));
        assertTrue(colony.getPacks().contains(pack2));
    }

    @Test
    @DisplayName("Cannot add same pack twice")
    void testCannotAddSamePackTwice() {
        colony.addPack(pack1);
        colony.addPack(pack1);
        
        assertEquals(1, colony.getPacks().size());
    }

    @Test
    @DisplayName("Cannot add null pack")
    void testCannotAddNullPack() {
        colony.addPack(null);
        
        assertEquals(0, colony.getPacks().size());
    }

    @Test
    @DisplayName("Remove pack from colony")
    void testRemovePack() {
        colony.addPack(pack1);
        colony.addPack(pack2);
        
        colony.removePack(pack1);
        
        assertEquals(1, colony.getPacks().size());
        assertFalse(colony.getPacks().contains(pack1));
        assertTrue(colony.getPacks().contains(pack2));
    }

    // ========== WEREWOLF COUNT TESTS ==========

    @Test
    @DisplayName("Total werewolf count with no packs")
    void testTotalWerewolfCountEmpty() {
        assertEquals(0, colony.getTotalWerewolfCount());
    }

    @Test
    @DisplayName("Total werewolf count with packs")
    void testTotalWerewolfCount() {
        colony.addPack(pack1);
        colony.addPack(pack2);
        
        // Each pack has 2 alphas
        assertEquals(4, colony.getTotalWerewolfCount());
    }

    @Test
    @DisplayName("Total werewolf count updates after adding members")
    void testTotalWerewolfCountAfterAddingMembers() {
        colony.addPack(pack1);
        
        Werewolf beta = createWerewolf("Beta", AbstractCharacter.Sex.MALE, 40);
        List<Werewolf> members = List.of(beta);
        pack1.createHierarchy(members);
        
        assertEquals(3, colony.getTotalWerewolfCount());
    }

    // ========== TIME ADVANCEMENT TESTS ==========

    @Test
    @DisplayName("Advance time increments season counter")
    void testAdvanceTimeIncrementsCounter() {
        colony.addPack(pack1);
        
        assertEquals(0, colony.getSeasonCounter());
        
        colony.advanceTime();
        assertEquals(1, colony.getSeasonCounter());
        
        colony.advanceTime();
        assertEquals(2, colony.getSeasonCounter());
    }

    @Test
    @DisplayName("Advance time with empty colony does not crash")
    void testAdvanceTimeWithEmptyColony() {
        assertDoesNotThrow(() -> colony.advanceTime());
        assertEquals(1, colony.getSeasonCounter());
    }

    @Test
    @DisplayName("Multiple time advances")
    void testMultipleTimeAdvances() {
        colony.addPack(pack1);
        
        for (int i = 0; i < 10; i++) {
            colony.advanceTime();
        }
        
        assertEquals(10, colony.getSeasonCounter());
    }

    // ========== DISPLAY METHODS TESTS ==========

    @Test
    @DisplayName("Display all werewolves does not crash")
    void testDisplayAllWerewolves() {
        colony.addPack(pack1);
        colony.addPack(pack2);
        
        assertDoesNotThrow(() -> colony.displayAllWerewolves());
    }

    @Test
    @DisplayName("Display colony stats does not crash")
    void testDisplayColonyStats() {
        colony.addPack(pack1);
        
        assertDoesNotThrow(() -> colony.displayColonyStats());
    }

    @Test
    @DisplayName("Display methods work with empty colony")
    void testDisplayMethodsWithEmptyColony() {
        assertDoesNotThrow(() -> colony.displayAllWerewolves());
        assertDoesNotThrow(() -> colony.displayColonyStats());
    }

    // ========== INTEGRATION TESTS ==========

    @Test
    @DisplayName("Colony simulation over multiple seasons")
    void testColonySimulationMultipleSeasons() {
        // Add initial pack with more members
        colony.addPack(pack1);
        
        Werewolf beta1 = createWerewolf("Beta1", AbstractCharacter.Sex.MALE, 40);
        Werewolf beta2 = createWerewolf("Beta2", AbstractCharacter.Sex.FEMALE, 38);
        Werewolf gamma1 = createWerewolf("Gamma1", AbstractCharacter.Sex.MALE, 35);
        
        List<Werewolf> members = List.of(beta1, beta2, gamma1);
        pack1.createHierarchy(members);
        
        int initialCount = colony.getTotalWerewolfCount();
        
        // Simulate 12 seasons (should trigger mating)
        for (int i = 0; i < 12; i++) {
            colony.advanceTime();
        }
        
        // Should have more werewolves due to reproduction
        assertTrue(colony.getTotalWerewolfCount() >= initialCount,
            "Colony should have at least as many werewolves after reproduction");
    }

    @Test
    @DisplayName("Pack can be removed after being emptied")
    void testEmptyPackRemoval() {
        colony.addPack(pack1);
        
        // Transform all werewolves (simplified test)
        for (Werewolf w : List.copyOf(pack1.getMembers())) {
            pack1.removeWerewolf(w);
        }
        
        colony.advanceTime(); // Should clean up empty pack
        
        // Pack might be removed if empty
        assertTrue(colony.getPacks().isEmpty() || !colony.getPacks().contains(pack1));
    }

    @Test
    @DisplayName("Large colony with many packs")
    void testLargeColony() {
        // Add 5 packs
        for (int i = 0; i < 5; i++) {
            Werewolf alphaM = createWerewolf("AlphaM" + i, AbstractCharacter.Sex.MALE, 50);
            Werewolf alphaF = createWerewolf("AlphaF" + i, AbstractCharacter.Sex.FEMALE, 45);
            Pack pack = new Pack("Pack" + i, alphaM, alphaF);
            colony.addPack(pack);
        }
        
        assertEquals(5, colony.getPacks().size());
        assertEquals(10, colony.getTotalWerewolfCount());
        
        // Advance time should work with many packs
        assertDoesNotThrow(() -> colony.advanceTime());
    }

    // ========== GETTER TESTS ==========

    @Test
    @DisplayName("Get packs returns copy")
    void testGetPacksReturnsCopy() {
        colony.addPack(pack1);
        
        List<Pack> packs = colony.getPacks();
        packs.clear();
        
        // Original colony should still have the pack
        assertEquals(1, colony.getPacks().size());
    }

    @Test
    @DisplayName("Get name returns correct name")
    void testGetName() {
        assertEquals("Test Colony", colony.getName());
    }
}