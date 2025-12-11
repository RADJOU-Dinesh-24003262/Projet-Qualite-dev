package org.example.model.character.roman;

import org.example.model.character.Interface.Combatant;

/**
 * Represents a Legionary, a standard soldier in the Roman army who is a combatant.
 * Legionaries gain strength and experience through combat.
 */
public class Legionary extends Roman implements Combatant {

    /**
     * The total number of battles this legionary has participated in.
     */
    private int battlesParticipated = 0;

    /**
     * Constructs a Legionary character with specified attributes.
     * @param name The name of the character.
     * @param age The age of the character.
     * @param strength The strength of the character.
     * @param health The health of the character.
     */
    public Legionary(String name, int age, int strength, int health) {
        super(name, age, strength, health);
    }

    /**
     * Performs a combat action, engaging in disciplined military combat.
     * This increases the legionary's belligerence and battle participation count.
     * For every 5 battles participated in, their strength increases.
     */
    @Override
    public void combat() {
        System.out.println(getName() + " engages in disciplined Roman military combat!");
        battlesParticipated++;
        setBelligerence(getBelligerence() + 10);

        // Combat experience increases strength
        if (battlesParticipated % 5 == 0) {
            setStrength(getStrength() + 2);
        }
    }

    /**
     * Gets the total number of battles this legionary has participated in.
     * @return The count of participated battles.
     */
    public int getBattlesParticipated() {
        return battlesParticipated;
    }
}
