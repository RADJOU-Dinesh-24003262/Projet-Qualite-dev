package org.example.Model.Character.Gallic;

import org.example.Model.Potion.Potion;

public class Druid extends Gallic {

    public Druid() {
        super();
    }

    public Druid(String name) {
        super();
        this.setName(name);
    }

    public Potion makePotion() {
        System.out.println(getName() + " prépare une potion magique...");
        return new Potion();
    }
}
