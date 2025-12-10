package org.example.model.places;

import org.example.model.Character.gallic.Gallic;
import java.util.ArrayList;

import org.example.model.Character.AbstractCharacter;
import org.example.model.Character.werewolf.Werewolf;
import org.example.model.food.FoodItem;

/**
 * Represents a Gallic village that can only contain Gallic characters and fantasy creatures.
 */
public final class GallicVillage extends AbstractPlace {

    /**
     * Creates a new Gallic village
     * @param clanChief The name of the clan chief
     * @param name The name of the village
     * @param surface The surface area
     * @param presentCharacters Initial list of characters (must be Gallic or Werewolf)
     * @param presentFoods Initial list of food items
     */
    public GallicVillage(String clanChief, String name, int surface, 
                        ArrayList<AbstractCharacter> presentCharacters, ArrayList<FoodItem> presentFoods) {
        super(TypePlace.gallicVillage, name, surface, clanChief, presentCharacters, presentFoods);
    }

    /**
     * Validates if a character can be present in a Gallic village.
     * Only Gallic characters and Werewolves are allowed.
     * @param character The character to validate
     * @return true if the character is Gallic or Werewolf, false otherwise
     */
    @Override
    protected boolean canContainCharacter(AbstractCharacter character) {
        return character instanceof Gallic || character instanceof Werewolf;
    }
}