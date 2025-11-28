package Places;

import Character.Werewolves;
import java.util.ArrayList;
import Character.AbstractCharacter;

public class Enclosure extends AbstractPlace {
    private String clanChief;

    public Enclosure(String clanChief, String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
        super(TypePlace.enclosure, name, surface, presentCharacters, presentFoods);
        this.clanChief = clanChief;
        for (AbstractCharacter character : presentCharacters) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            }
            if (!(character instanceof Werewolves)) {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans un enclos.");
            }
        }
    }
}
