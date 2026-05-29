package it.unibo.cluedolite.model.accuseandsuspect.impl;

import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicion;
import it.unibo.cluedolite.model.creationcards.impl.Card;

/**
 * Represents a suspicion made by a player in the CluedoLite game.
 * Encapsulates the character, weapon, and room suspected to be involved in the crime.
 */
public final class Suspicion implements InterfaceSuspicion {
    private final Card suspectCharacter;
    private final Card suspectWeapon;
    private final Card suspectRoom; // The room where the player is currently located when making the suspicion

    /**
    * Constructs a Suspicion with the given character, weapon and room.
    * @param suspectCharacter the suspected character card
    * @param suspectWeapon    the suspected weapon card
    * @param suspectRoom      the room card where the suspicion is made
    */
    public Suspicion(Card suspectCharacter, Card suspectWeapon, Card suspectRoom) {
        this.suspectCharacter = suspectCharacter;
        this.suspectWeapon = suspectWeapon;
        this.suspectRoom = suspectRoom;
    }

    public Card getCharacter() { 
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