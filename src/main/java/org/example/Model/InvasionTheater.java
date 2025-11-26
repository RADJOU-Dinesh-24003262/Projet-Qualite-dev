package org.example.Model;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Place.AbstractPlace;
import org.example.Model.Place.Battlefield;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the invasion theater managing all places and clan leaders.
 * This is the main game world container.
 */
public class InvasionTheater {

    private String name;
    private int maxPlaces;
    private List<AbstractPlace> places;
    private List<ClanLeader> clanLeaders;

    /**
     * Constructor for InvasionTheater.
     *
     * @param name the name of the theater
     * @param maxPlaces the maximum number of places allowed
     */
    public InvasionTheater(String name, int maxPlaces) {
        this.name = name;
        this.maxPlaces = maxPlaces;
        this.places = new ArrayList<>();
        this.clanLeaders = new ArrayList<>();
    }

    /**
     * Adds a place to the theater.
     *
     * @param place the place to add
     * @return true if successfully added, false if theater is full
     */
    public boolean addPlace(AbstractPlace place) {
        if (places.size() >= maxPlaces) {
            return false;
        }
        return places.add(place);
    }

    /**
     * Removes a place from the theater.
     *
     * @param place the place to remove
     * @return true if successfully removed
     */
    public boolean removePlace(AbstractPlace place) {
        return places.remove(place);
    }

    /**
     * Adds a clan leader to the theater.
     *
     * @param clanLeader the clan leader to add
     * @return true if successfully added
     */
    public boolean addClanLeader(ClanLeader clanLeader) {
        return clanLeaders.add(clanLeader);
    }

    /**
     * Displays all places in the theater.
     *
     * @return a string representation of all places
     */
    public String displayPlaces() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(name).append(" - Places ===\n");
        sb.append("Total places: ").append(places.size()).append("/").append(maxPlaces).append("\n\n");

        for (int i = 0; i < places.size(); i++) {
            AbstractPlace place = places.get(i);
            sb.append(i + 1).append(". ").append(place.getName())
              .append(" (").append(place.getClass().getSimpleName()).append(")")
              .append(" - ").append(place.getCharacterCount()).append(" characters\n");
        }

        return sb.toString();
    }

    /**
     * Counts total characters in the theater.
     *
     * @return the total number of characters
     */
    public int getTotalCharacterCount() {
        int count = 0;
        for (AbstractPlace place : places) {
            count += place.getCharacterCount();
        }
        return count;
    }

    /**
     * Displays all characters in all places.
     *
     * @return a string representation of all characters
     */
    public String displayAllCharacters() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== All Characters in ").append(name).append(" ===\n");
        sb.append("Total: ").append(getTotalCharacterCount()).append(" characters\n\n");

        for (AbstractPlace place : places) {
            if (place.getCharacterCount() > 0) {
                sb.append("--- ").append(place.getName()).append(" ---\n");
                for (AbstractCharacter character : place.getCharacters()) {
                    sb.append("  • ").append(character.getName())
                      .append(" [").append(character.getClass().getSimpleName()).append("]")
                      .append(" - Health: ").append(character.getHealth())
                      .append(", Strength: ").append(character.getStrength()).append("\n");
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Gets all battlefields in the theater.
     *
     * @return list of battlefields
     */
    public List<Battlefield> getBattlefields() {
        List<Battlefield> battlefields = new ArrayList<>();
        for (AbstractPlace place : places) {
            if (place instanceof Battlefield) {
                battlefields.add((Battlefield) place);
            }
        }
        return battlefields;
    }

    /**
     * Resets all clan leaders' actions for a new turn.
     */
    public void resetAllClanLeadersActions() {
        for (ClanLeader leader : clanLeaders) {
            leader.resetActionsForNewTurn();
        }
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPlaces() {
        return maxPlaces;
    }

    public void setMaxPlaces(int maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public List<AbstractPlace> getPlaces() {
        return new ArrayList<>(places);
    }

    public List<ClanLeader> getClanLeaders() {
        return new ArrayList<>(clanLeaders);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvasionTheater that)) return false;
        return maxPlaces == that.maxPlaces && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maxPlaces);
    }

    @Override
    public String toString() {
        return "InvasionTheater{" +
                "name='" + name + '\'' +
                ", places=" + places.size() +
                ", clanLeaders=" + clanLeaders.size() +
                ", characters=" + getTotalCharacterCount() +
                '}';
    }
}