package org.example.model.places;

/**
 * Enumeration of all possible place types in the game.
 * Each type has specific rules about which characters can be present.
 */
public enum TypePlace {
    /**
     * Gallic village - can contain Gauls and fantasy creatures (Werewolves)
     */
    gallicVillage,
    
    /**
     * Roman fortified camp - can contain Roman fighters (Legionaries, Generals) and fantasy creatures (Werewolves)
     */
    romanFortifiedCamp,
    
    /**
     * Roman city - can contain all Romans and fantasy creatures (Werewolves)
     */
    romanCity,
    
    /**
     * Gallo-Roman village - can contain Romans and Gauls (no fantasy creatures)
     */
    galloRomanVillage,
    
    /**
     * Enclosure - can only contain fantasy creatures (Werewolves)
     */
    enclosure,
    
    /**
     * Battlefield - can contain all character types
     */
    battlefield
}