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

    public AbstractCharacter() {

    }

    public AbstractCharacter(String name) {
        this.name = name;
    }

    public void setOriginPlace(AbstractPlace place) {
        this.originPlace = place;
    }

    public AbstractPlace getOriginPlace() {
        return this.originPlace;
    }

    public void fight(AbstractCharacter other) {
        int damage = Math.max(this.strength - other.stamina, 0);
        other.setHealth(other.getHealth() - damage);
    }

    public void mutualFight(AbstractCharacter other) {
        int damageToOther = Math.max(0, this.strength - other.stamina);
        int damageToThis = Math.max(0, other.strength - this.stamina);

        other.setHealth(other.getHealth() - damageToOther);
        this.setHealth(this.getHealth() - damageToThis);
    }

    public void heal(AbstractCharacter other) {
        // TO DO : continue really
        other.setHealth(other.getHealth() + this.strength);
    }

    protected abstract FoodItemType[] getFoodEdibleList();

    // Public method to check if the food is edible
    public boolean canEat(FoodItem food) {
        if (food == null || food.getType() == null) {
            return false; // Defensive null check
        }
        // Convert array to List and check if it contains the type
        return Arrays.asList(getFoodEdibleList()).contains(food.getType());
    }


    public void eatFood(FoodItem food) {
        if (!canEat(food) || food.freshnessApplicable() && !food.isFresh()) {
            this.setHealth(getHealth() - 10);
        } else {
            this.setHunger(getHunger() + food.getNutritionalScore());
        }
    }

    public void drinkPotion(Potion potion) {
        this.setLevelMagicPotion(getLevelMagicPotion() + potion.getMagicBoost());
    }

    public void die() {
        this.setHealth(0);
        this.setStrength(0);
        this.setStamina(0);
        this.isAlive = false;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }


    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getBelligerence() {
        return belligerence;
    }

    public void setBelligerence(int belligerence) {
        this.belligerence = belligerence;
    }

    public int getLevelMagicPotion() {
        return levelMagicPotion;
    }

    public void setLevelMagicPotion(int levelMagicPotion) {
        this.levelMagicPotion = levelMagicPotion;
    }

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
