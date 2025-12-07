package org.example.Model.Potion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PotionTest {

    private Potion potion;

    @BeforeEach
    void setUp() {
        potion = new Potion();
    }

    // ========== TESTS DE LA RECETTE DE BASE ==========

    @Test
    @DisplayName("Potion de base contient 10 doses")
    void testInitialDoses() {
        // Une marmite contient 10 doses par défaut
        String result = potion.drinkDose();
        assertNotNull(result);
        assertTrue(result.contains("superhuman strength"));
    }

    @Test
    @DisplayName("ToString affiche les ingrédients de base")
    void testToStringContainsBaseIngredients() {
        String description = potion.toString();
        
        assertTrue(description.contains("Mistletoe"));
        assertTrue(description.contains("Carrots"));
        assertTrue(description.contains("Salt"));
        assertTrue(description.contains("Four-leaf clover"));
        assertTrue(description.contains("Fish"));
        assertTrue(description.contains("Rock oil"));
        assertTrue(description.contains("Honey"));
        assertTrue(description.contains("Mead"));
        assertTrue(description.contains("Secret ingredient"));
    }

    // ========== TESTS DES AJOUTS D'INGRÉDIENTS ==========

    @Test
    @DisplayName("Ajout de homard")
    void testAddLobster() {
        potion.addLobster();
        String description = potion.toString();
        assertTrue(description.contains("Lobster"));
    }

    @Test
    @DisplayName("Ajout de fraises")
    void testAddStrawberries() {
        potion.addStrawberries();
        String description = potion.toString();
        assertTrue(description.contains("Strawberries"));
    }

    @Test
    @DisplayName("Remplacement huile de roche par jus de betterave")
    void testReplaceRockOilWithBeetrootJuice() {
        potion.replaceRockOilWithBeetrootJuice();
        String description = potion.toString();
        
        assertFalse(description.contains("Rock oil"));
        assertTrue(description.contains("Beetroot juice"));
    }

    @Test
    @DisplayName("Ajout de lait de licorne à deux têtes")
    void testAddTwoHeadedUnicornMilk() {
        potion.addTwoHeadedUnicornMilk();
        String description = potion.toString();
        assertTrue(description.contains("Two-headed unicorn milk"));
    }

    @Test
    @DisplayName("Ajout de poils d'Idéfix")
    void testAddIdefixHair() {
        potion.addIdefixHair();
        String description = potion.toString();
        assertTrue(description.contains("Idefix hair"));
    }

    // ========== TESTS DES EFFETS ==========

    @Test
    @DisplayName("Boire une dose donne force surhumaine et invincibilité temporaire")
    void testDrinkDoseBasicEffects() {
        String effects = potion.drinkDose();
        
        assertTrue(effects.contains("superhuman strength"));
        assertTrue(effects.contains("temporary invincibility"));
    }

    @Test
    @DisplayName("Boire une dose avec lait de licorne ajoute pouvoir de dédoublement")
    void testDrinkDoseWithUnicornMilk() {
        potion.addTwoHeadedUnicornMilk();
        String effects = potion.drinkDose();
        
        assertTrue(effects.contains("superhuman strength"));
        assertTrue(effects.contains("power of rolling"));
    }

    @Test
    @DisplayName("Boire une dose avec poils d'Idéfix ajoute pouvoir de métamorphose")
    void testDrinkDoseWithIdefixHair() {
        potion.addIdefixHair();
        String effects = potion.drinkDose();
        
        assertTrue(effects.contains("superhuman strength"));
        assertTrue(effects.contains("metamorphosis"));
        assertTrue(effects.contains("lycanthrope"));
    }

    @Test
    @DisplayName("Boire une dose avec lait et poils d'Idéfix combine les pouvoirs")
    void testDrinkDoseWithBothSpecialIngredients() {
        potion.addTwoHeadedUnicornMilk();
        potion.addIdefixHair();
        String effects = potion.drinkDose();
        
        assertTrue(effects.contains("superhuman strength"));
        assertTrue(effects.contains("power of rolling"));
        assertTrue(effects.contains("metamorphosis"));
    }

    @Test
    @DisplayName("Boire toute la marmite rend les effets permanents")
    void testDrinkCauldronMakesPermanent() {
        String effects = potion.drinkCauldron();
        
        assertTrue(effects.contains("permanent"));
    }

    @Test
    @DisplayName("Boire toute la marmite avec ingrédients spéciaux")
    void testDrinkCauldronWithSpecialIngredients() {
        potion.addTwoHeadedUnicornMilk();
        potion.addIdefixHair();
        String effects = potion.drinkCauldron();
        
        assertTrue(effects.contains("permanent"));
        assertTrue(effects.contains("power of rolling"));
        assertTrue(effects.contains("metamorphosis"));
    }

    @Test
    @DisplayName("Boire deux marmites transforme en statue de granit")
    void testDrinkTwoCauldrons() {
        String effects = Potion.drinkTwoCauldrons();
        
        assertTrue(effects.contains("granite statue"));
    }

    // ========== TESTS DE GESTION DES DOSES ==========

    @Test
    @DisplayName("Marmite vide après avoir bu toutes les doses")
    void testEmptyCauldronAfterAllDoses() {
        // Boire les 10 doses
        for (int i = 0; i < 10; i++) {
            potion.drinkDose();
        }
        
        // La 11ème tentative devrait échouer
        String result = potion.drinkDose();
        assertEquals("The cauldron is empty.", result);
    }

    @Test
    @DisplayName("Marmite vide après avoir bu toute la marmite d'un coup")
    void testEmptyCauldronAfterDrinkingAll() {
        potion.drinkCauldron();
        
        String result = potion.drinkDose();
        assertEquals("The cauldron is empty.", result);
    }

    @Test
    @DisplayName("Impossible de boire une marmite vide")
    void testCannotDrinkEmptyCauldron() {
        potion.drinkCauldron(); // Vide la marmite
        
        String result = potion.drinkCauldron();
        assertEquals("The cauldron is empty.", result);
    }

    @Test
    @DisplayName("getMagicBoost retourne 50")
    void testGetMagicBoost() {
        assertEquals(50, potion.getMagicBoost());
    }

    // ========== TESTS DE COMBINAISONS ==========

    @Test
    @DisplayName("Potion complète avec tous les ingrédients optionnels")
    void testFullPotion() {
        potion.addLobster();
        potion.addStrawberries();
        potion.replaceRockOilWithBeetrootJuice();
        potion.addTwoHeadedUnicornMilk();
        potion.addIdefixHair();
        
        String description = potion.toString();
        
        // Ingrédients de base
        assertTrue(description.contains("Mistletoe"));
        assertTrue(description.contains("Secret ingredient"));
        
        // Ingrédients ajoutés
        assertTrue(description.contains("Lobster"));
        assertTrue(description.contains("Strawberries"));
        assertTrue(description.contains("Beetroot juice"));
        assertTrue(description.contains("Two-headed unicorn milk"));
        assertTrue(description.contains("Idefix hair"));
        
        // Ingrédient remplacé
        assertFalse(description.contains("Rock oil"));
    }

    @Test
    @DisplayName("Boire plusieurs doses diminue le compteur")
    void testMultipleDoses() {
        // Boire 3 doses
        for (int i = 0; i < 3; i++) {
            String result = potion.drinkDose();
            assertNotNull(result);
            assertFalse(result.equals("The cauldron is empty."));
        }
        
        // Il devrait rester 7 doses
        // On peut encore boire
        String result = potion.drinkDose();
        assertFalse(result.equals("The cauldron is empty."));
    }
}