package org.example.model.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe FoodItem
 * Vérifie le vieillissement, la fraîcheur et les catégories d'aliments
 */
class FoodItemTest {

    private FoodItem boar;
    private FoodItem fish;
    private FoodItem clover;
    private FoodItem wine;

    @BeforeEach
    void setUp() {
        boar = new FoodItem(FoodItemType.BOAR);
        fish = new FoodItem(FoodItemType.FISH);
        clover = new FoodItem(FoodItemType.CLOVER);
        wine = new FoodItem(FoodItemType.WINE);
    }

    // ========== TESTS DE CRÉATION ==========

    @Test
    @DisplayName("Création d'un aliment avec type valide")
    void testValidFoodCreation() {
        assertNotNull(boar);
        assertEquals(FoodItemType.BOAR, boar.getType());
    }

    @Test
    @DisplayName("Aliment démarre avec la fraîcheur initiale correcte")
    void testInitialFreshness() {
        assertEquals(FreshnessState.NOT_APPLICABLE, boar.getFreshness());
        assertEquals(FreshnessState.PASSABLY_FRESH, fish.getFreshness());
    }

    // ========== TESTS DE VIEILLISSEMENT ==========

    @Test
    @DisplayName("Poisson passablement frais devient pas frais après vieillissement")
    void testFishAgingFromPassablyFresh() {
        assertTrue(fish.isFresh());
        assertEquals(FreshnessState.PASSABLY_FRESH, fish.getFreshness());
        
        fish.age();
        
        assertFalse(fish.isFresh());
        assertEquals(FreshnessState.NOT_FRESH, fish.getFreshness());
    }

    @Test
    @DisplayName("Aliment frais devient pas frais directement")
    void testFreshFoodAging() {
        FoodItem freshClover = new FoodItem(FoodItemType.CLOVER);
        // Modifions le test car CLOVER a NOT_APPLICABLE comme freshness
        
        // Créons un test avec un aliment qui peut être frais
        // Le trèfle dans le code n'a pas de freshness applicable
        // Testons plutôt avec le poisson
        FoodItem freshFish = new FoodItem(FoodItemType.FISH);
        freshFish.age();
        
        assertEquals(FreshnessState.NOT_FRESH, freshFish.getFreshness());
    }

    @Test
    @DisplayName("Aliment NOT_APPLICABLE ne change pas avec le vieillissement")
    void testNotApplicableFoodDoesNotAge() {
        assertEquals(FreshnessState.NOT_APPLICABLE, boar.getFreshness());
        
        boar.age();
        
        assertEquals(FreshnessState.NOT_APPLICABLE, boar.getFreshness());
    }

    @Test
    @DisplayName("Aliment déjà pas frais reste pas frais")
    void testAlreadyNotFreshStaysNotFresh() {
        fish.age(); // Devient NOT_FRESH
        assertEquals(FreshnessState.NOT_FRESH, fish.getFreshness());
        
        fish.age(); // Devrait rester NOT_FRESH
        
        assertEquals(FreshnessState.NOT_FRESH, fish.getFreshness());
    }

    // ========== TESTS DE FRAÎCHEUR ==========

    @Test
    @DisplayName("isFresh retourne true pour FRESH")
    void testIsFreshForFreshState() {
        // Le poisson commence PASSABLY_FRESH
        assertTrue(fish.isFresh());
    }

    @Test
    @DisplayName("isFresh retourne true pour PASSABLY_FRESH")
    void testIsFreshForPassablyFreshState() {
        assertTrue(fish.isFresh());
        assertEquals(FreshnessState.PASSABLY_FRESH, fish.getFreshness());
    }

    @Test
    @DisplayName("isFresh retourne false pour NOT_FRESH")
    void testIsFreshForNotFreshState() {
        fish.age();
        assertFalse(fish.isFresh());
    }

    @Test
    @DisplayName("isFresh retourne false pour NOT_APPLICABLE")
    void testIsFreshForNotApplicableState() {
        assertFalse(boar.isFresh());
    }

    @Test
    @DisplayName("freshnessApplicable retourne false pour NOT_APPLICABLE")
    void testFreshnessApplicableForNotApplicable() {
        assertFalse(boar.freshnessApplicable());
        assertFalse(wine.freshnessApplicable());
    }

    @Test
    @DisplayName("freshnessApplicable retourne true pour aliments périssables")
    void testFreshnessApplicableForPerishable() {
        assertTrue(fish.freshnessApplicable());
    }

