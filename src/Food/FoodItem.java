package Food;

public class FoodItem {
    private final FoodItemType type;
    private FreshnessState freshness;

    public FoodItem(FoodItemType type) {
        this.type = type;
        this.freshness = type.getStartingFreshness();
    }

    public void age() {
        if (this.freshness == FreshnessState.PASSABLY_FRESH) {
            this.freshness = FreshnessState.NOT_FRESH;
        } else if (this.freshness == FreshnessState.FRESH) {
            this.freshness = FreshnessState.NOT_FRESH;
        }
    }

    public FoodItemType getType() { return this.type; }
    public FreshnessState getFreshness() { return this.freshness; }

    public String getName() {
        String baseName = type.getName();
        return switch (this.freshness) {
            case NOT_FRESH -> baseName + " (not fresh)";
            case PASSABLY_FRESH -> baseName + " (passably fresh)";
            case FRESH -> baseName + " (fresh)";
            default -> baseName;
        };
    }

    public FoodType getCategory() {
        return type.getCategory();
    }
}