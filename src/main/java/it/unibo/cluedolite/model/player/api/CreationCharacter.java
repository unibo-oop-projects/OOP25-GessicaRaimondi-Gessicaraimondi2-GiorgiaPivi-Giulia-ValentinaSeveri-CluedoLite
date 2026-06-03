package it.unibo.cluedolite.model.player.api;

/**
 * Defines the contract for a playable character in CluedoLite.
 * A character has a name and a unique color.
 */
public interface CreationCharacter {

    /**
     * Returns the color associated with this character.
     *
     * @return the character's color
     */
    String getColor();

    /**
     * Returns the name of this character.
     *
     * @return the character's name
     */
    String getName();

    /**
     * Returns a string representation of this character,
     * typically including name and color.
     * 
     * @return a string representation of this character
     */
    @Override
    String toString();
}
