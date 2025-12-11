package org.example.ui;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.util.StringConverter;
import org.example.model.character.AbstractCharacter;
import org.example.model.character.gallic.*;
import org.example.model.character.roman.*;
import org.example.model.character.werewolf.Werewolf;

import java.util.List;
import java.util.Optional;

/**
 * Utility class containing methods to style UI components and create custom dialogs.
 */
public class UIStyles {

    /**
     * Creates a styled button with a specific background color and hover effect.
     *
     * @param text     Button label.
     * @param colorHex Hexadecimal color code (e.g., "#FF5500").
     * @return A styled Button instance.
     */
    public static Button createStyledButton(String text, String colorHex) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE); // Occupy full width
        btn.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5;");

        // Hover effects
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: derive(" + colorHex + ", 20%); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        return btn;
    }

    /**
     * Creates a styled toggle button.
     *
     * @param text     Button label.
     * @param colorHex Hexadecimal color code.
     * @return A styled ToggleButton instance.
     */
    public static ToggleButton createStyledToggleButton(String text, String colorHex) {
        ToggleButton btn = new ToggleButton(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5;");
        return btn;
    }

    /**
     * Displays a custom dialog with a ComboBox, circumventing ChoiceDialog limitations.
     *
     * @param title        Dialog title.
     * @param header       Header text.
     * @param contentLabel Label for the input.
     * @param items        List of items to select from.
     * @param converter    StringConverter to display items properly.
     * @param <T>          Type of the items.
     * @return An Optional containing the selected item, or empty if canceled.
     */
    public static <T> Optional<T> showCustomChoiceDialog(String title, String header, String contentLabel, List<T> items, StringConverter<T> converter) {
        Dialog<T> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label label = new Label(contentLabel);
        ComboBox<T> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setConverter(converter);
        comboBox.setMaxWidth(Double.MAX_VALUE);
        if (!items.isEmpty()) {
            comboBox.getSelectionModel().selectFirst();
        }

        content.getChildren().addAll(label, comboBox);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return comboBox.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        return dialog.showAndWait();
    }

    /**
     * Returns an emoji icon representing the character's class.
     * <p>
     * CORRECTION: We verify the most specific classes (Druid) BEFORE
     * the generic classes (Gallic) to avoid inheritance errors.
     * </p>
     *
     * @param c The character.
     * @return A string containing the emoji.
     */
    public static String getIconFor(AbstractCharacter c) {
        if (c instanceof Druid) return "🧪";
        if (c instanceof Gallic) return "🐓";
        if (c instanceof Legionary) return "🛡️";
        if (c instanceof Werewolf) return "🐺";

        return "👤";
    }
}