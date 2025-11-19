package org.example.Model.Character.Gallic;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Food.FoodItemType;

public class Gallic extends AbstractCharacter {

    public Gallic() {}

    /**
     * @return FoodItemType[] The list of food the chracter can eat
     */
    @Override
    protected FoodItemType[] getFoodEdibleList() {
        return new FoodItemType[]{FoodItemType.BOAR, FoodItemType.FISH, FoodItemType.WINE};
    }

}
