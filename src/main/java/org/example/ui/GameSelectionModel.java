package org.example.ui;

import org.example.model.character.AbstractCharacter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * A simple model to hold the application-wide selection state.
 * This allows different parts of the UI (e.g., PlacesView and ControlPanel)
 * to communicate about the currently selected character.
 */
public class GameSelectionModel {

    private final ObjectProperty<AbstractCharacter> selectedCharacter = new SimpleObjectProperty<>();

    public ObjectProperty<AbstractCharacter> selectedCharacterProperty() {
        return selectedCharacter;
    }

    public AbstractCharacter getSelectedCharacter() {
        return selectedCharacter.get();
    }

    public void setSelectedCharacter(AbstractCharacter character) {
        this.selectedCharacter.set(character);
    }

    public void clearSelection() {
        this.selectedCharacter.set(null);
    }
}
