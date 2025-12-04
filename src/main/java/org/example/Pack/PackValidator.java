package org.example.Pack;

import org.example.Model.Character.AbstractCharacter;
import org.example.Model.Character.Werewolf.Werewolf;
import org.example.Model.Character.Werewolf.AgeCategory;

public class PackValidator {

    public void validatePackCreation(String name, Werewolf alphaMale, Werewolf alphaFemale) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Pack name cannot be null or empty");

        if (alphaMale == null || alphaFemale == null)
            throw new IllegalArgumentException("Alpha couple cannot be null");

        if (alphaMale.getSex() != AbstractCharacter.Sex.MALE)
            throw new IllegalArgumentException("Alpha male must be MALE");

        if (alphaFemale.getSex() != AbstractCharacter.Sex.FEMALE)
            throw new IllegalArgumentException("Alpha female must be FEMALE");

        if (alphaMale.getAgeCategory() != AgeCategory.ADULT)
            throw new IllegalArgumentException("Alpha male must be ADULT");

        if (alphaFemale.getAgeCategory() != AgeCategory.ADULT)
            throw new IllegalArgumentException("Alpha female must be ADULT");
    }
}
