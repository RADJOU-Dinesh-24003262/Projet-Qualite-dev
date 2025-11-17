package Food.VegetableOrPlant;

import Food.AbstractFood;
import Food.FoodType;

public abstract class VegetableOrPlant extends AbstractFood {
    private final boolean fresh;

    protected VegetableOrPlant(String name, boolean fresh) {
        super(name, FoodType.VEGETABLE);
        this.fresh = fresh;
    }

    public boolean isFresh() { return fresh; }

}