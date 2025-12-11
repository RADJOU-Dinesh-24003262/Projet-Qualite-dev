package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import org.example.model.theaterInvasion.TheaterInvasion;
import org.example.ui.ControlPanel;
import org.example.ui.GameLogger;
import org.example.ui.GameSelectionModel;
import org.example.ui.PlacesView;
import org.example.utils.GameInitializer;

/**
 * Main JavaFX class organizing the modules.
 * Note: Must be started via Launcher.java to avoid module errors.
 */
public class GameGUI extends Application {

    private TheaterInvasion game;
    private int currentTurn = 0;
    private Timeline simulationTimeline;
    private boolean isAutoRunning = false;

    // UI Modules
    private GameLogger gameLogger;
    private PlacesView placesView;
    private ControlPanel controlPanel;
    private Label turnLabel;
    private GameSelectionModel selectionModel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // 1. Initialize Game Data
        this.game = GameInitializer.initialize();
        this.selectionModel = new GameSelectionModel();

        // 2. Initialize UI Components
        this.gameLogger = new GameLogger();
        this.placesView = new PlacesView(selectionModel);

        // Link the Control Panel to the Game logic via callbacks
        this.controlPanel = new ControlPanel(
                this.game,
                this.selectionModel,
                this::refreshUI,          // Callback to update UI
                this::executeTurn,        // Callback to run next turn
                this::toggleAutoMode      // Callback to switch auto mode
        );

        // 3. Main Layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f2f5;");
        root.setPadding(new Insets(15));

        // --- TOP ---
        root.setTop(createTopBar());

        // --- CENTER (Split: Map / Logs) ---
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);

        ScrollPane mapScroll = new ScrollPane(placesView.getView());
        mapScroll.setFitToWidth(true);
        mapScroll.setStyle("-fx-background: #f0f2f5; -fx-border-color: transparent;");

        VBox logsContainer = new VBox(5, new Label("📜 Event Journal"), gameLogger.getView());
        logsContainer.setPadding(new Insets(5));
        VBox.setVgrow(gameLogger.getView(), Priority.ALWAYS);

        splitPane.getItems().addAll(mapScroll, logsContainer);
        splitPane.setDividerPositions(0.65);
        root.setCenter(splitPane);

        // --- RIGHT ---
        root.setRight(controlPanel.getView());

        // Connect the "Clear Log" button from the panel to the logger
        controlPanel.getBtnClearLog().setOnAction(e -> gameLogger.clear());

        setupTimeline();
        refreshUI();

        Scene scene = new Scene(root, 1250, 850);
        primaryStage.setTitle("Theater Invasion - Modular Edition");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(0, 0, 15, 0));

        Label titleLabel = new Label("⚔️ " + game.getTheaterName().toUpperCase());
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#2c3e50"));

        turnLabel = new Label("Turn 0");
        turnLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 20));
        turnLabel.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-padding: 5 15; -fx-background-radius: 20;");

        topBar.getChildren().addAll(titleLabel, turnLabel);
        return topBar;
    }

    private void setupTimeline() {
        simulationTimeline = new Timeline(new KeyFrame(Duration.seconds(1.2), e -> executeTurn()));
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void toggleAutoMode(boolean enable) {
        isAutoRunning = enable;
        if (isAutoRunning) {
            simulationTimeline.play();
        } else {
            simulationTimeline.pause();
        }
        controlPanel.setAutoModeVisuals(isAutoRunning);
    }

    private void executeTurn() {
        currentTurn++;
        turnLabel.setText("Turn " + currentTurn);
        game.runGameCycle(currentTurn);
        refreshUI();
    }

    private void refreshUI() {
        placesView.refresh(game.getExistantsPlaces());
    }
}