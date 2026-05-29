package it.unibo.cluedolite.model.player.impl;

import it.unibo.cluedolite.model.creationcards.impl.Characters;
import it.unibo.cluedolite.model.player.api.CreationCharacter;
/**
 * Represents a playable character in the game
 * Each character has a name 
 * and a unique color associated with it
 */
public class CreationCharacterImpl extends Characters implements CreationCharacter{
    private final String color;

    public CreationCharacterImpl(String name, String color) {
        super(name);
        this.color = color;      
    }

    public String getColor(){
        return color;
    }
    /*
     * Returns a string representation of the character,
     * including its name and color
     */
    @Override
    public String toString() {
        return getName() + "(" + color + ")";
    }
}
