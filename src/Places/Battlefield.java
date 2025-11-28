package Places;

import Character.Werewolves;
import java.util.ArrayList;
import Character.AbstractCharacter;

public class Battlefield extends AbstractPlace {

    public Battlefield(String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
        super(TypePlace.enclosure, name, surface, presentCharacters, presentFoods);
        for (AbstractCharacter character : presentCharacters) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            }
        }
    }
}
