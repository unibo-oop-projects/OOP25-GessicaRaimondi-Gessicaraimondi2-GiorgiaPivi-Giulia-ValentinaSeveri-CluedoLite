package it.unibo.cluedolite.model.creationcards.impl;

/**
 * Represents a character card in the CluedoLite game.
 * Extends {@link Card} and returns {@link CardType#CHARACTER} as its type.
 */
public class Characters extends Card { 

    /**
    * Constructs a Characters card with the given name.
    * @param name the name of the character
    */
    public Characters(String name) {
        super(name);
    }

    @Override
    public CardType getType() {
        return CardType.CHARACTER;
    }
}