import Food.FoodItem;
import Food.FoodItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MagicPotion {

    private List<FoodItem> ingredients;
    private int doses;
    private static final int INITIAL_DOSES_PER_CAULDRON = 10;

    public MagicPotion() {
        this.doses = INITIAL_DOSES_PER_CAULDRON;
        this.ingredients = new ArrayList<>();
        ingredients.add(new FoodItem(FoodItemType.MISTLETOE));
        ingredients.add(new FoodItem(FoodItemType.CARROTS));
        ingredients.add(new FoodItem(FoodItemType.SALT));

        FoodItem clover = new FoodItem(FoodItemType.CLOVER);
        ingredients.add(clover);

        FoodItem fish = new FoodItem(FoodItemType.FISH);
        ingredients.add(fish);

        ingredients.add(new FoodItem(FoodItemType.ROCK_OIL));
        ingredients.add(new FoodItem(FoodItemType.HONEY));
        ingredients.add(new FoodItem(FoodItemType.MEAD));
        ingredients.add(new FoodItem(FoodItemType.SECRET_INGREDIENT));
    }

    public void addLobster() {
        this.ingredients.add(new FoodItem(FoodItemType.LOBSTER));
    }

    public void addStrawberries() {
        this.ingredients.add(new FoodItem(FoodItemType.STRAWBERRIES));
    }

    public void replaceRockOilWithBeetrootJuice() {
        Optional<FoodItem> rockOil = ingredients.stream()
                .filter(item -> item.getType() == FoodItemType.ROCK_OIL)
                .findFirst();
        if (rockOil.isPresent()) {
            ingredients.remove(rockOil.get());
            ingredients.add(new FoodItem(FoodItemType.BEETROOT_JUICE));
        }
    }

    public void addTwoHeadedUnicornMilk() {
        this.ingredients.add(new FoodItem(FoodItemType.UNICORN_MILK));
    }

    public void addIdefixHair() {
        this.ingredients.add(new FoodItem(FoodItemType.IDEFIX_HAIR));
    }

    private boolean hasIngredient(FoodItemType type) {
        return ingredients.stream().anyMatch(item -> item.getType() == type);
    }

    public String drinkDose() {
        if (this.doses <= 0) {
            return "The cauldron is empty.";
        }
        this.doses--;
        String effects = "Gives superhuman strength and temporary invincibility.";
        if (hasIngredient(FoodItemType.UNICORN_MILK)) {
            effects += " Also grants the power of rolling.";
        }
        if (hasIngredient(FoodItemType.IDEFIX_HAIR)) {
            effects += " Also grants the power of metamorphosis, allowing one to become a lycanthrope (werewolf).";
        }
        return effects;
    }

    public String drinkCauldron() {
        if (this.doses <= 0) {
            return "The cauldron is empty.";
        }
        this.doses = 0;
        String effects = "Makes these effects permanent.";
        if (hasIngredient(FoodItemType.UNICORN_MILK)) {
            effects += " Also grants the power of rolling.";
        }
        if (hasIngredient(FoodItemType.IDEFIX_HAIR)) {
            effects += " Also grants the power of metamorphosis, allowing one to become a lycanthrope (werewolf).";
        }
        return effects;
    }

    public static String drinkTwoCauldrons() {
        return "Transforms into a granite statue.";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Magic potion containing: ");
        for (int i = 0; i < ingredients.size(); i++) {
            sb.append(ingredients.get(i).getName());
            if (i < ingredients.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
