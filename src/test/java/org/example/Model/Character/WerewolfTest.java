package org.example.Model.Character;

import org.example.Model.Character.Werewolf.Werewolf;
import org.example.Model.Food.FoodItem;
import org.example.Model.Food.FoodItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WerewolfTest {

    private Werewolf werewolf;

    @BeforeEach
    void setUp() {
        werewolf = new Werewolf();
        werewolf.setName("Lupus");
        werewolf.setHealth(150);
        werewolf.setStrength(40);
        werewolf.setStamina(25);
    }

    // ========== TESTS DE NOURRITURE OMNIVORE ==========

    @Test
    @DisplayName("Werewolf peut manger TOUS les types de nourriture")
    void testCanEatAllFoodTypes() {
        for (FoodItemType type : FoodItemType.values()) {
            FoodItem food = new FoodItem(type);
            assertTrue(werewolf.canEat(food),
                    "Werewolf devrait pouvoir manger " + type.getName());
        }
    }

    @Test
    @DisplayName("Werewolf peut manger du sanglier")
    void testCanEatBoar() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        assertTrue(werewolf.canEat(boar));
    }

    @Test
    @DisplayName("Werewolf peut manger du poisson")
    void testCanEatFish() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        assertTrue(werewolf.canEat(fish));
    }

    @Test
    @DisplayName("Werewolf peut manger des aliments magiques")
    void testCanEatMagicFood() {
        FoodItem idefix = new FoodItem(FoodItemType.IDEFIX_HAIR);
        FoodItem secret = new FoodItem(FoodItemType.SECRET_INGREDIENT);

        assertTrue(werewolf.canEat(idefix));
        assertTrue(werewolf.canEat(secret));
    }

    @Test
    @DisplayName("Werewolf peut boire du lait de licorne bicéphale")
    void testCanEatUnicornMilk() {
        FoodItem unicornMilk = new FoodItem(FoodItemType.UNICORN_MILK);
        assertTrue(werewolf.canEat(unicornMilk));
    }

    @Test
    @DisplayName("Werewolf peut manger des légumes")
    void testCanEatVegetables() {
        FoodItem mistletoe = new FoodItem(FoodItemType.MISTLETOE);
        FoodItem carrots = new FoodItem(FoodItemType.CARROTS);
        FoodItem strawberries = new FoodItem(FoodItemType.STRAWBERRIES);

        assertTrue(werewolf.canEat(mistletoe));
        assertTrue(werewolf.canEat(carrots));
        assertTrue(werewolf.canEat(strawberries));
    }

    @Test
    @DisplayName("Werewolf peut manger des fruits de mer")
    void testCanEatSeafood() {
        FoodItem lobster = new FoodItem(FoodItemType.LOBSTER);
        assertTrue(werewolf.canEat(lobster));
    }

    // ========== TESTS DE LA LISTE D'ALIMENTS ==========

//    @Test
//    @DisplayName("Liste d'aliments Werewolf contient tous les FoodItemType")
//    void testFoodListContainsAllTypes() {
//        FoodItemType[] edibleList = werewolf.getFoodEdibleList();
//        FoodItemType[] allTypes = FoodItemType.values();
//
//        assertEquals(allTypes.length, edibleList.length);
//
//        for (FoodItemType type : allTypes) {
//            assertTrue(containsType(edibleList, type),
//                "Liste devrait contenir " + type.getName());
//        }
//    }

//    @Test
//    @DisplayName("Werewolf est le seul à pouvoir tout manger")
//    void testWerewolfUniqueOmnivore() {
//        // Vérifie que getFoodEdibleList retourne bien FoodItemType.values()
//        assertArrayEquals(FoodItemType.values(), werewolf.getFoodEdibleList());
//    }

    // ========== TESTS DE COMPORTEMENT SPÉCIAL ==========

    @Test
    @DisplayName("Werewolf peut manger des aliments que ni Gallic ni Roman ne peuvent")
    void testCanEatExclusiveFood() {
        // Aliments que ni Gallic ni Roman ne peuvent manger
        FoodItem mistletoe = new FoodItem(FoodItemType.MISTLETOE);
        FoodItem lobster = new FoodItem(FoodItemType.LOBSTER);
        FoodItem rockOil = new FoodItem(FoodItemType.ROCK_OIL);

        assertTrue(werewolf.canEat(mistletoe));
        assertTrue(werewolf.canEat(lobster));
        assertTrue(werewolf.canEat(rockOil));
    }

    // Méthode helper
    private boolean containsType(FoodItemType[] array, FoodItemType type) {
        for (FoodItemType item : array) {
            if (item == type) return true;
        }
        return false;
    }
}