package Places;

import java.util.ArrayList;
import Character.AbstractCharacter;
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
        presentCharacters.add(character);
        if (this.type == TypePlace.gallicVillage) {
            if (character instanceof Gallic || character instanceof Werewolves) {

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
