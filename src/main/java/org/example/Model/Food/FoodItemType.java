package org.example.Model.Food;

public enum FoodItemType {
    
    BOAR("Boar", FoodType.MEAT, 50),
    FISH("Fish", FoodType.FISH, 35),
    MISTLETOE("Mistletoe", FoodType.VEGETABLE, 10),
    LOBSTER("Lobster", FoodType.FISH, 40),
    STRAWBERRIES("Strawberries", FoodType.VEGETABLE, 15),
    CARROTS("Carrots", FoodType.VEGETABLE, 20),
    SALT("Salt", FoodType.OTHER, 1),
    CLOVER("Four-leaf clover", FoodType.VEGETABLE, 1),
    ROCK_OIL("Rock oil", FoodType.OTHER, 5),
    BEETROOT_JUICE("Beetroot juice", FoodType.DRINK, 10),
    HONEY("Honey", FoodType.OTHER, 20),
    WINE("Wine", FoodType.DRINK, 10),
    MEAD("Mead", FoodType.DRINK, 10),
    UNICORN_MILK("Two-headed unicorn milk", FoodType.DRINK, 100),
    IDEFIX_HAIR("Idefix hair", FoodType.MAGIC, 150),
    SECRET_INGREDIENT("Secret ingredient", FoodType.MAGIC, 500);

    private final String name;
    private final FoodType category;
    public final int nutritionalScore;

    FoodItemType(String name, FoodType category,  int nutritionalScore) {
        this.name = name;
        this.category = category;
        this.nutritionalScore = nutritionalScore;
    }

    public String getName() { return name; }
    public FoodType getCategory() { return category; }
}