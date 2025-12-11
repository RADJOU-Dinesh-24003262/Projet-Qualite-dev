package org.example.model.character.Interface;

/**
 * Interface for characters who can lead and direct others.
 * Prefects, Generals, and Druids have leadership capabilities.
 */
public interface Leader {
    /**
     * Executes leadership actions.
     * This includes giving orders, organizing troops, or guiding decisions.
     */
    void lead();

    /**
     * Gets the leadership effectiveness of this character.
     *
     * @return leadership score between 0 and 100
     */
    int getLeadershipScore();
}
