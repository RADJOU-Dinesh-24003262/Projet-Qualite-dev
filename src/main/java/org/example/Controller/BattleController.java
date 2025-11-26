package org.example.Controller;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Gallic.Gallic;
import org.example.Model.Character.Roman.Roman;
import org.example.Model.Place.Battlefield;
import org.example.View.BattleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller managing battles between characters.
 */
public class BattleController {

    private BattleView battleView;
    private Random random;

    /**
     * Constructor for BattleController.
     */
    public BattleController() {
        this.battleView = new BattleView();
        this.random = new Random();
    }

    /**
     * Conducts a battle on a battlefield.
     *
     * @param battlefield the battlefield where the battle takes place
     */
    public void conductBattle(Battlefield battlefield) {
        List<AbstractCharacter> fighters = new ArrayList<>(battlefield.getCharacters());

        if (fighters.size() < 2) {
            return;
        }

        // Separate fighters by faction
        List<AbstractCharacter> gallics = new ArrayList<>();
        List<AbstractCharacter> romans = new ArrayList<>();
        List<AbstractCharacter> others = new ArrayList<>();

        for (AbstractCharacter fighter : fighters) {
            if (fighter instanceof Gallic) {
                gallics.add(fighter);
            } else if (fighter instanceof Roman) {
                romans.add(fighter);
            } else {
                others.add(fighter);
            }
        }

        // Conduct faction vs faction battles
        if (!gallics.isEmpty() && !romans.isEmpty()) {
            battleView.displayFactionBattle("Gallics", "Romans");
            conductFactionBattle(gallics, romans);
        }

        // Random skirmishes
        conductRandomSkirmishes(fighters);

        // Remove dead characters
        removeDeadCharacters(battlefield);
    }

    /**
     * Conducts a battle between two factions.
     *
     * @param faction1 first faction fighters
     * @param faction2 second faction fighters
     */
    private void conductFactionBattle(List<AbstractCharacter> faction1, List<AbstractCharacter> faction2) {
        int rounds = Math.min(faction1.size(), faction2.size());

        for (int i = 0; i < rounds; i++) {
            if (i >= faction1.size() || i >= faction2.size()) {
                break;
            }

            AbstractCharacter fighter1 = faction1.get(i);
            AbstractCharacter fighter2 = faction2.get(i);

            if (fighter1.isAlive() && fighter2.isAlive()) {
                fight(fighter1, fighter2);
            }
        }
    }

    /**
     * Conducts random skirmishes between fighters.
     *
     * @param fighters list of all fighters
     */
    private void conductRandomSkirmishes(List<AbstractCharacter> fighters) {
        List<AbstractCharacter> aliveFighters = fighters.stream()
                .filter(AbstractCharacter::isAlive)
                .toList();

        int skirmishes = Math.min(3, aliveFighters.size() / 2);

        for (int i = 0; i < skirmishes; i++) {
            if (aliveFighters.size() < 2) {
                break;
            }

            AbstractCharacter fighter1 = aliveFighters.get(random.nextInt(aliveFighters.size()));
            AbstractCharacter fighter2 = aliveFighters.get(random.nextInt(aliveFighters.size()));

            if (fighter1 != fighter2 && fighter1.isAlive() && fighter2.isAlive()) {
                fight(fighter1, fighter2);
            }
        }
    }

    /**
     * Makes two characters fight each other.
     *
     * @param fighter1 first fighter
     * @param fighter2 second fighter
     */
    private void fight(AbstractCharacter fighter1, AbstractCharacter fighter2) {
        battleView.displayFight(fighter1.getName(), fighter2.getName());

        int initialHealth1 = fighter1.getHealth();
        int initialHealth2 = fighter2.getHealth();

        // Mutual combat
        fighter1.mutualFight(fighter2);

        int damage1 = initialHealth1 - fighter1.getHealth();
        int damage2 = initialHealth2 - fighter2.getHealth();

        battleView.displayDamage(fighter1.getName(), damage1, fighter1.getHealth());
        battleView.displayDamage(fighter2.getName(), damage2, fighter2.getHealth());

        // Check for deaths
        checkAndHandleDeath(fighter1);
        checkAndHandleDeath(fighter2);
    }

    /**
     * Checks if a character died and handles death.
     *
     * @param character the character to check
     */
    private void checkAndHandleDeath(AbstractCharacter character) {
        if (character.getHealth() <= 0 && character.isAlive()) {
            character.die();
            battleView.displayDeath(character.getName());
        }
    }

    /**
     * Removes dead characters from the battlefield.
     *
     * @param battlefield the battlefield
     */
    private void removeDeadCharacters(Battlefield battlefield) {
        List<AbstractCharacter> fighters = new ArrayList<>(battlefield.getCharacters());
        int removedCount = 0;

        for (AbstractCharacter fighter : fighters) {
            if (!fighter.isAlive()) {
                battlefield.removeCharacter(fighter);
                removedCount++;
            }
        }

        if (removedCount > 0) {
            battleView.displayRemovedDead(removedCount);
        }
    }

    /**
     * Conducts a duel between two specific characters.
     *
     * @param fighter1 first fighter
     * @param fighter2 second fighter
     * @return the winner, or null if both died
     */
    public AbstractCharacter conductDuel(AbstractCharacter fighter1, AbstractCharacter fighter2) {
        battleView.displayDuelStart(fighter1.getName(), fighter2.getName());

        while (fighter1.isAlive() && fighter2.isAlive()) {
            fight(fighter1, fighter2);

            if (!fighter1.isAlive()) {
                battleView.displayDuelWinner(fighter2.getName());
                return fighter2;
            }
            if (!fighter2.isAlive()) {
                battleView.displayDuelWinner(fighter1.getName());
                return fighter1;
            }
        }

        return null;
    }
}