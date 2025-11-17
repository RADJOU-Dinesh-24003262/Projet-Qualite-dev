package Character;

import Food.FoodItemType;

public class Werewolves extends AbstractCharacter {
    /**
     * @return FoodItemType[] The list of food the chracter can eat
     */
    @Override
    protected FoodItemType[] getFoodEdibleList() {
        return FoodItemType.values();
    }
}
