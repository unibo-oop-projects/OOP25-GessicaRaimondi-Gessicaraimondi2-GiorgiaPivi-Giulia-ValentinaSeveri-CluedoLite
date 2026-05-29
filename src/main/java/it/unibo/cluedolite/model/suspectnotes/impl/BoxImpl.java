package it.unibo.cluedolite.model.suspectnotes.impl;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.suspectnotes.api.*;

/*
 * Class representing a single entry in the suspect notes: it stores a card and its current state
 */

public class BoxImpl{
    private Card name;
    private State state;        

    public BoxImpl(Card name){
        this.name = name;
        this.state = State.POSSIBLE;  // default state before any card is excluded
    }

    // Marks this specific box as EXCLUDED, independently of the table or any player logic
    public void excludeCard(){              
        this.state = State.EXCLUDED;
    }

   public State getState(){
        return this.state;
    }

    public Card getCard(){
        return name;
    }
}