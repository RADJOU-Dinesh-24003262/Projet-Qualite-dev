package Places;

import Places.AbstractPlace;
import Character.Werewolves;

import java.sql.SQLOutput;
import java.util.ArrayList;
import Character.Gallic.Gallic;



public class GallicVillage extends AbstractPlace {
    private String clanChief;

    public GallicVillage(String clanChief, String name, int surface, ArrayList<Character> presentCharacters, ArrayList<String> presentFoods) {
        super(TypePlace.gallicVillage, name, surface, presentCharacters, presentFoods);
        this.clanChief = clanChief;

        if (presentCharacters.contains(! (character instanceof Gallic || character instanceof Werewolves))) {
            throw new Exception("Ce personnage : " + presentCharacters);

        }
    }
}
