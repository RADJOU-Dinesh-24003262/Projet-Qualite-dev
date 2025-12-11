package org.example.model.character;

import org.example.model.character.werewolf.Werewolf;
import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link Werewolf} class, focusing on its omnivorous eating habits
 * and special behaviors.
 */
class WerewolfTest {

    private Werewolf werewolf;

    /**
     * Sets up a new Werewolf instance before each test.
     */
    @BeforeEach
    void setUp() {
        werewolf = new Werewolf();
        werewolf.setName("Lupus");
        werewolf.setHealth(150);
        werewolf.setStrength(40);
        werewolf.setStamina(25);
    }

    // ========== OMNIVORE FOOD TESTS ==========

    /**
     * Tests that a Werewolf can eat all types of food available in {@link FoodItemType}.
     */
    @Test
    @DisplayName("Werewolf can eat ALL food types")
    void testCanEatAllFoodTypes() {
        for (FoodItemType type : FoodItemType.values()) {
            FoodItem food = new FoodItem(type);
            assertTrue(werewolf.canEat(food),
                    "Werewolf should be able to eat " + type.getName());
        }
    }

    /**
     * Tests that a Werewolf can eat boar.
     */
    @Test
    @DisplayName("Werewolf can eat boar")
    void testCanEatBoar() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        assertTrue(werewolf.canEat(boar));
    }

    /**
     * Tests that a Werewolf can eat fish.
     */
    @Test
    @DisplayName("Werewolf can eat fish")
    void testCanEatFish() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        assertTrue(werewolf.canEat(fish));
    }

    /**
     * Tests that a Werewolf can eat magical food items.
     */
    @Test
    @DisplayName("Werewolf can eat magic food")
    void testCanEatMagicFood() {
        FoodItem idefix = new FoodItem(FoodItemType.IDEFIX_HAIR);
        FoodItem secret = new FoodItem(FoodItemType.SECRET_INGREDIENT);

        assertTrue(werewolf.canEat(idefix));
        assertTrue(werewolf.canEat(secret));
    }

    /**
     * Tests that a Werewolf can drink two-headed unicorn milk.
     */
    @Test
    @DisplayName("Werewolf can drink two-headed unicorn milk")
    void testCanEatUnicornMilk() {
        FoodItem unicornMilk = new FoodItem(FoodItemType.UNICORN_MILK);
        assertTrue(werewolf.canEat(unicornMilk));
    }

    /**
     * Tests that a Werewolf can eat vegetables.
     */
    @Test
    @DisplayName("Werewolf can eat vegetables")
    void testCanEatVegetables() {
        FoodItem mistletoe = new FoodItem(FoodItemType.MISTLETOE);
        FoodItem carrots = new FoodItem(FoodItemType.CARROTS);
        FoodItem strawberries = new FoodItem(FoodItemType.STRAWBERRIES);

        assertTrue(werewolf.canEat(mistletoe));
        assertTrue(werewolf.canEat(carrots));
        assertTrue(werewolf.canEat(strawberries));
    }

    /**
     * Tests that a Werewolf can eat seafood.
     */
    @Test
    @DisplayName("Werewolf can eat seafood")
    void testCanEatSeafood() {
        FoodItem lobster = new FoodItem(FoodItemType.LOBSTER);
        assertTrue(werewolf.canEat(lobster));
    }

    // ========== SPECIAL BEHAVIOR TESTS ==========

    /**
     * Tests that a Werewolf can eat food items that neither Gallic nor Roman characters can eat.
     */
    @Test
    @DisplayName("Werewolf can eat food that neither Gallic nor Roman can")
    void testCanEatExclusiveFood() {
        // Foods that neither Gallic nor Roman can eat
        FoodItem mistletoe = new FoodItem(FoodItemType.MISTLETOE);
        FoodItem lobster = new FoodItem(FoodItemType.LOBSTER);
        FoodItem rockOil = new FoodItem(FoodItemType.ROCK_OIL);

        assertTrue(werewolf.canEat(mistletoe));
        assertTrue(werewolf.canEat(lobster));
        assertTrue(werewolf.canEat(rockOil));
    }

    /**
     * Helper method to check if an array of {@link FoodItemType} contains a specific type.
     * @param array The array to search.
     * @param type The type to find.
     * @return True if the type is found, false otherwise.
     */
    private boolean containsType(FoodItemType[] array, FoodItemType type) {
        for (FoodItemType item : array) {
            if (item == type) return true;
        }
        return false;
    }
}