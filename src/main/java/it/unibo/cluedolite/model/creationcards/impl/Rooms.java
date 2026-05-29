package it.unibo.cluedolite.model.creationcards.impl;

/*
* Class representing a room card in the game, inherits from Card
 */
public class Rooms extends Card{ 
     public Rooms(String name) {
         super(name);
    }

    @Override
    public CardType getType() {
        return CardType.ROOM;
    }

}