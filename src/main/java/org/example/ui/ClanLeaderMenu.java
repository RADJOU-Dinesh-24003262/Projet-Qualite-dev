package org.example.ui;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import org.example.model.Character.AbstractCharacter;
import org.example.model.Character.gallic.Blacksmith;
import org.example.model.Character.gallic.Druid;
import org.example.model.Character.gallic.Gallic;
import org.example.model.Character.gallic.Innkeeper;
import org.example.model.Character.gallic.Merchant;
import org.example.model.Character.roman.General;
import org.example.model.Character.roman.Legionary;
import org.example.model.Character.roman.Prefect;
import org.example.model.Character.roman.Roman;
import org.example.model.Character.werewolf.Werewolf;
import org.example.model.clanLeader.ClanLeader;
import org.example.model.food.FoodItem;
import org.example.model.food.FoodItemType;
import org.example.model.places.AbstractPlace;
import org.example.model.places.Battlefield;
import org.example.model.places.Enclosure;
import org.example.model.potion.Potion;

/**
 * Interactive menu system for managing clan leaders.
 * Allows users to control clan leaders and perform various actions.
 */
public class ClanLeaderMenu {

    private ClanLeader clanLeader;
    private Scanner scanner;
    private ArrayList<Battlefield> battlefields;
    private ArrayList<Enclosure> enclosures;

    /**
     * Constructs a new menu for managing a clan leader.
     *
     * @param clanLeader The clan leader to manage
     */
    public ClanLeaderMenu(ClanLeader clanLeader) {
        this.clanLeader = clanLeader;
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.battlefields = new ArrayList<>();
        this.enclosures = new ArrayList<>();
    }

    /**
     * Registers a battlefield for character transfers.
     *
     * @param battlefield The battlefield to register
     */
    public void registerBattlefield(Battlefield battlefield) {
        if (battlefield != null && !battlefields.contains(battlefield)) {
            battlefields.add(battlefield);
        }
    }

    /**
     * Registers an enclosure for character transfers.
     *
     * @param enclosure The enclosure to register
     */
    public void registerEnclosure(Enclosure enclosure) {
        if (enclosure != null && !enclosures.contains(enclosure)) {
            enclosures.add(enclosure);
        }
    }

