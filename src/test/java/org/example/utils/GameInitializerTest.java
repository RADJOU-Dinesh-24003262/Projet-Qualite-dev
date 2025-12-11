package org.example.utils;

import org.example.model.theaterInvasion.TheaterInvasion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameInitializerTest {

    @Test
    void testInitialize() {
        // Act
        TheaterInvasion game = GameInitializer.initialize();

        // Assert
        assertNotNull(game, "L'objet TheaterInvasion ne doit pas être null.");

        // Vérification des lieux (4 créés dans le code : Village, Camp, Enclos, Bataille)
        assertNotNull(game.getExistantsPlaces());
        assertEquals(4, game.getExistantsPlaces().size(), "Il doit y avoir 4 lieux initialisés.");

        // Vérification des leaders (3 créés : Abraracourcix, Caius, Gardien)
        // Note: cela nécessite que getClanLeaders() soit accessible
        assertNotNull(game.getClanLeaders());
        assertEquals(3, game.getClanLeaders().size(), "Il doit y avoir 3 chefs de clan.");

        // Vérification du nom du théâtre
        assertNotNull(game.getTheaterName());
        assertFalse(game.getTheaterName().isEmpty());
    }
}