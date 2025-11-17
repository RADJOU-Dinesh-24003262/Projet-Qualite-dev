package Food.SeaFood;

import Food.AbstractFood;
import Food.FoodType;

public class Lobster extends AbstractFood {

    protected Lobster(String name) {
        super(name, FoodType.SEAFOOD);
    }

}
