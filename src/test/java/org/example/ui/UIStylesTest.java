package org.example.ui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import org.example.model.character.gallic.Druid;
import org.example.model.character.roman.Legionary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UIStylesTest {

    @BeforeAll
    public static void initJFX() {
        new JFXPanel();
    }

    @Test
    void testCreateStyledButton() {
        // Act
        Button btn = UIStyles.createStyledButton("Test Button", "#FF0000");

        // Assert
        assertNotNull(btn);
        assertEquals("Test Button", btn.getText());
        // On vérifie que le style contient bien la couleur demandée
        assertTrue(btn.getStyle().contains("#FF0000"));
    }

    @Test
    void testGetIconFor() {
        // Arrange
        Druid druid = new Druid("Panoramix", 65, 20, 80);
        Legionary legionary = new Legionary("Titus", 30, 60, 100);

        // Act & Assert
        assertEquals("🧪", UIStyles.getIconFor(druid), "L'icône du Druide doit être 🧪");
        assertEquals("🛡️", UIStyles.getIconFor(legionary), "L'icône du Légionnaire doit être 🛡️");
    }
}