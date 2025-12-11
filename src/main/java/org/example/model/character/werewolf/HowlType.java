package org.example.model.character.werewolf;

public enum HowlType {
    DOMINATION("Domination howl - asserting authority"),
    SUBMISSION("Submission howl - showing respect"),
    AGGRESSION("Aggression howl - warning of attack"),
    BELONGING("Belonging howl - calling to the pack"),
    JOY("Joy howl - expressing happiness"),
    SADNESS("Sadness howl - expressing sorrow");

    private final String description;

    HowlType(String d) { description = d; }

    public String getDescription() { return description; }
}
