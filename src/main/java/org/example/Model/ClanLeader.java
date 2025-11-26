package org.example.Model;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Place.AbstractPlace;
import org.example.Model.Place.Battlefield;
import org.example.Model.Place.Enclosure;
import org.example.Model.Potion.Potion;

import java.util.Objects;

/**
 * Represents a clan leader who manages a place and its characters.
 */
public class ClanLeader {

    /**
     * Enum representing the sex of the clan leader.
     */
    public enum Sex {
        MALE, FEMALE
    }

    private String name;
    private Sex sex;
    private int age;
    private AbstractPlace place;
    private int actionsRemainingThisTurn;
    private static final int MAX_ACTIONS_PER_TURN = 3;

    /**
     * Constructor for ClanLeader.
     *
     * @param name the name of the clan leader
     * @param sex the sex of the clan leader
     * @param age the age of the clan leader
     * @param place the place managed by this clan leader
     */
    public ClanLeader(String name, Sex sex, int age, AbstractPlace place) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.place = place;
        this.actionsRemainingThisTurn = MAX_ACTIONS_PER_TURN;
        place.setClanLeaderName(name);
    }

    /**
     * Examines the place by displaying its characteristics.
     *
     * @return information about the place
     */
    public String examinePlace() {
        return place.displayInfo();
    }

    /**
     * Creates a new character in the place.
     *
     * @param character the character to create
     * @return true if successfully added, false otherwise
     */
    public boolean createCharacter(AbstractCharacter character) {
        if (actionsRemainingThisTurn <= 0) {
            return false;
        }
        boolean success = place.addCharacter(character);
        if (success) {
            actionsRemainingThisTurn--;
        }
        return success;
    }

    /**
     * Heals all characters in the place.
     *
     * @return true if action was performed
     */
    public boolean healCharacters() {
        if (actionsRemainingThisTurn <= 0) {
            return false;
        }
        place.healAllCharacters();
        actionsRemainingThisTurn--;
        return true;
    }

    /**
     * Feeds all characters in the place.
     *
     * @return true if action was performed
     */
    public boolean feedCharacters() {
        if (actionsRemainingThisTurn <= 0) {
            return false;
        }
        place.feedAllCharacters();
        actionsRemainingThisTurn--;
        return true;
    }

    /**
     * Asks a druid to make a magic potion.
     *
     * @return a new Potion object if successful
     */
    public Potion requestMagicPotion() {
        if (actionsRemainingThisTurn <= 0) {
            return null;
        }
        actionsRemainingThisTurn--;
        return new Potion();
    }

    /**
     * Makes characters drink a magic potion.
     *
     * @param potion the potion to drink
     * @param character the character who will drink
     * @return true if successful
     */
    public boolean givePotionToCharacter(Potion potion, AbstractCharacter character) {
        if (actionsRemainingThisTurn <= 0 || !place.getCharacters().contains(character)) {
            return false;
        }
        character.drinkPotion(potion);
        actionsRemainingThisTurn--;
        return true;
    }

    /**
     * Transfers a character to a battlefield or enclosure.
     *
     * @param character the character to transfer
     * @param destination the destination place
     * @return true if successfully transferred
     */
    public boolean transferCharacter(AbstractCharacter character, AbstractPlace destination) {
        if (actionsRemainingThisTurn <= 0) {
            return false;
        }
        
        if (!(destination instanceof Battlefield) && !(destination instanceof Enclosure)) {
            return false;
        }

        if (place.removeCharacter(character)) {
            boolean added = destination.addCharacter(character);
            if (added) {
                actionsRemainingThisTurn--;
                return true;
            } else {
                place.addCharacter(character);
            }
        }
        return false;
    }

    /**
     * Resets actions for a new turn.
     */
    public void resetActionsForNewTurn() {
        this.actionsRemainingThisTurn = MAX_ACTIONS_PER_TURN;
    }

    // Getters and Setters

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AbstractPlace getPlace() {
        return place;
    }

    public void setPlace(AbstractPlace place) {
        this.place = place;
        if (place != null) {
            place.setClanLeaderName(this.name);
        }
    }

    public int getActionsRemainingThisTurn() {
        return actionsRemainingThisTurn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClanLeader that)) return false;
        return age == that.age && Objects.equals(name, that.name) && sex == that.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sex, age);
    }

    @Override
    public String toString() {
        return "ClanLeader{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", place=" + (place != null ? place.getName() : "None") +
                ", actionsRemaining=" + actionsRemainingThisTurn +
                '}';
    }
}