package org.example.model.character.werewolf;

import java.util.Locale;

import org.example.model.pack.Pack;

/**
 * Manages the howling behavior for a {@link Werewolf}.
 * This class handles both initiating howls and reacting to howls from other werewolves,
 * adjusting the werewolf's state based on the social context of the howl.
 */
public class WerewolfHowlManager {

    /** The werewolf instance managed by this class. */
    private final Werewolf w;
    /** The health threshold below which a werewolf is too sick to react to howls. */
    private static final int SICK_THRESHOLD = 20;
    /** The amount by which belligerence increases in response to an aggression howl. */
    private static final int AGGRESSION_INCREASE = 10;

    /**
     * Constructs a howl manager for a specific werewolf.
     * @param w The werewolf whose howling behavior is to be managed.
     */
    public WerewolfHowlManager(Werewolf w) { this.w = w; }

    /**
     * Initiates a howl of a specific type.
     * The werewolf must be alive and in wolf form to howl.
     * If the werewolf is in a pack, the pack is notified of the howl.
     * @param type The {@link HowlType} of the howl to be performed.
     */
    public void howl(HowlType type) {
        if (!w.isAlive() || w.isHuman()) return;
        System.out.println(w.getName() + " howls: " + type.getDescription());

        Pack p = w.getPack();
        if (p != null) p.notifyHowl(w, type);
    }

    /**
     * Handles the werewolf's reaction to hearing a howl from another werewolf.
     * The werewolf will not react if dead, in human form, or too sick.
     * The reaction depends on the {@link HowlType}.
     * @param sender The {@link Werewolf} who initiated the howl.
     * @param type The type of howl that was heard.
     */
    public void hearHowl(Werewolf sender, HowlType type) {
        if (!w.isAlive() || w.getHealth() < SICK_THRESHOLD) return;

        System.out.println(w.getName() + " hears " + sender.getName() + "'s " + type.name().toLowerCase(Locale.ROOT));

        switch (type) {
            case DOMINATION -> handleDomination(sender);
            case SUBMISSION -> handleSubmission(sender);
            case AGGRESSION -> handleAggression(sender);
            case BELONGING -> handleBelonging(sender);
            case JOY, SADNESS -> System.out.println(w.getName() + " feels " + type.name().toLowerCase(Locale.ROOT));
        }
    }

    /**
     * Handles a howl of domination. The werewolf's domination factor decreases
     * if the sender has a higher rank.
     * @param sender The dominant werewolf who howled.
     */
    private void handleDomination(Werewolf sender) {
        if (w.getRank() < sender.getRank()) w.setDominationFactor(w.getDominationFactor() - 1);
    }

    /**
     * Handles a howl of submission. The werewolf's domination factor increases
     * if the sender has a lower rank.
     * @param sender The submissive werewolf who howled.
     */
    private void handleSubmission(Werewolf sender) {
        if (w.getRank() > sender.getRank())
            w.setDominationFactor(w.getDominationFactor() + 1);
    }

    @SuppressWarnings("unused")
    /**
     * Handles a howl of aggression. The werewolf may become more belligerent
     * based on its impetuosity factor.
     * @param sender The aggressive werewolf who howled.
     */
    private void handleAggression(Werewolf sender) {
        // sender parameter kept for consistency with other handle methods
        if (Math.random() < w.getImpetuosityFactor()) {
            w.setBelligerence(w.getBelligerence() + AGGRESSION_INCREASE);
        }
    }

    /**
     * Handles a howl of belonging. The werewolf responds if the sender is from the same pack.
     * @param sender The pack member who howled.
     */
    private void handleBelonging(Werewolf sender) {
        if (w.getPack() == sender.getPack())
            System.out.println(w.getName() + " answers the pack howl");
    }
}
