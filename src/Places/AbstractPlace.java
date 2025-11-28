package Places;

import java.util.ArrayList;
import Character.AbstractCharacter;
import Character.Roman.General;
import Character.Roman.RomanLegionary;
import Food.AbstractFood;
import Character.Werewolves;
import Character.Gallic.Gallic;

public abstract class AbstractPlace{
    private TypePlace type;
    private String name;
    private int surface;
    private ArrayList<AbstractCharacter> presentCharacters;
    private int numberPresentCharacters;
    private ArrayList<String> presentFoods;
    private int numberPresentFoods;

    protected AbstractPlace() {
    }

    public AbstractPlace(TypePlace type, String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
        this.type = type;
        this.name = name;
        this.surface = surface;
        this.presentCharacters = presentCharacters;
        this.numberPresentCharacters = presentCharacters.size();
        this.presentFoods = presentFoods;
    }

    public void addCharacter(AbstractCharacter character) {
        if (this.type == TypePlace.gallicVillage) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            } else if (character instanceof Gallic || character instanceof Werewolves) {
                presentCharacters.add(character);
            } else {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans un village gaulois");
            }
        }

        if (this.type == TypePlace.romanFortifiedCamp) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            } else if (character instanceof RomanLegionary || character instanceof General || character instanceof Werewolves) {
                presentCharacters.add(character);
            } else {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans un camp retranché romain");
            }
        }

        if (this.type == TypePlace.romanCity) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            } else if (character instanceof Roman || character instanceof Werewolves) {
                presentCharacters.add(character);
            } else {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans une ville romaine");
            }
        }

        if (this.type == TypePlace.galloRomanVillage) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            } else if (character instanceof Roman || character instanceof Gallic) {
                presentCharacters.add(character);
            } else {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans une bourgade gallo-romaine");
            }
        }

        if (this.type == TypePlace.enclosure) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            } else if (character instanceof Werewolves) {
                presentCharacters.add(character);
            } else {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans un camp retranché romain");
            }
        }

        if (this.type == TypePlace.battlefield) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            } else {
                presentCharacters.add(character);
            }
        }
    }

    public void deleteCharacter(AbstractCharacter character) {
        presentCharacters.remove(character.toString());
    }

    public void healCharacter(AbstractCharacter character) {
        character.heal();
    }

    public void feedCharacter(AbstractCharacter character, AbstractFood food) {
        character.eat(food);
        this.presentFoods.remove(food.toString());
    }
}
