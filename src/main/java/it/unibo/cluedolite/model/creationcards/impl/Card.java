package it.unibo.cluedolite.model.creationcards.impl;

/**
 * Abstract class representing a generic card in the CluedoLite game.
 * Each card has a name and a type, and subclasses must implement {@link #getType()}.
 */
public abstract class Card {
    private final String name;

    /**
    * Constructs a Card with the given name.
    * @param name the name of the card
    */
    public Card(String name) {
        this.name = name;
    }
    
    /**
    * Returns the name of this card.
    * @return the card name
    */
    public String getName() {
        return name;
    }

    /**
    * Returns the type of this card.
    * @return the {@link CardType} of this card
    */
    public abstract CardType getType();

    /**
    * Returns the name of this card as a string.
    * @return the card name
    */
   @Override
    public String toString() {
        return name;
    }
}

