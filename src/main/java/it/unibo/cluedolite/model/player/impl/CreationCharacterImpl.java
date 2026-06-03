package it.unibo.cluedolite.model.player.impl;

import it.unibo.cluedolite.model.creationcards.impl.Characters;
import it.unibo.cluedolite.model.player.api.CreationCharacter;

/**
 * Represents a playable character in the CluedoLite game.
 * Each character has a name and a unique color associated with it.
 */
public class CreationCharacterImpl extends Characters implements CreationCharacter {

    private final String color;

    /**
     * Constructs a new {@code CreationCharacterImpl} with the given name and color.
     *
     * @param name  the name of the character
     * @param color the color associated with the character
     */
    public CreationCharacterImpl(final String name, final String color) {
        super(name);
        this.color = color;
    }

    /**
     * Returns the color associated with this character.
     *
     * @return the character's color
     */
    @Override
    public String getColor() {
        return color;
    }

    /**
     * Returns a string representation of this character,
     * including its name and color.
     *
     * @return a string in the format {@code Name(COLOR)}
     */
    @Override
    public String toString() {
        return getName() + "(" + color + ")";
    }
}
