package org.example.model.places;

import org.example.model.character.roman.Roman;
import org.example.model.character.gallic.Gallic;
import java.util.ArrayList;

import org.example.model.character.AbstractCharacter;
import org.example.model.food.FoodItem;

/**
 * Represents a Gallo-Roman village that can only contain Gallic and Roman characters.
 * This is a mixed settlement where both cultures coexist, but fantasy creatures are not allowed.
 */
public final class GalloRomanVillage extends AbstractPlace {

    /**
     * Creates a new Gallo-Roman village
     * @param clanChief The name of the village chief
     * @param name The name of the village
     * @param surface The surface area
     * @param presentCharacters Initial list of characters (must be Roman or Gallic)
     * @param presentFoods Initial list of food items
     */
    public GalloRomanVillage(String clanChief, String name, int surface, 
                            ArrayList<AbstractCharacter> presentCharacters, ArrayList<FoodItem> presentFoods) {
        super(TypePlace.galloRomanVillage, name, surface, clanChief, presentCharacters, presentFoods);
    }

    /**
     * Validates if a character can be present in a Gallo-Roman village.
     * Only Roman and Gallic characters are allowed (no fantasy creatures).
     * @param character The character to validate
     * @return true if the character is Roman or Gallic, false otherwise
     */
    @Override
    protected boolean canContainCharacter(AbstractCharacter character) {
        return character instanceof Roman || character instanceof Gallic;
    }
}