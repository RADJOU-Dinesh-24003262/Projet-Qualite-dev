package org.example.model.potion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;

/**
 * Represents a magic potion with various ingredients and effects.
 * <p>
 * Base recipe: mistletoe, carrots, salt, fresh four-leaf clover,
 * passably fresh fish, rock oil, honey, mead, secret ingredient.
 * </p>
 * <p>
 * Effects:
 * - One dose: temporary superhuman strength and invincibility
 * - One cauldron: permanent effects
 * - Two cauldrons: transforms into granite statue
 * </p>
 * <p>
 * Special ingredients:
 * - Two-headed unicorn milk: adds power of rolling/duplication
 * - Idefix hair: adds power of metamorphosis (becoming a werewolf)
 * </p>
 */
public class Potion {

    private List<FoodItem> ingredients;
    private int doses;
    private static final int INITIAL_DOSES_PER_CAULDRON = 10;

    /**
     * Creates a new magic potion with the base recipe.
     * A cauldron contains 10 doses by default.
     */
    public Potion() {
        this.doses = INITIAL_DOSES_PER_CAULDRON;
        this.ingredients = new ArrayList<>();

        // Base recipe ingredients
        ingredients.add(new FoodItem(FoodItemType.MISTLETOE));
        ingredients.add(new FoodItem(FoodItemType.CARROTS));
        ingredients.add(new FoodItem(FoodItemType.SALT));
        ingredients.add(new FoodItem(FoodItemType.CLOVER)); // Fresh four-leaf clover
        ingredients.add(new FoodItem(FoodItemType.FISH)); // Passably fresh
        ingredients.add(new FoodItem(FoodItemType.ROCK_OIL));
        ingredients.add(new FoodItem(FoodItemType.HONEY));
        ingredients.add(new FoodItem(FoodItemType.MEAD));
        ingredients.add(new FoodItem(FoodItemType.SECRET_INGREDIENT));
    }

    /**
     * Adds lobster to the potion (nutritious ingredient).
     */
    public void addLobster() {
        this.ingredients.add(new FoodItem(FoodItemType.LOBSTER));
    }

    /**
     * Adds strawberries to the potion (nutritious ingredient).
     */
    public void addStrawberries() {
        this.ingredients.add(new FoodItem(FoodItemType.STRAWBERRIES));
    }

    /**
     * Replaces rock oil with beetroot juice (nutritious alternative).
     */
    public void replaceRockOilWithBeetrootJuice() {
        Optional<FoodItem> rockOil = ingredients.stream()
                .filter(item -> item.getType() == FoodItemType.ROCK_OIL)
                .findFirst();

        if (rockOil.isPresent()) {
            ingredients.remove(rockOil.get());
            ingredients.add(new FoodItem(FoodItemType.BEETROOT_JUICE));
        }
    }

    /**
     * Adds two-headed unicorn milk to grant the power of rolling/duplication.
     */
    public void addTwoHeadedUnicornMilk() {
        this.ingredients.add(new FoodItem(FoodItemType.UNICORN_MILK));
    }

    /**
     * Adds Idefix hair to grant the power of metamorphosis (becoming a werewolf).
     */
    public void addIdefixHair() {
        this.ingredients.add(new FoodItem(FoodItemType.IDEFIX_HAIR));
    }

    /**
     * Checks if the potion contains a specific ingredient type.
     *
     * @param type The ingredient type to check
     * @return true if the ingredient is present, false otherwise
     */
    private boolean hasIngredient(FoodItemType type) {
        return ingredients.stream().anyMatch(item -> item.getType() == type);
    }

    /**
     * Drinks one dose of the potion.
     *
     * @return A description of the effects
     */
    public String drinkDose() {
        if (this.doses <= 0) {
            return "The cauldron is empty.";
        }

        this.doses--;

        StringBuilder effects = new StringBuilder("Gives superhuman strength and temporary invincibility.");

        if (hasIngredient(FoodItemType.UNICORN_MILK)) {
            effects.append(" Also grants the power of rolling.");
        }

        if (hasIngredient(FoodItemType.IDEFIX_HAIR)) {
            effects.append(" Also grants the power of metamorphosis, allowing one to become a lycanthrope (werewolf).");
        }

        return effects.toString();
    }

    /**
     * Drinks the entire cauldron at once.
     * Makes all effects permanent.
     *
     * @return A description of the permanent effects
     */
    public String drinkCauldron() {
        if (this.doses <= 0) {
            return "The cauldron is empty.";
        }

        this.doses = 0;

        StringBuilder effects = new StringBuilder("Makes these effects permanent.");

        if (hasIngredient(FoodItemType.UNICORN_MILK)) {
            effects.append(" Also grants the power of rolling.");
        }

        if (hasIngredient(FoodItemType.IDEFIX_HAIR)) {
            effects.append(" Also grants the power of metamorphosis, allowing one to become a lycanthrope (werewolf).");
        }

        return effects.toString();
    }

    /**
     * Static method describing what happens when drinking two cauldrons.
     *
     * @return The transformation effect
     */
    public static String drinkTwoCauldrons() {
        return "Transforms into a granite statue.";
    }

    /**
     * Gets the magic boost value provided by this potion.
     *
     * @return The magic boost value (50)
     */
    public int getMagicBoost() {
        return 50;
    }

    /**
     * Gets the number of remaining doses.
     *
     * @return The number of doses left
     */
    public int getDoses() {
        return doses;
    }

    /**
     * Gets a copy of the ingredients list.
     *
     * @return A new list containing all ingredients
     */
    public List<FoodItem> getIngredients() {
        return new ArrayList<>(ingredients);
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

        sb.append(" (").append(doses).append(" doses remaining)");

        return sb.toString();
    }
}