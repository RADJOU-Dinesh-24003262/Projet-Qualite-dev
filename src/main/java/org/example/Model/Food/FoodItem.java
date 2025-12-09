package org.example.Model.Food;

/**
 * Represents an individual food item instance in the game.
 * <p>
 * A food item is a specific instance of a food type with a freshness state that can change over time.
 * Characters can consume food items to satisfy hunger, gain health, or gain magical effects.
 * </p>
 * <p>
 * Food items have the following characteristics:
 * </p>
 * <ul>
 *     <li>Type - The specific type of food (defined in {@link FoodItemType})</li>
 *     <li>Freshness - The current freshness state (defined in {@link FreshnessState})</li>
 *     <li>Aging - Food can age over time, reducing its quality</li>
 * </ul>
 */
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

    public String toString() {
        return getName();
    }

}