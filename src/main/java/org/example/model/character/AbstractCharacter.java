package org.example.model.character;

import java.util.Arrays;
import java.util.Objects;

import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.example.model.potion.Potion;
import org.example.model.places.AbstractPlace;

/**
 * Abstract base class for all characters in the game.
 * <p>
 * This class defines the common attributes and behaviors for all character types
 * (Gallic, Roman, Werewolf, etc.). Characters have physical attributes like health,
 * strength, and stamina, and can perform actions like fighting, eating, and drinking potions.
 * </p>
 * <p>
 * Subclasses must implement {@link #getFoodEdibleList()} to specify which food types
 * the character can eat.
 * </p>
 */
public abstract class AbstractCharacter {

    /**
     * Enum representing the biological sex of a character.
     */
    public enum Sex {
        MALE, FEMALE
    }

    protected String name;

    protected Sex sex;

    protected float height;

    protected int age;

    protected int strength;

    protected int stamina;

    protected int health;

    protected int hunger;

    protected int belligerence;

    protected int levelMagicPotion;

    protected boolean isAlive;

    protected AbstractPlace originPlace;

    /**
     * Default constructor.
     */
    public AbstractCharacter() {
        this.isAlive = true;
    }

    /**
     * Constructs a character with a specific name.
     * @param name The name of the character.
     */
    public AbstractCharacter(String name) {
        this.name = name;
        this.isAlive = true;
    }

    /**
     * Constructs a character with specified attributes.
     * @param name The name of the character.
     * @param age The age of the character.
     * @param strength The strength of the character.
     * @param health The health of the character.
     */
    public AbstractCharacter(String name, int age, int strength, int health) {
        this.name = name;
        this.age = age;
        this.strength = strength;
        this.health = health;
        this.isAlive = true;
    }

    /**
     * Sets the character's place of origin.
     * @param place The place of origin.
     */
    public void setOriginPlace(AbstractPlace place) {
        this.originPlace = place;
    }

    /**
     * Gets the character's place of origin.
     * @return The place of origin.
     */
    public AbstractPlace getOriginPlace() {
        return this.originPlace;
    }

    /**
     * The character attacks another character. Damage is based on the attacker's strength
     * minus the defender's stamina.
     * @param other The character being attacked.
     */
    public void fight(AbstractCharacter other) {
        if (!this.isAlive() || this.getHealth() <= 0) {
            return;
        }

        int damage = Math.max(this.strength - other.stamina, 0);
        other.setHealth(other.getHealth() - damage);
    }

    /**
     * A mutual fight where both characters attack each other simultaneously.
     * @param other The other character in the fight.
     */
    public void mutualFight(AbstractCharacter other) {
        if (!this.isAlive() || this.getHealth() <= 0 || !other.isAlive() || other.getHealth() <= 0) {
            return;
        }

        int damageToOther = Math.max(0, this.strength - other.stamina);
        int damageToThis = Math.max(0, other.strength - this.stamina);

        other.setHealth(other.getHealth() - damageToOther);
        this.setHealth(this.getHealth() - damageToThis);
    }

    /**
     * Heals another character. The amount of health restored is based on this character's strength.
     * @param other The character to heal.
     */
    public void heal(AbstractCharacter other) {
        // TODO: Implement a more complex healing logic.
        other.setHealth(other.getHealth() + this.strength);
    }

    /**
     * Gets the list of food types that this character is allowed to eat.
     * Each subclass must provide its own implementation of this method.
     * @return An array of {@link FoodItemType} that the character can eat.
     */
    protected abstract FoodItemType[] getFoodEdibleList();

    /**
     * Checks if the character can eat a specific food item.
     * The check is based on the list provided by {@link #getFoodEdibleList()}.
     * @param food The food item to check.
     * @return {@code true} if the character can eat the food, {@code false} otherwise.
     */
    public boolean canEat(FoodItem food) {
        if (food == null || food.getType() == null) {
            return false; // Defensive null check
        }
        return Arrays.asList(getFoodEdibleList()).contains(food.getType());
    }

    /**
     * The character attempts to eat a food item. If the food is not edible or is not fresh
     * (if applicable), the character loses health. Otherwise, their hunger is satiated.
     * @param food The food item to eat.
     */
    public void eatFood(FoodItem food) {
        if (!canEat(food) || food.freshnessApplicable() && !food.isFresh()) {
            this.setHealth(getHealth() - 10);
        } else {
            this.setHunger(getHunger() + food.getNutritionalScore());
        }
    }

    /**
     * The character drinks a potion, increasing their magic potion level.
     * @param potion The potion to drink.
     */
    public void drinkPotion(Potion potion) {
        this.setLevelMagicPotion(getLevelMagicPotion() + potion.getMagicBoost());
    }

    /**
     * The character dies, resetting their stats and marking them as not alive.
     */
    public void die() {
        this.setHealth(0);
        this.setStrength(0);
        this.setStamina(0);
        this.isAlive = false;
    }

    /**
     * Gets the character's name.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the character's name.
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the character's sex.
     * @return The sex.
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Sets the character's sex.
     * @param sex The new sex.
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * Gets the character's height.
     * @return The height.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the character's height.
     * @param height The new height.
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Gets the character's age.
     * @return The age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the character's age.
     * @param age The new age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the character's strength.
     * @return The strength.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Sets the character's strength.
     * @param strength The new strength.
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Gets the character's stamina.
     * @return The stamina.
     */
    public int getStamina() {
        return stamina;
    }

    /**
     * Sets the character's stamina.
     * @param stamina The new stamina.
     */
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    /**
     * Gets the character's current health.
     * @return The health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the character's health, ensuring it does not drop below zero.
     * @param health The new health value.
     */
    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }

    /**
     * Gets the character's hunger level.
     * @return The hunger level.
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * Sets the character's hunger level.
     * @param hunger The new hunger level.
     */
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    /**
     * Gets the character's belligerence level.
     * @return The belligerence level.
     */
    public int getBelligerence() {
        return belligerence;
    }

    /**
     * Sets the character's belligerence level.
     * @param belligerence The new belligerence level.
     */
    public void setBelligerence(int belligerence) {
        this.belligerence = belligerence;
    }

    /**
     * Gets the character's magic potion level.
     * @return The magic potion level.
     */
    public int getLevelMagicPotion() {
        return levelMagicPotion;
    }

    /**
     * Sets the character's magic potion level.
     * @param levelMagicPotion The new magic potion level.
     */
    public void setLevelMagicPotion(int levelMagicPotion) {
        this.levelMagicPotion = levelMagicPotion;
    }

    /**
     * Checks if the character is alive.
     * @return {@code true} if the character is alive, {@code false} otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public String toString() {
        return "AbstractCharacter{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", height=" + height +
                ", age=" + age +
                ", strength=" + strength +
                ", stamina=" + stamina +
                ", health=" + health +
                ", hunger=" + hunger +
                ", belligerence=" + belligerence +
                ", levelMagicPotion=" + levelMagicPotion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractCharacter that)) return false;
        return Float.compare(height, that.height) == 0 && age == that.age && strength == that.strength && stamina == that.stamina && health == that.health && hunger == that.hunger && belligerence == that.belligerence && levelMagicPotion == that.levelMagicPotion && Objects.equals(name, that.name) && sex == that.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sex, height, age, strength, stamina, health, hunger, belligerence, levelMagicPotion);
    }
}
