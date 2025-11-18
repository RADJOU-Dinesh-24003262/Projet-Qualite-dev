package Food;

public enum FoodItemType {
    
    BOAR("Boar", FoodType.MEAT, FreshnessState.NOT_APPLICABLE),
    FISH("Fish", FoodType.FISH, FreshnessState.PASSABLY_FRESH),
    MISTLETOE("Mistletoe", FoodType.VEGETABLE, FreshnessState.NOT_APPLICABLE),
    LOBSTER("Lobster", FoodType.FISH, FreshnessState.NOT_APPLICABLE),
    STRAWBERRIES("Strawberries", FoodType.VEGETABLE, FreshnessState.NOT_APPLICABLE),
    CARROTS("Carrots", FoodType.VEGETABLE, FreshnessState.NOT_APPLICABLE),
    SALT("Salt", FoodType.OTHER, FreshnessState.NOT_APPLICABLE),
    CLOVER("Four-leaf clover", FoodType.VEGETABLE, FreshnessState.NOT_APPLICABLE),
    ROCK_OIL("Rock oil", FoodType.OTHER, FreshnessState.NOT_APPLICABLE),
    BEETROOT_JUICE("Beetroot juice", FoodType.DRINK, FreshnessState.NOT_APPLICABLE),
    HONEY("Honey", FoodType.OTHER, FreshnessState.NOT_APPLICABLE),
    WINE("Wine", FoodType.DRINK, FreshnessState.NOT_APPLICABLE),
    MEAD("Mead", FoodType.DRINK, FreshnessState.NOT_APPLICABLE),
    UNICORN_MILK("Two-headed unicorn milk", FoodType.DRINK, FreshnessState.NOT_APPLICABLE),
    IDEFIX_HAIR("Idefix hair", FoodType.MAGIC, FreshnessState.NOT_APPLICABLE),
    SECRET_INGREDIENT("Secret ingredient", FoodType.MAGIC, FreshnessState.NOT_APPLICABLE);

    private final String name;
    private final FoodType category;
    private final FreshnessState startingFreshness;

    FoodItemType(String name, FoodType category, FreshnessState startingFreshness) {
        this.name = name;
        this.category = category;
        this.startingFreshness = startingFreshness;
    }

    public String getName() { return name; }
    public FoodType getCategory() { return category; }
    public FreshnessState getStartingFreshness() { return startingFreshness; }
}