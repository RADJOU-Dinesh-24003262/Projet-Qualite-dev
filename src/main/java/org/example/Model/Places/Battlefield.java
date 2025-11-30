package org.example.Model.Places;

import java.util.ArrayList;
import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Food.FoodItem;

/**
 * Represents a battlefield where all types of characters can be present.
 * This is the only place type without a clan chief, as it's a temporary combat zone.
 */
public class Battlefield extends AbstractPlace {

    /**
     * Creates a new battlefield
     * @param name The name of the battlefield
     * @param surface The surface area
     * @param presentCharacters Initial list of characters (all types allowed)
     * @param presentFoods Initial list of food items
     */
    public Battlefield(String name, int surface, 
                      ArrayList<AbstractCharacter> presentCharacters, ArrayList<FoodItem> presentFoods) {
        // Note: no clan chief for battlefields
        super(TypePlace.battlefield, name, surface, presentCharacters, presentFoods);
    }

    /**
     * Validates if a character can be present on a battlefield.
     * All types of characters are allowed on a battlefield.
     * @param character The character to validate
     * @return always true, as all character types are allowed
     */
    @Override
    protected boolean canContainCharacter(AbstractCharacter character) {
        // All characters can be present on a battlefield
        return true;
    }
}