package org.example.Model.Place;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Werewolf;

/**
 * Represents an enclosure that can only contain fantastic creatures.
 */
public class Enclosure extends AbstractPlace {

    public Enclosure(String name, int area) {
        super(name, area);
    }

    @Override
    public boolean canAddCharacter(AbstractCharacter character) {
        return character instanceof Werewolf;
    }
}