    /**
     * Displays the main menu and handles user input.
     */
    public void displayMainMenu() {
        boolean running = true;

        while (running) {
            displayHeader();
            displayMenuOptions();

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> examinePlace();
                case 2 -> createCharacterMenu();
                case 3 -> healCharactersMenu();
                case 4 -> feedCharactersMenu();
                case 5 -> createPotionMenu();
                case 6 -> givePotionMenu();
                case 7 -> transferCharacterMenu();
                case 8 -> addFoodToPlace();
                case 9 -> displayStatistics();
                case 0 -> running = false;
                default -> System.out.println("❌ Invalid choice. Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        System.out.println("\n👋 Goodbye, " + clanLeader.getName() + "!");
    }

    private void displayHeader() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("🏛️  CLAN LEADER MANAGEMENT SYSTEM");
        System.out.println("═".repeat(70));
        System.out.println("Leader: " + clanLeader.getName() + " | Age: " + clanLeader.getAge() 
                          + " | Place: " + clanLeader.getPlace().getName());
        System.out.println("═".repeat(70));
    }

    private void displayMenuOptions() {
        System.out.println("\n📋 MAIN MENU:");
        System.out.println("  1. 🔍 Examine Place");
        System.out.println("  2. ➕ Create New Character");
        System.out.println("  3. 💚 Heal Characters");
        System.out.println("  4. 🍖 Feed Characters");
        System.out.println("  5. 🧪 Ask Druid for Potion");
        System.out.println("  6. 🍺 Give Potion to Character");
        System.out.println("  7. 🚚 Transfer Character");
        System.out.println("  8. 🥖 Add Food to Place");
        System.out.println("  9. 📊 Display Statistics");
        System.out.println("  0. 🚪 Exit");
        System.out.println();
    }

    private void examinePlace() {
        System.out.println("\n🔍 EXAMINING PLACE...\n");
        clanLeader.examinePlace();
    }

    private void createCharacterMenu() {
        System.out.println("\n➕ CREATE NEW CHARACTER\n");
        System.out.println("Select character type:");
        System.out.println("  1. Gallic characters");
        System.out.println("  2. Roman characters");
        System.out.println("  3. Werewolf");
        System.out.println("  0. Cancel");

        int typeChoice = getIntInput("\nYour choice: ");

        switch (typeChoice) {
            case 1 -> createGallicCharacterSubmenu();
            case 2 -> createRomanCharacterSubmenu();
            case 3 -> createWerewolfCharacter();
            case 0 -> System.out.println("Cancelled.");
            default -> System.out.println("❌ Invalid choice.");
        }
    }

    private void createGallicCharacterSubmenu() {
        System.out.println("\nSelect Gallic character class:");
        System.out.println("  1. Druid");
        System.out.println("  2. Blacksmith");
        System.out.println("  3. Innkeeper");
        System.out.println("  4. Merchant");
        System.out.println("  5. Generic Gallic");
        System.out.println("  0. Cancel");

        int classChoice = getIntInput("\nYour choice: ");
        
        if (classChoice == 0) {
            System.out.println("Cancelled.");
            return;
        }

        System.out.print("Enter character name: ");
        String name = scanner.nextLine();

        Class<? extends Gallic> characterClass = switch (classChoice) {
            case 1 -> Druid.class;
            case 2 -> Blacksmith.class;
            case 3 -> Innkeeper.class;
            case 4 -> Merchant.class;
            case 5 -> Gallic.class;
            default -> null;
        };

        if (characterClass != null) {
            clanLeader.createGallicCharacter(name, characterClass);
        } else {
            System.out.println("❌ Invalid choice.");
        }
    }

    private void createRomanCharacterSubmenu() {
        System.out.println("\nSelect Roman character class:");
        System.out.println("  1. General");
        System.out.println("  2. Legionary");
        System.out.println("  3. Prefect");
        System.out.println("  4. Generic Roman");
        System.out.println("  0. Cancel");

        int classChoice = getIntInput("\nYour choice: ");
        
        if (classChoice == 0) {
            System.out.println("Cancelled.");
            return;
        }

        System.out.print("Enter character name: ");
        String name = scanner.nextLine();

        Class<? extends Roman> characterClass = switch (classChoice) {
            case 1 -> General.class;
            case 2 -> Legionary.class;
            case 3 -> Prefect.class;
            case 4 -> Roman.class;
            default -> null;
        };

        if (characterClass != null) {
            clanLeader.createRomanCharacter(name, characterClass);
        } else {
            System.out.println("❌ Invalid choice.");
        }
    }

    private void createWerewolfCharacter() {
        System.out.print("Enter werewolf name: ");
        String name = scanner.nextLine();
        clanLeader.createWerewolf(name);
    }

    private void healCharactersMenu() {
        System.out.println("\n💚 HEAL CHARACTERS\n");
        System.out.println("  1. Heal all characters");
        System.out.println("  2. Heal specific character");
        System.out.println("  0. Cancel");

        int choice = getIntInput("\nYour choice: ");

        switch (choice) {
            case 1 -> clanLeader.healAllCharacters();
            case 2 -> healSpecificCharacter();
            case 0 -> System.out.println("Cancelled.");
            default -> System.out.println("❌ Invalid choice.");
        }
    }

    private void healSpecificCharacter() {
        AbstractCharacter character = selectCharacter();
        if (character != null) {
            clanLeader.healCharacter(character);
        }
    }

    private void feedCharactersMenu() {
        System.out.println("\n🍖 FEED CHARACTERS\n");
        
        if (clanLeader.getPlace().getNumberPresentFoods() == 0) {
            System.out.println("❌ No food available in this place!");
            return;
        }

        System.out.println("  1. Feed all characters");
        System.out.println("  2. Feed specific character");
        System.out.println("  0. Cancel");

        int choice = getIntInput("\nYour choice: ");

        switch (choice) {
            case 1 -> feedAllCharacters();
            case 2 -> feedSpecificCharacter();
            case 0 -> System.out.println("Cancelled.");
            default -> System.out.println("❌ Invalid choice.");
        }
    }

    private void feedAllCharacters() {
        FoodItem food = selectFood();
        if (food != null) {
            clanLeader.feedAllCharacters(food);
        }
    }

    private void feedSpecificCharacter() {
        AbstractCharacter character = selectCharacter();
        if (character == null) return;

        FoodItem food = selectFood();
        if (food == null) return;

        clanLeader.feedCharacter(character, food);
    }

    private void createPotionMenu() {
        System.out.println("\n🧪 ASK DRUID FOR POTION\n");

        Druid druid = findDruidInPlace();
        if (druid == null) {
            System.out.println("❌ No druid found in this place!");
            return;
        }

        Potion potion = clanLeader.askDruidForPotion(druid);
        
        if (potion != null) {
            System.out.println("\n✨ Customize potion? (y/n): ");
            String customize = scanner.nextLine().toLowerCase(Locale.ROOT);
            
            if (customize.equals("y")) {
                customizePotion(potion);
            }
        }
    }

    private void customizePotion(Potion potion) {
        boolean customizing = true;

        while (customizing) {
            System.out.println("\n🧪 POTION CUSTOMIZATION:");
            System.out.println("  1. Add Lobster (nutritious)");
            System.out.println("  2. Add Strawberries (nutritious)");
            System.out.println("  3. Replace Rock Oil with Beetroot Juice");
            System.out.println("  4. Add Two-headed Unicorn Milk (power of rolling)");
            System.out.println("  5. Add Idefix Hair (metamorphosis power)");
            System.out.println("  0. Finish customization");

            int choice = getIntInput("\nYour choice: ");

            switch (choice) {
                case 1 -> {
                    potion.addLobster();
                    System.out.println("✅ Lobster added!");
                }
                case 2 -> {
                    potion.addStrawberries();
                    System.out.println("✅ Strawberries added!");
                }
                case 3 -> {
                    potion.replaceRockOilWithBeetrootJuice();
                    System.out.println("✅ Rock oil replaced with beetroot juice!");
                }
                case 4 -> {
                    potion.addTwoHeadedUnicornMilk();
                    System.out.println("✅ Unicorn milk added!");
                }
                case 5 -> {
                    potion.addIdefixHair();
                    System.out.println("✅ Idefix hair added!");
                }
                case 0 -> customizing = false;
                default -> System.out.println("❌ Invalid choice.");
            }
        }

        System.out.println("\n✅ Final potion: " + potion.toString());
    }

    private void givePotionMenu() {
        System.out.println("\n🍺 GIVE POTION TO CHARACTER\n");

        Druid druid = findDruidInPlace();
        if (druid == null) {
            System.out.println("❌ No druid found to create potion!");
            return;
        }

        AbstractCharacter character = selectCharacter();
        if (character == null) return;

        Potion potion = clanLeader.askDruidForPotion(druid);
        if (potion != null) {
            clanLeader.givePotionToCharacter(character, potion);
        }
    }

    private void transferCharacterMenu() {
        System.out.println("\n🚚 TRANSFER CHARACTER\n");
        System.out.println("  1. Transfer to Battlefield");
        System.out.println("  2. Transfer to Enclosure");
        System.out.println("  0. Cancel");

        int choice = getIntInput("\nYour choice: ");

        switch (choice) {
            case 1 -> transferToBattlefield();
            case 2 -> transferToEnclosure();
            case 0 -> System.out.println("Cancelled.");
            default -> System.out.println("❌ Invalid choice.");
        }
    }

    private void transferToBattlefield() {
        if (battlefields.isEmpty()) {
            System.out.println("❌ No battlefields registered!");
            return;
        }

        AbstractCharacter character = selectCharacter();
        if (character == null) return;

        Battlefield battlefield = selectBattlefield();
        if (battlefield == null) return;

        clanLeader.transferToBattlefield(character, battlefield);
    }

    private void transferToEnclosure() {
        if (enclosures.isEmpty()) {
            System.out.println("❌ No enclosures registered!");
            return;
        }

        AbstractCharacter character = selectCharacter();
        if (character == null) return;

        if (!(character instanceof Werewolf)) {
            System.out.println("❌ Only werewolves can be transferred to enclosures!");
            return;
        }

        Enclosure enclosure = selectEnclosure();
        if (enclosure == null) return;

        clanLeader.transferToEnclosure(character, enclosure);
    }

    private void addFoodToPlace() {
        System.out.println("\n🥖 ADD FOOD TO PLACE\n");
        System.out.println("Select food type:");

        FoodItemType[] foodTypes = FoodItemType.values();
        for (int i = 0; i < foodTypes.length; i++) {
            System.out.println("  " + (i + 1) + ". " + foodTypes[i].getName());
        }
        System.out.println("  0. Cancel");

        int choice = getIntInput("\nYour choice: ");

        if (choice > 0 && choice <= foodTypes.length) {
            FoodItem food = new FoodItem(foodTypes[choice - 1]);
            clanLeader.getPlace().addFood(food);
            System.out.println("✅ Added " + food.getName() + " to the place!");
        } else if (choice != 0) {
            System.out.println("❌ Invalid choice.");
        }
    }

    private void displayStatistics() {
        System.out.println("\n📊 STATISTICS\n");
        AbstractPlace place = clanLeader.getPlace();
        
        System.out.println("Place: " + place.getName());
        System.out.println("Type: " + place.getType());
        System.out.println("Surface: " + place.getSurface() + " m²");
        System.out.println("\nCharacters: " + place.getNumberPresentCharacters());
        System.out.println("Food items: " + place.getNumberPresentFoods());
        
        int totalHealth = 0;
        int totalHunger = 0;
        for (AbstractCharacter character : place.getPresentCharacters()) {
            totalHealth += character.getHealth();
            totalHunger += character.getHunger();
        }
        
        if (place.getNumberPresentCharacters() > 0) {
            System.out.println("\nAverage Health: " + (totalHealth / place.getNumberPresentCharacters()));
            System.out.println("Average Hunger: " + (totalHunger / place.getNumberPresentCharacters()));
        }
    }

    // Helper methods

    private AbstractCharacter selectCharacter() {
        ArrayList<AbstractCharacter> characters = clanLeader.getPlace().getPresentCharacters();
        
        if (characters.isEmpty()) {
            System.out.println("❌ No characters in this place!");
            return null;
        }

        System.out.println("\nAvailable characters:");
        for (int i = 0; i < characters.size(); i++) {
            AbstractCharacter c = characters.get(i);
            System.out.println("  " + (i + 1) + ". " + c.getName() + " (" + c.getClass().getSimpleName() 
                              + ") - Health: " + c.getHealth());
        }

        int choice = getIntInput("\nSelect character (0 to cancel): ");
        
        if (choice > 0 && choice <= characters.size()) {
            return characters.get(choice - 1);
        }
        
        return null;
    }

    private FoodItem selectFood() {
        ArrayList<FoodItem> foods = clanLeader.getPlace().getPresentFoods();
        
        if (foods.isEmpty()) {
            System.out.println("❌ No food in this place!");
            return null;
        }

        System.out.println("\nAvailable food:");
        for (int i = 0; i < foods.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + foods.get(i).getName());
        }

        int choice = getIntInput("\nSelect food (0 to cancel): ");
        
        if (choice > 0 && choice <= foods.size()) {
            return foods.get(choice - 1);
        }
        
        return null;
    }

    private Druid findDruidInPlace() {
        for (AbstractCharacter character : clanLeader.getPlace().getPresentCharacters()) {
            if (character instanceof Druid) {
                return (Druid) character;
            }
        }
        return null;
    }

    private Battlefield selectBattlefield() {
        System.out.println("\nAvailable battlefields:");
        for (int i = 0; i < battlefields.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + battlefields.get(i).getName());
        }

        int choice = getIntInput("\nSelect battlefield (0 to cancel): ");
        
        if (choice > 0 && choice <= battlefields.size()) {
            return battlefields.get(choice - 1);
        }
        
        return null;
    }

    private Enclosure selectEnclosure() {
        System.out.println("\nAvailable enclosures:");
        for (int i = 0; i < enclosures.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + enclosures.get(i).getName());
        }

        int choice = getIntInput("\nSelect enclosure (0 to cancel): ");
        
        if (choice > 0 && choice <= enclosures.size()) {
            return enclosures.get(choice - 1);
        }
        
        return null;
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }
}