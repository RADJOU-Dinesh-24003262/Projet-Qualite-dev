package org.example.Model.Places;

import org.example.Model.Character.Roman.Legionary;
import org.example.Model.Character.Roman.General;
import java.util.ArrayList;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Roman.General;
import org.example.Model.Character.Roman.Legionary;
import org.example.Model.Character.Werewolf;
import org.example.Model.Food.FoodItem;

/**
 * Represents a Roman fortified camp that can only contain Roman combatants and fantasy creatures.
 * Only Legionaries, Generals, and Werewolves are allowed.
 */
public class RomanFortifiedCamp extends AbstractPlace {

    /**
     * Creates a new Roman fortified camp
     * @param clanChief The name of the camp commander
     * @param name The name of the camp
     * @param surface The surface area
     * @param presentCharacters Initial list of characters (must be Legionary, General, or Werewolf)
     * @param presentFoods Initial list of food items
     */
    public RomanFortifiedCamp(String clanChief, String name, int surface,
                              ArrayList<AbstractCharacter> presentCharacters, ArrayList<FoodItem> presentFoods) {
        super(TypePlace.romanFortifiedCamp, name, surface, clanChief, presentCharacters, presentFoods);
    }

    /**
     * Validates if a character can be present in a Roman fortified camp.
     * Only Legionaries, Generals, and Werewolves are allowed.
     * @param character The character to validate
     * @return true if the character is Legionary, General, or Werewolf, false otherwise
     */
    @Override
    protected boolean canContainCharacter(AbstractCharacter character) {
        return character instanceof Legionary ||
                character instanceof General ||
                character instanceof Werewolf;
    }
}