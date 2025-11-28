package org.example.Model.Places;

import org.example.Model.Character.Gallic.*;
import java.util.ArrayList;
import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Werewolf;

public class GallicVillage extends AbstractPlace {
    private String clanChief;

    public GallicVillage(String clanChief, String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
        super(TypePlace.gallicVillage, name, surface, presentCharacters, presentFoods);
        this.clanChief = clanChief;
        for (AbstractCharacter character : presentCharacters) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            }
            if (!(character instanceof Gallic) && !(character instanceof Werewolf)) {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans un village gaulois");
            }
        }
    }
}
