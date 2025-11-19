package Character.Gallic;

import org.example.Model.Character.Gallic.*;
import org.example.Model.Food.FoodItem;
import org.example.Model.Food.FoodItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GallicTest {

    private Gallic gallic;

    @BeforeEach
    void setUp() {
        gallic = new Gallic();
        gallic.setName("Asterix");
        gallic.setHealth(100);
        gallic.setStrength(30);
        gallic.setStamina(15);
    }

    // ========== TESTS DE NOURRITURE COMESTIBLE ==========

    @Test
    @DisplayName("Gallic peut manger du sanglier")
    void testCanEatBoar() {
        FoodItem boar = new FoodItem(FoodItemType.BOAR);
        assertTrue(gallic.canEat(boar));
    }

    @Test
    @DisplayName("Gallic peut manger du poisson")
    void testCanEatFish() {
        FoodItem fish = new FoodItem(FoodItemType.FISH);
        assertTrue(gallic.canEat(fish));
    }

    @Test
    @DisplayName("Gallic peut boire du vin")
    void testCanEatWine() {
        FoodItem wine = new FoodItem(FoodItemType.WINE);
        assertTrue(gallic.canEat(wine));
    }

    // ========== TESTS DE NOURRITURE NON-COMESTIBLE ==========

    @Test
    @DisplayName("Gallic ne peut pas manger du miel")
    void testCannotEatHoney() {
        FoodItem honey = new FoodItem(FoodItemType.HONEY);
        assertFalse(gallic.canEat(honey));
    }

    @Test
    @DisplayName("Gallic ne peut pas boire de l'hydromel")
    void testCannotEatMead() {
        FoodItem mead = new FoodItem(FoodItemType.MEAD);
        assertFalse(gallic.canEat(mead));
    }

    @Test
    @DisplayName("Gallic ne peut pas manger de homard")
    void testCannotEatLobster() {
        FoodItem lobster = new FoodItem(FoodItemType.LOBSTER);
        assertFalse(gallic.canEat(lobster));
    }

    @Test
    @DisplayName("Gallic ne peut pas manger d'ingrédient magique")
    void testCannotEatMagicIngredient() {
        FoodItem secret = new FoodItem(FoodItemType.SECRET_INGREDIENT);
        assertFalse(gallic.canEat(secret));
    }

    // ========== TESTS DES SOUS-CLASSES ==========

    @Test
    @DisplayName("Blacksmith hérite de Gallic")
    void testBlacksmithIsGallic() {
        Blacksmith blacksmith = new Blacksmith();
        assertInstanceOf(Gallic.class, blacksmith);
    }

    @Test
    @DisplayName("Druid hérite de Gallic")
    void testDruidIsGallic() {
        Druid druid = new Druid();
        assertInstanceOf(Gallic.class, druid);
    }

    @Test
    @DisplayName("Innkeeper hérite de Gallic")
    void testInnkeeperIsGallic() {
        Innkeeper innkeeper = new Innkeeper();
        assertInstanceOf(Gallic.class, innkeeper);
    }

    @Test
    @DisplayName("Merchant hérite de Gallic")
    void testMerchantIsGallic() {
        Merchant merchant = new Merchant();
        assertInstanceOf(Gallic.class, merchant);
    }

    // ========== TESTS DE LA LISTE D'ALIMENTS ==========

//    @Test
//    @DisplayName("Liste d'aliments Gallic contient exactement 3 éléments")
//    void testFoodListSize() {
//        FoodItemType[] edibleList = gallic.getFoodEdibleList();
//        assertEquals(3, edibleList.length);
//    }

//    @Test
//    @DisplayName("Liste d'aliments Gallic contient BOAR, FISH, WINE")
//    void testFoodListContents() {
//        FoodItemType[] edibleList = gallic.getFoodEdibleList();
//
//        assertTrue(containsType(edibleList, FoodItemType.BOAR));
//        assertTrue(containsType(edibleList, FoodItemType.FISH));
//        assertTrue(containsType(edibleList, FoodItemType.WINE));
//    }

    // Méthode helper
    private boolean containsType(FoodItemType[] array, FoodItemType type) {
        for (FoodItemType item : array) {
            if (item == type) return true;
        }
        return false;
    }
}