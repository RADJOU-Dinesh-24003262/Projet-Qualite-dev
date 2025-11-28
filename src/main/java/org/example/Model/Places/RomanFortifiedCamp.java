package org.example.Model.Places;

import java.util.ArrayList;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Roman.General;
import org.example.Model.Character.Roman.Legionary;
import org.example.Model.Character.Werewolf;


public class RomanFortifiedCamp extends AbstractPlace {
    private String clanChief;

    public RomanFortifiedCamp(String clanChief, String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
        super(TypePlace.romanFortifiedCamp, name, surface, validateCharacters(presentCharacters), presentFoods);
        this.clanChief = clanChief;
    }

    private static ArrayList<AbstractCharacter> validateCharacters(ArrayList<AbstractCharacter> characters) {
        for (AbstractCharacter character : characters) {
            if (character == null) {
                throw new IllegalArgumentException("La liste contient un personnage null");
            }
            if (!(character instanceof Legionary) && !(character instanceof General) && !(character instanceof Werewolf)) {
                throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans un camp de retranché romain.");
            }
        }
        return characters;
    }

    public String getClanChief() {
        return clanChief;
    }
}

