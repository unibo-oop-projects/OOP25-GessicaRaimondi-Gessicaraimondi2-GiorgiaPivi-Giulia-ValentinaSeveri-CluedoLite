package it.unibo.cluedolite.model.creationcards.impl;

/**
 * Represents a room card in the CluedoLite game.
 * Extends {@link Card} and returns {@link CardType#ROOM} as its type.
 */
public class Rooms extends Card { 

    /**
    * Constructs a Rooms card with the given name.
    * @param name the name of the room
    */
     public Rooms(String name) {
         super(name);
    }

    @Override
    public CardType getType() {
        return CardType.ROOM;
    }

}