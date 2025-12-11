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
     * @return FoodItemType[] The list of food the chracter can eat
     */
    @Override
    protected FoodItemType[] getFoodEdibleList() {
        return new FoodItemType[]{FoodItemType.BOAR, FoodItemType.HONEY, FoodItemType.WINE, FoodItemType.MEAD};
    }
}
