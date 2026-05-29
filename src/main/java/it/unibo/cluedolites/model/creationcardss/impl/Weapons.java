package it.unibo.cluedolite.model.creationcards.impl;

/*
* Class representing a weapon card in the game, inherits from Card
 */
public class Weapons extends Card{ 
    public Weapons(String name) {
        super(name);
    }

    @Override
    public CardType getType() {
        return CardType.WEAPON;
    }
}