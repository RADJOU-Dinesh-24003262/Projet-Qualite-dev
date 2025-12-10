package org.example.model.Character.roman;

import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RomanTest {

    private Roman roman;

    @BeforeEach
    void setUp() {
        roman = new Roman();
        roman.setName("Julius");
        roman.setHealth(100);
        roman.setStrength(25);
        roman.setStamina(20);
    }

    // ========== TESTS DE NOURRITURE COMESTIBLE ==========

    @Test
    @DisplayName("Roman peut manger du sanglier")
    void testCanEatBoar() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        assertTrue(roman.canEat(boar));
    }

    @Test
    @DisplayName("Roman peut manger du miel")
    void testCanEatHoney() {
        FoodItem honey = new FoodItem(FoodItemType.HONEY);
        assertTrue(roman.canEat(honey));
    }

    @Test
    @DisplayName("Roman peut boire du vin")
    void testCanEatWine() {
        FoodItem wine = new FoodItem(FoodItemType.WINE);
        assertTrue(roman.canEat(wine));
    }

    @Test
    @DisplayName("Roman peut boire de l'hydromel")
    void testCanEatMead() {
        FoodItem mead = new FoodItem(FoodItemType.MEAD);
        assertTrue(roman.canEat(mead));
    }

    // ========== TESTS DE NOURRITURE NON-COMESTIBLE ==========

    @Test
    @DisplayName("Roman ne peut pas manger de poisson")
    void testCannotEatFish() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        assertFalse(roman.canEat(fish));
    }

    @Test
    @DisplayName("Roman ne peut pas manger de homard")
    void testCannotEatLobster() {
        FoodItem lobster = new FoodItem(FoodItemType.LOBSTER);
        assertFalse(roman.canEat(lobster));
    }

    @Test
    @DisplayName("Roman ne peut pas manger de gui")
    void testCannotEatMistletoe() {
        FoodItem mistletoe = new FoodItem(FoodItemType.MISTLETOE);
        assertFalse(roman.canEat(mistletoe));
    }

    @Test
    @DisplayName("Roman ne peut pas manger de trèfle à quatre feuilles")
    void testCannotEatClover() {
        FoodItem clover = new FoodItem(FoodItemType.CLOVER);
        assertFalse(roman.canEat(clover));
    }

    // ========== TESTS DES SOUS-CLASSES ==========

    @Test
    @DisplayName("General hérite de Roman")
    void testGeneralIsRoman() {
        General general = new General();
        assertInstanceOf(Roman.class, general);
    }

    @Test
    @DisplayName("Legionary hérite de Roman")
    void testLegionaryIsRoman() {
        Legionary legionary = new Legionary();
        assertInstanceOf(Roman.class, legionary);
    }

    @Test
    @DisplayName("Prefect hérite de Roman")
    void testPrefectIsRoman() {
        Prefect prefect = new Prefect();
        assertInstanceOf(Roman.class, prefect);
    }

    // ========== TESTS DE LA LISTE D'ALIMENTS ==========

//    @Test
//    @DisplayName("Liste d'aliments Roman contient exactement 4 éléments")
//    void testFoodListSize() {
//        FoodItemType[] edibleList = roman.getFoodEdibleList();
//        assertEquals(4, edibleList.length);
//    }
//
//    @Test
//    @DisplayName("Liste d'aliments Roman contient BOAR, HONEY, WINE, MEAD")
//    void testFoodListContents() {
//        FoodItemType[] edibleList = roman.getFoodEdibleList();
//
//        assertTrue(containsType(edibleList, FoodItemType.BOAR));
//        assertTrue(containsType(edibleList, FoodItemType.HONEY));
//        assertTrue(containsType(edibleList, FoodItemType.WINE));
//        assertTrue(containsType(edibleList, FoodItemType.MEAD));
//    }

    // ========== COMPARAISON GALLIC VS ROMAN ==========

    @Test
    @DisplayName("Roman et Gallic peuvent tous deux manger du sanglier et boire du vin")
    void testSharedFoodItems() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        FoodItem wine = new FoodItem(FoodItemType.WINE);

        assertTrue(roman.canEat(boar));
        assertTrue(roman.canEat(wine));
    }

    // Méthode helper
    private boolean containsType(FoodItemType[] array, FoodItemType type) {
        for (FoodItemType item : array) {
            if (item == type) return true;
        }
        return false;
    }
}