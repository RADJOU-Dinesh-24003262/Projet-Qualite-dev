package org.example.Model.Character;

import org.example.Model.Character.Gallic.Druid;
import org.example.Model.Character.Gallic.Gallic;
import org.example.Model.Food.FoodItem;
import org.example.Model.Potion.Potion;

public class ClanLeader {

    public enum Sex {
        MALE, FEMALE
    }

    private String name;
    private Sex sex;
    private int age;
    // private Place place;

    public ClanLeader(String name, Sex sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    
    public void examinePlace() {
    /*
        - Examine le lieu du chef de clan.
        - Affiche les caractéristiques du lieu ainsi que la liste des personnages et des aliments.
    */
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
        // Soigne un personnage spécifique du lieu.
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
