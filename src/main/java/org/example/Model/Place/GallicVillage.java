package org.example.Model.Place;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Gallic.Gallic;
import org.example.Model.Character.Werewolf;

/**
 * Represents a Gallic village that can only contain Gallic characters and werewolves.
 */
public class GallicVillage extends AbstractPlace {

    public GallicVillage(String name, int area) {
        super(name, area);
    }

    @Override
    public boolean canAddCharacter(AbstractCharacter character) {
        return character instanceof Gallic || character instanceof Werewolf;
    }
}