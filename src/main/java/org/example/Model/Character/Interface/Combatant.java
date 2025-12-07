package org.example.Model.Character.Interface;

import org.example.Model.Character.AbstractCharacter;

/**
 * Interface for characters who can fight in combat.
 * Legionaries, Generals, Druids, and Werewolves can fight.
 */
public interface Combatant {
    /**
     * Engages in combat behavior.
     * This method represents active combat participation.
     */
    void combat();
    
    /**
     * Checks if the character is currently able to fight.
     * @return true if the character can engage in combat
     */
    default boolean canFight() {
        if (this instanceof AbstractCharacter) {
            AbstractCharacter character = (AbstractCharacter) this;
            return character.isAlive() && character.getHealth() > 20;
        }
        return false;
    }
}

