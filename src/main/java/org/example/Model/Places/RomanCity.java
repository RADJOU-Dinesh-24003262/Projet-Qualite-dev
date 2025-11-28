package org.example.Model.Places;

import org.example.Model.Character.Roman.*;
import java.util.ArrayList;
import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Werewolf;


public class RomanCity extends AbstractPlace{
    private String clanChief;

    public RomanCity(String clanChief, String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
        super(TypePlace.romanCity, name, surface, presentCharacters, presentFoods);
        this.clanChief = clanChief;
        for (AbstractCharacter character : presentCharacters) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            }
            if (!(character instanceof Roman) && !(character instanceof Werewolf)) {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans une ville romaine.");
            }
        }
    }
}
