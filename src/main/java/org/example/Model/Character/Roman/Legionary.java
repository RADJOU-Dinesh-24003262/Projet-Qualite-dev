package org.example.Model.Character.Roman;

import org.example.Model.Character.Interface.Combatant;

/**
 * Legionary - Can fight
 */
public class Legionary extends Roman implements Combatant {

    private int battlesParticipated = 0;

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

    public int getBattlesParticipated() {
        return battlesParticipated;
    }
}
