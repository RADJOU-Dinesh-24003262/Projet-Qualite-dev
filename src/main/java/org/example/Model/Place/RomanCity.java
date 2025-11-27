package org.example.Model.Place;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Roman.Roman;
import org.example.Model.Character.Werewolf;

/**
 * Represents a Roman city that can only contain Romans and werewolves.
 */
public class RomanCity extends AbstractPlace {

    public RomanCity(String name, int area) {
        super(name, area);
    }

    @Override
    public boolean canAddCharacter(AbstractCharacter character) {
        return character instanceof Roman || character instanceof Werewolf;
    }
}
