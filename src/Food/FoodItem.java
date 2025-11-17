package Food;

public class FoodItem {
    private final FoodItemType type;
    private boolean isFresh;

    public FoodItem(FoodItemType type) {
        this.type = type;
        
        if (type == FoodItemType.FISH || type == FoodItemType.CLOVER) {
            this.isFresh = true;
        } else {
            this.isFresh = false;
        }
    }

    public void age() {
        if (this.type == FoodItemType.FISH || this.type == FoodItemType.CLOVER) {
            this.isFresh = false;
        }
    }

    public FoodItemType getType() { return this.type; }
    public boolean isFresh() { return this.isFresh; }

    public String getName() {
        if (type == FoodItemType.FISH && !isFresh) {
            return "Not fresh fish";
        }
        if (type == FoodItemType.CLOVER && !isFresh) {
            return "Not fresh four-leaf clover";
        }
        return type.getName();
    }

    public FoodType getCategory() {
        return type.getCategory();
    }
}