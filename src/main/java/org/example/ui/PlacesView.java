package org.example.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.model.character.AbstractCharacter;
import org.example.model.places.*;

import java.util.List;

/**
 * Handles the visualization of places and characters (Center Panel).
 */
public class PlacesView {

    private final VBox container;
    private final GameSelectionModel selectionModel;
    private final ToggleGroup characterToggleGroup;

    public PlacesView(GameSelectionModel selectionModel) {
        this.selectionModel = selectionModel;
        this.container = new VBox(15);
        this.container.setPadding(new Insets(10));
        this.container.setStyle("-fx-background-color: transparent;");

        this.characterToggleGroup = new ToggleGroup();
        this.characterToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null) {
                selectionModel.clearSelection();
            } else {
                selectionModel.setSelectedCharacter((AbstractCharacter) newToggle.getUserData());
            }
        });
    }

    public VBox getView() {
        return container;
    }

    /**
     * Refreshes the display with the current state of places.
     *
     * @param places List of active places in the game.
     */
    public void refresh(List<AbstractPlace> places) {
        container.getChildren().clear();
        characterToggleGroup.getToggles().clear();

        for (AbstractPlace place : places) {
            VBox card = new VBox(8);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 0);");

            // Header
            HBox header = new HBox(10);
            header.setAlignment(Pos.CENTER_LEFT);
            Label nameLbl = new Label(place.getName());
            nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

            String typeEmoji = "📍";
            if (place instanceof Battlefield) {
                typeEmoji = "⚔️";
                card.setStyle("-fx-background-color: #fff5f5; -fx-background-radius: 8; -fx-border-color: #e74c3c; -fx-border-radius: 8;");
                nameLbl.setTextFill(Color.web("#c0392b"));
            } else if (place instanceof GallicVillage) typeEmoji = "🏡";
            else if (place instanceof RomanFortifiedCamp) typeEmoji = "🏰";

            Label icon = new Label(typeEmoji);
            icon.setFont(Font.font(20));
            header.getChildren().addAll(icon, nameLbl);

            // Character Grid
            FlowPane charsFlow = new FlowPane();
            charsFlow.setHgap(5); charsFlow.setVgap(5);

            if (place.getPresentCharacters().isEmpty()) {
                Label empty = new Label("Place is empty...");
                empty.setTextFill(Color.GRAY);
                charsFlow.getChildren().add(empty);
            } else {
                for (AbstractCharacter c : place.getPresentCharacters()) {
                    ToggleButton charTag = new ToggleButton(UIStyles.getIconFor(c) + " " + c.getName() + " (" + c.getHealth() + ")");
                    charTag.setUserData(c);
                    charTag.setToggleGroup(characterToggleGroup);
                    charTag.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 3 8; -fx-background-radius: 15; -fx-font-size: 11px;");
                    // Highlight low health
                    if (c.getHealth() < 30)
                        charTag.setStyle("-fx-background-color: #ffcdd2; -fx-text-fill: #c62828; -fx-padding: 3 8; -fx-background-radius: 15; -fx-font-size: 11px;");

                    if (c.equals(selectionModel.getSelectedCharacter())) {
                        charTag.setSelected(true);
                    }
                    charsFlow.getChildren().add(charTag);
                }
            }

            // Food Stock
            HBox foodBox = new HBox(5);
            foodBox.setAlignment(Pos.CENTER_LEFT);
            Label foodIcon = new Label("🍎 Stocks:");
            foodIcon.setStyle("-fx-font-weight: bold; -fx-font-size: 11px;");
            String foodTxt = place.getPresentFoods().isEmpty() ? "Empty" : place.getPresentFoods().size() + " items";
            Label foodLbl = new Label(foodTxt);
            foodLbl.setFont(Font.font(11));
            foodBox.getChildren().addAll(foodIcon, foodLbl);

            card.getChildren().addAll(header, new Separator(), charsFlow, new Separator(), foodBox);
            container.getChildren().add(card);
        }
    }
}