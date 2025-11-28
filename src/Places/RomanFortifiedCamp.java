package Places;

import java.util.ArrayList;

import Places.AbstractPlace;
import Character.Werewolves;
import Character.Roman.RomanLegionary;
import Character.Roman.General;
import Character.AbstractCharacter;


public class RomanFortifiedCamp extends AbstractPlace {
        private String clanChief;

        public RomanFortifiedCamp(String clanChief, String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
            super(TypePlace.romanFortifiedCamp, name, surface, presentCharacters, presentFoods);
            this.clanChief = clanChief;
            for (AbstractCharacter character : presentCharacters) {
                if (character == null) {
                    throw new IllegalArgumentException("La liste contient un personnage null");
                }
                if (!(character instanceof RomanLegionary) && !(character instanceof General) && !(character instanceof Werewolves)) {
                    throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans un camp de retranché romain.");
                }
            }
        }

}
