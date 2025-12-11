package org.example.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Manages the redirection of System.out to a JavaFX ListView.
 * Handles styling of log entries based on content (e.g., combat, death).
 */
public class GameLogger {

    private final ListView<Node> eventLogList;

    public GameLogger() {
        this.eventLogList = new ListView<>();
        this.eventLogList.setStyle("-fx-background-color: white; -fx-border-radius: 5;");
        this.eventLogList.setPlaceholder(new Label("No events yet..."));

        // Start capturing console output immediately
        redirectSystemOut();
    }

    public ListView<Node> getView() {
        return eventLogList;
    }

    public void clear() {
        eventLogList.getItems().clear();
    }

    /**
     * Creates a styled graphic card for a log entry.
     *
     * @param text The log text.
     */
    private void addStyledLogEntry(String text) {
        if (text == null || text.trim().isEmpty()) return;
        text = text.trim();

        String icon = "ℹ️";
        Color bgColor = Color.web("#ffffff");
        Color stripeColor = Color.LIGHTGRAY;

        // Header detection
        if (text.contains("NOUVEAU CYCLE") || text.contains("NEW CYCLE")) {
            Label header = new Label(text);
            header.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 14px;");
            HBox container = new HBox(header);
            container.setAlignment(Pos.CENTER);
            container.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 5; -fx-background-radius: 5;");
            eventLogList.getItems().add(container);
            scrollToBottom();
            return;
        }

        // Event type detection
        if (text.contains("Combat") || text.contains("dmg") || text.contains("dégâts")) {
            icon = "⚔️"; bgColor = Color.web("#fff0f0"); stripeColor = Color.RED;
        } else if (text.contains("mort") || text.contains("died") || text.contains("💀")) {
            icon = "💀"; bgColor = Color.web("#2c3e50"); stripeColor = Color.BLACK;
        } else if (text.contains("soin") || text.contains("heal") || text.contains("PV") || text.contains("HP")) {
            icon = "💚"; bgColor = Color.web("#f0fff4"); stripeColor = Color.GREEN;
        } else if (text.contains("Nourriture") || text.contains("Food") || text.contains("Miam")) {
            icon = "🍖"; bgColor = Color.web("#fff8e1"); stripeColor = Color.ORANGE;
        } else if (text.contains("Potion") || text.contains("Druid")) {
            icon = "🧪"; bgColor = Color.web("#f3e5f5"); stripeColor = Color.PURPLE;
        } else if (text.contains("Transfér") || text.contains("Transfer")) {
            icon = "🚚"; bgColor = Color.web("#e3f2fd"); stripeColor = Color.DARKBLUE;
        }

        HBox card = new HBox(10);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(8));

        Region stripe = new Region();
        stripe.setMinWidth(4); stripe.setPrefHeight(30);
        stripe.setBackground(new Background(new BackgroundFill(stripeColor, new CornerRadii(2), Insets.EMPTY)));

        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font(18));

        Label msgLbl = new Label(text);
        msgLbl.setWrapText(true);
        msgLbl.setMaxWidth(350);
        if (icon.equals("💀")) msgLbl.setTextFill(Color.WHITE);

        card.getChildren().addAll(stripe, iconLbl, msgLbl);
        card.setBackground(new Background(new BackgroundFill(bgColor, new CornerRadii(5), Insets.EMPTY)));

        eventLogList.getItems().add(card);
        scrollToBottom();
    }

    private void scrollToBottom() {
        eventLogList.scrollTo(eventLogList.getItems().size() - 1);
    }

    /**
     * Intercepts System.out.println calls to redirect them to the GUI.
     * Uses UTF-8 to preserve emojis and removes ANSI color codes.
     */
    private void redirectSystemOut() {
        OutputStream out = new OutputStream() {
            private final java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();

            @Override
            public void write(int b) {
                if (b == '\n') {
                    String text = buffer.toString(StandardCharsets.UTF_8);
                    buffer.reset();
                    // Clean ANSI codes
                    String cleanText = text.replaceAll("\\x1B\\[[;\\d]*m", "").trim();
                    if (!cleanText.isEmpty()) {
                        Platform.runLater(() -> addStyledLogEntry(cleanText));
                    }
                } else {
                    buffer.write(b);
                }
            }
        };
        try {
            System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}