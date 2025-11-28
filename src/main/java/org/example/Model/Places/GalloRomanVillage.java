package org.example.Model.Places;

import org.example.Model.Character.Roman.*;
import org.example.Model.Character.Gallic.*;
import java.util.ArrayList;
import org.example.Model.Character.AbstractCharacter;

public class GalloRomanVillage extends AbstractPlace{
    private String clanChief;

    public GalloRomanVillage(String clanChief, String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
        super(TypePlace.galloRomanVillage, name, surface, presentCharacters, presentFoods);
        this.clanChief = clanChief;
        for (AbstractCharacter character : presentCharacters) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            }
            if (!(character instanceof Roman) && !(character instanceof Gallic)) {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans une bourgade gallo-romaine.");
            }
        }
    }
}
