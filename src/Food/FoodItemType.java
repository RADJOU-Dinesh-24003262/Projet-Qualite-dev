package Food;

public enum FoodItemType {
    
    BOAR("Boar", FoodType.MEAT),
    FISH("Fish", FoodType.FISH),
    MISTLETOE("Mistletoe", FoodType.VEGETABLE),
    LOBSTER("Lobster", FoodType.FISH),
    STRAWBERRIES("Strawberries", FoodType.VEGETABLE),
    CARROTS("Carrots", FoodType.VEGETABLE),
    SALT("Salt", FoodType.OTHER),
    CLOVER("Four-leaf clover", FoodType.VEGETABLE),
    ROCK_OIL("Rock oil", FoodType.OTHER),
    BEETROOT_JUICE("Beetroot juice", FoodType.DRINK),
    HONEY("Honey", FoodType.OTHER),
    WINE("Wine", FoodType.DRINK),
    MEAD("Mead", FoodType.DRINK),
    UNICORN_MILK("Two-headed unicorn milk", FoodType.DRINK),
    IDEFIX_HAIR("Idefix hair", FoodType.MAGIC),
    SECRET_INGREDIENT("Secret ingredient", FoodType.MAGIC);

    private final String name;
    private final FoodType category;

    FoodItemType(String name, FoodType category) {
        this.name = name;
        this.category = category;
    }

    public String getName() { return name; }
    public FoodType getCategory() { return category; }
}