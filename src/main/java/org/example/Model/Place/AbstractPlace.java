package org.example.Model.Place;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Food.FoodItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract base class representing a place in the game world.
 * Each place can contain characters and food items.
 */
public abstract class AbstractPlace {

    protected String name;
    protected int area;
    protected String clanLeaderName;
    protected List<AbstractCharacter> characters;
    protected List<FoodItem> foodItems;

    /**
     * Constructor for AbstractPlace.
     *
     * @param name the name of the place
     * @param area the surface area of the place
     */
    protected AbstractPlace(String name, int area) {
        this.name = name;
        this.area = area;
        this.characters = new ArrayList<>();
        this.foodItems = new ArrayList<>();
    }

    /**
     * Checks if a character can be added to this place.
     *
     * @param character the character to check
     * @return true if the character can be added, false otherwise
     */
    public abstract boolean canAddCharacter(AbstractCharacter character);

    /**
     * Adds a character to this place.
     *
     * @param character the character to add
     * @return true if successfully added, false otherwise
     */
    public boolean addCharacter(AbstractCharacter character) {
        if (canAddCharacter(character) && !characters.contains(character)) {
            characters.add(character);
            return true;
        }
        return false;
    }

    /**
     * Removes a character from this place.
     *
     * @param character the character to remove
     * @return true if successfully removed, false otherwise
     */
    public boolean removeCharacter(AbstractCharacter character) {
        return characters.remove(character);
    }

    /**
     * Adds a food item to this place.
     *
     * @param food the food item to add
     */
    public void addFood(FoodItem food) {
        foodItems.add(food);
    }

    /**
     * Removes a food item from this place.
     *
     * @param food the food item to remove
     * @return true if successfully removed, false otherwise
     */
    public boolean removeFood(FoodItem food) {
        return foodItems.remove(food);
    }

    /**
     * Heals all characters in this place.
     */
    public void healAllCharacters() {
        for (AbstractCharacter character : characters) {
            character.setHealth(character.getHealth() + 20);
        }
    }

    /**
     * Feeds all characters in this place if food is available.
     */
    public void feedAllCharacters() {
        if (foodItems.isEmpty()) {
            return;
        }

        for (AbstractCharacter character : characters) {
            if (!foodItems.isEmpty()) {
                FoodItem food = foodItems.get(0);
                if (character.canEat(food)) {
                    character.eatFood(food);
                    foodItems.remove(0);
                }
            }
        }
    }

    /**
     * Displays information about this place.
     *
     * @return a string representation of the place
     */
    public String displayInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(name).append(" ===\n");
        sb.append("Area: ").append(area).append(" m²\n");
        sb.append("Clan Leader: ").append(clanLeaderName != null ? clanLeaderName : "None").append("\n");
        sb.append("Characters: ").append(characters.size()).append("\n");
        
        for (int i = 0; i < characters.size(); i++) {
            AbstractCharacter c = characters.get(i);
            sb.append("  ").append(i + 1).append(". ").append(c.getName())
              .append(" (Health: ").append(c.getHealth())
              .append(", Strength: ").append(c.getStrength()).append(")\n");
        }
        
        sb.append("Food items: ").append(foodItems.size()).append("\n");
        for (int i = 0; i < foodItems.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(foodItems.get(i).getName()).append("\n");
        }
        
        return sb.toString();
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getClanLeaderName() {
        return clanLeaderName;
    }

    public void setClanLeaderName(String clanLeaderName) {
        this.clanLeaderName = clanLeaderName;
    }

    public List<AbstractCharacter> getCharacters() {
        return new ArrayList<>(characters);
    }

    public List<FoodItem> getFoodItems() {
        return new ArrayList<>(foodItems);
    }

    public int getCharacterCount() {
        return characters.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractPlace that)) return false;
        return area == that.area && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, area);
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", characters=" + characters.size() +
                ", food=" + foodItems.size() +
                '}';
    }
}