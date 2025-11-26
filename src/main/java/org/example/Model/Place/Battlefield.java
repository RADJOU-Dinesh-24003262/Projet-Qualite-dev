package org.example.Model.Place;

import org.example.Model.Character.AbstractCharacter;

/**
 * Represents a battlefield that can contain all types of characters.
 * This place has no clan leader.
 */
public class Battlefield extends AbstractPlace {

    public Battlefield(String name, int area) {
        super(name, area);
    }

    @Override
    public boolean canAddCharacter(AbstractCharacter character) {
        return true; // All characters can enter a battlefield
    }

    @Override
    public String displayInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== BATTLEFIELD: ").append(name).append(" ===\n");
        sb.append("Area: ").append(area).append(" m²\n");
        sb.append("Combatants: ").append(characters.size()).append("\n");
        
        for (int i = 0; i < characters.size(); i++) {
            AbstractCharacter c = characters.get(i);
            sb.append("  ").append(i + 1).append(". ").append(c.getName())
              .append(" [").append(c.getClass().getSimpleName()).append("]")
              .append(" (Health: ").append(c.getHealth())
              .append(", Strength: ").append(c.getStrength()).append(")\n");
        }
        
        return sb.toString();
    }
}