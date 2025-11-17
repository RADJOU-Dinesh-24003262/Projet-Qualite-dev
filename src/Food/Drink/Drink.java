package Food.Drink;

import Food.AbstractFood;
import Food.FoodType;

public abstract class Drink extends AbstractFood {
    protected Drink(String name) { super(name, FoodType.DRINK); }
}
