package org.example.Model.Character.Roman;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Food.FoodItemType;

public class Roman extends AbstractCharacter {
    /**
     * @return FoodItemType[] The list of food the chracter can eat
     */
    @Override
    protected FoodItemType[] getFoodEdibleList() {
        return new FoodItemType[]{FoodItemType.BOAR, FoodItemType.HONEY, FoodItemType.WINE, FoodItemType.MEAD};
    }
}
