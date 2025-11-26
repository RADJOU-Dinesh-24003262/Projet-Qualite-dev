package org.example.Controller;

import org.example.Model.ClanLeader;
import org.example.Model.Character.AbstractCharacter;
import org.example.Model.InvasionTheater;
import org.example.Model.Place.AbstractPlace;
import org.example.Model.Place.Battlefield;
import org.example.Model.Food.FoodItem;
import org.example.Model.Food.FoodItemType;
import org.example.View.GameView;

import java.util.List;
import java.util.Random;

/**
 * Main game controller orchestrating the game loop and temporal aspects.
 */
public class GameController {

    private InvasionTheater theater;
    private GameView gameView;
    private BattleController battleController;
    private ClanLeaderController clanLeaderController;
    private Random random;
    private int turnNumber;
    private boolean gameRunning;

    /**
     * Constructor for GameController.
     *
     * @param theater the invasion theater
     */
    public GameController(InvasionTheater theater) {
        this.theater = theater;
        this.gameView = new GameView();
        this.battleController = new BattleController();
        this.clanLeaderController = new ClanLeaderController(theater);
        this.random = new Random();
        this.turnNumber = 0;
        this.gameRunning = true;
    }

    /**
     * Starts the main game loop.
     */
    public void startGame() {
        gameView.displayWelcome(theater.getName());

        while (gameRunning) {
            turnNumber++;
            gameView.displayTurnStart(turnNumber);

            processTurn();

            if (!checkGameContinuation()) {
                break;
            }

            gameView.displayTurnEnd(turnNumber);
            sleep(2000);
        }

        gameView.displayGameOver(turnNumber);
    }

    /**
     * Processes a single turn of the game.
     */
    private void processTurn() {
        // 1. Combat phase on battlefields
        conductBattles();

        // 2. Modify character states randomly
        modifyCharacterStates();

        // 3. Spawn food items
        spawnFoodItems();

        // 4. Age food items (fresh -> not fresh)
        ageFoodItems();

        // 5. Reset clan leaders' actions
        theater.resetAllClanLeadersActions();

        // 6. Give control to clan leaders (player interaction)
        giveClanLeadersControl();
    }

    /**
     * Conducts battles on all battlefields.
     */
    private void conductBattles() {
        List<Battlefield> battlefields = theater.getBattlefields();

        for (Battlefield battlefield : battlefields) {
            List<AbstractCharacter> fighters = battlefield.getCharacters();

            if (fighters.size() < 2) {
                continue;
            }

            gameView.displayBattleStart(battlefield.getName(), fighters.size());

            battleController.conductBattle(battlefield);

            // Return survivors to their origin places
            returnSurvivorsToOrigin(battlefield);

            gameView.displayBattleEnd(battlefield.getName());
        }
    }

    /**
     * Returns surviving characters from battlefield to their origin.
     *
     * @param battlefield the battlefield
     */
    private void returnSurvivorsToOrigin(Battlefield battlefield) {
        List<AbstractCharacter> fighters = battlefield.getCharacters();
        List<AbstractCharacter> survivors = fighters.stream()
                .filter(AbstractCharacter::isAlive)
                .toList();

        for (AbstractCharacter survivor : survivors) {
            battlefield.removeCharacter(survivor);
            // In a real implementation, track origin place
        }
    }

    /**
     * Randomly modifies character states.
     */
    private void modifyCharacterStates() {
        for (AbstractPlace place : theater.getPlaces()) {
            for (AbstractCharacter character : place.getCharacters()) {
                // Random hunger increase
                if (random.nextInt(100) < 30) {
                    character.setHunger(Math.max(0, character.getHunger() - 10));
                }

                // Random potion level decrease
                if (random.nextInt(100) < 20) {
                    character.setLevelMagicPotion(Math.max(0, character.getLevelMagicPotion() - 5));
                }

                // Random belligerence increase
                if (random.nextInt(100) < 15) {
                    character.setBelligerence(character.getBelligerence() + 5);
                }
            }
        }
    }

    /**
     * Spawns food items in random places (except battlefields).
     */
    private void spawnFoodItems() {
        FoodItemType[] foodTypes = FoodItemType.values();

        for (AbstractPlace place : theater.getPlaces()) {
            if (place instanceof Battlefield) {
                continue;
            }

            if (random.nextInt(100) < 40) {
                FoodItemType randomFood = foodTypes[random.nextInt(foodTypes.length)];
                place.addFood(new FoodItem(randomFood));
            }
        }
    }

    /**
     * Ages all food items (fresh becomes not fresh).
     */
    private void ageFoodItems() {
        for (AbstractPlace place : theater.getPlaces()) {
            for (FoodItem food : place.getFoodItems()) {
                food.age();
            }
        }
    }

    /**
     * Gives control to each clan leader sequentially.
     */
    private void giveClanLeadersControl() {
        List<ClanLeader> leaders = theater.getClanLeaders();

        for (ClanLeader leader : leaders) {
            gameView.displayClanLeaderTurn(leader.getName());
            clanLeaderController.handleClanLeaderTurn(leader);
        }
    }

    /**
     * Checks if the game should continue.
     *
     * @return true if game should continue
     */
    private boolean checkGameContinuation() {
        int totalCharacters = theater.getTotalCharacterCount();

        if (totalCharacters == 0) {
            gameView.displayMessage("All characters have perished!");
            return false;
        }

        if (turnNumber >= 50) {
            gameView.displayMessage("Maximum turns reached!");
            return false;
        }

        return true;
    }

    /**
     * Stops the game.
     */
    public void stopGame() {
        this.gameRunning = false;
    }

    /**
     * Displays game statistics.
     */
    public void displayStatistics() {
        gameView.displayStatistics(theater, turnNumber);
    }

    /**
     * Helper method for sleeping between turns.
     *
     * @param milliseconds time to sleep
     */
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Getters

    public InvasionTheater getTheater() {
        return theater;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }
}