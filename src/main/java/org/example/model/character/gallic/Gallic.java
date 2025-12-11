package org.example.model.character.gallic;

import org.example.model.character.AbstractCharacter;
import org.example.model.food.FoodItemType;

/**
 * Represents a Gallic character in the game.
 * <p>
 * Gauls are characters from Celtic origin who can eat meat (boar), fish, and wine.
 * This class serves as a base for specific Gallic character types such as
 * Blacksmiths, Druids, Innkeepers, and Merchants.
 * </p>
 */
public class Gallic extends AbstractCharacter {

    /**
     * Default constructor for a Gallic character.
     */
    public Gallic() {
        super();
    }

    /**
     * Constructs a Gallic character with specified attributes.
     * @param name The name of the character.
     * @param age The age of the character.
     * @param strength The strength of the character.
     * @param health The health of the character.
     */
    public Gallic(String name, int age, int strength, int health) {
        super(name, age, strength, health);
    }

    /**
     * Gets the list of food types that a Gallic character can eat.
     * Gauls can eat boar, fish, and wine.
     *
     * @return An array of {@link FoodItemType} that the character can eat.
     */
    @Override
    protected FoodItemType[] getFoodEdibleList() {
        return new FoodItemType[]{FoodItemType.BOAR, FoodItemType.FISH, FoodItemType.WINE};
    }

}
