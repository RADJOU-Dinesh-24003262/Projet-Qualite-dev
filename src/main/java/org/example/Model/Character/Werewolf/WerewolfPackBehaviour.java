package org.example.Model.Character.Werewolf;

import org.example.Model.Pack.Pack;

public class WerewolfPackBehaviour {

    private final Werewolf w;

    public WerewolfPackBehaviour(Werewolf w) {
        this.w = w;
    }

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
