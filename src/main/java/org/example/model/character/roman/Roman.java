package org.example.model.character.roman;

import org.example.model.character.AbstractCharacter;
import org.example.model.food.FoodItemType;

/**
 * Represents a Roman character in the game.
 * <p>
 * Romans are characters from the Roman Empire who have access to a wider variety of food:
 * boar, honey, wine, and mead. This class serves as a base for specific Roman character types
 * such as Generals, Legionaries, and Prefects.
 * </p>
 */
public class Roman extends AbstractCharacter {

    /**
     * Default constructor for a Roman character.
     */
    public Roman() {
        super();
    }

    /**
     * Constructs a Roman character with specified attributes.
     * @param name The name of the character.
     * @param age The age of the character.
     * @param strength The strength of the character.
     * @param health The health of the character.
     */
    public Roman(String name, int age, int strength, int health) {
        super(name, age, strength, health);
    }

    /**
     * Gets the list of food types that a Roman character can eat.
     * Romans can eat boar, honey, wine, and mead.
     *
     * @return An array of {@link FoodItemType} that the character can eat.
     */
    @Override
    protected FoodItemType[] getFoodEdibleList() {
        return new FoodItemType[]{FoodItemType.BOAR, FoodItemType.HONEY, FoodItemType.WINE, FoodItemType.MEAD};
    }
}
