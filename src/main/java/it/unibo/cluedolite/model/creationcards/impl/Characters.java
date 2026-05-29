package it.unibo.cluedolite.model.creationcards.impl;

/*
* Class representing a character card in the game, inherits from Card
 */
public class Characters extends Card{ 
    public Characters(String name) {
        super(name);
    }

    @Override
    public CardType getType() {
        return CardType.CHARACTER;
    }
}