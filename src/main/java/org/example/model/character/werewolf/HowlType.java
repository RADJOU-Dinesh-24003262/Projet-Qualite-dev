package org.example.model.character.werewolf;

/**
 * Represents the different types of howls a werewolf can perform.
 * Each howl type has a specific meaning and purpose within the pack.
 */
public enum HowlType {
    /**
     * A howl to assert authority and dominance over other werewolves.
     */
    DOMINATION("Domination howl - asserting authority"),
    /**
     * A howl to show respect and submission to a higher-ranking werewolf.
     */
    SUBMISSION("Submission howl - showing respect"),
    /**
     * A howl to warn of an impending attack or to show aggression.
     */
    AGGRESSION("Aggression howl - warning of attack"),
    /**
     * A howl to call out to the pack and signify belonging.
     */
    BELONGING("Belonging howl - calling to the pack"),
    /**
     * A howl to express happiness or contentment.
     */
    JOY("Joy howl - expressing happiness"),
    /**
     * A howl to express sorrow or grief.
     */
    SADNESS("Sadness howl - expressing sorrow");

    /**
     * A description of the howl's meaning and purpose.
     */
    private final String description;

    /**
     * Constructs a HowlType with a specific description.
     * @param d The description of the howl.
     */
    HowlType(String d) { description = d; }

    /**
     * Gets the description of the howl type.
     * @return The description string.
     */
    public String getDescription() { return description; }
}
