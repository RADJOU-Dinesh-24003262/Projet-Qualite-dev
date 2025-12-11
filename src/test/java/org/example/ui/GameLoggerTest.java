package org.example.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class GameLoggerTest {

    @BeforeAll
    public static void initJFX() {
        new JFXPanel(); // Init JavaFX Toolkit
    }

    @Test
    void testLogCapture() throws InterruptedException {
        // Le logger redirige System.out dès sa création
        // Attention : cela capture la console pour tout le reste du test

        // On utilise un Latch pour attendre que le thread JavaFX ait fini d'ajouter l'élément
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            GameLogger logger = new GameLogger();

            // On simule un message du jeu
            System.out.println("Test Combat Log");

            // On laisse un petit délai au thread UI pour traiter la redirection (async)
            // Dans un vrai test d'intégration, on utiliserait des outils plus robustes (TestFX),
            // mais ici un petit délai suffit pour vérifier le principe.
            new Thread(() -> {
                try { Thread.sleep(200); } catch (InterruptedException e) {}

                Platform.runLater(() -> {
                    try {
                        assertFalse(logger.getView().getItems().isEmpty(), "La liste ne doit pas être vide.");

                        // On vérifie le contenu du dernier élément
                        Node lastItem = logger.getView().getItems().get(logger.getView().getItems().size() - 1);
                        assertTrue(lastItem instanceof HBox, "L'élément doit être une HBox (la carte stylisée).");

                        // On vérifie grossièrement qu'on retrouve le texte (c'est le 3ème enfant dans notre HBox: bande, icone, texte)
                        HBox card = (HBox) lastItem;
                        Label msgLabel = (Label) card.getChildren().get(2);
                        assertEquals("Test Combat Log", msgLabel.getText());

                    } finally {
                        latch.countDown();
                    }
                });
            }).start();
        });

        assertTrue(latch.await(2, TimeUnit.SECONDS), "Le test a timeout.");
    }

    @Test
    void testClear() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            GameLogger logger = new GameLogger();
            logger.getView().getItems().add(new Label("Fake Item"));

            logger.clear();

            assertTrue(logger.getView().getItems().isEmpty(), "La liste doit être vide après clear().");
            latch.countDown();
        });
        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }
}