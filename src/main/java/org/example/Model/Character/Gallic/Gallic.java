package org.example.Model.Character.Gallic;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Food.FoodItemType;

/**
 * Represents a Gallic character in the game.
 * <p>
 * Gauls are characters from Celtic origin who can eat meat (boar), fish, and wine.
 * This class serves as a base for specific Gallic character types such as
 * Blacksmiths, Druids, Innkeepers, and Merchants.
 * </p>
 */
public class Gallic extends AbstractCharacter {

    public Gallic() {
    }

    /**
     * @return FoodItemType[] The list of food the chracter can eat
     */
    @Override
    protected FoodItemType[] getFoodEdibleList() {
        return new FoodItemType[]{FoodItemType.BOAR, FoodItemType.FISH, FoodItemType.WINE};
    }

}
