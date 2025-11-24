package org.example.Model.Character;

import org.example.Model.Food.FoodItemType;

public class Werewolf extends AbstractCharacter {
    /**
     * @return FoodItemType[] The list of food the chracter can eat
     */
    @Override
    protected FoodItemType[] getFoodEdibleList() {
        return FoodItemType.values();
    }
}
