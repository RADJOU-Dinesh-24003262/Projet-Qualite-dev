package org.example.Model.Places;

import org.example.Model.Character.Werewolf;
import java.util.ArrayList;
import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Food.FoodItem;

/**
 * Represents an enclosure that can only contain fantasy creatures.
 * This is a special containment area exclusively for Werewolves.
 */
public class Enclosure extends AbstractPlace {

    /**
     * Creates a new enclosure
     * @param clanChief The name of the enclosure keeper
     * @param name The name of the enclosure
     * @param surface The surface area
     * @param presentCharacters Initial list of characters (must be Werewolves only)
     * @param presentFoods Initial list of food items
     */
    public Enclosure(String clanChief, String name, int surface, 
                    ArrayList<AbstractCharacter> presentCharacters, ArrayList<FoodItem> presentFoods) {
        super(TypePlace.enclosure, name, surface, clanChief, presentCharacters, presentFoods);
    }

    /**
     * Validates if a character can be present in an enclosure.
     * Only Werewolves (fantasy creatures) are allowed.
     * @param character The character to validate
     * @return true if the character is a Werewolf, false otherwise
     */
    @Override
    protected boolean canContainCharacter(AbstractCharacter character) {
        return character instanceof Werewolf;
    }
}