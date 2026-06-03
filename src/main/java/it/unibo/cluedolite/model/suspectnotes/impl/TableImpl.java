package it.unibo.cluedolite.model.suspectnotes.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.creationcards.impl.Characters;
import it.unibo.cluedolite.model.creationcards.impl.Weapons;
import it.unibo.cluedolite.model.gamesetup.impl.Deck;
import it.unibo.cluedolite.model.suspectnotes.api.Box;
import it.unibo.cluedolite.model.suspectnotes.api.State;
import it.unibo.cluedolite.model.suspectnotes.api.Table;

/**
 * Implementation of {@link Table}.
 * Tracks the state of each card in the suspect notes,
 * grouping them by type.
 */
public class TableImpl implements Table {

    private final List<Box> rooms = new ArrayList<>();
    private final List<Box> characters = new ArrayList<>();
    private final List<Box> weapons = new ArrayList<>();

    /**
     * Creates a new {@link TableImpl}, populating it with all cards
     * and initializing it based on the player's hand.
     *
     * @param hand the list of cards in the player's hand
     */
    public TableImpl(final List<Card> hand) {
        for (final Card name : Deck.getAllCards()) {
            getListByType(name).add(new BoxImpl(name));
        }
        initializeTable(hand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeTable(final List<Card> hand) {
        for (final Card name : hand) {
            getListByType(name).stream()
                .filter(box -> box.getCard().equals(name))
                .forEach(Box::excludeCard);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Box> searchType(final Card name) {
        return List.copyOf(getListByType(name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean alreadyExcluded(final Card name) {
        return getListByType(name).stream()
            .filter(box -> box.getCard().equals(name))
            .anyMatch(box -> box.getState().equals(State.EXCLUDED));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTable(final Card name) {
        getListByType(name).stream()
            .filter(box -> box.getCard().equals(name))
            .forEach(Box::excludeCard);
    }

    /**
     * Returns the mutable internal list corresponding to the card's type.
     *
     * @param name the card whose type determines the list to return
     * @return the mutable internal {@link List} of {@link Box}
     */
    private List<Box> getListByType(final Card name) {
        if (name instanceof Characters) {
            return characters;
        } else if (name instanceof Weapons) {
            return weapons;
        } else {
            return rooms;
        }
    }
}
