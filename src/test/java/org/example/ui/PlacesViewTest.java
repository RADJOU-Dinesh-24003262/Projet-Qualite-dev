package org.example.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.VBox;
import org.example.model.character.gallic.Gallic;
import org.example.model.places.AbstractPlace;
import org.example.model.places.GallicVillage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PlacesViewTest {

    @BeforeAll
    public static void initJFX() {
        new JFXPanel();
    }

    @Test
    void testRefreshPlaces() throws InterruptedException {
        // Arrange : Créer des données de test
        GallicVillage village = new GallicVillage("Test Village", "Village", 100, new ArrayList<>(), new ArrayList<>());
        Gallic asterix = new Gallic();
        asterix.setName("Asterix");
        asterix.setHealth(100);
        village.addCharacter(asterix);

        List<AbstractPlace> places = new ArrayList<>();
        places.add(village);

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            // Act
            PlacesView view = new PlacesView();
            view.refresh(places);

            // Assert
            VBox container = view.getView();
            assertEquals(1, container.getChildren().size(), "Il devrait y avoir 1 carte affichée.");

            // On peut vérifier plus en détail si besoin (ex: vérifier le titre de la carte)
            // Mais vérifier que la liste n'est pas vide est déjà un bon test de base.

            latch.countDown();
        });

        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }
}