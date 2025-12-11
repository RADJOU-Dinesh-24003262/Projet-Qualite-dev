package org.example.model.character.werewolf;

import org.example.model.pack.Pack;

public class
WerewolfHowlManager {

    private final Werewolf w;
    private static final int SICK_THRESHOLD = 20;
    private static final int AGGRESSION_INCREASE = 10;

    public WerewolfHowlManager(Werewolf w) { this.w = w; }

    public void howl(HowlType type) {
        if (!w.isAlive() || w.isHuman()) return;
        System.out.println(w.getName() + " howls: " + type.getDescription());

        Pack p = w.getPack();
        if (p != null) p.notifyHowl(w, type);
    }

    public void hearHowl(Werewolf sender, HowlType type) {
        if (!w.isAlive() || w.getHealth() < SICK_THRESHOLD) return;

        System.out.println(w.getName() + " hears " + sender.getName() + "'s " + type.name().toLowerCase());

        switch (type) {
            case DOMINATION -> handleDomination(sender);
            case SUBMISSION -> handleSubmission(sender);
            case AGGRESSION -> handleAggression(sender);
            case BELONGING -> handleBelonging(sender);
            case JOY, SADNESS -> System.out.println(w.getName() + " feels " + type.name().toLowerCase());
        }
    }

    private void handleDomination(Werewolf sender) {
        if (w.getRank() < sender.getRank()) w.setDominationFactor(w.getDominationFactor() - 1);
    }

    private void handleSubmission(Werewolf sender) {
        if (w.getRank() > sender.getRank())
            w.setDominationFactor(w.getDominationFactor() + 1);
    }

    private void handleAggression(Werewolf sender) {
        if (Math.random() < w.getImpetuosityFactor())
            w.setBelligerence(w.getBelligerence() + AGGRESSION_INCREASE);
    }

    private void handleBelonging(Werewolf sender) {
        if (w.getPack() == sender.getPack())
            System.out.println(w.getName() + " answers the pack howl");
    }
}
