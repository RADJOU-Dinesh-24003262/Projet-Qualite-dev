package org.example.model.clanLeader;

import org.example.model.character.AbstractCharacter;
import org.example.model.character.gallic.Blacksmith;
import org.example.model.character.gallic.Druid;
import org.example.model.character.gallic.Gallic;
import org.example.model.character.gallic.Innkeeper;
import org.example.model.character.gallic.Merchant;
import org.example.model.character.roman.General;
import org.example.model.character.roman.Legionary;
import org.example.model.character.roman.Prefect;
import org.example.model.character.roman.Roman;
import org.example.model.character.werewolf.Werewolf;
import org.example.model.food.FoodItem;
import org.example.model.places.AbstractPlace;
import org.example.model.places.Battlefield;
import org.example.model.places.Enclosure;
import org.example.model.potion.Potion;

/**
 * Represents a clan leader who manages a specific place.
 * <p>
 * A clan leader can:
 * - Examine their place (display characteristics, characters, and food)
 * - Create new characters in their place
 * - Heal characters in their place
 * - Feed characters in their place
 * - Ask a druid to create a magic potion
 * - Give magic potion to characters
 * - Transfer characters to battlefields or enclosures
 * </p>
 */
public final class ClanLeader {

    /**
     * Enum representing the biological sex of the clan leader.
     */
    public enum Sex {
        MALE, FEMALE
    }

    private String name;
    private Sex sex;
    private int age;
    private AbstractPlace place;

    /**
     * Validates the constructor parameters.
     *
     * @param name The name of the clan leader
     * @param sex The sex of the clan leader
     * @param age The age of the clan leader
     * @param place The place managed by the clan leader
     * @throws IllegalArgumentException if any parameter is invalid
     */
    private static void validateParameters(String name, Sex sex, int age, AbstractPlace place) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (sex == null) {
            throw new IllegalArgumentException("Sex cannot be null");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (place == null) {
            throw new IllegalArgumentException("Place cannot be null");
        }
    }

    /**
     * Constructs a new clan leader.
     *
     * @param name The name of the clan leader
     * @param sex The sex of the clan leader
     * @param age The age of the clan leader
     * @param place The place managed by this clan leader
     */
    public ClanLeader(String name, Sex sex, int age, AbstractPlace place) {
        validateParameters(name, sex, age, place);
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.place = place;
    }

