package it.unibo.CluedoLite.model.player.impl;

import java.util.List;
import java.util.ArrayList;

import it.unibo.CluedoLite.model.creationcards.impl.Card;
import it.unibo.CluedoLite.model.player.api.CreationCharacter;
import it.unibo.CluedoLite.model.player.api.Player;
import it.unibo.CluedoLite.model.suspectnotes.impl.*;

/**
 * Represents a player in the game
 * Each player has a name and can choose exactly one character
 * The player also has a hand of cards that they can use during the game
 */

public class PlayerImpl implements Player{
    private final String name;
    private CreationCharacter character; // chosen character
    private final List<Card> hand; // cards in the player's hand
    private TableImpl table;
    private boolean eliminated;//true if the player made a wrong final accusation and can no longer take actions
    private boolean moved;

    public PlayerImpl(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.table = new TableImpl(this.hand);
        this.eliminated = false;
        this.moved=false;
    }
    
    public void chooseCharacter(CreationCharacterImpl character) {
        this.character = character;
    }

    /*
     * Returns the character chosen by the player
     */
    public CreationCharacter getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    // Adds a card to the player's hand
    public void addCard(Card card) {
        hand.add(card);
    }

    // Returns the list of cards in the player's hand
    public List<Card> getHand() {
        return hand;
    }

        public Card findMatchingCard(Card character, Card weapon, Card room) {
        for (Card card : getHand()) {
            if (card.getName().equals(character.getName()) ||
                card.getName().equals(weapon.getName())    ||
                card.getName().equals(room.getName())) {
            }
        }
        return null;
    }

    public void eliminate() {
        this.eliminated = true;
    }

    public boolean isEliminated() {
        return this.eliminated;
    }

    public void setMoved(boolean moved){
        this.moved=moved;
    }

    public boolean hasmoved(){
        return this.moved;
    }

    public void restore() {
        this.eliminated = false;
    }
}
