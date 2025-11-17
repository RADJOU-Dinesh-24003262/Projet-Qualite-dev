package test.Food;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import Food.FoodItem;
import Food.FoodItemType;

import static org.junit.jupiter.api.Assertions.*;

class FoodItemTest {

    // ========== TESTS DE FRAÎCHEUR ==========

    @Test
    @DisplayName("Poisson est frais à la création")
    void testFishStartsFresh() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        assertTrue(fish.isFresh());
    }

    @Test
    @DisplayName("Trèfle à quatre feuilles est frais à la création")
    void testCloverStartsFresh() {
        FoodItem clover = new FoodItem(FoodItemType.CLOVER);
        assertTrue(clover.isFresh());
    }

    @Test
    @DisplayName("Sanglier n'a pas d'attribut de fraîcheur")
    void testBoarHasNoFreshness() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        assertNull(boar.isFresh());
    }

    @Test
    @DisplayName("Vin n'a pas d'attribut de fraîcheur")
    void testWineHasNoFreshness() {
        FoodItem wine = new FoodItem(FoodItemType.WINE);
        assertNull(wine.isFresh());
    }

    @Test
    @DisplayName("Miel n'a pas d'attribut de fraîcheur")
    void testHoneyHasNoFreshness() {
        FoodItem honey = new FoodItem(FoodItemType.HONEY);
        assertNull(honey.isFresh());
    }

    // ========== TESTS DE VIEILLISSEMENT ==========

    @Test
    @DisplayName("Poisson devient non-frais après vieillissement")
    void testFishAges() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        fish.age();
        assertFalse(fish.isFresh());
    }

    @Test
    @DisplayName("Trèfle devient non-frais après vieillissement")
    void testCloverAges() {
        FoodItem clover = new FoodItem(FoodItemType.CLOVER);
        clover.age();
        assertFalse(clover.isFresh());
    }

    @Test
    @DisplayName("Vieillir un aliment sans fraîcheur ne change rien")
    void testAgingNonPerishableFood() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        assertNull(boar.isFresh());
        
        boar.age();
        assertNull(boar.isFresh()); // Reste null
    }

    @Test
    @DisplayName("Poisson reste non-frais après plusieurs vieillissements")
    void testMultipleAgingKeepsFoodStale() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        fish.age();
        fish.age();
        fish.age();
        
        assertFalse(fish.isFresh());
    }

    // ========== TESTS DES NOMS ==========

    @Test
    @DisplayName("Poisson frais a le nom normal")
    void testFreshFishName() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        assertEquals("Fish", fish.getName());
    }

    @Test
    @DisplayName("Poisson non-frais a un nom spécial")
    void testStaleFishName() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        fish.age();
        assertEquals("Not fresh fish", fish.getName());
    }

    @Test
    @DisplayName("Trèfle frais a le nom normal")
    void testFreshCloverName() {
        FoodItem clover = new FoodItem(FoodItemType.CLOVER);
        assertEquals("Four-leaf clover", clover.getName());
    }

    @Test
    @DisplayName("Trèfle non-frais a un nom spécial")
    void testStaleCloverName() {
        FoodItem clover = new FoodItem(FoodItemType.CLOVER);
        clover.age();
        assertEquals("Not fresh four-leaf clover", clover.getName());
    }

    @Test
    @DisplayName("Sanglier garde toujours le même nom")
    void testBoarNameNeverChanges() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        String initialName = boar.getName();
        
        boar.age();
        assertEquals(initialName, boar.getName());
    }

    // ========== TESTS DES CATÉGORIES ==========

    @Test
    @DisplayName("Sanglier est de catégorie MEAT")
    void testBoarCategory() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        assertEquals(FoodType.MEAT, boar.getCategory());
    }

    @Test
    @DisplayName("Poisson est de catégorie FISH")
    void testFishCategory() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        assertEquals(FoodType.FISH, fish.getCategory());
    }

    @Test
    @DisplayName("Vin est de catégorie DRINK")
    void testWineCategory() {
        FoodItem wine = new FoodItem(FoodItemType.WINE);
        assertEquals(FoodType.DRINK, wine.getCategory());
    }

    @Test
    @DisplayName("Miel est de catégorie OTHER")
    void testHoneyCategory() {
        FoodItem honey = new FoodItem(FoodItemType.HONEY);
        assertEquals(FoodType.OTHER, honey.getCategory());
    }

    @Test
    @DisplayName("Ingrédient secret est de catégorie MAGIC")
    void testSecretIngredientCategory() {
        FoodItem secret = new FoodItem(FoodItemType.SECRET_INGREDIENT);
        assertEquals(FoodType.MAGIC, secret.getCategory());
    }

    // ========== TESTS DES SCORES NUTRITIONNELS ==========

    @Test
    @DisplayName("Sanglier a un score nutritionnel de 50")
    void testBoarNutritionalScore() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        assertEquals(50, boar.getNutritionalScore());
    }

    @Test
    @DisplayName("Poisson a un score nutritionnel de 35")
    void testFishNutritionalScore() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        assertEquals(35, fish.getNutritionalScore());
    }

    @Test
    @DisplayName("Lait de licorne a le plus haut score (100)")
    void testUnicornMilkHighScore() {
        FoodItem unicornMilk = new FoodItem(FoodItemType.UNICORN_MILK);
        assertEquals(100, unicornMilk.getNutritionalScore());
    }

    @Test
    @DisplayName("Ingrédient secret a le score maximal (500)")
    void testSecretIngredientMaxScore() {
        FoodItem secret = new FoodItem(FoodItemType.SECRET_INGREDIENT);
        assertEquals(500, secret.getNutritionalScore());
    }

    @Test
    @DisplayName("Sel a le score minimal (1)")
    void testSaltMinScore() {
        FoodItem salt = new FoodItem(FoodItemType.SALT);
        assertEquals(1, salt.getNutritionalScore());
    }

    // ========== TESTS PARAMÉTRIQUES ==========

    @ParameterizedTest
    @EnumSource(FoodItemType.class)
    @DisplayName("Tous les types de nourriture peuvent être instanciés")
    void testAllFoodTypesCanBeInstantiated(FoodItemType type) {
        assertDoesNotThrow(() -> new FoodItem(type));
    }

    @ParameterizedTest
    @EnumSource(FoodItemType.class)
    @DisplayName("Tous les aliments ont un nom non-null")
    void testAllFoodItemsHaveNames(FoodItemType type) {
        FoodItem food = new FoodItem(type);
        assertNotNull(food.getName());
    }

    @ParameterizedTest
    @EnumSource(FoodItemType.class)
    @DisplayName("Tous les aliments ont une catégorie")
    void testAllFoodItemsHaveCategories(FoodItemType type) {
        FoodItem food = new FoodItem(type);
        assertNotNull(food.getCategory());
    }

    @ParameterizedTest
    @EnumSource(FoodItemType.class)
    @DisplayName("Tous les scores nutritionnels sont positifs")
    void testAllNutritionalScoresPositive(FoodItemType type) {
        FoodItem food = new FoodItem(type);
        assertTrue(food.getNutritionalScore() > 0);
    }

    // ========== TESTS DE TYPE ==========

    @Test
    @DisplayName("getType retourne le type correct")
    void testGetType() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        assertEquals(FoodItemType.BOAR, boar.getType());
    }

    @Test
    @DisplayName("Le type ne change jamais")
    void testTypeIsImmutable() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        FoodItemType initialType = fish.getType();
        
        fish.age();
        assertEquals(initialType, fish.getType());
    }
}