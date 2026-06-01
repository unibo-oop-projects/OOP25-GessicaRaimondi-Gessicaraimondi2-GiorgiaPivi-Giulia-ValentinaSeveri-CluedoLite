package it.unibo.cluedolite.model.accuseandsuspect.impl;

import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicion;
import it.unibo.cluedolite.model.creationcards.impl.Card;

/*
 * This class represents a suspicion made by a player in the CluedoLite game. 
 * It encapsulates the character, weapon, and room that the player suspects to be involved in the crime. 
 * The class provides getter methods to retrieve the details of the suspicion.
 */
public final class Suspicion implements InterfaceSuspicion {
    private final Card suspectCharacter;
    private final Card suspectWeapon;
    private final Card suspectRoom; // The room where the player is currently located when making the suspicion

    public Suspicion (Card suspectCharacter, Card suspectWeapon, Card suspectRoom) {
        this.suspectCharacter = suspectCharacter;
        this.suspectWeapon = suspectWeapon;
        this.suspectRoom = suspectRoom;
    }

    public Card getCharacters() { 
        return suspectCharacter; 
    }

    public Card getWeapon() { 
        return suspectWeapon; 
    }

    public Card getRoom() { 
        return suspectRoom; 
    }

    @Override
    public String toString() {
        return "Suspect: " + suspectCharacter.getName() 
             + " with " + suspectWeapon.getName() 
             + " in the " + suspectRoom.getName();
    }
}