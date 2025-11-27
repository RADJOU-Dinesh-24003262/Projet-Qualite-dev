package org.example.Model.Place;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Gallic.Gallic;
import org.example.Model.Character.Roman.Roman;

/**
 * Represents a Gallo-Roman town that can contain both Gallic and Roman characters.
 */
public class GalloRomanTown extends AbstractPlace {

    public GalloRomanTown(String name, int area) {
        super(name, area);
    }

    @Override
    public boolean canAddCharacter(AbstractCharacter character) {
        return character instanceof Gallic || character instanceof Roman;
    }
}