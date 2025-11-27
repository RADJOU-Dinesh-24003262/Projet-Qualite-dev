package org.example.Model.Potion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.Model.Food.FoodItem;
import org.example.Model.Food.FoodItemType;

/**
 * Represents a magic potion with various effects.
 */
public class Potion {

    private List<FoodItem> ingredients;
    private int doses;
    private static final int INITIAL_DOSES_PER_CAULDRON = 10;
    private static final int MAGIC_BOOST_PER_DOSE = 50;

    /**
     * Constructor creating a standard magic potion.
     */
    public Potion() {
        this.doses = INITIAL_DOSES_PER_CAULDRON;
        this.ingredients = new ArrayList<>();
        
        // Standard recipe
        ingredients.add(new FoodItem(FoodItemType.MISTLETOE));
        ingredients.add(new FoodItem(FoodItemType.CARROTS));
        ingredients.add(new FoodItem(FoodItemType.SALT));
        ingredients.add(new FoodItem(FoodItemType.CLOVER));
        ingredients.add(new FoodItem(FoodItemType.FISH));
        ingredients.add(new FoodItem(FoodItemType.ROCK_OIL));
        ingredients.add(new FoodItem(FoodItemType.HONEY));
        ingredients.add(new FoodItem(FoodItemType.MEAD));
        ingredients.add(new FoodItem(FoodItemType.SECRET_INGREDIENT));
    }

    /**
     * Gets the magic boost value of the potion.
     *
     * @return the magic boost value
     */
    public int getMagicBoost() {
        return MAGIC_BOOST_PER_DOSE;
    }

    /**
     * Gets the number of doses remaining.
     *
     * @return number of doses
     */
    public int getDoses() {
        return doses;
    }

    /**
     * Adds lobster to the potion (makes it more nourishing).
     */
    public void addLobster() {
        this.ingredients.add(new FoodItem(FoodItemType.LOBSTER));
    }

    /**
     * Adds strawberries to the potion (makes it more nourishing).
     */
    public void addStrawberries() {
        this.ingredients.add(new FoodItem(FoodItemType.STRAWBERRIES));
    }

    /**
     * Replaces rock oil with beetroot juice.
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
     * Adds two-headed unicorn milk (grants rolling power).
     */
    public void addTwoHeadedUnicornMilk() {
        this.ingredients.add(new FoodItem(FoodItemType.UNICORN_MILK));
    }

    /**
     * Adds Idefix hair (grants metamorphosis power).
     */
    public void addIdefixHair() {
        this.ingredients.add(new FoodItem(FoodItemType.IDEFIX_HAIR));
    }

    /**
     * Checks if the potion contains a specific ingredient.
     *
     * @param type the ingredient type to check
     * @return true if the ingredient is present
     */
    private boolean hasIngredient(FoodItemType type) {
        return ingredients.stream().anyMatch(item -> item.getType() == type);
    }

    /**
     * Drinks one dose of the potion.
     *
     * @return description of the effects
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
            effects.append(" Also grants metamorphosis, allowing transformation into a lycanthrope.");
        }
        
        return effects.toString();
    }

    /**
     * Drinks the entire cauldron.
     *
     * @return description of the permanent effects
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
            effects.append(" Also grants metamorphosis, allowing transformation into a lycanthrope.");
        }
        
        return effects.toString();
    }

    /**
     * Describes the effect of drinking two cauldrons.
     *
     * @return the effect description
     */
    public static String drinkTwoCauldrons() {
        return "Transforms into a granite statue.";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Magic potion (");
        sb.append(doses).append(" doses) containing: ");
        
        for (int i = 0; i < ingredients.size(); i++) {
            sb.append(ingredients.get(i).getName());
            if (i < ingredients.size() - 1) {
                sb.append(", ");
            }
        }
        
        return sb.toString();
    }
}