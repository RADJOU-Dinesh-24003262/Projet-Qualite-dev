package org.example.Model.Places;

import org.example.Model.Character.Roman.Roman;
import java.util.ArrayList;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Werewolf.Werewolf;
import org.example.Model.Food.FoodItem;

/**
 * Represents a Roman city that can only contain Roman characters and fantasy creatures.
 */
public final class RomanCity extends AbstractPlace {

    /**
     * Creates a new Roman city
     * @param clanChief The name of the city governor
     * @param name The name of the city
     * @param surface The surface area
     * @param presentCharacters Initial list of characters (must be Roman or Werewolf)
     * @param presentFoods Initial list of food items
     */
    public RomanCity(String clanChief, String name, int surface, 
                    ArrayList<AbstractCharacter> presentCharacters, ArrayList<FoodItem> presentFoods) {
        super(TypePlace.romanCity, name, surface, clanChief, presentCharacters, presentFoods);
    }

    /**
     * Validates if a character can be present in a Roman city.
     * Only Roman characters and Werewolves are allowed.
     * @param character The character to validate
     * @return true if the character is Roman or Werewolf, false otherwise
     */
    @Override
    protected boolean canContainCharacter(AbstractCharacter character) {
        return character instanceof Roman || character instanceof Werewolf;
    }
}