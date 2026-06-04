package it.unibo.cluedolite.model.player.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.model.player.api.CreationCharacter;
import it.unibo.cluedolite.model.player.api.Player;

/**
 * Implementation of {@link Player} representing a player in the CluedoLite game.
 * Each player has a name, can choose exactly one character,
 * and holds a hand of cards used during the game.
 */
public class PlayerImpl implements Player {

    private final String name;
    private CreationCharacter character;
    private final List<AbstractCard> hand;
    private boolean eliminated;

    /**
     * Constructs a new {@code PlayerImpl} with the given name.
     *
     * @param name the name of the player
     */
    public PlayerImpl(final String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.eliminated = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chooseCharacter(final CreationCharacter selectedcharacter) {
        this.character = selectedcharacter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreationCharacter getCharacter() {
        return character;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCard(final AbstractCard card) {
        hand.add(card);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AbstractCard> getHand() {
        return List.copyOf(hand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AbstractCard> findMatchingCard(
        final AbstractCard characterCard, 
        final AbstractCard weapon, 
        final AbstractCard room) {

        final List<AbstractCard> shuffled = new ArrayList<>(hand);
        Collections.shuffle(shuffled);
        return shuffled.stream()
                .filter(c -> c.getName().equals(characterCard.getName())
                        || c.getName().equals(weapon.getName())
                        || c.getName().equals(room.getName()))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminate() {
        this.eliminated = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEliminated() {
        return this.eliminated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restore() {
        this.eliminated = false;
        this.character = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearHand() {
        hand.clear();
    }
}
