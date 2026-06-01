package it.unibo.cluedolite.model.creationcards.impl;

import it.unibo.cluedolite.model.creationcards.api.InterfaceCard;

/*
* Abstaract class representing a card in the game, 
* with a name property and a constructor to initialize it, 
* and a getter method to retrieve the name of the card. 
* This class is extended
 */
public abstract class Card implements InterfaceCard{
    private final String name;

    public Card(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public abstract CardType getType();

    @Override
    public String toString() {
        return name;
    }
}

