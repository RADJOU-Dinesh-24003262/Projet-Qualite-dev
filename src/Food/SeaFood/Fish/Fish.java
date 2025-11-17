package Food.SeaFood.Fish;

import Food.AbstractFood;
import Food.FoodType;

public abstract class Fish extends AbstractFood {
    private final boolean fresh;

    protected Fish(String name, boolean fresh) {
        super(name, FoodType.FISH);
        this.fresh = fresh;
    }

    public boolean isFresh() {
        return fresh;
    }
}
