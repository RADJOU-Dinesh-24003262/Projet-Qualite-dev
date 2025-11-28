package Places;

import Places.AbstractPlace;
import Character.Werewolves;
import java.util.ArrayList;
import Character.Gallic.Gallic;
import Character.AbstractCharacter;



public class GallicVillage extends AbstractPlace {
    private String clanChief;

    public GallicVillage(String clanChief, String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
        super(TypePlace.gallicVillage, name, surface, presentCharacters, presentFoods);
        this.clanChief = clanChief;
        for (AbstractCharacter character : presentCharacters) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            }
            if (!(character instanceof Gallic) && !(character instanceof Werewolves)) {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans un village gaulois");
            }
        }
    }
}
