package org.example.Model.Character.Interface;

/**
 * Interface for characters who can work and produce goods.
 * Innkeepers, Merchants, Blacksmiths, and Druids are workers.
 */
public interface Worker {
    /**
     * Performs work-related activities.
     * This includes crafting, trading, serving, or brewing potions.
     */
    void work();
    
    /**
     * Gets the productivity level of this worker.
     * @return productivity score between 0 and 100
     */
    int getProductivity();
    
    /**
     * Gets the type of work this character specializes in.
     * @return work type description
     */
    String getWorkType();
}
