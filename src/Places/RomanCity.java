package Places;

import Character.Roman.Roman;
import java.util.ArrayList;
import Character.Werewolves;
import Character.AbstractCharacter;


public class RomanCity extends AbstractPlace{
        private String clanChief;

        public RomanCity(String clanChief, String name, int surface, ArrayList<AbstractCharacter> presentCharacters, ArrayList<String> presentFoods) {
            super(TypePlace.romanCity, name, surface, presentCharacters, presentFoods);
            this.clanChief = clanChief;
            for (AbstractCharacter character : presentCharacters) {
                if (character == null) {
                    throw new IllegalArgumentException("La liste contient un personnage null");
                }
                if (!(character instanceof Roman) && !(character instanceof Werewolves)) {
                    throw new IllegalArgumentException("Le personnage " + character.getClass().getName() + "ne peux pas être présent dans une ville romaine.");
                }
            }
        }
}
