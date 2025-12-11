package org.example.model.character;

import org.example.model.character.werewolf.Werewolf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WerewolfCombatTest {

    private Werewolf werewolf;

    @BeforeEach
    void setUp() {
        werewolf = new Werewolf("Lupus");
        werewolf.setHealth(150);
        werewolf.setStrength(40);
        werewolf.setStamina(25);
        werewolf.setBelligerence(10);
    }

    @Test
    @DisplayName("Combat in human form should not be effective")
    void testCombatInHumanForm() {
        werewolf.setHuman(true);
        int initialBelligerence = werewolf.getBelligerence();

        werewolf.combat();

        assertEquals(initialBelligerence, werewolf.getBelligerence(), "Belligerence should not change in human form");
    }

    @Test
    @DisplayName("Combat in werewolf form should increase belligerence")
    void testCombatInWerewolfForm() {
        werewolf.setHuman(false);
        int initialBelligerence = werewolf.getBelligerence();

        werewolf.combat();

        assertEquals(initialBelligerence + 20, werewolf.getBelligerence(), "Belligerence should increase by 20 in werewolf form");
    }

    @Test
    @DisplayName("Combat with high level werewolf should be devastating")
    void testCombatWithHighLevelWerewolf() {
        werewolf.setHuman(false);
        // We need to set stats to get a high level
        werewolf.setStrength(100);
        werewolf.setStamina(100);
        werewolf.setBelligerence(50);
        int initialBelligerence = werewolf.getBelligerence();

        werewolf.combat();

        assertTrue(werewolf.calculateLevel() > 50, "Werewolf level should be over 50");
        assertEquals(initialBelligerence + 20, werewolf.getBelligerence(), "Belligerence should increase by 20");
    }
}
