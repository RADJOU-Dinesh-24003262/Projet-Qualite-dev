package org.example.Model.Food;

public enum FoodItemType {

    BOAR("Boar", FoodType.MEAT, 50, FreshnessState.NOT_APPLICABLE),
    FISH("Fish", FoodType.FISH, 35, FreshnessState.PASSABLY_FRESH),
    MISTLETOE("Mistletoe", FoodType.VEGETABLE, 10, FreshnessState.NOT_APPLICABLE),
    LOBSTER("Lobster", FoodType.FISH, 40, FreshnessState.NOT_APPLICABLE),
    STRAWBERRIES("Strawberries", FoodType.VEGETABLE, 15, FreshnessState.NOT_APPLICABLE),
    CARROTS("Carrots", FoodType.VEGETABLE, 20, FreshnessState.NOT_APPLICABLE),
    SALT("Salt", FoodType.OTHER, 1, FreshnessState.NOT_APPLICABLE),
    CLOVER("Four-leaf clover", FoodType.VEGETABLE, 1, FreshnessState.NOT_APPLICABLE),
    ROCK_OIL("Rock oil", FoodType.OTHER, 5, FreshnessState.NOT_APPLICABLE),
    BEETROOT_JUICE("Beetroot juice", FoodType.DRINK, 10, FreshnessState.NOT_APPLICABLE),
    HONEY("Honey", FoodType.OTHER, 20, FreshnessState.NOT_APPLICABLE),
    WINE("Wine", FoodType.DRINK, 10, FreshnessState.NOT_APPLICABLE),
    MEAD("Mead", FoodType.DRINK, 10, FreshnessState.NOT_APPLICABLE),
    UNICORN_MILK("Two-headed unicorn milk", FoodType.DRINK, 100, FreshnessState.NOT_APPLICABLE),
    IDEFIX_HAIR("Idefix hair", FoodType.MAGIC, 150, FreshnessState.NOT_APPLICABLE),
    SECRET_INGREDIENT("Secret ingredient", FoodType.MAGIC, 500, FreshnessState.NOT_APPLICABLE);

    private final String name;
    private final FoodType category;
    public final int nutritionalScore;
    private final FreshnessState startingFreshness;

    FoodItemType(String name, FoodType category, int nutritionalScore, FreshnessState startingFreshness) {
        this.name = name;
        this.category = category;
        this.nutritionalScore = nutritionalScore;
        this.startingFreshness = startingFreshness;
    }

    public String getName() {
        return name;
    }

    public FoodType getCategory() {
        return category;
    }
    
    public FreshnessState getStartingFreshness() {
        return startingFreshness; 
    }
}