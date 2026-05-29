package it.unibo.cluedolite.model.player.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.player.api.CreationCharacter;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.model.suspectnotes.impl.*;

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

    public PlayerImpl(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.table = new TableImpl(this.hand);
        this.eliminated = false;
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

    @Override
    public Optional<Card> findMatchingCard(Card character, Card weapon, Card room) {
        final List<Card> shuffled = new ArrayList<>(hand);
        Collections.shuffle(shuffled);   // evita di rivelare sempre la stessa carta
        return shuffled.stream()
            .filter(c -> c.getName().equals(character.getName())
                    || c.getName().equals(weapon.getName())
                    || c.getName().equals(room.getName()))
            .findFirst();
    }

    public void eliminate() {
        this.eliminated = true;
    }

    public boolean isEliminated() {
        return this.eliminated;
    }

    public void restore() {
        this.eliminated = false;
    }

    public void clearHand() {
        hand.clear();
    }
}