    /**
     * Examines the clan leader's place.
     * Displays all characteristics including characters and food items.
     */
    public void examinePlace() {
        if (place == null) {
            System.out.println("No place assigned to this clan leader.");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("🏛️  EXAMINATION BY CLAN LEADER: " + name);
        System.out.println("=".repeat(60));
        place.displayCharacteristics();
    }

    /**
     * Creates a new Gallic character in the clan leader's place.
     * Only works if the place can contain Gallic characters.
     *
     * @param characterName The name of the character to create
     * @param characterType The specific type of Gallic character
     * @return The created character, or null if creation failed
     */
    public Gallic createGallicCharacter(String characterName, Class<? extends Gallic> characterType) {
        if (place == null) {
            System.out.println("❌ Cannot create character: no place assigned.");
            return null;
        }

        try {
            Gallic newCharacter;
            int defaultAge = 25;
            int defaultStrength = 20;
            int defaultHealth = 100;

            if (characterType.equals(Druid.class)) {
                newCharacter = new Druid(characterName, 65, 15, 80);
            } else if (characterType.equals(Blacksmith.class)) {
                newCharacter = new Blacksmith(characterName, 40, 40, 110);
            } else if (characterType.equals(Innkeeper.class)) {
                newCharacter = new Innkeeper(characterName, 38, 10, 90);
            } else if (characterType.equals(Merchant.class)) {
                newCharacter = new Merchant(characterName, 42, 5, 85);
            } else {
                newCharacter = new Gallic(characterName, defaultAge, defaultStrength, defaultHealth);
            }

            newCharacter.setStamina(15);
            newCharacter.setHunger(50);
            newCharacter.setOriginPlace(this.place); // Set the origin place

            place.addCharacter(newCharacter);
            System.out.println("✅ Character '" + characterName + "' created successfully!");
            return newCharacter;

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Failed to create character: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new Roman character in the clan leader's place.
     * Only works if the place can contain Roman characters.
     *
     * @param characterName The name of the character to create
     * @param characterType The specific type of Roman character
     * @return The created character, or null if creation failed
     */
    public Roman createRomanCharacter(String characterName, Class<? extends Roman> characterType) {
        if (place == null) {
            System.out.println("❌ Cannot create character: no place assigned.");
            return null;
        }

        try {
            Roman newCharacter;
            int defaultAge = 28;
            int defaultStrength = 25;
            int defaultHealth = 100;

            if (characterType.equals(General.class)) {
                newCharacter = new General(characterName, 45, 60, 120);
            } else if (characterType.equals(Prefect.class)) {
                newCharacter = new Prefect(characterName, 40, 30, 100);
            } else {
                newCharacter = new Legionary(characterName, defaultAge, defaultStrength, defaultHealth);
            }

            newCharacter.setStamina(20);
            newCharacter.setHunger(50);
            newCharacter.setOriginPlace(this.place); // Set the origin place

            place.addCharacter(newCharacter);
            System.out.println("✅ Character '" + characterName + "' created successfully!");
            return newCharacter;

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Failed to create character: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new Werewolf in the clan leader's place.
     * Only works if the place can contain Werewolves.
     *
     * @param characterName The name of the werewolf to create
     * @return The created werewolf, or null if creation failed
     */
    public Werewolf createWerewolf(String characterName) {
        if (place == null) {
            System.out.println("❌ Cannot create character: no place assigned.");
            return null;
        }

        try {
            Werewolf newWerewolf = new Werewolf(characterName, 100, 40, 150);
            newWerewolf.setStamina(30);
            newWerewolf.setHunger(80);
            newWerewolf.setOriginPlace(this.place); // Set the origin place

            place.addCharacter(newWerewolf);
            System.out.println("✅ Werewolf '" + characterName + "' created successfully!");
            return newWerewolf;

        } catch (Exception e) {
            System.out.println("❌ Failed to create werewolf: " + e.getMessage());
            return null;
        }
    }

    /**
     * Heals all characters present in the clan leader's place.
     */
    public void healAllCharacters() {
        if (place == null) {
            System.out.println("❌ No place assigned.");
            return;
        }

        int count = place.getNumberPresentCharacters();
        if (count == 0) {
            System.out.println("ℹ️  No characters to heal.");
            return;
        }

        place.healAllCharacters();
        System.out.println("✅ All " + count + " character(s) have been healed!");
    }

    /**
     * Heals a specific character in the clan leader's place.
     *
     * @param character The character to heal
     */
    public void healCharacter(AbstractCharacter character) {
        if (place == null) {
            System.out.println("❌ No place assigned.");
            return;
        }

        if (character == null) {
            System.out.println("❌ Character cannot be null.");
            return;
        }

        if (!place.getPresentCharacters().contains(character)) {
            System.out.println("❌ Character '" + character.getName() + "' is not in this place.");
            return;
        }

        int healthBefore = character.getHealth();
        place.healCharacter(character);
        System.out.println("✅ Character '" + character.getName() + "' healed! Health: "
                          + healthBefore + " → " + character.getHealth());
    }

    /**
     * Feeds all characters in the place with a specific food item.
     *
     * @param food The food item to distribute
     */
    public void feedAllCharacters(FoodItem food) {
        if (place == null) {
            System.out.println("❌ No place assigned.");
            return;
        }

        if (food == null) {
            System.out.println("❌ Food cannot be null.");
            return;
        }

        int characterCount = place.getNumberPresentCharacters();
        if (characterCount == 0) {
            System.out.println("ℹ️  No characters to feed.");
            return;
        }

        int fedCount = 0;
        for (AbstractCharacter character : place.getPresentCharacters()) {
            if (character.canEat(food) && place.getPresentFoods().contains(food)) {
                place.feedCharacter(character, food);
                fedCount++;
                System.out.println("  ✓ Fed " + character.getName() + " with " + food.getName());
            }
        }

        System.out.println("✅ Fed " + fedCount + " character(s) with " + food.getName());
    }

    /**
     * Feeds a specific character with a food item.
     *
     * @param character The character to feed
     * @param food The food item to give
     */
    public void feedCharacter(AbstractCharacter character, FoodItem food) {
        if (place == null) {
            System.out.println("❌ No place assigned.");
            return;
        }

        if (character == null || food == null) {
            System.out.println("❌ Character and food cannot be null.");
            return;
        }

        if (!place.getPresentCharacters().contains(character)) {
            System.out.println("❌ Character '" + character.getName() + "' is not in this place.");
            return;
        }

        if (!place.getPresentFoods().contains(food)) {
            System.out.println("❌ Food '" + food.getName() + "' is not available in this place.");
            return;
        }

        if (!character.canEat(food)) {
            System.out.println("⚠️  Warning: " + character.getName() + " cannot eat " + food.getName());
        }

        int hungerBefore = character.getHunger();
        place.feedCharacter(character, food);
        System.out.println("✅ " + character.getName() + " ate " + food.getName()
                          + "! Hunger: " + hungerBefore + " → " + character.getHunger());
    }

    /**
     * Asks a druid to create a magic potion.
     *
     * @param druid The druid who will create the potion
     * @return A new magic potion, or null if the druid is not in the place
     */
    public Potion askDruidForPotion(Druid druid) {
        if (place == null) {
            System.out.println("❌ No place assigned.");
            return null;
        }

        if (druid == null) {
            System.out.println("❌ Druid cannot be null.");
            return null;
        }

        if (!place.getPresentCharacters().contains(druid)) {
            System.out.println("❌ Druid '" + druid.getName() + "' is not in this place.");
            return null;
        }

        Potion potion = new Potion();
        System.out.println("✅ Druid '" + druid.getName() + "' has prepared a magic potion!");
        System.out.println("   " + potion.toString());
        return potion;
    }

    /**
     * Gives a magic potion to a character in the clan leader's place.
     *
     * @param character The character who will drink the potion
     * @param potion The potion to give
     */
    public void givePotionToCharacter(AbstractCharacter character, Potion potion) {
        if (place == null) {
            System.out.println("❌ No place assigned.");
            return;
        }

        if (character == null || potion == null) {
            System.out.println("❌ Character and potion cannot be null.");
            return;
        }

        if (!place.getPresentCharacters().contains(character)) {
            System.out.println("❌ Character '" + character.getName() + "' is not in this place.");
            return;
        }

        int magicBefore = character.getLevelMagicPotion();
        character.drinkPotion(potion);
        System.out.println("✅ " + character.getName() + " drank the magic potion!");
        System.out.println("   Magic level: " + magicBefore + " → " + character.getLevelMagicPotion());
        System.out.println("   Effects: " + potion.drinkDose());
    }

    /**
     * Transfers a character from the clan leader's place to a battlefield.
     *
     * @param character The character to transfer
     * @param battlefield The destination battlefield
     */
    public void transferToBattlefield(AbstractCharacter character, Battlefield battlefield) {
        if (place == null) {
            System.out.println("❌ No place assigned.");
            return;
        }

        if (character == null || battlefield == null) {
            System.out.println("❌ Character and battlefield cannot be null.");
            return;
        }

        if (!place.getPresentCharacters().contains(character)) {
            System.out.println("❌ Character '" + character.getName() + "' is not in this place.");
            return;
        }

        try {
            place.deleteCharacter(character);
            battlefield.addCharacter(character);
            System.out.println("✅ " + character.getName() + " transferred to battlefield '"
                              + battlefield.getName() + "'");
        } catch (Exception e) {
            System.out.println("❌ Transfer failed: " + e.getMessage());
            // Re-add character to original place if transfer failed
            place.addCharacter(character);
        }
    }

    /**
     * Transfers a character from the clan leader's place to an enclosure.
     * Only Werewolves can be transferred to enclosures.
     *
     * @param character The character to transfer (must be a Werewolf)
     * @param enclosure The destination enclosure
     */
    public void transferToEnclosure(AbstractCharacter character, Enclosure enclosure) {
        if (place == null) {
            System.out.println("❌ No place assigned.");
            return;
        }

        if (character == null || enclosure == null) {
            System.out.println("❌ Character and enclosure cannot be null.");
            return;
        }

        if (!(character instanceof Werewolf)) {
            System.out.println("❌ Only Werewolves can be transferred to enclosures!");
            return;
        }

        if (!place.getPresentCharacters().contains(character)) {
            System.out.println("❌ Character '" + character.getName() + "' is not in this place.");
            return;
        }

        try {
            place.deleteCharacter(character);
            enclosure.addCharacter(character);
            System.out.println("✅ Werewolf '" + character.getName() + "' transferred to enclosure '"
                              + enclosure.getName() + "'");
        } catch (Exception e) {
            System.out.println("❌ Transfer failed: " + e.getMessage());
            // Re-add character to original place if transfer failed
            place.addCharacter(character);
        }
    }

    /**
     * Returns a character from a source place back to the leader's home place.
     *
     * @param character The character to return.
     * @param sourcePlace The place the character is currently in.
     */
    public void returnCharacter(AbstractCharacter character, AbstractPlace sourcePlace) {
        System.out.println("DEBUG: returnCharacter called for " + (character != null ? character.getName() : "null") +
                           " from " + (sourcePlace != null ? sourcePlace.getName() : "null") +
                           " to leader's place " + (this.place != null ? this.place.getName() : "null"));
        if (character == null || sourcePlace == null) {
            System.out.println("DEBUG: returnCharacter - Character or source place is null. Aborting.");
            return;
        }

        if (!sourcePlace.getPresentCharacters().contains(character)) {
            System.out.println("DEBUG: returnCharacter - Character '" + character.getName() + "' is not in the source place '" + sourcePlace.getName() + "'. Aborting.");
            return;
        }

        try {
            System.out.println("DEBUG: Attempting to delete " + character.getName() + " from " + sourcePlace.getName());
            sourcePlace.deleteCharacter(character);
            System.out.println("DEBUG: Successfully deleted " + character.getName() + " from " + sourcePlace.getName());
            
            System.out.println("DEBUG: Attempting to add " + character.getName() + " to " + this.place.getName());
            this.place.addCharacter(character);
            System.out.println("DEBUG: Successfully added " + character.getName() + " to " + this.place.getName());
            System.out.println("✅ " + character.getName() + " has returned to " + this.place.getName());
        } catch (Exception e) {
            System.out.println("DEBUG: returnCharacter - Exception during return: " + e.getMessage());
            System.out.println("❌ Return failed: " + e.getMessage());
            // Re-add character to original place if return failed
            sourcePlace.addCharacter(character);
        }
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        if (sex == null) {
            throw new IllegalArgumentException("Sex cannot be null");
        }
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    public AbstractPlace getPlace() {
        // Return the place directly as AbstractPlace is managed externally
        // and defensive copying would break functionality
        return place;
    }

    public void setPlace(AbstractPlace place) {
        if (place == null) {
            throw new IllegalArgumentException("Place cannot be null");
        }
        this.place = place;
    }

    @Override
    public String toString() {
        return "ClanLeader{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", place=" + (place != null ? place.getName() : "None") +
                '}';
    }
}