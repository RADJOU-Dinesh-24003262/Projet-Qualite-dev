package org.example.Model.ClanLeader;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Gallic.Druid;
import org.example.Model.Character.Gallic.Gallic;
import org.example.Model.Food.FoodItem;
import org.example.Model.Places.AbstractPlace;
import org.example.Model.Potion.Potion;

public final class ClanLeader {

    public enum Sex {
        MALE, FEMALE
    }

    private String name;
    private Sex sex;
    private int age;
    private AbstractPlace place;

    private static void validateParameters(String name, Sex sex, int age) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (sex == null) {
            throw new IllegalArgumentException("Sex cannot be null");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }

    public ClanLeader(String name, Sex sex, int age, AbstractPlace place) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.place = place;
        // Optionnel : Mettre à jour le nom du chef dans le lieu
        if (place != null) {
            place.setClanChief(this.name);
        }
    }

    
    public void examinePlace() {
        if (place != null) {
            place.displayCharacteristics();
        } else {
            System.out.println("Ce chef n'a pas de lieu attribué !");
        }
    }

    
    public Gallic createCharacter(String characterName) {
    /*
        - Crée un nouveau personnage (Gaulois) dans le lieu du chef de clan
        - parametre : *characterName* (nom du personnage à créer)
        - return : le personnage créé, ou null si le lieu ne correspond pas
    */
        return null;
    }

    public void healAllCharacters() {
        // Soigne tous les personnages du lieu.
    }

    public void healCharacter(AbstractCharacter character) {
        if (place != null) {
            System.out.println(name + " soigne tout le monde à " + place.getName());
            place.healAllCharacters();
        }
    }

    public AbstractPlace getPlace() {
        return place;
    }

    public void feedAllCharacters(FoodItem food) {
        // Nourris tous les personnages du lieu.
    }

    public void feedCharacter(AbstractCharacter character, FoodItem food) {
        // Nourris un personnage spécifique du lieu.
    }

    public Potion askDruidForPotion(Druid druid) {
        // Demande à un druide de préparer une potion magique
        return null;
    }

    public void givePotionToCharacter(AbstractCharacter character, Potion potion) {
        // Fait boire une potion magique à un personnage dans le lieu du chef de clan
    }

    public void transferToBattlefield(AbstractCharacter character, Object battlefield) {
        // Transfère un personnage vers un champ de bataille
    }

    public void transferToEnclosure(AbstractCharacter character, Object enclosure) {
        // Transfère un personnage vers un enclos
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ClanLeader{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }
}
