package org.example.Model.Character.Werewolf;

public class WerewolfTransformationManager {

    private final Werewolf w;

    private static final double STR_MULT = 0.5;
    private static final double STA_MULT = 0.7;

    public WerewolfTransformationManager(Werewolf w) {
        this.w = w;
    }

    public void toHuman() {
        if (w.isHuman()) return;

        w.setHuman(true);
        w.setStrength((int) (w.getStrength() * STR_MULT));
        w.setStamina((int) (w.getStamina() * STA_MULT));

        System.out.println(w.getName() + " transforms into human form.");
    }

    public void toWerewolf() {
        if (!w.isHuman()) return;

        w.setHuman(false);
        w.setStrength((int) (w.getStrength() / STR_MULT));
        w.setStamina((int) (w.getStamina() / STA_MULT));

        System.out.println(w.getName() + " transforms back into werewolf form!");
    }
}
