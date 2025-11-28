package org.example.Model.Places;

import java.util.ArrayList;

import org.example.Model.Character.AbstractCharacter;


public class Battlefield extends AbstractPlace {

    public Battlefield(String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
        super(TypePlace.enclosure, name, surface, validateCharacters(presentCharacters), presentFoods);
    }

    private static ArrayList<AbstractCharacter> validateCharacters(ArrayList<AbstractCharacter> characters) {
        for (AbstractCharacter character : characters) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            }
        }
        return characters;
    }
}
