package org.example.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.gallic.Druid;
import org.example.model.character.gallic.Gallic;
import org.example.model.character.roman.Legionary;
import org.example.model.clanLeader.ClanLeader;
import org.example.model.food.FoodItem;
import org.example.model.places.AbstractPlace;
import org.example.model.places.Battlefield;
import org.example.model.places.Enclosure;
import org.example.model.potion.Potion;
import org.example.model.theaterInvasion.TheaterInvasion;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * Manages the right-hand panel containing game controls and Clan Leader actions.
 */
public class ControlPanel {

    private final VBox panel;
    private final TheaterInvasion game;
    private final Runnable refreshCallback;

    // Controls
    private Button btnNextTurn;
    private ToggleButton btnAuto;
    private ComboBox<ClanLeader> leaderSelector;
    private VBox actionPanel;
    private Button btnClearLog;

    /**
     * Constructs the Control Panel.
     *
     * @param game              The game instance.
     * @param refreshCallback   Runnable to update the UI.
     * @param nextTurnCallback  Runnable to execute a turn.
     * @param autoModeCallback  Consumer to toggle auto-simulation.
     */
    public ControlPanel(TheaterInvasion game, Runnable refreshCallback, Runnable nextTurnCallback, java.util.function.Consumer<Boolean> autoModeCallback) {
        this.game = game;
        this.refreshCallback = refreshCallback;

        this.panel = new VBox(15);
        this.panel.setPadding(new Insets(15));
        this.panel.setAlignment(Pos.TOP_CENTER);
        this.panel.setPrefWidth(280);
        this.panel.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        String headerStyle = "-fx-font-weight: bold; -fx-text-fill: #7f8c8d; -fx-font-size: 12px;";

        // 1. Time Controls
        Label lblTime = new Label("TIME & SIMULATION");
        lblTime.setStyle(headerStyle);

        btnNextTurn = UIStyles.createStyledButton("▶  Next Turn", "#27ae60");
        btnNextTurn.setOnAction(e -> nextTurnCallback.run());

        btnAuto = UIStyles.createStyledToggleButton("⏩  Auto Mode", "#2980b9");
        btnAuto.selectedProperty().addListener((obs, oldVal, newVal) -> autoModeCallback.accept(newVal));

        // 2. Leader Controls
        Label lblActions = new Label("CLAN LEADER ACTIONS");
        lblActions.setStyle(headerStyle);

        leaderSelector = new ComboBox<>();
        leaderSelector.setMaxWidth(Double.MAX_VALUE);
        leaderSelector.setPromptText("Select a Leader...");
        leaderSelector.setStyle("-fx-font-size: 13px; -fx-background-color: #ecf0f1;");

        // Ensure TheaterInvasion has getClanLeaders() public!
        try {
            leaderSelector.getItems().addAll(game.getClanLeaders());
        } catch (Exception e) {
            System.err.println("Warning: Could not load leaders. Ensure getClanLeaders() is public in TheaterInvasion.");
        }

        leaderSelector.setConverter(new ClanLeaderConverter());

        actionPanel = new VBox(10);
        actionPanel.setAlignment(Pos.CENTER);
        setupActionButtons();

        actionPanel.setDisable(true); // Disabled until leader selected

        leaderSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            actionPanel.setDisable(newVal == null);
        });

        btnClearLog = UIStyles.createStyledButton("🗑️ Clear Log", "#95a5a6");

        panel.getChildren().addAll(lblTime, btnNextTurn, btnAuto, new Separator(), lblActions, leaderSelector, actionPanel, new Separator(), btnClearLog);
    }

    public VBox getView() { return panel; }

    public ComboBox<ClanLeader> getLeaderSelector() { return leaderSelector; }
    public Button getBtnNextTurn() { return btnNextTurn; }
    public ToggleButton getBtnAuto() { return btnAuto; }
    public VBox getActionPanel() { return actionPanel; }

    public Button getBtnClearLog() { return btnClearLog; }

    public void setAutoModeVisuals(boolean isAuto) {
        if (isAuto) {
            btnAuto.setText("⏸  Pause Simulation");
            btnAuto.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
            setControlsDisabled(true);
        } else {
            btnAuto.setText("⏩  Auto Mode");
            btnAuto.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-weight: bold;");
            setControlsDisabled(false);
        }
    }

    private void setControlsDisabled(boolean disabled) {
        btnNextTurn.setDisable(disabled);
        leaderSelector.setDisable(disabled);
        actionPanel.setDisable(disabled || leaderSelector.getValue() == null);
    }

    private void setupActionButtons() {
        Button btnCreateChar = UIStyles.createStyledButton("➕ Recruit", "#e67e22");
        btnCreateChar.setOnAction(e -> handleRecruit());

        Button btnHeal = UIStyles.createStyledButton("💚 Heal", "#16a085");
        btnHeal.setOnAction(e -> {
            if (getSelectedLeader() != null) {
                getSelectedLeader().healAllCharacters();
                System.out.println("✨ Area heal performed.");
                refreshCallback.run();
            }
        });

        Button btnFeed = UIStyles.createStyledButton("🍖 Banquet", "#d35400");
        btnFeed.setOnAction(e -> handleFeed());

        Button btnAskPotion = UIStyles.createStyledButton("🧪 Ask Potion", "#8e44ad");
        btnAskPotion.setOnAction(e -> handleAskPotion());

        Button btnGivePotion = UIStyles.createStyledButton("🍺 Give Potion", "#9b59b6");
        btnGivePotion.setOnAction(e -> handleGivePotion());

        Button btnTransfer = UIStyles.createStyledButton("🚚 Transfer", "#34495e");
        btnTransfer.setOnAction(e -> handleTransfer());

        actionPanel.getChildren().addAll(btnCreateChar, new Separator(), btnHeal, btnFeed, new Separator(), btnAskPotion, btnGivePotion, new Separator(), btnTransfer);

        Label lblColony = new Label("🐺 GESTION COLONIE");
        lblColony.setStyle("-fx-font-weight: bold; -fx-text-fill: #7f8c8d; -fx-font-size: 11px; -fx-padding: 10 0 0 0;");

        Button btnColonyStats = UIStyles.createStyledButton("📊 Stats Meute", "#8e44ad");
        btnColonyStats.setOnAction(e -> {
            if (game.getColony() != null) {
                // Affiche les détails dans la console (redirigée vers le GameLogger)
                game.getColony().displayColonyStats();
                game.getColony().displayAllWerewolves();
            } else {
                System.out.println("❌ Aucune colonie active.");
            }
        });

        Button btnForceReproduction = UIStyles.createStyledButton("💕 Forcer Repro.", "#e84393");
        btnForceReproduction.setOnAction(e -> {
            if (game.getColony() != null && !game.getColony().getPacks().isEmpty()) {
                System.out.println("💕 Déclenchement manuel de la saison des amours...");
                // On force la reproduction sur la première meute pour l'exemple
                game.getColony().getPacks().get(0).reproduce();
                refreshCallback.run(); // Met à jour l'interface graphique (nouveaux louveteaux)
            }
        });

        Button btnHowl = UIStyles.createStyledButton("📢 Hurlement", "#34495e");
        btnHowl.setOnAction(e -> {
            if (game.getColony() != null) {
                System.out.println("📢 Un hurlement résonne dans la colonie...");
                // Déclenche des hurlements aléatoires
                // Note: Cette méthode est privée dans Colony, idéalement il faudrait une méthode publique 'triggerRandomEvent'
                // Pour l'instant, on simule en avançant le temps ou en appelant une méthode spécifique si vous l'ajoutez.
                // Ici, on va simplement afficher l'état pour confirmer l'action.
                game.getColony().displayAllWerewolves();
            }
        });

        // Ajout des nouveaux boutons au panneau
        actionPanel.getChildren().addAll(
                // ... boutons existants ...
                new Separator(),
                lblColony,
                btnColonyStats,
                btnForceReproduction,
                btnHowl
        );
    }

    private ClanLeader getSelectedLeader() { return leaderSelector.getValue(); }

    private void handleRecruit() {
        ClanLeader leader = getSelectedLeader();
        if (leader == null) return;
        List<String> choices = List.of("Gaulois", "Romain", "Loup-Garou");
        UIStyles.showCustomChoiceDialog("Recruitment", "Reinforcements for " + leader.getName(), "Class :", choices, null)
                .ifPresent(type -> {
                    TextInputDialog nameDialog = new TextInputDialog();
                    nameDialog.setTitle("Identity");
                    nameDialog.setHeaderText("Name :");
                    nameDialog.showAndWait().ifPresent(name -> {
                        switch (type) {
                            case "Gaulois" -> leader.createGallicCharacter(name, Gallic.class);
                            case "Romain" -> leader.createRomanCharacter(name, Legionary.class);
                            case "Loup-Garou" -> leader.createWerewolf(name);
                        }
                        System.out.println("✅ Recruit: " + name + " (" + type + ")");
                        refreshCallback.run();
                    });
                });
    }

    private void handleFeed() {
        ClanLeader leader = getSelectedLeader();
        if (leader != null) {
            AbstractPlace place = leader.getPlace();
            if (!place.getPresentFoods().isEmpty()) {
                FoodItem food = place.getPresentFoods().get(0);
                leader.feedAllCharacters(food);
                place.getPresentFoods().remove(0);
                System.out.println("🍖 Banquet held with : " + food.getName());
                refreshCallback.run();
            } else {
                System.out.println("❌ Pantry is empty!");
            }
        }
    }

    private void handleAskPotion() {
        ClanLeader leader = getSelectedLeader();
        if (leader == null) return;
        Druid druid = findDruid(leader.getPlace());
        if (druid != null) {
            leader.askDruidForPotion(druid);
            System.out.println("🧪 Druid " + druid.getName() + " prepared a potion!");
        } else {
            System.out.println("❌ No Druid available here!");
        }
    }

    private void handleGivePotion() {
        ClanLeader leader = getSelectedLeader();
        if (leader == null) return;
        Druid druid = findDruid(leader.getPlace());
        if (druid == null) {
            System.out.println("❌ Impossible: No Druid to brew the potion.");
            return;
        }
        List<AbstractCharacter> chars = leader.getPlace().getPresentCharacters();
        if (chars.isEmpty()) return;

        UIStyles.showCustomChoiceDialog("Magic Potion", "Who should drink?", "Character :", chars,
                new CharacterWithHealthConverter()
        ).ifPresent(target -> {
            Potion p = leader.askDruidForPotion(druid);
            leader.givePotionToCharacter(target, p);
            System.out.println("🍺 " + target.getName() + " drinks the potion (Strength++ !)");
            refreshCallback.run();
        });
    }

    private void handleTransfer() {
        ClanLeader leader = getSelectedLeader();
        if (leader == null) return;
        List<AbstractCharacter> chars = leader.getPlace().getPresentCharacters();
        if (chars.isEmpty()) { System.out.println("❌ No one to transfer."); return; }

        UIStyles.showCustomChoiceDialog("Transfer - Step 1", "Who to move?", "Character :", chars,
                new CharacterNameConverter()
        ).ifPresent(characterToMove -> {
            List<AbstractPlace> destinations = game.getExistantsPlaces().stream().filter(p -> p != leader.getPlace()).collect(Collectors.toList());
            UIStyles.showCustomChoiceDialog("Transfer - Step 2", "Where to?", "Place :", destinations,
                    new PlaceNameConverter()
            ).ifPresent(destination -> {
                if (destination instanceof Battlefield) leader.transferToBattlefield(characterToMove, (Battlefield) destination);
                else if (destination instanceof Enclosure) leader.transferToEnclosure(characterToMove, (Enclosure) destination);
                else System.out.println("⚠️ Transfer to this place type not supported.");
                refreshCallback.run();
            });
        });
    }

    private Druid findDruid(AbstractPlace place) {
        for (AbstractCharacter c : place.getPresentCharacters()) {
            if (c instanceof Druid) return (Druid) c;
        }
        return null;
    }

    // Static inner classes for StringConverters (avoids SpotBugs SIC_INNER_SHOULD_BE_STATIC_ANON)
    
    private static final class ClanLeaderConverter extends StringConverter<ClanLeader> {
        @Override
        public String toString(ClanLeader l) {
            return l == null ? "" : l.getName() + "\n(" + l.getPlace().getName() + ")";
        }
        @Override
        public ClanLeader fromString(String s) {
            return null;
        }
    }

    private static final class CharacterWithHealthConverter extends StringConverter<AbstractCharacter> {
        @Override
        public String toString(AbstractCharacter c) {
            return c == null ? "" : c.getName() + " (HP:" + c.getHealth() + ")";
        }
        @Override
        public AbstractCharacter fromString(String s) {
            return null;
        }
    }

    private static final class CharacterNameConverter extends StringConverter<AbstractCharacter> {
        @Override
        public String toString(AbstractCharacter c) {
            return c == null ? "" : c.getName();
        }
        @Override
        public AbstractCharacter fromString(String s) {
            return null;
        }
    }

    private static final class PlaceNameConverter extends StringConverter<AbstractPlace> {
        @Override
        public String toString(AbstractPlace p) {
            return p == null ? "" : p.getName();
        }
        @Override
        public AbstractPlace fromString(String s) {
            return null;
        }
    }
}