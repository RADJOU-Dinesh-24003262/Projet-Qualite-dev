package org.example.Model.Food;

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

    public FoodItemType getType() {
        return this.type;
    }
    public boolean freshnessApplicable() {
        return this.freshness != FreshnessState.NOT_APPLICABLE;
    }
    public FreshnessState getFreshness() {
        return this.freshness;
    }
    public boolean isFresh() {
        return this.freshness == FreshnessState.FRESH || this.freshness == FreshnessState.PASSABLY_FRESH;
    }

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

    public int getNutritionalScore() {
        return type.nutritionalScore;
    }
}