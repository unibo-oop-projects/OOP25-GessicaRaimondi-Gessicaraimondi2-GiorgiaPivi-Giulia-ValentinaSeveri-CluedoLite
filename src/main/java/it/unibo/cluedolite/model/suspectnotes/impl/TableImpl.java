package it.unibo.cluedolite.model.suspectnotes.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.cluedolite.model.creationcards.impl.*;
import it.unibo.cluedolite.model.gamesetup.impl.*;
import it.unibo.cluedolite.model.suspectnotes.api.*;


public class TableImpl implements Table{
    private final List<BoxImpl> rooms = new ArrayList<>();
    private final List<BoxImpl> characters = new ArrayList<>();
    private final List<BoxImpl> weapons = new ArrayList<>();

    // Builds the suspect notes table by creating a Box for each card and grouping them by type
    public TableImpl(List<Card> hand){
        for (Card name : Deck.getAllCards()) {      
            BoxImpl box = new BoxImpl(name);   
            searchType(name).add(box);
        }
        initializeTable(hand);
    }

    // Updates the table based on the player's hand
    public void initializeTable(List<Card> hand){   
        for(Card name : hand){           
            searchType(name).stream()
            .filter(box -> box.getCard().equals(name))
            .forEach(BoxImpl::excludeCard);
        }
    }

    // Returns the list corresponding to the card's type (characters, weapons, or rooms).
    public List<BoxImpl> searchType(Card name){
        if(name instanceof Characters){
            return characters;
        }else if(name instanceof Weapons){
            return weapons;
        }else{
            return rooms;
        }
    }

    // Checks whether the given card is already marked as EXCLUDED in the table.
    // Returns true if the card is already present with state EXCLUDED.
    public boolean alreadyExcluded(Card name){
        return searchType(name).stream()
                        .filter(box -> box.getCard().equals(name))
                        .anyMatch(box -> box.getState().equals(State.EXCLUDED));
    }
    
    // Excludes the box corresponding to the given card in the table
    public void updateTable(Card name){
        searchType(name).stream()
        .filter(box -> box.getCard().equals(name))
        .forEach(box -> box.excludeCard());
    }
}
