package Food.Meat;

import Food.AbstractFood;
import Food.FoodType;

public abstract class Meat extends AbstractFood {
    protected Meat(String name) { super(name, FoodType.MEAT); }


}

