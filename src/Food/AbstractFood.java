package Food;

public abstract class AbstractFood {

    private final String name;
    private final FoodType type;

    public AbstractFood(String name, FoodType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() { return name; }

    public FoodType getType() { return type; }

    /** Default: food exists, so edible unless overridden */
    public boolean isEdible() {
        return true; 
    }

    public String toString() {
        return name + " (" + type + ")";
    }
}
