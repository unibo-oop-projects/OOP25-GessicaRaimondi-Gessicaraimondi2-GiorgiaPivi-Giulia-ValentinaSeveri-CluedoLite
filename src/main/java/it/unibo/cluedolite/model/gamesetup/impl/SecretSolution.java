package it.unibo.cluedolite.model.gamesetup.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.creationcards.impl.CardType;
import it.unibo.cluedolite.model.gamesetup.api.InterfaceSecretSolution;

/**
 * Implementation of the {@link InterfaceSecretSolution} interface.
 * This class generates a secret solution by randomly selecting one character card,
 * one weapon card, and one room card from the provided deck.
 * The selected cards are also removed from the deck so they cannot be dealt to players.
 */
public class SecretSolution implements InterfaceSecretSolution { 

    private final List<Card> solution = new ArrayList<>();
    private Card secretCharacter;
    private Card secretWeapon;
    private Card secretRoom;
    
    /**
     * Constructs a SecretSolution by shuffling the given cards
     * and selecting one card of each type.
     * @param cards the full deck of cards; the three selected cards will be removed from it
     */
    public SecretSolution(List<Card> cards) {
        Collections.shuffle(cards);
        generateSecretSolution(cards);
    }
    
    /**
     * Selects one card of each type from the shuffled deck
     * and removes them from the original list.
     * @param cards the shuffled deck of cards
     */
    private void generateSecretSolution(List<Card> cards) {
        secretCharacter = null;
        secretWeapon = null;
        secretRoom = null;

        for (Card card : cards) {
            if (card.getType() == CardType.CHARACTER && secretCharacter == null) {
                secretCharacter = card;
                solution.add(secretCharacter);
            } else if (card.getType() == CardType.WEAPON && secretWeapon == null) {
                secretWeapon = card;
                solution.add(secretWeapon);
            } else if (card.getType() == CardType.ROOM && secretRoom == null) {
                secretRoom = card;
                solution.add(secretRoom);
            }
        }
        cards.remove(secretCharacter);
        cards.remove(secretWeapon);
        cards.remove(secretRoom);
    }

    @Override
    public List<Card> getSolution() {
        return solution;
    }
}