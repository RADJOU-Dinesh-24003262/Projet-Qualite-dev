package org.example.Model.TheaterInvasion;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.ClanLeader.ClanLeader;
import org.example.Model.Places.AbstractPlace;

import java.util.ArrayList;

public class TheaterInvasion {
    private String theaterName;
    private int maxPlaces;
    private ArrayList<AbstractPlace> existantsPlaces;
    private ClanLeader clanLeader;

    public TheaterInvasion(String theaterName, int maxPlaces, ArrayList<AbstractPlace> existantsPlaces, ClanLeader clanLeader) {
        this.theaterName = theaterName;
        this.maxPlaces = maxPlaces;
        this.existantsPlaces = existantsPlaces;
        this.clanLeader = clanLeader;
    }

    // show places in the theater of invasion
    public ArrayList<AbstractPlace> showPlaces() {
        return existantsPlaces;
    }

    // retrun the number of character present in the theater of invasion
    public int getNumberPresentCharacter() {
        int numberPresentCharacterInTheaterInvasion = 0;
        for (int i = 0; i < existantsPlaces.size(); ++i) {
            numberPresentCharacterInTheaterInvasion += existantsPlaces.get(i).getNumberPresentCharacters();
        }
        return numberPresentCharacterInTheaterInvasion;
    }

    // return the characters of the theater of invasion
    public ArrayList<AbstractCharacter> getPresentCharactersTheaterInvasion(AbstractPlace place) {
        ArrayList<AbstractCharacter> presentCharactersInTheaterInvasion = new ArrayList<>();
        for (int i = 0; i < existantsPlaces.size(); ++i) {
            presentCharactersInTheaterInvasion.addAll(existantsPlaces.get(i).getPresentCharacters());
        }
        return presentCharactersInTheaterInvasion;
    }

    public int getMaxPlaces() {
        return maxPlaces;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public ArrayList<AbstractPlace> getExistantsPlaces() {
        return existantsPlaces;
    }

    public ClanLeader getClanLeader() {
        return clanLeader;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public void setMaxPlaces(int maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public void setExistantsPlaces(ArrayList<AbstractPlace> existantsPlaces) {
        this.existantsPlaces = existantsPlaces;
    }

    public void setClanLeader(ClanLeader clanLeader) {
        this.clanLeader = clanLeader;
    }
}


