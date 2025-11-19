package Character;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Food.FoodItem;
import org.example.Model.Food.FoodItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractCharacterTest {

    private TestCharacter attacker;
    private TestCharacter defender;
    private FoodItem boar;
    private FoodItem fish;

    // Classe de test concrète pour tester AbstractCharacter
    static class TestCharacter extends AbstractCharacter {
        public TestCharacter(String name) {
            super(name);
            this.health = 100;
            this.strength = 20;
            this.stamina = 10;
            this.hunger = 50;
            this.isAlive = true;
        }

        @Override
        protected FoodItemType[] getFoodEdibleList() {
            return new FoodItemType[]{FoodItemType.BOAR, FoodItemType.FISH};
        }
    }

    @BeforeEach
    void setUp() {
        attacker = new TestCharacter("Attacker");
        defender = new TestCharacter("Defender");
        boar = new FoodItem(FoodItemType.BOAR);
        fish = new FoodItem(FoodItemType.FISH);
    }

    // ========== TESTS DE COMBAT ==========

    @Test
    @DisplayName("Fight: Le défenseur perd de la santé selon la formule strength - stamina")
    void testFightReducesDefenderHealth() {
        int initialHealth = defender.getHealth();
        attacker.fight(defender);

        int expectedDamage = Math.max(attacker.getStrength() - defender.getStamina(), 0);
        assertEquals(initialHealth - expectedDamage, defender.getHealth());
    }

    @Test
    @DisplayName("Fight: Dégâts minimum à 0 si stamina > strength")
    void testFightNoDamageWhenStaminaHigher() {
        defender.setStamina(50);
        int initialHealth = defender.getHealth();

        attacker.fight(defender);

        assertEquals(initialHealth, defender.getHealth());
    }

    @Test
    @DisplayName("FightBoth: Les deux personnages perdent de la santé")
    void testFightBothDamagesBothCharacters() {
        int initialHealthAttacker = attacker.getHealth();
        int initialHealthDefender = defender.getHealth();

        attacker.mutualFight(defender);

        int expectedDamageToDefender = Math.max(0, attacker.getStrength() - defender.getStamina());
        int expectedDamageToAttacker = Math.max(0, defender.getStrength() - attacker.getStamina());

        assertEquals(initialHealthDefender - expectedDamageToDefender, defender.getHealth());
        assertEquals(initialHealthAttacker - expectedDamageToAttacker, attacker.getHealth());
    }

    @Test
    @DisplayName("Heal: Augmente la santé du personnage cible")
    void testHealIncreasesHealth() {
        defender.setHealth(50);
        int initialHealth = defender.getHealth();

        attacker.heal(defender);

        assertEquals(initialHealth + attacker.getStrength(), defender.getHealth());
    }

    // ========== TESTS DE NOURRITURE ==========

    @Test
    @DisplayName("CanEat: Retourne true pour nourriture comestible")
    void testCanEatValidFood() {
        assertTrue(attacker.canEat(boar));
        assertTrue(attacker.canEat(fish));
    }

    @Test
    @DisplayName("CanEat: Retourne false pour nourriture non comestible")
    void testCanEatInvalidFood() {
        FoodItem wine = new FoodItem(FoodItemType.WINE);
        assertFalse(attacker.canEat(wine));
    }

    @Test
    @DisplayName("CanEat: Gère les valeurs null")
    void testCanEatNullFood() {
        assertFalse(attacker.canEat(null));
    }

    @Test
    @DisplayName("EatFood: Verification de la méthode setHunger()")
    void testEatFoodBugWithHunger() {
        attacker.setHealth(100);
        attacker.setHunger(30);

        attacker.eatFood(boar);

        assertEquals(30 + boar.getNutritionalScore(), attacker.getHunger());
    }

    @Test
    @DisplayName("EatFood: Poisson frais vieillit après manipulation")
    void testEatFoodWithFreshFish() {
        assertTrue(fish.isFresh());
        fish.age();
        assertFalse(fish.isFresh());
    }

    // ========== TESTS DE POTIONS ==========
//
//    @Test
//    @DisplayName("DrinkPotion: BUG - Écrase le niveau au lieu d'ajouter")
//    void testDrinkPotionOverwritesLevel() {
//        attacker.setLevelMagicPotion(10);
//        Potion potion = new Potion(5);
//
//        attacker.drinkPotion(potion);
//
//        // BUG: devrait être 15 (10+5) mais écrase à 5
//        assertEquals(5, attacker.getLevelMagicPotion());
//    }

    // ========== TESTS DE MORT ==========

    @Test
    @DisplayName("Dead: Met tous les attributs à 0 et isAlive à false")
    void testDead() {
        attacker.die();

        assertEquals(0, attacker.getHealth());
        assertEquals(0, attacker.getStrength());
        assertEquals(0, attacker.getStamina());
        assertFalse(attacker.isAlive());
    }

    // ========== TESTS DE SETTERS ==========

    @Test
    @DisplayName("SetHealth: Ne peut pas être négatif (min 0)")
    void testSetHealthMinimumZero() {
        attacker.setHealth(-50);
        assertEquals(0, attacker.getHealth());
    }

    @Test
    @DisplayName("SetHealth: Accepte valeurs positives")
    void testSetHealthPositive() {
        attacker.setHealth(150);
        assertEquals(150, attacker.getHealth());
    }

    // ========== TESTS EQUALS & HASHCODE ==========

    @Test
    @DisplayName("Equals: Deux personnages identiques sont égaux")
    void testEquals() {
        TestCharacter char1 = new TestCharacter("Test");
        TestCharacter char2 = new TestCharacter("Test");

        char1.setAge(25);
        char2.setAge(25);

        assertEquals(char1, char2);
    }

    @Test
    @DisplayName("HashCode: Personnages égaux ont même hashcode")
    void testHashCode() {
        TestCharacter char1 = new TestCharacter("Test");
        TestCharacter char2 = new TestCharacter("Test");

        assertEquals(char1.hashCode(), char2.hashCode());
    }

    @Test
    @DisplayName("ToString: Retourne une représentation non-null")
    void testToString() {
        assertNotNull(attacker.toString());
        assertTrue(attacker.toString().contains("AbstractCharacter"));
    }
}