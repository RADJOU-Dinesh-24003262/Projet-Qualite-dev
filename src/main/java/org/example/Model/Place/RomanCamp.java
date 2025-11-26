package org.example.Model.Place;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Roman.Roman;
import org.example.Model.Character.Werewolf;

/**
 * Represents a Roman camp that can only contain Roman fighters and werewolves.
 */
public class RomanCamp extends AbstractPlace {

    public RomanCamp(String name, int area) {
        super(name, area);
    }

    @Override
    public boolean canAddCharacter(AbstractCharacter character) {
        return character instanceof Roman || character instanceof Werewolf;
    }
}