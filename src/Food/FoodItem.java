package Food;

public class FoodItem {
    private final FoodItemType type;
    private Boolean isFresh;

    public FoodItem(FoodItemType type) {
        this.type = type;
        
        if (type == FoodItemType.FISH || type == FoodItemType.CLOVER) {
            this.isFresh = true;
        } else {
            this.isFresh = null;
        }
    }

    public void age() {
        if (this.isFresh != null) {
            this.isFresh = false;
        }
    }

    public FoodItemType getType() { return this.type; }
    public Boolean isFresh() { return this.isFresh; }

    public String getName() {
        if (type == FoodItemType.FISH && Boolean.FALSE.equals(isFresh)) {
            return "Not fresh fish";
        }
        if (type == FoodItemType.CLOVER && Boolean.FALSE.equals(isFresh)) {
            return "Not fresh four-leaf clover";
        }
        return type.getName();
    }

    public FoodType getCategory() {
        return type.getCategory();
    }
}