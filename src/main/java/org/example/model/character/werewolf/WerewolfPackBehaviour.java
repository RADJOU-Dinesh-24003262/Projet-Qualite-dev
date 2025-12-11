package org.example.model.character.werewolf;

import org.example.model.pack.Pack;

/**
 * Manages the pack-related behaviors for a {@link Werewolf}.
 * This class handles actions such as leaving a pack and the consequences of that action.
 */
public class WerewolfPackBehaviour {

    /**
     * The werewolf instance managed by this class.
     */
    private final Werewolf w;

    /**
     * Constructs a pack behavior manager for a specific werewolf.
     * @param w The werewolf whose pack behavior is to be managed.
     */
    public WerewolfPackBehaviour(Werewolf w) {
        this.w = w;
    }

    /**
     * Makes the werewolf leave its current pack.
     * If the werewolf is not in a pack, this method does nothing.
     * Otherwise, it removes the werewolf from the pack, resets its pack-related attributes
     * (pack, rank, domination factor), and prints a notification.
     */
    public void leavePack() {
        Pack p = w.getPack();
        if (p == null) return;

        p.removeWerewolf(w);
        w.setPack(null);
        w.setRank(0);
        w.setDominationFactor(0);

        System.out.println(w.getName() + " leaves pack " + p.getName());
    }
}
