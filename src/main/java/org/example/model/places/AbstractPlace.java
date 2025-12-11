package org.example.model.places;

import java.util.ArrayList;

import org.example.model.character.AbstractCharacter;
import org.example.model.food.FoodItem;

/**
 * Abstract base class representing a place that can contain characters and food items.
 * Each place has specific rules about which types of characters can be present.
 */
public abstract sealed class AbstractPlace permits Battlefield, Enclosure, GallicVillage, GalloRomanVillage, RomanCity, RomanFortifiedCamp {
    private TypePlace type;
    private String name;
    private int surface;
    private String clanChief; // null for battlefields
    private ArrayList<AbstractCharacter> presentCharacters;
    private ArrayList<FoodItem> presentFoods;

    /**
     * Constructor for places with a clan chief
     * @param type The type of place
     * @param name The name of the place
     * @param surface The surface area
     * @param clanChief The name of the clan chief
     * @param presentCharacters Initial list of characters
     * @param presentFoods Initial list of food items
     */
    protected AbstractPlace(TypePlace type, String name, int surface, String clanChief,
                            ArrayList<? extends AbstractCharacter> presentCharacters, ArrayList<FoodItem> presentFoods) {
        this.type = type;
        this.name = name;
        this.surface = surface;
        this.clanChief = clanChief;
        this.presentCharacters = presentCharacters != null ? new ArrayList<>(presentCharacters) : new ArrayList<>();
        this.presentFoods = presentFoods != null ? presentFoods : new ArrayList<>();
        
        // Note: Initial character validation is deferred to avoid throwing exceptions in constructor
        // Characters added after construction will be validated via addCharacter()
    }

    /**
     * Constructor for places without a clan chief (battlefields)
     * @param type The type of place
     * @param name The name of the place
     * @param surface The surface area
     * @param presentCharacters Initial list of characters
     * @param presentFoods Initial list of food items
     */
    protected AbstractPlace(TypePlace type, String name, int surface,
                            ArrayList<AbstractCharacter> presentCharacters, ArrayList<FoodItem> presentFoods) {
        this(type, name, surface, null, presentCharacters, presentFoods);
    }

    /**
     * Template method to validate if a character can be added to this place.
     * Must be implemented by subclasses to define specific rules.
     * @param character The character to validate
     * @return true if the character is allowed, false otherwise
     */
    protected abstract boolean canContainCharacter(AbstractCharacter character);

    /**
     * Validates and adds a character to this place
     * @param character The character to add
     * @throws IllegalArgumentException if character is null or not allowed
     */
    public void addCharacter(AbstractCharacter character) {
        validateCharacter(character);
        presentCharacters.add(character);
    }

    /**
     * Validates if a character can be present in this place
     * @param character The character to validate
     * @throws IllegalArgumentException if character is null or not allowed
     */
    private void validateCharacter(AbstractCharacter character) {
        if (character == null) {
            throw new IllegalArgumentException("Character cannot be null");
        }
        if (!canContainCharacter(character)) {
            throw new IllegalArgumentException("Character of type " + character.getClass().getSimpleName() +
                    " cannot be present in " + type);
        }
    }

    /**
     * Removes a character from this place
     * @param character The character to remove
     */
    public void deleteCharacter(AbstractCharacter character) {
        boolean removed = presentCharacters.remove(character);
        if (!removed) {
            System.out.println("⚠️ Warning: Character " + (character != null ? character.getName() : "null") + " was not found in place " + this.getName() + " and could not be removed.");
        }
    }

    /**
     * Heals a character present in this place
     * @param character The character to heal
     */
    public void healCharacter(AbstractCharacter character) {
        if (presentCharacters.contains(character)) {
            character.heal(character);
        }
    }

    /**
     * Feeds a character with a food item present in this place
     * @param character The character to feed
     * @param food The food item to consume
     */
    public void feedCharacter(AbstractCharacter character, FoodItem food) {
        if (presentCharacters.contains(character) && presentFoods.contains(food)) {
            character.eatFood(food);
            presentFoods.remove(food);
        }
    }

    /**
     * Heals all characters present in this place
     */
    public void healAllCharacters() {
        for (AbstractCharacter character : presentCharacters) {
            character.heal(character);
        }
    }

    /**
     * Displays all characteristics of this place, including characters and food
     */
    public void displayCharacteristics() {
        System.out.println("=== " + name + " ===");
        System.out.println("Type: " + type);
        System.out.println("Surface: " + surface + " m²");
        if (clanChief != null) {
            System.out.println("Clan Chief: " + clanChief);
        }
        System.out.println("Number of characters present: " + getNumberPresentCharacters());

        System.out.println("\n--- Characters ---");
        for (AbstractCharacter character : presentCharacters) {
            System.out.println(character.toString());
        }

        System.out.println("\n--- Food Items ---");
        System.out.println("Number of food items: " + getNumberPresentFoods());
        for (FoodItem food : presentFoods) {
            System.out.println("- " + food.toString());
        }
        System.out.println("==================\n");
    }

    // Getters
    public TypePlace getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getSurface() {
        return surface;
    }

    public String getClanChief() {
        return clanChief;
    }

    public int getNumberPresentCharacters() {
        return presentCharacters.size();
    }

    public ArrayList<AbstractCharacter> getPresentCharacters() {
        return new ArrayList<>(presentCharacters); // Return a copy for encapsulation
    }

    public int getNumberPresentFoods() {
        return presentFoods.size();
    }

    public ArrayList<FoodItem> getPresentFoods() {
        return new ArrayList<>(presentFoods); // Return a copy for encapsulation
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public void setClanChief(String clanChief) {
        this.clanChief = clanChief;
    }

    public void addFood(FoodItem food) {
        if (food != null) {
            presentFoods.add(food);
        }
    }

    public void removeFood(FoodItem food) {
        presentFoods.remove(food);
    }
}