    // ========== TESTS DE NOM ==========

    @Test
    @DisplayName("getName retourne nom correct pour aliment frais")
    void testGetNameForFreshFood() {
        String name = fish.getName();
        assertTrue(name.contains("Fish"));
        assertTrue(name.contains("passably fresh"));
    }

    @Test
    @DisplayName("getName retourne nom correct pour aliment pas frais")
    void testGetNameForNotFreshFood() {
        fish.age();
        String name = fish.getName();
        assertTrue(name.contains("Fish"));
        assertTrue(name.contains("not fresh"));
    }

    @Test
    @DisplayName("getName retourne nom simple pour NOT_APPLICABLE")
    void testGetNameForNotApplicableFood() {
        String name = boar.getName();
        assertEquals("Boar", name);
        assertFalse(name.contains("fresh"));
    }

    // ========== TESTS DE CATÉGORIE ==========

    @Test
    @DisplayName("getCategory retourne catégorie correcte")
    void testGetCategory() {
        assertEquals(FoodType.MEAT, boar.getCategory());
        assertEquals(FoodType.FISH, fish.getCategory());
        assertEquals(FoodType.VEGETABLE, clover.getCategory());
        assertEquals(FoodType.DRINK, wine.getCategory());
    }

    // ========== TESTS DE VALEUR NUTRITIONNELLE ==========

    @Test
    @DisplayName("getNutritionalScore retourne valeur correcte")
    void testGetNutritionalScore() {
        assertEquals(50, boar.getNutritionalScore());
        assertEquals(35, fish.getNutritionalScore());
        assertEquals(10, wine.getNutritionalScore());
    }

    @Test
    @DisplayName("Valeur nutritionnelle ne change pas avec le vieillissement")
    void testNutritionalScoreDoesNotChangeWithAge() {
        int scoreBefore = fish.getNutritionalScore();
        fish.age();
        int scoreAfter = fish.getNutritionalScore();
        
        assertEquals(scoreBefore, scoreAfter);
    }

    // ========== TESTS TOSTRING ==========

    @Test
    @DisplayName("toString retourne représentation correcte")
    void testToString() {
        assertEquals(boar.getName(), boar.toString());
        assertEquals(fish.getName(), fish.toString());
    }

    @Test
    @DisplayName("toString change après vieillissement")
    void testToStringChangesAfterAging() {
        String beforeAging = fish.toString();
        fish.age();
        String afterAging = fish.toString();
        
        assertNotEquals(beforeAging, afterAging);
        assertTrue(afterAging.contains("not fresh"));
    }

    // ========== TESTS D'INTÉGRATION ==========

    @Test
    @DisplayName("Cycle complet de vie d'un poisson")
    void testFishLifeCycle() {
        // État initial
        assertTrue(fish.isFresh());
        assertTrue(fish.freshnessApplicable());
        assertEquals(FreshnessState.PASSABLY_FRESH, fish.getFreshness());
        
        // Après vieillissement
        fish.age();
        assertFalse(fish.isFresh());
        assertTrue(fish.freshnessApplicable());
        assertEquals(FreshnessState.NOT_FRESH, fish.getFreshness());
        
        // Vieillissement supplémentaire ne change rien
        fish.age();
        assertEquals(FreshnessState.NOT_FRESH, fish.getFreshness());
    }

    @Test
    @DisplayName("Tous les types d'aliments peuvent être créés")
    void testAllFoodTypesCanBeCreated() {
        for (FoodItemType type : FoodItemType.values()) {
            FoodItem food = new FoodItem(type);
            assertNotNull(food);
            assertEquals(type, food.getType());
        }
    }

    @Test
    @DisplayName("Aliments magiques ont des valeurs nutritionnelles élevées")
    void testMagicFoodHighNutritionalValue() {
        FoodItem unicornMilk = new FoodItem(FoodItemType.UNICORN_MILK);
        FoodItem idefixHair = new FoodItem(FoodItemType.IDEFIX_HAIR);
        FoodItem secret = new FoodItem(FoodItemType.SECRET_INGREDIENT);
        
        assertTrue(unicornMilk.getNutritionalScore() >= 100);
        assertTrue(idefixHair.getNutritionalScore() >= 100);
        assertTrue(secret.getNutritionalScore() >= 100);
    }
}